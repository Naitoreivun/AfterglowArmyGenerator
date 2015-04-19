package equipment;

public class Armor extends OrdinaryEquipment
{
	String armorPoints;
	
	public Armor(String name, String value, String armorPoints, String specialRule)
	{
		super(name, value, specialRule);
		
		this.armorPoints = armorPoints.trim();
	}

	@Override
	public String getDescription()
	{
		String armorPointsText = "Punkty Pancerza: " + armorPoints + "<br>";
		return armorPointsText + super.getDescription();
	}
	
	public String getArmorPoints()
	{
		return armorPoints;
	}
}
