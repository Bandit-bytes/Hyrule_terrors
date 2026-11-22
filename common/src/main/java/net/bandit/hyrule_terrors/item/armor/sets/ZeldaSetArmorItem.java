package net.bandit.hyrule_terrors.item.armor.sets;

import net.bandit.hyrule_terrors.util.ArmorSetHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * Shared behaviour for every Hyrule armor set: a full-set bonus that refreshes once a second while worn, and a
 * consistent tooltip layout.
 */
public abstract class ZeldaSetArmorItem extends ArmorItem {

    /** Long enough to outlast the refresh interval, and above 200 ticks so Night Vision never flickers. */
    protected static final int EFFECT_DURATION = 20 * 12;

    private final String setKey;

    protected ZeldaSetArmorItem(ArmorMaterial material, Type type, Properties properties, String setKey) {
        super(material, type, properties);
        this.setKey = setKey;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (
            entity instanceof Player player
                && ArmorSetHelper.shouldTickSetBonus(stack, player)
                && ArmorSetHelper.hasFullSet(player, getClass())
        ) {
            applySetBonus(player);
        }
    }

    protected abstract void applySetBonus(Player player);

    protected static void refresh(Player player, MobEffect effect, int amplifier) {
        player.addEffect(new MobEffectInstance(effect, EFFECT_DURATION, amplifier, true, false, true));
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        @org.jetbrains.annotations.Nullable net.minecraft.world.level.Level level,
        List<Component> tooltip,
        TooltipFlag flag
    ) {
        tooltip.add(
            Component.translatable(getDescriptionId() + ".tooltip")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC)
        );
        tooltip.add(Component.empty());
        tooltip.add(
            Component.translatable("item.hyrule_terrors." + setKey + ".set_bonus").withStyle(ChatFormatting.GOLD)
        );
        tooltip.add(
            Component.translatable("item.hyrule_terrors." + setKey + ".set_bonus.description")
                .withStyle(ChatFormatting.AQUA)
        );
    }
}
