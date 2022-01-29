package net.darktree.interference.mixin;

import net.darktree.interference.api.FluidReplaceable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FlowableFluid.class)
public abstract class FlowableFluidMixin {

	@Inject(method="canFill", at=@At(value="INVOKE_ASSIGN", target="Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"), cancellable=true, locals= LocalCapture.CAPTURE_FAILHARD)
	private void canFill(BlockView world, BlockPos pos, BlockState state, Fluid fluid, CallbackInfoReturnable<Boolean> info, Block block) {
		if( block instanceof FluidReplaceable) {
			info.setReturnValue(true);
		}
	}

}
