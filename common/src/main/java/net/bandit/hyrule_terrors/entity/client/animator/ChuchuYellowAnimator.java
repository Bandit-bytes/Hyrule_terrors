package net.bandit.hyrule_terrors.entity.client.animator;

import mod.azure.azurelib.common.animation.AzAnimatorConfig;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.ChuchuYellow;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ChuchuYellowAnimator extends AzEntityAnimator<ChuchuYellow> {

    private static final ResourceLocation ANIMATIONS = HyruleTerrorsMod.modResource(
        "animations/entity/chuchu.animation.json"
    );

    public ChuchuYellowAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<ChuchuYellow> animationControllerContainer) {
        animationControllerContainer.add(AzAnimationController.builder(this, "base_controller").build());
        animationControllerContainer.add(
            AzAnimationController.builder(this, "attack_controller")
                .setTransitionLength(5)
                .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(ChuchuYellow animatable) {
        return ANIMATIONS;
    }
}
