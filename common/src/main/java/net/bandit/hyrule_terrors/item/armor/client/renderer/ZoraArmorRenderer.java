package net.bandit.hyrule_terrors.item.armor.client.renderer;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.client.animator.ZoraArmorAnimator;
import net.minecraft.resources.ResourceLocation;

public class ZoraArmorRenderer extends  ZeldaArmorRenderer{

    public ZoraArmorRenderer() {
        super(
                ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "geo/armor/zora_armor.geo.json"),
                ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "textures/armor/zora_armor.png"),
                ZoraArmorAnimator::new
        );
    }
}
