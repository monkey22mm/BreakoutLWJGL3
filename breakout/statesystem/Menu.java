package breakout.statesystem;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

import java.nio.DoubleBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import breakout.graphics.Button;
import breakout.graphics.Camera;
import breakout.graphics.Font;
import breakout.graphics.Model;
import breakout.graphics.Shader;
import breakout.graphics.Texture;
import breakout.utils.Window;

public class Menu extends State{
	Camera cam;
	Shader textureShader,spriteShader;
	Matrix4f target;
	Texture tex;
	Model m; 
	Button[] options;
	Font info;
	double mousex,mousey;
	DoubleBuffer b1,b2;
	boolean RenderInfo = false;
	public Menu(Window w,StateHandler sh) {
		super(w,sh);
		cam = new Camera(w.getWidth(), w.getHeight());
		textureShader = new Shader("basic(vert,tex)");
		spriteShader = new Shader("basic(ss)");
		target = new Matrix4f();
		options = new Button[3];
		options[0] = new Button("PLAY");

		options[1] = new Button("ABOUT");

		options[2] = new Button("QUIT");


		float[] vertices = new float[] 
				{
						 0f, 128f, 0f,	//TL 0
						 640f, 128f, 0f,	//TR 1
						 640f, 0f, 0f,	//BR 2
						 0f, 0f, 0f,	//BL 3
				};
		float[] texture = new float[] 
			{
					0,1/4f,	//TL 0
					1,1/4f,	//TR 1
					1,0,	//BR 2
					0,0,	//BL 3
			};	
		int[] indices = new int[] 
			{
					0,1,2,
					2,3,0
			};
		tex = new Texture("/textures/logo.png");
		m = new Model(vertices, texture, indices);
		b1 = BufferUtils.createDoubleBuffer(1);
		b2 = BufferUtils.createDoubleBuffer(1);
		info = new Font();
		info.createParagraph(
				"WELCOME TO BREAKOUT!\n"
				+ "THIS GAME WAS CREATED IN LESS THAN 48 HOURS AS A PROGRAMMING EXERCISE\n"
				+ "THE SOURCE CAN BE FOUND HERE: WWW.");
	}

	public void update() {
		glfwGetCursorPos(w.getWindow(), b1, b2);

		mousex = b1.get(0);
		mousey = b2.get(0);
		
		RenderInfo = options[1].getHovered();
		if(w.getInput().isMouseButtonDown(0)) {
			if(options[0].getHovered()) 
			{
				sh.changeState(sh.GAME_STATE);
			}
			
			if(options[2].getHovered()) 
			{
				System.exit(0);
			}
		}
	}
	float timer = 0;
	int projectionLocation = -1;
	int texModLocation = -1;
	public void render() {
		if(projectionLocation == -1)
		projectionLocation = spriteShader.setLocation("projection");
		if(texModLocation == -1)
		texModLocation = spriteShader.setLocation("texModifier");
		timer+=.1f;
		spriteShader.bind();
		Matrix4f finishedProduct = new Matrix4f();	
		Matrix4f texStep = new Matrix4f().translate(0, 1/4f*(int)(Math.sin(timer)*2+2),0);
		cam.getProjection().mul(target, finishedProduct);
		Matrix4f ballLoc = new Matrix4f().translate(w.getWidth()/2-700/2, 100, 0);
		finishedProduct.mul(ballLoc);
		spriteShader.setUniform(projectionLocation, finishedProduct);
		spriteShader.setUniform(texModLocation, texStep);
		tex.bind(0);
		m.render();	
		textureShader.bind();
		for(int i = 0;i<3;i++)
			options[i].render(textureShader,spriteShader, cam, new Matrix4f().scale(4),w.getWidth()/8-32,64+i*32,(int)mousex,(int)mousey);
		if(RenderInfo)
		info.render(textureShader, cam, target, 0, 0);
	}

}
