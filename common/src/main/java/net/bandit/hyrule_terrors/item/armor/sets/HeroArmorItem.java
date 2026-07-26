package net.bandit.hyrule_terrors.item.armor.sets;

import net.bandit.hyrule_terrors.item.armor.ZeldaArmorMaterials;
import net.bandit.hyrule_terrors.item.armor.client.dispatcher.ZeldaArmorDispatcher;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class HeroArmorItem extends ArmorItem {

    public final ZeldaArmorDispatcher DISPATCHER;

    public HeroArmorItem(Type type, Properties properties) {
        super(ZeldaArmorMaterials.HERO_ARMOR, type, properties);
        this.DISPATCHER = new ZeldaArmorDispatcher();
    }

    @Override
    public void inventoryTick(
        ItemStack stack,
        Level level,
        Entity entity,
        int slotId,
        boolean isSelected
    ) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (level.isClientSide() || !(entity instanceof Player player)) {
            return;
        }
        if (getType() != Type.HELMET) {
            return;
        }

        if (player.tickCount % 20 != 0) {
            return;
        }

        if (hasFullSet(player) && isAtFullHealth(player)) {
            player.addEffect(
                new MobEffectInstance(
                    MobEffects.DAMAGE_BOOST,
                    40,
                    0,
                    true,
                    false,
                    true
                )
            );
        }
    }

    private boolean hasFullSet(LivingEntity entity) {
        return isHeroArmor(entity.getItemBySlot(EquipmentSlot.HEAD))
            && isHeroArmor(entity.getItemBySlot(EquipmentSlot.CHEST))
            && isHeroArmor(entity.getItemBySlot(EquipmentSlot.LEGS))
            && isHeroArmor(entity.getItemBySlot(EquipmentSlot.FEET));
    }

    private boolean isHeroArmor(ItemStack stack) {
        return stack.getItem() instanceof HeroArmorItem;
    }

    private boolean isAtFullHealth(LivingEntity entity) {
        return entity.getHealth() >= entity.getMaxHealth();
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(
            Component.translatable(
                getDescriptionId() + ".tooltip"
            ).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC)
        );

        tooltipComponents.add(Component.empty());

        tooltipComponents.add(
            Component.translatable(
                "item.hyrule_terrors.hero_armor.set_bonus"
            ).withStyle(ChatFormatting.GOLD)
        );

        tooltipComponents.add(
            Component.translatable(
                "item.hyrule_terrors.hero_armor.set_bonus.description"
            ).withStyle(ChatFormatting.AQUA)
        );
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemRegistry.CHUCHU_JELLY.get());
    }
}
