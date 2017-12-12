package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameLogic.GameManager;
import gameLogic.SkillUpdater;
import javafx.scene.canvas.GraphicsContext;
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
			System.out.println("Boss Fail!!");
			Thread skill = new Thread(() -> {
				try {
					this.isSkillActivated = true;
					Thread.sleep(15000);
					this.isSkillActivated = false;
				} catch (Exception e) {
				}
			});
			ThreadHolder.threads.add(skill);
			skill.start();
		}
	}

	public void deactivate() {
		if (this.isSkillActivated == true && isSilence == true) {

			lastHero.setAtk(lastAtk);
			System.out.println("Boss Skill Deactivated!!");
			Thread skill = new Thread(() -> {
				try {
					Thread.sleep(15000);
					this.isSkillActivated = false;
				} catch (InterruptedException e) {
					System.out.println();
				}
			});
			ThreadHolder.threads.add(skill);
			skill.start();
		}
	}

	public void setSilence(boolean x) {
		isSilence = x;
	}

	public boolean getisActivated() {
		return isSkillActivated;
	}

}
