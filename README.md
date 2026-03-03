# MapResetPlugin
---
## 🌟 Overview
This Plugin is a simple Map Reset plugin, intended to reset a PVP or Minigame Map by cloning a template while ignoring the Vanilla Clone limitations.

## ❓ Setup
**1:** Set up the Source (Template) from which the plugin will clone the map:
-> `/mineresetplugin setupsource <x> <y> <z> <x2> <y2> <z2>`

!! Make sure the first coordinates are *smaller* than the second coordinates.

---
**2:** Set up the Target Area to which the plugin will clone:
-> `/mineresetplugin setuptarget <x> <y> <z> <x2> <y2> <z2>`

!! Make sure the first coordinates are *smaller* than the second coordinates.

---
**3:** Set up extra flags for the Map Reset (Optional):

-> `/mineresetplugin resetdelay <time in s>`: The delay between each automatic reset.

-> `/mineresetplugin setworld`: Sets the world of the target and source areas to your current world.

-> `/mineresetplugin setannouncement <chat/subtitle/off>`: Sets the announcement type. Chat means a broadcast in the chat; subtitle means a smaller title visible on every player's screen.
