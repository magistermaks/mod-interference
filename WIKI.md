## Getting Started
To use Interference API in you project add it to your `build.gradle`:
```gradle
repositories{
    maven{
        allowInsecureProtocol=true
        url "http://maven.darktree.net"
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

#### Default BlockState Model
_This is referring to the thing usually created by placing JSON files in the blockstates/ 
directory._
To inject a JsonElement as default block state model use the `ModelInjector` class:  
This is safe to do from the common initializer.

```java
JsonObject model = ...;
ModelInjector.injectBlockState(new Identifier("modid", "your_block"), model);
```

#### Default Models
To inject a JsonElement as default block or item model use the `ModelInjector` class:  
This is safe to do from the common initializer.

```java
JsonObject model = ...;
ModelInjector.injectModel(new Identifier("modid", "block/your_block_model"), model);
```

#### Default Recipes
To inject a JsonElement as default recipe use the `RecipeInjector` class:  

```java
JsonObject recipe = ...;
RecipeInjector.inject(new Identifier("modid", "your_block"), recipe);
```

#### `RedstoneConnectable` interface
Can be used to make redstone wire connect to a block the implements it based on direction.  
Note that this is only useful for redstone consumers (eg. lamps), as all redstone emitting blocks
automatically connect with redstone wire

#### `MutableHardness` interface
Can be used to make the block implementing it has different hardness values based on position and block state.  
Note that the original hardness of a block can still be access using: `((HardnessAccessor) (Object) this).getStoredHardness()`,
where `this` is a block instance.