package net.bandit.hyrule_terrors.item.armor;


import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;


public class ZeldaArmorMaterials {
    public static final Holder<ArmorMaterial> ZORA_ARMOR = ZeldaArmorMaterialsRegister.registerArmorMaterial(HyruleTerrorsMod.MOD_ID, "zora_armor", Util.make(new EnumMap(ArmorItem.Type.class), enumMap -> {
        enumMap.put(ArmorItem.Type.BOOTS, 2);
        enumMap.put(ArmorItem.Type.LEGGINGS, 5);
        enumMap.put(ArmorItem.Type.CHESTPLATE, 6);
        enumMap.put(ArmorItem.Type.HELMET, 2);
        enumMap.put(ArmorItem.Type.BODY, 5);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 1.0F, () -> Ingredient.of(Items.IRON_INGOT));
}

