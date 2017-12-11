import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuickFlip extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ImageView front = new ImageView(new Image("Knight1.png"));

		ScaleTransition stHideFront = new ScaleTransition(Duration.millis(300), front);
		stHideFront.setFromX(1);
		stHideFront.setToX(0);

		ImageView back = new ImageView(new Image("SpellCaster1.png"));
		back.setScaleX(0);

		ScaleTransition stShowBack = new ScaleTransition(Duration.millis(300), back);
		stShowBack.setFromX(0);
		stShowBack.setToX(1);

		stHideFront.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stShowBack.play();
			}
		});

		StackPane root = new StackPane();
		root.getChildren().addAll(back);
		Scene scene = new Scene(root, 800, 800);
		primaryStage.setScene(scene);

		primaryStage.show();
		stHideFront.play();

	}

	private Scene createScene(Node card, Node card2) {
		StackPane root = new StackPane();
		root.getChildren().addAll(card, card2);

		Scene scene = new Scene(root, 600, 700, true, SceneAntialiasing.BALANCED);
		scene.setCamera(new PerspectiveCamera());

		return scene;
	}

	private Node createCard() {
		return new ImageView(new Image("Knight1.png"));
	}

	private Node createCard2() {
		return new ImageView(new Image("SpellCaster1.png"));
	}

	private RotateTransition createRotator(Node card) {
		RotateTransition rotator = new RotateTransition(Duration.millis(1000), card);
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(0);
		rotator.setToAngle(90);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(1);

		return rotator;
	}

	private RotateTransition createRotator2(Node card) {
		RotateTransition rotator = new RotateTransition(Duration.millis(1000), card);
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(90);
		rotator.setToAngle(0);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(1);

		return rotator;
	}

	public static void main(String[] args) {
		launch();
	}
}