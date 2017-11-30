package character;

public abstract class Entity {
	private String name;
	protected int level;
	
	public Entity(String name, int level) {
		this.name = name;
		this.level = 1;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public abstract void levelUp();
	
}
