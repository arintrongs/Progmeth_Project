package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameLogic.GameManager;
import gameLogic.SkillUpdater;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import scene.GamePlayScreen;
import sharedObject.ThreadHolder;

public class Boss extends Monster implements Skillable {
	private String skillName;
	final private List<Integer> maxBossHp = new ArrayList<>();
	final private List<Integer> BossExp = new ArrayList<>();
	private int Boss_no;
	private double lastAtk;
	private Hero lastHero;
	protected boolean isSkillActivated, isSilence;
	protected Random random = new Random();

	public Boss(String name, int level, String skillName) {
		super(name, level);
		this.skillName = skillName;
		setmaxBossHp();
		setBossExp();
		this.currentMaxHp = this.maxBossHp.get(0);
		this.currentHp = this.currentMaxHp;
		this.Boss_no = 0;
		this.isVisible = true;
		this.isSkillActivated = false;
		this.isSilence = false;
		this.alreadyDead = false;

	}

	public void setmaxBossHp() {
		this.maxBossHp.add(70000);
		this.maxBossHp.add(200000);
		this.maxBossHp.add(300000);
		this.maxBossHp.add(500000);
	}

	public void setBossExp() {
		this.BossExp.add(20000);
		this.BossExp.add(40000);
		this.BossExp.add(100000);
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public void newBoss() {
		this.Boss_no++;
		this.currentMaxHp = this.maxBossHp.get(this.Boss_no);
		this.currentHp = this.currentMaxHp;
	}

	@Override
	public void draw(GraphicsContext gc, double x, double y) {
		gc.drawImage(bossImg, x, y, bossImg.getWidth() / 3, bossImg.getHeight() / 3);
	}

	@Override
	public String getName() {
		return "Boss";
	}

	public void activate() {
		int rnd = random.nextInt(2);

		if (rnd == 1 && isSkillActivated == false && isSilence == false) {

			isSkillActivated = true;
			Thread skill = new Thread(() -> {
				if (isSilence == false) {
					System.out.println("Boss Skill Activated!!!");
					lastHero = GameManager.getCurrentCha();
					Platform.runLater(() -> {
						GamePlayScreen.showBossSkillActivated();
					});
					lastAtk = lastHero.getAtk();
					GameManager.getCurrentCha().setAtk(0);
					try {
						Thread.sleep(5000);
						deactivate();
					} catch (InterruptedException e) {
					}
				}
			});
			ThreadHolder.threads.add(skill);
			SkillUpdater.getSkills().add(skill);

		} else {

			isSkillActivated = true;
			isSilence = false;
			Thread skill = new Thread(() -> {
				try {

					Thread.sleep(15000);
					isSkillActivated = false;
				} catch (Exception e) {
				}
			});
			ThreadHolder.threads.add(skill);
			SkillUpdater.getSkills().add(skill);
		}
	}

	public void deactivate() {
		if (this.isSkillActivated == true && isSilence == false) {
			lastHero = GameManager.getCurrentCha();
			lastHero.setAtk(lastAtk);
			Thread skill = new Thread(() -> {
				try {
					Thread.sleep(15000);
					this.isSkillActivated = false;
				} catch (InterruptedException e) {

				}
			});
			ThreadHolder.threads.add(skill);
			SkillUpdater.getSkills().add(skill);
		}
	}

	@Override
	public void update(double atk, GamePlayScreen gamePlayScreen) {
		this.decreaseHp(atk);
		if (this.isDead() && this.alreadyDead == false) {
			GameManager.getCurrentCha().update(this.currentExp);
			this.alreadyDead = true;
			this.level++;
			this.currentHp = this.maxBossHp.get(this.level - 1);
			this.currentMaxHp = this.currentHp;
			this.currentExp = this.BossExp.get(this.level - 1);
			GamePlayScreen.getMusicControl().end();
		}
	}

	public void setSilence(boolean x) {
		isSilence = x;
	}

	public boolean getIsSilence() {
		return isSilence;
	}

	public boolean getisActivated() {
		return isSkillActivated;
	}

}
