package models;

import java.util.Scanner;

public class BasicUnitModel extends UnitModel
{
	private int maxDivisionSize;
	private int divisionSize;
	private Scanner scan;
	
	public BasicUnitModel(String unitName, String factionName)
	{
		super(unitName, factionName);
		scan = super.getScan();

		divisionSize = 1;
		maxDivisionSize = scan.nextInt();
	}
	
	@Override
	public int getValue()
	{
		return divisionSize * super.getValue();
	}

	public int getDivisionSize()
	{
		return divisionSize;
	}

	public void setDivisionSize(int divisionSize)
	{
		this.divisionSize = divisionSize;
	}
	
	public int getMaxDivisionSize()
	{
		return maxDivisionSize;
	}
}
