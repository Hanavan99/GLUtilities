package com.glutilities.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.glutilities.core.Reusable;

/**
 * A wrapper class for the {@code ResourceLoader} class. Extends the
 * functionality of the {@code ResourceLoader} to store any loaded objects for
 * later reference by some key. To store loaded resources manually, use the
 * {@code ResourceLoader} class, which only loads resources from external files.
 * 
 * @author Hanavan99
 *
 * @param <R> The type of resource stored
 * @param <K> The type of key used to uniquely identify each resource
 * @param <S> The type of source the resource is loaded from (File, String,
 *            etc.)
 */
public abstract class ResourceManager<R, K, S> {

	private ResourceLoader<R, K, S> loader;
	private List<R> resources = new ArrayList<R>();

	/**
	 * Constructor that accepts a {@code ResourceLoader} object for use when
	 * loading resources. This constructor should be called in the subclass.
	 * 
	 * @param loader The {@code ResourceLoader} object to use for loading
	 *            resources
	 */
	public ResourceManager(ResourceLoader<R, K, S> loader) {
		this.loader = loader;
	}

	/**
	 * Loads a resource from the specified file using the resource loader. Note
	 * that if the loader's {@code load()} method throws an exception, the
	 * resource will not be loaded.
	 * 
	 * @param src The source to load the resource from
	 * @param key The key to be used to reference the resource
	 */
	public void load(S src, K key) {
		try {
			resources.add(loader.load(src, key));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the resource identified by a key. If no resource is found,
	 * {@code null} is returned.
	 * 
	 * @param key The key of the resource
	 * @return The resource
	 */
	public R get(K key) {
		for (R resource : resources) {
			if (loader.keyReferencesResource(resource, key)) {
				return resource;
			}
		}
		return null;
	}

	/**
	 * Removes all resources that were stored internally. Once called, none of
	 * the previous resources will be accessible, but future additions will be.
	 * If the resource type implements {@code Reusable}, the {@code delete()}
	 * method of each item is called.
	 */
	public void deleteResources() {
		for (R resource : resources) {
			if (resource instanceof Reusable) {
				((Reusable) resource).delete();
			}
		}
		resources.clear();
	}

	/**
	 * Gets all of the resources loaded by this {@code ResourceLoader}.
	 * 
	 * @return The resources
	 */
	public List<R> getResources() {
		return resources;
	}

	/**
	 * Gets all of the keys that reference the resources held in this
	 * {@code ResourceLoader}.
	 * 
	 * @return The keys
	 */
	public List<K> getKeys() {
		List<K> keys = new ArrayList<K>();
		for (R resource : resources) {
			keys.add(loader.getKeyFromResource(resource));
		}
		return keys;
	}

}
