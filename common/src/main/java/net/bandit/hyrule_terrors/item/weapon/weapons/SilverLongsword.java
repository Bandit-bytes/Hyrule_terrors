package net.bandit.hyrule_terrors.item.weapon.weapons;

import net.bandit.hyrule_terrors.item.armor.sets.ZoraArmorItem;
import net.bandit.hyrule_terrors.util.ArmorSetHelper;
import net.bandit.hyrule_terrors.util.CombatHelper;
import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class SilverLongsword extends SwordItem {

    public SilverLongsword(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();

        if (!level.isClientSide()) {
            level.playSound(null, attacker.blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        if (attacker.isInWaterOrRain() && !level.isClientSide()) {
            float bonusDamage = 4.0F;
            if (ArmorSetHelper.hasFullSet(attacker, ZoraArmorItem.class)) {
                bonusDamage += 2.0F;
            }
            CombatHelper.dealBonusDamage(target, level.damageSources().indirectMagic(attacker, attacker), bonusDamage);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return repairWith.is(Items.PRISMARINE_CRYSTALS) || repairWith.is(Items.PRISMARINE_SHARD)
            || super.isValidRepairItem(toRepair, repairWith);
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
                Component.translatable("item.hyrule_terrors.silver_longsword.tooltip").withStyle(ChatFormatting.AQUA)
            );
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.silver_longsword.tooltip1").withStyle(ChatFormatting.GRAY)
            );
        } else {
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.hold_shift").withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }
}
