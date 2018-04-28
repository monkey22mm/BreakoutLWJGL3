package breakout.statesystem;

import org.joml.Matrix4f;

import breakout.entities.Ball;
import breakout.entities.Block;
import breakout.entities.Pad;
import breakout.graphics.Camera;
import breakout.graphics.Font;
import breakout.graphics.Shader;
import breakout.utils.Window;

public class Game extends State{
	Camera cam;
	Shader textureShader;
	Block[] blocks;
	Matrix4f target;
	Ball ball;
	Pad pad;
	Font scoreCounter,lifeCounter;
	Font startCountdown;
	int live_count = 3;
	float countdownTimer = 3;
	int score = 0;
	public Game(Window w,StateHandler sh) {
		super(w,sh);
		cam = new Camera(w.getWidth(), w.getHeight());
		textureShader = new Shader("basic(vert,tex)");
		
		target = new Matrix4f();
		ball = new Ball(w,w.getWidth()/2-32,w.getHeight()/2-24);
		pad = new Pad(w,w.getWidth()/2-64,w.getHeight()/2+128);
		blocks = new Block[80];
		for(int i = 0;i<blocks.length;i++)
			blocks[i] = new Block(w, 128+i%(8)*132,(int)100+(i/8)*24);
		scoreCounter = new Font();
		lifeCounter = new Font();
		startCountdown = new Font();
		lifeCounter.createParagraph("LIVES:"+live_count);
		scoreCounter.createParagraph("SCORE:"+score);	

	}

	public void update() {
		
		if(countdownTimer<=0) {
		ball.update();
		pad.update();
		ball.checkCollision(pad,136,8);
		for(int i = 0;i<blocks.length;i++) {
			if(blocks[i].isEnabled())
			if(ball.checkCollision(blocks[i],128,16)) 
			{
				ball.checkCollisionX(blocks[i],128,16);
				blocks[i].setEnabled(false);
				scoreCounter.createParagraph("SCORE:"+score);	
				score+=5;
			}
		}
		if(ball.isDead()) 
		{
			live_count-=1;
			ball.setX(w.getWidth()/2-32);
			ball.setY(w.getHeight()/2-24);
			ball.setVelx(3);
			ball.setVely(3);
			ball.setDead(false);
			pad.setX(w.getWidth()/2-64);
			pad.setY(w.getHeight()/2+128);
			countdownTimer=3;
			lifeCounter.createParagraph("LIVES:"+live_count);
		}
		if(score>0 && score % 400 == 0)
			resetBlocks();
		if(live_count<=0) 
		{
			resetBlocks();
			pad.setX(w.getWidth()/2-64);
			pad.setY(w.getHeight()/2+128);
			ball.setX(w.getWidth()/2-32);
			ball.setY(w.getHeight()/2-24);
			ball.setVelx(3);
			ball.setVely(3);
			live_count = 3;
			countdownTimer = 3;
			score = 0;
			lifeCounter.createParagraph("LIVES:"+live_count);
			scoreCounter.createParagraph("SCORE:"+score);	
			startCountdown.createParagraph(""+(int)(countdownTimer+1));
			sh.changeState(sh.MENU_STATE);
		}
	}
	}
	private void resetBlocks() 
	{
		for(int i = 0;i<blocks.length;i++)
			blocks[i].setEnabled(true);
	}
	public void render() {
		if(countdownTimer>0) {
			startCountdown.createParagraph(" "+(int)(countdownTimer+1));
			countdownTimer-=.02f;
		}	
			
		
		
		//font.createParagraph(x + "  ");
		textureShader.bind();
		ball.render(textureShader, cam, target);
		pad.render(textureShader, cam, target);
		for(int i = 0;i<blocks.length;i++) {
			if(blocks[i].isEnabled())
				blocks[i].render(textureShader, cam, target);
		}
		scoreCounter.render(textureShader, cam, target,0,0);		
		lifeCounter.render(textureShader, cam, target,0,16);
		if(countdownTimer>0)
			startCountdown.render(textureShader, cam, new Matrix4f().scale(4),w.getWidth()/8-16,w.getHeight()/8);

	}

}
