package net.bandit.hyrule_terrors.neoforge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.renderer.BokoblinRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.ChuchuRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.KeeseRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.LizalfosRenderer;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(HyruleTerrorsMod.MOD_ID)
public final class HyruleTerrorsNeoForge {
    public HyruleTerrorsNeoForge(IEventBus modEventBus) {
        EntityRegistry.init();
        HyruleTerrorsMod.init();

        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        HyruleTerrorsMod.initClient();
    }

    @EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityRegistry.BOKOBLIN.get(), BokoblinRenderer::new);
            event.registerEntityRenderer(EntityRegistry.CHUCHU.get(), ChuchuRenderer::new);
            event.registerEntityRenderer(EntityRegistry.LIZALFOS.get(), LizalfosRenderer::new);
            event.registerEntityRenderer(EntityRegistry.KEESE.get(), KeeseRenderer::new);
        }
    }
}

