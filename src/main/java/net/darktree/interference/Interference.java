package net.darktree.interference;

import net.fabricmc.api.ModInitializer;

public class Interference implements ModInitializer {

	@Override
	public void onInitialize() {
		MessageInjector.supply("SSdtIHRoZSBtYW4gd2hvIGFycmFuZ2VzIHRoZSBibG9ja3Mh");
		MessageInjector.supply("UGlyYWN5IGlzIGFsbCBhYm91dCBicmFuZGluZyE=");
		MessageInjector.supply("QW5kIHdoYXQgY2FuIHlvdSBkbywgbXkgZWZmZW1pbmF0ZSBmZWxsb3c/");
		MessageInjector.supply("Q2hlY2sgb3V0IFNlcXVlbnNhIFByb2dyYW1taW5nIExhbmd1YWdlIQ==");
		MessageInjector.supply("WW91IGtub3cgdGhlIHJ1bGVzIGFuZCBzbyBkbyBJIQ==");
		MessageInjector.supply("Q2hlY2sgb3V0IERhc2hMb2FkZXIh");
	}

}
