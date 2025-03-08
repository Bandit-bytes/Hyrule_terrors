package net.bandit.hyrule_terrors.registry;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.sets.ZoraArmorItem;
import net.minecraft.world.item.ArmorItem;

public class ArmorItemRegistry {
    public static void init() {
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "zora_helmet", () -> new ZoraArmorItem(ArmorItem.Type.HELMET));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "zora_chestplate", () -> new ZoraArmorItem(ArmorItem.Type.CHESTPLATE));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "zora_leggings", () -> new ZoraArmorItem(ArmorItem.Type.LEGGINGS));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "zora_boots", () -> new ZoraArmorItem(ArmorItem.Type.BOOTS));
    }
}
