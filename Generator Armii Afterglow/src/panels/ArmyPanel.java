package panels;

import generator.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import skills.BasicSkill;
import models.*;




public class ArmyPanel extends DefaultPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArmyModel armyModel;
	
	private SLabel allUnitsNumber;
	private SLabel basicUnitsNumber;
	private SLabel specialUnitsNumber;
	private SLabel heroesNumber;
	private SLabel specialistsNumber;
	private SLabel pointMenAndSkirmishersNumber;
	private SLabel leader;
	
	private SLabel value;


	public ArmyPanel(GeneratorFrame generatorFrame, ArmyModel armyModel)
	{
		super(generatorFrame);
		
		init(armyModel);
		
		addNamePanelMouseListener();
		makeCenterPanelPopupMenu();
		makeBasicInformationPanel();
		makeFeaturesPanel();
		makeCriteriaPanel();
		makeValuePanel(generatorFrame.VALUE_FONT);
		
		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentShown(ComponentEvent event)
			{
				generatorFrame.setSelectedShowArmyPanelButton(true);		
			}
			
			@Override
			public void componentHidden(ComponentEvent event)
			{
				generatorFrame.setSelectedShowArmyPanelButton(false);
			}
		});		
	}
	
	private void init(ArmyModel armyModel)
	{
		this.armyModel = armyModel;
		
		setNick(armyModel.getArmyName());
		
		centerPanel.setLayout(new GridLayout(2, 2, 50, 50));
	}
	
	//****************************************************************************//

	private void addNamePanelMouseListener()
	{
		nick.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent event)
			{
				if(event.getClickCount() == 2)
				{
					changeNick();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				setCursor(Cursor.getDefaultCursor());
			}
		});

//		nick.setToolTipText("Kliknij dwa razy, aby zmieniæ nazwê.");
	}
	
	private void changeNick()
	{
		String newName;
		newName = JOptionPane.showInputDialog(centerPanel, "Podaj now¹ nazwê armii:",
					"Zmiana nazwy", JOptionPane.QUESTION_MESSAGE);
		if(newName != null)
		{
			if("".equals(newName) || " ".equals(newName) || " ".equals(newName))
				newName = "Nie umiem pisaæ :C";
			setNick(newName);
		}
	}
	
	@Override
	protected final void customizeTheLookOfNamePanel(JPanel namePanel)
	{
		super.customizeTheLookOfNamePanel(namePanel);
		nick.setToolTipText("Kliknij dwa razy, aby zmieniæ nazwê.");
	}
	
	//****************************************************************************//

	private void makeCenterPanelPopupMenu()
	{
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(new AbstractAction("Zmieñ nazwê")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent event)
			{
				changeNick();
			}
		});
		centerPanel.setComponentPopupMenu(popupMenu);
	}
	
	//****************************************************************************//
	
	private void makeBasicInformationPanel()
	{
		MyPanel basicInformationPanel = new MyPanel(new GridLayout(0, 1), "Podstawowe informacje");
		
		initBasicInformationCopomentsValue();
		addBasicInformationComponents(basicInformationPanel);
		
		centerPanel.add(basicInformationPanel);
	}

	private void initBasicInformationCopomentsValue()
	{
		allUnitsNumber = new SLabel("0");
		basicUnitsNumber = new SLabel("0");
		specialUnitsNumber = new SLabel("0");
		heroesNumber = new SLabel("0");
		specialistsNumber = new SLabel("0");
		pointMenAndSkirmishersNumber = new SLabel("0");
		leader = new SLabel("Brak");
	}

	private void addBasicInformationComponents(MyPanel basicInformationPanel)
	{
		addAll(basicInformationPanel, new SLabel("Frakcja: "), new SLabel(armyModel.getFactionName()),
				new SLabel("Iloœæ oddzia³ów: "), allUnitsNumber,
				new SLabel("Iloœæ oddzia³ów podstawowych: "), basicUnitsNumber,
				new SLabel("Iloœæ oddzia³ów specjalnych: "), specialUnitsNumber,
				new SLabel("Iloœæ bohaterów: "), heroesNumber,
				new SLabel("Iloœæ specjalistów:"), specialistsNumber,
				new SLabel("Iloœæ zwiadowców i harcowników:"), pointMenAndSkirmishersNumber,
				new SLabel("Przywódca: "), leader);
	}

	private void makeFeaturesPanel()
	{
		MyPanel featuresPanel = new MyPanel(new GridLayout(0, 1), "Cechy frakcji");
		for(BasicSkill skill : armyModel.getFeatures())
			featuresPanel.add(new MyLabel(skill, SwingConstants.CENTER));
		
		centerPanel.add(featuresPanel);
	}
	
	private void makeCriteriaPanel()
	{ 
		MyPanel criteriaPanel = new MyPanel(new BorderLayout(), "Kryteria");

		SLabel checkCriteria = new SLabel("SprawdŸ kryteria", SwingConstants.CENTER);
		checkCriteria.setFont(generatorFrame.BIG_FONT);
		checkCriteria.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				showCriteria();
			}
			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent arg0)
			{
				setCursor(Cursor.getDefaultCursor());
			}
		});
		
		criteriaPanel.add(checkCriteria);
		centerPanel.add(criteriaPanel);
	}

	private void showCriteria()
	{
		int[] unitsNumber = armyModel.getUnitsNumber();
		String criteria = "";
		String error;
		
		if(!checkHeroesAndBasicUnitBeginning(unitsNumber))
		{
			error = generatorFrame.getErrorDescriptions()
					.get(GeneratorError.HEROES_AND_BASIC_UNIT_BEGINNING);
			
			criteria += "~ " + error + "<br>";
		}
		
		if(!checkSpecialUnitsLimit(unitsNumber))
		{
			error = generatorFrame.getErrorDescriptions()
					.get(GeneratorError.SPECIAL_UNITS_LIMIT);

			criteria += "~ " + error + "<br>";
		}
		
		if(!checkHeroesLimit(unitsNumber))
		{
			error = generatorFrame.getErrorDescriptions()
					.get(GeneratorError.HEROES_LIMIT);

			criteria += "~ " + error + "<br>";
		}
		
		if(!checkSpecialistsLimit(unitsNumber))
		{
			error = generatorFrame.getErrorDescriptions()
					.get(GeneratorError.SPECIALISTS_LIMIT);

			criteria += "~ " + error + "<br>";
		}
		
		if(!checkPointMenAndSkirmishersLimit(unitsNumber))
		{
			error = generatorFrame.getErrorDescriptions()
					.get(GeneratorError.POINT_MEN_AND_SKIRMISHERS_LIMIT);

			criteria += "~ " + error + "<br>";
		}
		
		if("".equals(criteria))
			generatorFrame.setInfo("Wszystkie kryteria spe³nione");
		else
			generatorFrame.setInfo("Niespe³nione kryteria", criteria);
	}

	private boolean checkHeroesAndBasicUnitBeginning(int[] unitsNumber)
	{
		if(unitsNumber[UnitType.HERO] > 0 && unitsNumber[UnitType.BASIC_UNIT] > 0)
			 return true;
		return false;
	}
	
	private boolean checkSpecialUnitsLimit(int[] unitsNumber)
	{
		if(unitsNumber[UnitType.SPECIAL_UNIT] <= unitsNumber[UnitType.BASIC_UNIT])
			return true;
		return false;
	}

	private boolean checkHeroesLimit(int[] unitsNumber)
	{
		if(unitsNumber[UnitType.HERO] < 2 || unitsNumber[UnitType.HERO] * 500 <= armyModel.getValue())
			return true;
		return false;
	}

	private boolean checkSpecialistsLimit(int[] unitsNumber)
	{
		if((unitsNumber[UnitType.SPECIALIST] - 1) * 300 < armyModel.getValue())
			return true;
		return false;
	}

	private boolean checkPointMenAndSkirmishersLimit(int[] unitsNumber)
	{
		if(unitsNumber[UnitType.POINT_MAN_AND_SKIRMISHER] <= 
				armyModel.getArmy().size() - unitsNumber[UnitType.POINT_MAN_AND_SKIRMISHER])
			return true;
		return false;
	}

	private void makeValuePanel(Font font)
	{
		MyPanel valuePanel = new MyPanel(new BorderLayout(), "Wartoœæ armii");
		
		value = new SLabel("0", SLabel.CENTER);
		
		value.setFont(font);
				
		valuePanel.add(value, BorderLayout.CENTER);
		
		centerPanel.add(valuePanel);
	}

	public void updatePanel()
	{
		updateUnitsNumber();
		updateLeader();
		updateValue();
		showCriteria();
	}

	private void updateUnitsNumber()
	{
		int[] unitsNumber = armyModel.getUnitsNumber();
		countCurrentUnitsNumber(unitsNumber);
		showCurrentUnitsNumber(unitsNumber);
	}
	
	private void countCurrentUnitsNumber(int... unitsNumber)
	{
		cleanUnitsNumber(unitsNumber);
		for(UnitModel unit : armyModel.getArmy().values())
		{
			++unitsNumber[unit.getBasicType()];
			if(unit.isSpecialist())
				++unitsNumber[UnitType.SPECIALIST];
			if(unit.ispointManOrSkirmisher())
				++unitsNumber[UnitType.POINT_MAN_AND_SKIRMISHER];
		}
	}

	private void cleanUnitsNumber(int[] unitsNumber)
	{
		for(int i = 0; i < unitsNumber.length; ++i)
			unitsNumber[i] = 0;
	}

	private void showCurrentUnitsNumber(int... unitsNumber)
	{
		allUnitsNumber.setText(String.valueOf(armyModel.getArmy().size()));
		
		basicUnitsNumber.setText(String.valueOf(unitsNumber[UnitType.BASIC_UNIT]));
		specialUnitsNumber.setText(String.valueOf(unitsNumber[UnitType.SPECIAL_UNIT]));
		heroesNumber.setText(String.valueOf(unitsNumber[UnitType.HERO]));
		specialistsNumber.setText(String.valueOf(unitsNumber[UnitType.SPECIALIST]));
		pointMenAndSkirmishersNumber.setText(String.valueOf(unitsNumber[UnitType.POINT_MAN_AND_SKIRMISHER]));
	}
	
	private void updateLeader()
	{
		String currentLeader = armyModel.getLeader();
		if(leaderExists(currentLeader))
			leader.setText(armyModel.getLeader());
		else
			leader.setText("Brak");
	}
		

	private boolean leaderExists(String currentLeader)
	{
		return armyModel.getArmy().containsKey(currentLeader);
	}

	private void updateValue()
	{
		armyModel.updateValue();
		value.setText(String.valueOf(armyModel.getValue()));
	}
}
