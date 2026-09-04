package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.ChuchuYellowAnimator;
import net.bandit.hyrule_terrors.entity.mobs.ChuchuYellow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ChuchuYellowRenderer extends AzEntityRenderer<ChuchuYellow> {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
        "geo/entity/chuchu.geo.json"
    );

    private static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource(
        "textures/entity/chuchu_yellow.png"
    );

    public ChuchuYellowRenderer(EntityRendererProvider.Context context) {
        super(
            AzEntityRendererConfig.<ChuchuYellow>builder(MODEL, TEXTURE)
                .setAnimatorProvider(ChuchuYellowAnimator::new)
                .setDeathMaxRotation(0.0F)
                .setShadowRadius(0.25F)
                .build(),
            context
        );
    }

    @Override
    public ResourceLocation getTextureLocation(ChuchuYellow entity) {
        return TEXTURE;
    }
}
