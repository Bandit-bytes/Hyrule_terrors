package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.ai.TerrorTargeting;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * Skeletal Moblin that rises at night. Undead (MobType.UNDEAD), so Smite hurts it, healing potions
 * harm it, and it crumbles (burns) in daylight like the Stal monsters of Breath of the Wild.
 */
public class Stalmoblin extends AbstractTerrorMob {

    public Stalmoblin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 8;
    }

    @Override
    public net.minecraft.world.entity.MobType getMobType() {
        return net.minecraft.world.entity.MobType.UNDEAD;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.stalmoblinHealth)
            .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.stalmoblinAttackDamage)
            .add(Attributes.ATTACK_SPEED, 1.0)
            .add(Attributes.ATTACK_KNOCKBACK, 1.2)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.3)
            .add(Attributes.FOLLOW_RANGE, 28.0)
            .add(Attributes.MOVEMENT_SPEED, HyruleTerrorsMod.config.stalmoblinMovementSpeed);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.25D, false) {

            @Override
            protected void checkAndPerformAttack(LivingEntity target, double distanceToSqr) {
                if (distanceToSqr <= this.getAttackReachSqr(target) && this.getTicksUntilNextAttack() <= 0) {
                    super.checkAndPerformAttack(target, distanceToSqr);
                    ((Stalmoblin) this.mob).animationDispatcher.attack();
                }
            }
        });
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 10.0F));

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
        if (this.isAlive() && HyruleTerrorsMod.config.stalmoblinBurnsInDaylight && this.isSunBurnTick()) {
            this.setSecondsOnFire(8);
        }
        super.aiStep();
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

        // Usually a Spiked Moblin Club; otherwise whatever is lying around, often a monster's own arm.
        int roll = this.random.nextInt(10);
        ItemStack weapon = switch (roll) {
            case 0, 1, 2, 3, 4 -> new ItemStack(ItemRegistry.SPIKED_MOBLIN_CLUB.get());
            case 5, 6 -> new ItemStack(ItemRegistry.BOKOBLIN_ARM.get());
            case 7 -> new ItemStack(Items.STONE_AXE);
            default -> ItemStack.EMPTY;
        };
        if (!weapon.isEmpty()) {
            this.setItemSlot(EquipmentSlot.MAINHAND, weapon);
            this.setDropChance(EquipmentSlot.MAINHAND, roll <= 4 ? 0.15F : 0.08F);
        }
        return data;
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.7F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.2F, 0.8F);
    }
}
