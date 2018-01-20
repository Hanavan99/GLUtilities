package com.glutilities.audio;

import java.io.File;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;

public class AudioUtils {

	public static ShortBuffer convertToMono(ShortBuffer buffer) {
		ShortBuffer result = BufferUtils.createShortBuffer(buffer.capacity() / 2);
		for (int i = 0; i < result.capacity(); i++) {
			result.put(buffer.get(i * 2));
		}
		result.flip();
		return result;
	}

	public static AudioBuffer load(File src, int format, int samplerate) {
		boolean stereo = format == AL10.AL_FORMAT_STEREO8 || format == AL10.AL_FORMAT_STEREO16;
		ShortBuffer data = STBVorbis.stb_vorbis_decode_filename(src.getAbsolutePath(), IntBuffer.wrap(new int[stereo ? 2 : 1]), IntBuffer.wrap(new int[samplerate]));
		if (!stereo) {
			data = convertToMono(data);
		}
		return new AudioBuffer(format, data, samplerate);
	}

}
