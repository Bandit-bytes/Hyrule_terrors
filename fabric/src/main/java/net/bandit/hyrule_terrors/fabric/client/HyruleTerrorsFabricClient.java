package net.bandit.hyrule_terrors.fabric.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsClientMod;
import net.bandit.hyrule_terrors.entity.client.renderer.BokoblinRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.ChuchuRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.KeeseRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.LizalfosRenderer;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;

public final class HyruleTerrorsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityRegistry.BOKOBLIN, BokoblinRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUCHU, ChuchuRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.LIZALFOS, LizalfosRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KEESE, KeeseRenderer::new);
        HyruleTerrorsClientMod.initClientAzRenders();
    }
}
