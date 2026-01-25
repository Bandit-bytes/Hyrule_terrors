package net.bandit.hyrule_terrors.item.item;

import net.bandit.hyrule_terrors.HyruleTerrorsConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.WeakHashMap;

public class MasterSword extends SwordItem {

    private static final String TAG_AWAKENED = "Awakened";

    private static final ResourceLocation AWAKEN_KB_ID =
        ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "master_sword_awaken_kb");

    private static final WeakHashMap<Player, ScanState> SCAN_STATE = new WeakHashMap<>();

    private static final class ScanState {

        long nextCheckTime = 0;

        long lastPosKey = Long.MIN_VALUE;

        boolean lastAwakenResult = false;
    }

    public static final TagKey<EntityType<?>> EVIL_MOBS_TAG =
        TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(HyruleTerrorsMod.MOD_ID, "evil_mobs")
        );

    public MasterSword(Properties properties) {
        super(Tiers.NETHERITE, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || isAwakened(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

        if (level.isClientSide)
            return;
        if (!(level instanceof ServerLevel serverLevel))
            return;
        if (!(entity instanceof Player player))
            return;

        HyruleTerrorsConfig cfg = HyruleTerrorsMod.config;
        if (cfg == null || !cfg.masterSwordEnable)
            return;

        boolean holding = isSelected || player.getOffhandItem() == stack;

        if (!holding) {
            removeKnockbackModifier(player);
            return;
        }

        ScanState state = SCAN_STATE.computeIfAbsent(player, p -> new ScanState());

        long time = serverLevel.getGameTime();
        if (time < state.nextCheckTime) {
            if (isAwakened(stack))
                applyKnockbackModifier(player, cfg.masterSwordKnockbackResistBonus);
            else
                removeKnockbackModifier(player);
            return;
        }

        long posKey = quantizedPosKey(player.blockPosition(), 2);
        if (posKey == state.lastPosKey) {
            state.nextCheckTime = time + cfg.masterSwordCheckIntervalTicks;
            if (state.lastAwakenResult)
                applyKnockbackModifier(player, cfg.masterSwordKnockbackResistBonus);
            else
                removeKnockbackModifier(player);
            return;
        }

        state.lastPosKey = posKey;
        state.nextCheckTime = time + cfg.masterSwordCheckIntervalTicks;

        boolean awakenedNow = shouldAwaken(serverLevel, player, cfg);
        state.lastAwakenResult = awakenedNow;

        boolean awakenedWas = isAwakened(stack);
        if (awakenedNow != awakenedWas) {
            setAwakened(stack, awakenedNow);
            if (awakenedNow)
                spawnAwakenParticles(serverLevel, player);
        }

        if (awakenedNow)
            applyKnockbackModifier(player, cfg.masterSwordKnockbackResistBonus);
        else
            removeKnockbackModifier(player);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        HyruleTerrorsConfig cfg = HyruleTerrorsMod.config;

        if (cfg != null && cfg.masterSwordEnable && isAwakened(stack) && isEvilTarget(target, cfg)) {
            float bonus = cfg.masterSwordBonusDamageVsEvil;
            target.hurt(attacker.damageSources().magic(), bonus);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        TooltipContext context,
        List<Component> tooltip,
        TooltipFlag tooltipFlag
    ) {
        boolean awakened = isAwakened(stack);

        tooltip.add(
            Component.translatable(
                awakened
                    ? "item." + HyruleTerrorsMod.MOD_ID + ".master_sword.state_awakened"
                    : "item." + HyruleTerrorsMod.MOD_ID + ".master_sword.state_dormant"
            )
                .withStyle(awakened ? ChatFormatting.AQUA : ChatFormatting.GRAY)
        );

        if (Screen.hasShiftDown()) {
            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.desc")
                    .withStyle(ChatFormatting.GRAY)
            );

            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.triggers")
                    .withStyle(ChatFormatting.GOLD)
            );

            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.trigger_nether_end")
                    .withStyle(ChatFormatting.DARK_GRAY)
            );
            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.trigger_sculk")
                    .withStyle(ChatFormatting.DARK_GRAY)
            );
            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.trigger_bosses")
                    .withStyle(ChatFormatting.DARK_GRAY)
            );
            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.trigger_evil_tag")
                    .withStyle(ChatFormatting.DARK_GRAY)
            );

            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.effects")
                    .withStyle(ChatFormatting.GOLD)
            );
            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.effect_bonus_damage")
                    .withStyle(ChatFormatting.DARK_AQUA)
            );
            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".master_sword.effect_kb_resist")
                    .withStyle(ChatFormatting.DARK_AQUA)
            );
        } else {
            tooltip.add(
                Component.translatable("item." + HyruleTerrorsMod.MOD_ID + ".hold_shift")
                    .withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }

    private static boolean shouldAwaken(ServerLevel level, Player player, HyruleTerrorsConfig cfg) {
        if (cfg.masterSwordAwakenInNetherOrEnd) {
            if (level.dimension() == Level.NETHER || level.dimension() == Level.END)
                return true;
        }

        if (cfg.masterSwordAwakenNearBosses) {
            if (isBossNearby(level, player, cfg.masterSwordCheckRadius, cfg.masterSwordExtraBossEntityIds))
                return true;
        }

        if (cfg.masterSwordAwakenNearEvilMobsTag) {
            if (isTaggedEvilNearby(level, player, cfg.masterSwordCheckRadius))
                return true;
        }

        if (cfg.masterSwordAwakenNearSculk) {
            if (isSculkNearbyLoadedOnly(level, player.blockPosition(), cfg.masterSwordSculkScanRadius))
                return true;
        }

        return false;
    }

    private static boolean isBossNearby(ServerLevel level, Player player, int radius, List<String> extraBossIds) {
        AABB box = player.getBoundingBox().inflate(radius);

        List<LivingEntity> entities = level.getEntitiesOfClass(
            LivingEntity.class,
            box,
            e -> e != player && e.isAlive()
        );

        for (LivingEntity e : entities) {
            EntityType<?> type = e.getType();
            ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(type);

            if (type == EntityType.WARDEN || type == EntityType.WITHER || type == EntityType.ENDER_DRAGON) {
                return true;
            }

            if (key != null && extraBossIds != null && extraBossIds.contains(key.toString())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTaggedEvilNearby(ServerLevel level, Player player, int radius) {
        AABB box = player.getBoundingBox().inflate(radius);
        return !level.getEntitiesOfClass(
            LivingEntity.class,
            box,
            e -> e != player && e.isAlive() && e.getType().is(EVIL_MOBS_TAG)
        ).isEmpty();
    }

    private static boolean isEvilTarget(LivingEntity target, HyruleTerrorsConfig cfg) {
        EntityType<?> type = target.getType();

        if (type == EntityType.WARDEN || type == EntityType.WITHER || type == EntityType.ENDER_DRAGON)
            return true;
        if (type.is(EVIL_MOBS_TAG))
            return true;

        ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        return key != null
            && cfg.masterSwordExtraBossEntityIds != null
            && cfg.masterSwordExtraBossEntityIds.contains(key.toString());
    }

    private static boolean isSculkNearbyLoadedOnly(ServerLevel level, BlockPos center, int radius) {
        int r = Mth.clamp(radius, 1, 32);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        int cx = center.getX();
        int cy = center.getY();
        int cz = center.getZ();

        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    pos.set(cx + dx, cy + dy, cz + dz);
                    if (!level.hasChunkAt(pos))
                        continue;

                    Block block = level.getBlockState(pos).getBlock();
                    if (
                        block == Blocks.SCULK
                            || block == Blocks.SCULK_CATALYST
                            || block == Blocks.SCULK_SENSOR
                            || block == Blocks.SCULK_SHRIEKER
                            || block == Blocks.SCULK_VEIN
                    ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isAwakened(ItemStack stack) {
        CompoundTag tag = getCustomDataTag(stack);
        return tag.getBoolean(TAG_AWAKENED);
    }

    private static void setAwakened(ItemStack stack, boolean value) {
        CompoundTag tag = getCustomDataTag(stack);
        boolean prev = tag.getBoolean(TAG_AWAKENED);
        if (prev == value)
            return;

        tag.putBoolean(TAG_AWAKENED, value);
        setCustomDataTag(stack, tag);
    }

    private static CompoundTag getCustomDataTag(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.copyTag();
    }

    private static void setCustomDataTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    private static void applyKnockbackModifier(Player player, double amount) {
        var attr = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (attr == null)
            return;

        AttributeModifier existing = attr.getModifier(AWAKEN_KB_ID);
        if (existing != null)
            return;

        attr.addTransientModifier(
            new AttributeModifier(
                AWAKEN_KB_ID,
                amount,
                AttributeModifier.Operation.ADD_VALUE
            )
        );
    }

    private static void removeKnockbackModifier(Player player) {
        var attr = player.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        if (attr == null)
            return;

        AttributeModifier existing = attr.getModifier(AWAKEN_KB_ID);
        if (existing != null) {
            attr.removeModifier(AWAKEN_KB_ID);
        }
    }

    private static void spawnAwakenParticles(ServerLevel level, Player player) {
        level.sendParticles(
            ParticleTypes.SOUL_FIRE_FLAME,
            player.getX(),
            player.getY() + 1.0D,
            player.getZ(),
            8,
            0.20D,
            0.35D,
            0.20D,
            0.01D
        );
    }

    private static long quantizedPosKey(BlockPos pos, int cellSize) {
        int x = pos.getX() / cellSize;
        int y = pos.getY() / cellSize;
        int z = pos.getZ() / cellSize;

        long key = 1469598103934665603L;
        key = (key ^ x) * 1099511628211L;
        key = (key ^ y) * 1099511628211L;
        key = (key ^ z) * 1099511628211L;
        return key;
    }
}
