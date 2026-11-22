package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class LizalfosTail extends Item {

    private static final FoodProperties LIZALFOS_TAIL_FOOD = new FoodProperties.Builder()
        .nutrition(2)
        .saturationMod(0.3f)
        .alwaysEat()
        .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 30, 0), 1.0F)
        .build();

    public LizalfosTail(Properties properties) {
        super(properties.food(LIZALFOS_TAIL_FOOD));
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
                Component.translatable("item.lizalfos_tail.tooltip").withStyle(ChatFormatting.DARK_GREEN)
            );
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
            tooltipComponents.add(
                Component.translatable("item.lizalfos_tail.tooltip_1")
                    .withStyle(ChatFormatting.ITALIC)
                    .withStyle(ChatFormatting.GRAY)
            );
        }
    }
}
