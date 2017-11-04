package com.glutilities.texture;

import java.io.File;

import com.glutilities.resource.ResourceManager;

public class TextureManager extends ResourceManager<Texture, String, File> {

	public TextureManager() {
		super(new TextureLoader());
	}

}
