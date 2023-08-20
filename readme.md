# AI Tweak for High Energy Focus - Starsector Mod #

## Description ##

This mod changes the way AI controlled ships use the `High Energy Focus` system. AI will no longer
be tempted to activate HEF just because there's a fighter or missile in range of PD lasers or because
an enemy ship can barely be reached by a Graviton Beam. The precious HEF charges will be preserved
for big guns instead.

The mod is in early stages of development and the logic used by the new HEF AI is in no way perfect.
Best use case is of course the mighty Executor. With two linked Gigacannons and officer with System
Expertise almost every salvo will be spiced up by High Energy Focus!

Worst use case is when a ship relies on different energy weapons for shield damage and armor/hull damage.
Currently, the modified AI struggles to prioritise the different weapon groups accordingly, as observed
by CapnHector.

[A video demonstration recorded by CapnHector](https://www.youtube.com/watch?v=UF8Bg1KcT9w)

## Installation ##

Just download the zip and unpack it into the mods directory. There's no configuration required.
The mod affects all ships, there's no possibility to toggle it on/off for selected ships.

## Details ##

The logic used by the modified AI is Very simple. Triggering HEF is tied directly to firing the
largest energy weapon installed on a ship. This means a ship with at least one large weapon
will never activate HEF when firing just medium or small weapons.

Additionally:

* firing at fighters and missiles will not trigger HEF
* firing anti-armor weapons (weapons with High Explosive damage or with `USE_LESS_VS_SHIELDS` tag)
  will not trigger HEF

The simple solution is subject to change in next releases.

## Future ideas ##
