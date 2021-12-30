package net.darktree.interference;

import net.darktree.interference.api.DefaultLoot;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class LootInjector {

	private static final Map<Identifier, DefaultLoot> defaultLootMap = new HashMap<>();

	public static void inject(Identifier id, DefaultLoot defaultLoot) {
		defaultLootMap.put(new Identifier(id.getNamespace(), "blocks/" + id.getPath()), defaultLoot);
	}

	@Nullable
	public static DefaultLoot getDefault(BlockState state, Identifier identifier) {
		if( state.getBlock() instanceof DefaultLoot loot ) {
			return loot;
		}

		return defaultLootMap.get(identifier);
	}
}
