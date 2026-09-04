package net.bandit.hyrule_terrors.item.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ChuchuJellyYellow extends Item {

    private static final double DISCHARGE_RADIUS = 5.0D;

    private static final float DISCHARGE_DAMAGE = 5.0F;

    private static final int DISCHARGE_COOLDOWN_TICKS = 20 * 8;

    private static final int SLOW_DURATION_TICKS = 20 * 2;

    public ChuchuJellyYellow(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            discharge(serverLevel, player);
            player.getCooldowns().addCooldown(this, DISCHARGE_COOLDOWN_TICKS);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        player.swing(hand, true);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private static void discharge(ServerLevel level, Player player) {
        AABB area = player.getBoundingBox().inflate(DISCHARGE_RADIUS);

        List<LivingEntity> targets = level.getEntitiesOfClass(
            LivingEntity.class,
            area,
            entity -> entity != player
                && entity.isAlive()
                && entity.getType().getCategory() == MobCategory.MONSTER
        );

        for (LivingEntity target : targets) {
            target.hurt(level.damageSources().lightningBolt(), DISCHARGE_DAMAGE);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, SLOW_DURATION_TICKS, 1, false, true));

            level.sendParticles(
                ParticleTypes.ELECTRIC_SPARK,
                target.getX(),
                target.getY() + target.getBbHeight() * 0.5D,
                target.getZ(),
                10,
                Math.max(0.2D, target.getBbWidth() * 0.35D),
                Math.max(0.25D, target.getBbHeight() * 0.25D),
                Math.max(0.2D, target.getBbWidth() * 0.35D),
                0.08D
            );
        }

        level.sendParticles(
            ParticleTypes.ELECTRIC_SPARK,
            player.getX(),
            player.getY() + 1.0D,
            player.getZ(),
            36,
            1.0D,
            0.75D,
            1.0D,
            0.12D
        );

        level.playSound(
            null,
            player.blockPosition(),
            SoundEvents.LIGHTNING_BOLT_IMPACT,
            SoundSource.PLAYERS,
            0.9F,
            1.35F
        );
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        Item.TooltipContext context,
        List<Component> tooltipComponents,
        TooltipFlag tooltipFlag
    ) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.chuchu_jelly_yellow.tooltip")
                    .withStyle(ChatFormatting.YELLOW)
            );
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.chuchu_jelly_yellow.tooltip1")
                    .withStyle(ChatFormatting.GRAY)
            );
            tooltipComponents.add(
                Component.translatable("item.hyrule_terrors.chuchu_jelly_yellow.tooltip2")
                    .withStyle(ChatFormatting.AQUA)
            );
        } else {
            tooltipComponents.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
