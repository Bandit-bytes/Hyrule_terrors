package net.bandit.hyrule_terrors.item.weapon.weapons;

import net.bandit.hyrule_terrors.item.armor.sets.KnightArmorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class KnightsClaymore extends SwordItem {

    public KnightsClaymore(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();

        if (!level.isClientSide()) {
            level.playSound(null, attacker.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 0.4F, 0.7F);
        }

        target.knockback(0.6D, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());

        if (attacker instanceof Player player) {
            boolean wearingFullKnightSet = isWearingFullKnightSet(player);

            if (wearingFullKnightSet) {
                target.hurt(level.damageSources().playerAttack(player), 3.0F);
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 0, false, false));
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    private boolean isWearingFullKnightSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof KnightArmorItem &&
            player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof KnightArmorItem &&
            player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof KnightArmorItem &&
            player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof KnightArmorItem;
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return repairWith.is(Items.IRON_INGOT) || repairWith.is(Items.IRON_BLOCK) || super.isValidRepairItem(
            toRepair,
            repairWith
        );
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (Screen.hasShiftDown()) {
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
