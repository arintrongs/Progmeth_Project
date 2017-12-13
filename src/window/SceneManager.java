package window;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import scene.CharacterSelectScreen;
import scene.GamePlayScreen;
import scene.MainMenuScreen;
import scene.ResultScreen;
import scene.WelcomeScreen;
import sharedObject.ThreadHolder;

public class SceneManager {

	private static Stage primaryStage;
	private static Canvas welcomeCanvas = new WelcomeScreen();
	private static Pane currentPane = new Pane(welcomeCanvas);
	private static Scene welcomeSceen = new Scene(currentPane);

	private static Thread fadein, fadeout;

	private static MediaPlayer bgMediaPlayer;
	private static Media mainBGM;
	private static Media resultBGM;

	public static final int SCENE_WIDTH = 800;
	public static final int SCENE_HEIGHT = 600;

	public static void initialize(Stage stage) throws Exception {

		mainBGM = new Media(ClassLoader.getSystemResource("Island-Dreaming.mp3").toURI().toString());
		resultBGM = new Media(ClassLoader.getSystemResource("Peaceful-Evening.mp3").toURI().toString());
		bgMediaPlayer = new MediaPlayer(mainBGM);
		primaryStage = stage;
		primaryStage.show();
		gotoWelcomeScreen();
	}

	public static void gotoWelcomeScreen() {
		primaryStage.setScene(welcomeSceen);
		welcomeCanvas.requestFocus();
	}

	public static void gotoSceneOf(Pane pane) {
		if (pane instanceof GamePlayScreen) {
			bgMediaPlayer.stop();
		}
		if (pane instanceof MainMenuScreen && !(currentPane instanceof CharacterSelectScreen)) {
			bgMediaPlayer.stop();
			bgMediaPlayer = new MediaPlayer(mainBGM);
		}
		if (pane instanceof ResultScreen) {
			bgMediaPlayer.stop();
			bgMediaPlayer = new MediaPlayer(resultBGM);
		}
		fadeout = new Thread(() -> {
			FadeTransition ft = new FadeTransition(Duration.millis(250), currentPane);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);
			ft.play();
			while (ft.getStatus() != javafx.animation.Animation.Status.STOPPED) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		fadein = new Thread(() -> {
			try {
				fadeout.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentPane = pane;
			FadeTransition ft = new FadeTransition(Duration.millis(250), currentPane);
			ft.setFromValue(0.0);
			ft.setToValue(1.0);
			ft.play();
			Platform.runLater(() -> {
				Scene sceneToGo = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);
				primaryStage.setScene(sceneToGo);
				if (!(currentPane instanceof GamePlayScreen))
					bgMediaPlayer.play();
			});
		});
		ThreadHolder.threads.add(fadein);
		ThreadHolder.threads.add(fadeout);
		fadeout.start();
		fadein.start();

	}

	public static Thread getFadeIn() {
		return fadein;
	}

	public static MediaPlayer getBgMediaPlayer() {
		return bgMediaPlayer;
	}
}
