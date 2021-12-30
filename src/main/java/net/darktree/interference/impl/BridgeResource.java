package net.darktree.interference.impl;

import com.google.gson.JsonElement;
import net.minecraft.resource.Resource;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class BridgeResource implements Resource {

	private final Identifier id;
	private final JsonElement json;

	public BridgeResource(Identifier id, JsonElement json) {
		this.id = id;
		this.json = json;
	}

	public JsonElement getJSON() {
		return this.json;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}

	@Override
	public InputStream getInputStream() {
		return null;
	}

	@Override
	public boolean hasMetadata() {
		return false;
	}

	@Nullable
	@Override
	public <T> T getMetadata(ResourceMetadataReader<T> metaReader) {
		return null;
	}

	@Override
	public String getResourcePackName() {
		return "Interference API - Bridge Resource";
	}

	@Override
	public void close() throws IOException {

	}

}
