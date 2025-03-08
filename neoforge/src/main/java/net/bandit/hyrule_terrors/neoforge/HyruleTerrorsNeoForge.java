package net.bandit.hyrule_terrors.neoforge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.registry.ArmorRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static net.bandit.hyrule_terrors.HyruleTerrorsMod.MOD_ID;


@Mod(MOD_ID)
public final class HyruleTerrorsNeoForge {
    public HyruleTerrorsNeoForge(IEventBus modEventBus) {
        HyruleTerrorsMod.init();
        modEventBus.addListener(this::onClientSetup);
    }
    private void onClientSetup(final FMLClientSetupEvent event) {
        ArmorRegistry.initClient(MOD_ID);
    }
}

