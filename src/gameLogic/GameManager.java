package gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;
import model.Clown;
import model.Field;
import model.Hero;
import model.Knight;
import model.Monster;
import model.Priest;
import model.SpellCaster;
import scene.GamePlayScreen;
import scene.MainMenuScreen;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class GameManager {

	private static Hero currentCha;
	private static Monster currentMon;
	private static int currentNumMon;
	private static int currentBoss;
	private static Hero knight;
	private static Hero spellCaster;
	private static Hero clown;
	private static Hero priest;
	private static Monster monster;
	// private static Monster boss;
	private static Field field;
	private static String currentMode;
	private static List<Integer> gameResult;
	private static List<Double> scoreBefore;
	private static ArrayList<Image> notesImage;

	public static void newGame() {
		knight = new Knight("Knight", 1, "skill1");
		spellCaster = new SpellCaster("SpellCaster", 1, "skill2");
		clown = new Clown("Clown", 1, "skill3");
		priest = new Priest("Priest", 1, "skill4");
		monster = new Monster("Monster", 1);
		// boss = new Boss("Boss", 1, "skillBoss");
		field = new Field();
		currentMon = monster;
		RenderableHolder.getInstance().add(knight);
		RenderableHolder.getInstance().add(spellCaster);
		RenderableHolder.getInstance().add(clown);
		RenderableHolder.getInstance().add(priest);
		RenderableHolder.getInstance().add(monster);
		// RenderableHolder.getInstance().add(boss);
		RenderableHolder.getInstance().add(field);
		currentBoss = 1;
		currentNumMon = 1;
		SceneManager.gotoSceneOf(new MainMenuScreen());
		gameResult = new ArrayList<>(Collections.nCopies(5, 0));
		scoreBefore = new ArrayList<>(Collections.nCopies(5, 0.0));

		notesImage = new ArrayList<>();
		notesImage.add(new Image("up-arrow.png"));
		notesImage.add(new Image("down-arrow.png"));
		notesImage.add(new Image("left-arrow.png"));
		notesImage.add(new Image("right-arrow.png"));

	}

	public static void UpdateScoreBefore() {
		scoreBefore.set(0, currentCha.getLevel() * 1.0);
		scoreBefore.set(1, currentCha.getAtk());
		scoreBefore.set(2, currentCha.getCurrentExp() * 1.0);
		scoreBefore.set(3, currentCha.getCurrentMaxExp() * 1.0);
	}

	public static List<Double> getScoreBefore() {
		return scoreBefore;
	}

	public static void setScoreBefore(List<Double> scoreBefore) {
		GameManager.scoreBefore = scoreBefore;
	}

	public static Hero getCurrentCha() {
		return currentCha;
	}

	public static void setCurrentCha(String hero) {

		for (IRenderable i : RenderableHolder.getInstance().getiRenderable()) {
			if (i instanceof Hero && ((Hero) i).getName().compareTo(hero) != 0) {
				((Hero) i).setVisible(false);
			} else if (i instanceof Hero && ((Hero) i).getName().compareTo(hero) == 0) {
				((Hero) i).setVisible(true);
			}
		}
		if (hero == "Knight") {
			currentCha = knight;
		} else if (hero == "SpellCaster") {
			currentCha = spellCaster;
		} else if (hero == "Clown") {
			currentCha = clown;
		} else {
			currentCha = priest;
		}

		field.setBg();
		scoreBefore.set(0, currentCha.getLevel() * 1.0);
		scoreBefore.set(1, currentCha.getAtk());
		scoreBefore.set(2, currentCha.getCurrentExp() * 1.0);
		scoreBefore.set(3, currentCha.getCurrentMaxExp() * 1.0);

	}

	public static int getCurrentBoss() {
		return currentBoss;
	}

	public void setCurrentBoss(int currentBoss) {
		GameManager.currentBoss = currentBoss;
	}

	public static String getCurrentMode() {
		return currentMode;
	}

	public static void setCurrentMode(String mode) {
		currentMode = mode;
	}

	public static Monster getCurrentMon() {
		return currentMon;
	}

	public static void setCurrentMon(Monster currentMon) {
		GameManager.currentMon = currentMon;
	}

	public static int getcurrentNumMon() {
		return currentNumMon;
	}

	public static void setcurrentNumMon(int currentNumMon) {
		GameManager.currentNumMon = currentNumMon;
	}

	public static Hero getKnight() {
		return knight;
	}

	public static Hero getSpellCaster() {
		return spellCaster;
	}

	public static Hero getClown() {
		return clown;
	}

	public static Hero getPriest() {
		return priest;
	}

	public static void update(List<Integer> list, GamePlayScreen gamePlayScreen) {
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				monster.update(1.1 * currentCha.getAtk());
			} else if (i == 1) {
				monster.update(1 * currentCha.getAtk());
			} else if (i == 2) {
				monster.update(0.8 * currentCha.getAtk());
			} else if (i == 3) {
				monster.update(0.6 * currentCha.getAtk());
			}
			gameResult.set(i, gameResult.get(i) + list.get(i));
		}
		System.out.println("game = " + gameResult);
		gamePlayScreen.setMonsInfo();
		gamePlayScreen.setHeroInfo();

	}

	public static List<Integer> getGameResult() {
		return gameResult;
	}

	public static void setGameResult(List<Integer> gameResult) {
		GameManager.gameResult = gameResult;
	}

	public static ArrayList<Image> getnotesImages() {
		return notesImage;
	}
}
