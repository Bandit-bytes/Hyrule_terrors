package net.bandit.hyrule_terrors.entity.projectile;

import net.bandit.hyrule_terrors.HyruleTerrorsConfig;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.item.MasterSword;
import net.bandit.hyrule_terrors.registry.EntityRegistry;
import net.bandit.hyrule_terrors.registry.ItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * The Master Sword's beam of light. Flies straight (no gravity), fades out after a short range and hits harder when the
 * sword was awakened and the target is evil.
 */
public class SwordBeam extends ThrowableItemProjectile {

    private static final int MAX_LIFETIME = 20;

    private boolean empowered;

    public SwordBeam(EntityType<? extends SwordBeam> type, Level level) {
        super(type, level);
    }

    public SwordBeam(Level level, LivingEntity owner, boolean empowered) {
        super(EntityRegistry.SWORD_BEAM.get(), owner, level);
        this.empowered = empowered;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.SWORD_BEAM.get();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.0D;
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            level().addParticle(
                empowered ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.END_ROD,
                getX(),
                getY(),
                getZ(),
                0.0D,
                0.0D,
                0.0D
            );
        } else if (tickCount > MAX_LIFETIME) {
            fizzle();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        Entity owner = getOwner();
        if (target == owner) {
            return;
        }

        HyruleTerrorsConfig cfg = HyruleTerrorsMod.config;
        float damage = cfg != null ? cfg.masterSwordBeamDamage : 8.0F;
        if (
            empowered && cfg != null && target instanceof LivingEntity living && MasterSword.isEvilTarget(living, cfg)
        ) {
            damage += cfg.masterSwordBonusDamageVsEvil;
        }

        target.hurt(damageSources().indirectMagic(this, owner), damage);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!level().isClientSide && !(result instanceof EntityHitResult ehr && ehr.getEntity() == getOwner())) {
            fizzle();
        }
    }

    private void fizzle() {
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.END_ROD, getX(), getY(), getZ(), 8, 0.2D, 0.2D, 0.2D, 0.05D);
            serverLevel.playSound(
                null,
                getX(),
                getY(),
                getZ(),
                SoundEvents.AMETHYST_BLOCK_BREAK,
                SoundSource.PLAYERS,
                0.6F,
                1.8F
            );
        }
        discard();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Empowered", empowered);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        empowered = tag.getBoolean("Empowered");
    }
}
