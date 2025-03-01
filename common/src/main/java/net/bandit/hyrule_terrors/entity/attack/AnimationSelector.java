package net.bandit.hyrule_terrors.entity.attack;

import net.bandit.hyrule_terrors.entity.mobs.AbstractTerrorMob;

public interface AnimationSelector<T extends AbstractTerrorMob > {

    void select(T entity);
}
