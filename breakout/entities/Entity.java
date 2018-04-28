package breakout.entities;

import org.joml.Matrix4f;

import breakout.graphics.Camera;
import breakout.graphics.Shader;
import breakout.utils.Window;

public abstract class Entity {
	protected int x,y;
	protected Window w;
	public Entity(Window w, int x, int y) 
	{
		this.w=w;
		this.x=x;
		this.y=y;
	}
	public abstract void update();
	
	public abstract void render(Shader sh,Camera c, Matrix4f target);
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
