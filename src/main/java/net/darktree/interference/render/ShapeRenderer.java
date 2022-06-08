package net.darktree.interference.render;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class ShapeRenderer {

	public static final Segment[] NO_OFFSET = {new Segment(0, 0)};

	public static void renderPrism(Matrix4f matrix, VertexConsumer buffer, int count, float height, float radius, float angle, float r, float g, float b, float a, float sx, float sz, float ox, float oy, float oz) {
		float step = (float) (Math.PI * 2) / count;

		float x = (float) (radius * Math.sin(angle));
		float z = (float) (radius * Math.cos(angle));

		while(count > 0) {
			count --;

			buffer.vertex(matrix, x + ox, height + oy, z + oz).color(r, g, b, a).next();
			buffer.vertex(matrix, x + sx, oy, z + sz).color(r, g, b, a).next();

			angle += step;
			x = (float) (radius * Math.sin(angle));
			z = (float) (radius * Math.cos(angle));

			buffer.vertex(matrix, x + sx, oy, z + sz).color(r, g, b, a).next();
			buffer.vertex(matrix, x + ox, height + oy, z + oz).color(r, g, b, a).next();
		}
	}

	public static void renderPrism(Matrix4f matrix, VertexConsumer buffer, int count, float height, float radius, float angle, float r, float g, float b, float a, Segment[] segments) {
		float step = height / segments.length;

		float x = 0, y = 0;

		for(int i = 0; i < segments.length; i ++) {
			renderPrism(matrix, buffer, count, step, radius, angle, r, g, b, a, x, y, segments[i].ox, i * step, segments[i].oz);
			x = segments[i].ox;
			y = segments[i].oz;
		}
	}

	public static void renderPrismAlong(MatrixStack matrices, VertexConsumer buffer, int count, float radius, float angle, float r, float g, float b, float a, float x1, float y1, float z1, float x2, float y2, float z2, Segment[] segments) {
		matrices.push();
		float length = RenderHelper.lookAlong(matrices, x1, y1, z1, x2, y2, z2);
		renderPrism(matrices.peek().getPositionMatrix(), buffer, count, length, radius, angle, r, g, b, a, segments);
		matrices.pop();
	}

	public static final class Segment {
		public float ox;
		public float oz;

		public Segment(float ox, float oz) {
			this.ox = ox;
			this.oz = oz;
		}
	}

}