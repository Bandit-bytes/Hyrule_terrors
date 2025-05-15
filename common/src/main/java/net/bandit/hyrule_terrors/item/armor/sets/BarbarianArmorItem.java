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

public class BarbarianArmorItem extends ArmorItem {

    public final ZeldaArmorDispatcher DISPATCHER;

    public BarbarianArmorItem(Type type, Properties properties) {
        super(
            ZeldaArmorMaterials.BARBARAIN_ARMOR,
            type,
            properties
        );
        this.DISPATCHER = new ZeldaArmorDispatcher();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player) {
            if (hasFullSet(player)) {
                {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 220, 0, false, false, true));
                }
            }
        }
    }

    private boolean hasFullSet(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof BarbarianArmorItem &&
            entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof BarbarianArmorItem &&
            entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof BarbarianArmorItem &&
            entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof BarbarianArmorItem;
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(
            Component.translatable("item.hyrule_terrors.barbarian_armor.tooltip").withStyle(ChatFormatting.ITALIC)
        );
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemRegistry.BOKOBLIN_FANG);
    }
}
