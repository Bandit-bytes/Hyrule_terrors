package net.bandit.hyrule_terrors.item.weapon.client.renderer;

import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.weapon.client.animator.BokoblinArmAnimator;
import net.minecraft.resources.ResourceLocation;

public class BokoblinArmRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID,
            "geo/weapon/bokoblin_arm.geo.json"
    );

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID,
            "textures/weapon/bokoblin_arm.png"
    );

    public BokoblinArmRenderer() {
        super(
                AzItemRendererConfig.builder(itemStack -> MODEL, itemStack -> TEXTURE)
                        .setAnimatorProvider(BokoblinArmAnimator::new)
                        .useEntityGuiLighting()
                        .useNewOffset(true)
                        .build()
        );
    }

}
