package models;

import equipment.*;
import generator.UnitType;

import java.util.*;

import skills.BasicSkill;

public class ArmyModel
{
	private String armyName;
	private String factionName;
	private Map<String, UnitModel> army;
	private ArmyCreator armyCreator;

	private int value;
	private String leader; 
	private int[] unitsNumber;
	
	public ArmyModel(String factionName)
	{
		armyName = "Bezimienni";
		value = 0;
		leader = "Brak";
		army = new HashMap<>(2);
		this.factionName = factionName;
		armyCreator = new ArmyCreator(factionName);
		unitsNumber = new int[UnitType.BASIC_TYPES_NUMBER];
	}
	
	public void updateValue()
	{
		value = 0;
		for(UnitModel um : army.values())
			value += um.getValue();
	}

	public String getArmyName()
	{
		return armyName;
	}

	public void setArmyName(String armyName)
	{
		this.armyName = armyName;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public String getLeader()
	{
		return leader;
	}

	public void setLeader(String leader)
	{
		this.leader = leader;
	}

	public String getFactionName()
	{
		return factionName;
	}

	public Map<String, UnitModel> getArmy()
	{
		return army;
	}

	public int[] getUnitsNumber()
	{
		return unitsNumber;
	}

	public ArrayList<String> getFactionUnitsNames()
	{
		return armyCreator.getFactionUnitsNames();
	}

	public ArrayList<BasicSkill> getFeatures()
	{
		return armyCreator.getFeatures();
	}

	public int getFeaturesNumber()
	{
		return armyCreator.getFeaturesNumber();
	}
	
	public Vector<Armor> getArmor()
	{
		return armyCreator.getArmor();
	}

	public Vector<OrdinaryEquipment> getOrdinaryEquipment()
	{
		return armyCreator.getOrdinaryEquipment();
	}

	public Vector<SmallArms> getSmallArms()
	{
		return armyCreator.getSmallArms();
	}

	public Vector<SpecialWeapons> getSpecialWeapons()
	{
		return armyCreator.getSpecialWeapons();
	}

	public Vector<WeaponForCloseCombat> getWeaponForCloseCombat()
	{
		return armyCreator.getWeaponForCloseCombat();
	}
}
