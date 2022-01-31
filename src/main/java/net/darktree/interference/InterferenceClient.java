package net.darktree.interference;

import net.darktree.interference.impl.LookAtTickHandle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class InterferenceClient implements ClientModInitializer {

	private static LookAtTickHandle.BlockPoint client = null;

	@Override
	public void onInitializeClient() {
		MessageInjector.inject("SSdtIHRoZSBtYW4gd2hvIGFycmFuZ2VzIHRoZSBibG9ja3Mh");
		MessageInjector.inject("UGlyYWN5IGlzIGFsbCBhYm91dCBicmFuZGluZyE=");
		MessageInjector.inject("QW5kIHdoYXQgY2FuIHlvdSBkbywgbXkgZWZmZW1pbmF0ZSBmZWxsb3c/");
		MessageInjector.inject("Q2hlY2sgb3V0IFNlcXVlbnNhIFByb2dyYW1taW5nIExhbmd1YWdlIQ==");
		MessageInjector.inject("WW91IGtub3cgdGhlIHJ1bGVzIGFuZCBzbyBkbyBJIQ==");
		MessageInjector.inject("Q2hlY2sgb3V0IERhc2hMb2FkZXIh");

		ClientTickEvents.END_WORLD_TICK.register(world -> {
			if(MinecraftClient.getInstance().player != null) {
				LookAtTickHandle.raytrace(MinecraftClient.getInstance().player, client, point -> client = point);
			}
		});
	}

}
