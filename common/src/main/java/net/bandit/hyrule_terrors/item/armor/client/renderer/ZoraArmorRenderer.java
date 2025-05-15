package net.bandit.hyrule_terrors.item.armor.client.renderer;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.client.animator.ZoraArmorAnimator;
import net.minecraft.resources.ResourceLocation;

public class ZoraArmorRenderer extends AzArmorRenderer {
    private static final ResourceLocation GEO = HyruleTerrorsMod.modResource(
            "geo/armor/zora_armor.geo.json"
    );

    private static final ResourceLocation TEX = HyruleTerrorsMod.modResource(
            "textures/armor/zora_armor.png"
    );

    public ZoraArmorRenderer() {
        super(
                AzArmorRendererConfig.builder(GEO, TEX)
                        .setAnimatorProvider(ZoraArmorAnimator::new)
                        .build()
        );
    }
}
