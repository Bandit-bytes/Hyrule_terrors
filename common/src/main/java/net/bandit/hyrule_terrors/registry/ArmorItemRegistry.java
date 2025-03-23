package net.bandit.hyrule_terrors.registry;

import net.bandit.hyrule_terrors.HyruleTerrorsMod;
import net.bandit.hyrule_terrors.item.armor.sets.BarbarianArmorItem;
import net.bandit.hyrule_terrors.item.armor.sets.EvilSpiritArmorItem;
import net.bandit.hyrule_terrors.item.armor.sets.KnightArmorItem;
import net.bandit.hyrule_terrors.item.armor.sets.ZoraArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ArmorItemRegistry {
    public static void init() {
        Item.Properties defaultProps = new Item.Properties().durability(390).stacksTo(1).rarity(Rarity.RARE).arch$tab(TabRegistry.HYRULE_TERRORS_TAB);
        Item.Properties KnightProps = new Item.Properties().durability(440).stacksTo(1).rarity(Rarity.RARE).arch$tab(TabRegistry.HYRULE_TERRORS_TAB);
        Item.Properties EvilProps = new Item.Properties().durability(510).stacksTo(1).rarity(Rarity.EPIC).arch$tab(TabRegistry.HYRULE_TERRORS_TAB);

        // Zora Armor
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "zora_helmet", () -> new ZoraArmorItem(ArmorItem.Type.HELMET, defaultProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "zora_chestplate", () -> new ZoraArmorItem(ArmorItem.Type.CHESTPLATE, defaultProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "zora_leggings", () -> new ZoraArmorItem(ArmorItem.Type.LEGGINGS, defaultProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "zora_boots", () -> new ZoraArmorItem(ArmorItem.Type.BOOTS, defaultProps));

        //Barbarian Armor
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "barbarian_helmet", () -> new BarbarianArmorItem(ArmorItem.Type.HELMET, defaultProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "barbarian_chestplate", () -> new BarbarianArmorItem(ArmorItem.Type.CHESTPLATE, defaultProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "barbarian_leggings", () -> new BarbarianArmorItem(ArmorItem.Type.LEGGINGS, defaultProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "barbarian_boots", () -> new BarbarianArmorItem(ArmorItem.Type.BOOTS, defaultProps));

        // Knight Armor
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "knight_helmet", () -> new KnightArmorItem(ArmorItem.Type.HELMET, KnightProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "knight_chestplate", () -> new KnightArmorItem(ArmorItem.Type.CHESTPLATE, KnightProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "knight_leggings", () -> new KnightArmorItem(ArmorItem.Type.LEGGINGS, KnightProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "knight_boots", () -> new KnightArmorItem(ArmorItem.Type.BOOTS, KnightProps));

        // Evil Spirit Armor
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "evil_spirit_helmet", () -> new EvilSpiritArmorItem(ArmorItem.Type.HELMET, EvilProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "evil_spirit_chestplate", () -> new EvilSpiritArmorItem(ArmorItem.Type.CHESTPLATE, EvilProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "evil_spirit_leggings", () -> new EvilSpiritArmorItem(ArmorItem.Type.LEGGINGS, EvilProps));
        ArmorRegistry.registerItem(HyruleTerrorsMod.MOD_ID, "evil_spirit_boots", () -> new EvilSpiritArmorItem(ArmorItem.Type.BOOTS, EvilProps));
    }
}
