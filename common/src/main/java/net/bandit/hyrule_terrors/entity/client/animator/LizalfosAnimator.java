package net.bandit.hyrule_terrors.entity.client.animator;

import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.impl.AzEntityAnimator;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LizalfosAnimator extends AzEntityAnimator<Lizalfos> {
    private static final ResourceLocation ANIMATIONS = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID,
            "animations/entity/lizalfos.animation.json"
    );

    @Override
    public void registerControllers(AzAnimationControllerContainer<Lizalfos> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "base_controller")
                        .build()
        );
        animationControllerContainer.add(
                AzAnimationController.builder(this, "attack_controller")
                        .setTransitionLength(5)
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(Lizalfos animatable) {
        return ANIMATIONS;
    }
}
