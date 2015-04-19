package panels;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import skills.BasicSkill;
import generator.GeneratorFrame;
import models.*;

public class CleanerPanel extends SpecialUnitPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CleanerPanel(GeneratorFrame generatorFrame, String nick,
			ArmyModel armyModel, CleanerModel cleaner)
	{
		super(generatorFrame, nick, armyModel, cleaner);
		
		makeCriterionInfoLabel();
	}

	private void makeCriterionInfoLabel()
	{
		BasicSkill criterion = new BasicSkill("~ Wymagania ~", "Czyœciciel musi posiadaæ "
							+ "jedn¹ z nastêpuj¹cych broni: CKM, Miotacz ognia, Wyrzutnia rakiet.");
		
		equipmentChoosersOutsidePanel.add(new MyLabel(criterion, SwingConstants.CENTER), BorderLayout.NORTH);
	}

}
