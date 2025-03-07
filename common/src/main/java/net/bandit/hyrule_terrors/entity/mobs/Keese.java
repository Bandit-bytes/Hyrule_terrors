package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.attack.FlyingAttackGoal;
import net.bandit.hyrule_terrors.entity.ai.RandomFlyingGoal;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Keese extends AbstractFlyTerrorMob {
    public AnimationDispatcher dispatcher;

    public Keese(EntityType<? extends AbstractFlyTerrorMob> entityType, Level level) {
        super(entityType, level);
        dispatcher = new AnimationDispatcher(this);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setNoGravity(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.keeseHealth)
                .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.keeseAttackDamage)
                .add(Attributes.ATTACK_SPEED, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.FLYING_SPEED, 0.6)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomFlyingGoal(this, 0.5));
        this.goalSelector.addGoal(2, new FlyingAttackGoal(this, 0.8));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.tickCount % 20 == 0) {
            double x = this.getX() + (this.random.nextFloat() - 0.5) * 8.0;
            double y = this.getY() + (this.random.nextFloat() - 0.2) * 2.5 + 5.0;
            double z = this.getZ() + (this.random.nextFloat() - 0.5) * 8.0;
            this.getMoveControl().setWantedPosition(x, y, z, 0.5);
        }

        if (!this.level().isClientSide && this.tickCount % 40 == 0) {
            double hoverAmount = Math.sin(this.tickCount * 0.2) * 0.4;
            this.setDeltaMovement(this.getDeltaMovement().add(0, hoverAmount, 0));
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource arg) {
        return SoundEvents.BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BAT_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.BAT_TAKEOFF, 0.15F, 1.0F);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        if (level.isClientSide()) return;
        this.dropExperience();
    }

    protected void dropExperience() {
        int baseXP = 5;
        int xpDrop = baseXP + this.random.nextInt(3);
        this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY(), this.getZ(), xpDrop));
    }

}
