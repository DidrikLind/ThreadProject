import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * @author      Didrik Lind, didrik.lind@gmail.com
 * @version     alfa!                 
 * @since       2015-10-13          
 */

public class Planets extends JLabel
{
	private String matName, planName;
	private final int ONE_SECOND_DELAY=1000;

	public Planets()
	{

	}

//No need for synchonized, since its here we create the object.
	/**
     * 
     *@param planName name of planet.
     */
	public Planets(String planName)
	{
		this.planName = planName;
		matName = planName; // material.
		setIcon(new ImageIcon("planet" + planName + ".png"));
		setText(planName);
		setSize(132,132);
	}

//One thread (SpaceShip) at the time can get materials!
   /**
   *This method is synchronized, therefore only one thread can reach it at the time.
   * @param ship The ship parameter is an instance of the class SpaceShips.java.
   */
	public synchronized void getMat(SpaceShips ship)
	{
			System.out.println("Now ship: " + ship.getShipName() + " takes materials");

			ship.moveToThisPlanet(matName); 
			ship.addThisMaterial(matName);

			ship.moveBackFromPlanet();
	}


   /**
   *getPlanname() returns the name of the Planet object.
   *@return String Name of planet
   */

	public String getPlanName()
	{
		return planName;
	}
}