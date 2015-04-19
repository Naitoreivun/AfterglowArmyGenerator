package models;

import equipment.WeaponForCloseCombat;

public class HoundModel extends BasicUnitModel
{
	public HoundModel(String unitName, String factionName)
	{
		super(unitName, factionName);
	}

	public WeaponForCloseCombat getWeapon()
	{
		return new WeaponForCloseCombat("Z�by i pazury", "0", "+3", "-", "-");
	}
}
