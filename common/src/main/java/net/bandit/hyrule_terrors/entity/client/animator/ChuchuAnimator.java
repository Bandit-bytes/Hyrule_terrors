package net.bandit.hyrule_terrors.entity.client.animator;

import mod.azure.azurelib.rewrite.animation.AzAnimatorConfig;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.impl.AzEntityAnimator;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Chuchu;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ChuchuAnimator extends AzEntityAnimator<Chuchu> {
    private static final ResourceLocation ANIMATIONS = HyruleTerrorsMod.modResource(
            "animations/entity/chuchu.animation.json"
    );
    public ChuchuAnimator() {
        super(AzAnimatorConfig.defaultConfig());
    }

    @Override
    public void registerControllers(AzAnimationControllerContainer<Chuchu> animationControllerContainer) {
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
    public @NotNull ResourceLocation getAnimationLocation(Chuchu animatable) {
        return ANIMATIONS;
    }
}
