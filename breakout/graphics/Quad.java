package breakout.graphics;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Quad {
	private int draw_count;
	private int v_id;
	public int t_id;

	public Quad(float[] vertices, float[] tex_coords) 
	{
		draw_count = vertices.length;
		//vertices
		v_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_DYNAMIC_DRAW);
		//texture coords
		t_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(tex_coords), GL_DYNAMIC_DRAW);
		//bind vertices			//vertices = 0
		glEnableVertexAttribArray(0);
		//bind texCoords		//texCoords = 1
		glEnableVertexAttribArray(1);
		//unbind
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	public void destroy() 
	{
		glDeleteBuffers(v_id);
		glDeleteBuffers(t_id);
	}
	public void render() 
	{
		
		//vertices
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glVertexAttribPointer(0, 2, GL_FLOAT,false, 0, 0);
		//texture coords
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		glVertexAttribPointer(1, 2, GL_FLOAT,false, 0, 0);
		//draw
		glDrawArrays(GL_QUADS, 0, draw_count);
		//unbind
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	public FloatBuffer createBuffer(float[] data) 
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}