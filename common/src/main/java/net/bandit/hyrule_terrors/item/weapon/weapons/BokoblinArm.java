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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BokoblinArm extends SwordItem {

    public final HyruleItemDispatcher dispatcher;

    public BokoblinArm() {
        super(
                HyruleWeaponMaterials.BOKOBLIN_TIER,
                -1,     // attack damage bonus (see note below)
                -2.0F,  // attack speed
                new Item.Properties()
                        .durability(100)
                        .rarity(Rarity.UNCOMMON)
                        .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
        );

        this.dispatcher = new HyruleItemDispatcher();
    }

    // Always animating / firing while selected
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
            attacker.level().playSound(
                    null,
                    attacker.blockPosition(),
                    SoundEvents.SKELETON_HURT,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
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

        stack.hurtAndBreak(1, attacker, (living) -> living.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public void appendHoverText(
            ItemStack stack,
            @Nullable Level level,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.bokoblin_arm.tooltip").withStyle(ChatFormatting.BLUE));
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
