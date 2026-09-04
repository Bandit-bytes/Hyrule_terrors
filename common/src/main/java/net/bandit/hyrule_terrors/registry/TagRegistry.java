package net.bandit.hyrule_terrors.registry;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;

public class TagRegistry {

    public static final TagKey<EntityType<?>> TERROR_ATTACK_TARGETS = TagKey.create(
        Registries.ENTITY_TYPE,
        HyruleTerrorsMod.modResource("terror_attack_targets")
    );

    // MOB BIOME SPAWN TAGS
    public static TagKey<Biome> CHUCHU_BIOMES = TagKey.create(
        Registries.BIOME,
        HyruleTerrorsMod.modResource("chuchu_spawn_in")
    );

    public static TagKey<Biome> BOKOBLIN_BIOMES = TagKey.create(
        Registries.BIOME,
        HyruleTerrorsMod.modResource("bokoblin_spawn_in")
    );

    public static TagKey<Biome> KEESE_BIOMES = TagKey.create(
        Registries.BIOME,
        HyruleTerrorsMod.modResource("keese_spawn_in")
    );

    public static TagKey<Biome> LIZALFOS_BIOMES = TagKey.create(
        Registries.BIOME,
        HyruleTerrorsMod.modResource("lizalfos_spawn_in")
    );
}
