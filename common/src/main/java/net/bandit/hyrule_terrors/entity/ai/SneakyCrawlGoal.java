package net.bandit.hyrule_terrors.entity.ai;

import net.bandit.hyrule_terrors.entity.mobs.Lizalfos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class SneakyCrawlGoal extends Goal {
    private final Lizalfos lizalfos;
    private final double crawlSpeed;

    public SneakyCrawlGoal(Lizalfos lizalfos, double speed) {
        this.lizalfos = lizalfos;
        this.crawlSpeed = speed;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = lizalfos.getTarget();
        return target != null && lizalfos.distanceTo(target) < 6.0D && lizalfos.distanceTo(target) > 2.0D;
    }

    @Override
    public void start() {
        lizalfos.dispatcher.crawl();
    }

    @Override
    public void tick() {
        if (lizalfos.getTarget() != null) {
            lizalfos.getNavigation().moveTo(lizalfos.getTarget(), crawlSpeed);
            if (!lizalfos.isInWater() && lizalfos.onGround()) {
                lizalfos.dispatcher.crawl();
            }
        }
    }
    @Override
    public void stop() {
        if (!lizalfos.getNavigation().isInProgress()) {
            if (lizalfos.hurtTime > 0) {
                lizalfos.dispatcher.crawl();
            } else {
                lizalfos.dispatcher.idle();
            }
        }
    }
}
