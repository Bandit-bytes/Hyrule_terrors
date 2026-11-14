package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.ai.TerrorTargeting;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * White (Ice) Chuchu. Its touch and its attacks freeze, it bursts into a frost cloud when killed, and fire melts it
 * back into a Blue Chuchu.
 */
public class ChuchuWhite extends AbstractTerrorMob {

    public AnimationDispatcher dispatcher;

    public ChuchuWhite(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        dispatcher = new AnimationDispatcher(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.chuchuWhiteHealth)
            .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.chuchuWhiteAttackDamage)
            .add(Attributes.ATTACK_SPEED, 1.0)
            .add(Attributes.ATTACK_KNOCKBACK, 1.0)
            .add(Attributes.MOVEMENT_SPEED, 0.28);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3D, false) {

            @Override
            protected void checkAndPerformAttack(LivingEntity target) {
                if (this.canPerformAttack(target)) {
                    super.checkAndPerformAttack(target);
                    if (this.mob instanceof ChuchuWhite chuchu) {
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
    public boolean canFreeze() {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide()) {
            if (this.tickCount % 4 == 0) {
                this.level()
                    .addParticle(
                        ParticleTypes.SNOWFLAKE,
                        getRandomX(0.5D),
                        getRandomY(),
                        getRandomZ(0.5D),
                        0.0D,
                        0.01D,
                        0.0D
                    );
            }
            return;
        }

        // Fire melts it back into a regular Blue Chuchu.
        if (this.isOnFire()) {
            Chuchu blue = this.convertTo(EntityRegistry.CHUCHU.get(), true);
            if (blue != null) {
                blue.clearFire();
                this.level()
                    .playSound(
                        null,
                        blue.blockPosition(),
                        SoundEvents.FIRE_EXTINGUISH,
                        SoundSource.HOSTILE,
                        0.8F,
                        1.2F
                    );
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            freeze(living, 1.0F);
        }
        return hit;
    }

    @Override
    public void playerTouch(Player player) {
        super.playerTouch(player);
        if (!this.level().isClientSide() && player.isAlive() && !player.isCreative() && this.tickCount % 10 == 0) {
            player.setTicksFrozen(Math.min(player.getTicksFrozen() + 20, player.getTicksRequiredToFreeze() + 40));
        }
    }

    private static void freeze(LivingEntity target, float strength) {
        if (!target.canFreeze()) {
            return;
        }
        target.setTicksFrozen(
            Math.min(target.getTicksRequiredToFreeze() + 80, target.getTicksFrozen() + (int) (140 * strength))
        );
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (60 * strength), 1));
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                ParticleTypes.SNOWFLAKE,
                getX(),
                getY() + 0.4D,
                getZ(),
                40,
                0.8D,
                0.5D,
                0.8D,
                0.05D
            );
            serverLevel.playSound(null, blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.HOSTILE, 0.8F, 1.3F);
            AABB box = getBoundingBox().inflate(2.5D);
            for (
                LivingEntity nearby : serverLevel.getEntitiesOfClass(
                    LivingEntity.class,
                    box,
                    e -> e != this && e.isAlive()
                )
            ) {
                freeze(nearby, 0.6F);
            }
        }
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnType) {
        if (level.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        if (level.getBrightness(LightLayer.BLOCK, this.blockPosition()) > 4) {
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
}
