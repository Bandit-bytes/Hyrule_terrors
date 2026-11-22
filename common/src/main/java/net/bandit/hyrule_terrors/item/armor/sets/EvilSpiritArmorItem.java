package net.bandit.hyrule_terrors.item.armor.sets;

import net.bandit.hyrule_terrors.item.armor.ZeldaArmorMaterials;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EvilSpiritArmorItem extends ZeldaSetArmorItem {

    public EvilSpiritArmorItem(Type type, Properties properties) {
        super(ZeldaArmorMaterials.EVIL_SPIRIT, type, properties, "evil_spirit");
    }

    @Override
    protected void applySetBonus(Player player) {
        refresh(player, MobEffects.DAMAGE_BOOST, 1);
        refresh(player, MobEffects.NIGHT_VISION, 0);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemRegistry.STEEL.get()) || repair.is(ItemRegistry.CHUCHU_JELLY_RED.get());
    }
}
