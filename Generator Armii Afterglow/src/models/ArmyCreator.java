package models;

import java.util.*;
import java.io.*;

import resources.ResourceLoader;
import skills.*;
import equipment.*;

public class ArmyCreator
{
	private String factionName;
	
	private ArrayList <String> factionUnitsNames;
	
	private ArrayList <BasicSkill> features;
	private int featuresNumber;

	private Vector<Armor> armor;
	private Vector<OrdinaryEquipment> ordinaryEquipment;
	private Vector<SmallArms> smallArms;
	private Vector<SpecialWeapons> specialWeapons;
	private Vector<WeaponForCloseCombat> weaponForCloseCombat;
	
	private Scanner scan;
	
	public ArmyCreator(String factionName)
	{
		this.factionName = factionName;
		loadFactionUnitsName();
		loadFactionFeatures();
		loadWeapons();
		scan.close();
	}
	
	private void loadFactionUnitsName()
	{
		factionUnitsNames = new ArrayList<>(9);
		InputStream input = ResourceLoader.getFactionUnitsName(factionName);
		scan = new Scanner(input);
		String name;
		while(scan.hasNext())
		{
			name = scan.nextLine().trim();
			factionUnitsNames.add(name);
		}
	}

	private void loadFactionFeatures()
	{
		features = new ArrayList<>(featuresNumber);
		InputStream input = ResourceLoader.getFactionFeatures(factionName);
		scan = new Scanner(input);
		featuresNumber = Integer.parseInt(scan.nextLine());
		for (int i = 0; i < featuresNumber; i++)
			features.add(new Skill(scan.nextLine(), scan.nextLine()));
	}

	private void loadWeapons()
	{
		loadArmor("Armor");
		loadOrdinaryEquipment("Ordinary equipment");
		loadSmallArms("Small arms");
		loadSpecialWeapons("Special weapons");
		loadWeaponForCloseCombat("Weapon for close combat");
	}
	
	//***********************************************************************//
	
	private void loadArmor(String path)
	{
		InputStream input = ResourceLoader.getArmor(factionName);
		scan = new Scanner(input);
		armor = new Vector<>();
		while(scan.hasNextLine())
			armor.add(new Armor(scan.nextLine(), scan.nextLine(), scan.nextLine(), scan.nextLine()));
		
		armor.sort(null);
	}
	private void loadOrdinaryEquipment(String path)
	{
		InputStream input = ResourceLoader.getOrdinaryEquipment(factionName);
		scan = new Scanner(input);
		ordinaryEquipment = new Vector<>();
		while(scan.hasNextLine())
			ordinaryEquipment.add(new OrdinaryEquipment(scan.nextLine(), scan.nextLine(), scan.nextLine()));

		ordinaryEquipment.sort(null);
	}

	private void loadSmallArms(String path)
	{
		InputStream input = ResourceLoader.getSmallArms(factionName);
		scan = new Scanner(input);
		smallArms = new Vector<>();
		while(scan.hasNextLine())
			smallArms.add(new SmallArms(scan.nextLine(), scan.nextLine(), 
					scan.nextLine(), scan.nextLine(), scan.nextLine(), scan.nextLine()));

		smallArms.sort(null);
	}

	private void loadSpecialWeapons(String path)
	{
		InputStream input = ResourceLoader.getSpecialWeapons(factionName);
		scan = new Scanner(input);
		specialWeapons = new Vector<>();
		while(scan.hasNextLine())
			specialWeapons.add(new SpecialWeapons(scan.nextLine(), scan.nextLine(), 
					scan.nextLine(), scan.nextLine(), scan.nextLine(), scan.nextLine()));

		specialWeapons.sort(null);
	}

	private void loadWeaponForCloseCombat(String path)
	{
		InputStream input = ResourceLoader.getWeaponForCloseCombat(factionName);
		scan = new Scanner(input);
		weaponForCloseCombat = new Vector<>();
		while(scan.hasNextLine())
			weaponForCloseCombat.add(new WeaponForCloseCombat(scan.nextLine(), scan.nextLine(), 
					scan.nextLine(), scan.nextLine(), scan.nextLine()));

		weaponForCloseCombat.sort(null);
	}

	public ArrayList<String> getFactionUnitsNames()
	{
		return factionUnitsNames;
	}

	public ArrayList<BasicSkill> getFeatures()
	{
		return features;
	}

	public int getFeaturesNumber()
	{
		return featuresNumber;
	}

	public Vector<Armor> getArmor()
	{
		return armor;
	}

	public Vector<OrdinaryEquipment> getOrdinaryEquipment()
	{
		return ordinaryEquipment;
	}

	public Vector<SmallArms> getSmallArms()
	{
		return smallArms;
	}

	public Vector<SpecialWeapons> getSpecialWeapons()
	{
		return specialWeapons;
	}

	public Vector<WeaponForCloseCombat> getWeaponForCloseCombat()
	{
		return weaponForCloseCombat;
	}
}
