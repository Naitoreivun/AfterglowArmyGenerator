package panels;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import generator.GeneratorFrame;
import models.*;

public class FanaticPanel extends BasicUnitPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FanaticModel fanatic;
	MyCheckBox wrathEmissaryActive;
	MyCheckBox wrathEmissaryEquipment;
	
	
	public FanaticPanel(GeneratorFrame generatorFrame, String nick,
			ArmyModel armyModel, FanaticModel fanatic)
	{
		super(generatorFrame, nick, armyModel, fanatic);
		this.fanatic = fanatic;
		
		makeWrathEmissaryPanel();
	}

	private void makeWrathEmissaryPanel()
	{
		MyPanel wrathEmissaryPanel = new MyPanel(new BorderLayout(), "Pos³aniec Gniewu");
		
		
		makeWrathEmissaryNorthPanel(wrathEmissaryPanel);
		wrathEmissaryPanel.add(new MyLabel(fanatic.getWrathEmissaryDescriptionAndRules(), SwingConstants.CENTER));
		
		customizeDivisionSizeCounter();
		
		final int WRATH_EMISSARY_PANEL_INDEX = 1;
		leftPanel.add(wrathEmissaryPanel, WRATH_EMISSARY_PANEL_INDEX);
	}

	private void makeWrathEmissaryNorthPanel(MyPanel wrathEmissaryPanel)
	{
		JPanel wrathEmissaryNorthPanel = new JPanel();
		wrathEmissaryNorthPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		makeCheckBoxes(wrathEmissaryNorthPanel);
		wrathEmissaryPanel.add(wrathEmissaryNorthPanel, BorderLayout.NORTH);
	}

	private void makeCheckBoxes(JPanel wrathEmissaryNorthPanel)
	{
		makeWrathEmissaryActive(wrathEmissaryNorthPanel);
		makeWrathEmissaryEquipment(wrathEmissaryNorthPanel);
	}

	private void makeWrathEmissaryActive(JPanel wrathEmissaryNorthPanel)
	{
		wrathEmissaryActive = new MyCheckBox("Aktywacja");
		wrathEmissaryActive.setEnabled(false);
		wrathEmissaryActive.setToolTipText("<html><center>Musisz posiadaæ przynajmniej trzech fanatyków, "
				+ "<br>aby zamieniæ jednego na Pos³añca Gniewu.</center></html>");
		
		wrathEmissaryActive.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				boolean selected = wrathEmissaryActive.isSelected();
				fanatic.setWrathEmissaryActive(selected);
				setEquipmentActive(selected);
			}
		});
		
		wrathEmissaryNorthPanel.add(wrathEmissaryActive);
	}

	private void setEquipmentActive(boolean selected)
	{
		wrathEmissaryEquipment.setEnabled(selected);
		wrathEmissaryEquipment.setSelected(selected);
		fanatic.setWrathEmissaryEquipment(selected);
		updateValue();
	}

	private void makeWrathEmissaryEquipment(JPanel wrathEmissaryNorthPanel)
	{
		wrathEmissaryEquipment = new MyCheckBox("Ekwipunek");
		wrathEmissaryEquipment.setEnabled(false);
		wrathEmissaryEquipment.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				fanatic.setWrathEmissaryEquipment(wrathEmissaryEquipment.isSelected());
				
				updateValue();
			}
		});
		
		wrathEmissaryNorthPanel.add(wrathEmissaryEquipment);
	}

	private void customizeDivisionSizeCounter()
	{
		divisionSizeCounter.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent event)
			{
				if((int) divisionSizeCounter.getValue() < 3)
				{
					fanatic.setWrathEmissaryActive(false);
					wrathEmissaryActive.setEnabled(false);
					wrathEmissaryActive.setSelected(false);
					setEquipmentActive(false);
				}
				else
					wrathEmissaryActive.setEnabled(true);
			}
		});
	}
}
