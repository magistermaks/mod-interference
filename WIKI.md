## Getting Started
To use Interference API in you project add it to your `build.gradle`:
```gradle
repositories{
    maven{
        allowInsecureProtocol=true
        url "http://darktree.net/maven"
    }
}

dependencies {
    modImplementation "net.darktree:interference:<version>"
    include "net.darktree:interference:<version>"
}
```

You can omit the `include` statement if you would like your users
to need to install the lib manually as a separate mod. For the newest
available version referer to `version` in [gradle.properties](./gradle.properties)

Also, remember to add `"interference": "<version>"` to your `fabric.mod.json`.

## Features

#### Default Loot
To set a default loot for a block make that block implement the `DefaultLoot` interface
(or `DropsItself`). If you don't want to do that, or you want to add default loot to something other
than a block, you can also use the `LootInjector` class:

```java
LootInjector.inject(new Identifier("modid", "blocks/your_block"), (state, builder, identifier, ctx, world, table) -> {
    return Collections.singletonList( new ItemStack(Items.DIRT, 2) )
});
```

**Additional features of the LootInjector:**  
The `LootInjector` class also allows for simpler addition of items into existing JSON-based loot tables,
using the `injectEntry` and `injectPool` methods. This can be used, for example, for adding items into chests in structures.

```java
// the "100" is the chance of the item appearing in the chest - 100%
LootInjector.injectEntry(LootTables.SPAWN_BONUS_CHEST, new ItemStack(Items.DIAMOND_BLOCK), 100);
```

#### Default Recipes
To inject a JsonElement as default recipe use the `RecipeInjector` class:  

```java
JsonObject recipe = ...;
RecipeInjector.inject(new Identifier("modid", "your_block"), recipe);
```

#### `RedstoneConnectable` interface
Can be used to make redstone wire connect to a block that implements it based on direction.  
Note that this is only useful for redstone consumers (e.g. lamps), as all redstone emitting blocks
automatically connect with redstone wire.

#### `MutableHardness` interface
Can be used to make the block implementing it have different hardness values based on position and block state.  
**Note:** The original hardness of a block can still be access using: `((HardnessAccessor) (Object) this).getStoredHardness()`,
where `this` is a block instance.

#### `LookAtEvent` interface
Implementing this interface on a block allows the block to react to players looking at them, with the 
3 new provided events `onLookAtStart` called once, `onLookAtTick` called per player per tick, and `onLookAtStop` called
once when a player stops looking at a block.

#### `FluidReplaceable` interface
Implementing this on a block makes fluids like lava and water break it when trying to spread to that position. This effect
can also be achieved in different ways (using materials) but this sometimes is the less clunky solution in the end.

#### `AxeScrapeable` interface
Allows a block that implements it to interact with axes, implementing the `getScrapedState` method allows the block to
turn into another block state after being clicked. The helper class `AxeScrapeHelper` can also be used to streamline playing the correct sounds
and spawning particles.

To emulate the given vanilla effect use:
- `AxeScrapeHelper.strip()` striping logs
- `AxeScrapeHelper.scrape()` scraping oxidation from copper blocks
- `AxeScrapeHelper.wax()` scraping wax from copper blocks

Example usage: make a block drop dirt and turn into stone with the scrape sound and particle.
```java
@Override
public Optional<BlockState> getScrapedState(World world, BlockPos pos, BlockState state, PlayerEntity entity) {
    Block.dropStack(world, pos, new ItemStack(Items.DIRT));
    return AxeScrapeHelper.scrape(world, pos, entity, Blocks.STONE.getDefaultState());
}
```