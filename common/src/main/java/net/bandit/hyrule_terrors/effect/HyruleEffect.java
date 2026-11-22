package net.bandit.hyrule_terrors.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

/** Marker effect: behaviour lives in HyruleTerrorsEvents (damage and tick hooks). */
public class HyruleEffect extends MobEffect {

    public HyruleEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
}
