package resources;

import java.io.*;
import java.net.URL;

public class ResourceLoader
{
	public static ResourceLoader rl = new ResourceLoader();
	
	/**
	 * make Input Stream
	 */
	private static InputStream makeIS(String firstPathElement, String... otherPathElements)
	{
		String path = firstPathElement;
		for(String element : otherPathElements)
			path += "/" + element;
		
		//System.out.println(path); //test
		InputStream is = rl.getClass().getResourceAsStream(path);
		if(is == null) System.out.println("nenfsd");
		return is;
	}
	
	public static InputStream getArmiesList()
	{
		return makeIS("army", "Armies.txt");
	}
	
	public static InputStream getErrors()
	{
		return makeIS("errors", "Errors.txt");
	}

	public static URL getMenuIcon(String fileName)
	{
		String path = "icons/" + fileName + ".gif";
		return rl.getClass().getResource(path);
	}
	
	public static InputStream getSkills(String fileName, boolean forAll)
	{
		if(forAll)
			return makeIS("skills", "all", fileName + ".txt");
		else
			return makeIS("skills", "leader", fileName + ".txt");
					
	}

	public static InputStream getFactionUnitsName(String factionName)
	{
		return makeIS("army", factionName, "Units.txt");
	}
	
	public static InputStream getFactionFeatures(String factionName)
	{
		return makeIS("army", factionName, "Features.txt");
	}

	private static InputStream getWeapons(String factionName, String type)
	{
		return makeIS("army", factionName, "equipment", type + ".txt");
	}
	
	public static InputStream getArmor(String factionName)
	{
		return getWeapons(factionName, "Armor");
	}

	public static InputStream getOrdinaryEquipment(String factionName)
	{
		return getWeapons(factionName, "Ordinary equipment");
	}

	public static InputStream getSmallArms(String factionName)
	{
		return getWeapons(factionName, "Small arms");
	}

	public static InputStream getSpecialWeapons(String factionName)
	{
		return getWeapons(factionName, "Special weapons");
	}

	public static InputStream getWeaponForCloseCombat(String factionName)
	{
		return getWeapons(factionName, "Weapon for close combat");
	}

	public static InputStream getUnit(String factionName, String unitName)
	{
		return makeIS("army", factionName, unitName + ".txt");
	}
}
