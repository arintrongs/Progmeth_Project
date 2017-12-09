package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Hero extends Entity implements Skillable {
	protected String skillName;
	protected int currentExp;
	protected int currentMaxExp;
	final protected List<Integer> maxExp = new ArrayList<>();
	protected double atk = 10, originalAtk;
	final protected double growthRateAtk = 1.2;
	private int z = -999;
	protected boolean isSkillActivated;
	protected Random random = new Random();

	public Hero(String name, int level, String skillName) {
		super(name, level);
		this.skillName = skillName;
		this.currentExp = 0;
		setMaxExp();
		this.currentMaxExp = this.maxExp.get(0);
		this.originalAtk = atk;
		this.isSkillActivated = false;
	}

	public void setMaxExp() {
		maxExp.add(1000);
		maxExp.add(1200);
		maxExp.add(1400);
		maxExp.add(1700);
		maxExp.add(2000);
		maxExp.add(3000);
		maxExp.add(3500);
		maxExp.add(4200);
		maxExp.add(5000);
		maxExp.add(9000);
		maxExp.add(10000);
		maxExp.add(13000);
		maxExp.add(26000);
		maxExp.add(31000);
		maxExp.add(37000);
		maxExp.add(80000);
		maxExp.add(100000);
		maxExp.add(250000);
		maxExp.add(300000);
		maxExp.add(700000);
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
		return maxExp;
	}

	public double getAtk() {
		return atk;
	}

	public void setAtk(double atk) {
		this.atk = atk;
	}

	public void levelUp() {
		this.level++;
		this.atk *= this.growthRateAtk;
		int oldExp = this.currentExp - this.currentMaxExp;
		this.currentMaxExp = this.maxExp.get(this.getLevel());
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
		}
	}

	// set currentMaxExp and atk when level up

}
