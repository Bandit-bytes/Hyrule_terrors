package net.bandit.hyrule_terrors.neoforge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Bokoblin;
import net.bandit.hyrule_terrors.entity.mobs.Chuchu;
import net.bandit.hyrule_terrors.entity.mobs.Keese;
import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@Mod(HyruleTerrorsMod.MOD_ID)
public final class HyruleTerrorsNeoForge {

    public HyruleTerrorsNeoForge(IEventBus modEventBus) {
        HyruleTerrorsMod.init();
        modEventBus.addListener(HyruleTerrorsNeoForge::commonSetup);
        modEventBus.addListener(HyruleTerrorsNeoForge::registerSpawnPlacements);
    }

    public static void commonSetup(final FMLCommonSetupEvent event) {
        HyruleTerrorsMod.initAzIdentityRegistry();
    }

    public static void registerSpawnPlacements(final RegisterSpawnPlacementsEvent event) {
        event.register(
            EntityRegistry.BOKOBLIN.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Bokoblin::checkMobSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
            EntityRegistry.KEESE.get(),
            SpawnPlacementTypes.NO_RESTRICTIONS,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Keese::checkMobSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
            EntityRegistry.LIZALFOS.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Lizalfos::checkMobSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
        event.register(
            EntityRegistry.CHUCHU.get(),
            SpawnPlacementTypes.ON_GROUND,
            Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
            Chuchu::checkMobSpawnRules,
            RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }
}
