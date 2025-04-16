package net.bandit.hyrule_terrors.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.BokoblinArmRenderer;
import net.bandit.hyrule_terrors.registry.ArmorRegistry;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

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
              ArmorRegistry.initClient(MOD_ID);
    }
    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional event) {
        AzIdentityRegistry.register(
                ItemRegistry.BOKOBLIN_ARM.get());
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
              AzItemRendererRegistry.register(ItemRegistry.BOKOBLIN_ARM.get(), BokoblinArmRenderer::new);
    }
}

