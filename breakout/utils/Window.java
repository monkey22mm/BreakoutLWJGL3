package breakout.utils;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
public class Window {
	private long window;
	private boolean fullscreen;
	private int width, height;
	private Input input;
	public static void setCallbacks() 
	{
		glfwSetErrorCallback(new GLFWErrorCallback() {

			@Override
			public void invoke(int errorCode, long description) {
				throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
			}});
	}
	
	public Window() 
	{
		setSize(1280, 720);
		setFullscreen(false);
	}
	public void update() 
	{
		input.update();
		glfwPollEvents();
	}
	public void createWindow(String s) 
	{//first 0 is for fullscreen
		window = glfwCreateWindow(width, height, s, fullscreen ? glfwGetPrimaryMonitor() : 0,0);
		
		if(window == 0) 
		{
			throw new IllegalStateException("Failed to create window!");
		}
		if(!fullscreen) {
		//Get all monitor details and put in video mode, make in center
		GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vid.width()-width)/2, (vid.height()-height)/2);
		//show window
		glfwShowWindow(window);
		}
		//allows GL creation abilities
		glfwMakeContextCurrent(window);
		
		input = new Input(window);
		
	}
	public void setFullscreen(boolean fullscreen) 
	{
		this.fullscreen = fullscreen;
	}
	public boolean isFullscreen() 
	{
		return fullscreen;
	}
	public boolean shouldClose() 
	{
		return glfwWindowShouldClose(window) != false;
	}
	public void swapBuffers() 
	{
		glfwSwapBuffers(window);
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	public long getWindow() 
	{
		return window;
	}
	public Input getInput()
	{
		return input;
	}
}
