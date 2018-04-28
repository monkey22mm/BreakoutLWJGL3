package breakout.graphics;

import java.util.ArrayList;

import org.joml.Matrix4f;

public class Font {
	char[] alphabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','!','?',
			'0','1','2','3','4','5','6','7','8','9','.',':',';','&','/',' ','\n'};
	Quad finishedMsg;
	Texture atlas;
	String msg;
	public Font() 
	{
		//finishedMsg = new ArrayList<Model>();
		atlas = new Texture("/textures/fontCustom.png");
	}
	public void createMessage(String msg) 
	{
		int y = 0;
		int x = 0;
		for(int i = 0;i<msg.length();i++) 
		{
			for(int j = 0;j<alphabet.length;j++) 
			{
				if(msg.charAt(i) == alphabet[j]) 
				{
					//if(msg.charAt(i) == '\n') 
					//{
					//	y+=1;
					//	x=0;
					//	continue;
					//}
					
					//finishedMsg.add(createLetter(j,x,y));
					//x+=1;
				}
			}
		}
	}
	public void createParagraph(String msg) {
		if(this.msg == msg) 
		{
			return;
		}
		
		this.msg = msg;
		float textureStepX = 1/12.0f;
		float textureStepY = 1/4.0f;
		float xtex = 0.0f;
		float ytex = 0.0f;
		int x=0;
		int y=0;
		float[] vertices = new float[msg.length() * 8];
		float[] texture = new float[msg.length() * 8];
		for(int i = 0;i<msg.length();i++) {
			for(int j = 0;j<alphabet.length;j++) 
			{
				if(msg.charAt(i) == alphabet[j]) 
				{
					xtex = textureStepX * (j%12);
					ytex = textureStepY * (int)(j/12);
				}
			}
			
			if(msg.charAt(i) == '\n')
			{
				y+=1;
				x=0;
				continue;
			}
			vertices[i*8] = x*12f;	
			vertices[i*8+1] = y*16f+16f; 
			vertices[i*8+2] = x*12f+12f;
			vertices[i*8+3] = y*16f+16f;
			vertices[i*8+4] = x*12f+12f; 
			vertices[i*8+5] = y*16f; 
			vertices[i*8+6] = x*12f; 	
			vertices[i*8+7] = y*16f;
			
			texture[i*8]=xtex;
			texture[i*8+1]=textureStepY+ytex;
			texture[i*8+2]=xtex+textureStepX;
			texture[i*8+3]=textureStepY+ytex;
			texture[i*8+4]=xtex+textureStepX;
			texture[i*8+5]=ytex;
			texture[i*8+6]=xtex;
			texture[i*8+7]=ytex;
			x+=1;
			}
		finishedMsg = new Quad(vertices, texture);
	}

	int projectionLocation = -1;
	public void render(Shader sh, Camera c, Matrix4f target,int x, int y) 
	{
		if(projectionLocation == -1)
		projectionLocation = sh.setLocation("projection");
		Matrix4f finishedProduct = new Matrix4f();
		c.getProjection().mul(target, finishedProduct);
		Matrix4f ballLoc = new Matrix4f().translate(x, y, 0);
		finishedProduct.mul(ballLoc);
		sh.setUniform(projectionLocation, finishedProduct);
		atlas.bind(0);
		finishedMsg.render();
		
	}
}
