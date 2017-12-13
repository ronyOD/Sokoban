package solution.presenter;

import java.util.TimerTask;

import controller.MyController;
import protocol.Command;

public class MoveTimerTask extends TimerTask {

	private int priority;
	private String direction;
	private MyController controller;
	
	public MoveTimerTask(MyController controller){
		this.controller = controller;
	}
	
	public void setDirection(String direction){
		this.direction = direction;
	}
	public MoveTimerTask(int priority, String direction, MyController controller) {
		this.priority = priority;
		this.controller = controller;
		this.direction = direction;
	}
	@Override
	public void run() {
			controller.command(new Command("Move", direction, priority));
	}

}
