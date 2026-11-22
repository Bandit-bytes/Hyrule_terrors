package net.bandit.hyrule_terrors;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.List;

@Config(name = HyruleTerrorsMod.MOD_ID)
public class HyruleTerrorsConfig implements ConfigData {

    // Bokoblin
    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int bokoblinHealth = 18;

    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int bokoblinAttackDamage = 3;

    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int bokoblinSpawnWeight = 5;

    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    public double BokoblinMovementSpeed = 0.3;

    // Chuchu
    @ConfigEntry.Category("chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int chuchuHealth = 10;

    @ConfigEntry.Category("chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int chuchuAttackDamage = 2;

    @ConfigEntry.Category("chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int chuchuSpawnWeight = 5;

    // Chuchured
    @ConfigEntry.Category("red_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int chuchuRedHealth = 10;

    @ConfigEntry.Category("red_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int chuchuRedAttackDamage = 2;

    @ConfigEntry.Category("red_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int chuchuRedSpawnWeight = 3;

    // White (Ice) Chuchu
    @ConfigEntry.Category("white_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int chuchuWhiteHealth = 12;

    @ConfigEntry.Category("white_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int chuchuWhiteAttackDamage = 2;

    @ConfigEntry.Category("white_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int chuchuWhiteSpawnWeight = 4;

    // Yellow Chuchu
    @ConfigEntry.Category("yellow_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int chuchuYellowHealth = 10;

    @ConfigEntry.Category("yellow_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int chuchuYellowAttackDamage = 2;

    @ConfigEntry.Category("yellow_chuchu")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int chuchuYellowSpawnWeight = 3;

    // Keese
    @ConfigEntry.Category("keese")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int keeseHealth = 10;

    @ConfigEntry.Category("keese")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int keeseAttackDamage = 2;

    @ConfigEntry.Category("keese")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int keeseSpawnWeight = 5;

    @ConfigEntry.Category("keese")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int elementalKeeseSpawnWeight = 3;

    // Lizalfos
    @ConfigEntry.Category("lizalfos")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int lizalfosHealth = 20;

    @ConfigEntry.Category("lizalfos")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int lizalfosAttackDamage = 4;

    @ConfigEntry.Category("lizalfos")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int lizalfosSpawnWeight = 5;

    @ConfigEntry.Category("lizalfos")
    @ConfigEntry.Gui.NoTooltip
    public double lizalfosMovementSpeed = 0.3;

    // Stalmoblin
    @ConfigEntry.Category("stalmoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int stalmoblinHealth = 30;

    @ConfigEntry.Category("stalmoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int stalmoblinAttackDamage = 6;

    @ConfigEntry.Category("stalmoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int stalmoblinSpawnWeight = 4;

    @ConfigEntry.Category("stalmoblin")
    @ConfigEntry.Gui.NoTooltip
    public double stalmoblinMovementSpeed = 0.25;

    @ConfigEntry.Category("stalmoblin")
    @ConfigEntry.Gui.NoTooltip
    public boolean stalmoblinBurnsInDaylight = true;

    // Master Sword
    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public boolean masterSwordEnable = true;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public boolean masterSwordAwakenInNetherOrEnd = true;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public boolean masterSwordAwakenNearSculk = true;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public boolean masterSwordAwakenNearBosses = true;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public boolean masterSwordAwakenNearEvilMobsTag = true;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
    public int masterSwordCheckRadius = 16;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
    public int masterSwordSculkScanRadius = 10;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 60)
    public int masterSwordCheckIntervalTicks = 10; // every 0.5s

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public float masterSwordBonusDamageVsEvil = 6.0f;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public double masterSwordKnockbackResistBonus = 0.25D;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public List<String> masterSwordExtraBossEntityIds = java.util.List.of();

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public boolean masterSwordBeamEnable = true;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    public float masterSwordBeamDamage = 8.0f;

    @ConfigEntry.Category("master_sword")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 5, max = 200)
    public int masterSwordBeamCooldownTicks = 20;

    // Loot
    @ConfigEntry.Category("loot")
    @ConfigEntry.Gui.Tooltip
    public boolean addLegendaryChestLoot = true;

}
