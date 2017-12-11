package model;

import java.util.ArrayList;
import java.util.List;

import gameLogic.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import scene.GamePlayScreen;
import window.SceneManager;

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
	final private int numMonster = 4;
	private static Image monImg1 = new Image("monster1.png");
	private static Image monImg2 = new Image("monster2.png");
	private static Image monImg3 = new Image("monster3.png");
	protected static Image bossImg = new Image("boss.png");
	private static Image hurtedmonImg1 = new Image("hurted_mon1.png");
	private static Image hurtedmonImg2 = new Image("hurted_mon2.png");
	private static Image hurtedmonImg3 = new Image("hurted_mon3.png");
	protected static Image hurtedbossImg = new Image("hurted_boss.png");

	public Monster(String name, int level) {
		super(name, level);
		setmaxHp();
		setMultipleHp();
		setExp();
		this.currentMaxHp = this.maxHp.get(0);
		this.currentHp = (double) this.currentMaxHp;
		this.currentExp = this.Exp.get(0);
		this.isVisible = true;
	}

	public void setmaxHp() {
		this.maxHp.add(500.0);
		this.maxHp.add(1000.0);
		this.maxHp.add(1500.0);
	}

	public void setMultipleHp() {
		this.multipleHp.add(2.5);
		this.multipleHp.add(2.0);
		this.multipleHp.add(1.8);
		this.multipleHp.add(15.0 / 9.00);
		this.multipleHp.add(22.0 / 15.00);
		this.multipleHp.add(32.0 / 22.00);

	}

	public void setExp() {
		this.Exp.add(3000);
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
		if (this.currentHp <= 0) {
			return true;
		}
		return false;
	}

	public boolean isUpgrade() {
		if (GameManager.getCurrentCha().getLevel() == 5 || GameManager.getCurrentCha().getLevel() == 9
				|| GameManager.getCurrentCha().getLevel() == 12 || GameManager.getCurrentCha().getLevel() == 15
				|| GameManager.getCurrentCha().getLevel() == 17 || GameManager.getCurrentCha().getLevel() == 19) {
			return true;
		}
		return false;
	}

	public void update(double atk, GamePlayScreen gamePlayScreen) {

		this.decreaseHp(atk);

		if (this.isDead()) {
			GameManager.getCurrentCha().update(this.currentExp);

			if (this.isUpgrade()) {
				this.upgradeMonster();
			} else {
				if (GameManager.getcurrentNumMon() == 3) {
					this.levelUp();
				}
				this.newMonster();

			}

			this.level++;

			this.draw(gamePlayScreen.getGcMon(), SceneManager.SCENE_WIDTH / 3 * 2 - 20, SceneManager.SCENE_HEIGHT / 3);

		}
	}

	public void newMonster() {
		if (GameManager.getcurrentNumMon() == 3) {
			GameManager.setcurrentNumMon(0);
		}
		GameManager.setcurrentNumMon((GameManager.getcurrentNumMon() + 1) % 4);

		this.currentMaxHp = this.maxHp.get(GameManager.getcurrentNumMon() - 1);
		this.currentHp = this.currentMaxHp;
		this.currentExp = this.Exp.get(GameManager.getcurrentNumMon() - 1);

	}

	public void upgradeMonster() {

		int index = 0;
		if (GameManager.getCurrentCha().getLevel() == 5) {
			index = 0;
		} else if (GameManager.getCurrentCha().getLevel() == 9) {
			index = 1;
		} else if (GameManager.getCurrentCha().getLevel() == 12) {
			index = 2;
		} else if (GameManager.getCurrentCha().getLevel() == 15) {
			index = 3;
		} else if (GameManager.getCurrentCha().getLevel() == 17) {
			index = 4;
		} else if (GameManager.getCurrentCha().getLevel() == 19) {
			index = 5;
		}

		for (int i = 0; i < this.maxHp.size(); i++) {
			this.maxHp.set(i, this.maxHp.get(i) * this.multipleHp.get(index));
		}
		for (int i = 0; i < this.Exp.size(); i++) {
			this.Exp.set(i, this.Exp.get(i) * this.multipleExp);
		}
		this.currentMaxHp = this.maxHp.get(0);
		this.currentHp = this.currentMaxHp;
		this.currentExp = this.Exp.get(0);
		GameManager.setcurrentNumMon(1);

	}

	public void levelUp() {

		for (int i = 0; i < this.maxHp.size(); i++) {
			this.maxHp.set(i, this.maxHp.get(i) * this.growthRateHp);
		}
		for (int i = 0; i < this.Exp.size(); i++) {
			this.Exp.set(i, (int) (this.Exp.get(i) * this.growthRateExp));
		}

	}

	public void decreaseHp(double atk) {
		this.currentHp -= atk;
	}

	public void draw(GraphicsContext gc, double x, double y) {
		gc.clearRect(0, 0, SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
		if (GameManager.getcurrentNumMon() == 1) {
			if (this.level == 1)
				gc.drawImage(monImg1, x, y, monImg1.getWidth() / 3, monImg1.getHeight() / 3);
			else {
				gc.drawImage(hurtedmonImg3, x, y, hurtedmonImg3.getWidth() / 3, hurtedmonImg3.getHeight() / 3);
				Thread t1 = new Thread(() -> {
					try {
						Thread.sleep(1000);
						gc.clearRect(0, 0, SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
						gc.drawImage(monImg1, x, y, monImg1.getWidth() / 3, monImg1.getHeight() / 3);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				t1.start();
			}

		} else if (GameManager.getcurrentNumMon() == 2) {
			gc.drawImage(hurtedmonImg1, x, y, hurtedmonImg1.getWidth() / 3, hurtedmonImg1.getHeight() / 3);
			Thread t2 = new Thread(() -> {
				try {
					Thread.sleep(1000);
					gc.clearRect(0, 0, SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
					gc.drawImage(monImg2, x, y, monImg2.getWidth() / 3, monImg2.getHeight() / 3);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			t2.start();

		} else {
			gc.drawImage(hurtedmonImg2, x, y, hurtedmonImg2.getWidth() / 3, hurtedmonImg1.getHeight() / 3);
			Thread t3 = new Thread(() -> {
				try {

					Thread.sleep(1000);
					gc.clearRect(0, 0, SceneManager.SCENE_WIDTH, SceneManager.SCENE_HEIGHT);
					gc.drawImage(monImg3, x, y, monImg3.getWidth() / 3, monImg3.getHeight() / 3);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			t3.start();

		}

	}

	@Override
	public String getName() {
		if (GameManager.getcurrentNumMon() == 1) {
			return "Monster1";
		} else if (GameManager.getcurrentNumMon() == 2) {
			return "Monster2";
		} else {
			return "Monster3";
		}
	}

}
