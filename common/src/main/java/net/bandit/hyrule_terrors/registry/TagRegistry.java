package net.bandit.hyrule_terrors.registry;

import dev.architectury.platform.Platform;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class TagRegistry {

    // MOB BIOME SPAWN TAGS
    public static TagKey<Biome> CHUCHU_BIOMES = TagKey.create(
        Registries.BIOME,
            Platform.isForge()
                    ? new ResourceLocation("forge", "is_wet")
                    : new ResourceLocation("c", "climate_wet")
    );

    public static TagKey<Biome> BOKOBLIN_BIOMES = TagKey.create(
        Registries.BIOME,
            Platform.isForge()
                    ? new ResourceLocation("forge", "is_dry")
                    : new ResourceLocation("c", "badlands")
    );

    public static TagKey<Biome> KEESE_BIOMES = TagKey.create(
        Registries.BIOME,
            Platform.isForge()
                    ? new ResourceLocation("minecraft", "is_overworld")
                    : new ResourceLocation("c", "is_overworld")
    );

    public static TagKey<Biome> LIZALFOS_BIOMES = TagKey.create(
        Registries.BIOME,
            Platform.isForge()
                    ? new ResourceLocation("forge", "is_hot")
                    : new ResourceLocation("c", "climate_temperate")
    );
}
