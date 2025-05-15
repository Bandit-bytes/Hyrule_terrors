package net.bandit.hyrule_terrors.fabric;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.fabricmc.api.ModInitializer;

public final class Hyrule_terrorsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        HyruleTerrorsMod.init();
        HyruleTerrorsMod.initAzIdentityRegistry();
    }
}
