package net.darktree.interference.api;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.List;

public interface DefaultLoot {

	List<ItemStack> getDefaultStacks(BlockState state, LootContext.Builder builder, Identifier identifier, LootContext lootContext, ServerWorld serverWorld, LootTable lootTable);

}
