package net.darktree.interference;

import net.darktree.interference.impl.LookAtTickHandle;
import net.darktree.interference.impl.LootTableLoadingHandle;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;

public class Interference implements ModInitializer {

	private static final HashMap<PlayerEntity, LookAtTickHandle.BlockPoint> blocks = new HashMap<>();

	@Override
	public void onInitialize() {
		LootTableLoadingHandle.register();

		ServerTickEvents.END_WORLD_TICK.register(world -> {
			for(PlayerEntity player : world.getPlayers()) {
				LookAtTickHandle.raytrace(player, blocks.get(player), point -> blocks.put(player, point));
			}
		});
	}

}
