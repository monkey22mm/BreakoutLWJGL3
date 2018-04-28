package breakout.statesystem;

import breakout.utils.Window;

public abstract class State {
	protected Window w;
	protected StateHandler sh;
	public State(Window w,StateHandler sh) {
		this.w=w;
		this.sh=sh;
	}
	public abstract void update();
	public abstract void render();

}
