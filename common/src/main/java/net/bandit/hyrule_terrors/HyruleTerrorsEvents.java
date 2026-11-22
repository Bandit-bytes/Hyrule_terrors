package net.bandit.hyrule_terrors;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LootEvent;
import dev.architectury.event.events.common.TickEvent;
import net.bandit.hyrule_terrors.item.item.HylianShieldItem;
import net.bandit.hyrule_terrors.item.item.MasterSword;
import net.bandit.hyrule_terrors.registry.EffectRegistry;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

/** Loader-agnostic gameplay hooks, registered once from {@link HyruleTerrorsMod#init()}. */
public final class HyruleTerrorsEvents {

    private HyruleTerrorsEvents() {}

    public static void init() {
        TickEvent.PLAYER_POST.register(HyruleTerrorsEvents::onPlayerTick);
        EntityEvent.LIVING_HURT.register(HyruleTerrorsEvents::onLivingHurt);
        LootEvent.MODIFY_LOOT_TABLE.register(HyruleTerrorsEvents::onModifyLootTable);
    }

    private static void onPlayerTick(Player player) {
        MasterSword.tickKnockbackCleanup(player);

        // Frost Resistance thaws the player every tick.
        if (player.getTicksFrozen() > 0 && player.hasEffect(EffectRegistry.FROST_RESISTANCE.get())) {
            player.setTicksFrozen(0);
        }
    }

    private static EventResult onLivingHurt(LivingEntity entity, DamageSource source, float amount) {
        boolean electric = source.is(DamageTypeTags.IS_LIGHTNING);
        if (
            electric && (entity.hasEffect(EffectRegistry.SHOCK_RESISTANCE.get()) || HylianShieldItem.shouldNegateLightning(
                entity
            ))
        ) {
            return EventResult.interruptFalse();
        }
        if (source.is(DamageTypes.FREEZE) && entity.hasEffect(EffectRegistry.FROST_RESISTANCE.get())) {
            return EventResult.interruptFalse();
        }
        return EventResult.pass();
    }

    private static void onModifyLootTable(
        @org.jetbrains.annotations.Nullable net.minecraft.world.level.storage.loot.LootDataManager lootDataManager,
        net.minecraft.resources.ResourceLocation key,
        LootEvent.LootTableModificationContext context,
        boolean builtin
    ) {
        if (!builtin || HyruleTerrorsMod.config == null || !HyruleTerrorsMod.config.addLegendaryChestLoot) {
            return;
        }

        // The Master Sword sleeps deep in the Ancient Cities, where it senses the sculk's corruption.
        if (key.equals(BuiltInLootTables.ANCIENT_CITY)) {
            context.addPool(rarePool(ItemRegistry.MASTER_SWORD.get(), 4, 96));
        }
        // The Hylian Shield turns up in forgotten strongholds.
        if (key.equals(BuiltInLootTables.STRONGHOLD_CORRIDOR) || key.equals(BuiltInLootTables.STRONGHOLD_CROSSING)) {
            context.addPool(rarePool(ItemRegistry.HYLIAN_SHIELD.get(), 5, 95));
        }
        // Rupees and elixirs make dungeon loot feel Hylian.
        if (
            key.equals(BuiltInLootTables.SIMPLE_DUNGEON) || key.equals(BuiltInLootTables.ABANDONED_MINESHAFT)
                || key.equals(BuiltInLootTables.DESERT_PYRAMID) || key.equals(BuiltInLootTables.JUNGLE_TEMPLE)
        ) {
            context.addPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ItemRegistry.RUPEE.get()).setWeight(30))
                    .add(LootItem.lootTableItem(ItemRegistry.RUPEE_BLUE.get()).setWeight(12))
                    .add(LootItem.lootTableItem(ItemRegistry.RUPEE_RED.get()).setWeight(3))
                    .add(LootItem.lootTableItem(ItemRegistry.HEARTY_ELIXIR.get()).setWeight(5))
                    .add(LootItem.lootTableItem(ItemRegistry.MIGHTY_ELIXIR.get()).setWeight(5))
                    .add(EmptyLootItem.emptyItem().setWeight(45))
            );
        }
    }

    private static LootPool.Builder rarePool(net.minecraft.world.item.Item item, int weight, int emptyWeight) {
        return LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1))
            .add(LootItem.lootTableItem(item).setWeight(weight))
            .add(EmptyLootItem.emptyItem().setWeight(emptyWeight));
    }
}
