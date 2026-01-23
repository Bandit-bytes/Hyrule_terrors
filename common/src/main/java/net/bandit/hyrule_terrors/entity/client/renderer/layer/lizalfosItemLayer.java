package net.bandit.hyrule_terrors.entity.client.renderer.layer;

import com.mojang.math.Axis;
import mod.azure.azurelib.common.model.AzBone;
import mod.azure.azurelib.common.render.AzRendererPipelineContext;
import mod.azure.azurelib.common.render.layer.AzBlockAndItemLayer;
import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class lizalfosItemLayer extends AzBlockAndItemLayer<UUID, Lizalfos> {

    private static final String BONE_RIGHT_ARM = "RightHand";
    private static final String BONE_LEFT_ARM  = "LeftHand";

    @Override
    public ItemStack itemStackForBone(AzBone bone, Lizalfos animatable) {
        String name = bone.getName();

        boolean isLeftBone = BONE_LEFT_ARM.equals(name);
        boolean isRightBone = BONE_RIGHT_ARM.equals(name);

        if (!isLeftBone && !isRightBone) return null;

        ItemStack main = animatable.getMainHandItem();
        ItemStack off  = animatable.getOffhandItem();

        boolean leftHanded = animatable.isLeftHanded();

        ItemStack stack = isLeftBone
                ? (leftHanded ? main : off)
                : (leftHanded ? off : main);

        return stack.isEmpty() ? null : stack;
    }

    @Override
    protected ItemDisplayContext getTransformTypeForStack(AzBone bone, ItemStack stack, Lizalfos animatable) {
        return BONE_RIGHT_ARM.equals(bone.getName())
                ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND
                : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    @Override
    protected void renderItemForBone(
            AzRendererPipelineContext<UUID, Lizalfos> context,
            AzBone bone,
            ItemStack itemStack,
            Lizalfos animatable
    ) {
        context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
        context.poseStack().mulPose(Axis.YP.rotationDegrees(10));
        context.poseStack().translate(
                0.1D,
                0.1D,
                -0.40D);

        super.renderItemForBone(context, bone, itemStack, animatable);
    }
}
