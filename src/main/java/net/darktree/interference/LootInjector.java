package net.darktree.interference;

import com.google.common.collect.ImmutableList;
import net.darktree.interference.api.DefaultLoot;
import net.darktree.interference.impl.LootTableLoadingHandle;
import net.darktree.interference.mixin.SetComponentLootFunctionInvoker;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class LootInjector {

	/**
	 * Used for adding an item stack to a JSON defined loot table, this can be used, for example, for
	 * adding something to chests in vanilla structures.
	 *
	 * @param table id of the loot table, for vanilla loot tables see {@link LootTables}
	 * @param stack the stack to be added, can have NBT data
	 * @param chance the chance of that item appearing, from 0 being never to 100 being always
	 */
	public static void injectEntry(Identifier table, ItemStack stack, int chance) {
		LootPool.Builder builder = LootPool.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.with(ItemEntry.builder(stack.getItem()).weight(chance).build())
				.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(stack.getCount())));

		if (stack.getComponentChanges() != ComponentChanges.EMPTY) {
			builder.apply(SetComponentLootFunctionInvoker.invoke_SetComponentsLootFunction(Collections.emptyList(), stack.getComponentChanges()));
		}

		if (chance < 100) {
			builder.with(EmptyEntry.builder().weight(100 - chance).build());
		}

		injectPool(table, builder.build());
	}

	/**
	 * Used for adding a pool to a JSON defined loot table, this can be used, for example, for
	 * adding something to chests in vanilla structures.
	 * for simple injection of items see {@link LootInjector#injectEntry}
	 *
	 * @param table id of the loot table, for vanilla loot tables see {@link LootTables}
	 * @param pool the loot table pool to add
	 */
	public static void injectPool(Identifier table, LootPool pool) {
		LootTableLoadingHandle.inject(table, pool);
	}

	@Nullable
	@ApiStatus.Internal
	public static DefaultLoot getDefaultLoot(BlockState state) {
		if (state.getBlock() instanceof DefaultLoot loot) {
			return loot;
		}

		return null;
	}
}
