package net.darktree.interference.impl;

import net.darktree.interference.api.LookAtEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Objects;

public class LookAtTickHandle {

	private static final HashMap<PlayerEntity, BlockPoint> blocks = new HashMap<>();

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register( LookAtTickHandle::execute );
	}

	private static void execute(MinecraftServer server) {
		for(PlayerEntity player : server.getPlayerManager().getPlayerList()) {

			if( player.world != null ) {
				HitResult hit = player.raycast(128, 0.0f, false);

				if( hit.getType() == HitResult.Type.BLOCK ) {

					BlockHitResult blockHit = (BlockHitResult) hit;
					BlockPoint previous = blocks.get(player);
					BlockPoint current = BlockPoint.of(player.world, blockHit.getBlockPos());

					if( !current.equals(previous) ) {

						if( current.block instanceof LookAtEvent handle ) {
							handle.onLookAtStart(current.cached, current.world, current.pos, player, blockHit);
						}

						notifyPrevious(previous, player);
						blocks.put(player, current);

					}else{

						if( current.block instanceof LookAtEvent handle ) {
							handle.onLookAtTick(current.cached, current.world, current.pos, player, blockHit);
						}

					}
				}else{

					notifyPrevious(blocks.get(player), player);
					blocks.put(player, null);

				}

			}

		}
	}

	private static void notifyPrevious(BlockPoint previous, PlayerEntity player) {
		if( previous != null && previous.block instanceof LookAtEvent handle ) {
			handle.onLookAtStop(previous.query(), previous.world, previous.pos, player);
		}
	}

	static class BlockPoint {

		public BlockPos pos;
		public Block block;
		public World world;
		public BlockState cached;

		BlockPoint(BlockPos pos, Block block, World world, BlockState state) {
			this.pos = pos;
			this.block = block;
			this.world = world;
			this.cached = state;
		}

		@Override
		public boolean equals(Object o) {
			if( this == o ) return true;
			if( !(o instanceof BlockPoint that) ) return false;
			return pos.equals(that.pos) && block.equals(that.block) && world.equals(that.world);
		}

		@Override
		public int hashCode() {
			return Objects.hash(pos, block, world);
		}

		public BlockState query() {
			return this.world.getBlockState(this.pos);
		}

		public static BlockPoint of(World world, BlockPos pos) {
			BlockState state = world.getBlockState(pos);
			return new BlockPoint(pos, state.getBlock(), world, state);
		}

	}

}
