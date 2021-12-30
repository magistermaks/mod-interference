package net.darktree.interference;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.darktree.interference.impl.Lockable;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class RecipeInjector {

	private static final Lockable<Pair<Identifier, JsonElement>, Set<Pair<Identifier, JsonElement>>> recipes = new Lockable<>(new HashSet<>());

	public static void inject(Identifier id, JsonElement json) {
		recipes.get().add(Pair.of(id, json));
	}

	@ApiStatus.Internal
	public static void consume( Consumer<Pair<Identifier, JsonElement>> consumer ) {
		recipes.consume(consumer);
	}

}
