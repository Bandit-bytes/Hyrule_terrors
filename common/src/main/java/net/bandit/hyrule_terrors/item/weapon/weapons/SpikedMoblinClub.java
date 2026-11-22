package net.bandit.hyrule_terrors.item.weapon.weapons;

import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * Spiked Moblin Club, carried by Stalmoblins. Slow, but every hit knocks the target back and can stagger it.
 */
public class SpikedMoblinClub extends SwordItem {

    public SpikedMoblinClub(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();
        if (!level.isClientSide()) {
            level.playSound(
                null,
                target.blockPosition(),
                SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR,
                SoundSource.PLAYERS,
                0.6F,
                0.9F
            );
            level.playSound(
                null,
                target.blockPosition(),
                SoundEvents.GENERIC_BIG_FALL,
                SoundSource.PLAYERS,
                0.4F,
                1.2F
            );

            target.knockback(0.8D, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
            if (attacker.getRandom().nextFloat() < 0.25F) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 1, false, true), attacker);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return repairWith.is(ItemTags.PLANKS) || super.isValidRepairItem(toRepair, repairWith);
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
                Component.translatable("item.hyrule_terrors.spiked_moblin_club.tooltip").withStyle(ChatFormatting.GOLD)
            );
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.spiked_moblin_club.tooltip1").withStyle(ChatFormatting.GRAY)
            );
        } else {
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.hold_shift").withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }
}
