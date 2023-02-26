package net.darktree.interference;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RecipeInjector {

	private static final List<Pair<Identifier, JsonElement>> recipes = new ArrayList<>();

	public static void register(Identifier id, JsonElement json) {
		recipes.add(Pair.of(id, json));
	}

	@ApiStatus.Internal
	public static void consume(Consumer<Pair<Identifier, JsonElement>> consumer) {
		recipes.forEach(consumer);
	}

}
