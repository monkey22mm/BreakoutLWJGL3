package breakout.graphics;

import org.joml.Matrix4f;

public class Button {
	Font message;	
	Texture tex;
	Model m; 
	boolean Hovered =  false;
	
	public Button(String msg) 
	{
		message = new Font();
		message.createParagraph(msg);
		float[] vertices = new float[] 
				{
						 0f, 24f, 0f,	//TL 0
						 120f, 24f, 0f,	//TR 1
						 120f, 0f, 0f,	//BR 2
						 0f, 0f, 0f,	//BL 3
				};
		float[] texture = new float[] 
			{
					0,1/2f,	//TL 0
					1,1/2f,	//TR 1
					1,0,	//BR 2
					0,0,	//BL 3
			};	
		int[] indices = new int[] 
			{
					0,1,2,
					2,3,0
			};
		tex = new Texture("/textures/button.png");
		m = new Model(vertices, texture, indices);
	}
	private boolean isHovered(int mx,int my,int x,int y) 
	{
		if(mx>x && mx< x+120) 
		{
			if(my>y && my< y+24) 
			{
				return true;
			}
		}
		return false;
	}
	public boolean getHovered() 
	{
		return Hovered;
	}
	int projectionLocation = -1;
	int texModLocation = -1;

	public void render(Shader texSh,Shader ssSh, Camera c, Matrix4f target,int x, int y,int mx, int my) 
	{
		if(projectionLocation == -1)
		projectionLocation = ssSh.setLocation("projection");
		if(texModLocation == -1)
		texModLocation = ssSh.setLocation("texModifier");
		Hovered = isHovered(mx/4,my/4,x-36, y-4);
		ssSh.bind();
		Matrix4f finishedProduct = new Matrix4f();	
		Matrix4f texStep = new Matrix4f().translate(0, 1/2f*(Hovered ? 1:0),0);
		c.getProjection().mul(target, finishedProduct);
		Matrix4f ballLoc = new Matrix4f().translate(x-36, y-4, 0);
		finishedProduct.mul(ballLoc);
		ssSh.setUniform(projectionLocation, finishedProduct);
		ssSh.setUniform(texModLocation, texStep);
		tex.bind(0);
		m.render();	
		texSh.bind();
		message.render(texSh, c, target, x, y);
	}
}
