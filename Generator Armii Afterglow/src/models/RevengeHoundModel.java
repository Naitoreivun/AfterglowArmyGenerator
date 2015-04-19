package models;

import java.util.Scanner;

import equipment.WeaponForCloseCombat;

public class RevengeHoundModel extends HoundModel
{
	private String additionalRule;
	
	public RevengeHoundModel(String unitName, String factionName)
	{
		super(unitName, factionName);
		setDivisionSize(0);
		Scanner scan = super.getScan();
		scan.nextLine();
		additionalRule = scan.nextLine();
	}
	
	@Override
	public WeaponForCloseCombat getWeapon()
	{
		return new WeaponForCloseCombat("Zêby i pazury", "0", "+4", "-", "-");
	}

	public String getAdditionalRule()
	{
		return additionalRule;
	}
}
