package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Keese extends AbstractFlyTerrorMob{
    public AnimationDispatcher dispatcher;

    public Keese(EntityType<? extends AbstractFlyTerrorMob> entityType, Level level) {
        super(entityType, level);
        dispatcher = new AnimationDispatcher(this);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.keeseHealth)
                .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.keeseAttackDamage)
                .add(Attributes.ATTACK_SPEED, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3D, false) {
            @Override
            protected void checkAndPerformAttack(LivingEntity target) {
                if (this.canPerformAttack(target)) {
                    super.checkAndPerformAttack(target);
                    if (this.mob instanceof Keese keese) {
                        keese.dispatcher.attack();
                    }
                }
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
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

        int lootingLevel = 0;
        if (source.getEntity() instanceof LivingEntity attacker) {
            Holder<Enchantment> looting = level.registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolderOrThrow(Enchantments.LOOTING);

            lootingLevel = EnchantmentHelper.getItemEnchantmentLevel(looting, attacker.getMainHandItem());
        }
        int jellyAmount = this.random.nextInt(3);
        if (jellyAmount > 0) {
            this.spawnAtLocation(ItemRegistry.CHUCHU_JELLY.get(), jellyAmount + this.random.nextInt(lootingLevel + 1));
        }
    }
}

