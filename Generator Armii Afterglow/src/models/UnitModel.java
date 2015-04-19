package models;

import equipment.OrdinaryEquipment;

import java.io.*;
import java.util.*;

import resources.ResourceLoader;
import skills.*;

public class UnitModel
{
	private String name;
	private String nick;
	private int type;
	private int basicType;
	private String basicTypeDescription;
	private String stand;
	private int basicValue;
	
	public static final int ACTION_POINTS = 0; //PA
	public static final int SHOOTING_SKILLS = 1; //US
	public static final int FIGHTING_SKILLS = 2; //UW
	public static final int AGILITY = 3; //ZW
	public static final int STRENGTH = 4; //SI
	public static final int STAMINA = 5; //WT
	public static final int WILLPOWER = 6; //SW
	public static final int DEFENSE_FIRST = 7; //OB x/
	public static final int DEFENSE_SECOND = 8; //OB /y
	private int[] statistics;
	
	private boolean tactics;
	private boolean mind;
	private boolean loot;
	private boolean combat;
	
	private int maxEquipmentValue;
	private int equipmentValue;
	private ArrayList<OrdinaryEquipment> equipment;
	
	private int maxAdditionalSkillsNumber;
	private int additionalSkillsNumber;
	private int additionalSkillsValue;
	private ArrayList<Skill> additionalSkills;
	
	private int basicSkillsNumber;
	private ArrayList<BasicSkill> basicSkills;
	
	private Scanner scan;
	
	public UnitModel(String unitName, String factionName)
	{
		InputStream input = ResourceLoader.getUnit(factionName, unitName);
		scan = new Scanner(input);
		
		loadBasicInformation();
		loadStatistics();
		loadSkillsGroupsInformation();
		loadEquipmentInforamtion();
		loadAdditionalSkillsInformation();
		loadBasicSkillsInformation();
	}
	
	private void loadBasicInformation()
	{
		nick = "";
		
		name = scan.nextLine();
		type = scan.nextInt();
		basicType = Integer.parseInt(scan.nextLine().trim());
		basicTypeDescription = scan.nextLine();
		stand = scan.next();
		basicValue = scan.nextInt();
	}

	private void loadStatistics()
	{
		statistics = new int[9];
		
		statistics[ACTION_POINTS] = scan.nextInt();
		statistics[SHOOTING_SKILLS] = scan.nextInt();
		statistics[FIGHTING_SKILLS] = scan.nextInt();
		statistics[AGILITY] = scan.nextInt();
		statistics[STRENGTH] = scan.nextInt();
		statistics[STAMINA] = scan.nextInt();
		statistics[WILLPOWER] = scan.nextInt();
		statistics[DEFENSE_FIRST] = scan.nextInt();
		statistics[DEFENSE_SECOND] = scan.nextInt();
	}

	private void loadSkillsGroupsInformation()
	{
		tactics = scan.nextBoolean();
		mind = scan.nextBoolean();
		loot = scan.nextBoolean();
		combat = scan.nextBoolean();
	}

	private void loadEquipmentInforamtion()
	{
		equipmentValue = 0;
		
		maxEquipmentValue = scan.nextInt();
		if(maxEquipmentValue > 0)
			equipment = new ArrayList<>();
	}

	private void loadAdditionalSkillsInformation()
	{
		additionalSkillsNumber = 0;
		additionalSkillsValue = 0;
		
		maxAdditionalSkillsNumber = scan.nextInt();
		additionalSkills = new ArrayList<>(maxAdditionalSkillsNumber);
	}

	private void loadBasicSkillsInformation()
	{
		basicSkillsNumber = scan.nextInt();
		if (basicSkillsNumber > 0) scan.nextLine();
		
		basicSkills = new ArrayList<>(basicSkillsNumber);
		
		for(int i = 0; i < basicSkillsNumber; i++)
			basicSkills.add(new BasicSkill(scan.nextLine(), scan.nextLine()));
	}
	

	//****************************************************************************//
	
	
	public int getValue()
	{
		return basicValue + equipmentValue + additionalSkillsValue;
	}

	public void increaseAdditionalSkillsNumber()
	{
		++additionalSkillsNumber;
	}
	
	public void decreaseAdditionalSkillsNumber()
	{
		--additionalSkillsNumber;
	}
	
	public void increaseAdditionalSkillsValue(int increase)
	{
		additionalSkillsValue += increase;
	}
	
	public void decreaseAdditionalSkillsValue(int decrease)
	{
		additionalSkillsValue -= decrease;
	}
	
	public void increaseEquipmentValue(int increase)
	{
		equipmentValue += increase;
	}
	
	public void decreaseEquipmentValue(int decrease)
	{
		equipmentValue -= decrease;
	}

	//settery i gettery do zmiennych/sta³ych:

	public String getNick()
	{
		return nick;
	}


	public void setNick(String nick)
	{
		this.nick = nick;
	}

	public int getEquipmentValue()
	{
		return equipmentValue;
	}


	public void setEquipmentValue(int equipmentValue)
	{
		this.equipmentValue = equipmentValue;
	}


	public ArrayList<OrdinaryEquipment> getEquipment()
	{
		return equipment;
	}


	public void setEquipment(ArrayList<OrdinaryEquipment> equipment)
	{
		this.equipment = equipment;
	}


	public int getAdditionalSkillsNumber()
	{
		return additionalSkillsNumber;
	}


	public void setAdditionalSkillsNumber(int additionalSkillsNumber)
	{
		this.additionalSkillsNumber = additionalSkillsNumber;
	}


	public int getAdditionalSkillsValue()
	{
		return additionalSkillsValue;
	}


	public void setAdditionalSkillsValue(int additionalSkillsValue)
	{
		this.additionalSkillsValue = additionalSkillsValue;
	}


	public ArrayList<Skill> getAdditionalSkills()
	{
		return additionalSkills;
	}


	public void setAdditionalSkills(ArrayList<Skill> additionalSkills)
	{
		this.additionalSkills = additionalSkills;
	}


	public String getName()
	{
		return name;
	}

	public int getType()
	{
		return type;
	}

	public int getBasicType()
	{
		return basicType;
	}

	public String getBasicTypeDescription()
	{
		return basicTypeDescription;
	}


	public String getStand()
	{
		return stand;
	}


	public int getBasicValue()
	{
		return basicValue;
	}

	public int[] getStatistics()
	{
		return statistics;
	}

	public int getActionPoints()
	{
		return statistics[ACTION_POINTS];
	}


	public int getShootingSkills()
	{
		return statistics[SHOOTING_SKILLS];
	}


	public int getFightingSkills()
	{
		return statistics[FIGHTING_SKILLS];
	}


	public int getAgility()
	{
		return statistics[AGILITY];
	}


	public int getStrength()
	{
		return statistics[STRENGTH];
	}


	public int getStamina()
	{
		return statistics[STAMINA];
	}


	public int getWillpower()
	{
		return statistics[WILLPOWER];
	}


	public int getDefenseFirst()
	{
		return statistics[DEFENSE_FIRST];
	}


	public int getDefenseSecond()
	{
		return statistics[DEFENSE_SECOND];
	}


	public boolean isTactics()
	{
		return tactics;
	}


	public boolean isMind()
	{
		return mind;
	}


	public boolean isLoot()
	{
		return loot;
	}


	public boolean isCombat()
	{
		return combat;
	}


	public int getMaxEquipmentValue()
	{
		return maxEquipmentValue;
	}


	public int getMaxAdditionalSkillsNumber()
	{
		return maxAdditionalSkillsNumber;
	}


	public int getBasicSkillsNumber()
	{
		return basicSkillsNumber;
	}


	public ArrayList<BasicSkill> getBasicSkills()
	{
		return basicSkills;
	}


	public Scanner getScan()
	{
		return scan;
	}

	public boolean isSpecialist()
	{
		return additionalSkills.contains(BasicSkill.SPECIALIST) || basicSkills.contains(BasicSkill.SPECIALIST);
	}

	public boolean ispointManOrSkirmisher()
	{
		boolean isPointMan = additionalSkills.contains(BasicSkill.POINT_MAN) || basicSkills.contains(BasicSkill.POINT_MAN);
		boolean isSkirmisher = additionalSkills.contains(BasicSkill.SKIRMISHER) || basicSkills.contains(BasicSkill.SKIRMISHER);
		return isPointMan || isSkirmisher;
	}
}