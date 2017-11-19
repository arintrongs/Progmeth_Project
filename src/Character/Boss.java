package Character;

import java.util.ArrayList;
import java.util.List;

public class Boss extends Monster {
	private String skillName;
	final private List<Integer> maxBossHp = new ArrayList<>();
	final private List<Integer> BossExp = new ArrayList<>();
	private int Boss_no;

	public Boss(String name, int level, String skillName) {
		super(name, level);
		this.skillName = skillName;
		setmaxBossHp();
		setBossExp();
		this.currentMaxHp = this.maxBossHp.get(0);
		this.currentHp = this.currentMaxHp;
		this.Boss_no = 0;
		
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
	
	
	
}
