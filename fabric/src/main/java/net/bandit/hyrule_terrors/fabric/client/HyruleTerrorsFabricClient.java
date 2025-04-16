package net.bandit.hyrule_terrors.fabric.client;

import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.BokoblinArmRenderer;
import net.bandit.hyrule_terrors.registry.ArmorRegistry;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.fabricmc.api.ClientModInitializer;

import static net.bandit.hyrule_terrors.HyruleTerrorsMod.MOD_ID;

public final class HyruleTerrorsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HyruleTerrorsMod.initClient();
        ArmorRegistry.initClient(MOD_ID);
        AzItemRendererRegistry.register(ItemRegistry.BOKOBLIN_ARM.get(), BokoblinArmRenderer::new);
    }
}
