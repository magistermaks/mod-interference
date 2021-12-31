package net.darktree.interference.api;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

public interface RedstoneConnectable {

	boolean connectsTo(BlockState state, Direction direction);

}
