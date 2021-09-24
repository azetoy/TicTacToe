package morpion;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class GUI { 
	// tictactoe_sav.png will always be clean he is to make sure that we can set a clean board if we want or need it and combined is always the actual board
	private static int test;
	// path to the folder that contain the png
	private static String path = "src//morpion//";
	private static BufferedImage combined = null;
	static BufferedImage board = null;
	BufferedImage x = null;
	BufferedImage o = null;
	BufferedImage cSymbol = null;
 
	// this method will make a copy of the save to create the board so after each game the board.png is deleted and a new is created
	private static void copyFile() throws IOException 
	{
		InputStream source = GUI.class.getResourceAsStream("tictactoe_sav.png");
		//InputStream dest = GUI.class.getResourceAsStream("tictactoe.png");
		String path = "src//morpion//tictactoe.png";
		Files.copy(source, Paths.get(path));
	}
	  
	//yeah bad name for this but its ok this method will make the fusion of the actual board and the new token into a new board and delet the previous
    public void temp(int symbol,int coo_x,int coo_y) throws Exception
    {
    	//this will delet the board if he is not clean after the game start
    	File b = new File(path,"tictactoe.png");
    			
    			if(test != 1)
    			{
    				test ++;
    				try
    				{
    					copyFile();
    				}
    				catch(Exception e)
    				{
    					b.delete();
    					copyFile();
    				}
    			}
    			// load source images
    			board = ImageIO.read(b);
    			x = ImageIO.read(GUI.class.getResourceAsStream("X.png"));
    			o = ImageIO.read(GUI.class.getResourceAsStream("O.png"));
    			
    			// chek to know what token we will use
    			if(symbol == 1)
    			{
    				cSymbol = x;
    			}
    			else
    			{
    				cSymbol = o;
    			} 
    			 
    			// create the new image, canvas size is the max. of both image sizes
    			int w = Math.max(board.getWidth(), cSymbol.getWidth());
    			int h = Math.max(board.getHeight(), cSymbol.getHeight());
    			combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

    			// paint both images, preserving the alpha channels 
    			Graphics g = combined.getGraphics();
    			g.drawImage(board, 0, 0, null);
    			g.drawImage(cSymbol, coo_x, coo_y, null);

    			g.dispose();

    			// Save as new image
    			ImageIO.write(combined, "PNG", new File(path,"tictactoe.png"));
    }
    
    //this will return the buffer of the image if the buffer of combined is null then return a clean one else return the combined buffer image (so the actual board)
    public static BufferedImage get_buff() throws IOException
    {
    	if(combined == null)
    	{
    		BufferedImage tmp = ImageIO.read(GUI.class.getResourceAsStream("tictactoe_sav.png"));
    		return tmp;
    	}
    	return combined;
    }
    
    //this will clean the board by setting the buffer combined with the clean board and write it as the actual board and return it
    public static BufferedImage clean() throws IOException
    {
    		combined = ImageIO.read(GUI.class.getResourceAsStream("tictactoe_sav.png"));
    		ImageIO.write(combined, "PNG", new File(path,"tictactoe.png"));
    		return combined;
    }
    public GUI() throws Exception
    {
    	 
    }
}