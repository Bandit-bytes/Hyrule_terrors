package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.ChuchuRedAnimator;
import net.bandit.hyrule_terrors.entity.mobs.ChuchuRed;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChuchuRedRenderer extends AzEntityRenderer<ChuchuRed> {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
        "geo/entity/chuchu.geo.json"
    );

    private static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource(
        "textures/entity/chuchu_red.png"
    );

    public ChuchuRedRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<ChuchuRed>builder(MODEL, TEXTURE)
                .setAnimatorProvider(ChuchuRedAnimator::new)
                .setDeathMaxRotation(0.0F)
                .setShadowRadius(0.25F)
                .build(),
            context
        );
    }

    @Override
    public ResourceLocation getTextureLocation(ChuchuRed entity) {
        return TEXTURE;
    }

}
