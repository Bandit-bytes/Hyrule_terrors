package net.bandit.hyrule_terrors;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = HyruleTerrorsMod.MOD_ID)
public class HyruleTerrorsConfig implements ConfigData{

    // Bokoblin
    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int bokoblinHealth = 10;
    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int bokoblinAttackDamage = 2;
    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int bokoblinSpawnWeight = 10;
}
