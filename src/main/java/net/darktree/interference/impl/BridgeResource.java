package net.darktree.interference.impl;

import com.google.gson.JsonElement;
import net.minecraft.resource.Resource;

import java.io.InputStream;

public class BridgeResource extends Resource {

	private final JsonElement json;

	public BridgeResource(JsonElement json) {
		super(null, null);
		this.json = json;
	}

	public JsonElement getJSON() {
		return this.json;
	}

	@Override
	public InputStream getInputStream() {
		return null;
	}

	@Override
	public String getResourcePackName() {
		return "Interference API - Bridge Resource";
	}

}
