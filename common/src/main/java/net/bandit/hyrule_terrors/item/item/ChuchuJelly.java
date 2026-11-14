package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ChuchuJelly extends Item {

    public ChuchuJelly(Properties properties) {
        super(
            properties.food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.2f).alwaysEdible().build())
        );
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        Item.TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (TooltipHelper.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.chuchu_jelly.tooltip").withStyle(ChatFormatting.BLUE));
            tooltipComponents.add(Component.translatable("item.chuchu_jelly.tooltip1").withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        // Roll the effect first, then let vanilla handle eating (hunger, saturation, stack shrink). The old code
        // skipped
        // super entirely, so the jelly never actually fed you.
        if (!level.isClientSide && entity instanceof Player player) {
            RandomSource random = level.getRandom();

            int roll = random.nextInt(6); // 0 to 5
            switch (roll) {
                case 0 -> player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
                case 1 -> player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0));
                case 2 -> player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300, 0));
                case 3 -> player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
                case 4 -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
                case 5 -> {
                    level.explode(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        0.5F,
                        Level.ExplosionInteraction.NONE
                    );
                }
            }

            level.playSound(null, player.blockPosition(), SoundEvents.SLIME_SQUISH, SoundSource.PLAYERS, 0.8F, 1.2F);

        }

        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
