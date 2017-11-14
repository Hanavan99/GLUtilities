package com.glutilities.ui;

public abstract class RenderContext {

	public abstract int getWidth();

	public abstract int getHeight();

	public float getAspect() {
		return (float) getWidth() / getHeight();
	}

}
