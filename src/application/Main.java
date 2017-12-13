package application;

import javafx.application.Application;
import javafx.stage.Stage;
import sharedObject.ThreadHolder;
import window.SceneManager;

public class Main extends Application {

	public void start(Stage primaryStage) {
		try {
			SceneManager.initialize(primaryStage);
			primaryStage.setTitle("GAME");
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
		for (int i = 0; i < ThreadHolder.threads.size(); i++) {
			try {
				ThreadHolder.threads.get(i).interrupt();
			} catch (Exception e) {

			}
		}

	}
}
