package com.glutilities.model;

import java.io.File;

import com.glutilities.resource.ResourceManager;

public class ModelManager extends ResourceManager<Model, String, File> {

	public ModelManager() {
		super(new ModelLoader());
	}

}
