package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.registry.TabRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class LizalfosTail extends Item {

    private static final FoodProperties LIZALFOS_TAIL_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3f)
            .alwaysEdible()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 30, 0), 1.0F)
            .build();

    public LizalfosTail(Properties properties) {
        super(new Properties().food(LIZALFOS_TAIL_FOOD).arch$tab(TabRegistry.HYRULE_TERRORS_TAB));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.lizalfos_tail.tooltip").withStyle(ChatFormatting.DARK_GREEN));
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
            tooltipComponents.add(Component.translatable("item.lizalfos_tail.tooltip_1").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 30, 0));
        }
        return super.finishUsingItem(stack, world, entity);
    }
}
