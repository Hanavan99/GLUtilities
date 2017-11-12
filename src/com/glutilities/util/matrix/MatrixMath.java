package com.glutilities.util.matrix;

import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;

/**
 * Performs more complex mathematical operations than defined in the matrix
 * classes.
 * 
 * @author Hanavan99
 *
 */
public class MatrixMath {

	/**
	 * Translates a matrix by a given vector.
	 * 
	 * @param src the matrix to translate
	 * @param translation the translation vector
	 * @return the translated matrix
	 */
	public static Matrix4f translate(Matrix4f src, Vertex3f translation) {
		return src.set(0, 3, src.get(0, 3) + translation.getX()).set(1, 3, src.get(1, 3) + translation.getY()).set(2, 3, src.get(2, 3) + translation.getZ());
	}

	/**
	 * Scales a matrix by a given vector.
	 * 
	 * @param src the matrix to scale
	 * @param scale the scale vector
	 * @return the scaled matrix
	 */
	public static Matrix4f scale(Matrix4f src, Vertex3f scale) {
		return src.set(0, 0, src.get(0, 0) * scale.getX()).set(1, 1, src.get(1, 1) * scale.getY()).set(2, 2, src.get(2, 2) * scale.getZ());
	}

	/**
	 * Rotates a matrix by a given angle, with the given rotation mask.
	 * 
	 * @param src the matrix to rotate
	 * @param angle the angle to rotate it, in radians
	 * @param axismask the mask used to determine the rotation axes. 0b100 = x,
	 *            0b010 = y, and 0b001 = z;
	 * @return the rotated matrix
	 */
	public static Matrix4f rotate(Matrix4f src, float angle, int axismask) {
		Vertex3f scale = new Vertex3f(src.get(0, 0), src.get(1, 1), src.get(2, 2));
		Matrix4f result = src;
		if ((axismask & 0b100) == 0b100) {
			result = result.set(1, 1, scale.getX() * (float) Math.cos(angle)).set(1, 2, scale.getX() * (float) -Math.sin(angle));
			result = result.set(2, 1, scale.getX() * (float) Math.sin(angle)).set(2, 2, scale.getX() * (float) Math.cos(angle));
		}
		if ((axismask & 0b010) == 0b010) {
			result = result.set(0, 0, scale.getY() * (float) Math.cos(angle)).set(0, 2, scale.getY() * (float) Math.sin(angle));
			result = result.set(2, 0, scale.getY() * (float) -Math.sin(angle)).set(2, 2, scale.getY() * (float) Math.cos(angle));
		}
		if ((axismask & 0b001) == 0b001) {
			result = result.set(0, 0, scale.getX() * (float) Math.cos(angle)).set(0, 1, scale.getX() * (float) -Math.sin(angle));
			result = result.set(1, 0, scale.getX() * (float) Math.sin(angle)).set(1, 1, scale.getX() * (float) Math.cos(angle));
		}
		return result;
	}

	/**
	 * Creates a perspective projection matrix.
	 * 
	 * @param fov the field-of-view of the camera
	 * @param aspect the aspect ratio of the screen
	 * @param near the near clipping pane
	 * @param far the far clipping pane
	 * @return the perspective projection matrix
	 */
	public static Matrix4f createPerspectiveMatrix(float fov, float aspect, float near, float far) {
		float t = (float) Math.tan(fov / 360f * Math.PI) * near;
		float b = -t;
		float r = t * aspect;
		float l = -r;
		float[] matrix = new float[16];
		matrix[0] = 2f * near / (r - l);
		matrix[2] = (r + l) / (r - l);
		matrix[5] = 2f * near / (t - b);
		matrix[6] = (t + b) / (t - b);
		matrix[10] = -(far + near) / (far - near);
		matrix[11] = -(2f * far * near) / (far - near);
		matrix[14] = -1;
		matrix[15] = 0;
		return new Matrix4f(matrix);
	}

	/**
	 * Creates an orthographic projection matrix.
	 * 
	 * @param l the left clipping pane
	 * @param r the right clipping pane
	 * @param b the bottom clipping pane
	 * @param t the top clipping pane
	 * @param n the near clipping pane
	 * @param f the far clipping pane
	 * @return the orthographic projection matrix
	 */
	public static Matrix4f createOrthographicMatrix(float l, float r, float b, float t, float n, float f) {
		float[] matrix = new float[16];
		matrix[0] = 2f / (r - l);
		matrix[5] = 2f / (t - b);
		matrix[10] = -2f / (f - n);
		matrix[12] = -(r + l) / (r - l);
		matrix[13] = -(t + b) / (t - b);
		matrix[14] = -(f + n) / (f - n);
		matrix[15] = 1;
		return new Matrix4f(matrix);
	}

	/**
	 * Creates a transformation matrix that can be used to position, rotate, and
	 * scale an object.
	 * 
	 * @param position the position
	 * @param rotation the rotation
	 * @param scale the scale
	 * @return the transformation matrix
	 */
	public static Matrix4f createTransformationMatrix(Vertex3f position, Vertex3f rotation, Vertex3f scale) {
		float[] matrix = new float[16];
		matrix[0] = scale.getX();
		matrix[3] = position.getX();
		matrix[5] = scale.getY();
		matrix[7] = position.getY();
		matrix[10] = scale.getZ();
		matrix[11] = position.getZ();
		matrix[15] = 1;
		return new Matrix4f(matrix);
	}

	/**
	 * Multiplies a given vector by a matrix. This is generally used to convert
	 * between different spaces, and OpenGL generally does this internally.
	 * 
	 * @param vertex the vertex to multiply
	 * @param matrix the matrix to multiply
	 * @return
	 */
	public static Vertex4f multiply(Vertex4f vertex, Matrix4f matrix) {
		float x = matrix.get(0, 0) * vertex.getX() + matrix.get(0, 1) * vertex.getY() + matrix.get(0, 2) * vertex.getZ() + matrix.get(0, 3) * vertex.getW();
		float y = matrix.get(1, 0) * vertex.getX() + matrix.get(1, 1) * vertex.getY() + matrix.get(1, 2) * vertex.getZ() + matrix.get(1, 3) * vertex.getW();
		float z = matrix.get(2, 0) * vertex.getX() + matrix.get(2, 1) * vertex.getY() + matrix.get(2, 2) * vertex.getZ() + matrix.get(2, 3) * vertex.getW();
		float w = matrix.get(3, 0) * vertex.getX() + matrix.get(3, 1) * vertex.getY() + matrix.get(3, 2) * vertex.getZ() + matrix.get(3, 3) * vertex.getW();
		return new Vertex4f(x, y, z, w);
	}

}
