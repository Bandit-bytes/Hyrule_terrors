package net.bandit.hyrule_terrors.entity.mobs;

import mod.azure.azurelib.rewrite.util.MoveAnalysis;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class AbstractFlyTerrorMob extends PathfinderMob {
    public final AnimationDispatcher animationDispatcher;
    public final MoveAnalysis moveAnalysis;

    protected AbstractFlyTerrorMob(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.animationDispatcher = new AnimationDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();

        if (this.level().isClientSide) {
            boolean isFlying = !this.onGround();
            Runnable animationRunner = isFlying ? animationDispatcher::fly : animationDispatcher::idle;
            animationRunner.run();
        }
    }
}