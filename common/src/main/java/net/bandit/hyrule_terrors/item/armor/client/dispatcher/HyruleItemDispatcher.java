package net.bandit.hyrule_terrors.item.armor.client.dispatcher;

import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class HyruleItemDispatcher {
    private static final AzCommand FIRING_COMMAND = AzCommand.create(
            "base_controller",
            "firing",
            AzPlayBehaviors.PLAY_ONCE
    );

    private static final AzCommand GROUND_IDLE_COMMAND = AzCommand.create(
            "base_controller",
            "ground_idle",
            AzPlayBehaviors.LOOP
    );

    public void firing(Entity entity, ItemStack itemStack) {
        FIRING_COMMAND.sendForItem(entity, itemStack);
    }
    public void ground_idle(Entity entity, ItemStack itemStack) {
        GROUND_IDLE_COMMAND.sendForItem(entity, itemStack);
    }
}
