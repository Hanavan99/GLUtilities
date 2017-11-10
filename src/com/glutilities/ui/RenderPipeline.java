package com.glutilities.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.glutilities.ui.fbo.Framebuffer;
import com.glutilities.ui.scene.SceneProjection;

public class RenderPipeline {

	public static final int CMD_BIND_FRAMEBUFFER = 0x0;
	
	public static final int CMD_UNBIND_FRAMEBUFFER = 0x1;
	
	public static final int CMD_SETUP_PROJECTION = 0x2;
	
	public static final int CMD_CALL_RENDERER = 0x3;
	
	public static final int CMD_GL_CLEAR_AND_VIEWPORT = 0x4;
	
	public static final int CMD_SETUP_MODELVIEW = 0x5;
	
	public static final int CMD_GL_PUSH_MATRIX = 0x6;
	
	public static final int CMD_GL_POP_MATRIX = 0x7;
	
	private Map<String, Framebuffer> framebuffers = new HashMap<String, Framebuffer>();
	private Map<String, SceneProjection> scenes = new HashMap<String, SceneProjection>();
	private Map<String, Renderer> renderers = new HashMap<String, Renderer>();
	//private Map<Integer, String[]> commands = new HashMap<Integer, String[]>();
	private List<Integer> commands = new ArrayList<Integer>();
	private List<String[]> arguments = new ArrayList<String[]>();
	
	public RenderPipeline() {
		
	}
	
	public void addFramebuffer(String name, Framebuffer framebuffer) {
		framebuffers.put(name, framebuffer);
	}
	
	public void addScene(String name, SceneProjection scene) {
		scenes.put(name, scene);
	}
	
	public void addRenderer(String name, Renderer renderer) {
		renderers.put(name, renderer);
	}
	
	public void addCommand(int command, String... args) {
		//commands.put(command, args);
		commands.add(command);
		arguments.add(args);
	}
	
	public void render(int width, int height, float aspect) {
		//Iterator<Entry<Integer, String[]>> iterator = commands.entrySet().iterator();
		Iterator<Integer> cmdIterator = commands.iterator();
		Iterator<String[]> argIterator = arguments.iterator();
		while (cmdIterator.hasNext() && argIterator.hasNext()) {
			int command = cmdIterator.next();
			String[] args = argIterator.next();
			switch (command) {
			case CMD_BIND_FRAMEBUFFER:
				Framebuffer framebuffer = framebuffers.get(args[0]);
				framebuffer.bind();
				break;
			case CMD_UNBIND_FRAMEBUFFER:
				Framebuffer framebuffer2 = framebuffers.get(args[0]);
				framebuffer2.unbind();
				break;
			case CMD_SETUP_PROJECTION:
				SceneProjection projection = scenes.get(args[0]);
				projection.setupProjectionMatrix(width, height, aspect);
				break;
			case CMD_CALL_RENDERER:
				Renderer renderer = renderers.get(args[0]);
				renderer.render();
				break;
			case CMD_GL_CLEAR_AND_VIEWPORT:
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glViewport(0, 0, width, height);
				break;
			case CMD_SETUP_MODELVIEW:
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
				break;
			case CMD_GL_PUSH_MATRIX:
				GL11.glPushMatrix();
				break;
			case CMD_GL_POP_MATRIX:
				GL11.glPushMatrix();
				break;
			}
		}
	}
	
	public void updateFramebuffers(int width, int height) {
		for (Framebuffer framebuffer : framebuffers.values()) {
			framebuffer.delete();
			framebuffer.setSize(width, height);
			framebuffer.create();
		}
	}
	
}
