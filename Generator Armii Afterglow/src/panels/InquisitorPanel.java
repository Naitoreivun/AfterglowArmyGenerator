package panels;

import java.awt.BorderLayout;

import generator.GeneratorFrame;

import javax.swing.*;
import javax.swing.event.*;

import models.*;

public class InquisitorPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HeroPanel inquisitorPanel;
	RevengeHoundPanel revengeHoundPanel;
	JTabbedPane tabbedPane;
	final int INQUISITOR_INDEX = 0;
	
	public InquisitorPanel(GeneratorFrame generatorFrame, String nick,
			ArmyModel armyModel, ButtonGroup leadersButtonGroup, InquisitorModel inquisitor)
	{
		setLayout(new BorderLayout());

		inquisitorPanel = new HeroPanel(generatorFrame, nick, armyModel, leadersButtonGroup, inquisitor);
		revengeHoundPanel = new RevengeHoundPanel(generatorFrame, "Ogar Zemsty", armyModel, inquisitor.getRevengeHound());
		makeTabbedPane();
		
	}

	private void makeTabbedPane()
	{
		tabbedPane = new JTabbedPane();
		
		tabbedPane.add("Inkwizytor", inquisitorPanel);
		tabbedPane.add("Ogar Zemsty", revengeHoundPanel);
		
		tabbedPane.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent event)
			{
				final int SELECTED_INDEX = tabbedPane.getSelectedIndex();
				if(SELECTED_INDEX == INQUISITOR_INDEX)
					inquisitorPanel.updateValue();
				tabbedPane.setSelectedIndex(SELECTED_INDEX);
			}
		});
		
		add(tabbedPane);
	}
}
