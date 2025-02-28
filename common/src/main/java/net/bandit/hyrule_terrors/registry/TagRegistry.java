package net.bandit.hyrule_terrors.registry;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class TagRegistry {
    // MOB BIOME SPAWN TAGS
    public static TagKey<Biome> CHUCHU_BIOMES = TagKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "chuchu_spawn_in")
    );
    public static TagKey<Biome> BOKOBLIN_BIOMES = TagKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "bokoblin_spawn_in")
    );
    public static TagKey<Biome> KEESE_BIOMES = TagKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "keese_spawn_in")
    );
    public static TagKey<Biome> LIZALFOS_BIOMES = TagKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "lizalfos_spawn_in")
    );
}
