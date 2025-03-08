package net.bandit.hyrule_terrors.entity.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRenderer;
import mod.azure.azurelib.rewrite.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.LizalfosAnimator;
import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LizalfosRenderer extends AzEntityRenderer<Lizalfos> {

    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID, "geo/entity/lizalfos.geo.json"
    );
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID, "textures/entity/lizalfos.png"
    );

    public LizalfosRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<Lizalfos>builder(MODEL, TEXTURE)
                        .setAnimatorProvider(LizalfosAnimator::new)
                        .setDeathMaxRotation(0.5F)
                        .build(),
                context
        );
        this.shadowRadius = 0.75f;
    }
    @Override
    public void render(Lizalfos entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        float scaleFactor = 1.4F;
        poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
