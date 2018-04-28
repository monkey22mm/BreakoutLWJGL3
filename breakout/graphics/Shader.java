package breakout.graphics;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

public class Shader {
	private int program_id;
	private int vert_id;
	private int frag_id;
	
	public Shader(String fileName) 
	{
		//Create Program
		program_id = glCreateProgram();
		//Create Vertex Shader
		vert_id = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vert_id, readFile(fileName + ".vs"));
		glCompileShader(vert_id);
		if(glGetShaderi(vert_id, GL_COMPILE_STATUS) != 1) 
		{
			System.err.println(glGetShaderInfoLog(vert_id));
			System.exit(1);
		}
		//Create Fragment Shader
		frag_id = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(frag_id, readFile(fileName + ".fs"));
		glCompileShader(frag_id);
		if(glGetShaderi(frag_id, GL_COMPILE_STATUS) != 1) 
		{
			System.err.println(glGetShaderInfoLog(frag_id));
			System.exit(1);

		}
		//Attach Shaders
		glAttachShader(program_id,vert_id);
		glAttachShader(program_id,frag_id);
		//state attributes 0 = position, 1 = texCoords
		glBindAttribLocation(program_id, 0, "vertices");
		glBindAttribLocation(program_id, 1, "textures");

		//Link Program & validate
		glLinkProgram(program_id);
		if(glGetProgrami(program_id, GL_LINK_STATUS) != 1) 
		{
			System.err.println(glGetProgramInfoLog(program_id));
			System.exit(1);
		}
		glValidateProgram(program_id);
		if(glGetProgrami(program_id, GL_VALIDATE_STATUS) != 1) 
		{
			System.err.println(glGetProgramInfoLog(program_id));
			System.exit(1);
		}
	}
	protected void finalize() throws Throwable 
	{
		glDetachShader(program_id,vert_id);
		glDetachShader(program_id,frag_id);
		glDeleteShader(vert_id);
		glDeleteShader(frag_id);
		glDeleteProgram(program_id);
		super.finalize();
	}
	public void setUniform(int location, float value) 
	{
		if(location != -1)
			glUniform1f(location, value);
	}
	public void setUniform(int location, int value) 
	{
		if(location != -1)
			glUniform1i(location, value);
	}
	public void setUniform(int location, Vector3f value) 
	{
		if(location != -1)
			glUniform3f(location, value.x,value.y, value.z);
	}
	public void setUniform(int location, Matrix4f value) 
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		if(location != -1)
			glUniformMatrix4fv(location, false, buffer);
	}
	public int setLocation(String name) 
	{
		return glGetUniformLocation(program_id, name);
	}
	public void bind() 
	{
		glUseProgram(program_id);
	}
	public void unbind() 
	{
		glUseProgram(0);
	}
	private String readFile(String fileName) 
	{
		StringBuilder string = new StringBuilder();
		BufferedReader br;
		try 
		{
			Reader reader = new InputStreamReader(getClass().getResourceAsStream("/shaders/"+fileName));

			br = new BufferedReader(reader);
			String line;
			while((line = br.readLine())!= null) 
			{
				string.append(line);
				string.append("\n");
			}
			br.close();
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
		return string.toString();
	}
}
