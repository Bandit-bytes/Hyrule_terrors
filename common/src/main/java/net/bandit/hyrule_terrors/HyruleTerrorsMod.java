package net.bandit.hyrule_terrors;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import mod.azure.azurelib.animation.cache.AzIdentityRegistry;
import net.bandit.hyrule_terrors.registry.BlockRegistry;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.bandit.hyrule_terrors.registry.TabRegistry;
import net.minecraft.resources.ResourceLocation;

public final class HyruleTerrorsMod {
    public static final String MOD_ID = "hyrule_terrors";

    public static HyruleTerrorsConfig config;

    public static void init() {
        // Write common init code here.
        AutoConfig.register(HyruleTerrorsConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(HyruleTerrorsConfig.class).getConfig();

        ItemRegistry.register();
        BlockRegistry.register();
        EntityRegistry.init();
        TabRegistry.init();

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
        return new ResourceLocation(MOD_ID, name);
    }

    public static void initClient() {
        HyruleTerrorsClientMod.initClientAzRenders();

    }
}
