package net.bandit.hyrule_terrors.neoforge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import static net.bandit.hyrule_terrors.HyruleTerrorsMod.MOD_ID;

@Mod(MOD_ID)
public final class HyruleTerrorsNeoForge {

    public HyruleTerrorsNeoForge(IEventBus modEventBus) {
        HyruleTerrorsMod.init();
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Must run on both sides: AzureLib needs these on the server too so item animations sync in multiplayer.
        // (Previously this only ran in a client-only model event, so dedicated servers never registered them.)
        event.enqueueWork(HyruleTerrorsMod::initAzIdentityRegistry);
    }
}
