package net.darktree.interference.mixin;

import net.darktree.interference.api.AxeScrapeable;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

	@ModifyVariable(method="useOnBlock", index=10, at=@At(value="INVOKE", shift=At.Shift.BEFORE, ordinal=3, target="Ljava/util/Optional;isPresent()Z"))
	public Optional<BlockState> useOnBlock(Optional<BlockState> optional4, ItemUsageContext context) {
		if( !optional4.isPresent() ) {
			World world = context.getWorld();
			BlockPos pos = context.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if (state.getBlock() instanceof AxeScrapeable scrapeable) {
				return scrapeable.getScrapedState(world, pos, state, context.getPlayer());
			}
		}

		return optional4;
	}

}
