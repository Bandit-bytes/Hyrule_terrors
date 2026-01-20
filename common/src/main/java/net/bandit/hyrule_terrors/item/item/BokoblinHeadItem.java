package net.bandit.hyrule_terrors.item.item;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BokoblinHeadItem extends StandingAndWallBlockItem {

    public BokoblinHeadItem(Block standing, Block wall, Properties props) {
        super(standing, wall, props, Direction.DOWN);
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
}
