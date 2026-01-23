package net.bandit.hyrule_terrors.item.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class LizalSpear extends SwordItem {

    private static final int LUNGE_COOLDOWN_TICKS = 20 * 5;
    private static final float LUNGE_STRENGTH = 1.25F;
    private static final float UPWARD_BOOST = 0.05F;
    private static final int DURABILITY_COST = 1;

    public LizalSpear(Properties properties) {
        super(Tiers.IRON, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            lunge(sp);
            tryLungeHit(sp, stack);
            stack.hurtAndBreak(DURABILITY_COST, sp, hand == InteractionHand.MAIN_HAND
                    ? EquipmentSlot.MAINHAND
                    : EquipmentSlot.OFFHAND);
            sp.getCooldowns().addCooldown(this, LUNGE_COOLDOWN_TICKS);
            level.playSound(null, sp.blockPosition(), SoundEvents.TRIDENT_RIPTIDE_1.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        player.swing(hand, true);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private static void lunge(ServerPlayer player) {
        Vec3 look = player.getLookAngle();

        double x = look.x;
        double z = look.z;

        double len = Math.sqrt(x * x + z * z);
        if (len < 1.0e-4) return;

        x /= len;
        z /= len;

        player.push(x * LUNGE_STRENGTH, UPWARD_BOOST, z * LUNGE_STRENGTH);
        player.hurtMarked = true;
    }

    private static void tryLungeHit(ServerPlayer player, ItemStack spearStack) {
        final double reach = 3.0D;

        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle().normalize();

        AABB box = player.getBoundingBox()
                .expandTowards(look.scale(reach))
                .inflate(1.0D, 1.0D, 1.0D);

        LivingEntity target = null;
        double bestDistSqr = Double.MAX_VALUE;

        for (Entity e : player.level().getEntities(player, box, ent -> ent instanceof LivingEntity le && le.isAlive() && ent.isPickable())) {
            LivingEntity le = (LivingEntity) e;
            if (le instanceof Player p) {
                if (p.isSpectator() || p.isCreative()) continue;
                if (!player.canHarmPlayer(p)) continue;
            }

            double distSqr = le.distanceToSqr(player);
            if (distSqr < bestDistSqr) {
                Vec3 to = le.getBoundingBox().getCenter().subtract(start);

                if (to.normalize().dot(look) > 0.35D) {
                    bestDistSqr = distSqr;
                    target = le;
                }
            }
        }

        if (target == null) return;

        float base = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float bonus = 2.0F;
        float damage = base + bonus;

        boolean hurt = target.hurt(player.damageSources().playerAttack(player), damage);
        if (hurt) {
            player.attack(target);
            Vec3 kb = look.scale(0.6D);
            target.push(kb.x, 0.1D, kb.z);

            player.level().playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 0.9F, 1.0F);
        }
    }



    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag
    ) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.lizal_spear.tooltip").withStyle(ChatFormatting.DARK_GREEN));
            tooltipComponents.add(Component.literal("Right-click: Lunge").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
            tooltipComponents.add(Component.translatable("item.lizal_spear.tooltip_1").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
        }
    }
}
