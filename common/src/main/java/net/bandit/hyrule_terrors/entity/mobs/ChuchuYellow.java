package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.ai.TerrorTargeting;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Electric Yellow ChuChu variant. It alternates between charged and discharged states. While charged, touching it
 * shocks players and direct melee hits are reflected back to the attacker. Projectiles can safely damage it while
 * charged.
 */
public class ChuchuYellow extends AbstractTerrorMob {

    private static final EntityDataAccessor<Boolean> CHARGED = SynchedEntityData.defineId(
        ChuchuYellow.class,
        EntityDataSerializers.BOOLEAN
    );

    private static final int CHARGED_DURATION = 20 * 5;

    private static final int DISCHARGED_DURATION = 20 * 3;

    public AnimationDispatcher dispatcher;

    private int chargeStateTicks = CHARGED_DURATION;

    public ChuchuYellow(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        dispatcher = new AnimationDispatcher(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.chuchuYellowHealth)
            .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.chuchuYellowAttackDamage)
            .add(Attributes.ATTACK_SPEED, 1.0)
            .add(Attributes.ATTACK_KNOCKBACK, 1.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CHARGED, true);
    }

    public boolean isCharged() {
        return this.entityData.get(CHARGED);
    }

    public void setCharged(boolean charged) {
        this.entityData.set(CHARGED, charged);
        this.chargeStateTicks = charged ? CHARGED_DURATION : DISCHARGED_DURATION;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3D, false) {

            @Override
            protected void checkAndPerformAttack(LivingEntity target) {
                if (this.canPerformAttack(target)) {
                    super.checkAndPerformAttack(target);
                    if (this.mob instanceof ChuchuYellow chuchu) {
                        chuchu.dispatcher.attack();
                    }
                }
            }
        });
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(
            3,
            new NearestAttackableTargetGoal<>(
                this,
                LivingEntity.class,
                10,
                true,
                false,
                TerrorTargeting::isWhitelistedTarget
            )
        );
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide()) {
            if (this.isCharged() && this.tickCount % 2 == 0) {
                double x = this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth();
                double y = this.getY() + this.random.nextDouble() * this.getBbHeight();
                double z = this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth();
                this.level().addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z, 0.0D, 0.02D, 0.0D);
            }
            return;
        }

        if (--this.chargeStateTicks <= 0) {
            this.setCharged(!this.isCharged());
            this.level()
                .playSound(
                    null,
                    this.blockPosition(),
                    SoundEvents.LIGHTNING_BOLT_IMPACT,
                    SoundSource.HOSTILE,
                    0.35F,
                    this.isCharged() ? 1.6F : 0.8F
                );
        }
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (!this.level().isClientSide() && this.isCharged() && player.isAlive()) {
            player.hurt(this.level().damageSources().lightningBolt(), HyruleTerrorsMod.config.chuchuYellowAttackDamage);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!this.level().isClientSide() && this.isCharged()) {
            Entity direct = source.getDirectEntity();
            Entity attacker = source.getEntity();

            // For direct melee damage both references are the attacking living entity.
            // Projectiles have a projectile as the direct entity and therefore remain safe.
            if (direct instanceof LivingEntity livingAttacker && direct == attacker && livingAttacker != this) {
                livingAttacker.hurt(
                    this.level().damageSources().lightningBolt(),
                    HyruleTerrorsMod.config.chuchuYellowAttackDamage + 1.0F
                );
                if (this.level() instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(
                        ParticleTypes.ELECTRIC_SPARK,
                        livingAttacker.getX(),
                        livingAttacker.getY() + livingAttacker.getBbHeight() * 0.5D,
                        livingAttacker.getZ(),
                        12,
                        0.25D,
                        0.35D,
                        0.25D,
                        0.08D
                    );
                }
                return false;
            }
        }

        return super.hurt(source, amount);
    }

    @Override
    public void thunderHit(ServerLevel level, net.minecraft.world.entity.LightningBolt lightning) {
        // Electricity powers Yellow ChuChus instead of damaging them.
        this.setCharged(true);
        level.sendParticles(
            ParticleTypes.ELECTRIC_SPARK,
            this.getX(),
            this.getY() + 0.5D,
            this.getZ(),
            24,
            0.4D,
            0.5D,
            0.4D,
            0.1D
        );
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnType) {
        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        BlockPos pos = this.blockPosition();
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        if (blockLight > 4) {
            return false;
        }
        return super.checkSpawnRules(level, spawnType);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SLIME_SQUISH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SLIME_JUMP, 0.15F, 1.0F);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        if (!level.isClientSide()) {
            int xpDrop = 5 + this.random.nextInt(3);
            level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), xpDrop));
        }
    }
}
