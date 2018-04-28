package breakout.utils;

public class Rectangle {
	public float left;
	public float right;
	public float up;
	public float down;
	public Rectangle(float left, float right, float up, float down) 
	{
		this.left=left;
		this.right=right;
		this.up=up;
		this.down=down;
	}
	public void setBounds(float left, float right, float up, float down) 
	{
		this.left=left;
		this.right=right;
		this.up=up;
		this.down=down;
	}
}