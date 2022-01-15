package net.darktree.interference.api;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface RedstoneConnectable {

	/**
	 * Return whether a redstone wire should connect to this block based on direction and state
	 */
	boolean connectsTo(BlockState state, Direction direction);

}
