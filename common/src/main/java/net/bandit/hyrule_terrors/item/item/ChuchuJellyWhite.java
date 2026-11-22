package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.util.TooltipHelper;
import net.minecraft.ChatFormatting;
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
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/** White Chuchu Jelly: right-click to release a burst of frost that freezes nearby monsters. */
public class ChuchuJellyWhite extends Item {

    private static final double RADIUS = 5.0D;

    private static final int COOLDOWN = 20 * 8;

    public ChuchuJellyWhite(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (level instanceof ServerLevel serverLevel) {
            List<LivingEntity> targets = serverLevel.getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(RADIUS),
                e -> e != player && e.isAlive() && e instanceof Enemy
            );
            for (LivingEntity target : targets) {
                if (target.canFreeze()) {
                    target.setTicksFrozen(target.getTicksRequiredToFreeze() + 100);
                }
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 4, 2));
                serverLevel.sendParticles(
                    ParticleTypes.SNOWFLAKE,
                    target.getX(),
                    target.getY(0.5D),
                    target.getZ(),
                    12,
                    0.3D,
                    0.4D,
                    0.3D,
                    0.02D
                );
            }
            serverLevel.sendParticles(
                ParticleTypes.SNOWFLAKE,
                player.getX(),
                player.getY() + 1.0D,
                player.getZ(),
                40,
                1.5D,
                0.6D,
                1.5D,
                0.05D
            );
            serverLevel.playSound(
                null,
                player.blockPosition(),
                SoundEvents.POWDER_SNOW_BREAK,
                SoundSource.PLAYERS,
                1.0F,
                0.8F
            );
            serverLevel.playSound(
                null,
                player.blockPosition(),
                SoundEvents.GLASS_BREAK,
                SoundSource.PLAYERS,
                0.6F,
                1.5F
            );

            player.getCooldowns().addCooldown(this, COOLDOWN);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        player.swing(hand, true);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable net.minecraft.world.level.Level level, List<Component> tooltip, TooltipFlag flag) {
        if (TooltipHelper.hasShiftDown()) {
            tooltip.add(
                Component.translatable("item.hyrule_terrors.chuchu_jelly_white.tooltip").withStyle(ChatFormatting.WHITE)
            );
            tooltip.add(
                Component.translatable("item.hyrule_terrors.chuchu_jelly_white.tooltip1").withStyle(ChatFormatting.GRAY)
            );
        } else {
            tooltip.add(Component.translatable("item.hyrule_terrors.hold_shift"));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
