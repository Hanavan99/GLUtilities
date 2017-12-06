package com.glutilities.model;

import java.io.File;

import com.glutilities.resource.ResourceManager;

public class BufferedModelManager extends ResourceManager<BufferedModel, String, File> {

	public BufferedModelManager() {
		super(new BufferedModelLoader());
	}

}
