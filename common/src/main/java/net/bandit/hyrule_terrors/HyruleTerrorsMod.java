package net.bandit.hyrule_terrors;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import mod.azure.azurelib.common.internal.common.AzureLib;
import net.bandit.hyrule_terrors.entity.client.renderer.BokoblinRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.ChuchuRenderer;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.bandit.hyrule_terrors.registry.TabRegistry;

public final class HyruleTerrorsMod {
    public static final String MOD_ID = "hyrule_terrors";
    public static HyruleTerrorsConfig config;

    public static void init() {
        AutoConfig.register(HyruleTerrorsConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(HyruleTerrorsConfig.class).getConfig();

        AzureLib.initialize();
        EntityRegistry.init();
        TabRegistry.init();
    }
    public static void initClient() {
        EntityRendererRegistry.register(EntityRegistry.BOKOBLIN, BokoblinRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUCHU, ChuchuRenderer::new);
    }
}
