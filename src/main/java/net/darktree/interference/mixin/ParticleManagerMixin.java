package net.darktree.interference.mixin;

import net.darktree.interference.render.ParticleHelper;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

	@Inject(method="loadTextureList", at=@At("HEAD"), cancellable=true)
	public void loadTextureList(ResourceManager resourceManager, Identifier id, Map<Identifier, List<Identifier>> result, CallbackInfo ci) {
		List<Identifier> sprites = ParticleHelper.getSpritesAndForget(id);

		if(sprites != null) {
			result.put(id, sprites);
			ci.cancel();
		}
	}

}
