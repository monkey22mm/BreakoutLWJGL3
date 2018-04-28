/**
Title: LWJGL Breakout Game
Author: Liam Speakman
Notes: N/A
Copyright (C) 2018 Liam Speakman <lspeakman001@gmail.com>
You are free to copy, redistribute, and modify this work with no permission from the author 
**/
package breakout.utils;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import breakout.statesystem.StateHandler;
public class Application 
{

	StateHandler sh;
	Window w;
	public void run() 
	{
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		
		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(w.getWindow());
		glfwDestroyWindow(w.getWindow());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() 
	{
		Window.setCallbacks();
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Create the window
        w = new Window();
        w.createWindow("Game Of Humans");
		//glfwSetKeyCallback(w.getWindow(), keyCallback = new KeyCallback());
		GL.createCapabilities();
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		sh = new StateHandler(w);
		}

	private void loop() 
	{
		double frame_cap = 1.0/60.0;
		double frame_time = 0;
		int frames = 0;
		double time = Timer.getTime();
		double unprocessed = 0;
		
		while (!w.shouldClose()) {
			boolean can_render = false;
			double time_2 = Timer.getTime();
			double passed = time_2 - time;
			unprocessed+=passed;
			frame_time += passed;
			time = time_2;
			while(unprocessed >= frame_cap) 
			{
				unprocessed-=frame_cap;
				can_render = true;
				
				w.update();
				sh.update();
				if (w.getInput().isKeyPressed(GLFW_KEY_ESCAPE))
					glfwSetWindowShouldClose(w.getWindow(), true);
				
				
				if(frame_time >= 1.0) 
				{
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			}
			
			if(can_render) {
			frames++;
			glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
			
			sh.render();
			w.swapBuffers(); // swap the color buffers
			}
		}
	}

	public static void main(String[] args) 
	{
		new Application().run();
	}

}