package net.bandit.hyrule_terrors.item.weapon.client.renderer;

import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.resources.ResourceLocation;

public class SpikedMoblinClubRenderer extends AzItemRenderer {

    private static final ResourceLocation MODEL = HyruleTerrorsMod.modResource(
        "geo/weapon/spiked_moblin_club.geo.json"
    );

    private static final ResourceLocation TEXTURE = HyruleTerrorsMod.modResource(
        "textures/weapon/spiked_moblin_club.png"
    );

    public SpikedMoblinClubRenderer() {
        super(
            AzItemRendererConfig.builder(itemStack -> MODEL, itemStack -> TEXTURE)
                .useEntityGuiLighting()
                .useNewOffset(true)
                .build()
        );
    }
}
