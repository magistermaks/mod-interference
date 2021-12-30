package net.darktree.interference;

import net.fabricmc.api.ModInitializer;

public class Interference implements ModInitializer {

	@Override
	public void onInitialize() {
		MessageInjector.inject("SSdtIHRoZSBtYW4gd2hvIGFycmFuZ2VzIHRoZSBibG9ja3Mh");
		MessageInjector.inject("UGlyYWN5IGlzIGFsbCBhYm91dCBicmFuZGluZyE=");
		MessageInjector.inject("QW5kIHdoYXQgY2FuIHlvdSBkbywgbXkgZWZmZW1pbmF0ZSBmZWxsb3c/");
		MessageInjector.inject("Q2hlY2sgb3V0IFNlcXVlbnNhIFByb2dyYW1taW5nIExhbmd1YWdlIQ==");
		MessageInjector.inject("WW91IGtub3cgdGhlIHJ1bGVzIGFuZCBzbyBkbyBJIQ==");
		MessageInjector.inject("Q2hlY2sgb3V0IERhc2hMb2FkZXIh");
	}

}
