package net.bandit.forge;

import dev.architectury.platform.forge.EventBuses;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HyruleTerrorsMod.MOD_ID)
public final class HyruleTerrorsForge {

    public HyruleTerrorsForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(HyruleTerrorsMod.MOD_ID, modEventBus);
        HyruleTerrorsMod.init();
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Must run on both sides: AzureLib needs these on the server too so item animations sync in multiplayer.
        event.enqueueWork(HyruleTerrorsMod::initAzIdentityRegistry);
    }
}
