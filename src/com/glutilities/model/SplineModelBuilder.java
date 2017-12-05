package com.glutilities.model;

import org.lwjgl.opengl.GL11;

import com.glutilities.util.GLMath;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;
import com.glutilities.util.matrix.MatrixMath;
import com.glutilities.util.matrix.TransformMatrix;

public class SplineModelBuilder {

	public static BufferedModel build(float[] points, int samples, float[] verts) {
		float[] finalVerts = new float[verts.length * samples];
		int offset = 0;
		float[] pos = GLMath.bezier(points, samples);
		TransformMatrix transMat = new TransformMatrix();
		for (int i = 0; i < pos.length; i += 2) {
			if (i < pos.length - 3) {
				float theta = (float) Math.atan2(pos[i + 3] - pos[i + 1], pos[i + 2] - pos[i]);
				transMat.setRotation(0, 0, theta + GLMath.PI_1_2);
			}
			float x = pos[i];
			float y = pos[i + 1];
			for (int j = 0; j < verts.length; j += 3) {
				Vertex4f v = new Vertex4f(verts[j], verts[j + 1], verts[j + 2], 0);
				v = MatrixMath.multiply(v, transMat.getMatrix());
				finalVerts[offset + j] = v.getX() + x;
				finalVerts[offset + j + 1] = v.getY() + y;
				finalVerts[offset + j + 2] = v.getZ();
			}
			offset += verts.length;
		}
		return new BufferedModel("asdf", finalVerts, null, null, null, null, GL11.GL_LINES);
	}

}
