package panels;

import java.awt.GridLayout;
import java.awt.event.*;

import generator.GeneratorFrame;

import javax.swing.*;

import models.*;

public class HeroPanel extends UnitPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HeroModel hero;
	private JRadioButton leader;
	
	
	public HeroPanel(GeneratorFrame generatorFrame, String nick, 
			ArmyModel armyModel, ButtonGroup leadersButtonGroup, HeroModel hero)
	{
		super(generatorFrame, nick, armyModel, hero);
		this.hero = hero;
		makeLeaderRadioButton(leadersButtonGroup, armyModel);
		makeLeaderSkillPanel();
	}
	
	private void makeLeaderRadioButton(ButtonGroup leadersButtonGroup, ArmyModel armyModel)
	{
		leader = new JRadioButton();
		leadersButtonGroup.add(leader);
		addAll(basicInformationPanel, new SLabel("Przywódca: "), leader);
		
		leader.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				armyModel.setLeader(hero.getNick());
			}
		});
	}
	
	private void makeLeaderSkillPanel()
	{
		MyPanel leaderSkillPanel = new MyPanel(new GridLayout(0,1), "Zdolnoœæ Przywódcy");
		leaderSkillPanel.add(new MyLabel(hero.getLeaderSkill(), SwingConstants.CENTER));
		final int LEADER_SKILL_PANEL_POSITION = 1;
		leftPanel.add(leaderSkillPanel, LEADER_SKILL_PANEL_POSITION);
	}
}
