package net.bandit.forge;

import me.shedaniel.autoconfig.AutoConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsClientMod;
import net.bandit.hyrule_terrors.HyruleTerrorsConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.renderer.*;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.bandit.hyrule_terrors.registry.BlockRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class HyruleTerrorsForgeClient {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        HyruleTerrorsMod.initClient();
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.BOKOBLIN_HEAD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.BOKOBLIN_WALL_HEAD.get(), RenderType.cutout());
        });
        event.enqueueWork(HyruleTerrorsClientMod::registerItemProperties);
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (minecraft, screen) -> AutoConfig.getConfigScreen(HyruleTerrorsConfig.class, screen).get()
                )
        );
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.BOKOBLIN.get(), BokoblinRenderer::new);
        event.registerEntityRenderer(EntityRegistry.CHUCHU.get(), ChuchuRenderer::new);
        event.registerEntityRenderer(EntityRegistry.CHUCHU_RED.get(), ChuchuRedRenderer::new);
        event.registerEntityRenderer(EntityRegistry.LIZALFOS.get(), LizalfosRenderer::new);
        event.registerEntityRenderer(EntityRegistry.KEESE.get(), KeeseRenderer::new);
    }
}
