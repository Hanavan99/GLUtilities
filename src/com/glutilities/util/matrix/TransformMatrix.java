package com.glutilities.util.matrix;

import com.glutilities.shader.ShaderProgram;
import com.glutilities.util.Vertex3f;

public class TransformMatrix {

	private ShaderProgram program;
	private String matrixName = "transformMatrix";
	private Vertex3f rot = new Vertex3f(0);
	private Vertex3f trans = new Vertex3f(0);
	private Vertex3f scale = new Vertex3f(1);

	public TransformMatrix(ShaderProgram program, String matrixName) {
		this.program = program;
		this.matrixName = matrixName;
	}

	public void rotate(float x, float y, float z) {
		rot.addX(x);
		rot.addY(y);
		rot.addZ(z);
		update();
	}

	public void setRotation(float x, float y, float z) {
		rot.setX(x);
		rot.setY(y);
		rot.setZ(z);
		update();
	}

	public void translate(float x, float y, float z) {
		trans.addX(x);
		trans.addY(y);
		trans.addZ(z);
		update();
	}

	public void setTranslation(float x, float y, float z) {
		trans.setX(x);
		trans.setY(y);
		trans.setZ(z);
		update();
	}

	public void scale(float x, float y, float z) {
		scale.setX(scale.getX() * x);
		scale.setY(scale.getY() * y);
		scale.setZ(scale.getZ() * z);
		update();
	}

	public void setScale(float x, float y, float z) {
		scale.setX(x);
		scale.setY(y);
		scale.setZ(z);
		update();
	}

	public void reset() {
		setRotation(0, 0, 0);
		setTranslation(0, 0, 0);
		setScale(1, 1, 1);
		update();
	}

	public void update() {
		if (program != null && matrixName != null) {
			program.setMatrix4f(matrixName, getMatrix());
		}
	}

	public Matrix4f getMatrix() {
		Matrix4f mat = Matrix4f.IDENTITY_MATRIX;
		mat = MatrixMath.setTranslation(mat, trans);
		mat = MatrixMath.setScale(mat, scale);
		Matrix4f rotX = MatrixMath.rotate(mat, rot.getX(), 0b100);
		Matrix4f rotY = MatrixMath.rotate(mat, rot.getY(), 0b010);
		Matrix4f rotZ = MatrixMath.rotate(mat, rot.getZ(), 0b001);
		mat = mat.multiply(rotX).multiply(rotY).multiply(rotZ);
		return mat;
	}

}
