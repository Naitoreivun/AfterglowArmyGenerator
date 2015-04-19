package panels;

import equipment.*;
import generator.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import skills.*;
import models.*;

public class UnitPanel extends DefaultPanel
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected UnitModel unit;
	
	protected JPanel leftPanel;
	protected JPanel rightPanel;
	
	protected MyPanel basicInformationPanel;
	
	private SLabel name;
	private SLabel basicType;
	private SLabel stand;
	
	private SLabel additionalSkillsCounter;
	private JPanel addedSkillsPanel;
	private Map<Skill, SkillLabel> additionalSkillsLabels;
	
	protected MyPanel equipmentPanel;
	private SLabel equipmentValueCounter;
	protected JPanel addedItemsPanel;
	private Map<OrdinaryEquipment, ItemLabel> itemsLabels;
	private JButton addSpecialWeaponButton;
	protected JPanel equipmentChoosersOutsidePanel;
	private JPanel equipmentChoosersInsidePanel;
	
	private SLabel value;
	
	private MyPanel statisticsPanel;
	
	public UnitPanel(GeneratorFrame generatorFrame, String nick, ArmyModel armyModel, UnitModel unit)
	{
		super(generatorFrame);
		this.unit = unit;
		
		setNick(nick);
		makeCenterPanel();
		makeLeftPanel();
		makeRightPanel(armyModel);
		makeStatisticsPanel();
	}
	
	private void makeCenterPanel()
	{
		centerPanel.setLayout(new GridLayout(1, 2, 50, 50));
	}
	
	private void makeLeftPanel()
	{
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		
		makeBasicInformationPanel();
		makeBasicSkillsPanel();
		makeAdditionalSkillsPanel();
		
		centerPanel.add(leftPanel);
	}
	
	private void makeBasicInformationPanel()
	{
		initBasicInformationLabels();
		basicInformationPanel = new MyPanel(new GridLayout(0,1), "Podstawowe informacje");
		
		addAll(basicInformationPanel, new SLabel("Postaæ: "), name,
				new SLabel("Typ: "), basicType,
				new SLabel("Podstawka: "), stand);
		leftPanel.add(basicInformationPanel);
	}

	private void initBasicInformationLabels()
	{
		name = new SLabel(unit.getName());
		basicType = new SLabel(unit.getBasicTypeDescription());
		stand = new SLabel(unit.getStand());
	}

	private void makeBasicSkillsPanel()
	{
		if(unit.getBasicSkillsNumber() > 0)
		{
			MyPanel basicSkillsPanel = new MyPanel(new GridLayout(0,1), "Podstawowe umiejêtnoœci");
			for(BasicSkill skill : unit.getBasicSkills())
				basicSkillsPanel.add(new MyLabel(skill, SwingConstants.CENTER));
			leftPanel.add(basicSkillsPanel);
		}
	}

	private void makeAdditionalSkillsPanel()
	{
		if(unit.getMaxAdditionalSkillsNumber() > 0)
		{
			MyPanel additionalSkillsPanel = new MyPanel(new BorderLayout(), "Dodatkowe umiejêtnoœci");
			makeAddedSkillsPanel(additionalSkillsPanel);
			makeAdditionalSkillsChooserPanel(additionalSkillsPanel);
			leftPanel.add(additionalSkillsPanel);
		}
	}

	private void makeAdditionalSkillsChooserPanel(MyPanel additionalSkillsPanel)
	{
		JPanel skillsChooserPanel = new JPanel(new BorderLayout(5, 5));
		skillsChooserPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
	
		JComboBox<Skill> additionalSkillsChooser = makeAdditionalSkillsChooser(skillsChooserPanel);
		
		makeAdditionalSkillsCounterPanel(skillsChooserPanel);
		
		makeAddSkillButton(skillsChooserPanel, additionalSkillsChooser);
		
		additionalSkillsPanel.add(skillsChooserPanel, BorderLayout.NORTH);
	}

	private JComboBox<Skill> makeAdditionalSkillsChooser(JPanel skillsChooserPanel)
	{
		Vector<Skill> additionalSkillsGroup = makeAdditionalSkillsGroup();
		
		JComboBox<Skill> additionalSkillsChooser = new JComboBox<>(additionalSkillsGroup);
		additionalSkillsChooser.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent event)
			{
				Skill selectedSkill = (Skill) additionalSkillsChooser.getSelectedItem();
				generatorFrame.setInfo(selectedSkill);
			}
		});
		skillsChooserPanel.add(additionalSkillsChooser, BorderLayout.CENTER);
		return additionalSkillsChooser;
	}
	
	private Vector<Skill> makeAdditionalSkillsGroup()
	{
		Vector <Skill> additionalSkillsGroup = new Vector<>();
		fillAdditionalSkillsGroup(additionalSkillsGroup);
		
		return additionalSkillsGroup;
	}
	
	private void fillAdditionalSkillsGroup(Vector<Skill> additionalSkillsGroup)
	{
		if(unit.isTactics())
			addSkills(Skill.TACTICS, additionalSkillsGroup);
		if(unit.isMind())
			addSkills(Skill.MIND, additionalSkillsGroup);
		if(unit.isLoot())
			addSkills(Skill.LOOT, additionalSkillsGroup);
		if(unit.isCombat())
			addSkills(Skill.COMBAT, additionalSkillsGroup);
		
		additionalSkillsGroup.sort(null);
	}

	private void addSkills(final int SKILL_GROUP, Vector<Skill> additionalSkillsGroup)
	{
		for(Skill skill : generatorFrame.getSkillsInGameForAll().get(SKILL_GROUP))
			additionalSkillsGroup.add(skill);
	}
	
	private void makeAdditionalSkillsCounterPanel(JPanel skillsChooserPanel)
	{
		JPanel additionalSkillsCounterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		additionalSkillsCounterPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		additionalSkillsCounter = new SLabel(String.valueOf(unit.getAdditionalSkillsNumber()));
		
		additionalSkillsCounterPanel.add(additionalSkillsCounter);
		additionalSkillsCounterPanel.add(new SLabel(" / " + String.valueOf(unit.getMaxAdditionalSkillsNumber())));

		skillsChooserPanel.add(additionalSkillsCounterPanel, BorderLayout.WEST);
	}

	private void makeAddSkillButton(JPanel skillsChooserPanel, JComboBox<Skill> additionalSkillsChooser)
	{
		JButton addSkillButton = new JButton("Dodaj");
		addSkillButton.setToolTipText("Kliknij, aby dodaæ umiejêtnoœæ");
		addSkillButton.addActionListener(new ActionListener()
		{
			GeneratorError error;
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				error = new GeneratorError();
				Skill selectedSkill = (Skill) additionalSkillsChooser.getSelectedItem();
				
				checkSkillCriteria(selectedSkill);
				
				if(error.isOccurred())
				{
					generatorFrame.setInfo("B³¹d", generatorFrame.getErrorDescriptions()
							.getOrDefault(error.getName(), GeneratorError.UNKNOWN_ERROR));
				}
				else
				{
					addNewAdditionalSkill(selectedSkill);
					generatorFrame.setInfo("Dodano now¹ umiejêtnoœæ", selectedSkill.getName());
				}
			}

			private void checkSkillCriteria(Skill selectedSkill)
			{
				if(unit.getMaxAdditionalSkillsNumber() == unit.getAdditionalSkillsNumber())
					error.setOccurredAndName(true, GeneratorError.ADDITIONAL_SKILLS_LIMIT);
				
				else if(unit.getAdditionalSkills().contains(selectedSkill) || unit.getBasicSkills().contains(selectedSkill))
					error.setOccurredAndName(true, GeneratorError.ALREADY_OWNED_SKILL);
			}
		});
	
		skillsChooserPanel.add(addSkillButton, BorderLayout.EAST);
	}
	
	private void addNewAdditionalSkill(Skill selectedSkill)
	{
		unit.getAdditionalSkills().add(selectedSkill);
		
		makeSelectedSkillLabel(selectedSkill);
		updateSkillsCounters(selectedSkill);
		updateAddedSkillsPanelView();
	}

	private void makeSelectedSkillLabel(Skill selectedSkill)
	{
		SkillLabel selectedSkillLabel = new SkillLabel(selectedSkill, SwingConstants.CENTER);
		additionalSkillsLabels.put(selectedSkill, selectedSkillLabel);
		addedSkillsPanel.add(selectedSkillLabel);
	}

	private void updateSkillsCounters(Skill selectedSkill)
	{
		unit.increaseAdditionalSkillsNumber();
		unit.increaseAdditionalSkillsValue(selectedSkill.getValue());
		updateValue();
	}

	private void updateAddedSkillsPanelView()
	{
		additionalSkillsCounter.setText(String.valueOf(unit.getAdditionalSkillsNumber()));
		addedSkillsPanel.revalidate();
	}

	private void makeAddedSkillsPanel(MyPanel additionalSkillsPanel)
	{
		additionalSkillsLabels = new HashMap<>();
		
		addedSkillsPanel = new JPanel(new GridLayout(0, 1));
		addedSkillsPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		additionalSkillsPanel.add(addedSkillsPanel, BorderLayout.CENTER);
		
		addedSkillsPanel.addContainerListener(new ContainerListener()
		{
			@Override
			public void componentRemoved(ContainerEvent event)
			{
				checkHavingASpecialistSkill();
			}

			@Override
			public void componentAdded(ContainerEvent event)
			{
				checkHavingASpecialistSkill();
			}
			
			private void checkHavingASpecialistSkill()
			{
				if(unit.isSpecialist())
				{
					addSpecialWeaponButton.setEnabled(true);
					addSpecialWeaponButton.setToolTipText("Kliknij, aby dodaæ przedmiot");
				}
				else
				{
					addSpecialWeaponButton.setEnabled(false);
					addSpecialWeaponButton.setToolTipText("Wymagana umiejêtnoœæ: Specjalista");
					deleteSpecialWeapons();
				}
			}

			private void deleteSpecialWeapons()
			{
				ArrayList<OrdinaryEquipment> specialWeapons = new ArrayList<>(2);
				
				for(OrdinaryEquipment item : unit.getEquipment())
					if(item instanceof SpecialWeapons)
						specialWeapons.add(item);
				
				for(OrdinaryEquipment item : specialWeapons)
						deleteItem(item);
			}
		});
	}
	
	//****************************************************************************//

	private void makeRightPanel(ArmyModel armyModel)
	{
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		
		makeWeaponsPanel(armyModel);
		makeBottomRightPanel();

		centerPanel.add(rightPanel);
	}
	
	private void makeWeaponsPanel(ArmyModel armyModel)
	{
		equipmentPanel = new MyPanel(new BorderLayout(), "Wyposa¿enie");
		
		if(unit.getMaxEquipmentValue() > 0)
		{
			makeEquipmentChoosersPanels(armyModel);
			
			equipmentChoosersOutsidePanel.add(equipmentChoosersInsidePanel);
			equipmentPanel.add(equipmentChoosersOutsidePanel, BorderLayout.NORTH);
			
			makeAddedItemsPanel(equipmentPanel);
		}
		
		rightPanel.add(equipmentPanel);
	}
	
	private void makeEquipmentChoosersPanels(ArmyModel armyModel)
	{
		equipmentChoosersOutsidePanel = new JPanel(new BorderLayout());
		equipmentChoosersOutsidePanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		equipmentChoosersInsidePanel = new JPanel(new GridLayout(0,1));
		equipmentChoosersInsidePanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		
		makeOridinaryEquipmentPanel(armyModel);
		makeArmorPanel(armyModel);
		makeWeaponForCloseCombatPanel(armyModel);
		makeSmallArmsPanel(armyModel);
		makeSpecialWeaponsPanel(armyModel);
	}

	private void makeOridinaryEquipmentPanel(ArmyModel armyModel)
	{
		JPanel ordinaryEquipmentPanel = makeWeaponChooserPanel(
				armyModel.getOrdinaryEquipment(), "Zwyczajny ekwipunek");
		equipmentChoosersInsidePanel.add(ordinaryEquipmentPanel);
		
		makeEquipmentValueCounterPanel(ordinaryEquipmentPanel);
	}

	private void makeArmorPanel(ArmyModel armyModel)
	{
		JPanel armorPanel = makeWeaponChooserPanel(
				armyModel.getArmor(), "Pancerz");
		equipmentChoosersInsidePanel.add(armorPanel);
	}

	private void makeWeaponForCloseCombatPanel(ArmyModel armyModel)
	{
		JPanel weaponForCloseCombatPanel = makeWeaponChooserPanel(
				armyModel.getWeaponForCloseCombat(), "Broñ do walki w zwarciu");
		equipmentChoosersInsidePanel.add(weaponForCloseCombatPanel);
	}

	private void makeSmallArmsPanel(ArmyModel armyModel)
	{
		JPanel smallArmsPanel = makeWeaponChooserPanel(
				armyModel.getSmallArms(), "Broñ strzelecka");
		equipmentChoosersInsidePanel.add(smallArmsPanel);
	}

	private void makeSpecialWeaponsPanel(ArmyModel armyModel)
	{
		JPanel specialWeaponsPanel = makeWeaponChooserPanel(
				armyModel.getSpecialWeapons(), "Broñ specjalna");
		equipmentChoosersInsidePanel.add(specialWeaponsPanel);
		
		final int SPECIA_WEAPON_BUTTON_ID = 1;
		addSpecialWeaponButton = (JButton) specialWeaponsPanel.getComponent(SPECIA_WEAPON_BUTTON_ID);
		
		if(!unit.getBasicSkills().contains(BasicSkill.SPECIALIST))
		{
			addSpecialWeaponButton.setEnabled(false);
			addSpecialWeaponButton.setToolTipText("Wymagana umiejêtnoœæ: \'Specjalista\'.");
		}
	}

	private void makeEquipmentValueCounterPanel(JPanel ordinaryEquipmentPanel)
	{
		JPanel equipmentValueCounterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		equipmentValueCounterPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		equipmentValueCounter = new SLabel(String.valueOf(unit.getEquipmentValue()));
		
		equipmentValueCounterPanel.add(equipmentValueCounter);
		equipmentValueCounterPanel.add(new SLabel(" / " + String.valueOf(unit.getMaxEquipmentValue())));

		ordinaryEquipmentPanel.add(equipmentValueCounterPanel, BorderLayout.WEST);
	}

	private JPanel makeWeaponChooserPanel(Vector<?> vector, String title)
	{
		JPanel weaponPanel = new JPanel(new BorderLayout());
		
		JComboBox<?> weaponChooser = makeWeaponChooser(weaponPanel, vector);
		makeAddWeaponButton(weaponPanel, weaponChooser);

		weaponPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		weaponPanel.add(new SLabel(title, SwingConstants.CENTER), BorderLayout.NORTH);
		return weaponPanel;
	}

	private JComboBox<?> makeWeaponChooser(
			JPanel weaponPanel, Vector<?> vector)
	{
		JComboBox<?> weaponChooser = new JComboBox<>(vector);
		
		weaponChooser.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent event)
			{
				OrdinaryEquipment selectedItem = (OrdinaryEquipment) weaponChooser.getSelectedItem();
				generatorFrame.setInfo(selectedItem);
			}
		});

		weaponPanel.add(weaponChooser, BorderLayout.CENTER);
		return weaponChooser;
	}

	private void makeAddWeaponButton(JPanel weaponPanel, JComboBox<?> weaponChooser)
	{
		JButton addWeaponButton = new JButton("Dodaj");
		addWeaponButton.setToolTipText("Kliknij, aby dodaæ przedmiot.");
		addWeaponButton.addActionListener(new ActionListener()
		{
			GeneratorError error;
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				error = new GeneratorError();
				OrdinaryEquipment selectedItem = (OrdinaryEquipment) weaponChooser.getSelectedItem();
				
				checkWeaponCriteria(selectedItem);
				
				if(error.isOccurred())
				{
					generatorFrame.setInfo("B³¹d", generatorFrame.getErrorDescriptions()
							.getOrDefault(error.getName(), GeneratorError.UNKNOWN_ERROR));
				}
				else
				{
					addNewItem(selectedItem);
					generatorFrame.setInfo("Dodano nowy przedmiot", selectedItem.getName());
				}
			}

			private void checkWeaponCriteria(OrdinaryEquipment selectedItem)
			{
				if(unit.getMaxEquipmentValue() < unit.getEquipmentValue() + selectedItem.getValue())
					error.setOccurredAndName(true, GeneratorError.WEAPONS_VALUE_LIMIT);
				
				else if(unit.getEquipment().contains(selectedItem))
					error.setOccurredAndName(true, GeneratorError.ALREADY_OWNED_WEAPON);
			}
		});
	
		weaponPanel.add(addWeaponButton, BorderLayout.EAST);
	}
	
	private void addNewItem(OrdinaryEquipment selectedItem)
	{
		unit.getEquipment().add(selectedItem);
		
		makeSelectedItemLabel(selectedItem);
		updateItemsCounters(selectedItem);
		updateAddedItemsPanelView();
	}

	private void makeSelectedItemLabel(OrdinaryEquipment selectedItem)
	{
		ItemLabel selectedItemLabel = new ItemLabel(selectedItem, SwingConstants.CENTER);
		itemsLabels.put(selectedItem, selectedItemLabel);
		addedItemsPanel.add(selectedItemLabel);
	}

	private void updateItemsCounters(OrdinaryEquipment selectedItem)
	{
		unit.increaseEquipmentValue(selectedItem.getValue());
		updateValue();
	}

	private void updateAddedItemsPanelView()
	{
		equipmentValueCounter.setText(String.valueOf(unit.getEquipmentValue()));
		addedItemsPanel.revalidate();
	}

	private void makeAddedItemsPanel(MyPanel equipmentPanel)
	{
		itemsLabels = new HashMap<>();
		
		addedItemsPanel = new JPanel(new WrapLayout());
		addedItemsPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		equipmentPanel.add(addedItemsPanel, BorderLayout.CENTER);
	}
	

	protected void makeBottomRightPanel()
	{
		makeValuePanel();
	}
	
	private void makeValuePanel()
	{
		MyPanel valuePanel = new MyPanel(new BorderLayout(), "Wartoœæ oddzia³u");
		
		value = new SLabel("0", SLabel.CENTER);
		value.setFont(generatorFrame.VALUE_FONT);
		
		valuePanel.add(value, BorderLayout.CENTER);
		updateValue();
		rightPanel.add(valuePanel);
	}

	//****************************************************************************//
	
	private void makeStatisticsPanel()
	{
		statisticsPanel = new MyPanel(new GridLayout(1, 0), "Statystyki");
		
		makeStatLabel("PA", "Punkty Akcji", unit.getActionPoints());
		makeStatLabel("US", "Umiejêtnoœci Strzeleckie", unit.getShootingSkills());
		makeStatLabel("UW", "Umiejêtnoœci Walki", unit.getFightingSkills());
		makeStatLabel("ZW", "Zwinnoœæ", unit.getAgility());
		makeStatLabel("SI", "Si³a", unit.getStrength());
		makeStatLabel("WT", "Wytrzyma³oœæ", unit.getStamina());
		makeStatLabel("SW", "Si³a Woli", unit.getWillpower());
		makeStatLabel("OB", "Obrona", unit.getDefenseFirst(), unit.getDefenseSecond());
		
		statisticsPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		mainPanel.add(statisticsPanel, BorderLayout.SOUTH);
	}
	
	//****************************************************************************//

	protected void updateValue()
	{
		value.setText(String.valueOf(unit.getValue()));
		revalidate();
	}

	
	private class SkillLabel extends MyLabel
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SkillLabel(Skill skill, int align)
		{
			super(skill, align);
			JPopupMenu popupMenu = new JPopupMenu();
			popupMenu.add(new AbstractAction("Usuñ")
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent event)
				{
					unit.getAdditionalSkills().remove(skill);
					
					updateCounters(skill);
					updateAdditionalSkillsPanel(skill);
					
					generatorFrame.setInfo("Usuniêto umiejêtnoœæ", skill.getName());
				}
				
				private void updateCounters(Skill skill)
				{
					unit.decreaseAdditionalSkillsNumber();
					unit.decreaseAdditionalSkillsValue(skill.getValue());
					updateValue();
				}

				private void updateAdditionalSkillsPanel(Skill skill)
				{
					addedSkillsPanel.remove(additionalSkillsLabels.get(skill));
					additionalSkillsCounter.setText(String.valueOf(unit.getAdditionalSkillsNumber()));
					addedSkillsPanel.revalidate();
				}
			});
			setComponentPopupMenu(popupMenu);
			setToolTipText("<html>Kliknij LPM, aby zobaczyæ pe³ny opis.<br>"
					+ "Kliknij PPM aby otworzyæ menu.</html>");
		}
		
	}
	
	private class ItemLabel extends MyLabel
	{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ItemLabel(OrdinaryEquipment item, int align)
		{
			super(item, align);
			JPopupMenu popupMenu = new JPopupMenu();
			popupMenu.add(new AbstractAction("Usuñ")
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent event)
				{
					deleteItem(item);
				}
			});
			setComponentPopupMenu(popupMenu);
			setToolTipText("<html>Kliknij LPM, aby zobaczyæ pe³ny opis.<br>"
					+ "Kliknij PPM aby otworzyæ menu.</html>");
		}
	}
	
	private void deleteItem(OrdinaryEquipment item)
	{
		unit.getEquipment().remove(item);
		
		updateCounters(item);
		updateEquipmentPanel(item);
		
		generatorFrame.setInfo("Usuniêto umiejêtnoœæ", item.getName());
	}
	
	private void updateCounters(OrdinaryEquipment item)
	{
		unit.decreaseEquipmentValue(item.getValue());
		updateValue();
	}

	private void updateEquipmentPanel(OrdinaryEquipment item)
	{
		addedItemsPanel.remove(itemsLabels.get(item));
		equipmentValueCounter.setText(String.valueOf(unit.getEquipmentValue()));
		addedItemsPanel.revalidate();
	}
	
	/**
	 *	Statistic Label
	 */
	private class StatLabel extends SLabel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private StatLabel(String name, String fullName, int align)
		{
			super(name, align);
			setBorder(makeBorder(2, 2, ""));
			setToolTipText(fullName);
		}
	}
	
	private void makeStatLabel(String acronym, String fullName, int value)
	{
		acronym += ": " + Integer.toString(value);
		statisticsPanel.add(new StatLabel(acronym, fullName, SwingConstants.CENTER));
	}
	private void makeStatLabel(String acronym, String fullName, int firstValue, int secondValue)
	{
		acronym += ": " + Integer.toString(firstValue) + " / " + Integer.toString(secondValue);
		statisticsPanel.add(new StatLabel(acronym, fullName, SwingConstants.CENTER));
	}
	
}