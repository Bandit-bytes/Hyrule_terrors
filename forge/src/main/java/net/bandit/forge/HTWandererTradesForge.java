package net.bandit.hyrule_terrors.forge;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.List;

@Mod.EventBusSubscriber(modid = HyruleTerrorsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HTWandererTradesForge {

    private static final int COST = 64;

    @SubscribeEvent
    public static void onWanderingTraderTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> rare = event.getRareTrades();

        rare.add((trader, random) -> new MerchantOffer(
                new ItemStack(ItemRegistry.RUPEE.get(), COST),
                new ItemStack(ItemRegistry.MASTER_SWORD.get(), 1),
                1,
                0,
                0.05F
        ));

        rare.add((trader, random) -> new MerchantOffer(
                new ItemStack(ItemRegistry.RUPEE.get(), COST),
                new ItemStack(ItemRegistry.HYLIAN_SHIELD.get(), 1),
                1,
                0,
                0.05F
        ));
    }
}
