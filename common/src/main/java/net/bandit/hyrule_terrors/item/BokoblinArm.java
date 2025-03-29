package net.bandit.hyrule_terrors.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

import java.util.List;

import static mod.azure.azurelib.sblforked.util.RandomUtil.RANDOM;

public class BokoblinArm extends SwordItem {


    public BokoblinArm(Tier tier, Properties properties) {
        super(tier, properties);
    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {
            attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.SKELETON_HURT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (attacker instanceof Player player && !player.getAbilities().instabuild) {
            if (RANDOM.nextFloat() < 0.10f) {
                player.drop(stack.copy(), false);
                player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.Bokoblin_arm.tooltip").withStyle(ChatFormatting.BLUE));
            tooltipComponents.add(Component.translatable("item.bokoblin_arm.tooltip1").withStyle(ChatFormatting.GRAY));
        }else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return repairWith.is(Items.BONE) || super.isValidRepairItem(toRepair, repairWith);
    }
}
