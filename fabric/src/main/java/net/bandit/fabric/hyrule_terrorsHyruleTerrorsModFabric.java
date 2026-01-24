package net.bandit.fabric;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.fabricmc.api.ModInitializer;

public final class hyrule_terrorsHyruleTerrorsModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HyruleTerrorsMod.init();
        HyruleTerrorsMod.initAzIdentityRegistry();
        HTWandererTradesFabric.init();
    }
}
