package net.bandit.hyrule_terrors.entity.client.animator;

import mod.azure.azurelib.animation.AzAnimatorConfig;
import mod.azure.azurelib.animation.controller.AzAnimationController;
import mod.azure.azurelib.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.animation.impl.AzEntityAnimator;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.ChuchuWhite;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ChuchuWhiteAnimator extends AzEntityAnimator<ChuchuWhite> {

    private static final ResourceLocation ANIMATIONS = HyruleTerrorsMod.modResource(
        "animations/entity/chuchu.animation.json"
    );

    public ChuchuWhiteAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ChuchuWhite> animationControllerContainer) {
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
    public @NotNull ResourceLocation getAnimationLocation(ChuchuWhite animatable) {
        return ANIMATIONS;
    }
}
