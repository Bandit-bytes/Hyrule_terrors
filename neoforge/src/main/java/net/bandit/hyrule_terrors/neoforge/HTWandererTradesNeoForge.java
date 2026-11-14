package net.bandit.hyrule_terrors.neoforge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.trade.HyruleTrades;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class HTWandererTradesNeoForge {

    @SubscribeEvent
    public static void onWanderingTraderTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(HyruleTrades.generic());
        event.getRareTrades().addAll(HyruleTrades.rare());
    }
}
