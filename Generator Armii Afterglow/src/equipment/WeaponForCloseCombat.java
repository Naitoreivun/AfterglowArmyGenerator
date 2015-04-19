package equipment;

public class WeaponForCloseCombat extends OrdinaryEquipment
{
	private String strength;
	private String range;
	
	public WeaponForCloseCombat(String name, String value, String strength, String range,
			String specialRule)
	{
		super(name, value, specialRule);
		
		this.strength = strength.trim();
		this.range = range.trim();
	}

	public WeaponForCloseCombat(String name)
	{
		super(name);
	}

	@Override
	public String getDescription()
	{
		String strengthText = "Si³a: " + strength + "<br>";
		String rangeText = "Zasiêg: " + range + "<br>";
		return strengthText + rangeText + super.getDescription();
	}
	
	public String getStrength()
	{
		return strength;
	}

	public String getRange()
	{
		return range;
	}
}
