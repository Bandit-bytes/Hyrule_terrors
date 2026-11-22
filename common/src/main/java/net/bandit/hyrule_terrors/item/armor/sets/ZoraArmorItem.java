package net.bandit.hyrule_terrors.item.armor.sets;

import net.bandit.hyrule_terrors.item.armor.ZeldaArmorMaterials;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ZoraArmorItem extends ZeldaSetArmorItem {

    public ZoraArmorItem(Type type, Properties properties) {
        super(ZeldaArmorMaterials.ZORA_ARMOR, type, properties, "zora_armor");
    }

    @Override
    protected void applySetBonus(Player player) {
        // Player movement is client-authoritative, so speed has to come from an effect rather than server-side
        // velocity changes (the old approach compounded 1.2x per armor piece per tick and rubber-banded).
        refresh(player, MobEffects.WATER_BREATHING, 0);
        if (player.isInWaterOrBubble()) {
            refresh(player, MobEffects.DOLPHINS_GRACE, 0);
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(ItemRegistry.CHUCHU_JELLY.get());
    }
}
