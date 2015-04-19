package models;

import java.util.Scanner;

import skills.BasicSkill;

public class FanaticModel extends BasicUnitModel
{
	private BasicSkill wrathEmissaryDescriptionAndRules;
	private boolean wrathEmissaryActive;
	private boolean wrathEmissaryEquipment;
	
	public FanaticModel(String unitName, String factionName)
	{
		super(unitName, factionName);
		
		Scanner scan = super.getScan();
		scan.nextLine();
		
		initWrathEmissary(scan);
	}

	private void initWrathEmissary(Scanner scan)
	{
		wrathEmissaryDescriptionAndRules = new BasicSkill(scan.nextLine(), scan.nextLine());
		wrathEmissaryActive = false;
		wrathEmissaryEquipment = false;
	}

	public boolean isWrathEmissaryActive()
	{
		return wrathEmissaryActive;
	}

	public void setWrathEmissaryActive(boolean wrathEmissaryActive)
	{
		this.wrathEmissaryActive = wrathEmissaryActive;
	}

	public boolean isWrathEmissaryEquipment()
	{
		return wrathEmissaryEquipment;
	}

	public void setWrathEmissaryEquipment(boolean wrathEmissaryEquipment)
	{
		this.wrathEmissaryEquipment = wrathEmissaryEquipment;
	}

	public BasicSkill getWrathEmissaryDescriptionAndRules()
	{
		return wrathEmissaryDescriptionAndRules;
	}
	
	@Override
	public int getValue()
	{
		int additionalCost = 0;
		if(wrathEmissaryActive)
		{
			final int WRATH_EMISSARY_COST = 14;
			additionalCost += WRATH_EMISSARY_COST;
			
			if(!wrathEmissaryEquipment)
				additionalCost -= getEquipmentValue();
		}
		
		return super.getValue() + additionalCost;
	}
}
