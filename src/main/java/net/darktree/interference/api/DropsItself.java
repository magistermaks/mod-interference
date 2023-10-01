package net.darktree.interference.api;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.List;

public interface DropsItself extends DefaultLoot {

	@Override
	default List<ItemStack> getDefaultStacks(BlockState state, LootContextParameterSet.Builder builder, Identifier identifier, LootContextParameterSet set, ServerWorld serverWorld, LootTable lootTable) {
		return Collections.singletonList(new ItemStack(state.getBlock().asItem()));
	}

}
