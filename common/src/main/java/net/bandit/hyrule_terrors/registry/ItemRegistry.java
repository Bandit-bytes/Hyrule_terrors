package net.bandit.hyrule_terrors.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.*;
import net.bandit.hyrule_terrors.item.armor.sets.ZoraArmorItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(HyruleTerrorsMod.MOD_ID, Registries.ITEM);
    //Eggies
    public static final RegistrySupplier<Item> BOKOBLIN_SPAWN_EGG = ITEMS.register("bokoblin_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.BOKOBLIN,
                    0xCF6E11, 0xA85807, new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB))); // Orange
    public static final RegistrySupplier<Item> LIZALFOS_SPAWN_EGG = ITEMS.register("lizalfos_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.LIZALFOS,
                    0x7CA81F, 0xF2C849, new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB))); // Green/Yellow
    public static final RegistrySupplier<Item> CHUCHU_SPAWN_EGG = ITEMS.register("chuchu_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.CHUCHU,
                    0x3A89CF, 0x9DD3F7, new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB))); // Blue/Cyan/Light Blue
    public static final RegistrySupplier<Item> KEESE_SPAWN_EGG = ITEMS.register("keese_spawn_egg",
            () -> new ArchitecturySpawnEggItem(EntityRegistry.KEESE,
                    0x3A3A3A, 0x888888, new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB))); // Black/Gray
    //Items
    public static final RegistrySupplier<Item> BOKOBLIN_FANG = ITEMS.register("bokoblin_fang",
            () -> new BokoblinFang(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> BOKOBLIN_HORN = ITEMS.register("bokoblin_horn",
            () -> new BokoblinHorn(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> LIZALFOS_HORN = ITEMS.register("lizalfos_horn",
            () -> new LizalfosHorn(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> LIZALFOS_TAIL = ITEMS.register("lizalfos_tail",
            () -> new LizalfosTail(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB)));
    public static final RegistrySupplier<Item> LIZALFOS_TALON = ITEMS.register("lizalfos_talon",
            () -> new LizalfosTalon(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> CHUCHU_JELLY = ITEMS.register("chuchu_jelly",
            () -> new ChuchuJelly(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.UNCOMMON)));
    // WEAPONS
    public static final RegistrySupplier<Item> LIZALFOS_HORN_DAGGER = ITEMS.register("lizalfos_horn_dagger", () -> new SwordItem(Tiers.IRON,
            new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 2, -1.5F)).rarity(Rarity.UNCOMMON).durability(75).arch$tab(TabRegistry.HYRULE_TERRORS_TAB)));


    public static void register() {
        ITEMS.register();
    }
}


