package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * The Paraglider. Hold use while airborne to glide: descent is capped to a slow drift, you glide toward where you look,
 * and fall damage is cancelled. Runs on both sides because player movement is client-authoritative.
 */
public class ParagliderItem extends Item {

    private static final double MAX_FALL_SPEED = -0.085D;

    private static final double GLIDE_ACCEL = 0.045D;

    private static final double MAX_HORIZONTAL_SPEED = 0.55D;

    public ParagliderItem(Properties properties) {
        super(properties);
    }

    public static boolean canGlide(LivingEntity entity) {
        if (
            entity.onGround() || entity.isInWater() || entity.isInLava() || entity.isPassenger() || entity
                .isFallFlying()
        ) {
            return false;
        }
        return !(entity instanceof Player player && player.getAbilities().flying);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!canGlide(player)) {
            return InteractionResultHolder.pass(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (!canGlide(entity)) {
            entity.stopUsingItem();
            return;
        }

        Vec3 motion = entity.getDeltaMovement();
        Vec3 look = entity.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0D, look.z);
        if (flat.lengthSqr() > 1.0E-4D) {
            flat = flat.normalize();
        }

        double vx = motion.x + flat.x * GLIDE_ACCEL;
        double vz = motion.z + flat.z * GLIDE_ACCEL;
        double horizontal = Math.sqrt(vx * vx + vz * vz);
        if (horizontal > MAX_HORIZONTAL_SPEED) {
            vx *= MAX_HORIZONTAL_SPEED / horizontal;
            vz *= MAX_HORIZONTAL_SPEED / horizontal;
        }

        entity.setDeltaMovement(vx, Math.max(motion.y, MAX_FALL_SPEED), vz);
        entity.resetFallDistance();

        if (!level.isClientSide && entity.tickCount % 40 == 0) {
            stack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(
            Component.translatable("item.hyrule_terrors.paraglider.tooltip")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC)
        );
        if (TooltipHelper.hasShiftDown()) {
            tooltip.add(
                Component.translatable("item.hyrule_terrors.paraglider.tooltip1").withStyle(ChatFormatting.AQUA)
            );
        } else {
            tooltip.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }
}
