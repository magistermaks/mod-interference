package net.darktree.interference;

import com.google.gson.JsonElement;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModelInjector {

	private static final Map<Identifier, JsonElement> blockStates = new HashMap<>();
	private static final Map<Identifier, JsonElement> models = new HashMap<>();

	/**
	 * Used for injecting a JsonElement as a default model
	 *
	 * @param id the id of the model to inject
	 * @param json model data as a JsonElement
	 */
	public static void injectModel(Identifier id, JsonElement json) {
		models.put(new Identifier(id.getNamespace(), "models/" + id.getPath() + ".json"), json);
	}

	/**
	 * Used for injecting a JsonElement as a default block state model
	 *
	 * @param id the id of the block state model to inject
	 * @param json model data as a JsonElement
	 */
	public static void injectBlockState(Identifier id, JsonElement json) {
		blockStates.put(new Identifier(id.getNamespace(), "blockstates/" + id.getPath() + ".json"), json);
	}

	@Nullable
	@ApiStatus.Internal
	public static JsonElement getDefaultBlockState(Identifier id) {
		return blockStates.get(id);
	}

	@Nullable
	@ApiStatus.Internal
	public static JsonElement getDefaultModel(Identifier id) {
		return models.get(id);
	}

}
