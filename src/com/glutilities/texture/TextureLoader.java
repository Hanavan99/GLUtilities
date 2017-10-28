package com.glutilities.texture;

import java.io.File;
import java.io.IOException;

import com.glutilities.resource.ResourceLoader;

public class TextureLoader extends ResourceLoader<Texture, Integer> {

	@Override
	public Texture load(File file, Integer key) throws IOException {
		return null;
	}

	@Override
	public boolean keyReferencesResource(Texture resource, Integer key) {
		return resource.getID() == key.intValue();
	}

}
