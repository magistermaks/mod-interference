package net.darktree.interference.mixin;

import com.google.gson.Gson;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(JsonUnbakedModel.class)
public interface JsonModelAccessor {

	@Accessor("GSON")
	static Gson getGson() {
		throw new RuntimeException("Unreachable statement!");
	}

}
