package com.glutilities.core;

/**
 * Represents an object that has resources that can be reused later after they
 * have been deleted.
 * 
 * @author Hanavan99
 */
public interface Deletable {

	/**
	 * Deletes any resources used by the object, allowing for later reuse.
	 */
	public void delete();

}
