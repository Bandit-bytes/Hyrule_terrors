package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.render.entity.AzEntityRenderer;
import mod.azure.azurelib.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.ChuchuWhiteAnimator;
import net.bandit.hyrule_terrors.entity.mobs.ChuchuWhite;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChuchuWhiteRenderer extends AzEntityRenderer<ChuchuWhite> {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
        "geo/entity/chuchu.geo.json"
    );

    private static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource(
        "textures/entity/chuchu_white.png"
    );

    public ChuchuWhiteRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<ChuchuWhite>builder(MODEL, TEXTURE)
                .setAnimatorProvider(ChuchuWhiteAnimator::new)
                .setDeathMaxRotation(0.0F)
                .setShadowRadius(0.25F)
                .build(),
            context
        );
    }

    @Override
    public ResourceLocation getTextureLocation(ChuchuWhite entity) {
        return TEXTURE;
    }

}
