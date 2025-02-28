package net.bandit.hyrule_terrors.entity.mobs;


import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class Bokoblin extends AbstractTerrorMob{
    public Bokoblin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, HyruleTerrorsMod.config.bokoblinHealth)
                .add(Attributes.ATTACK_DAMAGE, HyruleTerrorsMod.config.bokoblinAttackDamage)
                .add(Attributes.ATTACK_SPEED, 2)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }
}
