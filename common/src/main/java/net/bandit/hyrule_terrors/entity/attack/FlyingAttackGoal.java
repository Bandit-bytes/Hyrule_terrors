package net.bandit.hyrule_terrors.entity.attack;

import net.bandit.hyrule_terrors.entity.mobs.Keese;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

public class FlyingAttackGoal extends Goal {

    private final Mob mob;

    private LivingEntity target;

    private final double speed;

    private int attackCooldown = 0;

    private int circlingTime = 0;

    private final Random random = new Random();

    public FlyingAttackGoal(Mob mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.mob.getTarget();
        return this.target != null && this.target.isAlive();
    }

    @Override
    public void tick() {
        if (target == null)
            return;

        double distance = this.mob.distanceToSqr(this.target);

        if (circlingTime > 0) {

            circlingTime--;
            double angle = random.nextFloat() * 360;
            double radius = 3.0;
            double x = target.getX() + Math.cos(angle) * radius;
            double z = target.getZ() + Math.sin(angle) * radius;
            double y = target.getY() + 1.5 + (random.nextFloat() - 0.5) * 1.5;

            mob.getMoveControl().setWantedPosition(x, y, z, speed * 0.8);
        } else {
            if (distance > 2.5D) {
                Vec3 direction = new Vec3(
                    target.getX() - mob.getX(),
                    target.getY() - mob.getY() + 1.5,
                    target.getZ() - mob.getZ()
                ).normalize();
                this.mob.setDeltaMovement(direction.scale(speed * 0.8));
            }

            if (distance < 2.5D && attackCooldown == 0) {
                if (this.mob instanceof Keese keese) {
                    keese.dispatcher.attack();
                }

                this.mob.doHurtTarget(target);
                attackCooldown = 30;
                circlingTime = 40 + random.nextInt(20);
            }
        }

        if (attackCooldown > 0) {
            attackCooldown--;
        }
    }
}
