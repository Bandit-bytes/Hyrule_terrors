package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;

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

        if (isHoldingThisExactStack(player, stack) && player.isOnFire()) {
            player.clearFire();
        }

        int cd = getInt(stack, TAG_STORM_CD, 0);
        if (cd > 0) setInt(stack, TAG_STORM_CD, cd - 1);
        if (!isBlockingWithThisExactStack(player, stack)) return;
        if ((level.getGameTime() % STORM_CHECK_INTERVAL) != 0) return;
        if (!level.isThundering()) return;
        if (getInt(stack, TAG_STORM_CD, 0) > 0) return;

        BlockPos pos = player.blockPosition();
        if (!level.canSeeSky(pos)) return;

        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        if (blockLight > 13) return;

        if (player.getRandom().nextFloat() > LIGHTNING_CHANCE) return;

        setInt(stack, TAG_STORM_CD, STORM_COOLDOWN_TICKS);

        if (level instanceof ServerLevel serverLevel) {
            pullLightningAndBurst(serverLevel, player, stack);
        }
    }

    private static boolean isHoldingThisExactStack(Player player, ItemStack stack) {
        return player.getMainHandItem() == stack || player.getOffhandItem() == stack;
    }

    private static boolean isBlockingWithThisExactStack(Player player, ItemStack stack) {
        return player.isUsingItem() && player.getUseItem() == stack;
    }

    private static void pullLightningAndBurst(ServerLevel level, Player player, ItemStack stack) {
        long now = level.getGameTime();
        long lastZap = getLong(stack, TAG_LAST_ZAP, -1L);
        if (lastZap == now) return;
        setLong(stack, TAG_LAST_ZAP, now);

        var bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.moveTo(player.getX(), player.getY(), player.getZ());
            level.addFreshEntity(bolt);
        }

        AABB box = player.getBoundingBox().inflate(BURST_RADIUS);

        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                box,
                e -> e != player && e.isAlive()
        );

        boolean hitAny = false;

        for (LivingEntity target : targets) {
            if (target.getType().getCategory() != MobCategory.MONSTER) continue;

            hitAny = true;

            target.hurt(level.damageSources().lightningBolt(), BURST_DAMAGE);
            target.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN,
                    BURST_SLOW_TICKS,
                    BURST_SLOW_AMPLIFIER,
                    false,
                    true
            ));
        }

        if (hitAny) {
            stack.hurtAndBreak(
                    1,
                    player,
                    LivingEntity.getSlotForHand(player.getUsedItemHand())
            );
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
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


    private static CompoundTag getCustomDataTag(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.copyTag();
    }

    private static void setCustomDataTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    private static int getInt(ItemStack stack, String key, int def) {
        CompoundTag tag = getCustomDataTag(stack);
        return tag.contains(key) ? tag.getInt(key) : def;
    }

    private static void setInt(ItemStack stack, String key, int value) {
        CompoundTag tag = getCustomDataTag(stack);
        int prev = tag.contains(key) ? tag.getInt(key) : Integer.MIN_VALUE;
        if (prev == value) return;
        tag.putInt(key, value);
        setCustomDataTag(stack, tag);
    }

    private static long getLong(ItemStack stack, String key, long def) {
        CompoundTag tag = getCustomDataTag(stack);
        return tag.contains(key) ? tag.getLong(key) : def;
    }

    private static void setLong(ItemStack stack, String key, long value) {
        CompoundTag tag = getCustomDataTag(stack);
        long prev = tag.contains(key) ? tag.getLong(key) : Long.MIN_VALUE;
        if (prev == value) return;
        tag.putLong(key, value);
        setCustomDataTag(stack, tag);
    }
}
