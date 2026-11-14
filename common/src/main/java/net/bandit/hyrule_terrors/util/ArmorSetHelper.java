package net.bandit.hyrule_terrors.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public final class ArmorSetHelper {

    /** How often (in ticks) set bonuses refresh. Effects are applied with a longer duration so they never flicker. */
    public static final int REFRESH_INTERVAL = 20;

    private ArmorSetHelper() {}

    public static boolean hasFullSet(LivingEntity entity, Class<? extends ArmorItem> setClass) {
        return setClass.isInstance(entity.getItemBySlot(EquipmentSlot.HEAD).getItem())
            && setClass.isInstance(entity.getItemBySlot(EquipmentSlot.CHEST).getItem())
            && setClass.isInstance(entity.getItemBySlot(EquipmentSlot.LEGS).getItem())
            && setClass.isInstance(entity.getItemBySlot(EquipmentSlot.FEET).getItem());
    }

    /**
     * Set bonuses are driven from {@code inventoryTick}, which fires for every piece in every inventory slot. Only the
     * chestplate that is actually being worn should run the bonus, and only once per refresh interval.
     */
    public static boolean shouldTickSetBonus(ItemStack stack, Player player) {
        return !player.level().isClientSide()
            && player.tickCount % REFRESH_INTERVAL == 0
            && player.getItemBySlot(EquipmentSlot.CHEST) == stack;
    }
}
