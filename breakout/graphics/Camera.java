package breakout.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	private Vector3f position;
	private Matrix4f projection;
	
	public Camera(int width, int height) 
	{
		position = new Vector3f(0,0,0);
		//origin is center
		//projection = new Matrix4f().ortho2D(-width/2, width/2, -height/2, height/2);
		//origin is top left
		//textures need flipped now
		projection = new Matrix4f().ortho2D(0, width, height, 0);

	}
	public void setPosition(Vector3f position) 
	{
		this.position = position;
	}
	public void addPosition(Vector3f position) 
	{
		this.position.add(position);
	}
	public Vector3f getPosition() 
	{
		return this.position;
	}
	public Matrix4f getProjection() 
	{
		return projection.translate(position, new Matrix4f());
	}
}
