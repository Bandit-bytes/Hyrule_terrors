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

    public static final Holder<ArmorMaterial> ZORA_ARMOR = ZeldaArmorMaterialsRegister.registerArmorMaterial(
        HyruleTerrorsMod.MOD_ID,
        "zora_armor",
        Util.make(new EnumMap(ArmorItem.Type.class), enumMap -> {
            enumMap.put(ArmorItem.Type.BOOTS, 2);
            enumMap.put(ArmorItem.Type.LEGGINGS, 5);
            enumMap.put(ArmorItem.Type.CHESTPLATE, 6);
            enumMap.put(ArmorItem.Type.HELMET, 2);
            enumMap.put(ArmorItem.Type.BODY, 5);
        }),
        9,
        SoundEvents.ARMOR_EQUIP_IRON,
        1.0F,
        1.0F,
        () -> Ingredient.of(Items.IRON_INGOT)
    );

    public static final Holder<ArmorMaterial> EVIL_SPIRIT = ZeldaArmorMaterialsRegister.registerArmorMaterial(
        HyruleTerrorsMod.MOD_ID,
        "evil_spirit",
        Util.make(new EnumMap(ArmorItem.Type.class), enumMap -> {
            enumMap.put(ArmorItem.Type.BOOTS, 4);
            enumMap.put(ArmorItem.Type.LEGGINGS, 7);
            enumMap.put(ArmorItem.Type.CHESTPLATE, 9);
            enumMap.put(ArmorItem.Type.HELMET, 4);
            enumMap.put(ArmorItem.Type.BODY, 7);
        }),
        9,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        2.0F,
        1.5F,
        () -> Ingredient.of(Items.NETHERITE_INGOT)
    );

    public static final Holder<ArmorMaterial> KNIGHT_ARMOR = ZeldaArmorMaterialsRegister.registerArmorMaterial(
        HyruleTerrorsMod.MOD_ID,
        "knight_armor",
        Util.make(new EnumMap(ArmorItem.Type.class), enumMap -> {
            enumMap.put(ArmorItem.Type.BOOTS, 3);
            enumMap.put(ArmorItem.Type.LEGGINGS, 6);
            enumMap.put(ArmorItem.Type.CHESTPLATE, 8);
            enumMap.put(ArmorItem.Type.HELMET, 3);
            enumMap.put(ArmorItem.Type.BODY, 6);
        }),
        9,
        SoundEvents.ARMOR_EQUIP_DIAMOND,
        1.5F,
        1.5F,
        () -> Ingredient.of(Items.DIAMOND)
    );

    public static final Holder<ArmorMaterial> BARBARAIN_ARMOR = ZeldaArmorMaterialsRegister.registerArmorMaterial(
        HyruleTerrorsMod.MOD_ID,
        "barbarian_armor",
        Util.make(new EnumMap(ArmorItem.Type.class), enumMap -> {
            enumMap.put(ArmorItem.Type.BOOTS, 1);
            enumMap.put(ArmorItem.Type.LEGGINGS, 4);
            enumMap.put(ArmorItem.Type.CHESTPLATE, 6);
            enumMap.put(ArmorItem.Type.HELMET, 4);
            enumMap.put(ArmorItem.Type.BODY, 6);
        }),
        9,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        3.5F,
        3.5F,
        () -> Ingredient.of(Items.DIAMOND)
    );
}
