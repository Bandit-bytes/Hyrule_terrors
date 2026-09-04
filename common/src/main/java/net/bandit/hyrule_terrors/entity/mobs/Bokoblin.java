package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.ai.TerrorTargeting;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.bandit.hyrule_terrors.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Bokoblin extends AbstractTerrorMob {

    public AnimationDispatcher dispatcher;

    private int hornCooldownTicks = 0;

    private boolean hasAlertedThisTarget = false;

    private static final ResourceLocation BOKOBLIN_HEAD_ID =
        ResourceLocation.fromNamespaceAndPath("hyrule_terrors", "bokoblin_head");

    private static final EntityDataAccessor<Boolean> HORN_BLOWER =
        SynchedEntityData.defineId(Bokoblin.class, EntityDataSerializers.BOOLEAN);

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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HORN_BLOWER, false);
    }

    public boolean isHornBlower() {
        return this.entityData.get(HORN_BLOWER);
    }

    public void setHornBlower(boolean value) {
        this.entityData.set(HORN_BLOWER, value);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
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

        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true) {

            @Override
            public boolean canUse() {
                if (!super.canUse())
                    return false;

                if (this.target instanceof Player player) {
                    if (mob.getLastHurtByMob() == player)
                        return true;

                    return !((Bokoblin) mob).isDisguised(player);
                }
                return true;
            }

            @Override
            public boolean canContinueToUse() {
                if (!super.canContinueToUse())
                    return false;

                if (this.target instanceof Player player) {
                    if (mob.getLastHurtByMob() == player)
                        return true;

                    return !((Bokoblin) mob).isDisguised(player);
                }
                return true;
            }
        });

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(
            this,
            LivingEntity.class,
            10,
            true,
            false,
            TerrorTargeting::isWhitelistedTarget
        ));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (hornCooldownTicks > 0)
            hornCooldownTicks--;

        if (this.level().isClientSide())
            return;
        if (!this.isHornBlower())
            return;

        LivingEntity target = this.getTarget();

        if (!(target instanceof Player player) || !target.isAlive()) {
            hasAlertedThisTarget = false;
            return;
        }

        if (!hasAlertedThisTarget && hornCooldownTicks <= 0) {
            hasAlertedThisTarget = true;
            hornCooldownTicks = 20 * 12;

            blowHornAndAlertAllies(player);
        }
    }

    private void blowHornAndAlertAllies(Player player) {
        if (!(this.level() instanceof ServerLevel serverLevel))
            return;
        if (isDisguised(player))
            return;

        SoundEvent hornSound = SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(3).value();

        serverLevel.playSound(
            null,
            this.blockPosition(),
            hornSound,
            SoundSource.HOSTILE,
            2.0F,
            1.0F
        );

        double radius = 24.0D;
        AABB box = this.getBoundingBox().inflate(radius);

        List<Bokoblin> allies = serverLevel.getEntitiesOfClass(
            Bokoblin.class,
            box,
            b -> b != this && b.isAlive()
        );

        for (Bokoblin ally : allies) {
            if (ally.getTarget() == null) {
                ally.setTarget(player);
            }
        }
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnType) {
        if (level.getDifficulty() == Difficulty.PEACEFUL)
            return false;

        BlockPos pos = this.blockPosition();
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        if (blockLight > 4)
            return false;

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
        if (this.isHornBlower()) {
            float chance = 0.30F;

            if (this.random.nextFloat() < chance) {
                this.spawnAtLocation(BlockRegistry.BOKOBLIN_HEAD.get());
            }
        }

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
        ServerLevelAccessor level,
        DifficultyInstance difficulty,
        MobSpawnType reason,
        @Nullable SpawnGroupData spawnData
    ) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData);

        boolean horn = this.random.nextInt(6) == 0;
        this.setHornBlower(horn);

        if (horn) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.GOAT_HORN));
            this.setDropChance(EquipmentSlot.MAINHAND, 0.25F);
        }
        return data;
    }

    private boolean isDisguised(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        return BuiltInRegistries.ITEM.getKey(helmet.getItem()).equals(BOKOBLIN_HEAD_ID);
    }
}
