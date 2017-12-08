package model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scene.GamePlayScreen;

public class JudgeStyle extends ImageView {

	private Image img;
	private GamePlayScreen gamePlayScreen;

	public JudgeStyle(int x, GamePlayScreen gamePlayScreen) {
		this.gamePlayScreen = gamePlayScreen;
		if (x == 0) {
			this.img = new Image("Cri_Perfect.png");
		}

		if (x == 1) {
			this.img = new Image("Perfect.png");
		}
		if (x == 2) {
			this.img = new Image("Great.png");
		}
		if (x == 3) {
			this.img = new Image("Good.png");
		}
		if (x == 4) {
			this.img = new Image("Miss.png");
		}

		this.setImage(img);
		this.setTranslateX(700 - 59);
		this.setTranslateY(600 - 170);

	}

	public void show() {
		new Thread(() -> {
			Platform.runLater(() -> {
				gamePlayScreen.getChildren().add(this);
			});
			try {
				Thread.sleep(150);
			} catch (Exception e2) {
			}
			Platform.runLater(() -> {
				gamePlayScreen.getChildren().remove(this);
			});
		}).start();
	}

}
