package com.glutilities.model;

import java.io.File;

import com.glutilities.resource.ResourceManager;

public class ModelManager extends ResourceManager<Model, String, File> {

	public ModelManager() {
		super(new ModelLoader());
	}

	// private static ModelLoader modelLoader = new ModelLoader();
	// private static List<Model> models = new ArrayList<Model>();
	//
	// public static void loadModel(File file) {
	// loadModel(file, file.getName());
	// }
	//
	// public static void loadModel(File file, String modelName) {
	// models.add(modelLoader.loadModel(file, modelName));
	// }
	//
	// public static Model getModel(String modelName) {
	// for (Model model : models) {
	// if (model.getModelName().equals(modelName)) {
	// return model;
	// }
	// }
	// return null;
	// }

}
