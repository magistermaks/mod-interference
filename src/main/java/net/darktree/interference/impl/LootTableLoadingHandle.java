package net.darktree.interference.impl;

import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class LootTableLoadingHandle {

	private static final Map<Identifier, LootPool> pools = new HashMap<>();

	public static void register() {
		LootTableLoadingCallback.EVENT.register(LootTableLoadingHandle::execute);
	}

	public static void inject(Identifier table, LootPool pool) {
		pools.put(table, pool);
	}

	private static void execute(ResourceManager resource, LootManager manager, Identifier id, FabricLootSupplierBuilder table, LootTableLoadingCallback.LootTableSetter setter) {
		LootPool pool = pools.get(id);

		if( pool != null ) {
			table.withPool(pool);
		}
	}
}
