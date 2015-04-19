package panels;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;

import skills.BasicSkill;
import generator.GeneratorFrame;
import models.*;

public class RevengeHoundPanel extends HoundPanel
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RevengeHoundPanel(GeneratorFrame generatorFrame, String nick,
			ArmyModel armyModel, RevengeHoundModel revengeHound)
	{
		super(generatorFrame, nick, armyModel, revengeHound);
	}
	
	@Override
	protected int countMinimumValue()
	{
		return 0;
	}
	
	@Override
	protected void makeBottomRightPanel()
	{
		makeAdditionalRulePanel();
	}

	private void makeAdditionalRulePanel()
	{
		MyPanel additionalRulePanel = new MyPanel(new BorderLayout(), "Pozosta³e informacje");
		
		String message = ((RevengeHoundModel) unit).getAdditionalRule();
		BasicSkill additionalRule = new BasicSkill("Dodatkowa Zasada", message);
		MyLabel additionalRuleLabel = new MyLabel(additionalRule, SwingConstants.CENTER);
		
		additionalRulePanel.add(additionalRuleLabel);
		rightPanel.add(additionalRulePanel);
	}
	
	@Override
	protected void updateValue(){}
}
