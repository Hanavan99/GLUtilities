package com.glutilities.test;

import java.io.File;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.glutilities.gui.GLComponent;
import com.glutilities.gui.GLRootPane;
import com.glutilities.model.Model;
import com.glutilities.model.ModelManager;

public class Test {

	// https://www.microsoft.com/typography/otspec/glyf.htm

	/*
	 * Features: JavaScript rendering TTF Font support Texture Loading Dynamic
	 * Shaders Maybe VBOs AWT-Like rendering system Input handling (Keyboard,
	 * Mouse, Controller)
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

//		FontManager.loadFont("Arial", 0);
//		
//		GLFW.glfwInit();
//		GLRootPane pane = new GLRootPane(800, 600, "Testing", 0);
//		pane.addComponent(new GLTextArea());
//		pane.loop();
		ModelManager mm = new ModelManager();
		mm.load(new File("C:/Users/Hanavan/Desktop/car.obj"), "car");
		final Model car = mm.get("car");
		GLFW.glfwInit();
		GLRootPane pane = new GLRootPane(800, 600, "Testing", 0);
		pane.addComponent(new GLComponent() {

			@Override
			public void render() {
				GL11.glTranslated(0, 0, -15);
				car.draw();
			}
			
		});
		pane.loop();
	}

}
