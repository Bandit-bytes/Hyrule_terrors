package net.bandit.forge;

import mod.azure.azurelib.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import dev.architectury.platform.forge.EventBuses;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.BokoblinArmRenderer;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.bandit.hyrule_terrors.item.armor.client.renderer.BarbarianArmorRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.EvilSpiritRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.KnightArmorRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.ZoraArmorRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HyruleTerrorsMod.MOD_ID)
public final class HyruleTerrorsForge {

    public HyruleTerrorsForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(HyruleTerrorsMod.MOD_ID, modEventBus);
        modEventBus.register(this);
        modEventBus.addListener(this::onClientSetup);
        HyruleTerrorsMod.init();
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        HyruleTerrorsMod.initClient();
    }

    @SubscribeEvent
    public void onModelRegister(ModelEvent.RegisterAdditional event) {
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
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
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
}

