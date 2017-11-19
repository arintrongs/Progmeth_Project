package Character;

import java.util.Random;

public class Priest extends Hero {
	Random random = new Random();
	public Priest(String name, int level, String skillName) {
		super(name, level, skillName);
	}
	
	
	public void skill() {
		int rnd = random.nextInt(3);
		if(rnd!=1) {
			this.currentExp+= 0.1*(this.currentMaxExp-this.currentExp);
		}
	}
}
