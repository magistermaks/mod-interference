package net.darktree.interference.api;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public interface MutableHardness {

	float getHardness(BlockState state, BlockView world, BlockPos pos);

}
