package net.bandit.hyrule_terrors.item.armor.client.renderer;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.client.animator.KnightArmorAnimator;
import net.minecraft.resources.ResourceLocation;

public class KnightArmorRenderer extends AzArmorRenderer {

    private static final ResourceLocation GEO = HyruleTerrorsMod.modResource(
        "geo/armor/knight_armor.geo.json"
    );

    private static final ResourceLocation TEX = HyruleTerrorsMod.modResource(
        "textures/armor/knight_armor.png"
    );

    public KnightArmorRenderer() {
        super(
            AzArmorRendererConfig.builder(GEO, TEX)
                .setAnimatorProvider(KnightArmorAnimator::new)
                .build()
        );
    }
}
