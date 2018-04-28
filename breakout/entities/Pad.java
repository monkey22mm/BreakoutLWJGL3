package breakout.entities;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;

import breakout.graphics.Camera;
import breakout.graphics.Model;
import breakout.graphics.Shader;
import breakout.graphics.Texture;
import breakout.utils.Window;

public class Pad extends Entity{
	Texture tex;
	Model m;
	float[] vertices = new float[] 
			{
					 0f, 8f, 0f,	//TL 0
					 136f, 8f, 0f,	//TR 1
					 136f, 0f, 0f,	//BR 2
					 0f, 0f, 0f,	//BL 3
			};
	float[] texture = new float[] 
		{
				0,1,	//TL 0
				1,1,	//TR 1
				1,0,	//BR 2
				0,0,	//BL 3
		};	
	int[] indices = new int[] 
		{
				0,1,2,
				2,3,0
		};
	public Pad(Window w, int x, int y) {
		super(w, x, y);
		tex = new Texture("/textures/pad.png");
		m = new Model(vertices, texture, indices);	
		}

	@Override
	public void update() {
		if (w.getInput().isKeyDown(GLFW_KEY_A)) 
		{
			x-=4;
		}
		if (w.getInput().isKeyDown(GLFW_KEY_D)) 
		{
			x+=4;
		}
	}
	int projectionLocation = -1;

	public void render(Shader sh, Camera c, Matrix4f target) {
		if(projectionLocation == -1)
			projectionLocation = sh.setLocation("projection");
		Matrix4f finishedProduct = new Matrix4f();
		c.getProjection().mul(target, finishedProduct);
		Matrix4f ballLoc = new Matrix4f().translate(x, y, 0);
		finishedProduct.mul(ballLoc);
		sh.setUniform(projectionLocation, finishedProduct);
		tex.bind(0);
		m.render();				
	}

}
