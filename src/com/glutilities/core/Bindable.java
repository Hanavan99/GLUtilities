package com.glutilities.core;

/**
 * Represents and object that can be bound and unbound at any time to allow
 * temporary access to its data.
 * 
 * @author Hanavan99
 */
public interface Bindable {

	/**
	 * Binds this object's data.
	 */
	public void bind();

	/**
	 * Unbinds this object's data.
	 */
	public void unbind();

}
