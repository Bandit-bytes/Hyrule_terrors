package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.sets.ZeldaArmorItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ArmorRegistry {
    // Map to hold DeferredRegisters for each mod ID
    private static final Map<String, DeferredRegister<Item>> MOD_REGISTERS = new HashMap<>();
    private static final Map<String, RegistrySupplier<Item>> REGISTERED_ITEMS = new HashMap<>();

    /**
     * Ensures a DeferredRegister exists for the given mod ID.
     *
     * @param modId The mod ID of the dependent mod.
     * @return The DeferredRegister for the given mod ID.
     */
    private static DeferredRegister<Item> getOrCreateRegister(String modId) {
        return MOD_REGISTERS.computeIfAbsent(modId, id -> DeferredRegister.create(id, Registries.ITEM));
    }

    /**
     * Registers an item dynamically for the given mod ID.
     *
     * @param modId The mod ID of the dependent mod.
     * @param name The name of the item.
     * @param itemSupplier The item supplier.
     * @return The RegistrySupplier for the registered item.
     */
    public static RegistrySupplier<Item> registerItem(String modId, String name, Supplier<Item> itemSupplier) {
        String fullName = modId + ":" + name;
        if (REGISTERED_ITEMS.containsKey(fullName)) {
            throw new IllegalArgumentException("Item " + fullName + " is already registered!");
        }
        DeferredRegister<Item> register = getOrCreateRegister(modId);
        RegistrySupplier<Item> item = register.register(name, itemSupplier);
        if (item instanceof ZeldaArmorItem) AzIdentityRegistry.register(((ZeldaArmorItem) item).asItem());
        REGISTERED_ITEMS.put(fullName, item);
        return item;
    }

    /**
     * Retrieves a registered item by mod ID and name.
     *
     * @param modId The mod ID.
     * @param name The name of the item.
     * @return The RegistrySupplier for the item, or null if not found.
     */
    public static RegistrySupplier<Item> getItem(String modId, String name) {
        return REGISTERED_ITEMS.get(modId + ":" + name);
    }

    /**
     * Initializes the DeferredRegister for a specific mod ID.
     * This method should be called by each mod individually.
     *
     * @param modId The mod ID for which the DeferredRegister should be initialized.
     */
    public static void init(String modId) {
        HyruleTerrorsMod.LOGGER.info("Initializing DeferredRegister for mod: " + modId);
        DeferredRegister<Item> register = getOrCreateRegister(modId);
        register.register(); // Initialize the DeferredRegister for this specific mod
    }

    /**
     * Registers all registered armor items in AzArmorRendererRegistry.
     * This method should be called by each mod individually.
     *
     * @param modId The mod ID for which the armor items should be registered in AzArmorRendererRegistry.
     */
    public static void initClient(String modId) {
        for (Map.Entry<String, RegistrySupplier<Item>> entry : REGISTERED_ITEMS.entrySet()) {
            Item item = entry.getValue().get();
            if (item instanceof ZeldaArmorItem) {
                AzArmorRendererRegistry.register(item, ((ZeldaArmorItem) item).RENDERER);
            }
        }
    }
}