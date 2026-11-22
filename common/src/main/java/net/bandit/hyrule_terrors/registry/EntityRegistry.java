package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.*;
import net.bandit.hyrule_terrors.entity.projectile.SwordBeam;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
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

    public static final RegistrySupplier<EntityType<ChuchuRed>> CHUCHU_RED = ENTITIES.register(
        "chuchu_red",
        () -> EntityType.Builder.of(ChuchuRed::new, MobCategory.MONSTER)
            .sized(0.60f, 0.75f)
            .build(HyruleTerrorsMod.modResource("chuchu_red").toString())
    );

    public static final RegistrySupplier<EntityType<ChuchuYellow>> CHUCHU_YELLOW = ENTITIES.register(
        "chuchu_yellow",
        () -> EntityType.Builder.of(ChuchuYellow::new, MobCategory.MONSTER)
            .sized(0.60f, 0.75f)
            .build(HyruleTerrorsMod.modResource("chuchu_yellow").toString())
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

    public static final RegistrySupplier<EntityType<Stalmoblin>> STALMOBLIN = ENTITIES.register(
        "stalmoblin",
        () -> EntityType.Builder.of(Stalmoblin::new, MobCategory.MONSTER)
            .sized(0.9f, 2.6f)
            .clientTrackingRange(10)
            .build(HyruleTerrorsMod.modResource("stalmoblin").toString())
    );

    public static final RegistrySupplier<EntityType<ChuchuWhite>> CHUCHU_WHITE = ENTITIES.register(
        "chuchu_white",
        () -> EntityType.Builder.of(ChuchuWhite::new, MobCategory.MONSTER)
            .sized(0.60f, 0.75f)
            .build(HyruleTerrorsMod.modResource("chuchu_white").toString())
    );

    public static final RegistrySupplier<EntityType<ElementalKeese>> FIRE_KEESE = ENTITIES.register(
        "fire_keese",
        () -> EntityType.Builder.of(ElementalKeese::fire, MobCategory.MONSTER)
            .sized(0.55f, 0.75f)
            .fireImmune()
            .build(HyruleTerrorsMod.modResource("fire_keese").toString())
    );

    public static final RegistrySupplier<EntityType<ElementalKeese>> ICE_KEESE = ENTITIES.register(
        "ice_keese",
        () -> EntityType.Builder.of(ElementalKeese::ice, MobCategory.MONSTER)
            .sized(0.55f, 0.75f)
            .immuneTo(Blocks.POWDER_SNOW)
            .build(HyruleTerrorsMod.modResource("ice_keese").toString())
    );

    public static final RegistrySupplier<EntityType<ElementalKeese>> ELECTRIC_KEESE = ENTITIES.register(
        "electric_keese",
        () -> EntityType.Builder.of(ElementalKeese::electric, MobCategory.MONSTER)
            .sized(0.55f, 0.75f)
            .build(HyruleTerrorsMod.modResource("electric_keese").toString())
    );

    public static final RegistrySupplier<EntityType<SwordBeam>> SWORD_BEAM = ENTITIES.register(
        "sword_beam",
        () -> EntityType.Builder.<SwordBeam>of(SwordBeam::new, MobCategory.MISC)
            .sized(0.6f, 0.6f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build(HyruleTerrorsMod.modResource("sword_beam").toString())
    );

    private static <T extends Mob> void addSpawn(
        RegistrySupplier<EntityType<T>> type,
        SpawnPlacements.Type placement,
        SpawnPlacements.SpawnPredicate<T> predicate,
        TagKey<Biome> biomes,
        int weight,
        int min,
        int max
    ) {
        SpawnPlacementsRegistry.register(type, placement, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate);
        if (weight <= 0) {
            return;
        }
        BiomeModifications.addProperties(
            b -> b.hasTag(biomes),
            (ctx, b) -> b.getSpawnProperties()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(type.get(), weight, min, max))
        );
    }

    private static void initSpawns() {
        HyruleTerrorsConfig cfg = HyruleTerrorsMod.config;
        addSpawn(
            BOKOBLIN,
            SpawnPlacements.Type.ON_GROUND,
            Bokoblin::checkMobSpawnRules,
            TagRegistry.BOKOBLIN_BIOMES,
            cfg.bokoblinSpawnWeight,
            1,
            3
        );
        addSpawn(
            CHUCHU,
            SpawnPlacements.Type.ON_GROUND,
            Chuchu::checkMobSpawnRules,
            TagRegistry.CHUCHU_BIOMES,
            cfg.chuchuSpawnWeight,
            2,
            5
        );
        addSpawn(
            CHUCHU_YELLOW,
            SpawnPlacements.Type.ON_GROUND,
            ChuchuYellow::checkMobSpawnRules,
            TagRegistry.CHUCHU_YELLOW_BIOMES,
            cfg.chuchuYellowSpawnWeight,
            1,
            3
        );
        addSpawn(
            CHUCHU_RED,
            SpawnPlacements.Type.ON_GROUND,
            ChuchuRed::checkMobSpawnRules,
            TagRegistry.CHUCHU_RED_BIOMES,
            cfg.chuchuRedSpawnWeight,
            1,
            3
        );
        addSpawn(
            CHUCHU_WHITE,
            SpawnPlacements.Type.ON_GROUND,
            ChuchuWhite::checkMobSpawnRules,
            TagRegistry.CHUCHU_WHITE_BIOMES,
            cfg.chuchuWhiteSpawnWeight,
            1,
            3
        );
        addSpawn(
            LIZALFOS,
            SpawnPlacements.Type.ON_GROUND,
            Lizalfos::checkMobSpawnRules,
            TagRegistry.LIZALFOS_BIOMES,
            cfg.lizalfosSpawnWeight,
            1,
            3
        );
        addSpawn(
            STALMOBLIN,
            SpawnPlacements.Type.ON_GROUND,
            Monster::checkMonsterSpawnRules,
            TagRegistry.STALMOBLIN_BIOMES,
            cfg.stalmoblinSpawnWeight,
            1,
            2
        );
        addSpawn(
            KEESE,
            SpawnPlacements.Type.NO_RESTRICTIONS,
            Keese::checkMobSpawnRules,
            TagRegistry.KEESE_BIOMES,
            cfg.keeseSpawnWeight,
            2,
            4
        );
        addSpawn(
            FIRE_KEESE,
            SpawnPlacements.Type.NO_RESTRICTIONS,
            Keese::checkMobSpawnRules,
            TagRegistry.FIRE_KEESE_BIOMES,
            cfg.elementalKeeseSpawnWeight,
            1,
            3
        );
        addSpawn(
            ICE_KEESE,
            SpawnPlacements.Type.NO_RESTRICTIONS,
            Keese::checkMobSpawnRules,
            TagRegistry.ICE_KEESE_BIOMES,
            cfg.elementalKeeseSpawnWeight,
            1,
            3
        );
        addSpawn(
            ELECTRIC_KEESE,
            SpawnPlacements.Type.NO_RESTRICTIONS,
            Keese::checkMobSpawnRules,
            TagRegistry.ELECTRIC_KEESE_BIOMES,
            cfg.elementalKeeseSpawnWeight,
            1,
            3
        );
    }

    private static void initAttributes() {
        EntityAttributeRegistry.register(BOKOBLIN, Bokoblin::createAttributes);
        EntityAttributeRegistry.register(CHUCHU, Chuchu::createAttributes);
        EntityAttributeRegistry.register(CHUCHU_RED, ChuchuRed::createAttributes);
        EntityAttributeRegistry.register(CHUCHU_YELLOW, ChuchuYellow::createAttributes);
        EntityAttributeRegistry.register(LIZALFOS, Lizalfos::createAttributes);
        EntityAttributeRegistry.register(STALMOBLIN, Stalmoblin::createAttributes);
        EntityAttributeRegistry.register(KEESE, Keese::createAttributes);
        EntityAttributeRegistry.register(CHUCHU_WHITE, ChuchuWhite::createAttributes);
        EntityAttributeRegistry.register(FIRE_KEESE, ElementalKeese::createElementalAttributes);
        EntityAttributeRegistry.register(ICE_KEESE, ElementalKeese::createElementalAttributes);
        EntityAttributeRegistry.register(ELECTRIC_KEESE, ElementalKeese::createElementalAttributes);
    }

    public static void init() {
        ENTITIES.register();
        initAttributes();
        initSpawns();
    }
}
