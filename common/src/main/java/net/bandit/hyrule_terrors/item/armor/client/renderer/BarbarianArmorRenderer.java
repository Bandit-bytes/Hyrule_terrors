package net.bandit.hyrule_terrors.item.armor.client.renderer;

import mod.azure.azurelib.common.render.armor.AzArmorRenderer;
import mod.azure.azurelib.common.render.armor.AzArmorRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.client.animator.BarbarianAnimator;
import net.bandit.hyrule_terrors.item.armor.client.bone.BarbarianBoneProvider;
import net.minecraft.resources.ResourceLocation;

public class BarbarianArmorRenderer extends AzArmorRenderer {

    private static final ResourceLocation GEO = HyruleTerrorsMod.modResource(
        "geo/armor/barbarian_armor.geo.json"
    );

    private static final ResourceLocation TEX = HyruleTerrorsMod.modResource(
        "textures/armor/barbarian_armor.png"
    );

    public BarbarianArmorRenderer() {
        super(
            AzArmorRendererConfig.builder(GEO, TEX)
                .setAnimatorProvider(BarbarianAnimator::new)
                .setBoneProvider(new BarbarianBoneProvider())
                .build()
        );
    }
}
