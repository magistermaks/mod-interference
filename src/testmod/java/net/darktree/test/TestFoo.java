package net.darktree.test;

import com.google.gson.JsonObject;
import net.darktree.interference.ModelInjector;
import net.darktree.interference.render.ParticleHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestFoo implements ModInitializer, ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("interference_tests");

	public static Identifier id(String name) {
		return new Identifier("interference_tests", name);
	}

	public static final DefaultParticleType PARTICLE_1 = ParticleHelper.register(id("particle_1"), false);

	private static final Block BLOCK = new Block(FabricBlockSettings.of(Material.AMETHYST, MapColor.BLACK).breakInstantly());

	@Override
	public void onInitialize() {
		LOGGER.info("Test mod loaded!");

		Registry.register(Registry.BLOCK, id("test_block"), BLOCK);
	}

	@Override
	public void onInitializeClient() {
		ParticleHelper.registerFactory(PARTICLE_1, TestParticle::new);

		{
			JsonObject state = new JsonObject();
			JsonObject variants = new JsonObject();
			JsonObject variant = new JsonObject();
			state.add("variants", variants);
			variants.add("", variant);
			variant.addProperty("model", id("block/test_block.json").toString());

			ModelInjector.injectBlockState(id("test_block"), state);

			// this one should not apply - overridden by JSON files
			ModelInjector.injectBlockState(new Identifier("grass_block"), state);
		}

		{
			JsonObject model = new JsonObject();
			JsonObject textures = new JsonObject();
			model.addProperty("parent", "minecraft:block/cube_all");
			model.add("textures", textures);
			textures.addProperty("all", "minecraft:block/stone");

			ModelInjector.injectModel(id("block/test_block.json"), model);

			// this one should not apply - overridden by JSON files
			ModelInjector.injectBlockState(new Identifier("grass_block"), model);
		}
	}

}
