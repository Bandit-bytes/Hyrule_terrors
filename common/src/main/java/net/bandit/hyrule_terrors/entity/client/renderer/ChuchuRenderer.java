package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.ChuchuAnimator;
import net.bandit.hyrule_terrors.entity.mobs.Chuchu;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChuchuRenderer extends AzEntityRenderer<Chuchu> {

    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID, "geo/entity/chuchu.geo.json"
    );
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID, "textures/entity/chuchu.png"
    );

    public ChuchuRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<Chuchu>builder(MODEL, TEXTURE)
                        .setAnimatorProvider(ChuchuAnimator::new)
                        .setDeathMaxRotation(0.0F)
                        .setShadowRadius(0.25F)
                        .build(),
                context
        );
    }

}
