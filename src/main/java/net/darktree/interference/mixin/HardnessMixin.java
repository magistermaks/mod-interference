package net.darktree.interference.mixin;

import net.darktree.interference.api.MutableHardness;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class HardnessMixin {

	@Inject(at = @At("HEAD"), method = "getHardness(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F", cancellable = true)
	void getHardness(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
		BlockState state = world.getBlockState(pos);

		if( state.getBlock() instanceof MutableHardness hardness ) {
			info.setReturnValue( hardness.getHardness(state, world, pos) );
		}
	}

}
