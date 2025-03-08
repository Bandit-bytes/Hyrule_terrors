package net.bandit.hyrule_terrors.item.armor.client.renderer;

import mod.azure.azurelib.rewrite.animation.AzAnimator;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class ZeldaArmorRenderer extends AzArmorRenderer {
    public ZeldaArmorRenderer(ResourceLocation modelLocation, ResourceLocation textureLocation, Supplier<@Nullable AzAnimator<ItemStack>> animatorProvider) {
        super(AzArmorRendererConfig.builder(modelLocation, textureLocation).setAnimatorProvider(animatorProvider).build());
    }
}
