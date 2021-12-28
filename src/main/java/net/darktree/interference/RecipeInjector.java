package net.darktree.interference;

import com.google.gson.JsonElement;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class RecipeInjector {

	private static final Map<Identifier, JsonElement> recipes = new HashMap<>();
	private static boolean mutable = true;

	public static void inject(Identifier id, JsonElement jsonElement) {
		if( mutable ) {
			recipes.put(id, jsonElement);
		}else{
			throw new RuntimeException("Unable to add new message after the consumer has run!");
		}
	}

	public static void consume( BiConsumer<Identifier, JsonElement> consumer ) {
		mutable = false;
		recipes.forEach(consumer);
	}

}
