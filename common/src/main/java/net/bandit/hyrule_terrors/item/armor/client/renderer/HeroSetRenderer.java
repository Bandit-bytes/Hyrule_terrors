package net.bandit.hyrule_terrors.item.armor.client.renderer;

import mod.azure.azurelib.common.render.armor.AzArmorRenderer;
import mod.azure.azurelib.common.render.armor.AzArmorRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.resources.ResourceLocation;

public class HeroSetRenderer extends AzArmorRenderer {

    private static final ResourceLocation GEO = HyruleTerrorsMod.modResource(
        "geo/armor/hero_set.geo.json"
    );

    private static final ResourceLocation TEX = HyruleTerrorsMod.modResource(
        "textures/armor/hero_set.png"
    );

    public HeroSetRenderer() {
        super(
            AzArmorRendererConfig.builder(GEO, TEX)
                .build()
        );
    }
}
