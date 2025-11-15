package net.bandit.hyrule_terrors;

import mod.azure.azurelib.common.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.common.render.item.AzItemRendererRegistry;
import net.bandit.hyrule_terrors.item.armor.client.renderer.BarbarianArmorRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.EvilSpiritRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.KnightArmorRenderer;
import net.bandit.hyrule_terrors.item.armor.client.renderer.ZoraArmorRenderer;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.BokoblinArmRenderer;
import net.bandit.hyrule_terrors.registry.ItemRegistry;

public class HyruleTerrorsClientMod {

    public static void initClientAzRenders() {
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
