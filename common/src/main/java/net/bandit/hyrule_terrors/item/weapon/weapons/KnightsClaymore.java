package net.bandit.hyrule_terrors.item.weapon.weapons;

import net.bandit.hyrule_terrors.item.armor.sets.KnightArmorItem;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.bandit.hyrule_terrors.util.ArmorSetHelper;
import net.bandit.hyrule_terrors.util.CombatHelper;
import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class KnightsClaymore extends SwordItem {

    public KnightsClaymore(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();

        if (!level.isClientSide()) {
            level.playSound(null, attacker.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.4F, 0.7F);
        }

        target.knockback(0.6D, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());

        if (attacker instanceof Player player && ArmorSetHelper.hasFullSet(player, KnightArmorItem.class)) {
            CombatHelper.dealBonusDamage(target, level.damageSources().playerAttack(player), 3.0F);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, false, false));
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return repairWith.is(ItemRegistry.STEEL.get()) || repairWith.is(Items.IRON_INGOT) || super.isValidRepairItem(
            toRepair,
            repairWith
        );
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        @org.jetbrains.annotations.Nullable net.minecraft.world.level.Level level,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (TooltipHelper.hasShiftDown()) {
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.knights_claymore.tooltip").withStyle(ChatFormatting.GOLD)
            );
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.knights_claymore.tooltip1").withStyle(ChatFormatting.GRAY)
            );
        } else {
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.hold_shift").withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }
}
