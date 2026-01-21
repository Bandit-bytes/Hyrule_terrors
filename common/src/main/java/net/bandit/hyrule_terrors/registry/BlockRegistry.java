package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.block.BokoblinHeadBlock;
import net.bandit.hyrule_terrors.block.BokoblinWallHeadBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            HyruleTerrorsMod.MOD_ID,
            Registries.BLOCK
    );

    public static final RegistrySupplier<Block> BOKOBLIN_HEAD = BLOCKS.register(
            "bokoblin_head",
            () -> new BokoblinHeadBlock(
                    BlockBehaviour.Properties.of()
                            .strength(1.0F)
                            .noOcclusion()
            )
    );

    public static final RegistrySupplier<Block> BOKOBLIN_WALL_HEAD = BLOCKS.register(
            "bokoblin_wall_head",
            () -> new BokoblinWallHeadBlock(
                    BlockBehaviour.Properties.of()
                            .strength(1.0F)
                            .noOcclusion()
            )
    );


    public static void register() {
        BLOCKS.register();
    }
}
