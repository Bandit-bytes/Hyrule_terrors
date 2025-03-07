package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Bokoblin;
import net.bandit.hyrule_terrors.entity.mobs.Chuchu;
import net.bandit.hyrule_terrors.entity.mobs.Keese;
import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;



public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(HyruleTerrorsMod.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<Lizalfos>> LIZALFOS = registerEntity("lizalfos", Lizalfos::new, 1.0f, 2.0f);
    public static final RegistrySupplier<EntityType<Chuchu>> CHUCHU = registerEntity("chuchu", Chuchu::new, 0.55f, 0.75f);
    public static final RegistrySupplier<EntityType<Keese>> KEESE = registerEntity("keese", Keese::new, 0.55f, 0.75f);
    public static final RegistrySupplier<EntityType<Bokoblin>> BOKOBLIN = registerEntity("bokoblin", Bokoblin::new, 0.75f, 1.75f);

    private static void initSpawns() {

        registerSpawnPlacements(EntityRegistry.BOKOBLIN, Bokoblin::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.BOKOBLIN_BIOMES, BOKOBLIN.get(), HyruleTerrorsMod.config.bokoblinSpawnWeight, 2, 5);
        registerSpawnPlacements(EntityRegistry.CHUCHU, Chuchu::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.CHUCHU_BIOMES, CHUCHU.get(), HyruleTerrorsMod.config.chuchuSpawnWeight, 2, 5);
        registerSpawnPlacements(EntityRegistry.LIZALFOS, Lizalfos::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.LIZALFOS_BIOMES, LIZALFOS.get(), HyruleTerrorsMod.config.lizalfosSpawnWeight, 2, 5);
        registerSpawnPlacements(EntityRegistry.KEESE, Keese::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.KEESE_BIOMES, KEESE.get(), HyruleTerrorsMod.config.keeseSpawnWeight, 2, 4);
    }

    private static <T extends Mob> RegistrySupplier<EntityType<T>> registerEntity(
            String id, EntityType.EntityFactory<T> entityFactory, float hitboxWidth, float hitboxHeight) {
        return ENTITIES.register(id, () ->
                EntityType.Builder.of(entityFactory, MobCategory.CREATURE)
                        .sized(hitboxWidth, hitboxHeight)
                        .build(ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, id).toString())
        );
    }

    private static <T extends Mob> void registerSpawnPlacements(RegistrySupplier<EntityType<T>> type, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        SpawnPlacementsRegistry.register(
                type,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                spawnPredicate
        );
    }


    private static void addBiomeProperties(TagKey<Biome> tag, EntityType<?> entityType, int spawnWeight, int minGroup, int maxGroup) {
        BiomeModifications.addProperties(
                b -> b.hasTag(tag),
                (ctx, b) -> b.getSpawnProperties().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(entityType, spawnWeight, minGroup, maxGroup))
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
//        LifecycleEvent.SERVER_LEVEL_LOAD.register(level -> initSpawns());
        initSpawns();
    }
}
