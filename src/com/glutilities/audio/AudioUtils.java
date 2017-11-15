package com.glutilities.audio;

import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;

public class AudioUtils {

	public static ShortBuffer convertToMono(ShortBuffer buffer) {
		ShortBuffer result = BufferUtils.createShortBuffer(buffer.capacity() / 2);
		for (int i = 0; i < result.capacity(); i++) {
			result.put(buffer.get(i * 2));
		}
		result.flip();
		return result;
	}
	
}
