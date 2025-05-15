package net.bandit.hyrule_terrors.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.renderer.BokoblinRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.ChuchuRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.KeeseRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.LizalfosRenderer;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HyruleTerrorsNeoForgeClient {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        HyruleTerrorsMod.initClient();
        ModLoadingContext.get()
            .registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, screen) -> AutoConfig.getConfigScreen(HyruleTerrorsConfig.class, screen).get()
            );
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.BOKOBLIN.get(), BokoblinRenderer::new);
        event.registerEntityRenderer(EntityRegistry.CHUCHU.get(), ChuchuRenderer::new);
        event.registerEntityRenderer(EntityRegistry.LIZALFOS.get(), LizalfosRenderer::new);
        event.registerEntityRenderer(EntityRegistry.KEESE.get(), KeeseRenderer::new);
    }
}
