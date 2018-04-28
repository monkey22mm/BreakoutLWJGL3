package breakout.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;


public class Texture {
	private int id,width,height;
	ByteBuffer pixels;
	public Texture(String filename) 
	{
		BufferedImage bi;
		try{
			bi = ImageIO.read(getClass().getResourceAsStream(filename));
			width=bi.getWidth();
			height=bi.getHeight();
			
			int[] pixels_raw = new int[width * height * 4];
			pixels_raw  = bi.getRGB(0,0, width, height, null, 0, width);
			
			pixels = BufferUtils.createByteBuffer(width * height * 4);
			for(int i = 0; i < height;i++) 
			{
				for(int j = 0; j < width;j++) 
				{
					int pixel = pixels_raw[i*width + j];
					pixels.put((byte)((pixel >> 16) & 0xFF));//red
					pixels.put((byte)((pixel >> 8) & 0xFF));//green
					pixels.put((byte)(pixel & 0xFF));//blue
					pixels.put((byte)((pixel >> 24) & 0xFF));//alpha

				}
			}
			pixels.flip();
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width,height,0,GL_RGBA, GL_UNSIGNED_BYTE,pixels);
			
		}catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	public void bind(int sampler) 
	{
		//bind & use sampler
		if(sampler >=0 && sampler <= 31)
		glActiveTexture(GL_TEXTURE0 + sampler);
		glBindTexture(GL_TEXTURE_2D, id);
	}
	public void changePixels(int x, int y, int w, int h,Texture t) 
	{
		glTexSubImage2D(GL_TEXTURE_2D,0,x,y,w,h,GL_RGBA,GL_UNSIGNED_BYTE,t.pixels);
	}
	protected void finalize() throws Throwable
	{
		glDeleteTextures(id);
		super.finalize();
	}
}