package equipment;

public class SpecialWeapons extends SmallArms
{
	public SpecialWeapons(String name)
	{
		super(name);
	}
	
	public SpecialWeapons(String name, String value, String actionPoints,
			String strength, String range, String specialRule)
	{
		super(name, value, actionPoints, strength, range, specialRule);
	}

	@Override
	public String getDescription()
	{
		String specialWeaponText = "~ Broñ Specjalna " + "~" + "<br>";
		return specialWeaponText + super.getDescription();
	}
	
}
