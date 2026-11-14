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
        Supplier<Ingredient> repairIngredient
    ) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modId, armorId);
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(id));

        EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            defense.put(type, typeProtection.getOrDefault(type, 0));
        }

        // Registered under our own namespace (previously these landed in "minecraft:", which could collide with
        // vanilla or other mods using the same names).
        return Registry.registerForHolder(
            BuiltInRegistries.ARMOR_MATERIAL,
            id,
            new ArmorMaterial(
                defense,
                enchantability,
                equipSound,
                repairIngredient,
                layers,
                toughness,
                knockbackResistance
            )
        );
    }
}
