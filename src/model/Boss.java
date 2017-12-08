package model;

import java.util.ArrayList;
import java.util.List;

import gameLogic.GameManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Boss extends Monster {
	private String skillName;
	final private List<Integer> maxBossHp = new ArrayList<>();
	final private List<Integer> BossExp = new ArrayList<>();
	private int Boss_no;
	private Image bossImg1, bossImg2, bossImg3;
	

	public Boss(String name, int level, String skillName) {
		super(name, level);
		this.skillName = skillName;
		setmaxBossHp();
		setBossExp();
		this.currentMaxHp = this.maxBossHp.get(0);
		this.currentHp = this.currentMaxHp;
		this.Boss_no = 0;
		this.isVisible = true;
		
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
		if(GameManager.getCurrentMon()==1) {
			gc.drawImage(bossImg1, x, y, bossImg1.getWidth()/3, bossImg1.getHeight()/3);
		}
		else if(GameManager.getCurrentMon()==2) {
			gc.drawImage(bossImg2, x, y, bossImg2.getWidth()/3, bossImg2.getHeight()/3);
		}
		else  {
			gc.drawImage(bossImg3, x, y, bossImg3.getWidth()/3, bossImg3.getHeight()/3);
		}

		
	}
	
	
	
}
