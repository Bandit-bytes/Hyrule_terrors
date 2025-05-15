package net.bandit.hyrule_terrors.fabric.client;

import net.bandit.hyrule_terrors.HyruleTerrorsClientMod;
import net.fabricmc.api.ClientModInitializer;

public final class HyruleTerrorsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HyruleTerrorsClientMod.initClientEntityRenders();
        HyruleTerrorsClientMod.initClientAzRenders();
    }
}
