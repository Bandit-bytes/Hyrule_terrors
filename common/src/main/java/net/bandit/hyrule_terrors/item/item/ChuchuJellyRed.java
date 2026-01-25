package net.bandit.hyrule_terrors.item.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ChuchuJellyRed extends Item {

    public ChuchuJellyRed(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        Item.TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.chuchu_jelly_red.tooltip")
                    .withStyle(ChatFormatting.RED)
            );
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.chuchu_jelly_red.tooltip1")
                    .withStyle(ChatFormatting.GRAY)
            );
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }
}
