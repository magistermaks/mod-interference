package net.darktree.interference.impl;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class LootTableLoadingHandle {

	private static final Map<Identifier, LootPool> pools = new HashMap<>();

	public static void register() {
		LootTableEvents.MODIFY.register(LootTableLoadingHandle::execute);
	}

	public static void inject(Identifier table, LootPool pool) {
		pools.put(table, pool);
	}

	private static void execute(RegistryKey<LootTable> key, LootTable.Builder builder, LootTableSource source) {
		if (source.isBuiltin()) {
			LootPool pool = pools.get(key.getValue());

			if (pool != null) {
				builder.pool(pool);
			}
		}
	}
}
