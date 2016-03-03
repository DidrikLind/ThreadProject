import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;	

import javax.swing.Timer; 
import java.lang.Thread; 
import java.awt.Color; 

import java.util.Random; 
import java.util.ArrayList; 

import javax.swing.SwingUtilities; 
import javax.swing.ImageIcon; 

/**
 * @author      Didrik Lind, didrik.lind@gmail.com
 * @version     alfa!
 * @since       2015-10-13       
 */



public class SpaceSwing4 extends JFrame
{
	
   /**
   * This is the main method which creates an object of SpaceSwing4.java.
   *An anonymous class-call to get our code to get executed by the "Event Dispatch Thread",
   *which is the thread for GUI-swing.(Swing is not threadsafe)
   * @param args Unused.
   * @see SwingUtilities
   */
	public static void main(String[] args)
	{ 
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new SpaceSwing4();
			}		
		});	
	}

	private static SpaceShips[] ships; 
	private static Planets[] planets;
	private static Random rand = new Random();
	private static ArrayList<SpaceShips> shipList = new ArrayList<SpaceShips>(); 

	private Timer timer3;
	private final int ONE_SECOND_DELAY=1000;
	private JLabel textLabel;

	/**
     * Initiates the GUI, i.e setting up all the Swing components on the JFrame
     * by calling an private method: "initGUI()". 
     */
	public SpaceSwing4()
	{
		initGUI();
	}

	private void initGUI()
	{
		getContentPane().setLayout(null);
		setSize(710,710);
		setTitle("Threads test !");

		planets = new Planets[3];
		makePlanets(planets);

		ships = new SpaceShips[4];
		makeShips(ships);

		add(makeTextLabel());

		timer3 = new Timer(ONE_SECOND_DELAY, new TimerTextClass());
		timer3.start();
		
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void makePlanets(Planets[] planets)
	{
		String str = "ABC";
		for(int i=0; i<planets.length; i=i+1)
		{
			planets[i] = new Planets(str.substring(i,i+1));
			getContentPane().add(planets[i]);
			if(i==0)
				planets[i].setLocation(110,355);
			if(i==1)
				planets[i].setLocation(310,355);
			if(i==2)
				planets[i].setLocation(510,355);
		}

	}

	private void makeShips(SpaceShips[] ships)
	{
		for(int i=0; i<ships.length; i=i+1)
		{
			ships[i] = new SpaceShips("ship" + (i+1));
			getContentPane().add(ships[i]);
			shipList.add(ships[i]);
			if(i==0)
			{
				ships[i].setLocation(110,120); ships[i].setStartX(110);
			}
			if(i==1)
			{
				ships[i].setLocation(210,120); ships[i].setStartX(210);
			}
			if(i==2)
			{
				ships[i].setLocation(310,120); ships[i].setStartX(310);
			}
			if(i==3)
			{
				ships[i].setLocation(410,120); ships[i].setStartX(410);
			}

			ships[i].getPlanetInfo(planets[0], planets[1], planets[2]); 
			ships[i].getThread().start();
		}

	}

	private JLabel makeTextLabel()
	{
		textLabel = new JLabel("HEJSAN");
		textLabel.setOpaque(true);
		textLabel.setBounds(40,40,700,30);
        textLabel.setFont((new Font(Font.SANS_SERIF, Font.BOLD, 15)));
        textLabel.setForeground(Color.MAGENTA); // text
        return textLabel;
	}

	private class TimerTextClass implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			textLabel.setText("**-->   Consumation of three materials ship1/ship2/ship3/ship4 = " +
			ships[0].getMatCount() + "/" + 
			ships[1].getMatCount() + "/" + 
			ships[2].getMatCount() + "/" + 
			ships[3].getMatCount() );
		}
	}	

}