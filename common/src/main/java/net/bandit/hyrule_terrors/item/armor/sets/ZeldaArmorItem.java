package net.bandit.hyrule_terrors.item.armor.sets;

import mod.azure.azurelib.rewrite.render.armor.AzArmorRenderer;
import net.bandit.hyrule_terrors.registry.TabRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;


import java.util.function.Supplier;

public class ZeldaArmorItem extends ArmorItem {
    public final Supplier<AzArmorRenderer> RENDERER;

    public ZeldaArmorItem(Holder<ArmorMaterial> holder, Type type, Supplier<AzArmorRenderer> ZeldaArmorRenderer) {
        super(holder, type, new Item.Properties().arch$tab(TabRegistry.HYRULE_TERRORS_TAB));
        this.RENDERER = ZeldaArmorRenderer;
    }
}
