# Changelog

## 2.3.0 - Breath of the Wild Update

### New
- **White (Ice) Chuchu**: spawns in snowy and icy biomes, freezes on touch, and bursts into frost on death. Drops **White Chuchu Jelly**, which you can right-click for a freezing frost burst.
- **Elemental Chuchus**: Blue Chuchus now turn Red when set on fire, White when frozen, and Yellow when struck by lightning. Fire melts White Chuchus back to Blue.
- **Fire, Ice and Electric Keese**, each with an elemental bite and a death burst.
- **Red Chuchus now spawn naturally** in deserts, badlands and savannas.
- **Master Sword Sword Beam**: at full health, right-click to fire a beam of light (configurable).
- **Paraglider**: hold right-click while falling to glide, with no fall damage.
- **8 Elixirs** (Hearty, Mighty, Tough, Hasty, Sneaky, Fireproof, Electro, Chilly), brewed from Chuchu jelly and monster parts.
- New effects: **Shock Resistance** and **Frost Resistance**. A raised Hylian Shield also protects its bearer from the lightning it calls down.
- **Blue and Red Rupees**, convertible with Green Rupees at a crafting table.
- **Wandering Trader overhaul**: buys monster parts for Rupees, sells elixirs, and rarely offers the Paraglider, Hylian Shield and Master Sword.
- **Legendary chest loot**: the Master Sword in Ancient Cities, the Hylian Shield in Strongholds, the Paraglider in Stronghold libraries and Woodland Mansions, and Rupees and elixirs in dungeons and temples (can be disabled in the config).
- Bokoblins now spawn carrying crude weapons.
- 8 new advancements.

### Fixed
- **Weapon bonus damage never applied.** The Master Sword's bonus vs evil, the Silver Longsword's wet bonus, the Knight's Claymore set bonus and the Boulder Breaker's armor-crush bonus were swallowed by vanilla's post-hit invulnerability window. They now work.
- **Enchanting**: the Master Sword and Lizal Spear couldn't get sword enchantments (Sharpness, Looting, Sweeping Edge…); the Hylian Shield couldn't get Unbreaking or Mending; the Hero armor set couldn't get any armor enchantments. All fixed.
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
- Low-health Lizalfos repathed every tick (a server lag source).
- Bokoblin horn-blowers forgot their role after a world reload.
- Bokoblin Arm sent an animation packet every tick while held.
- NeoForge: mobs were registered for spawning twice (once ignoring the config), spawn rules and AzureLib item identities were only registered client-side (broke dedicated servers), and renderers were registered twice.
- Armor materials were registered in the `minecraft:` namespace.
- Tooltips referenced a client-only class from common code (a dedicated-server crash risk).

### Changed
- Zora Chestplate recipe uses a Nautilus Shell instead of a Netherite Ingot (it gives iron-tier protection).
- Armor enchantability tuned per set (Hero/Evil Spirit 15, Zora/Barbarian 12, Knight 10).
- Armor repair materials: Hero (Leather/Green Wool), Knight (Steel), Evil Spirit (Steel/Red Jelly), Barbarian (Bokoblin Fang/Horn).
- Updated to AzureLib 3.2.8, Fabric API 0.116.18, NeoForge 21.1.266, Architectury 13.0.11, Mod Menu 11.0.5.
- License clarified as All Rights Reserved.
