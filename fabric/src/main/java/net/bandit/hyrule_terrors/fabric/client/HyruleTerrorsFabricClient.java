package net.bandit.hyrule_terrors.fabric.client;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.registry.ArmorRegistry;
import net.fabricmc.api.ClientModInitializer;

import static net.bandit.hyrule_terrors.HyruleTerrorsMod.MOD_ID;

public final class HyruleTerrorsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HyruleTerrorsMod.initClient();
        ArmorRegistry.initClient(MOD_ID);
    }
}
