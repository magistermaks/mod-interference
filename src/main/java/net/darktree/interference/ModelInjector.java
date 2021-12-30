package net.darktree.interference;

import com.google.gson.JsonElement;
import net.darktree.interference.mixin.JsonModelAccessor;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModelInjector {

	private static final Map<Identifier, JsonElement> blockStates = new HashMap<>();
	private static final Map<Identifier, JsonElement> models = new HashMap<>();

	public static void injectModel(Identifier id, JsonElement json) {
		models.put(new Identifier(id.getNamespace(), "models/" + id.getPath() + ".json"), json);

		JsonUnbakedModel model = JsonModelAccessor.getGson().getAdapter(JsonUnbakedModel.class).fromJsonTree(json);

		ModelLoadingRegistry.INSTANCE.registerResourceProvider(manager -> (identifier, context) -> {
			if( identifier.equals(id) ) {
				return model;
			} else {
				return null;
			}
		});
	}

	public static void injectBlockState(Identifier id, JsonElement json) {
		blockStates.put(new Identifier(id.getNamespace(), "blockstates/" + id.getPath() + ".json"), json);
	}

	public static JsonElement getBlockState(Identifier id) {
		return blockStates.get(id);
	}

	public static JsonElement getModel(Identifier id) {
		return models.get(id);
	}

}
