package net.bandit.hyrule_terrors.item.armor.client.renderer;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.client.animator.BarbarianAnimator;
import net.minecraft.resources.ResourceLocation;

public class BarbarianArmorRenderer extends AzArmorRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID,
            "geo/armor/barbarian_armor.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID,
            "textures/armor/barbarian_armor.png"
    );

    public BarbarianArmorRenderer() {
        super(
                AzArmorRendererConfig.builder(GEO, TEX)
                        .setAnimatorProvider(BarbarianAnimator::new)
                        .build()
        );
    }
}
