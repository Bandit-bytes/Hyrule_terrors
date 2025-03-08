package net.bandit.hyrule_terrors.item.armor.client.animator;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ZoraArmorAnimator extends  ZeldaArmorAnimator{
    @Override
    public @NotNull ResourceLocation getAnimationLocation(ItemStack animatable) {
        return ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "animations/armor/zora_armor.animation.json");
    }
}
