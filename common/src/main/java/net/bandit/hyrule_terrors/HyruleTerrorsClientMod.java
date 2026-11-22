package net.bandit.hyrule_terrors;

import dev.architectury.registry.item.ItemPropertiesRegistry;
import mod.azure.azurelib.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.entity.client.renderer.*;
import net.bandit.hyrule_terrors.item.armor.client.renderer.*;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.BokoblinArmRenderer;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.SpikedMoblinClubRenderer;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class HyruleTerrorsClientMod {

    /** Client-only setup shared by both loaders. */
    public static void initClient() {
        TooltipHelper.setShiftSupplier(Screen::hasShiftDown);
    }

    /** Entity renderers shared by both loaders; each loader passes its own registration callback. */
    public static void registerEntityRenderers(EntityRendererSink sink) {
        sink.register(EntityRegistry.BOKOBLIN.get(), BokoblinRenderer::new);
        sink.register(EntityRegistry.CHUCHU.get(), ChuchuRenderer::new);
        sink.register(EntityRegistry.CHUCHU_RED.get(), ChuchuRedRenderer::new);
        sink.register(EntityRegistry.CHUCHU_YELLOW.get(), ChuchuYellowRenderer::new);
        sink.register(EntityRegistry.CHUCHU_WHITE.get(), ChuchuWhiteRenderer::new);
        sink.register(EntityRegistry.LIZALFOS.get(), LizalfosRenderer::new);
        sink.register(EntityRegistry.STALMOBLIN.get(), StalmoblinRenderer::new);
        sink.register(EntityRegistry.KEESE.get(), KeeseRenderer::new);
        sink.register(EntityRegistry.FIRE_KEESE.get(), ctx -> new KeeseRenderer(ctx, KeeseRenderer.FIRE_TEXTURE));
        sink.register(EntityRegistry.ICE_KEESE.get(), ctx -> new KeeseRenderer(ctx, KeeseRenderer.ICE_TEXTURE));
        sink.register(
            EntityRegistry.ELECTRIC_KEESE.get(),
            ctx -> new KeeseRenderer(ctx, KeeseRenderer.ELECTRIC_TEXTURE)
        );
        sink.register(EntityRegistry.SWORD_BEAM.get(), ctx -> new ThrownItemRenderer<>(ctx, 1.5F, true));
    }

    @FunctionalInterface
    public interface EntityRendererSink {

        <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> provider);
    }

    public static void initClientAzRenders() {
        AzItemRendererRegistry.register(ItemRegistry.BOKOBLIN_ARM.get(), BokoblinArmRenderer::new);
        AzItemRendererRegistry.register(ItemRegistry.SPIKED_MOBLIN_CLUB.get(), SpikedMoblinClubRenderer::new);
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
        AzArmorRendererRegistry.register(
            PhantomArmorRenderer::new,
            ItemRegistry.PHANTOM_HELMET.get(),
            ItemRegistry.PHANTOM_CHESTPLATE.get(),
            ItemRegistry.PHANTOM_LEGGINGS.get(),
            ItemRegistry.PHANTOM_BOOTS.get()
        );
        AzArmorRendererRegistry.register(
            HeroSetRenderer::new,
            ItemRegistry.HERO_HELMET.get(),
            ItemRegistry.HERO_CHESTPLATE.get(),
            ItemRegistry.HERO_LEGGINGS.get(),
            ItemRegistry.HERO_BOOTS.get()
        );
    }

    public static void registerItemProperties() {
        ItemPropertiesRegistry.register(
            ItemRegistry.HYLIAN_SHIELD.get(),
            new ResourceLocation("blocking"),
            (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack
                ? 1.0F
                : 0.0F
        );
    }

}
