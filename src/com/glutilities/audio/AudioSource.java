package com.glutilities.audio;

import org.lwjgl.openal.AL10;

import com.glutilities.core.Reusable;
import com.glutilities.util.Vertex3f;

public class AudioSource implements Reusable {

	private int id;

	public AudioSource() {
		setPosition(new Vertex3f(0));
		setVelocity(new Vertex3f(0));
		setPitch(1);
		setGain(1);
	}
	
	public void setPosition(Vertex3f pos) {
		AL10.alSource3f(id, AL10.AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
	}
	
	public void setVelocity(Vertex3f v) {
		AL10.alSource3f(id, AL10.AL_VELOCITY, v.getX(), v.getY(), v.getZ());
	}
	
	public void setPitch(float p) {
		AL10.alSourcef(id, AL10.AL_PITCH, p);
	}
	
	public void setGain(float g) {
		AL10.alSourcef(id, AL10.AL_GAIN, g);
	}
	
	public void setBuffer(AudioBuffer buffer) {
		AL10.alSourcei(id, AL10.AL_BUFFER, buffer.getID());
	}

	public void play() {
		AL10.alSourcePlay(id);
	}
	
	public void play(AudioBuffer buffer) {
		AL10.alSourcei(id, AL10.AL_BUFFER, buffer.getID());
		AL10.alSourcePlay(id);
	}

	@Override
	public void create() {
		id = AL10.alGenSources();
	}

	@Override
	public void delete() {
		AL10.alDeleteSources(id);
	}

}
