package panels;

import java.awt.BorderLayout;
import java.awt.event.*;

import generator.GeneratorFrame;

import javax.swing.*;

import models.*;

public class ProphetPanel extends HeroPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ProphetModel prophet;
	
	public ProphetPanel(GeneratorFrame generatorFrame, String nick,
			ArmyModel armyModel, ButtonGroup leadersButtonGroup, ProphetModel prophet)
	{
		super(generatorFrame, nick, armyModel, leadersButtonGroup, prophet);
		
		this.prophet = prophet;
		
		makeRelicPanel();
	}

	private void makeRelicPanel()
	{
		JPanel relicPanel = new JPanel(new BorderLayout());
		relicPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		
		relicPanel.add(new MyLabel(prophet.getRelic(), SwingConstants.CENTER));
		MyCheckBox relic = new MyCheckBox("Kup");
		relic.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				prophet.setHavingRelic(relic.isSelected());
				updateValue();
			}
		});
		relicPanel.add(relic, BorderLayout.EAST);
		
		equipmentChoosersOutsidePanel.add(relicPanel, BorderLayout.NORTH);
	}

}
