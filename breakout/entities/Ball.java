package breakout.entities;

import org.joml.Matrix4f;

import breakout.graphics.Camera;
import breakout.graphics.Model;
import breakout.graphics.Shader;
import breakout.graphics.Texture;
import breakout.utils.Window;

public class Ball extends Entity{
	Texture tex;
	Model m; 
	private boolean Dead = false;
	int velx,vely;
float[] vertices = new float[] 
		{
				 0f, 16f, 0f,	//TL 0
				 16f, 16f, 0f,	//TR 1
				 16f, 0f, 0f,	//BR 2
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
	public Ball(Window w,int x, int y) {
		super(w, x, y);
		tex = new Texture("/textures/ball.png");
		m = new Model(vertices, texture, indices);
		vely=3;
		velx=3;
	}

	public void update() {
		x+=velx;
		y+=vely;
		if(x<=0 || x+32>=w.getWidth()) 
		{
			velx*=-1.0;
		}
		if(y<=0) 
		{
			vely*=-1.0;
		}
		if(y+16>=w.getHeight())
			setDead(true);
	}
	public void checkCollisionX(Entity e, int width, int height) 
	{
		if(x<e.getX()+5 || x>e.getX()+width-5)
			velx*=-1;
	}
	public boolean checkCollision(Entity e,int width, int height) 
	{
		if(((x < e.getX()+width) && (x+16 > e.getX())) && 
				((y < e.getY()+height) && (y+16 > e.getY()))) 
		{
			

			vely*=-1;
			return true;
		}
		
		return false;

	}
	int projectionLocation = -1;

	public void render(Shader sh,Camera cam,Matrix4f target) {
		if(projectionLocation == -1)
			projectionLocation = sh.setLocation("projection");
		Matrix4f finishedProduct = new Matrix4f();
		cam.getProjection().mul(target, finishedProduct);
		Matrix4f ballLoc = new Matrix4f().translate(x, y, 0);
		finishedProduct.mul(ballLoc);
		sh.setUniform(projectionLocation, finishedProduct);
		tex.bind(0);
		m.render();		
	}

	public boolean isDead() {
		return Dead;
	}

	public void setDead(boolean dead) {
		Dead = dead;
	}

	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}

	public int getVely() {
		return vely;
	}

	public void setVely(int vely) {
		this.vely = vely;
	}

}
