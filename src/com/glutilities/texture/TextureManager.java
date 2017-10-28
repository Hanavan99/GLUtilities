package com.glutilities.texture;

import com.glutilities.resource.ResourceManager;

public class TextureManager extends ResourceManager<Texture, Integer> {

	public TextureManager() {
		super(new TextureLoader());
	}

}
