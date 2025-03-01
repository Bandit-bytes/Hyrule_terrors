package net.bandit.hyrule_terrors.entity.attack;

import net.bandit.hyrule_terrors.entity.mobs.AbstractTerrorMob;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class DelayedAttackGoal extends MeleeAttackGoal {

    int delayBeforeAttack;

    boolean triggeredAttackAnimation;

    protected final int delayTicksBeforeAttack;

    public DelayedAttackGoal(AbstractTerrorMob mob, double speedModifier, int delayTicksBeforeAttack) {
        super(mob, speedModifier, true);
        this.delayTicksBeforeAttack = delayTicksBeforeAttack;
    }

    @Override
    public void start() {
        super.start();
        this.delayBeforeAttack = 0;
        this.triggeredAttackAnimation = false;
    }

    @Override
    public void checkAndPerformAttack(LivingEntity target) {
        if (this.canPerformAttack(target) && this.mob instanceof AbstractTerrorMob mob) {
            if (this.delayBeforeAttack > 0) {
                this.delayBeforeAttack--;

                if (this.delayBeforeAttack == delayTicksBeforeAttack && !this.triggeredAttackAnimation) {
                    mob.animationSelector.select(mob);
                    this.triggeredAttackAnimation = true;
                }
            } else {
                this.resetAttackCooldown();
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.mob.doHurtTarget(target);
                this.triggeredAttackAnimation = false;
            }
        } else {
            this.delayBeforeAttack = this.adjustedTickDelay(10);
            this.triggeredAttackAnimation = false;
        }
    }
}
