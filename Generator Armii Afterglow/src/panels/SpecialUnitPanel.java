package panels;

import generator.GeneratorFrame;
import models.*;

public class SpecialUnitPanel extends UnitPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpecialUnitPanel(GeneratorFrame generatorFrame, String nick,
			ArmyModel armyModel, SpecialUnitModel specialUnit)
	{
		super(generatorFrame, nick, armyModel, specialUnit);
	}

}
