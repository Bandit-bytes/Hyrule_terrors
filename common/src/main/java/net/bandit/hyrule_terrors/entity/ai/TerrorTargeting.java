package net.bandit.hyrule_terrors.entity.ai;

import net.bandit.hyrule_terrors.registry.TagRegistry;
import net.minecraft.world.entity.LivingEntity;

/**
 * Shared targeting helpers for Hyrule Terrors mobs.
 *
 * Additional vanilla or modded targets can be added through the
 * hyrule_terrors:terror_attack_targets entity type tag without recompiling.
 */
public final class TerrorTargeting {

    private TerrorTargeting() {}

    public static boolean isWhitelistedTarget(LivingEntity target) {
        return target != null && target.isAlive() && target.getType().is(TagRegistry.TERROR_ATTACK_TARGETS);
    }
}
