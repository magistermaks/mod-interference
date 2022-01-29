package net.darktree.interference.api;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public interface AxeScrapeable {
	Optional<BlockState> getScrapedState(World world, BlockPos pos, BlockState state, PlayerEntity entity);
}
