import javax.swing.JLabel;
import java.util.Random;
import java.awt.Color;
import java.lang.Math;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Graphics;


/**
 * @author      Didrik Lind, didrik.lind@gmail.com
 * @version     alfa!              
 * @since       2015-10-13          
 */

public class SpaceShips extends JLabel implements Runnable
{
	private final int ONE_SECOND_DELAY=1000;
	private static Random rand = new Random();
	private boolean matA, matB, matC;
	private Thread thr;
	private String shipName, materials = " ";
	private Planets pA, pB, pC;
	private int xStep, yStep, xTarget, yTarget, xStart, matCount;
	private Timer timer1;


	/**
    *Not in use.
    *@see #SpaceShips(String shipName)
    */
	public SpaceShips()
	{

	}

	/**
     * Constructor taking the name of the ship.
     * @param shipName The name of the object.
     */
	public SpaceShips(String shipName)
	{
		this.shipName = shipName;
		matA = false; matB = false; matC = false;
		setOpaque(true); 
		setBackground(Color.RED);
		setText(shipName);
		setSize(32,32); 
		thr = new Thread(this); //new Thread(Runnable target); 
	}

	/**
     *getThread() returns the thread of the object.
     * @return The thread of the object.
     */
	public Thread getThread()
	{
		return thr;
	}

	 /**
     *getShipName() returns the name of the object.
     * @return The Ship name of the object
     */
	public String getShipName()
	{
		return shipName;
	}

	/**
     * hasThisMaterial(String str) checks whether given material str
     * exists on the ship already.
     * @param str The name of the material.
     * @return The state of materials on the ship.
     */
	public boolean hasThisMaterial(String str)
	{
		if(str.equals("A"))
			return matA;
		if(str.equals("B"))
			return matB;
		if(str.equals("C"))
			return matC;
		return false;
	}

     /**
     * addThisMaterial(String str) adds given material str.
     * @param str The name of the material.
     */
	public void addThisMaterial(String str)
	{
		if(str.equals("A"))
		{
			matA = true;
			addMaterials(str);
		}
		if(str.equals("B"))
		{
			matB = true;
			addMaterials(str);
		}
		if(str.equals("C"))
		{
			matC = true;
			addMaterials(str);
		}
		//stopTimerMove();
	}

	 /**
     * addThisMaterial(String str) adds given material str.
     * @param pA Planet A in the simulation.
     * @param pB Planet B in the simulation.
     * @param pC Planet C in the simulation.
     */
	public void getPlanetInfo(Planets pA, Planets pB, Planets pC) 
	{
		this.pA = pA;
		this.pB = pB;
		this.pC = pC;
	}

	/**
     * moveToThisPlanet(String planetName) moves the ship to given planet, "planetName".
     * @param planetName Name of planet
     */
	public void moveToThisPlanet(String planetName)
	{
		if(planetName.equals(pA.getPlanName()))
		{
			xTarget = pA.getX();
			yTarget = pA.getY();
		}

		if(planetName.equals(pB.getPlanName()))
		{
			xTarget = pB.getX();
			yTarget = pB.getY();
		}

		if(planetName.equals(pC.getPlanName()))
		{
			xTarget = pC.getX();
			yTarget = pC.getY();
		}

		timerMove();
	}

	/**
     * moveBackFromPlanet() moves the ship back to homelocation.
     *@see #setStartX(int xStart)
     */
	public void moveBackFromPlanet()
	{
			sleepTheThread(5);
			this.xTarget = xStart+getHeight();
			this.yTarget = 120+getWidth();

			timerMove();
	}

	public void setXTarget(int xTarget)
	{
		this.xTarget = xTarget;
	}

	public  void setYTarget(int yTarget)
	{
		this.yTarget = yTarget;
	}

	public int getXTarget()
	{
		return xTarget;
	}

	public int getYTarget()
	{
		return yTarget;
	}

	public void setStartX(int xStart)
	{
		this.xStart = xStart;
	}

	public void addMaterials(String materials)
	{
		this.materials = this.materials + materials;
		sleepTheThread(rand.nextInt(2)+2);
		setText(this.materials);
		sleepTheThread(2);
	}

	public void consumeMaterials()
	{
		materials = " ";
		setText(materials);
		matA=false; matB=false; matC=false;
	}

// Describes the activity of the threads. The thread will execute itself and
// be active(exist) until the end of the execution of the run method.
// You can compare the run() method with the main() method, where our 
// main thread comes from.

	/**
     * Runs the thread for the object, since the class implements Runnable interface.
     */
	public void run() 
	{
		while(!Thread.interrupted())			
		{
			int randomPick = rand.nextInt(3);
		//Add materials*****************************		
			if(randomPick==0 && !matA)
			{
				sleepTheThread(rand.nextInt(1)+2);
				pA.getMat(this); 
			}

			if(randomPick==1 && !matB)
			{
				sleepTheThread(rand.nextInt(1)+2);
				pB.getMat(this);
			}

			if(randomPick==2 && !matC)
			{
				sleepTheThread(rand.nextInt(1)+2);
				pC.getMat(this);
			}

		//Delete/consume materials****************
			if(matA && matB && matC)
			{ 
				System.out.println("Current Thread will reset materials: " + Thread.currentThread());
				sleepTheThread(1);
				moveBackFromPlanet();
				consumeMaterials();
				matCount = matCount+1;
			}
	    }	
	}

	public void sleepTheThread(int sec)
	{
		try 
		{
			Thread.sleep(ONE_SECOND_DELAY*sec); 
		}
		catch(InterruptedException e)
		{
			//__
		}
	}

	public int getMatCount()
	{
		return matCount;
	}

//Anonymous class call of timer.
	public void timerMove()
	{
		timer1 = new Timer(ONE_SECOND_DELAY*(1/100), new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) 
			{
				double thetaAngleFromxy, moveXPixelsDouble, moveYPixelsDouble;
				int x, y, width, height, xStep, yStep, moveXPixelsInt, moveYPixelsInt;

				
					x = getX();  width  = getWidth();   xStep = 1;
					y = getY();  height = getHeight();	yStep = 1;

					xTarget = getXTarget();
					yTarget = getYTarget();
					
					/*Returns the angle theta from the conversion of rectangular coordinates
					  (x, y) to polar coordinates, from java doc */
					thetaAngleFromxy = Math.atan2(yTarget-height-y, xTarget-width-x);
			
					moveXPixelsDouble = Math.cos(thetaAngleFromxy)*xStep;
					moveYPixelsDouble = Math.sin(thetaAngleFromxy)*yStep;

					moveXPixelsInt = (int) Math.round(moveXPixelsDouble);
					moveYPixelsInt = (int) Math.round(moveYPixelsDouble);

					setLocation(x+moveXPixelsInt, y+moveYPixelsInt);				
			}
		});
		timer1.start();
	}

	public void stopTimerMove()
	{
		timer1.stop();
	}

}