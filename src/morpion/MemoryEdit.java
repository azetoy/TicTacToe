package morpion;


public class MemoryEdit 
{
	private static int taille;
	private int player_round;
	private static int alive;
	private int x1;
	private int y1;
	GUI gui = new GUI();
	  
	/**
	 * @param alive the alive to set
	 */
	//setter method
	public static void setAlive(int alive) 
	{
		MemoryEdit.alive = alive;
	}

	// this method will perform the array edit to make the token but only if the array at x and y pos are free
	public void modif(char [][] array,int x,int y) throws Exception
	{ 
		x1 = x*50 + 50;
		y1 = y*50 + 50;
		if(x >= 1)
		{
			x1 += (100 * x);
		}
		
		if(y >= 1)
		{
			y1 += (100 * y);
		}
		
		if(array[x][y] == '\u0000')
		{
			if(player_round == 1)
			{
				array[x][y] = 'X';
				gui.temp(player_round, x1, y1);
				player_round = 2;
			}
			
			else if(player_round == 2)
			{
				array[x][y] = 'O';
				gui.temp(player_round, x1, y1);
				player_round  = 1;
			}
		} 
		
		else if(WindowSetting.getRound() == 2)
		{
			//modif(array,WindowSetting.random(),WindowSetting.random());
			WindowSetting.setRound(WindowSetting.getRound() - 1);
		}
		else
		{ 
			System.out.printf("cant take the posiotion of another player you skip your round  \n");
		}
		test(array);
	}  
	 
	// this method will test the array to know if the user win 
	static void test(char [][]array)
	{
		//acc for the X acc2 for the O
		int i,j,b,d,acc = 0,acc2 = 0;
		//the first and second loop to check all line and columns
		for(i = 0;i<taille;i++)
		{	 
			//this loop is to check each char of the line
			for(j = 0;j<taille;j++)
			{
				if(array[i][j] == 'X')
					acc ++;
				
				if(array[i][j] == 'O')
					acc2 ++;
				
				if((acc == taille) || (acc2 == taille))
				{
						System.out.printf("GG you win you can take a Maximator \n");
						alive = 0;
				}
			}
			acc = 0;acc2 = 0;
	 	 	
			//this loop is to check each char for 
			for(b = 0;b<taille;b++)
			{
				if(array[b][i] == 'X')
					acc ++;
				
				if(array[b][i] == 'O')
					acc2 ++;
				 
				if((acc == taille) || (acc2 == taille))
				{
					System.out.printf("GG1 you win you can take a Maximator \n");
					alive = 0;
				}
			} 
			
			acc = 0;acc2 = 0;
			//this is to check the diagonal 
			for(d = 0;d<taille;d++)
			{
				if(array[d][d] == 'X')
				{
					acc ++;
				}
				else if(array[d][d] == 'O') 
				{
					acc2 ++;
				}
				
			}
			//thes if are to check the revers diagonal
			if((array[0][2] == 'X') && (array[1][1] == 'X') && (array[2][0] == 'X'))
			{
				acc = taille;
			}
			
			if((array[0][2] == 'O') && (array[1][1] == 'O') && (array[2][0] == 'O'))
			{
				acc = taille;
			}
			
			if((acc == taille) || (acc2 == taille))
			{
				System.out.printf("GG2 you win you can take a Maximator \n");
				alive = 0;
			}
			//reset the counter after each check 
			acc = 0;acc2 = 0;
		}
	}
	
	// getter that return the status of the game 
	public static int get_game_alive()
	{
		return alive;
	}
	
	//constructor of the class memory edit
	
	public MemoryEdit(char [][]array,int taille,int round) throws Exception
	{
		MemoryEdit.taille = taille;
		this.player_round = round;
		alive = 1;
		
	}

}
