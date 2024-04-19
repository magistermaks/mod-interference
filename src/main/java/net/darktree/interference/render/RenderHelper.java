package net.darktree.interference.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

@Environment(EnvType.CLIENT)
public class RenderHelper {

	private static final Vector3f POSITIVE_Y = new Vector3f(0, 1, 0);
	private static final Random RANDOM = Random.create();
	private static final Quaternionf FACING = getDegreesQuaternion(POSITIVE_Y, 180);

	public static Quaternionf getDegreesQuaternion(Vector3f vector, float degrees) {
		return new Quaternionf().fromAxisAngleDeg(vector, degrees);
	}

	/**
	 * Render flat quad into given vertex consumer
	 */
	public static void renderFlatQuad(VertexConsumer vertexConsumer, MatrixStack.Entry matrix, BakedQuad quad, float brightness, int light, int overlay) {
		vertexConsumer.quad(matrix, quad, new float[]{brightness, brightness, brightness, brightness}, 1.0f, 1.0f, 1.0f, 1.0f, new int[]{light, light, light, light}, overlay, true);
	}

	/**
	 * Render flat quad list into given vertex consumer
	 */
	public static void renderFlatQuads(BlockRenderView world, BlockPos pos, int light, int overlay, boolean useWorldLight, MatrixStack matrices, VertexConsumer vertexConsumer, List<BakedQuad> quads) {
		for (BakedQuad bakedQuad : quads) {
			if (useWorldLight) {
				BlockPos source = isQuadExtended(bakedQuad.getVertexData(), bakedQuad.getFace()) ? pos.offset(bakedQuad.getFace()) : pos;
				light = WorldRenderer.getLightmapCoordinates(world, source.up());
			}

			float brightness = world.getBrightness(bakedQuad.getFace(), bakedQuad.hasShade());
			renderFlatQuad(vertexConsumer, matrices.peek(), bakedQuad, brightness, light, overlay);
		}
	}

	/**
	 * Render a textured quad facing the player,
	 * modifies the state of given matrices
	 */
	public static void renderCutoutBillboard(Identifier texture, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
		VertexConsumer buffer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(texture));
		renderBillboard(matrices, buffer, light);
	}

	private static void renderBillboard(MatrixStack matrices, VertexConsumer buffer, int light) {
		matrices.multiply(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation());
		matrices.multiply(FACING);

		MatrixStack.Entry entry = matrices.peek();
		Matrix4f position = entry.getPositionMatrix();
		Matrix3f normal = entry.getNormalMatrix();

		Vector3f vec3f = normal.transform(new Vector3f(0, 1, 0));
		float nx = vec3f.x(), ny = vec3f.y(), nz = vec3f.z();

		buffer.vertex(position, -0.5f, -0.5f,  0.0f).color(255, 255, 255, 255).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(nx, ny, nz).next();
		buffer.vertex(position,  0.5f, -0.5f,  0.0f).color(255, 255, 255, 255).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(nx, ny, nz).next();
		buffer.vertex(position,  0.5f,  0.5f,  0.0f).color(255, 255, 255, 255).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(nx, ny, nz).next();
		buffer.vertex(position, -0.5f,  0.5f,  0.0f).color(255, 255, 255, 255).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(nx, ny, nz).next();
	}

	/**
	 * Decide whether less important details should be rendered with accordance with graphics quality mode
	 */
	public static boolean shouldRenderDetails() {
		return MinecraftClient.isFancyGraphicsOrBetter();
	}

	/**
	 * Get buffer for immediate rendering
	 */
	public static VertexConsumerProvider.Immediate getImmediate() {
		return MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
	}

	/**
	 * Scales and transforms the matrices to point at (x2, y2, z2) from (x1, y1, z1)
	 * and returns the distance between those points.
	 * based on: stackoverflow.com/q/14337441
	 */
	public static float lookAlong(MatrixStack matrices, float x1, float y1, float z1, float x2, float y2, float z2) {
		x2 -= x1; y2 -= y1; z2 -= z1;

		matrices.translate(-x1, y1, -z1);

		// if straight down or up
		if(x2 == 0 && z2 == 0) {
			if(y2 > y1) {
				return y2 - y1;
			}else{
				matrices.multiply(new Quaternionf().rotateYXZ((float) Math.PI, 0, 0));
				return y1 - y2;
			}
		}

		// we need the distance anyway so fastInverseSqrt won't be helpful
		float distance = (float) Math.sqrt(x2*x2 + y2*y2 + z2*z2);
		float scale = 1.0f / distance;

		x2 *= scale;
		y2 *= scale;
		z2 *= scale;

		final Vector3f axis = new Vector3f(-z2, 0, x2);
		axis.normalize();
		Quaternionf q = new Quaternionf().fromAxisAngleRad(axis, (float) Math.acos(y2));
		matrices.multiply(q);

		return distance;
	}

	/**
	 * Render a baked model without attached block state
	 */
	public static void renderModel(BakedModel model, BlockRenderView view, BlockPos pos, VertexConsumer vertexConsumer, MatrixStack matrices, int overlay, int seed) {
		BlockPos.Mutable mutable = pos.mutableCopy();

		for (Direction direction : Direction.values()) {
			RANDOM.setSeed(seed);
			List<BakedQuad> list = model.getQuads(null, direction, RANDOM);

			if (!list.isEmpty()) {
				mutable.set(pos, direction);
				int j = WorldRenderer.getLightmapCoordinates(view, mutable);
				RenderHelper.renderFlatQuads(view, pos, j, overlay, false, matrices, vertexConsumer, list);
			}
		}

		RANDOM.setSeed(seed);
		List<BakedQuad> list2 = model.getQuads(null, null, RANDOM);

		if (!list2.isEmpty()) {
			RenderHelper.renderFlatQuads(view, pos, -1, overlay, true, matrices, vertexConsumer, list2);
		}
	}

	private static boolean isQuadExtended(int[] vertexData, Direction face) {
		float f =  32.0f, g =  32.0f, h =  32.0f;
		float i = -32.0F, j = -32.0F, k = -32.0F;

		for(int l = 0; l < 32; l += 8) {
			float m = Float.intBitsToFloat(vertexData[l]);
			float n = Float.intBitsToFloat(vertexData[l + 1]);
			float o = Float.intBitsToFloat(vertexData[l + 2]);
			f = Math.min(f, m);
			g = Math.min(g, n);
			h = Math.min(h, o);
			i = Math.max(i, m);
			j = Math.max(j, n);
			k = Math.max(k, o);
		}

		return switch (face) {
			case DOWN -> g == j && g < 1.0E-4F;
			case UP -> g == j && j > 0.9999F;
			case NORTH -> h == k && h < 1.0E-4F;
			case SOUTH -> h == k && k > 0.9999F;
			case WEST -> f == i && f < 1.0E-4F;
			case EAST -> f == i && i > 0.9999F;
		};
	}

}
