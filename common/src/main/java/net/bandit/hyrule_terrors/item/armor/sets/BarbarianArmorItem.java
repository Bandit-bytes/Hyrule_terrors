package net.bandit.hyrule_terrors.item.armor.sets;

import net.bandit.hyrule_terrors.item.armor.ZeldaArmorMaterials;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BarbarianArmorItem extends ZeldaSetArmorItem {

    public BarbarianArmorItem(Type type, Properties properties) {
        super(ZeldaArmorMaterials.BARBARIAN_ARMOR, type, properties, "barbarian_armor");
    }

    @Override
    protected void applySetBonus(Player player) {
        refresh(player, MobEffects.DAMAGE_BOOST, 0);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemRegistry.BOKOBLIN_FANG.get()) || repair.is(ItemRegistry.BOKOBLIN_HORN.get());
    }
}
