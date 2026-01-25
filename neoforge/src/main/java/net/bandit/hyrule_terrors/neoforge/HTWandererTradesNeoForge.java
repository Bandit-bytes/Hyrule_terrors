package net.bandit.hyrule_terrors.neoforge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class HTWandererTradesNeoForge {

    private static final int COST = 64;

    @SubscribeEvent
    public static void onWanderingTraderTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> rare = event.getRareTrades();

        rare.add(
            (trader, random) -> new MerchantOffer(
                new ItemCost(ItemRegistry.RUPEE.get(), COST),
                new ItemStack(ItemRegistry.MASTER_SWORD.get(), 1),
                1,
                0,
                0.05F
            )
        );

        rare.add(
            (trader, random) -> new MerchantOffer(
                new ItemCost(ItemRegistry.RUPEE.get(), COST),
                new ItemStack(ItemRegistry.HYLIAN_SHIELD.get(), 1),
                1,
                0,
                0.05F
            )
        );
    }
}
