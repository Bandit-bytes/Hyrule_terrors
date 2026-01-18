package net.bandit.fabric.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsClientMod;
import net.bandit.hyrule_terrors.entity.client.renderer.*;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;

public final class hyrule_terrorsHyruleTerrorsModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.BOKOBLIN, BokoblinRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUCHU, ChuchuRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUCHU_RED, ChuchuRedRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.LIZALFOS, LizalfosRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KEESE, KeeseRenderer::new);
        HyruleTerrorsClientMod.initClientAzRenders();
    }
}
