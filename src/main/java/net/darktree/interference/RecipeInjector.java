package net.darktree.interference;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.darktree.interference.impl.LockableSet;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;

public class RecipeInjector {

	private static final LockableSet<Pair<Identifier, JsonElement>> recipes = new LockableSet<>();

	public static void inject(Identifier id, JsonElement json) {
		recipes.add(Pair.of(id, json));
	}

	@ApiStatus.Internal
	public static void consume( Consumer<Pair<Identifier, JsonElement>> consumer ) {
		recipes.consume(consumer);
	}

}
