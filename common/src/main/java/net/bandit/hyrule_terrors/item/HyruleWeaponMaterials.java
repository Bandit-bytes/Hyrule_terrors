package net.bandit.hyrule_terrors.item;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class HyruleWeaponMaterials implements Tier {

    public static final Tier BOKOBLIN_TIER = new HyruleWeaponMaterials(
        101,
        1.0f,
        3.0f,
        3,
        1,
        () -> Ingredient.of(Items.BONE)
    );

    /** Spiked Moblin Club: iron-grade, very enchantable, mended with planks. */
    public static final Tier MOBLIN_CLUB_TIER = new HyruleWeaponMaterials(
        450,
        2.0f,
        5.0f,
        12,
        2,
        () -> Ingredient.of(ItemTags.PLANKS)
    );

    int uses;

    float damage;

    float speed;

    int enchantmentValue;

    int level;

    Supplier<Ingredient> repairIngredient;

    public HyruleWeaponMaterials(
        int uses,
        float damage,
        float speed,
        int enchantmentValue,
        int level,
        Supplier<Ingredient> repairIngredient
    ) {
        this.uses = uses;
        this.damage = damage;
        this.speed = speed;
        this.enchantmentValue = enchantmentValue;
        this.level = level;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}
