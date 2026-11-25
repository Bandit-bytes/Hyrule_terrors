# Changelog

## 2.4.1

### Fixed
- **Mod weapons could not hit anything with Better Combat installed.** The Master Sword, Silver Longsword, Knight's Claymore, Boulder Breaker, Lizal Spear, Lizalfos Horn Dagger, Bokoblin Arm and Spiked Moblin Club swung but never connected, because their Better Combat attack range was read as 0. Their ranges now match Better Combat's own defaults for each weapon type.

## 2.4.0 - Breath of the Wild Update

Brings the 1.20.1 version up to date with the 1.21.1 release.

### New
- **Stalmoblin**: a hunched skeletal Moblin that spawns at night in plains, forests, savannas and taigas. It is undead (Smite hurts it, healing harms it), burns in daylight (configurable), and counts as an evil mob for the Master Sword. Drops bones and Rupees.
- **Spiked Moblin Club**: a slow, heavy two-handed club. Hits knock targets back and can briefly slow them. Stalmoblins usually carry one. Repaired with planks; takes sword enchantments; includes Better Combat attributes.
- **Phantom armor set**: armor modeled after Ganon's phantom knights. Full set grants Strength and Resistance.
- **Hero armor set**.
- **White (Ice) Chuchu**: spawns in snowy and icy biomes, freezes on touch, and bursts into frost on death. Drops **White Chuchu Jelly**, which you can right-click for a freezing frost burst.
- **Yellow (Electric) Chuchu**, dropping **Yellow Chuchu Jelly**.
- **Elemental Chuchus**: Blue Chuchus now turn Red when set on fire, White when frozen, and Yellow when struck by lightning. Fire melts White Chuchus back to Blue.
- **Fire, Ice and Electric Keese**, each with an elemental bite and a death burst.
- **Red Chuchus now spawn naturally** in deserts, badlands and savannas.
- **Master Sword Sword Beam**: at full health, right-click to fire a beam of light (configurable).
- **8 Elixirs** (Hearty, Mighty, Tough, Hasty, Sneaky, Fireproof, Electro, Chilly), brewed from Chuchu jelly and monster parts.
- New effects: **Shock Resistance** and **Frost Resistance**. A raised Hylian Shield also protects its bearer from the lightning it calls down.
- **Blue and Red Rupees**, convertible with Green Rupees at a crafting table. Rupees are now currency instead of a food item.
- **Wandering Trader overhaul**: buys monster parts for Rupees, sells elixirs, and rarely offers the Hylian Shield and Master Sword.
- **Legendary chest loot**: the Master Sword in Ancient Cities, the Hylian Shield in Strongholds, and Rupees and elixirs in dungeons and temples (can be disabled in the config).
- Bokoblins now spawn carrying crude weapons.
- 10 advancements.

### Fixed
- **Weapon bonus damage never applied.** The Master Sword's bonus vs evil, the Silver Longsword's wet bonus, the Knight's Claymore set bonus and the Boulder Breaker's armor-crush bonus were swallowed by vanilla's post-hit invulnerability window. They now work.
- **Looting reduced drop chances** on every mob instead of increasing them.
- Red Chuchus killed in melee dropped nothing (no jelly, rupees or XP).
- Boulder Breaker mined stone quickly but dropped nothing.
- Lizal Spear lunge dealt its full damage twice.
- Blue Chuchu Jelly gave no hunger or saturation when eaten.
- Zora armor's swim boost compounded each tick and rubber-banded. It now uses Dolphin's Grace.
- Armor set bonuses ran from every piece every tick; they now refresh once per second.
- Hylian Shield constantly rewrote its item data while on cooldown, making it bob in your hand.
- Master Sword's knockback-resistance bonus could stick around after you dropped the sword.
- Hyrule monsters didn't despawn on Peaceful, didn't stop you sleeping, and dropped XP even when not killed by a player.
- Keese's bat AI fought its own attack AI; Keese now dive properly while hunting.
- Keese wings (and ears and tail) were invisible from some angles.
- Low-health Lizalfos repathed every tick (a server lag source).
- Bokoblin horn-blowers forgot their role after a world reload.
- Bokoblin Arm sent an animation packet every tick while held, and had a faint white outline around its fingers.
- Forge: AzureLib item identities were only registered client-side, which broke item animations on dedicated servers.
- Tooltips referenced a client-only class from common code (a dedicated-server crash risk).

### Changed
- Zora Chestplate recipe uses a Nautilus Shell instead of a Netherite Ingot (it gives iron-tier protection).
- Armor enchantability tuned per set (Hero/Evil Spirit 15, Zora/Barbarian/Phantom 12, Knight 10).
- Armor repair materials: Hero (Leather/Green Wool), Knight and Phantom (Steel), Evil Spirit (Steel/Red Jelly), Barbarian (Bokoblin Fang/Horn).
- Mob spawn biomes reworked; each mob has its own biome tag (`data/hyrule_terrors/tags/worldgen/biome/*_spawn_in.json`).
- **Now requires AzureLib 3.2.8 or newer.** Cloth Config is now declared as a required dependency on Forge (it was already needed).
- License clarified as All Rights Reserved.
