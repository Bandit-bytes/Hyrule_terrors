package net.bandit.hyrule_terrors.item.armor.sets;

import net.bandit.hyrule_terrors.item.armor.ZeldaArmorMaterials;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HeroArmorItem extends ZeldaSetArmorItem {

    public HeroArmorItem(Type type, Properties properties) {
        super(ZeldaArmorMaterials.HERO_ARMOR, type, properties, "hero_armor");
    }

    @Override
    protected void applySetBonus(Player player) {
        // Hero's Resolve: only while at full health, so it drops off quickly when hurt.
        if (player.getHealth() >= player.getMaxHealth()) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, 0, true, false, true));
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.is(Items.LEATHER) || repair.is(Items.GREEN_WOOL);
    }
}
