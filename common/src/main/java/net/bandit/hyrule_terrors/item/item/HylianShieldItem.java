package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HylianShieldItem extends ShieldItem {

    private static final int STORM_COOLDOWN_TICKS = 20 * 18;

    private static final int STORM_CHECK_INTERVAL = 10;

    private static final float LIGHTNING_CHANCE = 0.12f;

    private static final double BURST_RADIUS = 7.0D;

    private static final float BURST_DAMAGE = 4.0F;

    private static final int BURST_SLOW_TICKS = 20 * 2;

    private static final int BURST_SLOW_AMPLIFIER = 2;

    /**
     * Per-player storm cooldown, tracked as the game time when the shield can next call lightning. Kept off the
     * ItemStack on purpose: rewriting stack data every tick resyncs the item constantly and makes it bob in hand.
     */
    private static final Map<UUID, Long> NEXT_STORM_TIME = new HashMap<>();

    public HylianShieldItem(Properties props) {
        super(props);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (!(level instanceof ServerLevel serverLevel) || !(entity instanceof Player player))
            return;

        if (isHoldingThisExactStack(player, stack) && player.isOnFire()) {
            player.clearFire();
        }

        if (!isBlockingWithThisExactStack(player, stack))
            return;
        long now = level.getGameTime();
        if ((now % STORM_CHECK_INTERVAL) != 0)
            return;
        if (!level.isThundering())
            return;
        if (now < NEXT_STORM_TIME.getOrDefault(player.getUUID(), 0L))
            return;

        BlockPos pos = player.blockPosition();
        if (!level.canSeeSky(pos))
            return;
        if (level.getBrightness(LightLayer.BLOCK, pos) > 13)
            return;
        if (player.getRandom().nextFloat() > LIGHTNING_CHANCE)
            return;

        NEXT_STORM_TIME.put(player.getUUID(), now + STORM_COOLDOWN_TICKS);
        pullLightningAndBurst(serverLevel, player, stack);
    }

    private static boolean isHoldingThisExactStack(Player player, ItemStack stack) {
        return player.getMainHandItem() == stack || player.getOffhandItem() == stack;
    }

    private static boolean isBlockingWithThisExactStack(Player player, ItemStack stack) {
        return player.isUsingItem() && player.getUseItem() == stack;
    }

    private static void pullLightningAndBurst(ServerLevel level, Player player, ItemStack stack) {
        var bolt = EntityType.LIGHTNING_BOLT.create(level);
        if (bolt != null) {
            bolt.moveTo(player.getX(), player.getY(), player.getZ());
            // Mark the bolt as caused by the player so it doesn't count as a natural strike, and the shield
            // bearer is protected below.
            if (player instanceof ServerPlayer serverPlayer) {
                bolt.setCause(serverPlayer);
            }
            level.addFreshEntity(bolt);
        }

        AABB box = player.getBoundingBox().inflate(BURST_RADIUS);
        List<LivingEntity> targets = level.getEntitiesOfClass(
            LivingEntity.class,
            box,
            e -> e != player && e.isAlive() && e instanceof Enemy
        );

        for (LivingEntity target : targets) {
            target.hurt(level.damageSources().indirectMagic(player, player), BURST_DAMAGE);
            target.addEffect(
                new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, BURST_SLOW_TICKS, BURST_SLOW_AMPLIFIER, false, true)
            );
        }

        if (!targets.isEmpty()) {
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(player.getUsedItemHand()));
        }
    }

    /** The bearer of a raised Hylian Shield is not harmed by the lightning it calls down. */
    public static boolean shouldNegateLightning(LivingEntity entity) {
        return entity.isUsingItem() && entity.getUseItem().getItem() instanceof HylianShieldItem;
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
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip")
                    .withStyle(ChatFormatting.GOLD)
            );
            tooltipComponents.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_royal")
                    .withStyle(ChatFormatting.DARK_GREEN)
            );
            tooltipComponents.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_durability")
                    .withStyle(ChatFormatting.GRAY)
            );
            tooltipComponents.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_fire")
                    .withStyle(ChatFormatting.RED)
            );
            tooltipComponents.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_lightning")
                    .withStyle(ChatFormatting.AQUA)
            );
        } else {
            tooltipComponents.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hold_shift")
                    .withStyle(ChatFormatting.DARK_GRAY)
            );
            tooltipComponents.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hylian_shield.tooltip_1")
                    .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)
            );
        }
    }
}
