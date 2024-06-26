package net.darktree.test;

import net.darktree.interference.LootInjector;
import net.darktree.interference.render.ParticleHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;


public class TestFoo implements ModInitializer, ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("interference_tests");

	public static Identifier id(String name) {
		return Identifier.of("interference_tests", name);
	}

	public static final SimpleParticleType PARTICLE_1 = ParticleHelper.register(id("particle_1"), false);

	private static final Block BLOCK = new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK).mapColor(MapColor.BLACK).breakInstantly());

	@Override
	public void onInitialize() {
		LOGGER.info("Test mod loaded!");

		ItemStack stack = new ItemStack(Items.DIRT);
		stack.set(DataComponentTypes.LORE, new LoreComponent(List.of(Text.literal("Added via Interference Testmod").formatted(Formatting.GRAY))));
		LootInjector.injectEntry(LootTables.WHITE_SHEEP_ENTITY.getValue(), stack, 42);

		Registry.register(Registries.BLOCK, id("test_block"), BLOCK);
	}

	@Override
	public void onInitializeClient() {
		ParticleHelper.registerFactory(PARTICLE_1, TestParticle::new);
	}

}
