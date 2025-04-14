package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.item.armor.sets.ZeldaArmorItem;
import net.bandit.hyrule_terrors.item.weapon.geo_weapon.WeaponItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ArmorRegistry {
    private static final Map<String, DeferredRegister<Item>> MOD_REGISTERS = new HashMap<>();
    private static final Map<String, RegistrySupplier<Item>> REGISTERED_ITEMS = new HashMap<>();

    private static DeferredRegister<Item> getOrCreateRegister(String modId) {
        return MOD_REGISTERS.computeIfAbsent(modId, id -> DeferredRegister.create(id, Registries.ITEM));
    }
    public static RegistrySupplier<Item> registerItem(String modId, String name, Supplier<Item> itemSupplier) {
        String fullName = modId + ":" + name;
        if (REGISTERED_ITEMS.containsKey(fullName)) {
            throw new IllegalArgumentException("Item " + fullName + " is already registered!");
        }
        DeferredRegister<Item> register = getOrCreateRegister(modId);
        RegistrySupplier<Item> item = register.register(name, itemSupplier);

        item.listen(i -> AzIdentityRegistry.register(i));

        REGISTERED_ITEMS.put(fullName, item);
        return item;
    }

    public static RegistrySupplier<Item> getItem(String modId, String name) {
        return REGISTERED_ITEMS.get(modId + ":" + name);
    }
    public static void init(String modId) {
        DeferredRegister<Item> register = getOrCreateRegister(modId);
        register.register();
    }

    public static void initClient(String modId) {
        for (Map.Entry<String, RegistrySupplier<Item>> entry : REGISTERED_ITEMS.entrySet()) {
            Item item = entry.getValue().get();
            if (item instanceof ZeldaArmorItem) {
                AzArmorRendererRegistry.register(item, ((ZeldaArmorItem) item).RENDERER);
            }
            if (item instanceof WeaponItem) {
                AzItemRendererRegistry.register(item, ((WeaponItem) item).RENDERER);
            }
            if (item instanceof WeaponItem) {
                AzIdentityRegistry.register(item);
            }
        }
    }
}