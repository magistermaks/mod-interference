package net.darktree.interference.mixin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.darktree.interference.ModelInjector;
import net.darktree.interference.impl.BridgeResource;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelVariantMap;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {

	@Final
	@Shadow
	private ModelVariantMap.DeserializationContext variantMapDeserializationContext;

	@Shadow @Final private ResourceManager resourceManager;

	/**
	 * <b> getAllResources Redirect </b>
	 *
	 * <p> We redirect getAllResources to catch the exception it throws when no Resource is found.
	 * When that happens we check if there is a Default BlockState Model we want to inject with that name,
	 * if no ({@link ModelInjector#getDefaultBlockState} returned null) we rethrow the exception as if nothing happened,
	 * if yes we wrap the json data in a fake Resource object - {@link BridgeResource} for later processing in the lambda inject </p>
	 */
	@Redirect(method="loadModel", at=@At(value="INVOKE", target="Lnet/minecraft/resource/ResourceManager;getAllResources(Lnet/minecraft/util/Identifier;)Ljava/util/List;"))
	private List<Resource> redirectVariant(ResourceManager instance, Identifier identifier) {
		List<Resource> resources = instance.getAllResources(identifier);

		if (resources.isEmpty()) {
			JsonElement json = ModelInjector.getDefaultBlockState(identifier);

			if( json != null ) {
				return Collections.singletonList( new BridgeResource(json) );
			}
		}

		return resources;
	}

	/**
	 * <b> Lambda Injection </b>
	 *
	 * <p> Adds a special case for processing {@link BridgeResource} objects, when an object of that class is found
	 * the JsonElement stored within is deserialized using {@link #variantMapDeserializationContext} and returned as a pair </p>
	 */
	@Inject(method="method_4737", remap=false, at=@At("HEAD"), cancellable=true)
	private void injectVariant(Identifier identifier, Resource resource, CallbackInfoReturnable<Pair<String, ModelVariantMap>> info) {
		if( resource instanceof BridgeResource bridge ) {
			Gson gson = ((DeserializationContextAccessor) (Object) this.variantMapDeserializationContext).getGson();
			ModelVariantMap map = gson.getAdapter(ModelVariantMap.class).fromJsonTree(bridge.getJSON());
			info.setReturnValue( new Pair<>(bridge.getResourcePackName(), map) );
		}
	}

	/**
	 * <b> loadModelFromJson Injection </b>
	 *
	 * <p> Adds a special case for processing {@link BridgeResource} objects, when an object of that class is found
	 * the JsonElement stored within is deserialized and returned
	 */
	@Inject(method="loadModelFromJson", at=@At(value="INVOKE", target="Lnet/minecraft/resource/ResourceManager;openAsReader(Lnet/minecraft/util/Identifier;)Ljava/io/BufferedReader;", shift=At.Shift.BEFORE), cancellable=true)
	private void injectModel(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> info) {
		Identifier model = new Identifier(id.getNamespace(), "models/" + id.getPath() + ".json");
		JsonElement json = ModelInjector.getDefaultModel(model);

		if (json != null && !resourceManager.getResource(model).isPresent()) {
			info.setReturnValue(JsonModelAccessor.getGson().getAdapter(JsonUnbakedModel.class).fromJsonTree(json));
		}
	}

}
