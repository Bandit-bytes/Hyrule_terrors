package net.bandit.fabric;

import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;

public class HTWandererTradesFabric {

    private static final int COST = 64;

    public static void init() {
        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {

            factories.add((entity, random) -> new MerchantOffer(
                    new ItemStack(ItemRegistry.RUPEE.get(), COST),
                    new ItemStack(ItemRegistry.MASTER_SWORD.get(), 1),
                    1,
                    0,
                    0.05F
            ));

            factories.add((entity, random) -> new MerchantOffer(
                    new ItemStack(ItemRegistry.RUPEE.get(), COST),
                    new ItemStack(ItemRegistry.HYLIAN_SHIELD.get(), 1),
                    1,
                    0,
                    0.05F
            ));
        });
    }
}
