package com.engine.graphics;

import com.engine.maths.*;
import com.engine.utils.*;

import java.util.*;

import org.lwjgl.opengl.*;
//import org.lwjgl.opengl.GL20.*;

public class Shader {

	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader BG;
	
	private boolean enabled = false;
	
	private final int ID;
	
	private Map<String, Integer> locationCache = new HashMap<String,Integer>();
	
	public Shader(String vert, String frag) {
		ID = ShaderUtils.load(vert, frag);
	}
	
	public static void loadAll() {
		BG = new Shader("shaders/bg.vert", "shaders/bg.frag");
	}
		
	public int getUniform(String name){
		if (locationCache.containsKey(name))
			return locationCache.get(name);
		
		int result = GL20.glGetUniformLocation(ID, name);
		if (result == -1)
			System.err.println("Could not find uniform variable: " + name + "!");
		else
			locationCache.put(name, result);
		
		return result;
	}

	public void setUniform1i(String name, int value) {
		if (!enabled) enable();
		GL20.glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value) {
		if (!enabled) enable();
		GL20.glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y) {
		if (!enabled) enable();
		GL20.glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniform3f(String name, Vector3f vector) {
		if (!enabled) enable();
		GL20.glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix){
		if (!enabled) enable();
		GL20.glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void enable(){
		GL20.glUseProgram(ID);
		enabled = true;
	}
	
	public void disable(){
		GL20.glUseProgram(0);
		enabled = false;
	}
}
