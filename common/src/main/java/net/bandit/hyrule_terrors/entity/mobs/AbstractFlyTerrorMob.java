package net.bandit.hyrule_terrors.entity.mobs;

import mod.azure.azurelib.common.util.MoveAnalysis;
import net.bandit.hyrule_terrors.helper.AnimationDispatcher;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.level.Level;

public class AbstractFlyTerrorMob extends FlyingMob {

    public final AnimationDispatcher animationDispatcher;

    public final MoveAnalysis moveAnalysis;

    protected AbstractFlyTerrorMob(EntityType<? extends FlyingMob> entityType, Level level) {
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
        if (!this.level().isClientSide && this.tickCount % 30 == 0) {
            double hoverAmount = Math.sin(this.tickCount * 0.2) * 0.4;
            this.setDeltaMovement(this.getDeltaMovement().add(0, hoverAmount, 0));
        }
    }
}
