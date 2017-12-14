package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import scene.GamePlayScreen;

public abstract class Hero extends Entity implements Skillable {
	protected String skillName;
	protected int currentExp;
	protected int currentMaxExp;
	final protected List<Integer> MAX_EXP = new ArrayList<>();
	protected double atk = 10, originalAtk;
	final protected double GROWTH_RATE_ATK = 1.2;

	protected boolean isSkillActivated;
	protected Random random = new Random();

	public Hero(String name, int level, String skillName) {
		super(name, level);
		this.skillName = skillName;
		this.currentExp = 0;
		setMaxExp();
		this.currentMaxExp = this.MAX_EXP.get(0);
		this.originalAtk = atk;
		this.isSkillActivated = false;
	}

	public void setMaxExp() {
		MAX_EXP.add(1000);
		MAX_EXP.add(1200);
		MAX_EXP.add(1400);
		MAX_EXP.add(1700);
		MAX_EXP.add(2000);
		MAX_EXP.add(3000);
		MAX_EXP.add(3500);
		MAX_EXP.add(4200);
		MAX_EXP.add(5000);
		MAX_EXP.add(9000);
		MAX_EXP.add(10000);
		MAX_EXP.add(13000);
		MAX_EXP.add(26000);
		MAX_EXP.add(31000);
		MAX_EXP.add(37000);
		MAX_EXP.add(80000);
		MAX_EXP.add(100000);
		MAX_EXP.add(250000);
		MAX_EXP.add(300000);
		MAX_EXP.add(700000);
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public void setCurrentExp(int currentExp) {
		this.currentExp = currentExp;
	}

	public int getCurrentMaxExp() {
		return currentMaxExp;
	}

	public void setCurrentMaxExp(int currentMaxExp) {
		this.currentMaxExp = currentMaxExp;
	}

	public List<Integer> getMaxExp() {
		return MAX_EXP;
	}

	public double getAtk() {
		return atk;
	}

	public void setAtk(double atk) {
		this.atk = atk;
	}

	public void levelUp() {
		this.level++;
		this.atk *= this.GROWTH_RATE_ATK;
		int oldExp = this.currentExp - this.currentMaxExp;
		this.currentMaxExp = this.MAX_EXP.get(this.getLevel());
		this.currentExp = 0;

		this.originalAtk = this.atk;

		this.update(oldExp);

	}

	public abstract void activate();

	public abstract void deactivate();

	public void increaseExp(double exp) {
		this.currentExp += exp;
	}

	public boolean isLevelUp() {
		if (this.currentExp >= this.currentMaxExp) {
			return true;
		}
		return false;
	}

	public void update(double exp) {
		this.increaseExp(exp);
		if (this.isLevelUp()) {
			this.levelUp();
			Platform.runLater(() -> {
				GamePlayScreen.showLevelUP();
			});

		}
	}

	public boolean getIsSkillActivated() {
		return isSkillActivated;
	}

	public void setIsSkillActive(boolean x) {
		isSkillActivated = x;
	}

}
