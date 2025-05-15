package net.bandit.hyrule_terrors;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import net.bandit.hyrule_terrors.entity.client.renderer.BokoblinRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.ChuchuRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.KeeseRenderer;
import net.bandit.hyrule_terrors.entity.client.renderer.LizalfosRenderer;
import net.bandit.hyrule_terrors.registry.*;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HyruleTerrorsMod {
    public static final String MOD_ID = "hyrule_terrors";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static HyruleTerrorsConfig config;

    public static void init() {
        AutoConfig.register(HyruleTerrorsConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(HyruleTerrorsConfig.class).getConfig();

        ItemRegistry.register();
        EntityRegistry.init();
        TabRegistry.init();
    }
    public static void initClient() {
        EntityRendererRegistry.register(EntityRegistry.BOKOBLIN, BokoblinRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CHUCHU, ChuchuRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.LIZALFOS, LizalfosRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.KEESE, KeeseRenderer::new);
    }
    public static void initAzIdentityRegistry() {
        AzIdentityRegistry.register(
            ItemRegistry.BOKOBLIN_ARM.get(),
            ItemRegistry.ZORA_HELMET.get(),
            ItemRegistry.ZORA_CHESTPLATE.get(),
            ItemRegistry.ZORA_LEGGINGS.get(),
            ItemRegistry.ZORA_BOOTS.get(),
            ItemRegistry.EVIL_SPIRIT_HELMET.get(),
            ItemRegistry.EVIL_SPIRIT_CHESTPLATE.get(),
            ItemRegistry.EVIL_SPIRIT_LEGGINGS.get(),
            ItemRegistry.EVIL_SPIRIT_BOOTS.get(),
            ItemRegistry.BARBARIAN_HELMET.get(),
            ItemRegistry.BARBARIAN_CHESTPLATE.get(),
            ItemRegistry.BARBARIAN_LEGGINGS.get(),
            ItemRegistry.BARBARIAN_BOOTS.get(),
            ItemRegistry.KNIGHT_HELMET.get(),
            ItemRegistry.KNIGHT_CHESTPLATE.get(),
            ItemRegistry.KNIGHT_LEGGINGS.get(),
            ItemRegistry.KNIGHT_BOOTS.get()
        );
    }
    public static ResourceLocation modResource(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}
