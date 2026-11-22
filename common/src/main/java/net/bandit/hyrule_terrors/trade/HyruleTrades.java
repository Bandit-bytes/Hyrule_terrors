package net.bandit.hyrule_terrors.trade;

import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Wandering Trader offers, shared by both loaders. The trader acts as a traveling merchant who buys monster parts for
 * rupees and sells elixirs and legendary gear.
 */
public final class HyruleTrades {

    private HyruleTrades() {}

    /** Common offers: elixirs for sale, monster parts bought for rupees. */
    public static List<VillagerTrades.ItemListing> generic() {
        return List.of(
            sell(ItemRegistry.HEARTY_ELIXIR, cost(ItemRegistry.RUPEE_BLUE, 2), 4),
            sell(ItemRegistry.MIGHTY_ELIXIR, cost(ItemRegistry.RUPEE, 8), 4),
            sell(ItemRegistry.TOUGH_ELIXIR, cost(ItemRegistry.RUPEE, 8), 4),
            sell(ItemRegistry.HASTY_ELIXIR, cost(ItemRegistry.RUPEE, 6), 4),
            sell(ItemRegistry.SNEAKY_ELIXIR, cost(ItemRegistry.RUPEE, 6), 4),
            buy(ItemRegistry.BOKOBLIN_HORN, 4, ItemRegistry.RUPEE, 1),
            buy(ItemRegistry.LIZALFOS_HORN, 3, ItemRegistry.RUPEE, 1),
            buy(ItemRegistry.CHUCHU_JELLY, 6, ItemRegistry.RUPEE, 1)
        );
    }

    /** Rare offers: legendary gear. 3 Red + 4 Green Rupees = 64, the same price as before. */
    public static List<VillagerTrades.ItemListing> rare() {
        return List.of(
            sell(
                ItemRegistry.MASTER_SWORD,
                cost(ItemRegistry.RUPEE_RED, 3),
                Optional.of(cost(ItemRegistry.RUPEE, 4)),
                1
            ),
            sell(
                ItemRegistry.HYLIAN_SHIELD,
                cost(ItemRegistry.RUPEE_RED, 3),
                Optional.of(cost(ItemRegistry.RUPEE, 4)),
                1
            )
        );
    }

    private static ItemStack cost(Supplier<Item> item, int count) {
        return new ItemStack(item.get(), count);
    }

    private static VillagerTrades.ItemListing sell(Supplier<Item> result, ItemStack price, int maxUses) {
        return sell(result, price, Optional.empty(), maxUses);
    }

    private static VillagerTrades.ItemListing sell(
        Supplier<Item> result,
        ItemStack price,
        Optional<ItemStack> secondPrice,
        int maxUses
    ) {
        return (trader, random) -> new MerchantOffer(
            price.copy(),
            secondPrice.map(ItemStack::copy).orElse(ItemStack.EMPTY),
            new ItemStack(result.get()),
            maxUses,
            1,
            0.05F
        );
    }

    private static VillagerTrades.ItemListing buy(Supplier<Item> wanted, int count, Supplier<Item> payment, int paid) {
        return (trader, random) -> new MerchantOffer(
            new ItemStack(wanted.get(), count),
            new ItemStack(payment.get(), paid),
            8,
            1,
            0.05F
        );
    }
}
