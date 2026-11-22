package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.ai.TerrorTargeting;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class Lizalfos extends AbstractTerrorMob {

    public AnimationDispatcher dispatcher;

    public Lizalfos(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        dispatcher = new AnimationDispatcher(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.lizalfosHealth)
            .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.lizalfosAttackDamage)
            .add(Attributes.ATTACK_SPEED, 1.3)
            .add(Attributes.ATTACK_KNOCKBACK, 1.0)
            .add(Attributes.MOVEMENT_SPEED, HyruleTerrorsMod.config.lizalfosMovementSpeed);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3D, false) {

            @Override
            protected void checkAndPerformAttack(LivingEntity target, double distanceToSqr) {
                if (distanceToSqr <= this.getAttackReachSqr(target) && this.getTicksUntilNextAttack() <= 0) {
                    super.checkAndPerformAttack(target, distanceToSqr);
                    if (this.mob instanceof Lizalfos lizalfos) {
                        lizalfos.dispatcher.attack();
                    }
                }
            }
        });
        // this.goalSelector.addGoal(3, new SneakyCrawlGoal(this, 1.0D));

        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));

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
    public void tick() {
        super.tick();

        // Badly hurt Lizalfos scramble away. This used to repath and resend the crawl animation every tick on both
        // sides; it now runs server-side, a few times a second, and only once the previous path has finished.
        if (
            !this.level().isClientSide()
                && this.getHealth() < this.getMaxHealth() * 0.3F
                && this.tickCount % 10 == 0
                && this.getNavigation().isDone()
        ) {
            this.dispatcher.crawl();
            this.getNavigation()
                .moveTo(
                    this.getX() + (random.nextBoolean() ? 4 : -4),
                    this.getY(),
                    this.getZ() + (random.nextBoolean() ? 4 : -4),
                    1.2
                );
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.AXOLOTL_IDLE_AIR;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.TURTLE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.TURTLE_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.TURTLE_SHAMBLE, 0.15F, 1.0F);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(
        ServerLevelAccessor level,
        DifficultyInstance difficulty,
        MobSpawnType reason,
        @Nullable SpawnGroupData spawnData,
        @Nullable net.minecraft.nbt.CompoundTag dataTag
    ) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);

        if (this.random.nextInt(4) == 0) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.LIZAL_SPEAR.get()));
            this.setDropChance(EquipmentSlot.MAINHAND, 0.25F);
        }

        return data;
    }
}
