package net.bandit.hyrule_terrors.entity.mobs;

import mod.azure.azurelib.util.MoveAnalysis;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.ai.RandomFlyingGoal;
import net.bandit.hyrule_terrors.entity.ai.TerrorTargeting;
import net.bandit.hyrule_terrors.entity.attack.FlyingAttackGoal;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Keese: cave bats that swarm the player. Built on vanilla {@link Bat} for its flight physics, but tagged as an
 * {@link Enemy} so it behaves like a hostile mob (Peaceful despawn, golem targeting, no sleeping nearby).
 */
public class Keese extends Bat implements Enemy {

    public AnimationDispatcher dispatcher;

    public final AnimationDispatcher animationDispatcher;

    public final MoveAnalysis moveAnalysis;

    public Keese(EntityType<? extends Bat> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new AnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
        dispatcher = new AnimationDispatcher(this);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.setNoGravity(true);
        this.xpReward = 5;
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
        this.targetSelector.addGoal(
            2,
            new NearestAttackableTargetGoal<>(
                this,
                net.minecraft.world.entity.LivingEntity.class,
                10,
                true,
                false,
                TerrorTargeting::isWhitelistedTarget
            )
        );
    }

    @Override
    protected void customServerAiStep() {
        // Bat's own AI randomly steers every tick and fights the attack goal. Let goals drive while hunting.
        if (this.getTarget() != null) {
            if (this.isResting()) {
                this.setResting(false);
            }
            return;
        }
        super.customServerAiStep();
    }

    @Override
    public boolean shouldDespawnInPeaceful() {
        return true;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            onElementalHit(living);
        }
        return hit;
    }

    /** Hook for elemental Keese variants. */
    protected void onElementalHit(LivingEntity target) {}

    @Override
    public void swing(net.minecraft.world.InteractionHand hand, boolean updateSelf) {
        super.swing(hand, updateSelf);

        if (this.level().isClientSide) {
            dispatcher.attack();
        }
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
    public void tick() {
        super.tick();
        moveAnalysis.update();

        if (this.level().isClientSide) {
            boolean isFlying = !this.onGround();
            Runnable animationRunner = isFlying ? animationDispatcher::fly : animationDispatcher::idle;
            animationRunner.run();
        }
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
}
