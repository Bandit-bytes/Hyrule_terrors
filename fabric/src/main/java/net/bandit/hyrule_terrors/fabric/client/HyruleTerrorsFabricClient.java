package net.bandit.hyrule_terrors.fabric.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsClientMod;
import net.bandit.hyrule_terrors.entity.client.renderer.*;
import net.bandit.hyrule_terrors.registry.BlockRegistry;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public final class HyruleTerrorsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.BOKOBLIN, BokoblinRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUCHU, ChuchuRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUCHU_RED, ChuchuRedRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.LIZALFOS, LizalfosRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KEESE, KeeseRenderer::new);
        HyruleTerrorsClientMod.initClientAzRenders();
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOKOBLIN_HEAD.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BOKOBLIN_WALL_HEAD.get(), RenderType.cutout());
    }
}
