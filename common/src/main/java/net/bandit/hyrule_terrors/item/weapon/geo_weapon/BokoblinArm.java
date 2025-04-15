package net.bandit.hyrule_terrors.item.weapon.geo_weapon;

import net.bandit.hyrule_terrors.item.weapon.dispatcher.HyruleItemDispatcher;
import net.bandit.hyrule_terrors.item.HyruleWeaponMaterials;
import net.bandit.hyrule_terrors.item.weapon.client.renderer.BokoblinArmRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.*;

import static mod.azure.azurelib.sblforked.util.RandomUtil.RANDOM;


public class BokoblinArm  extends WeaponItem {
    public final HyruleItemDispatcher dispatcher;
    private static final Map<UUID, Integer> cooldowns = new HashMap<>();
    private static final int LOOP_INTERVAL_TICKS = 80;

    public BokoblinArm(Properties properties) {
        super(HyruleWeaponMaterials.BOKOBLIN_TIER, BokoblinArmRenderer::new, properties
        );
        // Create the instance of the class here to use later.
        this.dispatcher = new HyruleItemDispatcher();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide && entity instanceof LivingEntity living) {
            UUID id = living.getUUID();
            int ticksLeft = cooldowns.getOrDefault(id, 0);
            if (!selected) {
                cooldowns.remove(id);
                return;
            }
            if (ticksLeft <= 0) {
                cooldowns.put(id, LOOP_INTERVAL_TICKS);
                dispatcher.ground_idle(living, stack);
                System.out.println("[DEBUG] Animation triggered for " + id);

            } else {
                cooldowns.put(id, ticksLeft - 1);
            }
        }
    }
//    @Override
//    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
//        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
//        if (livingEntity instanceof Player player && !level.isClientSide()) {
//            // This is where you now trigger an animation to play
//            dispatcher.ground_idle(player, stack);
//        }
//    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {
            attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.SKELETON_HURT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        if (attacker instanceof Player player && !player.getAbilities().instabuild) {
            if (RANDOM.nextFloat() < 0.10f) {
                player.drop(stack.copy(), false);
                player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
                player.displayClientMessage(Component.literal("The Bokoblin Arm slips from your hand!").withStyle(ChatFormatting.GRAY), true);
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
