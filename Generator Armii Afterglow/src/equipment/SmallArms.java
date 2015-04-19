package equipment;

public class SmallArms extends WeaponForCloseCombat
{
	private String actionPoints;
	
	public SmallArms(String name, String value, String actionPoints, String strength, String range,
			String specialRule)
	{
		super(name, value, strength, range, specialRule);

		this.actionPoints = actionPoints.trim();
	}

	public SmallArms(String name)
	{
		super(name);
	}

	@Override
	public String getDescription()
	{
		String actionPointsText = "Punkty Akcji: " + actionPoints + "<br>";
		return actionPointsText + super.getDescription();
	}

	public String getActionPoints()
	{
		return actionPoints;
	}

}
