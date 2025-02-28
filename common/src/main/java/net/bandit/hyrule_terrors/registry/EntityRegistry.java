package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Bokoblin;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(HyruleTerrorsMod.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<Bokoblin>> BOKOBLIN = registerEntity("bokoblin", Bokoblin::new, MobCategory.CREATURE, 0.75f, 1.75f);

    private static void initSpawns() {
        registerSpawnPlacements(EntityRegistry.BOKOBLIN, Bokoblin::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.BOKOBLIN_BIOMES, MobCategory.CREATURE, BOKOBLIN.get(), HyruleTerrorsMod.config.bokoblinSpawnWeight, 1, 1);

    }

    public static RegistrySupplier registerEntity(String id, EntityType.EntityFactory entityFactory, MobCategory category, float hitboxWidth, float hitboxHeight) {
        RegistrySupplier registrySupplier = ENTITIES.register(id, () ->
                EntityType.Builder.of(entityFactory, category)
                        .sized(hitboxWidth, hitboxHeight)
                        .build(ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, id).toString())
        );
        return registrySupplier;
    }

    private static <T extends Mob> void registerSpawnPlacements(Supplier<? extends EntityType<T>> type, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        SpawnPlacementsRegistry.register(
                type,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                spawnPredicate
        );
    }

    private static void addBiomeProperties(TagKey<Biome> tag, MobCategory category, EntityType<?> entityType, int spawnWeight, int minGroup, int maxGroup) {
        BiomeModifications.addProperties(
                b -> b.hasTag(tag),
                (ctx, b) -> b.getSpawnProperties().addSpawn(category, new MobSpawnSettings.SpawnerData(entityType, spawnWeight, minGroup, maxGroup))
        );
    }

    private static void initAttributes() {
        EntityAttributeRegistry.register(BOKOBLIN, Bokoblin::createAttributes);

    }

    public static void init() {
        ENTITIES.register();
        initAttributes();
        initSpawns();
    }
}
