package com.glutilities.audio;

import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALC11;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.openal.ALUtil;

/**
 * Provides a more useful binding to create valid OpenAL bindings and contexts.
 * 
 * @author Hanavan99
 *
 */
public class AudioSystem {

	private static long device;
	private static long context;
	private static ALCCapabilities alcCaps;
	private static ALCapabilities caps;
	private static String selectedDevice = null;

	/**
	 * Creates an OpenAL context with the selected audio device.
	 */
	public static void create() {
		device = ALC10.alcOpenDevice(selectedDevice);
		alcCaps = ALC.createCapabilities(device);
		context = ALC10.alcCreateContext(device, (IntBuffer) null);
		ALC10.alcMakeContextCurrent(context);
		AL.createCapabilities(alcCaps);

	}

	/**
	 * Destroys the OpenAL audio context and frees the device currently in use.
	 */
	public static void destroy() {
		ALC10.alcCloseDevice(device);
		ALC10.alcDestroyContext(context);
		ALC.destroy();
	}

	/**
	 * Gets the currently available devices that can accept sound.
	 * 
	 * @return the devices
	 */
	public static String[] getDevices() {
		if (alcCaps.OpenALC11) {
			return ALUtil.getStringList(0, ALC11.ALC_ALL_DEVICES_SPECIFIER).toArray(new String[0]);
		}
		return null;
	}

	/**
	 * Selects a device to be opened. If the specified name is null, the default
	 * device will be selected. In order for these changes to take effect, the
	 * OpenAL context must be destroyed and recreated. The names of devices can
	 * be retrieved from the {@code getDevices()} method.
	 * 
	 * @param deviceName the name of the device
	 */
	public static void selectDevice(String deviceName) {
		selectedDevice = deviceName;
	}

	/**
	 * Sets the doppler effect factor. 0 means no doppler effect, 1 means
	 * default, and > 1 is more exaggerated.
	 * 
	 * @param factor the factor
	 */
	public static void setDopplerFactor(float factor) {
		AL10.alDopplerFactor(factor);
	}

	/**
	 * Sets the global speed of sound for doppler calculations. The default is
	 * set at 343.3m/s.
	 * 
	 * @param speed the speed
	 */
	public static void setSpeedOfSound(float speed) {
		AL11.alSpeedOfSound(speed);
	}

	/**
	 * Gets the current capabilities of the device and context.
	 * 
	 * @return the capabilities
	 */
	public static ALCapabilities getCapabilities() {
		return caps;
	}

}
