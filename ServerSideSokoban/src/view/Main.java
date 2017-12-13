package view;
	
import SokobanServer.ClientHandler;
import SokobanServer.MyServer;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ServerSideModel;
import viewmodel.ServerSideViewModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static final int port = 1235;
	
	public static void main(String[] args) {
		
		MyServer server = new MyServer(port, new ClientHandler());
		
		ServerSideModel.getInstance().setServer(server);
		
		try {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						server.startServer();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ServerSideModel model= ServerSideModel.getInstance();
			ServerSideViewModel viewModel = new ServerSideViewModel(model);
			model.addObserver(viewModel);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			Parent root = (Parent) loader.load();
			MainWindowController controller = loader.getController();
			controller.setViewModel(viewModel);
			
	        Scene scene = new Scene(root, 500, 500);
	    
	        primaryStage.setTitle("Admin View");
	        primaryStage.setScene(scene);
	        primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
