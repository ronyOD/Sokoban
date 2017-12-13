package application;

import java.io.File;
import java.io.FileInputStream;

import client.TCPClient;
import controller.MyController;
import displayers.LevelDisplayer;
import displayers.LevelDisplayerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import leaderboard.DAL;
import leaderboard.DataModel;
import leaderboard.SaveResults;
import leaderboard.TableController;
import model.data.cells.Box;
import model.data.cells.Cell;
import model.data.cells.Floor;
import model.data.cells.Target;
import model.data.cells.Wall;
import model.data.levels.Level;
import model.data.loaders.LevelLoaderException;
import model.data.loaders.LevelLoaders;
import model.data.positions.Position;
import protocol.Command;
import solution.presenter.DisplaySolutionDialog;

/**
* <h1>SokobanApplication</h1>
* The class starts the Sokoban GUI,
* Handles GUI events.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/

public class SokobanApplication extends Application implements LevelDisplayer {

	private static Image FLOOR;
	private static Image WALL;
	private static Image BOX;
	private static Image TARGET;
	private static Image PLAYER;
	
	private static Stage pStage;

	private static SokobanApplication sokobanApplication;

	/**
	* This method gets the JavaFX stage.
	* @return Stage.
	*/
	public static Stage getpStage() {
		return pStage;
	}


	/**
	* This method sets the JavaFX stage.
	* @param pStage
	*/
	public static void setpStage(Stage pStage) {
		SokobanApplication.pStage = pStage;
	}


	/**
	* This method returns the SokobanApplication
	* @return Stage.
	*/
	public static SokobanApplication it() {
		return sokobanApplication;
	}
	

	/**
	* This method starts the Sokoban GUI.
	* Event handlers are defined here for all GUI events.
	* @param stage
	* @exception Exception
	*/
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Sokoban.fxml"));
		Scene scene = new Scene(root, 640, 400);
		stage.setScene(scene);
		stage.setTitle("Sokoban");
		SokobanApplication.setpStage(stage);

		FLOOR = new Image(new FileInputStream(new File("resources/floor.png")));
		WALL = new Image(new FileInputStream(new File("resources/wall.png")));
		BOX = new Image(new FileInputStream(new File("resources/box.png")));
		TARGET = new Image(new FileInputStream(new File("resources/target.png")));
		PLAYER = new Image(new FileInputStream(new File("resources/player.png")));

		final FileChooser fileChooser = new FileChooser();
		for (String supported : LevelLoaders.supported()) {
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(supported.toUpperCase() + " (*." + supported + ")", "*." + supported));
		}
		
		//trial - how to get the solution from server
		SokobanController.it().getSolution().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						//MyController.it().getModel()
						TCPClient client = new TCPClient();
						client.setHost("localhost");
						client.setPort(1235);
						String levelName = MyController.it().getModel().getLevel().getLevelName();
						String userName = System.getProperty("user.name");
						String solution = client.send(levelName, userName, new File(levelName + ".txt"));
						MyController.it().command(new Command("displaySolutionDialog", solution, Command.NORMAL));
					}
				}).start();
			}
		});
		

		SokobanController.it().getLoad().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fileChooser.setTitle("Load Level");
				File file = fileChooser.showOpenDialog(stage);
				if (file != null) {
					MyController.it().command(new Command("Load", file.getName(), Command.NORMAL));
					
				}
			}
		});
		SokobanController.it().getSave().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fileChooser.setTitle("Save Level");
				File file = fileChooser.showSaveDialog(stage);
				if (file != null) {
					MyController.it().command(new Command("Save", file.getName(), Command.NORMAL));
				}
			}
		});
		SokobanController.it().getExit().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MyController.it().command(new Command("Exit", null, Command.NORMAL));
			}
		});
		//display leader board results
		SokobanController.it().getResults().setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				
				MyController.it().command(new Command("Results", null, Command.NORMAL));
			}
		});

		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (SokobanKeys.it().isUp(event)) {
					MyController.it().command(new Command("Move", "up", Command.NORMAL));
				} else if (SokobanKeys.it().isDown(event)) {
					MyController.it().command(new Command("Move", "down", Command.NORMAL));
				} else if (SokobanKeys.it().isLeft(event)) {
					MyController.it().command(new Command("Move", "left", Command.NORMAL));
				} else if (SokobanKeys.it().isRight(event)) {
					MyController.it().command(new Command("Move", "right", Command.NORMAL));
				}
			}
		});

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				MyController.it().command(new Command("Exit", null, Command.NORMAL));
			}
		});

		sokobanApplication = this;

		stage.setResizable(false);
		stage.show();
	}

	/**
	* This method displays the level.
	* @param level
	* @exception LevelDisplayerException
	*/
	@Override
	public void display(Level level) throws LevelDisplayerException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					Pane pane = SokobanController.it().getLevel();

					Position position;
					Cell cell;
					VBox vBox = new VBox();
					for (int h = level.height(); h > 0; h--) {
						HBox hBox = new HBox();
						for (int w = 1;; w++) {
							position = new Position(h, w);
							if ((cell = level.get(position)) == null)
								break;
							hBox.getChildren().add(new ImageView(level.getPlayerPosition().equals(position) ? encodePlayer() : encode(cell)));
						}
						vBox.getChildren().add(hBox);
					}

					pane.getChildren().clear();
					pane.getChildren().add(vBox);
					pane.getParent().getParent().getParent().autosize();

				} catch (Exception e) {
					//Errors.report(e);
				}
			}
		});
	}


	/**
	* This method encodes level cells to images.
	* @param cell
	* @exception LevelDisplayerException
	* @return Image.
	*/
	private Image encode(Cell cell) throws LevelLoaderException {
		if (cell instanceof Floor) {
			return FLOOR;
		} else if (cell instanceof Wall) {
			return WALL;
		} else if (cell instanceof Box) {
			return BOX;
		} else if (cell instanceof Target) {
			return TARGET;
		}
		throw new LevelLoaderException("unknown cell type: " + cell);
	}

	/**
	* This method encodes the player.
	* @return Image.
	*/
	private Image encodePlayer() {
		return PLAYER;
	}

	/**
	* This method terminates the app.
	*/
	public void exit() {
		Platform.exit();
	}

	/**
	* This method displays the save results dialog.
	*/
	@Override
	public void displaySaveResultsDialog() {
		SaveResults r = new SaveResults();
		Platform.runLater(r);
	}

	/**
	* This method displays the results.
	*/
	@Override
	public void displayResults() {
		new TableController().initModel(new DataModel(new DAL().GetData(MyController.it().getModel().getLevel().getLevelName())));
	}


	/**
	* This method displays the solution dialog.
	*/
	@Override
	public void displaySolutionDialog(String solution) {
		DisplaySolutionDialog d = new DisplaySolutionDialog(solution);
		Platform.runLater(d);
		
	}
	
	
}
