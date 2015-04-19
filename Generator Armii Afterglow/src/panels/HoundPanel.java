package panels;

import javax.swing.SwingConstants;

import equipment.WeaponForCloseCombat;
import generator.GeneratorFrame;
import models.*;

public class HoundPanel extends BasicUnitPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WeaponForCloseCombat weapon; 
	private HoundModel hound;
	
	public HoundPanel(GeneratorFrame generatorFrame, String nick,
			ArmyModel armyModel, HoundModel hound)
	{
		super(generatorFrame, nick, armyModel, hound);
		this.hound = hound;
		addWeapon();
	}
	
	private void addWeapon()
	{
		weapon = hound.getWeapon();
		MyLabel weaponLabel = new MyLabel(weapon, SwingConstants.CENTER);
		equipmentPanel.add(weaponLabel);
	}
}
