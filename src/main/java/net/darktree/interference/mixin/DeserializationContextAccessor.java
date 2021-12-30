package net.darktree.interference.mixin;

import com.google.gson.Gson;
import net.minecraft.client.render.model.json.ModelVariantMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelVariantMap.DeserializationContext.class)
public interface DeserializationContextAccessor {

	@Accessor("gson")
	Gson getGson();

}
