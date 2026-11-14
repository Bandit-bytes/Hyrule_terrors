package net.bandit.hyrule_terrors.entity.client.renderer.layer;

import com.mojang.math.Axis;
import mod.azure.azurelib.common.model.AzBone;
import mod.azure.azurelib.common.render.AzRendererPipelineContext;
import mod.azure.azurelib.common.render.layer.AzBlockAndItemLayer;
import net.bandit.hyrule_terrors.entity.mobs.Stalmoblin;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

/**
 * Renders held items on the empty "RightItem"/"LeftItem" locator bones, which sit inside each closed fist.
 */
public class StalmoblinItemLayer extends AzBlockAndItemLayer<UUID, Stalmoblin> {

    private static final String BONE_RIGHT = "RightItem";

    private static final String BONE_LEFT = "LeftItem";

    @Override
    public ItemStack itemStackForBone(AzBone bone, Stalmoblin animatable) {
        String name = bone.getName();
        boolean isLeft = BONE_LEFT.equals(name);
        if (!isLeft && !BONE_RIGHT.equals(name))
            return null;

        boolean leftHanded = animatable.isLeftHanded();
        ItemStack stack = isLeft == leftHanded ? animatable.getMainHandItem() : animatable.getOffhandItem();
        return stack.isEmpty() ? null : stack;
    }

    @Override
    protected ItemDisplayContext getTransformTypeForStack(AzBone bone, ItemStack stack, Stalmoblin animatable) {
        return BONE_LEFT.equals(bone.getName())
            ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND
            : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }

    @Override
    protected void renderItemForBone(
        AzRendererPipelineContext<UUID, Stalmoblin> context,
        AzBone bone,
        ItemStack itemStack,
        Stalmoblin animatable
    ) {
        // The fist points down along the bone; tip the item forward out of it like a vanilla held item.
        context.poseStack().mulPose(Axis.XP.rotationDegrees(270));
        context.poseStack().translate(0.0D, 0.0D, -0.05D);
        super.renderItemForBone(context, bone, itemStack, animatable);
    }
}
