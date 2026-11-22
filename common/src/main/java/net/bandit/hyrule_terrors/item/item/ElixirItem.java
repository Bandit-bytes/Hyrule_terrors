package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

/**
 * Breath of the Wild style elixir: brewed from monster parts and Chuchu jelly, drunk like a potion and returns the
 * bottle.
 */
public class ElixirItem extends Item {

    private final Supplier<List<MobEffectInstance>> effects;

    public ElixirItem(Properties properties, Supplier<List<MobEffectInstance>> effects) {
        super(properties.stacksTo(16));
        this.effects = effects;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            for (MobEffectInstance effect : effects.get()) {
                if (effect.getEffect().isInstantenous()) {
                    effect.getEffect().applyInstantenousEffect(null, null, entity, effect.getAmplifier(), 1.0D);
                } else {
                    entity.addEffect(new MobEffectInstance(effect));
                }
            }
        }

        if (entity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (entity instanceof Player player && player.getAbilities().instabuild) {
            return stack;
        }

        stack.shrink(1);
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        if (entity instanceof Player player && !player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
            player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable net.minecraft.world.level.Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(
            Component.translatable(getDescriptionId() + ".tooltip")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC)
        );
        if (TooltipHelper.hasShiftDown()) {
            for (MobEffectInstance effect : effects.get()) {
                Component name = Component.translatable(effect.getDescriptionId());
                if (effect.getAmplifier() > 0) {
                    name = Component.translatable(
                        "potion.withAmplifier",
                        name,
                        Component.translatable("potion.potency." + effect.getAmplifier())
                    );
                }
                if (!effect.getEffect().isInstantenous()) {
                    name = Component.translatable("potion.withDuration", name, formatDuration(effect.getDuration()));
                }
                tooltip.add(Component.literal(" ").append(name).withStyle(ChatFormatting.BLUE));
            }
        } else {
            tooltip.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }

    private static String formatDuration(int ticks) {
        int seconds = ticks / 20;
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
