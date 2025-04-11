package net.bandit.hyrule_terrors.item.weapon.geo_weapon;

import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import net.bandit.hyrule_terrors.item.armor.client.dispatcher.HyruleItemDispatcher;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;


import java.util.function.Supplier;

public class WeaponItem extends Item  {
    public final Supplier<AzItemRenderer> RENDERER;
    public final HyruleItemDispatcher dispatcher;

    public WeaponItem(Tier tier, Supplier<AzItemRenderer> HyruleWeaponRender, Properties properties) {
        super(properties);
        this.RENDERER = HyruleWeaponRender;
        this.dispatcher = new HyruleItemDispatcher();
    }

}
