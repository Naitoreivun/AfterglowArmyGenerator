package generator;

public class GeneratorError
{
	public static final String ADDITIONAL_SKILLS_LIMIT = "ADDITIONAL SKILLS LIMIT";
	public static final String ALREADY_OWNED_SKILL = "ALREADY OWNED SKILL";
	public static final String BUSY_NICK = "BUSY NICK";
	public static final String NOT_EXISTS = "NOT EXISTS";
	public static final String WEAPONS_VALUE_LIMIT = "WEAPONS VALUE LIMIT";
	public static final String ALREADY_OWNED_WEAPON = "ALREADY OWNED WEAPON";

	public static final String HEROES_AND_BASIC_UNIT_BEGINNING = "HEROES AND BASIC UNIT BEGINNING";
	public static final String SPECIAL_UNITS_LIMIT = "SPECIAL UNITS LIMIT";
	public static final String HEROES_LIMIT = "HEROES LIMIT";
	public static final String SPECIALISTS_LIMIT = "SPECIALISTS LIMIT";
	public static final String POINT_MEN_AND_SKIRMISHERS_LIMIT = "POINT MEN AND SKIRMISHERS LIMIT";
	
	public static final String UNKNOWN_ERROR = "Nieznany b³¹d.";
	
	private boolean occurred;
	private String name; 
	
	public GeneratorError()
	{
		occurred = false;
		name = "";
	}

	public GeneratorError(boolean occurred, String name)
	{
		this.occurred = occurred;
		this.name = name;
	}

	public boolean isOccurred()
	{
		return occurred;
	}

	public void setOccurred(boolean occurred)
	{
		this.occurred = occurred;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setOccurredAndName(boolean occurred, String name)
	{
		this.occurred = occurred;
		this.name = name;
	}
}
