package gameLogic;

import character.Boss;
import character.Clown;
import character.Hero;
import character.Knight;
import character.Monster;
import character.Priest;
import character.SpellCaster;
import scene.CharacterSelectScreen;
import scene.MainMenuScreen;
import window.SceneManager;

public class GameManager {
	private static Hero currentCha;
	private static Monster currentMon;
	private static Boss currentBoss;
	private static Hero knight;
	private static Hero spellCaster;
	private static Hero clown;
	private static Hero priest;
	private static String currentMode;
	
	
	public static void newGame(){
		knight = new Knight("Knight", 1, "skill1");
		spellCaster = new SpellCaster("SpellCaster", 1, "skill2");
		clown = new Clown("Clown",1,"skill3");
		priest = new Priest("Priset",1,"skill4");
		SceneManager.gotoSceneOf(new MainMenuScreen());
		
	}
	
	

	public static Hero getCurrentCha() {
		return currentCha;
	}

	public static void setCurrentCha(String hero) {
		if(hero=="Knight") {
			currentCha = knight;
		}
		else if(hero=="spellCaster") {
			currentCha = spellCaster;
		}
		else if(hero=="clown") {
			currentCha = clown;
		}
		else  {
			currentCha = priest;
		}
	}

	public Boss getCurrentBoss() {
		return currentBoss;
	}

	public void setCurrentBoss(Boss currentBoss) {
		GameManager.currentBoss = currentBoss;
	}

	public String getCurrentMode() {
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




	
	
	
}
