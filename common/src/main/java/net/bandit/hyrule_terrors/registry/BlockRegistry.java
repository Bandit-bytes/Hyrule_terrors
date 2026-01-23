package net.bandit.hyrule_terrors.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.block.BokoblinHeadBlock;
import net.bandit.hyrule_terrors.block.BokoblinWallHeadBlock;
import net.bandit.hyrule_terrors.item.item.BokoblinHeadItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(HyruleTerrorsMod.MOD_ID, Registries.BLOCK);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(HyruleTerrorsMod.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Block> BOKOBLIN_HEAD = BLOCKS.register(
            "bokoblin_head",
            () -> new BokoblinHeadBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );

    public static final RegistrySupplier<Block> BOKOBLIN_WALL_HEAD = BLOCKS.register(
            "bokoblin_wall_head",
            () -> new BokoblinWallHeadBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion())
    );

    public static final RegistrySupplier<Item> BOKOBLIN_HEAD_ITEM = ITEMS.register(
            "bokoblin_head",
            () -> new BokoblinHeadItem(
                    BOKOBLIN_HEAD.get(),
                    BOKOBLIN_WALL_HEAD.get(),
                    new Item.Properties()
                            .stacksTo(1)
                            .arch$tab(TabRegistry.HYRULE_TERRORS_TAB)
                            .rarity(Rarity.RARE)
            )
    );

    public static void register() {
        BLOCKS.register();
        ITEMS.register();
    }
}
