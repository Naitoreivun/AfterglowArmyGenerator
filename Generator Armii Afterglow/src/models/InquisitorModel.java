package models;

public class InquisitorModel extends HeroModel
{
	private RevengeHoundModel revengeHound;
	
	public InquisitorModel(String unitName, String factionName)
	{
		super(unitName, factionName);
		
		revengeHound = new RevengeHoundModel("Ogar Zemsty", factionName);
		revengeHound.setNick("Ogar Zemsty");
	}

	public RevengeHoundModel getRevengeHound()
	{
		return revengeHound;
	}
	
	@Override
	public int getValue()
	{
		return super.getValue() + revengeHound.getValue();
	}
}
