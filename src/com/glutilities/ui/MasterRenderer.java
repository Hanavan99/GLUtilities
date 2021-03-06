package com.glutilities.ui;

public abstract class MasterRenderer {

	/**
	 * Called when the render target is initializing.
	 * 
	 * @param context the render context
	 */
	public void init(RenderContext context) {

	}

	/**
	 * Called when the render target is rendering the scene.
	 * 
	 * @param context the render context
	 */
	public void render(RenderContext context) {

	}

	/**
	 * Called when the render target has received some sort of update, such as a
	 * resizing.
	 * 
	 * @param context the render context
	 */
	public void update(RenderContext context) {

	}

	/**
	 * Called when the render target is being closed.
	 * 
	 * @param context the render context
	 */
	public void exit(RenderContext context) {

	}
	
	public void keyPressed(RenderContext context, int key, int action) {
		
	}
	
	public void mouseClicked(RenderContext context, int button, int action) {
		
	}
	
	public void mouseMoved(RenderContext context, int xpos, int ypos) {
		
	}
	
	public void textTyped(RenderContext context, int codepoint) {
		
	}

}
