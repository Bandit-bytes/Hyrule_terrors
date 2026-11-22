package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class LizalfosTalon extends Item {

    public LizalfosTalon(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        @org.jetbrains.annotations.Nullable net.minecraft.world.level.Level level,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (TooltipHelper.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.lizalfos_talon.tooltip"));
            tooltipComponents.add(
                Component.translatable("item.lizalfos_talon.tooltip1").withStyle(ChatFormatting.GRAY)
            );
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }
}
