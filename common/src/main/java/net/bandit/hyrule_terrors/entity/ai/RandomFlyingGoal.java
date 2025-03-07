package net.bandit.hyrule_terrors.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;
import java.util.Random;

public class RandomFlyingGoal extends Goal {
    private final Mob mob;
    private final double speed;
    private int idleTime = 0;
    private final Random random = new Random();

    public RandomFlyingGoal(Mob mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.mob.getTarget() == null;
    }

    @Override
    public void tick() {
        if (idleTime > 0) {
            idleTime--;
            return;
        }

        double x = mob.getX() + (random.nextFloat() - 0.5) * 8.0;
        double y = mob.getY() + (random.nextFloat() - 0.2) * 2.5;
        double z = mob.getZ() + (random.nextFloat() - 0.5) * 8.0;

        mob.getMoveControl().setWantedPosition(x, y, z, speed);

        idleTime = 40 + random.nextInt(60);
    }
}
