package com.glutilities.ui.gfx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.glutilities.buffer.VBO;
import com.glutilities.shader.FragmentShader;
import com.glutilities.shader.ShaderProgram;
import com.glutilities.shader.VertexShader;
import com.glutilities.ui.MasterRenderer;
import com.glutilities.ui.RenderContext;
import com.glutilities.util.Vertex2f;
import com.glutilities.util.Vertex3f;
import com.glutilities.util.Vertex4f;
import com.glutilities.util.matrix.MatrixMath;

public class UIRenderer extends MasterRenderer {

	private List<UIElement> elements = new ArrayList<UIElement>();
	private ShaderProgram program;
	private UIHandle handle;
	private int selectedElement = -1;
	private Vertex2f mousePos = new Vertex2f(0);
	private VBO mask;

	public UIRenderer() {
	}

	@Override
	public void init(RenderContext context) {
		VertexShader vsh = new VertexShader(loadFile(new File("res/shaders/ui.vsh")));
		FragmentShader fsh = new FragmentShader(loadFile(new File("res/shaders/ui.fsh")));
		program = new ShaderProgram(vsh, fsh);
		vsh.create();
		fsh.create();
		program.create();
		program.link();
		System.out.println(program.getError());
		handle = new UIHandle(program, context);
		mask = handle.createBox(0, 0, 1, 1, Vertex4f.BLACK);
		GL11.glClearColor(1, 1, 1, 1);
	}

	@Override
	public void render(RenderContext context) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glViewport(0, 0, context.getWidth(), context.getHeight());

		program.enable();
		// handle.updateProjectionMatrix();
		// handle.setRenderScale(new Vertex3f(uiScale, uiScale, 1));
		handle.setProjectionMatrix(MatrixMath.createOrthographicMatrix(0, context.getWidth(), context.getHeight(), 0, -1, 1));
		handle.updateProjectionMatrix();
		//handle.setRenderScale(new Vertex3f(200, 20, 1));

		for (UIElement element : elements) {
			handle.pushMatrix();
			handle.setRenderPos(element.getPosition());
			handle.setRenderScale(element.getSize());
			// GL11.glEnable(GL11.GL_STENCIL_TEST);
			// GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
			// GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
			// GL11.glStencilMask(0xFF);
			// GL11.glDepthMask(false);
			//
			// mask.draw();
			//
			// GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
			// GL11.glStencilMask(0x00);
			// GL11.glDepthMask(true);
			element.render(handle);
			// GL11.glDisable(GL11.GL_STENCIL_TEST);
			handle.popMatrix();
		}
		program.disable();
	}

	@Override
	public void update(RenderContext context) {

	}

	@Override
	public void exit(RenderContext context) {
		handle.cleanup();
	}

	@Override
	public void keyPressed(RenderContext context, int key, int action) {
		if (selectedElement != -1) {
			elements.get(selectedElement).keyPressed(key, action);
		}
	}

	@Override
	public void mouseClicked(RenderContext context, int button, int action) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS) {
			int i = 0;
			selectedElement = -1;
			for (UIElement element : elements) {
				Vertex3f pos = element.getPosition();
				Vertex3f size = element.getSize();
				System.out.println(pos + "\t" + size);
				if (mousePos.getX() > pos.getX() && mousePos.getX() < pos.getX() + size.getX() && mousePos.getY() > pos.getY() && mousePos.getY() < pos.getY() + size.getY()) {
					System.out.println(element);
					selectedElement = i;
					element.selected();
					break;
				} else {
					element.deselected();
				}
				i++;
			}

		}
	}

	@Override
	public void mouseMoved(RenderContext context, int xpos, int ypos) {
		mousePos.setX(xpos);/// (float) context.getWidth() * 2f - 1f);
		mousePos.setY(ypos);/// (float) context.getHeight() * 2f - 1f);
		// mousePos.scale(new Vertex2f(1 / uiScale, 1 / uiScale / 4f));
		System.out.println(mousePos);
		// check hover event
	}

	@Override
	public void textTyped(RenderContext context, int codepoint) {
		if (selectedElement != -1) {
			elements.get(selectedElement).textTyped((char) codepoint);
		}
	}

	public void addElement(UIElement element) {
		element.setup(handle);
		elements.add(element);
	}

	private String loadFile(File f) {
		try {
			Scanner s = new Scanner(f);
			String result = "";
			while (s.hasNext()) {
				result += s.nextLine() + "\n";
			}
			s.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
