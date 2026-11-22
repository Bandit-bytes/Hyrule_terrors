package net.bandit.fabric;

import net.bandit.hyrule_terrors.trade.HyruleTrades;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;

public class HTWandererTradesFabric {

    public static void init() {
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.addAll(HyruleTrades.generic()));
        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> factories.addAll(HyruleTrades.rare()));
    }
}
