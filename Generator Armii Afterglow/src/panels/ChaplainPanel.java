package panels;

import generator.GeneratorFrame;

import javax.swing.ButtonGroup;

import models.*;

public class ChaplainPanel extends HeroPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChaplainPanel(GeneratorFrame generatorFrame, String nick,
			ArmyModel armyModel, ButtonGroup leadersButtonGroup, ChaplainModel chaplain)
	{
		super(generatorFrame, nick, armyModel, leadersButtonGroup, chaplain);
	}

}
