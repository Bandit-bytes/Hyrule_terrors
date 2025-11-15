package net.bandit.hyrule_terrors.entity.client.animator;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LizalfosAnimator extends AzEntityAnimator<Lizalfos> {

    private static final ResourceLocation ANIMATIONS = HyruleTerrorsMod.modResource(
        "animations/entity/lizalfos.animation.json"
    );

    public LizalfosAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

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
