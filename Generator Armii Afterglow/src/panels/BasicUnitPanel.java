package panels;

import javax.swing.*;
import javax.swing.event.*;

import generator.GeneratorFrame;
import models.*;

public class BasicUnitPanel extends UnitPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JSpinner divisionSizeCounter;
	private BasicUnitModel basicUnit;
	
	public BasicUnitPanel(GeneratorFrame generatorFrame, String nick, ArmyModel armyModel, BasicUnitModel basicUnit)
	{
		super(generatorFrame, nick, armyModel, basicUnit);
		this.basicUnit = basicUnit;
		makeDivisionSizeCounter();
	}
	
	private void makeDivisionSizeCounter()
	{
		final int MINIMUM_VALUE = countMinimumValue();
		final int INITIAL_VALUE = MINIMUM_VALUE;
		final int STEP_SIZE = 1;
		SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
				INITIAL_VALUE, MINIMUM_VALUE, basicUnit.getMaxDivisionSize(), STEP_SIZE);
		divisionSizeCounter = new JSpinner(spinnerModel);
		divisionSizeCounter.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent event)
			{
				basicUnit.setDivisionSize((int) divisionSizeCounter.getValue());
				updateValue();
			}
		});
		addAll(basicInformationPanel, new SLabel("Wielkoœæ oddzia³u: "), divisionSizeCounter); 
	}
	//TODO: przy wczytywaniu trzeb dac mozliwosc ustawainaia divisionSizeCountera

	protected int countMinimumValue()
	{
		return 1;
	}
}
