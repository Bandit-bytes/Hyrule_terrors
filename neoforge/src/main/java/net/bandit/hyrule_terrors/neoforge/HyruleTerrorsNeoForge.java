package net.bandit.hyrule_terrors.neoforge;

import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Bokoblin;
import net.bandit.hyrule_terrors.item.armor.client.renderer.BarbarianArmorRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.EvilSpiritRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.KnightArmorRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.ZoraArmorRenderer;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.BokoblinArmRenderer;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import static net.bandit.hyrule_terrors.HyruleTerrorsMod.MOD_ID;


@Mod(MOD_ID)
@EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class HyruleTerrorsNeoForge {
          public HyruleTerrorsNeoForge(IEventBus modEventBus) {
        HyruleTerrorsMod.init();
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::commonSetup);
    }
    public void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
    }
    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional event) {
        AzIdentityRegistry.register(
                ItemRegistry.BOKOBLIN_ARM.get(),
                ItemRegistry.ZORA_HELMET.get(),
                ItemRegistry.ZORA_CHESTPLATE.get(),
                ItemRegistry.ZORA_LEGGINGS.get(),
                ItemRegistry.ZORA_BOOTS.get(),
                ItemRegistry.EVIL_SPIRIT_HELMET.get(),
                ItemRegistry.EVIL_SPIRIT_CHESTPLATE.get(),
                ItemRegistry.EVIL_SPIRIT_LEGGINGS.get(),
                ItemRegistry.EVIL_SPIRIT_BOOTS.get(),
                ItemRegistry.BARBARIAN_HELMET.get(),
                ItemRegistry.BARBARIAN_CHESTPLATE.get(),
                ItemRegistry.BARBARIAN_LEGGINGS.get(),
                ItemRegistry.BARBARIAN_BOOTS.get(),
                ItemRegistry.KNIGHT_HELMET.get(),
                ItemRegistry.KNIGHT_CHESTPLATE.get(),
                ItemRegistry.KNIGHT_LEGGINGS.get(),
                ItemRegistry.KNIGHT_BOOTS.get()
                );
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
              AzItemRendererRegistry.register(ItemRegistry.BOKOBLIN_ARM.get(), BokoblinArmRenderer::new);
        AzArmorRendererRegistry.register(
                ZoraArmorRenderer::new,
                ItemRegistry.ZORA_HELMET.get(),
                ItemRegistry.ZORA_CHESTPLATE.get(),
                ItemRegistry.ZORA_LEGGINGS.get(),
                ItemRegistry.ZORA_BOOTS.get()
        );
        AzArmorRendererRegistry.register(
                EvilSpiritRenderer::new,
                ItemRegistry.EVIL_SPIRIT_HELMET.get(),
                ItemRegistry.EVIL_SPIRIT_CHESTPLATE.get(),
                ItemRegistry.EVIL_SPIRIT_LEGGINGS.get(),
                ItemRegistry.EVIL_SPIRIT_BOOTS.get()
        );
        AzArmorRendererRegistry.register(
                BarbarianArmorRenderer::new,
                ItemRegistry.BARBARIAN_HELMET.get(),
                ItemRegistry.BARBARIAN_CHESTPLATE.get(),
                ItemRegistry.BARBARIAN_LEGGINGS.get(),
                ItemRegistry.BARBARIAN_BOOTS.get()
        );
        AzArmorRendererRegistry.register(
                KnightArmorRenderer::new,
                ItemRegistry.KNIGHT_HELMET.get(),
                ItemRegistry.KNIGHT_CHESTPLATE.get(),
                ItemRegistry.KNIGHT_LEGGINGS.get(),
                ItemRegistry.KNIGHT_BOOTS.get()
        );
    }
    //THIS DOES NOT WORK - JSONS for biome modifiers dont see to be working either
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
              event.register(EntityRegistry.BOKOBLIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                      Monster::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}

