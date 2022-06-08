package net.darktree.test;

import net.darktree.interference.render.RenderedParticle;
import net.darktree.interference.render.ShapeRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;

public class TestParticle extends RenderedParticle {

	public TestParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz) {
		super(world, x, y, z, vx, vy, vz);
	}

	@Override
	public void render(VertexConsumerProvider.Immediate immediate, MatrixStack matrices, float delta) {
		VertexConsumer buffer = immediate.getBuffer(RenderLayer.getLightning());
		ShapeRenderer.renderPrismAlong(matrices, buffer, 4, 0.1f, 0, 1.0f, 0.8f, 0.6f, 1.0f, 0f, 0f, 0f, 0f, 1f, 0f, ShapeRenderer.NO_OFFSET);
	}

}
