package net.bandit.hyrule_terrors.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public final class CombatHelper {

    private CombatHelper() {}

    /**
     * Deals extra damage on top of a melee hit that already landed this tick. Vanilla ignores a second hit while the
     * target is in its post-hit invulnerability window unless it is stronger than the first, which silently swallowed
     * every weapon "bonus damage" effect. Clearing the window first makes the bonus actually apply.
     */
    public static boolean dealBonusDamage(LivingEntity target, DamageSource source, float amount) {
        if (amount <= 0.0F || !target.isAlive()) {
            return false;
        }
        target.invulnerableTime = 0;
        return target.hurt(source, amount);
    }
}
