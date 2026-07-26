package net.bandit.hyrule_terrors.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.HyruleWeaponMaterials;
import net.bandit.hyrule_terrors.item.armor.sets.*;
import net.bandit.hyrule_terrors.item.item.*;
import net.bandit.hyrule_terrors.item.weapon.weapons.BokoblinArm;
import net.bandit.hyrule_terrors.item.weapon.weapons.BoulderBreaker;
import net.bandit.hyrule_terrors.item.weapon.weapons.KnightsClaymore;
import net.bandit.hyrule_terrors.item.weapon.weapons.SilverLongsword;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            HyruleTerrorsMod.MOD_ID,
            Registries.ITEM
    );

    private static final int DIAMOND_DURABILITY = 33;
    private static final int NETHERITE_DURABILITY = 37;

    private static Item.Properties armorProps(ArmorItem.Type type, int multiplier, Rarity rarity) {
        return new Item.Properties()
                .durability(type.getDurability(multiplier))
                .rarity(rarity)
                .arch$tab(TabRegistry.HYRULE_TERRORS_TAB);
    }

    // Eggies
    public static final RegistrySupplier<Item> BOKOBLIN_SPAWN_EGG = ITEMS.register(
            "bokoblin_spawn_egg",
            () -> new ArchitecturySpawnEggItem(
                    EntityRegistry.BOKOBLIN,
                    0xCF6E11,
                    0xA85807,
                    new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    ); // Orange

    public static final RegistrySupplier<Item> LIZALFOS_SPAWN_EGG = ITEMS.register(
            "lizalfos_spawn_egg",
            () -> new ArchitecturySpawnEggItem(
                    EntityRegistry.LIZALFOS,
                    0x7CA81F,
                    0xF2C849,
                    new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    ); // Green/Yellow

    public static final RegistrySupplier<Item> CHUCHU_SPAWN_EGG = ITEMS.register(
            "chuchu_spawn_egg",
            () -> new ArchitecturySpawnEggItem(
                    EntityRegistry.CHUCHU,
                    0x3A89CF,
                    0x9DD3F7,
                    new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    ); // Blue/Cyan/Light Blue

    public static final RegistrySupplier<Item> RED_CHUCHU_SPAWN_EGG = ITEMS.register(
            "red_chuchu_spawn_egg",
            () -> new ArchitecturySpawnEggItem(
                    EntityRegistry.CHUCHU_RED,
                    0xB71C1C,
                    0xFF6D00,
                    new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    ); // red/orange

    public static final RegistrySupplier<Item> KEESE_SPAWN_EGG = ITEMS.register(
            "keese_spawn_egg",
            () -> new ArchitecturySpawnEggItem(
                    EntityRegistry.KEESE,
                    0x3A3A3A,
                    0x888888,
                    new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    ); // Black/Gray
    // Items

    public static final RegistrySupplier<Item> BOKOBLIN_FANG = ITEMS.register(
            "bokoblin_fang",
            () -> new BokoblinFang(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE))
    );

    public static final RegistrySupplier<Item> KEESE_EYEBALL = ITEMS.register(
            "keese_eyeball",
            () -> new KeeseEyeball(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.UNCOMMON))
    );

    public static final RegistrySupplier<Item> RUPEE = ITEMS.register(
            "rupee",
            () -> new Item(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE))
    );

    public static final RegistrySupplier<Item> STEEL = ITEMS.register(
            "steel",
            () -> new SteelIngot(
                    new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).stacksTo(64).rarity(Rarity.RARE)
            )
    );

    public static final RegistrySupplier<Item> BOKOBLIN_HORN = ITEMS.register(
            "bokoblin_horn",
            () -> new BokoblinHorn(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE))
    );

    public static final RegistrySupplier<Item> LIZALFOS_HORN = ITEMS.register(
            "lizalfos_horn",
            () -> new LizalfosHorn(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE))
    );

    public static final RegistrySupplier<Item> LIZALFOS_TAIL = ITEMS.register(
            "lizalfos_tail",
            () -> new LizalfosTail(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB))
    );

    public static final RegistrySupplier<Item> LIZALFOS_TALON = ITEMS.register(
            "lizalfos_talon",
            () -> new LizalfosTalon(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE))
    );

    public static final RegistrySupplier<Item> CHUCHU_JELLY = ITEMS.register(
            "chuchu_jelly",
            () -> new ChuchuJelly(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.UNCOMMON))
    );

    public static final RegistrySupplier<Item> CHUCHU_JELLY_RED = ITEMS.register(
            "chuchu_jelly_red",
            () -> new ChuchuJellyRed(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.UNCOMMON))
    );
    // ARMOR

    public static final RegistrySupplier<Item> ZORA_HELMET = ITEMS.register(
            "zora_helmet",
            () -> new ZoraArmorItem(ArmorItem.Type.HELMET, armorProps(ArmorItem.Type.HELMET, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> ZORA_CHESTPLATE = ITEMS.register(
            "zora_chestplate",
            () -> new ZoraArmorItem(ArmorItem.Type.CHESTPLATE, armorProps(ArmorItem.Type.CHESTPLATE, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> ZORA_LEGGINGS = ITEMS.register(
            "zora_leggings",
            () -> new ZoraArmorItem(ArmorItem.Type.LEGGINGS, armorProps(ArmorItem.Type.LEGGINGS, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> ZORA_BOOTS = ITEMS.register(
            "zora_boots",
            () -> new ZoraArmorItem(ArmorItem.Type.BOOTS, armorProps(ArmorItem.Type.BOOTS, DIAMOND_DURABILITY, Rarity.RARE))
    );

    // Hero Set

    public static final RegistrySupplier<Item> HERO_HELMET = ITEMS.register(
            "hero_helmet",
            () -> new HeroArmorItem(ArmorItem.Type.HELMET, armorProps(ArmorItem.Type.HELMET, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> HERO_CHESTPLATE = ITEMS.register(
            "hero_chestplate",
            () -> new HeroArmorItem(ArmorItem.Type.CHESTPLATE, armorProps(ArmorItem.Type.CHESTPLATE, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> HERO_LEGGINGS = ITEMS.register(
            "hero_leggings",
            () -> new HeroArmorItem(ArmorItem.Type.LEGGINGS, armorProps(ArmorItem.Type.LEGGINGS, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> HERO_BOOTS = ITEMS.register(
            "hero_boots",
            () -> new HeroArmorItem(ArmorItem.Type.BOOTS, armorProps(ArmorItem.Type.BOOTS, DIAMOND_DURABILITY, Rarity.RARE))
    );

    // Barbarian Armor
    public static final RegistrySupplier<Item> BARBARIAN_HELMET = ITEMS.register(
            "barbarian_helmet",
            () -> new BarbarianArmorItem(ArmorItem.Type.HELMET, armorProps(ArmorItem.Type.HELMET, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> BARBARIAN_CHESTPLATE = ITEMS.register(
            "barbarian_chestplate",
            () -> new BarbarianArmorItem(ArmorItem.Type.CHESTPLATE, armorProps(ArmorItem.Type.CHESTPLATE, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> BARBARIAN_LEGGINGS = ITEMS.register(
            "barbarian_leggings",
            () -> new BarbarianArmorItem(ArmorItem.Type.LEGGINGS, armorProps(ArmorItem.Type.LEGGINGS, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> BARBARIAN_BOOTS = ITEMS.register(
            "barbarian_boots",
            () -> new BarbarianArmorItem(ArmorItem.Type.BOOTS, armorProps(ArmorItem.Type.BOOTS, DIAMOND_DURABILITY, Rarity.RARE))
    );

    // Knight Armor
    public static final RegistrySupplier<Item> KNIGHT_HELMET = ITEMS.register(
            "knight_helmet",
            () -> new KnightArmorItem(ArmorItem.Type.HELMET, armorProps(ArmorItem.Type.HELMET, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> KNIGHT_CHESTPLATE = ITEMS.register(
            "knight_chestplate",
            () -> new KnightArmorItem(ArmorItem.Type.CHESTPLATE, armorProps(ArmorItem.Type.CHESTPLATE, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> KNIGHT_LEGGINGS = ITEMS.register(
            "knight_leggings",
            () -> new KnightArmorItem(ArmorItem.Type.LEGGINGS, armorProps(ArmorItem.Type.LEGGINGS, DIAMOND_DURABILITY, Rarity.RARE))
    );

    public static final RegistrySupplier<Item> KNIGHT_BOOTS = ITEMS.register(
            "knight_boots",
            () -> new KnightArmorItem(ArmorItem.Type.BOOTS, armorProps(ArmorItem.Type.BOOTS, DIAMOND_DURABILITY, Rarity.RARE))
    );

    // Evil Spirit Armor
    public static final RegistrySupplier<Item> EVIL_SPIRIT_HELMET = ITEMS.register(
            "evil_spirit_helmet",
            () -> new EvilSpiritArmorItem(ArmorItem.Type.HELMET, armorProps(ArmorItem.Type.HELMET, NETHERITE_DURABILITY, Rarity.EPIC))
    );

    public static final RegistrySupplier<Item> EVIL_SPIRIT_CHESTPLATE = ITEMS.register(
            "evil_spirit_chestplate",
            () -> new EvilSpiritArmorItem(ArmorItem.Type.CHESTPLATE, armorProps(ArmorItem.Type.CHESTPLATE, NETHERITE_DURABILITY, Rarity.EPIC))
    );

    public static final RegistrySupplier<Item> EVIL_SPIRIT_LEGGINGS = ITEMS.register(
            "evil_spirit_leggings",
            () -> new EvilSpiritArmorItem(ArmorItem.Type.LEGGINGS, armorProps(ArmorItem.Type.LEGGINGS, NETHERITE_DURABILITY, Rarity.EPIC))
    );

    public static final RegistrySupplier<Item> EVIL_SPIRIT_BOOTS = ITEMS.register(
            "evil_spirit_boots",
            () -> new EvilSpiritArmorItem(ArmorItem.Type.BOOTS, armorProps(ArmorItem.Type.BOOTS, NETHERITE_DURABILITY, Rarity.EPIC))
    );


    public static final RegistrySupplier<Item> HYLIAN_SHIELD = ITEMS.register(
            "hylian_shield",
            () -> new HylianShieldItem(
                    new Item.Properties().durability(1936).arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.EPIC)
            )
    );

    public static final RegistrySupplier<Item> MASTER_SWORD = ITEMS.register(
            "mastersword",
            () -> new MasterSword(
                    new Item.Properties().attributes(SwordItem.createAttributes(Tiers.NETHERITE, 9, -2.0F))
                            .rarity(Rarity.EPIC)
                            .durability(2031) // Netherite
                            .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    );

    public static final RegistrySupplier<Item> LIZALFOS_HORN_DAGGER = ITEMS.register(
            "lizalfos_horn_dagger",
            () -> new SwordItem(
                    Tiers.DIAMOND,
                    new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 2, -1.5F))
                            .rarity(Rarity.UNCOMMON)
                            .durability(461) // Diamond
                            .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    );

    public static final RegistrySupplier<Item> SILVER_LONGSWORD = ITEMS.register(
            "silver_longsword",
            () -> new SilverLongsword(
                    Tiers.DIAMOND,
                    new Item.Properties().attributes(SwordItem.createAttributes(Tiers.DIAMOND, 4, -2.5F))
                            .rarity(Rarity.RARE)
                            .durability(1561) // Diamond
                            .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    );

    public static final RegistrySupplier<Item> LIZAL_SPEAR = ITEMS.register(
            "lizal_spear",
            () -> new LizalSpear(
                    new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.5F))
                            .rarity(Rarity.RARE)
                            .durability(1561)
                            .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    );

    public static final RegistrySupplier<Item> KNIGHTS_CLAYMORE = ITEMS.register(
            "knights_claymore",
            () -> new KnightsClaymore(
                    Tiers.DIAMOND,
                    new Item.Properties().attributes(SwordItem.createAttributes(Tiers.DIAMOND, 9, -3.0F))
                            .rarity(Rarity.RARE)
                            .durability(1561) // Diamond
                            .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    );

    public static final RegistrySupplier<Item> BOULDER_BREAKER = ITEMS.register(
            "boulder_breaker",
            () -> new BoulderBreaker(
                    Tiers.NETHERITE,
                    new Item.Properties().attributes(SwordItem.createAttributes(Tiers.NETHERITE, 9, -3.0F))
                            .rarity(Rarity.EPIC)
                            .durability(2031) // Netherite
                            .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    );

    public static final RegistrySupplier<Item> BOKOBLIN_ARM = ITEMS.register(
            "bokoblin_arm",
            () -> new BokoblinArm(
                    new Item.Properties().attributes(SwordItem.createAttributes(HyruleWeaponMaterials.BOKOBLIN_TIER, -1, -2f))
                            .durability(101) //intentionally weak joke weapon
                            .rarity(Rarity.UNCOMMON)
                            .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
            )
    );

    public static void register() {
        ITEMS.register();
    }
}