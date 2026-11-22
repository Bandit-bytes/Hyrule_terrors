package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.effect.HyruleEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class EffectRegistry {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(
        HyruleTerrorsMod.MOD_ID,
        Registries.MOB_EFFECT
    );

    /** Electro Elixir: immunity to lightning and electric (Yellow Chuchu, Electric Keese) damage. */
    public static final RegistrySupplier<MobEffect> SHOCK_RESISTANCE = EFFECTS.register(
        "shock_resistance",
        () -> new HyruleEffect(MobEffectCategory.BENEFICIAL, 0xF2D53C)
    );

    /** Chilly Elixir: immunity to freezing (powder snow, White Chuchus, Ice Keese). */
    public static final RegistrySupplier<MobEffect> FROST_RESISTANCE = EFFECTS.register(
        "frost_resistance",
        () -> new HyruleEffect(MobEffectCategory.BENEFICIAL, 0x9FE3FF)
    );

    public static void register() {
        EFFECTS.register();
    }
}
