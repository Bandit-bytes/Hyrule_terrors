package net.bandit.hyrule_terrors.item.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class KeeseEyeball extends Item {

    public KeeseEyeball(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            return equipToHead(level, player, stack);
        }

        InteractionResultHolder<ItemStack> placed = super.use(level, player, hand);
        if (placed.getResult().consumesAction()) {
            return placed;
        }

        return equipToHead(level, player, stack);
    }

    private InteractionResultHolder<ItemStack> equipToHead(Level level, Player player, ItemStack stack) {
        ItemStack inHead = player.getItemBySlot(EquipmentSlot.HEAD);

        if (!inHead.isEmpty()) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide) {
            ItemStack copy = stack.copy();
            copy.setCount(1);
            player.setItemSlot(EquipmentSlot.HEAD, copy);
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
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
                Component.translatable("item.keese_eyeball.tooltip")
                    .withStyle(ChatFormatting.ITALIC)
                    .withStyle(ChatFormatting.LIGHT_PURPLE)
            );
            tooltipComponents.add(Component.translatable("item.keese_eyeball.tooltip1").withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }
}
