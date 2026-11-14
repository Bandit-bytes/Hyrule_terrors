package net.bandit.hyrule_terrors.fabric.client;

import net.bandit.hyrule_terrors.HyruleTerrorsClientMod;
import net.bandit.hyrule_terrors.registry.BlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public final class HyruleTerrorsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HyruleTerrorsClientMod.initClient();
        HyruleTerrorsClientMod.registerEntityRenderers(EntityRendererRegistry::register);
        HyruleTerrorsClientMod.initClientAzRenders();
        HyruleTerrorsClientMod.registerItemProperties();
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOKOBLIN_HEAD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOKOBLIN_WALL_HEAD.get(), RenderType.cutout());
    }
}
