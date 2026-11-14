package net.bandit.hyrule_terrors.entity.mobs;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Fire, Ice and Electric Keese. They share the Keese model and AI, but every hit applies their element, and they burst
 * when killed, just like in Breath of the Wild.
 */
public class ElementalKeese extends Keese {

    public enum Element {

        FIRE(ParticleTypes.FLAME),
        ICE(ParticleTypes.SNOWFLAKE),
        ELECTRIC(ParticleTypes.ELECTRIC_SPARK);

        final ParticleOptions particle;

        Element(ParticleOptions particle) {
            this.particle = particle;
        }
    }

    private final Element element;

    protected ElementalKeese(EntityType<? extends Bat> type, Level level, Element element) {
        super(type, level);
        this.element = element;
        this.xpReward = 7;
    }

    public static ElementalKeese fire(EntityType<? extends Bat> type, Level level) {
        return new ElementalKeese(type, level, Element.FIRE);
    }

    public static ElementalKeese ice(EntityType<? extends Bat> type, Level level) {
        return new ElementalKeese(type, level, Element.ICE);
    }

    public static ElementalKeese electric(EntityType<? extends Bat> type, Level level) {
        return new ElementalKeese(type, level, Element.ELECTRIC);
    }

    public static AttributeSupplier.Builder createElementalAttributes() {
        return Keese.createAttributes()
            .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.keeseHealth + 4)
            .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.keeseAttackDamage + 1);
    }

    public Element getElement() {
        return element;
    }

    @Override
    public boolean fireImmune() {
        return element == Element.FIRE || super.fireImmune();
    }

    @Override
    public boolean canFreeze() {
        return element != Element.ICE && super.canFreeze();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level().isClientSide && tickCount % 3 == 0) {
            level().addParticle(
                element.particle,
                getRandomX(0.6D),
                getRandomY(),
                getRandomZ(0.6D),
                0.0D,
                -0.02D,
                0.0D
            );
        }
        // Ice Keese melt in the Nether, Fire Keese are snuffed out by water.
        if (!level().isClientSide && tickCount % 20 == 0) {
            if (element == Element.FIRE && isInWaterRainOrBubble()) {
                hurt(damageSources().drown(), 2.0F);
            } else if (element == Element.ICE && level().dimensionType().ultraWarm()) {
                hurt(damageSources().onFire(), 2.0F);
            }
        }
    }

    @Override
    protected void onElementalHit(LivingEntity target) {
        applyElement(target, 1.0F);
    }

    private void applyElement(LivingEntity target, float strength) {
        switch (element) {
            case FIRE -> target.igniteForSeconds(4.0F * strength);
            case ICE -> {
                target.setTicksFrozen(
                    Math.min(target.getTicksRequiredToFreeze() + 60, target.getTicksFrozen() + (int) (120 * strength))
                );
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (60 * strength), 1));
            }
            case ELECTRIC -> {
                target.hurt(damageSources().lightningBolt(), 2.0F * strength);
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (30 * strength), 3));
            }
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        // Elemental burst on death.
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(element.particle, getX(), getY() + 0.3D, getZ(), 30, 0.6D, 0.6D, 0.6D, 0.05D);
            serverLevel.playSound(
                null,
                blockPosition(),
                element == Element.ELECTRIC
                    ? SoundEvents.LIGHTNING_BOLT_IMPACT
                    : element == Element.FIRE
                        ? SoundEvents.FIRECHARGE_USE
                        : SoundEvents.GLASS_BREAK,
                SoundSource.HOSTILE,
                0.7F,
                1.4F
            );
            AABB box = getBoundingBox().inflate(2.0D);
            for (
                LivingEntity nearby : serverLevel.getEntitiesOfClass(
                    LivingEntity.class,
                    box,
                    e -> e != this && e.isAlive()
                )
            ) {
                applyElement(nearby, 0.5F);
            }
        }
    }
}
