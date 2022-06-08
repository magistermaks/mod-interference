package net.darktree.interference.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Particle class meant to help in creation of particles that
 * render special geometry not just billboard sprites.
 * Implement the render method and create new vertex consumer
 * in render layer of your choosing using {@code VertexConsumer buffer = immediate.getBuffer(RENDER_LAYER);}
 */
@Environment(EnvType.CLIENT)
public abstract class RenderedParticle extends Particle {

	protected final int seed;

	protected RenderedParticle(ClientWorld world, double x, double y, double z) {
		super(world, x, y, z);
		this.seed = world.random.nextInt();
	}

	public RenderedParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		super(world, x, y, z, velocityX, velocityY, velocityZ);
		this.seed = world.random.nextInt();
	}

	public abstract void render(VertexConsumerProvider.Immediate immediate, MatrixStack matrices, float delta);

	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float delta) {
		VertexConsumerProvider.Immediate immediate = RenderHelper.getImmediate();

		MatrixStack matrices = new MatrixStack();
		Vec3d origin = camera.getPos();

		double x = MathHelper.lerp(delta, this.prevPosX, this.x) - origin.getX();
		double y = MathHelper.lerp(delta, this.prevPosY, this.y) - origin.getY();
		double z = MathHelper.lerp(delta, this.prevPosZ, this.z) - origin.getZ();
		matrices.translate(x, y, z);

		render(immediate, matrices, delta);
		immediate.draw();
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.CUSTOM;
	}

}
