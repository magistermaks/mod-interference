package net.darktree.interference;

import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.ArrayList;

public class Voxels {

	/**
	 * Helper for creating VoxelShapes.
	 */
	public static VoxelShape shape(float x1, float y1, float z1, float x2, float y2, float z2) {
		return VoxelShapes.cuboid(
				Math.min(x1, x2) / 16d,
				Math.min(y1, y2) / 16d,
				Math.min(z1, z2) / 16d,
				Math.max(x1, x2) / 16d,
				Math.max(y1, y2) / 16d,
				Math.max(z1, z2) / 16d
		);
	}

	/**
	 * Method used to start creation of a voxel shape that includes more than one cuboid.
	 */
	public static VoxelShapeBuilder box(float x1, float y1, float z1, float x2, float y2, float z2) {
		return new VoxelShapeBuilder().box(x1, y1, z1, x2, y2, z2);
	}

	public static class VoxelShapeBuilder {

		private final ArrayList<VoxelShape> shapes;

		private VoxelShapeBuilder() {
			shapes = new ArrayList<>();
		}

		/**
		 * Add a shape to the VoxelShape builder.
		 *
		 * @param shape the VoxelShape to add to the builder
		 */
		public VoxelShapeBuilder add(VoxelShape shape) {
			shapes.add(shape);
			return this;
		}

		/**
		 * Add a box to the VoxelShape builder.
		 */
		public VoxelShapeBuilder box(float x1, float y1, float z1, float x2, float y2, float z2) {
			return add(shape(x1, y1, z1, x2, y2, z2));
		}

		/**
		 * Compile the list of shapes into a singular VoxelShape
		 * using the provided boolean function.
		 *
		 * @return the combined VoxelShape
		 */
		public VoxelShape build(BooleanBiFunction function) {
			int size = shapes.size();

			if( size < 1 ) {
				throw new RuntimeException("Can't join an empty collection!");
			}

			VoxelShape shape = shapes.get(0);

			for (int i = 1; i < size; i++) {
				shape = VoxelShapes.combine(shape, shapes.get(i), function);
			}

			return shape;
		}

		/**
		 * Compile the list of shapes into a singular VoxelShape
		 * by adding together the shapes of all the boxes.
		 *
		 * @return the combined VoxelShape
		 */
		public VoxelShape build() {
			return build(BooleanBiFunction.OR);
		}

	}

}
