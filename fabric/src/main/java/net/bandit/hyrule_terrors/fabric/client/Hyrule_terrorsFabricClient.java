package net.bandit.hyrule_terrors.fabric.client;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.fabricmc.api.ClientModInitializer;

public final class Hyrule_terrorsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HyruleTerrorsMod.initClient();
    }
}
