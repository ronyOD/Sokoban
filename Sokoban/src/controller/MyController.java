package controller;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import commands.DisplayLevelCommand;
import commands.DisplayResultsLevelCommand;
import commands.DisplaySolutionDialogLevelCommand;
import commands.ExitLevelCommand;
import commands.LoadLevelCommand;
import commands.MoveLevelCommand;
import commands.SaveLevelCommand;
import controller.server.MyServer;
import errors.Errors;
import interfaces.Commandable;
import interfaces.Stoppable;
import model.MyModel;
import model.data.directions.LevelDirections;
import model.data.loaders.LevelLoaders;
import model.policy.MySokobanPolicy;
import protocol.Command;
import view.MyView;
/**
* <h1>Controller</h1>
* This class represents the controller.
* @author  Ron Yaish
* @version 1.0
* @since   13-7-2017
*/
public class MyController implements Runnable, Observer, Commandable, Stoppable {

	private static MyController controller;

	/**
	* This method returns the controller.
	* @return Nothing.
	*/
	public static MyController it() {
		return controller;
	}

	private MyModel model;
	private MyView view;

	/**
	* This method returns the model.
	* @return MyModel.
	*/
	public MyModel getModel() {
		return model;
	}

	/**
	* This method returns the view.
	* @return MyView.
	*/
	public MyView getView() {
		return view;
	}

	/**
	* This method sets the model.
	* @param model
	*/
	public void setModel(MyModel model) {
		this.model = model;
	}

	/**
	* This method sets the view.
	*@param view
	*/
	public void setView(MyView view) {
		this.view = view;
	}

	private PriorityBlockingQueue<Command> commands = new PriorityBlockingQueue<Command>();

	private volatile boolean stopped;
	private Thread thread;
	private MyServer server;

	/**
	* This method returns the thread in which the controller runs.
	* @return Thread
	*/
	public Thread getThread() {
		return thread;
	}

	/**
	* Constructor
	*/
	public MyController() {
		super();
		controller = this;
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			if (o == model) {
				view.display(model.getLevel());
				if(model.getLevel().IsWon() && model.getLevel().gettotalTime() < 4)
					view.displaySaveResultsDialog();
			} else if (o == view) {

			}
		} catch (Exception e) {
			Errors.report(e);
		}
	}

	/**
	* This method starts the controller thread
	*/
	public void start() {
		thread = new Thread(this, "Controller");
		thread.start();
	}

	/**
	* This method stops the controller.
	*/
	@Override
	public void stop() {
		stopped = true;
	}

	public static void main(String[] args) {
		MyModel model = new MyModel();
		model.setPolicy(new MySokobanPolicy());
		MyView view = new MyView();
		MyController controller = new MyController();
		controller.setModel(model);
		model.addObserver(controller);
		controller.setView(view);
		view.addObserver(controller);
		controller.run();
	}

	/**
	* This method polls commands from the priority queue
	* and executes them.
	*/
	@Override
	public void run() {
		while (!stopped) {
			try {
				Command command = commands.poll(200, TimeUnit.MILLISECONDS);
				if (command != null) {
					switch (command.getCmd()) {
						case "Display":
							try {
								DisplayLevelCommand levelCommand = new DisplayLevelCommand();
								levelCommand.setLevelDisplayer(view);
								levelCommand.setLevel(model.getLevel());
								levelCommand.execute();
							} catch (Exception e) {
								Errors.report(e);
							}
							break;
						case "Load":
							try {
								LoadLevelCommand levelCommand = new LoadLevelCommand();
								levelCommand.setLevelLoader(LevelLoaders.choose(command.getArg()));
								levelCommand.setFile(command.getArg());
								
								levelCommand.execute();
								model.setLevel(levelCommand.getLevel());
								model.notifyObservers();
							} catch (Exception e) {
								Errors.report(e);
							}
							break;
						case "Save":
							try {
								SaveLevelCommand levelCommand = new SaveLevelCommand();
								levelCommand.setLevelLoader(LevelLoaders.choose(command.getArg()));
								levelCommand.setFile(command.getArg());
								levelCommand.setLevel(model.getLevel());
								levelCommand.execute();
							} catch (Exception e) {
								Errors.report(e);
							}
							break;
						case "Move":
							try {
								MoveLevelCommand levelCommand = new MoveLevelCommand();
								levelCommand.setDirection(LevelDirections.choose(command.getArg()));
								levelCommand.setLevel(model.getLevel());
								levelCommand.setPolicy(model.getPolicy());
								levelCommand.execute();
								model.setLevel(levelCommand.getLevel());
								model.notifyObservers();
								view.notifyObservers();
							} catch (Exception e) {
								Errors.report(e);
							}
							break;
						case "Exit":
							try {
								if (server != null) {
									ExitLevelCommand levelCommand = new ExitLevelCommand();
									levelCommand.setStoppable(server);
									levelCommand.execute();
								}
								ExitLevelCommand levelCommand = new ExitLevelCommand();
								levelCommand.setStoppable(this);
								levelCommand.execute();
								view.stop();
								view.notifyObservers();
							} catch (Exception e) {
								Errors.report(e);
							}
							break;
							
						case "Results":
							try{
								
								DisplayResultsLevelCommand levelCommand = new DisplayResultsLevelCommand();
								levelCommand.setLevelDisplayer(view);
								levelCommand.execute();
								
							}
							catch(Exception e){
								Errors.report(e);
							}
							
						case "displaySolutionDialog":
							try {
								DisplaySolutionDialogLevelCommand levelCommand = new DisplaySolutionDialogLevelCommand();
								levelCommand.setSolution(command.getArg().toString());
								levelCommand.setLevelDisplayer(view);
								levelCommand.execute();
								break;
								
							} catch (Exception e) {
								Errors.report(e);
							}
					}
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	/**
	* This method adds command to the queue
	* @param command
	*/
	@Override
	public void command(Command command) {
		commands.add(command);
	}

	/**
	* This method sets the server
	* @param server
	*/
	public void setServer(MyServer server) {
		this.server = server;
	}
}
