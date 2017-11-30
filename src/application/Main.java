package application;

import java.util.ArrayList;

import gameInterface.IRenderable;
import gameInterface.TimingLine;
import gameLogic.GameManager;
import gameLogic.Note;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
<<<<<<< HEAD
import javafx.util.Duration;
||||||| merged common ancestors
=======
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

}
