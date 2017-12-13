package cli;

import java.util.Scanner;

import commands.ExitLevelCommand;
import commands.HelpLevelCommand;
import controller.MyController;
import controller.client.MyClient;
import errors.Errors;
import interfaces.Commandable;
import interfaces.Helpable;
import interfaces.Startable;
import interfaces.Stoppable;
import model.MyModel;
import model.policy.MySokobanPolicy;
import protocol.Command;
import view.MyView;

public class CLI implements Runnable, Startable, Stoppable, Helpable {

	private boolean stopped;

	private Commandable commandable;

	private Thread thread;

	private Scanner scanner;

	public Scanner getScanner() {
		return scanner;
	}

	@Override
	public void run() {
		// prepare scanner
		scanner = new Scanner(System.in);
		try {
			while (!stopped) {
				System.out.print("> ");
				String line = scanner.nextLine();
				String[] split = line.split(" ");
				String cmd = split[0];
				String arg = split.length == 2 ? split[1] : null;
				// System.out.println("Cmd: " + cmd + " Arg: " + arg);
				if (commandable == null || cmd.isEmpty())
					continue;
				if (commandable != null)
					commandable.command(new Command(cmd, arg, Command.NORMAL));
				switch (cmd) {
					case "Exit":
						try {
							ExitLevelCommand levelCommand = new ExitLevelCommand();
							levelCommand.setStoppable(this);
							levelCommand.execute();
						} catch (Exception e) {
							Errors.report(e);
						}
						break;
					case "Help":
						try {
							HelpLevelCommand levelCommand = new HelpLevelCommand();
							levelCommand.setHelpable(this);
							levelCommand.execute();
						} catch (Exception e) {
							Errors.report(e);
						}
						break;
				}
			}
		} catch (Exception e) {
			Errors.report(e);
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

	@Override
	public void stop() {
		stopped = true;
	}

	public static void main(String[] args) {
		try {
			CLI cli = new CLI();
			if (args.length == 3 && args[0].equals("-client")) {
				MyClient client = new MyClient();
				client.setHost(args[1]);
				client.setPort(Integer.valueOf(args[2]));

				cli.setCommandable(client);
			} else {
				MyModel model = new MyModel();
				model.setPolicy(new MySokobanPolicy());

				MyView view = new MyView();

				MyController controller = new MyController();

				controller.setModel(model);
				model.addObserver(controller);

				controller.setView(view);
				view.addObserver(controller);

				cli.setCommandable(controller);
			}
			cli.start();
			cli.getThread().join();

		} catch (Exception e) {
			Errors.report(e);
		}
	}

	public void setCommandable(Commandable commandable) {
		this.commandable = commandable;
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public void help() {
		System.out.println("Load file");
		System.out.println("Display");
		System.out.println("Move {up, down, left, right}");
		System.out.println("Save file");
		System.out.println("Help");
		System.out.println("Exit");
	}

	@Override
	public void start() {
		thread = new Thread(this, "CLI");
		thread.start();
	}
}
