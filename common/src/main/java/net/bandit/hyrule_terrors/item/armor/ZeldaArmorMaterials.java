package net.bandit.hyrule_terrors.item.armor;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ZeldaArmorMaterials implements ArmorMaterial {

    // Defense order: BOOTS, LEGGINGS, CHESTPLATE, HELMET
    ZORA_ARMOR(
        "zora_armor",
        33,
        new int[] { 2, 5, 6, 2 },
        12,
        SoundEvents.ARMOR_EQUIP_IRON,
        1.0F,
        0.2F,
        () -> Ingredient.of(Items.IRON_INGOT)
    ),

    HERO_ARMOR(
        "hero_armor",
        33,
        new int[] { 2, 5, 6, 2 },
        15,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        1.0F,
        0.2F,
        () -> Ingredient.of(Items.LEATHER)
    ),

    EVIL_SPIRIT(
        "evil_spirit",
        37,
        new int[] { 4, 7, 9, 4 },
        15,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        2.0F,
        0.5F,
        () -> Ingredient.of(Items.NETHERITE_INGOT)
    ),

    KNIGHT_ARMOR(
        "knight_armor",
        33,
        new int[] { 3, 6, 8, 3 },
        10,
        SoundEvents.ARMOR_EQUIP_DIAMOND,
        1.5F,
        0.4F,
        () -> Ingredient.of(Items.DIAMOND)
    ),

    PHANTOM_ARMOR(
        "phantom_armor",
        33,
        new int[] { 3, 7, 8, 3 },
        12,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        2.0F,
        0.4F,
        () -> Ingredient.of(Items.DIAMOND)
    ),

    BARBARIAN_ARMOR(
        "barbarian_armor",
        33,
        new int[] { 1, 4, 6, 4 },
        12,
        SoundEvents.ARMOR_EQUIP_LEATHER,
        3.5F,
        0.6F,
        () -> Ingredient.of(Items.DIAMOND)
    );

    /** Vanilla base durability per slot (BOOTS, LEGGINGS, CHESTPLATE, HELMET). */
    private static final int[] HEALTH_PER_SLOT = new int[] { 13, 15, 16, 11 };

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

    /** Same formula as 1.21's {@code ArmorItem.Type#getDurability(int)}. */
    public static int durabilityFor(ArmorItem.Type type, int multiplier) {
        return HEALTH_PER_SLOT[index(type)] * multiplier;
    }

    private static int index(ArmorItem.Type type) {
        return switch (type) {
            case BOOTS -> 0;
            case LEGGINGS -> 1;
            case CHESTPLATE -> 2;
            case HELMET -> 3;
        };
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return durabilityFor(type, durabilityMultiplier);
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return protection[index(type)];
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

    /** Also the armor texture prefix: hyrule_terrors:textures/models/armor/&lt;name&gt;_layer_1.png */
    @Override
    public String getName() {
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
