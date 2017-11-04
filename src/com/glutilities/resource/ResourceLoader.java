package com.glutilities.resource;

import java.io.File;
import java.io.IOException;

/**
 * Loads resources from files. This class has no functionality without
 * implementation.
 * 
 * @author Hanavan99
 *
 * @param <R>
 *            The type of resource stored
 * @param <K>
 *            The type of key used to uniquely identify each resource
 */
public abstract class ResourceLoader<R, K> {

	/**
	 * Load a resource from a file.
	 * 
	 * @param file
	 *            The file path
	 * @param key
	 *            The key used to identify this resource
	 * @return The resource
	 * @throws IOException
	 *             If there is some problem reading the file
	 */
	public abstract R load(File file, K key) throws IOException;

	/**
	 * Determines if a given resource is identified by this key. Usually the key
	 * will be some unique value that is stored inside of the resource.
	 * 
	 * @param resource
	 *            The resource to validate
	 * @param key
	 *            The key
	 * @return If the resource is matched with the key
	 */
	public abstract boolean keyReferencesResource(R resource, K key);

	/**
	 * Uses the resource to determine the key that was used to reference it.
	 * 
	 * @param resource
	 *            The resource
	 * @return The key
	 */
	public abstract K getKeyFromResource(R resource);

}
