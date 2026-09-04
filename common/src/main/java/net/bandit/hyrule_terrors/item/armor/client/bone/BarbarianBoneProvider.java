package net.bandit.hyrule_terrors.item.armor.client.bone;

import mod.azure.azurelib.common.model.AzBakedModel;
import mod.azure.azurelib.common.model.AzBone;
import mod.azure.azurelib.common.render.armor.bone.AzDefaultArmorBoneProvider;

/**
 * Barbarian armor uses the normal AzureLib left/right bone convention. The previous implementation intentionally
 * crossed the leg mappings (left player leg -> right armor leg and vice versa). That makes the greaves and boots
 * separate from the animated player legs during walking, EMF player animations, and Better Combat poses.
 */
public class BarbarianBoneProvider extends AzDefaultArmorBoneProvider {

    @Override
    public AzBone getLeftBootBone(AzBakedModel model) {
        return model.getBone("armorLeftBoot").orElse(null);
    }

    @Override
    public AzBone getLeftLegBone(AzBakedModel model) {
        return model.getBone("armorLeftLeg").orElse(null);
    }

    @Override
    public AzBone getRightBootBone(AzBakedModel model) {
        return model.getBone("armorRightBoot").orElse(null);
    }

    @Override
    public AzBone getRightLegBone(AzBakedModel model) {
        return model.getBone("armorRightLeg").orElse(null);
    }
}
