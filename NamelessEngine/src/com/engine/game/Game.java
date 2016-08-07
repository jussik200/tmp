package com.engine.game;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import com.engine.graphics.*;
import com.engine.input.*;
import com.engine.maths.*;

public class Game implements Runnable {

	private int width = 1280;
	private int height = 780;
	
	private Thread thread;
	boolean running = false;
	
	private long window;
	
	private Level level;
	
	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	private void init() {
		if (!GLFW.glfwInit()){
			// TODO: handle it
			return;
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
		window = GLFW.glfwCreateWindow(width, height, "Game", 0, 0);
		
		if (window == 0L) {
			System.out.println("window == 0L");
			return;
		}
		
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		GLFW.glfwSetKeyCallback(window, new Input());
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwShowWindow(window);
		GL.createCapabilities();
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		System.out.println("OpenGL: " + GL11.glGetString(GL11.GL_VERSION));
		
		Shader.loadAll();
		Shader.BG.enable();
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.disable();
		
		level = new Level();
	}
	
	public void run() {
		init();
		while(running){
			update();
			render();
			
			if (GLFW.glfwWindowShouldClose(window)) {
				running = false;
			}
		}
	}
	
	private void update() {
		GLFW.glfwPollEvents();
		running = !Input.isKeyPressed(GLFW.GLFW_KEY_ESCAPE);
	}
	
	private void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		level.render();
		
		int error = GL11.glGetError();
		if (error != GL11.GL_NO_ERROR)
			System.out.println(error);
		
		GLFW.glfwSwapBuffers(window);
	}
	
	public static void main(String[] args) {
		 new Game().start();
	}


}
