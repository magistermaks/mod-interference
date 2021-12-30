package net.darktree.interference;

import com.google.gson.JsonObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collections;

public class Interference implements ModInitializer, ClientModInitializer {

	private Identifier id(String id) {
		return new Identifier("interference", id);
	}

	@Override
	public void onInitialize() {
		MessageInjector.supply("SSdtIHRoZSBtYW4gd2hvIGFycmFuZ2VzIHRoZSBibG9ja3Mh");
		MessageInjector.supply("UGlyYWN5IGlzIGFsbCBhYm91dCBicmFuZGluZyE=");
		MessageInjector.supply("QW5kIHdoYXQgY2FuIHlvdSBkbywgbXkgZWZmZW1pbmF0ZSBmZWxsb3c/");
		MessageInjector.supply("Q2hlY2sgb3V0IFNlcXVlbnNhIFByb2dyYW1taW5nIExhbmd1YWdlIQ==");
		MessageInjector.supply("WW91IGtub3cgdGhlIHJ1bGVzIGFuZCBzbyBkbyBJIQ==");
		MessageInjector.supply("Q2hlY2sgb3V0IERhc2hMb2FkZXIh");

		Block b1 = Registry.register(Registry.BLOCK, id("test"), new Block(AbstractBlock.Settings.of(Material.METAL)));

		LootInjector.inject(id("test"), ((state, builder, identifier, lootContext, serverWorld, lootTable) -> {
			return Collections.singletonList(new ItemStack(Items.DIRT));
		}) );

	}


	@Override
	public void onInitializeClient() {

		{
			JsonObject json = new JsonObject();
			JsonObject variants = new JsonObject();
			JsonObject variant = new JsonObject();

			json.add("variants", variants);
			variants.add("", variant);
			variant.addProperty("model", "interference:block/test_glass");

			ModelInjector.injectBlockState(id("test"), json);
		}

		{
			JsonObject json = new JsonObject();
			JsonObject textures = new JsonObject();

			json.addProperty("parent", "block/cube_all");
			json.add("textures", textures);
			textures.addProperty("all", "minecraft:block/glass");

			ModelInjector.injectModel(id("block/test_glass"), json);
		}

	}

}
