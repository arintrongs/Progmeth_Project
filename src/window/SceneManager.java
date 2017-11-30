package window;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scene.WelcomeScreen;



public class SceneManager {
	private static Stage primaryStage;
	private static Canvas welcomeCanvas = new WelcomeScreen();
	private static Scene welcomeSceen = new Scene(new Pane(welcomeCanvas));
	public static final int SCENE_WIDTH = 600;
	public static final int SCENE_HEIGHT = 800;
	
	public static void initialize(Stage stage) {
		primaryStage = stage;
		primaryStage.show();
		gotoWelcomeScreen();
	}

	public static void gotoWelcomeScreen() {
		//TODO Fill Code
		primaryStage.setScene(welcomeSceen);
		welcomeCanvas.requestFocus();
	}
	
	public static void gotoSceneOf(Pane pane) {
		//TODO Fill Code
		Scene sceneToGo = new Scene(pane,SCENE_WIDTH,SCENE_HEIGHT);
		primaryStage.setScene(sceneToGo);
	}
	
	
}
