package breakout.statesystem;

import breakout.utils.Window;

public class StateHandler {
	private State[] states;
	private int STATE_CURRENT;
	public int MENU_STATE = 0;
	public int GAME_STATE = 1;
	public StateHandler(Window w) 
	{
		STATE_CURRENT = 0;
		states = new State[2];
		states[0] = new Menu(w,this);
		states[1] = new Game(w,this);
	}
	public void update() 
	{
		states[STATE_CURRENT].update();
	}
	public void render() 
	{
		states[STATE_CURRENT].render();
	}
	public void changeState(int state) 
	{
		STATE_CURRENT = state;
	}
}

