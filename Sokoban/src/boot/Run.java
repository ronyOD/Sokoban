package boot;

import java.io.File;
import java.io.FileInputStream;

import application.SokobanApplication;
import application.SokobanKeys;
import controller.MyController;
import controller.server.MyServer;
import errors.Errors;
import javafx.application.Application;
import model.MyModel;
import model.policy.MySokobanPolicy;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		try {
			MyModel model = new MyModel();
			model.setPolicy(new MySokobanPolicy());

			MyView view = new MyView();

			MyController controller = new MyController();
			controller.setModel(model);
			model.addObserver(controller);
			controller.setView(view);
			view.addObserver(controller);

			int port;
			if (args.length > 0 && args.length == 2 && args[0].equals("-server") && (port = Integer.valueOf(args[1])) > 0) {
				MyServer server = new MyServer();
				controller.setServer(server);
				server.setController(controller);
				server.listen(port);
				//added 22.6
				server.start();
			}

			controller.start();

			SokobanKeys.load(new FileInputStream(new File("resources/keys.xml")));

			Application.launch(SokobanApplication.class);

			controller.getThread().join();

		} catch (Exception e) {
			Errors.report(e);
		}
	}
}
