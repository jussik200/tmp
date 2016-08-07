package com.engine.input;

import org.lwjgl.glfw.*;

public class Input extends GLFWKeyCallback {

	public static boolean[] keys = new boolean[65536];
	
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW.GLFW_RELEASE;
	}
	
	public static boolean isKeyPressed(int key){
		return keys[key];
	}

}
