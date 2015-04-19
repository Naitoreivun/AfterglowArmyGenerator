package models;

import java.util.Scanner;

import skills.BasicSkill;

public class ProphetModel extends HeroModel
{
	private boolean havingRelic;
	private BasicSkill relic;
	
	public ProphetModel(String unitName, String factionName)
	{
		super(unitName, factionName);
		
		Scanner scan = getScan();
		
		relic = new BasicSkill(scan.nextLine(), scan.nextLine());
		havingRelic = false;
	}

	public boolean isHavingRelic()
	{
		return havingRelic;
	}

	public void setHavingRelic(boolean havingRelic)
	{
		this.havingRelic = havingRelic;
	}
	
	public BasicSkill getRelic()
	{
		return relic;
	}
	
	@Override
	public int getValue()
	{
		int additionalCost = 0;
		final int RELIC_COST = 20;
		if(havingRelic)
			additionalCost = RELIC_COST;
		
		return super.getValue() + additionalCost;
	}
}
