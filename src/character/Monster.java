package character;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Entity {
	protected double currentMaxHp;
	protected double currentHp;
	protected double currentExp;
	private List<Double> maxHp = new ArrayList<>();
	private List<Integer> Exp = new ArrayList<>();
	final private List<Double> multipleHp = new ArrayList<>();
	final private int multipleExp = 3;
	final private double growthRateHp = 1.2;
	final private double growthRateExp = 1.2;
	final private int numMonster = 3;

	public Monster(String name, int level) {
		super(name, level);
		setmaxHp();
		setMultipleHp();
		setExp();
		this.currentMaxHp = this.maxHp.get(0);
		this.currentHp = (double)this.currentMaxHp;
		this.currentExp = this.Exp.get(0);
	}
	
	public void setmaxHp() {
		this.maxHp.add(1000.0);
		this.maxHp.add(2000.0);
		this.maxHp.add(3000.0);
	}

	public void setMultipleHp() {
		this.multipleHp.add(2.5);
		this.multipleHp.add(2.0);
		this.multipleHp.add(1.8);
		this.multipleHp.add(15.0/9.00);
		this.multipleHp.add(22.0/15.00);
		this.multipleHp.add(32.0/22.00);
		
	}
	
	public void setExp() {
		this.Exp.add(150);
		this.Exp.add(300);
		this.Exp.add(500);
	}
	
	public List<Double> getMultipleHp() {
		return multipleHp;
	}

	public int getMultipleExp() {
		return multipleExp;
	}
	
	public double getCurrentMaxHp() {
		return currentMaxHp;
	}

	public void setCurrentMaxHp(int currentMaxHp) {
		this.currentMaxHp = currentMaxHp;
	}

	public Double getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(Double currentHp) {
		this.currentHp = currentHp;
	}

	public List<Double> getMaxHp() {
		return maxHp;
	}

	public List<Integer> getExp() {
		return Exp;
	}
	
	public boolean isDead() {
		if(this.currentHp<=0) {
			return true;
		}
		return false;
	}
	
	public void newMonster() {
		this.level = (this.level+1) % this.numMonster;
		this.currentMaxHp = this.maxHp.get(this.level-1);
		this.currentHp = this.currentMaxHp;
		this.currentExp = this.Exp.get(this.level-1);
	}
	
	public void upgradeMonster(int index) {
		this.level = 1;
		for(int i =0;i<this.maxHp.size();i++) {
			this.maxHp.add(i, this.maxHp.get(i)*this.multipleHp.get(index));
		}
		for(int i =0;i<this.Exp.size();i++) {
			this.Exp.add(i, this.Exp.get(i)*this.multipleExp);
		}
		this.currentMaxHp = this.maxHp.get(0);
		this.currentHp = this.currentMaxHp;
		this.currentExp = this.Exp.get(0);
	
	}
	
	public void levelUp() {
		this.level = 1;
		this.currentMaxHp *= this.growthRateHp;
		this.currentHp = this.currentMaxHp;
		this.currentExp *= this.growthRateExp;
	}
	
	public void decreaseHp(double hp) {
		this.currentHp-=this.currentHp;
	}
	
	
	
	
	
}
