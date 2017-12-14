package application;

import javafx.application.Application;
import javafx.stage.Stage;
import scene.SceneManager;
import sharedObject.ThreadHolder;

public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
			ThreadHolder.instance = new ThreadHolder();
			SceneManager.initialize(primaryStage);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Train to the beat!");
			primaryStage.centerOnScreen();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() {

		SceneManager.getBgMediaPlayer().stop();
		for (int i = 0; i < ThreadHolder.instance.getThreads().size(); i++) {
			try {
				ThreadHolder.instance.getThreads().get(i).interrupt();
			} catch (Exception e) {

			}
		}

	}
}
