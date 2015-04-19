package models;

import java.util.*;
import skills.BasicSkill;

public class HeroModel extends UnitModel
{
	private final BasicSkill leaderSkill;
	private Scanner scan;

	public HeroModel(String unitName, String factionName)
	{
		super(unitName, factionName);
		scan = super.getScan();
		leaderSkill = new BasicSkill(scan.nextLine(), scan.nextLine());
	}

	public BasicSkill getLeaderSkill()
	{
		return leaderSkill;
	}
}