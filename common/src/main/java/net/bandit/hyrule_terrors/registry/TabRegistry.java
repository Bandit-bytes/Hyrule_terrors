package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(HyruleTerrorsMod.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> HYRULE_TERRORS_TAB = TABS.register(
            HyruleTerrorsMod.MOD_ID + "_tab",
            () -> CreativeTabRegistry.create(
                    Component.translatable("category." + HyruleTerrorsMod.MOD_ID),
                    () -> new ItemStack(ItemRegistry.BOKOBLIN_FANG)
            )
    );

    public static void init() {
        TABS.register();
    }
}
