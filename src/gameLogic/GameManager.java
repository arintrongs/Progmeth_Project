package gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import model.Boss;
import model.Clown;
import model.Field;
import model.Hero;
import model.Knight;
import model.Monster;
import model.Priest;
import model.SpellCaster;
import scene.GamePlayScreen;
import scene.MainMenuScreen;
import scene.SceneManager;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;

public class GameManager {

	private static Hero currentCha;
	private static Monster currentMonKnight, currentMonSpell, currentMonPriest, currentMonClown, currentMon;
	private static int currentNumMon;
	private static int currentBoss;
	private static Hero knight;
	private static Hero spellCaster;
	private static Hero clown;
	private static Hero priest;
	private static Monster boss;
	private static Field field;
	private static String currentMode;
	private static List<Integer> gameResult;
	private static List<Double> statusBefore;
	private static ArrayList<Image> notesImage;
	private static ArrayList<Hero> heroes;
	private static Random random = new Random();
	private static boolean isGameFinished;

	public static void newGame() {

		knight = new Knight("Knight", 1, "skill1");
		spellCaster = new SpellCaster("SpellCaster", 1, "skill2");
		clown = new Clown("Clown", 1, "skill3");
		priest = new Priest("Priest", 1, "skill4");
		heroes = new ArrayList<>();
		boss = new Boss("Boss", 1, "skillBoss");
		field = new Field();

		heroes.add(knight);
		heroes.add(spellCaster);
		heroes.add(clown);
		heroes.add(priest);

		currentMonClown = new Monster("Monster", 1);
		currentMonKnight = new Monster("Monster", 1);
		currentMonSpell = new Monster("Monster", 1);
		currentMonPriest = new Monster("Monster", 1);
		currentMon = new Monster("Monster", 1);

		isGameFinished = false;
		currentCha = knight;
		RenderableHolder.getInstance().add(knight);
		RenderableHolder.getInstance().add(spellCaster);
		RenderableHolder.getInstance().add(clown);
		RenderableHolder.getInstance().add(priest);
		RenderableHolder.getInstance().add(field);
		currentBoss = 1;
		currentNumMon = 1;
		SceneManager.gotoSceneOf(new MainMenuScreen());
		gameResult = new ArrayList<>(Collections.nCopies(5, 0));
		statusBefore = new ArrayList<>(Collections.nCopies(5, 0.0));
		notesImage = new ArrayList<>();
		notesImage.add(new Image("up-arrow.png"));
		notesImage.add(new Image("down-arrow.png"));
		notesImage.add(new Image("left-arrow.png"));
		notesImage.add(new Image("right-arrow.png"));

	}

	public static void UpdateStatusBefore() {
		statusBefore.set(0, currentCha.getLevel() * 1.0);
		statusBefore.set(1, currentCha.getAtk());
		statusBefore.set(2, currentCha.getCurrentExp() * 1.0);
		statusBefore.set(3, currentCha.getCurrentMaxExp() * 1.0);
		currentCha = knight;
		gameResult = new ArrayList<>(Collections.nCopies(5, 0));
	}

	public static List<Double> getStatusBefore() {
		return statusBefore;
	}

	public static void setStatusBefore(List<Double> statusBefore) {
		GameManager.statusBefore = statusBefore;
	}

	public static Hero getCurrentCha() {
		return currentCha;
	}

	public static synchronized void setCurrentCha(String hero, boolean isGameStart) {
		Hero lastHero = currentCha;
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

		if (GamePlayScreen.getIsCreated() == true)
			GamePlayScreen.instance.changeHero(currentCha);
		if (isGameStart == true) {
			field.setBg();
			statusBefore.set(0, currentCha.getLevel() * 1.0);
			statusBefore.set(1, currentCha.getAtk());
			statusBefore.set(2, currentCha.getCurrentExp() * 1.0);
			statusBefore.set(3, currentCha.getCurrentMaxExp() * 1.0);
		}
	}

	public static int getCurrentBoss() {
		return currentBoss;
	}

	public static void setCurrentBoss(int currentBoss) {
		GameManager.currentBoss = currentBoss;
	}

	public static String getCurrentMode() {
		return currentMode;
	}

	public static void setCurrentMode(String mode) {

		currentMode = mode;
		if (currentMode.compareTo("Farm") == 0) {

		} else {
			RenderableHolder.getInstance().getiRenderable().remove(currentMon);
			currentMon = boss;
			String rhHero = heroes.get(random.nextInt(4)).getName();

			setCurrentCha(rhHero, true);
			RenderableHolder.getInstance().add(currentMon);
		}

	}

	public static Monster getCurrentMon() {
		return currentMon;
	}

	public static void setCurrentMon() {
		RenderableHolder.getInstance().getiRenderable().remove(currentMon);
		if (currentCha.getName().compareTo("Knight") == 0)
			currentMon = currentMonKnight;
		if (currentCha.getName().compareTo("Priest") == 0)
			currentMon = currentMonPriest;
		if (currentCha.getName().compareTo("Clown") == 0)
			currentMon = currentMonClown;
		if (currentCha.getName().compareTo("SpellCaster") == 0)
			currentMon = currentMonSpell;
		RenderableHolder.getInstance().add(currentMon);

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
		double atk = 0;
		if (currentMode.compareTo("Boss") == 0) {
			for (int i = 0; i < heroes.size(); i++) {
				atk += heroes.get(i).getAtk();
			}
		} else {
			atk = currentCha.getAtk();
		}
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				currentMon.update(1.1 * atk * list.get(i), gamePlayScreen);
			} else if (i == 1) {
				currentMon.update(1 * atk * list.get(i), gamePlayScreen);
			} else if (i == 2) {
				currentMon.update(0.8 * atk * list.get(i), gamePlayScreen);
			} else if (i == 3) {
				currentMon.update(0.6 * atk * list.get(i), gamePlayScreen);
			}
			gameResult.set(i, gameResult.get(i) + list.get(i));
		}

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

	public static ArrayList<Hero> getHeroes() {
		return heroes;
	}

	public static void setIsGameFinished(boolean x) {
		isGameFinished = x;
	}

	public static boolean isGameFinished() {
		return isGameFinished;
	}
}
