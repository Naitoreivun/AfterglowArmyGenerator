package equipment;

public class OrdinaryEquipment implements Comparable<OrdinaryEquipment>
{
	private String name;
	private int value;
	private String specialRule;
	
	public OrdinaryEquipment(String name, String value, String specialRule)
	{
		this.name = name.trim();
		this.value = Integer.parseInt(value.trim());
		this.specialRule = specialRule.trim();
	}
	
	public OrdinaryEquipment(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name + " (" + value + ")";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OrdinaryEquipment))
			return false;
		OrdinaryEquipment other = (OrdinaryEquipment) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(OrdinaryEquipment other)
	{
		return name.compareTo(other.getName());
	}
	
	public String getDescription()
	{
		String specialRuleText = "Specjalna Zasada: " + specialRule + "<br>";
		String valueText = "Koszt: " + String.valueOf(value) + "<br>";
		return specialRuleText + valueText;
	}

	public String getName()
	{
		return name;
	}
	public String getSpecialRule()
	{
		return specialRule;
	}
	public int getValue()
	{
		return value;
	}
}
