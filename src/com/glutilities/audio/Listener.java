package com.glutilities.audio;

import org.lwjgl.openal.AL10;

import com.glutilities.util.Vertex3f;

public class Listener {

	public static void setPos(Vertex3f pos) {
		AL10.alListener3f(AL10.AL_POSITION, pos.getX(), pos.getY(), pos.getZ());
	}

	public static void setAngle(Vertex3f angle) {
		AL10.alListener3f(AL10.AL_ORIENTATION, angle.getX(), angle.getY(), angle.getZ());
	}
	
	public static void setVelocity(Vertex3f velocity) {
		AL10.alListener3f(AL10.AL_VELOCITY, velocity.getX(), velocity.getY(), velocity.getZ());
	}

}
