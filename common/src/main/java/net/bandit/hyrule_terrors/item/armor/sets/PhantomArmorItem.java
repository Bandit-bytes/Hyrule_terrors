package net.bandit.hyrule_terrors.item.armor.sets;

import net.bandit.hyrule_terrors.item.armor.ZeldaArmorMaterials;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PhantomArmorItem extends ZeldaSetArmorItem {

    public PhantomArmorItem(Type type, Properties properties) {
        super(ZeldaArmorMaterials.PHANTOM_ARMOR, type, properties, "phantom_armor");
    }

    @Override
    protected void applySetBonus(Player player) {
        refresh(player, MobEffects.DAMAGE_BOOST, 0);
        refresh(player, MobEffects.DAMAGE_RESISTANCE, 0);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemRegistry.STEEL.get());
    }
}
