package net.bandit.hyrule_terrors.neoforge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.*;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

/**
 * Spawn placement rules on NeoForge. This used to live in a client-only subscriber, so dedicated servers skipped it.
 * Biome spawns themselves come from Architectury's BiomeModifications in common code (which honors the config).
 */
@EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class HyruleTerrorsNeoForgeSpawns {

    private HyruleTerrorsNeoForgeSpawns() {}

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        ground(event, EntityRegistry.BOKOBLIN.get(), Bokoblin::checkMobSpawnRules);
        ground(event, EntityRegistry.LIZALFOS.get(), Lizalfos::checkMobSpawnRules);
        ground(event, EntityRegistry.CHUCHU.get(), Chuchu::checkMobSpawnRules);
        ground(event, EntityRegistry.CHUCHU_RED.get(), ChuchuRed::checkMobSpawnRules);
        ground(event, EntityRegistry.CHUCHU_YELLOW.get(), ChuchuYellow::checkMobSpawnRules);
        ground(event, EntityRegistry.CHUCHU_WHITE.get(), ChuchuWhite::checkMobSpawnRules);
        air(event, EntityRegistry.KEESE.get(), Keese::checkMobSpawnRules);
        air(event, EntityRegistry.FIRE_KEESE.get(), Keese::checkMobSpawnRules);
        air(event, EntityRegistry.ICE_KEESE.get(), Keese::checkMobSpawnRules);
        air(event, EntityRegistry.ELECTRIC_KEESE.get(), Keese::checkMobSpawnRules);
    }

    private static <T extends Mob> void ground(
        RegisterSpawnPlacementsEvent event,
        EntityType<T> type,
        SpawnPlacements.SpawnPredicate<T> predicate
    ) {
        register(event, type, SpawnPlacementTypes.ON_GROUND, predicate);
    }

    private static <T extends Mob> void air(
        RegisterSpawnPlacementsEvent event,
        EntityType<T> type,
        SpawnPlacements.SpawnPredicate<T> predicate
    ) {
        register(event, type, SpawnPlacementTypes.NO_RESTRICTIONS, predicate);
    }

    private static <T extends Mob> void register(
        RegisterSpawnPlacementsEvent event,
        EntityType<T> type,
        SpawnPlacementType placement,
        SpawnPlacements.SpawnPredicate<T> predicate
    ) {
        event.register(
            type,
            placement,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            predicate,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }
}
