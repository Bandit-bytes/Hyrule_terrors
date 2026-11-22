package net.bandit.hyrule_terrors.item.armor.client.renderer;

import mod.azure.azurelib.render.armor.AzArmorRenderer;
import mod.azure.azurelib.render.armor.AzArmorRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.resources.ResourceLocation;

public class PhantomArmorRenderer extends AzArmorRenderer {

    private static final ResourceLocation GEO = HyruleTerrorsMod.modResource(
        "geo/armor/phantom_armor.geo.json"
    );

    private static final ResourceLocation TEX = HyruleTerrorsMod.modResource(
        "textures/armor/phantom_armor.png"
    );

    public PhantomArmorRenderer() {
        super(
            AzArmorRendererConfig.builder(GEO, TEX)
                .addRenderLayer(new AzAutoGlowingLayer<>())
                .build()
        );
    }
}
