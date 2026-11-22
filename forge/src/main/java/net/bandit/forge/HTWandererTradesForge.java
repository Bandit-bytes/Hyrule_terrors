package net.bandit.forge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.trade.HyruleTrades;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HTWandererTradesForge {

    @SubscribeEvent
    public static void onWanderingTraderTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(HyruleTrades.generic());
        event.getRareTrades().addAll(HyruleTrades.rare());
    }
}
