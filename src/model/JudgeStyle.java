package model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import scene.GamePlayScreen;

public class JudgeStyle extends ImageView {

	private Image image;

	public JudgeStyle(int x) {

		if (x == 0) {
			this.image = new Image("Cri_Perfect.png");
		}

		if (x == 1) {
			this.image = new Image("Perfect.png");
		}
		if (x == 2) {
			this.image = new Image("Great.png");
		}
		if (x == 3) {
			this.image = new Image("Good.png");
		}
		if (x == 4) {
			this.image = new Image("Miss.png");
		}

		this.setImage(image);
		this.setTranslateX(700 - 59);
		this.setTranslateY(600 - 170);

	}

	public void show() {
		new Thread(() -> {
			Platform.runLater(() -> {
				GamePlayScreen.instance.getChildren().add(this);
			});
			try {
				Thread.sleep(150);
			} catch (Exception e2) {
			}
			Platform.runLater(() -> {
				GamePlayScreen.instance.getChildren().remove(this);
			});
		}).start();
	}

}
