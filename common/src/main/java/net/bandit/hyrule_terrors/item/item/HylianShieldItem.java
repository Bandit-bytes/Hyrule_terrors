package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HylianShieldItem extends ShieldItem {

    private static final int STORM_COOLDOWN_TICKS = 20 * 18;
    private static final int STORM_CHECK_INTERVAL = 10;
    private static final float LIGHTNING_CHANCE = 0.12f;
    private static final double BURST_RADIUS = 7.0D;
    private static final float BURST_DAMAGE = 4.0F;
    private static final int BURST_SLOW_TICKS = 20 * 2;
    private static final int BURST_SLOW_AMPLIFIER = 2;

    private static final String TAG_STORM_CD = "HylianStormCD";
    private static final String TAG_LAST_ZAP = "HylianLastZapTick";

    public HylianShieldItem(Properties props) {
        super(props);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (level.isClientSide) return;
        if (!(entity instanceof Player player)) return;
        boolean holding = isHoldingThisShield(player, stack);
        if (holding) {
            if (player.isOnFire()) {
                player.clearFire();
            }
        }

        CompoundTag tag = stack.getOrCreateTag();
        int cd = tag.getInt(TAG_STORM_CD);
        if (cd > 0) tag.putInt(TAG_STORM_CD, cd - 1);
        if (!isBlockingWithThisShield(player, stack)) return;


        if ((level.getGameTime() % STORM_CHECK_INTERVAL) != 0) return;


        if (!level.isThundering()) return;


        if (tag.getInt(TAG_STORM_CD) > 0) return;

        BlockPos pos = player.blockPosition();
        if (!level.canSeeSky(pos)) return;

        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        if (blockLight > 13) return;

        if (player.getRandom().nextFloat() > LIGHTNING_CHANCE) return;

        tag.putInt(TAG_STORM_CD, STORM_COOLDOWN_TICKS);

        if (level instanceof ServerLevel serverLevel) {
            pullLightningAndBurst(serverLevel, player, stack);
        }
    }

    private static boolean isHoldingThisShield(Player player, ItemStack stack) {
        return ItemStack.isSameItemSameTags(player.getMainHandItem(), stack)
                || ItemStack.isSameItemSameTags(player.getOffhandItem(), stack);
    }

    private static boolean isBlockingWithThisShield(Player player, ItemStack stack) {
        return player.isUsingItem()
                && ItemStack.isSameItemSameTags(player.getUseItem(), stack);
    }


    private static void pullLightningAndBurst(ServerLevel level, Player player, ItemStack stack) {
        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.moveTo(player.getX(), player.getY(), player.getZ());
            level.addFreshEntity(bolt);
        }

        CompoundTag tag = stack.getOrCreateTag();
        long now = level.getGameTime();
        long lastZap = tag.getLong(TAG_LAST_ZAP);
        if (lastZap == now) return;
        tag.putLong(TAG_LAST_ZAP, now);

        AABB box = player.getBoundingBox().inflate(BURST_RADIUS);

        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                box,
                e -> e != player && e.isAlive()
        );

        for (LivingEntity target : targets) {
            if (target.getType().getCategory() != MobCategory.MONSTER) continue;

            target.hurt(level.damageSources().lightningBolt(), BURST_DAMAGE);
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN,
                    BURST_SLOW_TICKS,
                    BURST_SLOW_AMPLIFIER,
                    false,
                    true
            ));
        }
        stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            @Nullable Level level,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip")
                    .withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_royal")
                    .withStyle(ChatFormatting.DARK_GREEN));
            tooltipComponents.add(Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_durability")
                    .withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_fire")
                    .withStyle(ChatFormatting.RED));
            tooltipComponents.add(Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_lightning")
                    .withStyle(ChatFormatting.AQUA));
        } else {
            tooltipComponents.add(Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hold_shift")
                    .withStyle(ChatFormatting.DARK_GRAY));
            tooltipComponents.add(Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_1")
                    .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        }
    }
}
