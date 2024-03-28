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

	@ModifyVariable(method="useOnBlock", index=5, at=@At(value="INVOKE_ASSIGN", target="Lnet/minecraft/item/AxeItem;tryStrip(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/block/BlockState;)Ljava/util/Optional;"))
	public Optional<BlockState> useOnBlock(Optional<BlockState> optional, ItemUsageContext context) {
		if (!optional.isPresent()) {
			World world = context.getWorld();
			BlockPos pos = context.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if (state.getBlock() instanceof AxeScrapeable scrapeable) {
				return scrapeable.getScrapedState(world, pos, state, context.getPlayer());
			}
		}

		return optional;
	}

}
