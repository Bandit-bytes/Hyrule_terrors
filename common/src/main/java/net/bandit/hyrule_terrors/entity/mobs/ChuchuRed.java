package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class ChuchuRed extends AbstractTerrorMob {

    public AnimationDispatcher dispatcher;

    private boolean hasExploded = false;

    public ChuchuRed(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        dispatcher = new AnimationDispatcher(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.chuchuRedHealth)
            .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.chuchuRedAttackDamage)
            .add(Attributes.ATTACK_SPEED, 1.0)
            .add(Attributes.ATTACK_KNOCKBACK, 1.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3);
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
                    if (this.mob instanceof Chuchu chuchu) {
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
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.hasExploded) {
            return super.hurt(source, amount);
        }

        if (!this.level().isClientSide() && shouldExplodeFrom(source)) {
            this.hasExploded = true;
            explodeAndIgnite(source);
            this.discard();
            return true;
        }

        return super.hurt(source, amount);
    }

    private boolean shouldExplodeFrom(DamageSource source) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            return false;
        }
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            return false;
        }
        return source.getDirectEntity() instanceof LivingEntity;
    }

    private void explodeAndIgnite(DamageSource source) {
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        serverLevel.sendParticles(
            ParticleTypes.FLAME,
            this.getX(),
            this.getY() + 0.5D,
            this.getZ(),
            35,
            0.35D,
            0.35D,
            0.35D,
            0.02D
        );
        serverLevel.playSound(
            null,
            this.blockPosition(),
            SoundEvents.BLAZE_SHOOT,
            SoundSource.HOSTILE,
            1.0F,
            1.0F
        );

        serverLevel.explode(
            this,
            this.getX(),
            this.getY(),
            this.getZ(),
            1.8F,
            true,
            Level.ExplosionInteraction.NONE
        );

        final double radius = 3.0D;
        AABB box = this.getBoundingBox().inflate(radius);

        for (
            LivingEntity living : serverLevel.getEntitiesOfClass(LivingEntity.class, box, e -> e != this && e.isAlive())
        ) {
            living.setRemainingFireTicks(6);
        }
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

        if (level.isClientSide())
            return;
        this.dropExperience();
    }

    protected void dropExperience() {
        int baseXP = 5;
        int xpDrop = baseXP + this.random.nextInt(3);
        this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY(), this.getZ(), xpDrop));
    }
}
