package com.glutilities.core;

/**
 * Represents an object that has resources that can be reused later after they
 * have been deleted.
 * 
 * @author Hanavan99
 */
public interface Reusable {

	/**
	 * Creates any resources that will be used by the object with the ability to
	 * delete or free the resources later.
	 */
	public void create();

	/**
	 * Deletes any resources used by the object, allowing for later reuse.
	 */
	public void delete();

}
