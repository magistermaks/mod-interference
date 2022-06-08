package net.darktree.interference;

import net.darktree.interference.impl.LootTableLoadingHandle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;

public class Interference implements ModInitializer {

	@ApiStatus.Experimental
	@ApiStatus.Internal
	public static <T> T getClient(T object) {
		return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT ? object : null;
	}

	@Override
	public void onInitialize() {
		LootTableLoadingHandle.register();
	}

}
