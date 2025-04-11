package net.bandit.hyrule_terrors.item.weapon.weapons;

import net.bandit.hyrule_terrors.item.armor.sets.ZoraArmorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class SilverLongsword extends SwordItem {

    public SilverLongsword(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();

        if (!level.isClientSide()) {
            level.playSound(null, attacker.blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        boolean isWet = attacker.isInWaterOrRain();
        boolean wearingFullZoraSet = false;

        if (attacker instanceof Player player) {
            ItemStack head = player.getInventory().getArmor(3);
            ItemStack chest = player.getInventory().getArmor(2);
            ItemStack legs = player.getInventory().getArmor(1);
            ItemStack boots = player.getInventory().getArmor(0);

            wearingFullZoraSet =
                    head.getItem() instanceof ZoraArmorItem &&
                            chest.getItem() instanceof ZoraArmorItem &&
                            legs.getItem() instanceof ZoraArmorItem &&
                            boots.getItem() instanceof ZoraArmorItem;
        }

        if (isWet && !level.isClientSide()) {
            float bonusDamage = 4.0F;

            if (wearingFullZoraSet) {
                bonusDamage += 2.0F;
            }

            target.hurt(level.damageSources().magic(), bonusDamage);
        }
        return super.hurtEnemy(stack, target, attacker);
    }


    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return repairWith.is(Items.PRISMARINE_CRYSTALS) || repairWith.is(Items.PRISMARINE_SHARD) || super.isValidRepairItem(toRepair, repairWith);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.silver_longsword.tooltip").withStyle(ChatFormatting.AQUA));
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.silver_longsword.tooltip1").withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift").withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
