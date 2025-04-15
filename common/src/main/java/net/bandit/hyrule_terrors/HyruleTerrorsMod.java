package net.bandit.hyrule_terrors;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import mod.azure.azurelib.common.internal.common.AzureLib;
import net.bandit.hyrule_terrors.entity.client.renderer.BokoblinRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.ChuchuRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.KeeseRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.LizalfosRenderer;
import net.bandit.hyrule_terrors.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HyruleTerrorsMod {
    public static final String MOD_ID = "hyrule_terrors";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static HyruleTerrorsConfig config;

    public static void init() {
        AutoConfig.register(HyruleTerrorsConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(HyruleTerrorsConfig.class).getConfig();

        AzureLib.initialize();
        ItemRegistry.register();
        EntityRegistry.init();
        TabRegistry.init();

        ArmorItemRegistry.init();
        ArmorRegistry.init(MOD_ID);
    }
    public static void initClient() {
        EntityRendererRegistry.register(EntityRegistry.BOKOBLIN, BokoblinRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUCHU, ChuchuRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.LIZALFOS, LizalfosRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KEESE, KeeseRenderer::new);
    }
}
