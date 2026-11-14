package net.bandit.hyrule_terrors.entity.mobs;

import mod.azure.azurelib.common.util.MoveAnalysis;
import net.bandit.hyrule_terrors.entity.attack.AnimationSelector;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * Base for the ground-based Hyrule monsters. Extends {@link Monster} so they behave like real hostile mobs: they
 * despawn on Peaceful, stop players sleeping nearby, are targeted by iron golems, and drop vanilla experience only when
 * killed by a player.
 */
public class AbstractTerrorMob extends Monster {

    public final AnimationDispatcher animationDispatcher;

    public final MoveAnalysis moveAnalysis;

    public AnimationSelector<AbstractTerrorMob> animationSelector;

    protected AbstractTerrorMob(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 6;
        this.animationDispatcher = new AnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();

        if (this.level().isClientSide) {
            var isMovingOnGround = moveAnalysis.isMovingHorizontally() && onGround();
            Runnable animationRunner;
            if (isMovingOnGround) {
                if (this.isAggressive()) {
                    animationRunner = animationDispatcher::run;
                } else {
                    animationRunner = animationDispatcher::walk;
                }
            } else {
                animationRunner = animationDispatcher::idle;
            }
            animationRunner.run();
        }
    }
}
