package net.bandit.hyrule_terrors.item.weapon.weapons;

import net.bandit.hyrule_terrors.item.HyruleWeaponMaterials;
import net.bandit.hyrule_terrors.item.weapon.dispatcher.HyruleItemDispatcher;
import net.bandit.hyrule_terrors.registry.TabRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.*;

public class BokoblinArm extends Item {

    public final HyruleItemDispatcher dispatcher;

    public BokoblinArm(Properties properties) {
        super(
            new Properties().attributes(SwordItem.createAttributes(HyruleWeaponMaterials.BOKOBLIN_TIER, 2, -1f))
                .durability(100)
                .rarity(Rarity.UNCOMMON)
                .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
        );
        this.dispatcher = new HyruleItemDispatcher();
    }

    // This is an on click method
    // @Override
    // public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
    // super.inventoryTick(stack, level, entity, slotId, isSelected);
    // }
    //
    // @Override
    // public void onUseTick(
    // @NotNull Level level,
    // @NotNull LivingEntity livingEntity,
    // @NotNull ItemStack stack,
    // int remainingUseDuration
    // ) {
    // super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    // if (livingEntity instanceof Player player && !level.isClientSide()) {
    // dispatcher.serverFire(player, stack);
    // }
    // }
    //
    // @Override
    // public @NotNull InteractionResultHolder<ItemStack> use(
    // @NotNull Level world,
    // Player user,
    // @NotNull InteractionHand hand
    // ) {
    // final var itemStack = user.getItemInHand(hand);
    // user.startUsingItem(hand);
    // return InteractionResultHolder.consume(itemStack);
    // }
    // always animating method
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (!level.isClientSide() && isSelected && entity instanceof Player player) {
            dispatcher.serverFire(player, stack);
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {
            attacker.level()
                .playSound(null, attacker.blockPosition(), SoundEvents.SKELETON_HURT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (attacker instanceof Player player && !player.getAbilities().instabuild) {
            if (attacker.getRandom().nextFloat() < 0.10f) {
                InteractionHand hand = player.getMainHandItem() == stack
                    ? InteractionHand.MAIN_HAND
                    : player.getOffhandItem() == stack ? InteractionHand.OFF_HAND : null;

                if (hand != null) {
                    player.drop(stack, false);
                    player.setItemInHand(hand, ItemStack.EMPTY);
                }

                player.displayClientMessage(
                    Component.literal("The Bokoblin Arm slips from your hand!").withStyle(ChatFormatting.GRAY),
                    true
                );
            }
        }

        stack.hurtAndBreak(1, attacker, (EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.Bokoblin_arm.tooltip").withStyle(ChatFormatting.BLUE));
            tooltipComponents.add(Component.translatable("item.bokoblin_arm.tooltip1").withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repairWith) {
        return repairWith.is(Items.BONE) || super.isValidRepairItem(toRepair, repairWith);
    }
}
