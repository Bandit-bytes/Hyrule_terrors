package net.bandit.hyrule_terrors.neoforge;

import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.BokoblinArmRenderer;
import net.bandit.hyrule_terrors.registry.ArmorRegistry;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.bandit.hyrule_terrors.HyruleTerrorsMod.MOD_ID;


@Mod(MOD_ID)
public final class HyruleTerrorsNeoForge {
          public HyruleTerrorsNeoForge(IEventBus modEventBus) {
        HyruleTerrorsMod.init();
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::commonSetup);
    }
    public void commonSetup(final FMLCommonSetupEvent event) {
        AzIdentityRegistry.register(
                ItemRegistry.BOKOBLIN_ARM.get());
    }
    @SubscribeEvent
    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            AzItemRendererRegistry.register(ItemRegistry.BOKOBLIN_ARM.get(), BokoblinArmRenderer::new);
            ArmorRegistry.initClient(MOD_ID);
        });
    }
}

