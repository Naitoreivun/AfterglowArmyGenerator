package generator;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Generator Armii Afterglow
 * @version 1.0 - 13.12.2014
 * @author Naitoreivun
 */
public class Generator
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				GeneratorFrame frame = new GeneratorFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("Generator Armii Afterglow");
				frame.setVisible(true);
			}
		});
	}
}
