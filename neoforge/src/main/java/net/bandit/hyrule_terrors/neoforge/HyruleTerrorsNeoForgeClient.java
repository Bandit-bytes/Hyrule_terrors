package net.bandit.hyrule_terrors.neoforge;

import me.shedaniel.autoconfig.AutoConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = HyruleTerrorsMod.MOD_ID, dist = Dist.CLIENT)
public class HyruleTerrorsNeoForgeClient {
    public HyruleTerrorsNeoForgeClient() {
        HyruleTerrorsMod.initClient();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (modContainer, screen) -> AutoConfig.getConfigScreen(HyruleTerrorsConfig.class, screen).get());
    }
}
