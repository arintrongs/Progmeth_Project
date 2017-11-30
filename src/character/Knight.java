package character;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Knight extends Hero {
	Random random = new Random();
	
	public Knight(String name, int level, String skillName) {
		super(name, level, skillName);
		
	}
	
	
	public void skill() {
		int rnd = random.nextInt(2);
		if(rnd==1) {
			this.atk*=this.growthRateAtk;
		}
		
	}
}
