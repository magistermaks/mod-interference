package net.darktree.interference.api;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.List;

public interface DefaultLoot {

	/**
	 * Return a list of items that this block should drop if no JSON loot table is defined for it,
	 * use {@link DropsItself} to make block loot based on the value of {@link net.minecraft.block.AbstractBlock#asItem}
	 */
	List<ItemStack> getDefaultStacks(BlockState state, LootContext.Builder builder, Identifier identifier, LootContext lootContext, ServerWorld serverWorld, LootTable lootTable);

}
