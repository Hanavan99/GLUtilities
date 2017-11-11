package com.glutilities.util.matrix;

import com.glutilities.util.Vertex3f;

public class MatrixMath {

	public static Matrix4f translate(Matrix4f src, Vertex3f translate) {
		return src.set(0, 3, src.get(0, 3) + translate.getX()).set(1, 3, src.get(1, 3) + translate.getY()).set(2, 3, src.get(2, 3) + translate.getZ());
	}
	
	public static Matrix4f scale(Matrix4f src, Vertex3f scale) {
		return src.set(0, 0, src.get(0, 0) * scale.getX()).set(1, 1, src.get(1, 1) * scale.getY()).set(2, 2, src.get(2, 2) * scale.getZ());
	}
	
	public static Matrix4f rotate(Matrix4f src, Vertex3f angle) {
		Vertex3f scale = new Vertex3f(src.get(0, 0), src.get(1, 1), src.get(2, 2));
		return src.set(1, 1, scale.getX() * (float) Math.cos(angle.getX())).set(1, 2, scale.getX() * (float) -Math.sin(angle.getX())).set(2, 1, scale.getX() * (float) Math.sin(angle.getX())).set(2, 2, scale.getX() * (float) Math.cos(angle.getX()));
	}
	
}
