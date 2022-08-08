package net.darktree.test;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFoo implements ModInitializer, ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("interference_tests");

	public static Identifier id(String name) {
		return new Identifier("interference_tests", name);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Test mod has loaded!");
	}

	@Override
	public void onInitializeClient() {

	}

}
