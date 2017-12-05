package com.glutilities.resource;

public class MissingResourceException extends Exception {

	private static final long serialVersionUID = -1906880668078701850L;

	private String resourceName;

	public MissingResourceException(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public String getMessage() {
		return "Failed to locate resource '" + resourceName + "'." + super.getMessage();
	}

}
