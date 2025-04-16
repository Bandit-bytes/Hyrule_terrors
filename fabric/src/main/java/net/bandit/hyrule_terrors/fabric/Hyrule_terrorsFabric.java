package net.bandit.hyrule_terrors.fabric;

import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;

public final class Hyrule_terrorsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HyruleTerrorsMod.init();
        AzIdentityRegistry.register(ItemRegistry.BOKOBLIN_ARM.get());
    }
}
