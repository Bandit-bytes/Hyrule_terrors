package net.bandit.forge;

import me.shedaniel.autoconfig.AutoConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsClientMod;
import net.bandit.hyrule_terrors.HyruleTerrorsConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.registry.BlockRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class HyruleTerrorsForgeClient {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        HyruleTerrorsClientMod.initClient();
        ModLoadingContext.get()
            .registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                    (minecraft, screen) -> AutoConfig.getConfigScreen(HyruleTerrorsConfig.class, screen).get()
                )
            );
        event.enqueueWork(HyruleTerrorsClientMod::registerItemProperties);
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.BOKOBLIN_HEAD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.BOKOBLIN_WALL_HEAD.get(), RenderType.cutout());
        });
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        HyruleTerrorsClientMod.registerEntityRenderers(event::registerEntityRenderer);
        HyruleTerrorsClientMod.initClientAzRenders();
    }
}
