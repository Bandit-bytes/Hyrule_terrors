package net.bandit.hyrule_terrors.entity.client.animator;

import mod.azure.azurelib.rewrite.animation.controller.AzAnimationController;
import mod.azure.azurelib.rewrite.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.rewrite.animation.impl.AzEntityAnimator;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.entity.mobs.Bokoblin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BokoblinAnimator extends AzEntityAnimator<Bokoblin> {
    private static final ResourceLocation ANIMATIONS = ResourceLocation.fromNamespaceAndPath(
            HyruleTerrorsMod.MOD_ID,
            "animations/entity/bokoblin.animation.json"
    );

    @Override
    public void registerControllers(AzAnimationControllerContainer<Bokoblin> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "base_controller")
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(Bokoblin animatable) {
        return ANIMATIONS;
    }
}
