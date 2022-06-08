package net.darktree.interference.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class ModelHelper {

	// TODO this is cursed, fix it

	/**
	 * Shorthand for getting the baked model identified by the given ID
	 */
	public static BakedModel getModel(Identifier id) {
		return BakedModelManagerHelper.getModel(MinecraftClient.getInstance().getBakedModelManager(), id);
	}

	/**
	 * Request multiples models to be loaded from JSON
	 */
	public static void loadModels(Identifier... ids) {
		getModelLoader().add(ids).submit();
	}

	/**
	 * Get the model loader for batching model requests
	 */
	public static ModelLoader getModelLoader() {
		return new ModelLoader();
	}

	static class ModelLoader {
		private final ArrayList<Identifier> models = new ArrayList<>();

		public ModelLoader add(Identifier... ids) {
			models.addAll(Arrays.asList(ids));
			return this;
		}

		@Environment(EnvType.CLIENT)
		public void submit() {
			ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
				for(Identifier id : this.models) out.accept(id);
			});
		}
	}

}
