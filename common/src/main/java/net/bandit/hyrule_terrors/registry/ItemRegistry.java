package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(HyruleTerrorsMod.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> BOKOBLIN_FANG = ITEMS.register("bokoblin_fang",
            () -> new Item(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> BOKOBLIN_HORN = ITEMS.register("bokoblin_horn",
            () -> new Item(new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB).rarity(Rarity.RARE)));



    public static void register() {
        ITEMS.register();
    }
}


