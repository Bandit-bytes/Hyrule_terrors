package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(
        HyruleTerrorsMod.MOD_ID,
        Registries.ENTITY_TYPE
    );

    public static final RegistrySupplier<EntityType<Lizalfos>> LIZALFOS = ENTITIES.register(
        "lizalfos",
        () -> EntityType.Builder.of(Lizalfos::new, MobCategory.MONSTER)
            .sized(1.5f, 2.0f)
            .build(HyruleTerrorsMod.modResource("lizalfos").toString())
    );

    public static final RegistrySupplier<EntityType<Chuchu>> CHUCHU = ENTITIES.register(
        "chuchu",
        () -> EntityType.Builder.of(Chuchu::new, MobCategory.MONSTER)
            .sized(0.60f, 0.75f)
            .build(HyruleTerrorsMod.modResource("chuchu").toString())
    );

    public static final RegistrySupplier<EntityType<Keese>> KEESE = ENTITIES.register(
        "keese",
        () -> EntityType.Builder.of(Keese::new, MobCategory.MONSTER)
            .sized(0.55f, 0.75f)
            .build(HyruleTerrorsMod.modResource("keese").toString())
    );

    public static final RegistrySupplier<EntityType<Bokoblin>> BOKOBLIN = ENTITIES.register(
        "bokoblin",
        () -> EntityType.Builder.of(Bokoblin::new, MobCategory.MONSTER)
            .sized(0.75f, 1.75f)
            .build(HyruleTerrorsMod.modResource("bokoblin").toString())
    );

    private static void initSpawns() {
        SpawnPlacementsRegistry.register(
            EntityRegistry.BOKOBLIN,
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Bokoblin::checkMobSpawnRules
        );
        BiomeModifications.addProperties(
            b -> b.hasTag(TagRegistry.BOKOBLIN_BIOMES),
            (ctx, b) -> b.getSpawnProperties()
                .addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(BOKOBLIN.get(), HyruleTerrorsMod.config.bokoblinSpawnWeight, 2, 4)
                )
        );

        SpawnPlacementsRegistry.register(
            EntityRegistry.CHUCHU,
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Chuchu::checkMobSpawnRules
        );
        BiomeModifications.addProperties(
            b -> b.hasTag(TagRegistry.CHUCHU_BIOMES),
            (ctx, b) -> b.getSpawnProperties()
                .addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(CHUCHU.get(), HyruleTerrorsMod.config.chuchuSpawnWeight, 2, 5)
                )
        );

        SpawnPlacementsRegistry.register(
            EntityRegistry.LIZALFOS,
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Lizalfos::checkMobSpawnRules
        );
        BiomeModifications.addProperties(
            b -> b.hasTag(TagRegistry.LIZALFOS_BIOMES),
            (ctx, b) -> b.getSpawnProperties()
                .addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(LIZALFOS.get(), HyruleTerrorsMod.config.lizalfosSpawnWeight, 2, 3)
                )
        );

        SpawnPlacementsRegistry.register(
            EntityRegistry.KEESE,
            SpawnPlacementTypes.NO_RESTRICTIONS,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Keese::checkMobSpawnRules
        );
        BiomeModifications.addProperties(
            b -> b.hasTag(TagRegistry.KEESE_BIOMES),
            (ctx, b) -> b.getSpawnProperties()
                .addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(KEESE.get(), HyruleTerrorsMod.config.keeseSpawnWeight, 2, 4)
                )
        );
    }

    private static void initAttributes() {
        EntityAttributeRegistry.register(BOKOBLIN, Bokoblin::createAttributes);
        EntityAttributeRegistry.register(CHUCHU, Chuchu::createAttributes);
        EntityAttributeRegistry.register(LIZALFOS, Lizalfos::createAttributes);
        EntityAttributeRegistry.register(KEESE, Keese::createAttributes);
    }

    public static void init() {
        ENTITIES.register();
        initAttributes();
        initSpawns();
    }
}
