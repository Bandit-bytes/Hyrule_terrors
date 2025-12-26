package net.bandit.hyrule_terrors.registry;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class TagRegistry {
    public static final TagKey<Biome> CHUCHU_BIOMES = TagKey.create(
            Registries.BIOME,
            new ResourceLocation(HyruleTerrorsMod.MOD_ID, "spawns/chuchu")
    );

    public static final TagKey<Biome> BOKOBLIN_BIOMES = TagKey.create(
            Registries.BIOME,
            new ResourceLocation(HyruleTerrorsMod.MOD_ID, "spawns/bokoblin")
    );

    public static final TagKey<Biome> KEESE_BIOMES = TagKey.create(
            Registries.BIOME,
            new ResourceLocation(HyruleTerrorsMod.MOD_ID, "spawns/keese")
    );

    public static final TagKey<Biome> LIZALFOS_BIOMES = TagKey.create(
            Registries.BIOME,
            new ResourceLocation(HyruleTerrorsMod.MOD_ID, "spawns/lizalfos")
    );
}

