package scene;


import gameLogic.GameManager;
import gameLogic.Note;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import window.SceneManager;

public class WelcomeScreen extends Canvas {
	private static final Font TITLE_FONT = Font.font("Monospace",80) ;
			
	private static final Font MENU_FONT = Font.font("Monospace",40) ;
	
	private Image bg = new Image("bg10.png"); 


	public WelcomeScreen() {
		super(SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
		
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage(bg, 0, 0, SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);

		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFill(Color.WHITE);
		gc.setFont(TITLE_FONT);
		gc.fillText("BONCHON", SceneManager.SCENE_WIDTH / 2+60, SceneManager.SCENE_HEIGHT / 4);
		gc.setFont(MENU_FONT);
		gc.fillText("Press [Enter] to start", SceneManager.SCENE_WIDTH / 2, SceneManager.SCENE_HEIGHT * 3 / 4);
		
		
		this.addKeyEventHandler();
	}
	
	private void addKeyEventHandler() {
		//TODO Fill Code
		GraphicsContext gc = this.getGraphicsContext2D();
		 this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			 public void handle(KeyEvent event) {
				 if(event.getCode()==KeyCode.ENTER) {
					 /*
					 Pane mainMenu = new MainMenuScreen();
					 SceneManager.gotoSceneOf(mainMenu);
					 */
					 GameManager.newGame();
					 
				 }
				 if(event.getCode()==KeyCode.ESCAPE) {
					 Platform.exit();
				 }
			 }
		 });
		 
	}
}
