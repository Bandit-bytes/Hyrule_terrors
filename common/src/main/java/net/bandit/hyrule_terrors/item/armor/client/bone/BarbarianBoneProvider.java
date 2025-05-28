package net.bandit.hyrule_terrors.item.armor.client.bone;

import mod.azure.azurelib.rewrite.model.AzBakedModel;
import mod.azure.azurelib.rewrite.model.AzBone;
import mod.azure.azurelib.rewrite.render.armor.bone.AzDefaultArmorBoneProvider;

public class BarbarianBoneProvider extends AzDefaultArmorBoneProvider {

    @Override
    public AzBone getLeftBootBone(AzBakedModel model) {
        return model.getBone("armorRightBoot").orElse(null);
    }

    @Override
    public AzBone getLeftLegBone(AzBakedModel model) {
        return model.getBone("armorRightLeg").orElse(null);
    }

    @Override
    public AzBone getRightBootBone(AzBakedModel model) {
        return model.getBone("armorLeftBoot").orElse(null);
    }

    @Override
    public AzBone getRightLegBone(AzBakedModel model) {
        return model.getBone("armorLeftLeg").orElse(null);
    }
}
