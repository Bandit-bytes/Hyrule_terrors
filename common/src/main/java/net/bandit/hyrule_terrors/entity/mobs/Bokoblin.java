package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
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
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Bokoblin extends AbstractTerrorMob {

    public AnimationDispatcher dispatcher;

    public Bokoblin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        dispatcher = new AnimationDispatcher(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.bokoblinHealth)
            .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.bokoblinAttackDamage)
            .add(Attributes.ATTACK_SPEED, 1.3)
            .add(Attributes.ATTACK_KNOCKBACK, 1.0)
            .add(Attributes.MOVEMENT_SPEED, HyruleTerrorsMod.config.BokoblinMovementSpeed);
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
                    if (this.mob instanceof Bokoblin bokoblin) {
                        bokoblin.dispatcher.attack();
                    }
                }
            }
        });
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
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
        return SoundEvents.PIGLIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.PIGLIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIGLIN_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.PIGLIN_STEP, 0.15F, 1.0F);
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

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(
        ServerLevelAccessor serverLevelAccessor,
        DifficultyInstance difficultyInstance,
        MobSpawnType mobSpawnType,
        @Nullable SpawnGroupData spawnGroupData
    ) {
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
    }
}
