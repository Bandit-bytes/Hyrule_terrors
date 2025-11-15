package net.bandit.hyrule_terrors.item.armor.client.renderer;

import mod.azure.azurelib.common.render.armor.AzArmorRenderer;
import mod.azure.azurelib.common.render.armor.AzArmorRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.client.animator.EvilSpiritAnimator;
import net.minecraft.resources.ResourceLocation;

public class EvilSpiritRenderer extends AzArmorRenderer {

    private static final ResourceLocation GEO = HyruleTerrorsMod.modResource(
        "geo/armor/evil_spirit.geo.json"
    );

    private static final ResourceLocation TEX = HyruleTerrorsMod.modResource(
        "textures/armor/evil_spirit.png"
    );

    public EvilSpiritRenderer() {
        super(
            AzArmorRendererConfig.builder(GEO, TEX)
                .setAnimatorProvider(EvilSpiritAnimator::new)
                .build()
        );
    }
}
