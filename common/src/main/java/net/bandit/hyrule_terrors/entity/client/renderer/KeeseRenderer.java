package net.bandit.hyrule_terrors.entity.client.renderer;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.client.animator.KeeseAnimator;
import net.bandit.hyrule_terrors.entity.mobs.Keese;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KeeseRenderer extends AzEntityRenderer<Keese> {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
        "geo/entity/keese.geo.json"
    );

    public static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource("textures/entity/keese.png");

    public static final ResourceLocation FIRE_TEXTURE = HyruleTerrorsMod.modResource("textures/entity/fire_keese.png");

    public static final ResourceLocation ICE_TEXTURE = HyruleTerrorsMod.modResource("textures/entity/ice_keese.png");

    public static final ResourceLocation ELECTRIC_TEXTURE = HyruleTerrorsMod.modResource(
        "textures/entity/electric_keese.png"
    );

    public KeeseRenderer(EntityRendererProvider.Context context) {
        this(context, TEXTURE);
    }

    /** Elemental Keese share the model and animations and only swap the texture. */
    public KeeseRenderer(EntityRendererProvider.Context context, ResourceLocation texture) {
        super(
            AzEntityRendererConfig.<Keese>builder(MODEL, texture)
                .setAnimatorProvider(KeeseAnimator::new)
                .setDeathMaxRotation(0.0F)
                .setShadowRadius(0.25F)
                .build(),
            context
        );
    }
}
