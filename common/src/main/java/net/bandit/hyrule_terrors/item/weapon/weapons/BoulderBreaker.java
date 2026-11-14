package net.bandit.hyrule_terrors.item.weapon.weapons;

import net.bandit.hyrule_terrors.util.CombatHelper;
import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BoulderBreaker extends SwordItem {

    public BoulderBreaker(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();

        if (!level.isClientSide()) {
            level.playSound(null, attacker.blockPosition(), SoundEvents.STONE_BREAK, SoundSource.PLAYERS, 1.0F, 0.75F);
        }

        // Armor-crusher: extra damage against armored foes.
        if (target.getArmorCoverPercentage() > 0.25f && attacker instanceof Player player) {
            CombatHelper.dealBonusDamage(target, level.damageSources().playerAttack(player), 3.0F);
        }

        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1, false, false));

        // hurtEnemy only runs on the server, so particles have to be sent from here.
        if (level instanceof ServerLevel serverLevel) {
            BlockState stateBelow = level.getBlockState(target.blockPosition().below());
            if (!stateBelow.isAir()) {
                serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, stateBelow),
                    target.getX(),
                    target.getY() + 0.1D,
                    target.getZ(),
                    16,
                    0.3D,
                    0.05D,
                    0.3D,
                    0.15D
                );
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            return 8.0F;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        // Without this, stone broke quickly but dropped nothing.
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            return !state.is(getTier().getIncorrectBlocksForDrops());
        }
        return super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return repairWith.is(Items.NETHERITE_INGOT) || super.isValidRepairItem(toRepair, repairWith);
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (TooltipHelper.hasShiftDown()) {
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.boulder_breaker.tooltip").withStyle(ChatFormatting.RED)
            );
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.boulder_breaker.tooltip1").withStyle(ChatFormatting.GRAY)
            );
        } else {
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.hold_shift").withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }
}
