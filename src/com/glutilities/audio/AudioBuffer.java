package com.glutilities.audio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.openal.AL10;

import com.glutilities.core.Reusable;

public class AudioBuffer implements Reusable {

	private int id;
	private int format;
	private Buffer data;
	private int frequency;

	public AudioBuffer(int format, Buffer data, int frequency) {
		this.format = format;
		this.data = data;
		this.frequency = frequency;
	}

	public int getID() {
		return id;
	}

	@Override
	public void create() {
		id = AL10.alGenBuffers();
		if (data instanceof ShortBuffer) {
			AL10.alBufferData(id, format, (ShortBuffer) data, frequency);
		} else if (data instanceof ByteBuffer) {
			AL10.alBufferData(id, format, (ByteBuffer) data, frequency);
		} else if (data instanceof FloatBuffer) {
			AL10.alBufferData(id, format, (FloatBuffer) data, frequency);
		} else {
			throw new IllegalStateException("Invalid buffer type for audio buffer (" + data.getClass().getName() + ")");
		}
	}

	@Override
	public void delete() {
		AL10.alDeleteBuffers(id);
	}

}
