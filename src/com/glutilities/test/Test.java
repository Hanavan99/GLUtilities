package com.glutilities.test;

import java.io.File;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.glutilities.gui.GLComponent;
import com.glutilities.gui.GLRootPane;
import com.glutilities.model.Model;
import com.glutilities.model.ModelManager;

public class Test {

	// https://www.microsoft.com/typography/otspec/glyf.htm

	public static int fps;
	/*
	 * Features: JavaScript rendering TTF Font support Texture Loading Dynamic
	 * Shaders Maybe VBOs AWT-Like rendering system Input handling (Keyboard, Mouse,
	 * Controller)
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TTFPrimitive test = new TTFPrimitive(TTFPrimitive.Type.ULONG,
		// 0x676C7966);
		// System.out.println(test.getStringValue());
		// try {
		// TTFFile file = new TTFFile(new
		// File("C:/Users/Hanavan/Desktop/times.ttf"));
		// TTFFontLoader loader = new TTFFontLoader(file);
		// TTFFontDescriptor desc = loader.load();
		// HeaderTable ht = (HeaderTable) desc.getTable(HeaderTable.class);
		// System.out.println("0x" + Long.toHexString(ht.getMagicNumber()));
		// // System.out.println(file.readAsDouble(DataType.FIXED));
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// TTFFontManager.loadFont("C:/Users/Hanavan/Desktop/times.ttf");
		// TTFFont font = TTFFontManager.getFont(null);
		// OffsetTable ot = (OffsetTable) font.getTable(OffsetTable.class);
		// System.out.println(ot.getSfntVersion());

		// FontManager.loadFont("Arial", 0);
		//
		// GLFW.glfwInit();
		// GLRootPane pane = new GLRootPane(800, 600, "Testing", 0);
		// pane.addComponent(new GLTextArea());
		// pane.loop();
		GLFW.glfwInit();
		ModelManager mm = new ModelManager();
		File f = new File("D:/models/car.obj");
		mm.load(f, "car");
		final Model car = mm.get("car");
		GLRootPane pane = new GLRootPane(1920, 1080, "Testing", GLFW.glfwGetPrimaryMonitor());
		pane.addComponent(new GLComponent() {

			@Override
			public void render() {
				GL11.glTranslated(0, 0, -10);
				GL11.glRotated(-40, 1, 0, 0);
				GL11.glRotated(System.nanoTime() / 100000000d, 0, 0, 1);
				if (car != null) {
					car.draw();
				}
				fps++;
			}

		});
		Thread fpsThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000);
					System.out.printf("FPS: %d\n", fps);
					fps = 0;
				} catch (InterruptedException e) {
					break;
				}
			}
		});
		fpsThread.start();
		
		pane.loop();
		
		fpsThread.interrupt();
	}

}
