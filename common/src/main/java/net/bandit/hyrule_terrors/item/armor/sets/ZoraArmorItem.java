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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class ZoraArmorItem extends ArmorItem {

    public final ZeldaArmorDispatcher DISPATCHER;

    public ZoraArmorItem(Type type, Item.Properties properties) {
        super(ZeldaArmorMaterials.ZORA_ARMOR, type, properties);
        this.DISPATCHER = new ZeldaArmorDispatcher();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player) {
            if (hasFullSet(player)) {
                if (player.isInWater()) {
                    player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 220, 0, false, false, true));
                    player.setDeltaMovement(player.getDeltaMovement().multiply(1.2, 1.0, 1.2));
                }
            }
        }
    }

    private boolean hasFullSet(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ZoraArmorItem &&
            entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ZoraArmorItem &&
            entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ZoraArmorItem &&
            entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof ZoraArmorItem;
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        tooltipComponents.add(
            Component.translatable("item.hyrule_terrors.zora_armor.tooltip")
                .withStyle(ChatFormatting.AQUA)
                .withStyle(ChatFormatting.ITALIC)
        );
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemRegistry.CHUCHU_JELLY.get());
    }
}
