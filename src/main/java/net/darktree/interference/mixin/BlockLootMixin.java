package net.darktree.interference.mixin;

import net.darktree.interference.LootInjector;
import net.darktree.interference.api.DefaultLoot;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(AbstractBlock.class)
abstract public class BlockLootMixin {

	@Inject(method="getDroppedStacks", at=@At(value="RETURN", ordinal=1), cancellable=true, locals=LocalCapture.CAPTURE_FAILHARD)
	private void onDefaultLoot(BlockState state, LootContextParameterSet.Builder builder, CallbackInfoReturnable<List> info, RegistryKey key, LootContextParameterSet set, ServerWorld serverWorld, LootTable lootTable) {
		if (lootTable == LootTable.EMPTY) {
			DefaultLoot loot = LootInjector.getDefaultLoot(state);

			if (loot != null) {
				info.setReturnValue(loot.getDefaultStacks(state, builder, key.getValue(), set, serverWorld, lootTable));
			}
		}
	}

}
