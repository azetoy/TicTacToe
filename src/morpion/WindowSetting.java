package morpion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/*________________________________________________________________________________________
________________________________________________________________________________________
________________________________________________________________________________________
____________________________________________██__________________________________________
__________________________________________██░░██________________________________________
__  __________  ________________________██░░░░░░██__________________________________
______________________________________██░░░░░░░░░░██____________________________________
______________________________________██░░░░░░░░░░██____________________________________
____________________________________██░░░░░░░░░░░░░░██__________________________________
__________________________________██░░░░░░██████    ██________________________________
__________________________________██░░░░░░██████      ██________________________________
________________________________██░░░░░░░░██████        ██______________________________
________________________________██░░░░░░░░██████        ██______________________________
______________________________██░░░░░░░░░░██████          ██____________________________
____________________________██░░░░░░░░░░░░██████          ██__________________________
____________________________██░░░░░░░░░░░░██████            ██__________________________
__________________________██░░░░░░░░░░░░░░██████              ██________________________
__________________________██                                  ██________________________
________________________██░░░░░░░░░░░░░░░░██████              ██______________________
________________________██░░░░░░░░░░░░░░░░██████                ██______________________
______________________██░░░░░░░░░░░░░░░░░░██████                ██____________________
________  ____________██                                          ██____________________
________________________██████████████████████████████████████████______________________
__________________________IN THIS CODE YOU NEED TO WAIT A LITTLE AFTER EACH CLICK______
____________________________IF YOU TRY TO SPEED OR CLICK A LITTER FASTER THIS MAY CAUSE
_____________________________YOUR TURN TO BE SKIPPED SO IF THAT HAPPEN JUST RESTART
________________________________________________________________________________________
__________________  ____________________________________________________________________
 																					
*/									
public class WindowSetting extends Thread
{
	static int taille = 3;
	private static int round = 1;
	private static int exit;
	static int x_c;
	static int y_c;
	static int change;
	static Thread myThread_pve = generate_pve(); 
	static Thread myThread_pvp = generate_pvp();
	 
	static char [][] array = new char [taille][taille];
	
	// Launch the pvp fonction
	public static void play() throws Exception  
    {
        // 1 to X 2 to O
        int first_player = 1;
        // create a game type of MemoryEdit
        MemoryEdit game = new MemoryEdit(array,3,first_player);
        // if alive is at 0 that mean the game is done (draw win or loose)
        MemoryEdit.setAlive(1);
        //while the game is not end
        while(MemoryEdit.get_game_alive() == 1)
        {
        	// if exit is at 1 that mean we will want to stop the thread by sending an interupt signal
        	if(exit == 1)
        	{
				myThread_pvp.interrupt();
				exit = 0;
        		
        	}
        	// check if the threas is interupted because even if the signa interupt is send to the thread we will need to wait to be sure the thread is interupted and then break the loop so that the methode play pve will return a statement and interupt the thread safely
        	if (Thread.interrupted())
        	{		
        			break; 
        	}
       
        	// the round 1 is for the X player
        	if(round == 1) 
        	{
	            // if the user click on the screen the change will be set at 1 and the the thread will wait for the user to click on the screen after that change will be set a 0
        		while(change != 1)
        		{
        			Thread.sleep(1000);
            	}
        		change = 0;
        		// edit the game array and update the screen
        		game.modif(array, x_c, y_c);
	            MyPanel.update(GUI.get_buff());
	            round = 2;
        	}
        	else if(round == 2)
        	{
        		while(change != 1)
        		{
        			Thread.sleep(1000);
            	}
        		change = 0;
        		game.modif(array, x_c, y_c);
	            MyPanel.update(GUI.get_buff());
	            round = 1;
        	}

        }
    } 
	     // method to play vs the ia
	public static void play_pve() throws Exception 
    {
		//same as the method pvp exept that after the player its the round of the ia 
        int first_player = 1;
        MemoryEdit game = new MemoryEdit(array,3,first_player);
        MemoryEdit.setAlive(1);
        round = 1;
        while(MemoryEdit.get_game_alive() == 1)
        {
        	if(exit == 1)
        	{
				myThread_pve.interrupt();
				exit = 0;
        		
        	}
        	
        	if (Thread.interrupted())
        	{		
        			System.out.printf("thread end \n");
        			break; 
        	}
        	
        	if(round == 1) 
        	{
	            
        		while(change != 1)
        		{
        			Thread.sleep(1000);
            	}
        		change = 0;
        		if((array[x_c][y_c] == 'X') || (array[x_c][y_c] == 'O'))
        		{
        			System.out.printf("nice try select another case you cant take the same case in pve at least \n");
        			round = 1;
        		}
        		else
        		{
        			game.modif(array, x_c, y_c);
    	            MyPanel.update(GUI.get_buff());
    	            round = 2;
        		}
        	}
        	else if(round == 2)
        	{
        		//get a random x and y for the ia
        		int x_r= random(),y_r= random();
        		if(array[x_r][y_r] == '\u0000')
        		{
        			game.modif(array, x_r, y_r);
            		round = 1;
            		MyPanel.update(GUI.get_buff());
        		}
        		else
        		{
        			round = 2;
        		}
        	}
        }
         
    } 
	
	// generate random number between 0 and 2 and return the number
	public static int random()
	{
		return (int)(Math.random()*3);
	}
	 
    public static void main(String[] args) throws Exception
    {
    	// run the GUI
    	SwingUtilities.invokeLater(new Runnable() 
    	{
            public void run() 
            {
                createAndShowGUI(); 
            }
        });
    }
    
    // this method create a thread for the pve method and return it with that we can just call this methode if we want to play another game 
    static Thread generate_pve()
    {
	    	Thread thread_pve = new Thread(new Runnable()
			{
	            public void run()
	            {
	                    try 
	                    {
							play_pve();
						} catch (Exception e) 
	                    {
							e.printStackTrace();
						}
	                    
	            }
			});
			return thread_pve;
    }
    // this method create a thread for the pvp method and return it with that we can just call this methode if we want to play another game 
    static Thread generate_pvp()
    {
	    	Thread thread_pvp = new Thread(new Runnable()
			{
	            public void run()
	            {
	                    try 
	                    {
							play();
						} catch (Exception e) 
	                    {
							e.printStackTrace();
						}
	                    
	            }
			});
			return thread_pvp;
    }
    // here we will create the button frame and panel
    private static void createAndShowGUI() 
    {
        SwingUtilities.isEventDispatchThread();
        JFrame f = new JFrame("Game TicTacToe(morpion)");
        JPanel myPanel = new MyPanel();
        	
		JButton pvp_btn = new JButton("PVP");
		JButton pve_btn	 = new JButton("PVE");
		JButton restart = new JButton("restart");
		
		// with this the btn will place automatically 
		pvp_btn.setLayout(null);
		pve_btn.setLayout(null);
		
		//add the btn to the panel
	    myPanel.add(pvp_btn);
	    myPanel.add(restart);
	    myPanel.add(pve_btn);
	    
	    
	    
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        //this method will get the click position
        f.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) 
        	{
                int x=e.getX();
                int y=e.getY();
                //this is to limit the bug that if the player clik with too much speed or if he clcik after the game is end the co will not be saved and generate a free token in the next game
                if(MemoryEdit.get_game_alive() == 1)
                {
		            //we will first check the y axe and after that the x axe
		            if(y >= 45 && y <= 195)
		            {
		            	y_c = 0;
		            	if(x >= 0 && x <= 170)
		            	{
		            		x_c = 0;
		            	}
		            	else if(x >= 185 && x <= 340)
		            	{
		            		x_c = 1;
		            	}
		            	else if(x > 340)
		            	{
		            		x_c = 2;
		            	}
		            }
		            else if(y >= 200 && y <= 360)
		            {
		            	y_c = 1;
		            	if(x >= 0 && x <= 170)
		            	{
		            		x_c = 0;
		            	}
		            	else if(x >= 185 && x <= 340)
		            	{
		            		x_c = 1;
		            	}
		            	else if(x > 340)
		            	{
		            		x_c = 2;
		            	}
		            }
		            else if(y > 370)
		            {
		            	y_c = 2;
		            	if(x >= 0 && x <= 170)
		            	{
		            		x_c = 0;
		            	}
		            	else if(x >= 185 && x <= 340)
		            	{
		            		x_c = 1;
		            	}
		            	else if(x > 340)
		            	{
		            		x_c = 2;
		            	}
		            }
		            
		            change = 1;
		         }
            }

            //cant delet the because java wont let me suse clicklistener wihtout the other mouselistener
			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) 
			{
			}

			@Override
			public void mouseEntered(MouseEvent e) 
			{
			}

			@Override
			public void mouseExited(MouseEvent e) 
			{
			}
        });
        
        //add the panel to the frame (window) and pack it set the visible to true or the window will never show
        f.getContentPane().add(new MyPanel());
        f.getContentPane().add(myPanel);
        f.pack();
        f.setVisible(true);
        // never met the user size the window
        f.setResizable(false);
        
        // the action performed if the user click on the pve button
        pve_btn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	// clean the array by creating a new char array 2d and affect it to the array declared the in the global
            	array = new char [taille][taille];
            	//set the exit at 0
            	exit = 0;
            	// interupt the pve and pvp thread if running 
            	myThread_pve.interrupt();
            	myThread_pvp.interrupt();
            	try {
            		//joint the thread to be sure that the thread ar dead
					myThread_pve.join();
					myThread_pvp.join();
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
            	// generate new pve thread
            	myThread_pve = generate_pve();
            	//hide the button 
            	pve_btn.setVisible(false);
            	pvp_btn.setVisible(false);
            	//update the screen
            	try {
					MyPanel.update(GUI.get_buff());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        		//start the game
            	myThread_pve.start();
        		System.out.println("thread is running pve\n");
        		
            }
        });
         
        //same as the pve action listener
        pvp_btn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	array = new char [taille][taille];
            	exit = 0;
            	myThread_pve.interrupt();
            	myThread_pvp.interrupt();
            	try {
					myThread_pve.join();
					myThread_pvp.join();
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
            	myThread_pvp = generate_pvp();
            	pve_btn.setVisible(false);
            	pvp_btn.setVisible(false);
            	try {
					MyPanel.update(GUI.get_buff());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
        		
            	myThread_pvp.start();
        		System.out.println("thread is running pvp\n");
            }
        }); 
        //action performed when the user click on restart
        restart.addActionListener(new ActionListener() 
        {

			public void actionPerformed(ActionEvent e) 
            {
				array = new char [taille][taille];
				//set exit at 1 so that the thread in pve and/or pvp method will start to stop
				exit = 1;
				//send a interupt singla to be sure that the thread are stoped 
            	myThread_pve.interrupt();
            	myThread_pvp.interrupt();
            	// last verification to stop the thread
            	exit_thread();
            	
            	//show the button that were previously hiden
            	pve_btn.setVisible(true);
            	pvp_btn.setVisible(true);
            	try 
            	{
            		//update the window
            		MyPanel.update(GUI.clean());
				} 
            	catch (IOException e1) 
            	{
					e1.printStackTrace();
				}
        		
            }
        });
    }
    
    //return the actual round
	public static int getRound() 
	{
		return round;
	}
	//set the actual round
	public static void setRound(int round) 
	{
		WindowSetting.round = round;
	}
	//make the exit at 1 to leave the thread
	static void exit_thread()
	{
		WindowSetting.exit = 1;
	}
	
}

  
class MyPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5018840662107954549L;
	static BufferedImage image;
    
	//update the buffer image or the board and the token to perfectly show and work
	public static void update(BufferedImage png)
	{
		image = png;
	}
	//setting border and repaint(refresh the window)
	public MyPanel()
	{
       setBorder(BorderFactory.createLineBorder(Color.black));
       repaint();
	}
    //set the dimmension of the window
    public Dimension getPreferredSize() 
    {
        return new Dimension(500, 500);
    }
    //this method will draw the buffer image(board and token) to the window
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	g.drawImage(image,0,0,this);
    	repaint();
    }
    
    
}
