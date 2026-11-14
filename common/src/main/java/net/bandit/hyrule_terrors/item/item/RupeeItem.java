package net.bandit.hyrule_terrors.item.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/** Hyrule's currency. Wandering Traders accept them, and they convert up or down at a crafting table. */
public class RupeeItem extends Item {

    private final int value;

    public RupeeItem(Properties properties, int value) {
        super(properties);
        this.value = value;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.hyrule_terrors.rupee.value", value).withStyle(ChatFormatting.GREEN));
    }
}
