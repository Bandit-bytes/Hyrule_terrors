package net.bandit.hyrule_terrors.item.armor;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ZeldaArmorMaterialsRegister {

    public static Holder<ArmorMaterial> registerArmorMaterial(
        String modId,
        String armorId,
        EnumMap<ArmorItem.Type, Integer> typeProtection,
        int enchantability,
        Holder<SoundEvent> equipSound,
        float toughness,
        float knockbackResistance,
        Supplier<Ingredient> ingredientItem
    ) {
        List<ArmorMaterial.Layer> list = List.of(
            new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(modId, armorId))
        );
        return register(
            armorId,
            typeProtection,
            enchantability,
            equipSound,
            toughness,
            knockbackResistance,
            ingredientItem,
            list
        );
    }

    private static Holder<ArmorMaterial> register(
        String string,
        EnumMap<ArmorItem.Type, Integer> enumMap,
        int i,
        Holder<SoundEvent> holder,
        float f,
        float g,
        Supplier<Ingredient> supplier,
        List<ArmorMaterial.Layer> list
    ) {
        EnumMap<ArmorItem.Type, Integer> enumMap2 = new EnumMap(ArmorItem.Type.class);

        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            enumMap2.put(type, (Integer) enumMap.get(type));
        }

        return Registry.registerForHolder(
            BuiltInRegistries.ARMOR_MATERIAL,
            ResourceLocation.withDefaultNamespace(string),
            new ArmorMaterial(enumMap2, i, holder, supplier, list, f, g)
        );
    }
}
