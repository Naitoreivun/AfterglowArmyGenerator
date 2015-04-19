package panels;

import equipment.OrdinaryEquipment;
import generator.GeneratorFrame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import skills.*;

public class DefaultPanel extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_WIDTH = GeneratorFrame.PANEL_DEFAULT_WIDTH;
	private static final int DEFAULT_HEIGHT = GeneratorFrame.PANEL_DEFAULT_HEIGHT;
	
	protected JPanel mainPanel;
	protected JPanel centerPanel;
	protected JLabel nick;
	
	protected GeneratorFrame generatorFrame;
	
	public DefaultPanel(GeneratorFrame generatorFrame)
	{
		init(generatorFrame);
		
		mainPanel.add(makeNamePanel(), BorderLayout.NORTH);
		
		add(new JScrollPane(mainPanel), BorderLayout.CENTER);
		
	}
	
	private void init(GeneratorFrame generatorFrame)
	{
		this.generatorFrame = generatorFrame;
		
		setLayout(new BorderLayout());
		mainPanel = new JPanel(new BorderLayout());
		
		
		centerPanel = new JPanel();
        centerPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		setBorder(GeneratorFrame.makeBorder());
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}

	//****************************************************************************//
	
	private JPanel makeNamePanel()
	{
		nick = new JLabel();
		JPanel namePanel = new JPanel();
		namePanel.add(nick);
		
		customizeTheLookOfNamePanel(namePanel);
		return namePanel;
	}

	protected void setNick(String newName)
	{
		nick.setText(newName);
	}

	protected void customizeTheLookOfNamePanel(JPanel namePanel)
	{
		Font nameFont = generatorFrame.BIG_FONT;
		nick.setFont(nameFont);
		nick.setForeground(generatorFrame.FONT_COLOR);
		namePanel.setBackground(generatorFrame.BACKGROUND_COLOR);
	}

	//****************************************************************************//
	
	protected void addAll(JPanel panel,JComponent ... components)
	{
		JPanel flowPanel = new JPanel(new BorderLayout());
		boolean isFirst = true;
		for(JComponent component : components)
		{
			if (isFirst)
			{
				flowPanel = makeFlowPanel();
				flowPanel.add(component, BorderLayout.WEST);
				isFirst = false;
			}
			else
			{
				flowPanel.add(component, BorderLayout.EAST);
				panel.add(flowPanel);
				isFirst = true;
			}
		}
	}
	
	private JPanel makeFlowPanel()
	{
		JPanel flowPanel = new JPanel(new BorderLayout());
		flowPanel.setFont(generatorFrame.FONT);
		flowPanel.setForeground(generatorFrame.FONT_COLOR);
		flowPanel.setBackground(generatorFrame.BACKGROUND_COLOR);
		return flowPanel;
	}

	//****************************************************************************//
	
	protected CompoundBorder makeBorder(int horizontalMargin, int verticalMargin, String title)
	{
		Border outsideBorder = BorderFactory.createEmptyBorder(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);		
		
		TitledBorder insideBorder = BorderFactory.createTitledBorder(null, title, TitledBorder.CENTER,
				TitledBorder.ABOVE_TOP, generatorFrame.FONT, generatorFrame.FONT_COLOR);
		
		return BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
	}

	//****************************************************************************//
	
	protected class MyPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		MyPanel(LayoutManager layout, String title)
		{
			super(layout);
			
			setBackground(generatorFrame.BACKGROUND_COLOR);
			
			setBorder(makeBorder(1, 1, title));
		}
	}
	
	/**
	 * Special Label with generator Frame's default style
	 */
	protected class SLabel extends JLabel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public SLabel(String name)
		{
			super(name);
			customize();
		}
		
		public SLabel(String name, int align)
		{
			super(name, align);
			customize();
		}
		
		private void customize()
		{
			setFont(generatorFrame.FONT);
			setForeground(generatorFrame.FONT_COLOR);
			setBackground(generatorFrame.BACKGROUND_COLOR);
		}
	}
	
	protected class MyLabel extends SLabel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public MyLabel(String name, String description, int value, int align)
		{
			super(name, align);
			addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent arg0)
				{
					if(value == 0)
						generatorFrame.setInfo(name, description);
					else
						generatorFrame.setInfo(name, description, String.valueOf(value));
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
			customize();
		}
		public MyLabel(Skill skill, int align)
		{
			this(skill.getName(), skill.getDescription(), skill.getValue(), align);
		}
		public MyLabel(BasicSkill skill, int align)
		{
			this(skill.getName(), skill.getDescription(), 0, align);
		}
		public MyLabel(OrdinaryEquipment item, int align)
		{
			super(item.getName(), align);
			addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent arg0)
				{
					generatorFrame.setInfo(item);
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
			customize();
		}
		private void customize()
		{
			setToolTipText("Kliknij, aby zobaczyæ pe³ny opis.");
			setBorder(makeBorder(1, 2, ""));
		}
	}
	
	protected class MyCheckBox extends JCheckBox
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyCheckBox()
		{
			super();
			init();
		}
		public MyCheckBox(String name)
		{
			super(name);
			init();
		}
		
		private void init()
		{
			setFont(generatorFrame.FONT);
			setForeground(generatorFrame.FONT_COLOR);
		}
	}
}
