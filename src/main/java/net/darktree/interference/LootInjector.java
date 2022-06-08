package net.darktree.interference;

import net.darktree.interference.api.DefaultLoot;
import net.darktree.interference.impl.LootTableLoadingHandle;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetNbtLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class LootInjector {

	private static final Map<Identifier, DefaultLoot> defaultLootMap = new HashMap<>();

	/**
	 * Used for defining a default block loot, this setting is overwritten
	 * by the {@link DefaultLoot} and {@link net.darktree.interference.api.DropsItself} interfaces
	 *
	 * @param id id of the block
	 * @param defaultLoot default loot provider
	 */
	public static void inject(Identifier id, DefaultLoot defaultLoot) {
		defaultLootMap.put(new Identifier(id.getNamespace(), "blocks/" + id.getPath()), defaultLoot);
	}

	/**
	 * Used for adding an item stack to a JSON defined loot table, this can be used, for example, for
	 * adding something to chests in vanilla structures.
	 *
	 * @param table id of the loot table, for vanilla loot tables see {@link LootTables}
	 * @param stack the stack to be added, can have NBT data
	 * @param chance the chance of that item appearing, from 0 being never to 100 being always
	 */
	public static void injectEntry(Identifier table, ItemStack stack, int chance) {
		FabricLootPoolBuilder pool = FabricLootPoolBuilder.builder()
				.rolls(ConstantLootNumberProvider.create(1))
				.withEntry(ItemEntry.builder(stack.getItem()).weight(chance).build())
				.withFunction(SetCountLootFunction.builder(ConstantLootNumberProvider.create(stack.getCount())).build());

		if( stack.getNbt() != null ) {
			pool.withFunction(SetNbtLootFunction.builder(stack.getNbt()).build());
		}

		if( chance < 100 ) {
			pool.withEntry(EmptyEntry.Serializer().weight(100 - chance).build());
		}

		injectPool(table, pool.build());
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
	public static DefaultLoot getDefaultLoot(BlockState state, Identifier identifier) {
		if( state.getBlock() instanceof DefaultLoot loot ) {
			return loot;
		}

		return defaultLootMap.get(identifier);
	}
}
