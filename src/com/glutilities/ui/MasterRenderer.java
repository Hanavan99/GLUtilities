package com.glutilities.ui;

public abstract class MasterRenderer {

	/**
	 * Called when the parent window's {@code init()} function is called.
	 * 
	 * @param parent the parent window
	 */
	public void init(GLWindow parent) {

	}

	/**
	 * Called when the window is rendering the scene.
	 * 
	 * @param parent the parent window
	 */
	public void render(GLWindow parent) {

	}

	/**
	 * Called when the window has received some sort of update, such as a
	 * resizing.
	 * 
	 * @param parent the parent window
	 */
	public void update(GLWindow parent) {

	}

	/**
	 * Called when the window has been successfully closed.
	 * 
	 * @param parent the parent window.
	 */
	public void exit(GLWindow parent) {

	}

}
