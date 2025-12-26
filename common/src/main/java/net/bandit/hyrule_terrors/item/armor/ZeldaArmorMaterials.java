package net.bandit.hyrule_terrors.item.armor;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ZeldaArmorMaterials implements ArmorMaterial {

    ZORA(
            "zora_armor",
            25,
            new int[]{2, 5, 6, 2}, // FEET, LEGS, CHEST, HEAD
            9,
            SoundEvents.ARMOR_EQUIP_IRON,
            1.0F,
            0.2F,
            () -> Ingredient.of(Items.IRON_INGOT)
    ),

    EVIL_SPIRIT(
            "evil_spirit",
            25,
            new int[]{4, 7, 9, 4},
            9,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            2.0F,
            0.5F,
            () -> Ingredient.of(Items.NETHERITE_INGOT)
    ),

    KNIGHT(
            "knight_armor",
            25,
            new int[]{3, 6, 8, 3},
            9,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            1.5F,
            0.4F,
            () -> Ingredient.of(Items.DIAMOND)
    ),

    BARBARIAN(
            "barbarian_armor",
            25,
            new int[]{1, 4, 6, 4},
            9,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            3.5F,
            0.6F,
            () -> Ingredient.of(Items.DIAMOND)
    );

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11}; // FEET, LEGS, CHEST, HEAD

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protection;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ZeldaArmorMaterials(
            String name,
            int durabilityMultiplier,
            int[] protection,
            int enchantmentValue,
            SoundEvent equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protection = protection;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return 0;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return 0;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    @Override
    public String getName() {
        // 1.20.1 expects a string like "modid:material_name"
        return HyruleTerrorsMod.MOD_ID + ":" + name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
