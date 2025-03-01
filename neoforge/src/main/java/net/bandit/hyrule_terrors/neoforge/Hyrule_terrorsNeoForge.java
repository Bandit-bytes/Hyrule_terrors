package net.bandit.hyrule_terrors.neoforge;

import dev.architectury.utils.EnvExecutor;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(HyruleTerrorsMod.MOD_ID)
public final class Hyrule_terrorsNeoForge {
    public Hyrule_terrorsNeoForge() {
        HyruleTerrorsMod.init();
        EnvExecutor.runInEnv(Dist.CLIENT, () -> HyruleTerrorsMod::initClient);
    }
}
