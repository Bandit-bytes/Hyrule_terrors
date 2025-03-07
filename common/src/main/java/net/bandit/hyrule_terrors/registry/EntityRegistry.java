package net.bandit.hyrule_terrors.registry;

import dev.architectury.event.events.common.LifecycleEvent;
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

    public static final RegistrySupplier<EntityType<Lizalfos>> LIZALFOS = registerEntity("lizalfos", Lizalfos::new, MobCategory.MONSTER, 1.0f, 2.0f);
    public static final RegistrySupplier<EntityType<Chuchu>> CHUCHU = registerEntity("chuchu", Chuchu::new, MobCategory.MONSTER, 0.55f, 0.75f);
    public static final RegistrySupplier<EntityType<Keese>> KEESE = registerEntity("keese", Keese::new, MobCategory.MONSTER, 0.55f, 0.75f);
    public static final RegistrySupplier<EntityType<Bokoblin>> BOKOBLIN = registerEntity("bokoblin", Bokoblin::new, MobCategory.MONSTER, 0.75f, 1.75f);

    private static void initSpawns() {

        registerSpawnPlacements(EntityRegistry.BOKOBLIN, Bokoblin::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.BOKOBLIN_BIOMES, MobCategory.MONSTER, BOKOBLIN.get(), HyruleTerrorsMod.config.bokoblinSpawnWeight, 1, 1);
        registerSpawnPlacements(EntityRegistry.CHUCHU, Chuchu::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.CHUCHU_BIOMES, MobCategory.MONSTER, CHUCHU.get(), HyruleTerrorsMod.config.chuchuSpawnWeight, 1, 1);
        registerSpawnPlacements(EntityRegistry.LIZALFOS, Lizalfos::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.LIZALFOS_BIOMES, MobCategory.MONSTER, LIZALFOS.get(), HyruleTerrorsMod.config.lizalfosSpawnWeight, 1, 1);
        registerSpawnPlacements(EntityRegistry.KEESE, Keese::checkMobSpawnRules);
        addBiomeProperties(TagRegistry.KEESE_BIOMES, MobCategory.MONSTER, KEESE.get(), HyruleTerrorsMod.config.keeseSpawnWeight, 1, 1);
    }

    private static <T extends Mob> RegistrySupplier<EntityType<T>> registerEntity(
            String id, EntityType.EntityFactory<T> entityFactory, MobCategory category, float hitboxWidth, float hitboxHeight) {
        return ENTITIES.register(id, () ->
                EntityType.Builder.of(entityFactory, category)
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


    private static void addBiomeProperties(TagKey<Biome> tag, MobCategory category, EntityType<?> entityType, int spawnWeight, int minGroup, int maxGroup) {
        BiomeModifications.addProperties(
                b -> b.hasTag(tag),
                (ctx, b) -> b.getSpawnProperties().addSpawn(category, new MobSpawnSettings.SpawnerData(entityType, spawnWeight, minGroup, maxGroup))
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
        LifecycleEvent.SETUP.register(EntityRegistry::initSpawns);
    }
}
