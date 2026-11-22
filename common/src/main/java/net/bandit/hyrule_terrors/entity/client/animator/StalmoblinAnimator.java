package net.bandit.hyrule_terrors.entity.client.animator;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Stalmoblin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class StalmoblinAnimator extends AzEntityAnimator<Stalmoblin> {

    private static final ResourceLocation ANIMATIONS = HyruleTerrorsMod.modResource(
        "animations/entity/stalmoblin.animation.json"
    );

    public StalmoblinAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<Stalmoblin> animationControllerContainer) {
        animationControllerContainer.add(
            AzAnimationController.builder(this, "base_controller")
                .setTransitionLength(4)
                .build()
        );
        // The attack only keys the upper body, so the legs keep walking/running underneath it.
        animationControllerContainer.add(
            AzAnimationController.builder(this, "attack_controller")
                .setTransitionLength(3)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(Stalmoblin animatable) {
        return ANIMATIONS;
    }
}
