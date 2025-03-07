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
    public int bokoblinHealth = 18;
    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int bokoblinAttackDamage = 3;
    @ConfigEntry.Category("bokoblin")
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int bokoblinSpawnWeight = 10;

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
    public int chuchuSpawnWeight = 10;

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
    public int keeseSpawnWeight = 10;

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
    public int lizalfosSpawnWeight = 10;
}
