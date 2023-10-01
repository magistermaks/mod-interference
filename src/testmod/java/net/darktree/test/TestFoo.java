package net.darktree.test;

import net.darktree.interference.render.ParticleHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestFoo implements ModInitializer, ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("interference_tests");

	public static Identifier id(String name) {
		return new Identifier("interference_tests", name);
	}

	public static final DefaultParticleType PARTICLE_1 = ParticleHelper.register(id("particle_1"), false);

	private static final Block BLOCK = new Block(FabricBlockSettings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK).mapColor(MapColor.BLACK).breakInstantly());

	@Override
	public void onInitialize() {
		LOGGER.info("Test mod loaded!");

		Registry.register(Registries.BLOCK, id("test_block"), BLOCK);
	}

	@Override
	public void onInitializeClient() {
		ParticleHelper.registerFactory(PARTICLE_1, TestParticle::new);
	}

}
