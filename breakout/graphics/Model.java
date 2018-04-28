package breakout.graphics;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Model {
	private int draw_count;
	private int v_id;
	private int t_id;
	private int i_id;

	public Model(float[] vertices, float[] tex_coords,int[] indices) 
	{
		draw_count = indices.length;
		//vertices
		v_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
		//texture coords
		t_id = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(tex_coords), GL_STATIC_DRAW);
		//indices & create intbuffer
		IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
		buffer.put(indices);
		buffer.flip();
		i_id = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		//bind vertices			//vertices = 0
		glEnableVertexAttribArray(0);
		//bind texCoords		//texCoords = 1
		glEnableVertexAttribArray(1);
		//unbind
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	protected void finalize() throws Throwable 
	{
		glDeleteBuffers(v_id);
		glDeleteBuffers(t_id);
		glDeleteBuffers(i_id);
		super.finalize();
	} 
	public void render() 
	{
		
		
		//vertices
		glBindBuffer(GL_ARRAY_BUFFER, v_id);
		glVertexAttribPointer(0, 3, GL_FLOAT,false, 0, 0);
		//texture coords
		glBindBuffer(GL_ARRAY_BUFFER, t_id);
		glVertexAttribPointer(1, 2, GL_FLOAT,false, 0, 0);
		//indices
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
		//draw
		glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0);
		//unbind
		//glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		///glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	private FloatBuffer createBuffer(float[] data) 
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
