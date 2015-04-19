package skills;


public class Skill extends BasicSkill
{
	public static final int TACTICS = 0;
	public static final int MIND = 1;
	public static final int LOOT = 2;
	public static final int COMBAT = 3;
	private final int value; 
	
	public Skill()
	{
		super();
		value = 0;
	}
	
	public Skill(String name)
	{
		super(name);
		value = 0;
	}
	
	public Skill(String name, String description)
	{
		super(name, description);
		value = 0;
	}

	public Skill(String name, String description, int value)
	{
		super(name, description);
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " (" + value + ")";
	}

	public int getValue()
	{
		return value;
	}
}
