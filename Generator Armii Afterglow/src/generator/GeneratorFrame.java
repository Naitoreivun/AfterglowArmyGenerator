package generator;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import equipment.OrdinaryEquipment;
import panels.*;
import resources.ResourceLoader;
import skills.Skill;
import models.*;

public class GeneratorFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WIDTH = 1000;
	private static final int DEFAULT_HEIGHT = 800;
	public static final int PANEL_DEFAULT_WIDTH = 800;
	public static final int PANEL_DEFAULT_HEIGHT = 600;
	private static final int LIST_DEFAULT_WIDTH = 200;
	private static final int LIST_DEFAULT_HEIGHT = 600;
	private static final int INFO_DEFAULT_WIDTH = 1000;
	private static final int INFO_DEFAULT_HEIGHT = 200;
	
	public final Font FONT;
	public final Font BIG_FONT;
	public final Font VALUE_FONT;
	public final Color FONT_COLOR;
	public final Color BACKGROUND_COLOR;
	
	private JEditorPane infoEditorPane;
//	private boolean saved; //czy musimy zapisac zmiany //TODO: to to sie musi inazcej nazywac :x

	private String[] factionsNames;
	
	private Map<String, UnitModel> army;
	private JList<String> unitsList;
	private DefaultListModel<String> unitsListModel;
	private ArmyModel armyModel;
	
	private String[] divisionsNames;
	private JButton addDivisionButton;
	private JToggleButton showArmyPanelButton;

	private JPanel infoPanel;
	
	private JPanel topPanel;
	
	private JPanel layerPanel;
	private CardLayout cardLayout;
	private ArmyPanel armyPanel;
	private static final String ARMY = "ARMY";
	private Map<String, JPanel> unitsPanels;
	
	private GeneratorError error;
	private Map<String, String> errorDescriptions;
	
	private List<ArrayList<Skill>> skillsInGameForAll;
	private List<ArrayList<Skill>> skillsInGameForLeader;
	
	private ButtonGroup leadersButtonGroup;
	
	private Scanner scan;
	
	public GeneratorFrame()
	{	
		/*****************************************************************/
		/*****************     Podstawowe ustawienia    ******************/
		/*****************************************************************/

		FONT = new Font("Arial", Font.BOLD, 12);
		BIG_FONT = new Font(FONT.getName(), FONT.getStyle(), 30);
		VALUE_FONT = new Font(FONT.getFontName(), FONT.getStyle(), 60);
		FONT_COLOR = Color.WHITE;
		BACKGROUND_COLOR = new Color(115,164,209);
		
		UIManager.put("info", FONT_COLOR);
		setMinimumSize(new Dimension(DEFAULT_WIDTH - 100, DEFAULT_HEIGHT - 100));
		setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
//		saved = true; //TODO
		

		InputStream input = ResourceLoader.getArmiesList();
		scan = new Scanner(input);
		loadFactionsNames();
		input = ResourceLoader.getErrors();
		scan = new Scanner(input);
		loadErrorsDescriptions();
		makeMenu();
		
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		
		makeSplitPane();
		
		loadSkillsInGame();
		
		makeNimbusStyle();
		
		scan.close();
	}

	private void loadFactionsNames()
	{
		final int FACTIONS_NUMBER = Integer.parseInt(scan.nextLine());
		factionsNames = new String[FACTIONS_NUMBER];
		for(int i=0; i < factionsNames.length; i++)
			factionsNames[i] = scan.nextLine().trim();
	}
	
	private void loadErrorsDescriptions()
	{
		errorDescriptions = new HashMap<>();
		while(scan.hasNextLine())
			errorDescriptions.put(scan.nextLine().trim(), scan.nextLine().trim());
	}
	
	//****************************************************************************//
	
	private void makeMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(makeFileMenu());
		menuBar.add(makeInfoMenu());
		setJMenuBar(menuBar);
	}
	
	private JMenu makeFileMenu()
	{
		JMenu file = new JMenu("Plik");
		
		file.add(makeNewArmyMenuItem());
		file.add(makeLoadMenuItem());
		file.addSeparator();
		file.add(makeSaveMenuItem());
		file.add(makeSaveAsMenuItem());
		file.addSeparator();
		file.add(makeExitMenuItem());
		
		//Wiem, ¿e te 3 linijki ni¿ej (i ten komentarz) nie powinny tak wygladac, ale to na szybko | TODO: zmien to kiedys
		file.getMenuComponent(1).setEnabled(false); //wczytaj
		file.getMenuComponent(3).setEnabled(false); //zapisz
		file.getMenuComponent(4).setEnabled(false); //zapisz jako
		
		return file;
	}
	
	private JMenuItem makeNewArmyMenuItem()
	{
		MyAction newArmyAction = new MyAction("Nowa Armia", "Stwórz now¹ armiê", "new")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String choseArmyName;
			@Override
			public void actionPerformed(ActionEvent event)
			{
				//TODO: tutaj dodaj mozliwosc zapisu jesli to nie pierwsza armia
				clearAll();
				chooseFaction();
			}

			private void clearAll()
			{
				unitsListModel.removeAllElements();
				unitsList.removeAll();
				layerPanel.removeAll();
			}
			
			private void chooseFaction()
			{
				String choseFaction = showArmyChooserDialog();
				if (choseFaction != null)
				{
					makeArmy(choseFaction);
					makeArmyPanel();
				}
			}

			private String showArmyChooserDialog()
			{
				return (String) JOptionPane.showInternalInputDialog(getContentPane(), "Wybierz frakcjê:",
						"Tworzenie armii", JOptionPane.QUESTION_MESSAGE, null, factionsNames, factionsNames[0]);
			}

			private void makeArmy(String choseFaction)
			{
				String choseName = showNameFieldDialog();
				armyModel = new ArmyModel(choseFaction);
				armyModel.setArmyName(choseName);
				setInfo("Armia zosta³a stworzona");
				
				army = armyModel.getArmy();
				loadDivisionsNames();
			}

			private String showNameFieldDialog()
			{
				choseArmyName = JOptionPane.showInternalInputDialog(getContentPane(), "Wpisz nazwê swojej armii:",
						"Tworzenie armii", JOptionPane.QUESTION_MESSAGE);
				return choseArmyName == null || "".equals(choseArmyName) ? "Bezimienni" : choseArmyName;
			}
			
			private void loadDivisionsNames()
			{
				divisionsNames = new String[armyModel.getFactionUnitsNames().size()];
				divisionsNames = armyModel.getFactionUnitsNames().toArray(divisionsNames);
				addDivisionButton.setEnabled(true); //TODO to i to wyzej armybutton tez musza dzialac na tego boola co zapis
				//TODO w sensie ze jak zapisalem i robie nowa armie to on ma byc... eee? cos tam jakos
			}
			
			private void makeArmyPanel()
			{
				makeLayerPanel(choseArmyName);
				showArmyPanelButton.setEnabled(true);
			}
			
			private void makeLayerPanel(String armyName)
			{
				leadersButtonGroup = new ButtonGroup();
				unitsPanels = new HashMap<>();
				armyPanel = new ArmyPanel(GeneratorFrame.this, armyModel);
				layerPanel.add(armyPanel, ARMY);
				cardLayout.show(layerPanel, ARMY);
			}
		};
		return new JMenuItem(newArmyAction);
	}

	private JMenuItem makeLoadMenuItem()
	{
		MyAction loadAction = new MyAction("Wczytaj...", "Wczytaj armiê", "load")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				//TODO: System.out.println("Okno wybory pliku...");
			}
		};
		return new JMenuItem(loadAction);
	}
	
	private JMenuItem makeSaveMenuItem()
	{
		MyAction saveAction = new MyAction("Zapisz", "Zapisz armiê", "save")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				//TODO: System.out.println("Zapisuje...");
			}
		};
		return new JMenuItem(saveAction);
	}
	
	private JMenuItem makeSaveAsMenuItem()
	{
		MyAction saveAsAction = new MyAction("Zapisz jako...", "Zapisz armiê jako...", "saveAs")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				//TODO: System.out.println("Okno zapisu pliku...");
			}
		};
		return new JMenuItem(saveAsAction);
	}
	
	private JMenuItem makeExitMenuItem()
	{
		MyAction exitAction = new MyAction("Zamknij", "Zamknij program", "exit")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				//TODO: System.out.println("Czy na pewno chcesz zamkn¹æ?");
				System.exit(0);
			}
		};
		return new JMenuItem(exitAction);
	}
	
	private JMenu makeInfoMenu()
	{
		JMenu info = new JMenu("Informacje");
		info.add(new JMenuItem(new AbstractAction("O programie")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setInfo("O programie", "Autor: Naitoreivun -> https://github.com/Naitoreivun<br>"
						+ "Wersja: 1.0 (zabugowana beta)<br>"
						+ "Opis: Program ma celu pomaganie w utworzeniu armii do gry \"Afterglow\" -> http://afterglowthegame.com/");
			}
		}));
		return info;
	}
	
	class MyAction extends AbstractAction
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyAction(String name, String description, String iconFileName)
		{
//			URL iconPath = ResourceLoader.getMenuIcon(iconFileName);
//			ImageIcon icon = new ImageIcon(iconPath);
			
			putValue(Action.NAME, name);
			putValue(Action.SHORT_DESCRIPTION, description);
//			putValue(Action.SMALL_ICON, icon);
		}
		
		@Override
		public void actionPerformed(ActionEvent event)
		{
			System.out.println("Chyba zapomnia³em przes³oniæ metodê...");
		}
	}
	
	//****************************************************************************//
	
	private void makeSplitPane()
	{
		makeInfoPanel();
		makeTopPanel();
		JSplitPane mainPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, infoPanel);
		mainPane.setContinuousLayout(true);
		mainPane.setOneTouchExpandable(true);
		mainPane.setDividerLocation(DEFAULT_HEIGHT * 7 / 10);
		add(mainPane, BorderLayout.CENTER);
	}
	
	private void makeInfoPanel()
	{
		infoPanel = new JPanel(new BorderLayout());
		makeInfoEditorPane();
		infoPanel.add(new JScrollPane(infoEditorPane));
		customizeTheLookOfInfoPanel(infoPanel);
		add(infoPanel, BorderLayout.SOUTH);
	}

	private void makeInfoEditorPane()
	{
		infoEditorPane = new JEditorPane("text/html; charset=utf-8",
				"<h1 style=\"font-size: 35; \"><center>Generator Armii Afterglow 1.0<br>Twórca: Naitoreivun</center></h1>");
		infoEditorPane.setEditable(false);
		customizeTheLookOfInfoEditorPane();
	}

	private void customizeTheLookOfInfoEditorPane()
	{
		UIDefaults defaults = new UIDefaults();
		defaults.put("EditorPane[Enabled].backgroundPainter", BACKGROUND_COLOR);
		infoEditorPane.putClientProperty("Nimbus.Overrides", defaults);
		infoEditorPane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
		infoEditorPane.setFont(FONT);
		infoEditorPane.setForeground(FONT_COLOR);
		infoEditorPane.setBackground(BACKGROUND_COLOR);
	}
	
	private void customizeTheLookOfInfoPanel(JPanel infoPanel)
	{
		infoPanel.setBorder(GeneratorFrame.makeBorder());
		infoPanel.setPreferredSize(new Dimension(INFO_DEFAULT_WIDTH, INFO_DEFAULT_HEIGHT)); 
	}

	public void setInfo(String main)
	{
		main = "<h1 style=\"font-size: xx-large; \"><center>" + main + "</center></h1>";
		infoEditorPane.setText(main);
	}
	
	public void setInfo(String main, String text)
	{
		main = "<h2 style=\"font-size: xx-large; \"><center>" + main + "</center></h2>";
		text = "<h3 style=\"font-size: large; \"><i>" + text + "</i></h3>";
		infoEditorPane.setText("<html>" + main + text + "</html>");
	}
	
	public void setInfo(String name, String description, String value)
	{
		name = "<h2 style=\"font-size: xx-large;\"><center>" + name + "</center></h2>";
		description = "<br><h3 style=\"font-size: large;\"><i>" + description + "</i>"
				+ "<br><br>Koszt: " + value + "</h3>";
		infoEditorPane.setText(name + description);
	}
	
	public void setInfo(Skill skill)
	{
		setInfo(skill.getName(), skill.getDescription(), String.valueOf(skill.getValue()));
	}
	
	public void setInfo(OrdinaryEquipment item)
	{
		setInfo(item.getName(), item.getDescription());
	}
	
	//****************************************************************************//
		
	private void makeTopPanel()
	{
		topPanel = new JPanel(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(DEFAULT_WIDTH, PANEL_DEFAULT_HEIGHT));
		makeListPanel();
		makeLayerPanel();
	}
	
	private void makeListPanel()
	{
		JPanel listPanel = new JPanel(new BorderLayout());
		customizeTheLookOfListPanel(listPanel);
		
		listPanel.add(new JScrollPane(makeUnitsList()), BorderLayout.CENTER);
		makeListPanelButtons(listPanel);
		
		topPanel.add(listPanel, BorderLayout.EAST);
	}

	private void customizeTheLookOfListPanel(JPanel listPanel)
	{
		listPanel.setBorder(GeneratorFrame.makeBorder());
		listPanel.setPreferredSize(new Dimension(LIST_DEFAULT_WIDTH, LIST_DEFAULT_HEIGHT));
	}

	private JList<String> makeUnitsList()
	{
		unitsListModel = new DefaultListModel<>();
		unitsList = new JList<>(unitsListModel);
		
		addUnitsListListeners();
		addUnitsListPopupMenu();
		customizeTheLookOfUnitsList();
		
		return unitsList;
	}

	private void addUnitsListListeners()
	{
		unitsList.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent event)
			{
				showSelectedUnitPanel();
			}
		});
		
		unitsList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent arg0)
			{
				showSelectedUnitPanel();
			}
		});
	}
	
	private void showSelectedUnitPanel()
	{
		UnitModel unit = army.get(unitsList.getSelectedValue());
		if(unit != null)
			cardLayout.show(layerPanel, unit.getNick());
		else
		{
			armyPanel.updatePanel();
			cardLayout.show(layerPanel, ARMY);
		}
	}

	private void addUnitsListPopupMenu()
	{
		JPopupMenu listMenu = new JPopupMenu();
		listMenu.add(new MyAction("Usuñ", "Usuñ wybrany oddzia³", "exit")
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					String selection = unitsList.getSelectedValue();
					removeSelectedUnit(selection);
					armyPanel.updatePanel();
				}
				catch(NullPointerException e)
				{
					setInfo("Brak oddzia³u do usuniêcia.");
				}
			}

			private void removeSelectedUnit(String selection) throws NullPointerException
			{
				UnitModel unit = army.get(selection);
				layerPanel.remove(unitsPanels.get(unit.getNick()));
				army.remove(selection);
				unitsListModel.removeElement(selection);
				
				setInfo("Oddzia³ zosta³ usuniêty");
			}
		});
		unitsList.setComponentPopupMenu(listMenu);
	}
	
	private void customizeTheLookOfUnitsList()
	{
		unitsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		unitsList.setBackground(BACKGROUND_COLOR);
		unitsList.setForeground(FONT_COLOR);
		unitsList.setFont(FONT);
	}

	private void makeListPanelButtons(JPanel listPanel)
	{
		makeShowArmyPanelButton();
		listPanel.add(showArmyPanelButton, BorderLayout.NORTH);
		makeAddDivisionButton();
		listPanel.add(addDivisionButton, BorderLayout.SOUTH);
	}
	
	private void makeShowArmyPanelButton()
	{
		showArmyPanelButton = new JToggleButton("Poka¿ panel armii");
		showArmyPanelButton.setEnabled(false);
		showArmyPanelButton.setSelected(true);
		
		showArmyPanelButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				if(!showArmyPanelButton.isSelected())
				{
					showFirstDivision();
				}
				else
				{
					showArmy();
				}
			}

			private void showFirstDivision()
			{
				if(army.size() > 0)
				{
					final int FIRST_POSITION = 0;
					String first_division = unitsListModel.get(FIRST_POSITION);
					unitsList.setSelectedIndex(FIRST_POSITION);
					cardLayout.show(layerPanel, first_division);
				}
				else //do nothing
				{
					showArmyPanelButton.setSelected(true);
				}
			}

			private void showArmy()
			{
				unitsList.clearSelection();
				armyPanel.updatePanel();
				cardLayout.show(layerPanel, ARMY);
			}
		});
	}
	
	public void setSelectedShowArmyPanelButton(boolean selected)
	{
		showArmyPanelButton.setSelected(selected);
	}
	
	private void makeAddDivisionButton()
	{
		addDivisionButton = new JButton("Dodaj oddzia³");
		addDivisionButton.setEnabled(false);
		addDivisionButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				error = new GeneratorError();
				String choseDivision = chooseDivision();
				if (choseDivision != null)
				{
					String choseNick = chooseNick(choseDivision);
					UnitModel unitModel = new UnitModel(choseDivision, armyModel.getFactionName());
					addNewUnit(unitModel, choseNick, choseDivision);
					setInfo("Oddzia³ zosta³ stworzony");
				}
			}
			private String chooseDivision()
			{
				return (String) JOptionPane.showInternalInputDialog(getContentPane(), "Wybierz oddzia³:",
						"Tworzenie oddzia³u", JOptionPane.QUESTION_MESSAGE, null, divisionsNames, divisionsNames[0]);
			}

			private String chooseNick(String choseDivision)
			{
				String nick;
				do
				{
					error.setOccurred(false);
					nick = (String) JOptionPane.showInternalInputDialog(getContentPane(), "Wpisz nazwê oddzia³u:", 
							"Tworzenie oddzia³u", JOptionPane.QUESTION_MESSAGE);
					if(nick == null || "".equals(nick) || " ".equals(nick))
						nick = choseDivision;
					if(army.containsKey(nick))
					{
						error.setOccurredAndName(true, GeneratorError.BUSY_NICK);
						setInfo("B³¹d", errorDescriptions.getOrDefault(error.getName(), GeneratorError.UNKNOWN_ERROR));
					}
				}
				while(error.isOccurred());
				return nick;
			}
		});
	}
	
	private void addNewUnit(UnitModel unitModel, String choseNick, String choseDivision)
	{
		makeUnit(unitModel, choseNick, choseDivision);
		makeUnitPanel(unitModel, choseNick);
		addUnitToList(choseNick);
	}
	
	private void makeUnit(UnitModel unitModel, String choseNick, String choseDivision)
	{
		switch(unitModel.getType())
		{
		case UnitType.HOUND:
			makeHound(choseNick, choseDivision);
			break;
		
		case UnitType.FANATIC:
			makeFanatic(choseNick, choseDivision);
			break;
			
		case UnitType.DELARIAN_FOOTMAN:
			makeDelarianFootman(choseNick, choseDivision);
			break;
			
		case UnitType.CLEANER:
			makeCleaner(choseNick, choseDivision);
			break;

		case UnitType.SKULL_WARRIOR:
			makeSkullWarrior(choseNick, choseDivision);
			break;

		case UnitType.ACOLYTE:
			makeAcolyte(choseNick, choseDivision);
			break;
			
		case UnitType.PROPHET:
			makeProphet(choseNick, choseDivision);
			break;

		case UnitType.INQUISITOR:
			makeInquisitor(choseNick, choseDivision);
			break;
			
		case UnitType.CHAPLAIN:
			makeChaplain(choseNick, choseDivision);
			break;
			
		default:
			error.setOccurredAndName(true, GeneratorError.NOT_EXISTS);
		}
	}
	
	private void makeHound(String choseNick, String choseDivision)
	{
		HoundModel houndModel = new HoundModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, houndModel);
		unitsPanels.put(choseNick, new HoundPanel(GeneratorFrame.this, choseNick,
				armyModel, houndModel));
	}
	
	private void makeFanatic(String choseNick, String choseDivision)
	{
		FanaticModel fanaticModel = new FanaticModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, fanaticModel);
		unitsPanels.put(choseNick, new FanaticPanel(GeneratorFrame.this, choseNick,
				armyModel, fanaticModel));
	}
	
	private void makeDelarianFootman(String choseNick, String choseDivision)
	{
		DelarianFootmanModel delarianFootmanModel = new DelarianFootmanModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, delarianFootmanModel);
		unitsPanels.put(choseNick, new DelarianFootmanPanel(GeneratorFrame.this, choseNick,
				armyModel, delarianFootmanModel));
	}
	
	private void makeCleaner(String choseNick, String choseDivision)
	{
		CleanerModel cleanerModel = new CleanerModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, cleanerModel);
		unitsPanels.put(choseNick, new CleanerPanel(GeneratorFrame.this, choseNick,
				armyModel, cleanerModel));
	}
	
	private void makeSkullWarrior(String choseNick, String choseDivision)
	{
		SkullWarriorModel skullWarriorModel = new SkullWarriorModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, skullWarriorModel);
		unitsPanels.put(choseNick, new SkullWarriorPanel(GeneratorFrame.this, choseNick,
				armyModel, skullWarriorModel));
	}
	
	private void makeAcolyte(String choseNick, String choseDivision)
	{
		AcolyteModel acolyteModel = new AcolyteModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, acolyteModel);
		unitsPanels.put(choseNick, new AcolytePanel(GeneratorFrame.this, choseNick,
				armyModel, acolyteModel));
	}
	
	private void makeProphet(String choseNick, String choseDivision)
	{
		ProphetModel prophetModel = new ProphetModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, prophetModel);
		unitsPanels.put(choseNick, new ProphetPanel(GeneratorFrame.this, choseNick,
				armyModel, leadersButtonGroup, prophetModel));
	}

	private void makeInquisitor(String choseNick, String choseDivision)
	{
		InquisitorModel inquisitorModel = new InquisitorModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, inquisitorModel);
		unitsPanels.put(choseNick, new InquisitorPanel(GeneratorFrame.this, choseNick,
				armyModel, leadersButtonGroup, inquisitorModel));
	}
	
	private void makeChaplain(String choseNick, String choseDivision)
	{
		ChaplainModel chaplainModel = new ChaplainModel(choseDivision, armyModel.getFactionName());
		army.put(choseNick, chaplainModel);
		unitsPanels.put(choseNick, new ChaplainPanel(GeneratorFrame.this, choseNick,
				armyModel, leadersButtonGroup, chaplainModel));
	}

	private void makeUnitPanel(UnitModel unitModel, String choseNick)
	{
		unitModel = army.get(choseNick);
		unitModel.setNick(choseNick);
		layerPanel.add(unitsPanels.get(choseNick), unitModel.getNick());
		cardLayout.show(layerPanel, unitModel.getNick());
	}
	
	private void addUnitToList(String choseNick)
	{
		unitsListModel.addElement(choseNick);
		unitsList.setSelectedIndex(army.size() - 1);
	}

	//****************************************************************************//
	
	private void makeLayerPanel()
	{
		cardLayout = new CardLayout();
		layerPanel = new JPanel(cardLayout);
		
		layerPanel.setPreferredSize(new Dimension(PANEL_DEFAULT_WIDTH, PANEL_DEFAULT_HEIGHT));
		topPanel.add(layerPanel, BorderLayout.CENTER);
	}
	
	//****************************************************************************//
	
	private void loadSkillsInGame()
	{	
		final int AMOUNT_OF_SKILL_TYPES = 4;
		
		skillsInGameForAll = new ArrayList<>(AMOUNT_OF_SKILL_TYPES);
		loadSkillsInGameForAll();
		
		skillsInGameForLeader = new ArrayList<>(AMOUNT_OF_SKILL_TYPES);
		loadSkillsInGameForLeader();
	}
	
	private void loadSkillsInGameForAll()
	{
		boolean FOR_ALL = true;
		loadSkills(skillsInGameForAll, FOR_ALL);
	}

	private void loadSkillsInGameForLeader()
	{
		final boolean FOR_ALL = false;
		loadSkills(skillsInGameForLeader, FOR_ALL);
	}
	
	private void loadSkills(List<ArrayList<Skill>> skillsInGame, boolean forAll)
	{
		skillsInGame.add(loadSkills("Tactics Skills", forAll));
		skillsInGame.add(loadSkills("Mind Skills", forAll));
		skillsInGame.add(loadSkills("Loot Skills", forAll));
		skillsInGame.add(loadSkills("Combat Skills", forAll));
	}

	private ArrayList<Skill> loadSkills(String fileName, boolean forAll)
	{
		InputStream input = ResourceLoader.getSkills(fileName, forAll);
		scan = new Scanner(input);
		ArrayList<Skill> skillsFromFile = new ArrayList<>();
		
		if(scan.nextBoolean())
		{
			scan.nextLine();
			while(scan.hasNextLine())
				skillsFromFile.add(loadSkill());
		}
		
		return skillsFromFile;
	}
	
	private Skill loadSkill()
	{
		String name = scan.nextLine().trim();
		int value = Integer.parseInt(scan.nextLine().trim());
		String description = scan.nextLine();
		return new Skill(name, description, value);
	}

	//****************************************************************************//
	
	private void makeNimbusStyle()
	{
		try
		{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(GeneratorFrame.this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//****************************************************************************//
	
	public static Border makeBorder()
	{
        Border outsideBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border insideBorder = new SoftBevelBorder(EtchedBorder.LOWERED, Color.WHITE, Color.GRAY);
		
        return BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
	}
	
	//****************************************************************************//

	public List<ArrayList<Skill>> getSkillsInGameForAll()
	{
		return skillsInGameForAll;
	}
	

	public List<ArrayList<Skill>> getSkillsInGameForLeader()
	{
		return skillsInGameForLeader;
	}
	
	
	public Map<String, String> getErrorDescriptions()
	{
		return errorDescriptions;
	}
	

	public Font getFONT()
	{
		return FONT;
	}

	
	public Color getFONT_COLOR()
	{
		return FONT_COLOR;
	}

	
	public Color getBACKGROUND_COLOR()
	{
		return BACKGROUND_COLOR;
	}
}
