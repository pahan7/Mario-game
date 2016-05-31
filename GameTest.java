package utc.edu.Grigorjevs3520;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class GameTest extends Scene {

    private Mario mario;
    private ArrayList<Floor> floor;
    private ArrayList<Wall> wall;
    private ArrayList<Platform> platforms;
    private ArrayList<SpecialPlatform> specPlatform;
    private ArrayList<Pipe> pipes;
    private Background background;
    private ArrayList<Goomba> goombas;
    private int time;
    private LinkedList<Projectile> bullets;
    private ArrayList<Bonus> bonuses;
    private int score, before, after;
    private TrueTypeFont font;
    private ArrayList<String> bonusTypes;
    private int screenLocationX1, screenLocationX2;
    private boolean gameIsOver;

    public GameTest() throws IOException
    {
    	gameIsOver=false;
    	bullets = new LinkedList<>();
        mario = new Mario(15,"res/mario/standing_right.png",bullets);
        goombas = new ArrayList<Goomba>();
        goombas.add(new Goomba());
        score=0;
        
        
        floor = new ArrayList<Floor>();
        for (int i = 0; i < 95; i++)
        {
        	floor.add(new Floor(i*17,Display.getHeight()-17));
        }
        
        wall = new ArrayList<Wall>();
        for (int i = 1; i < Display.getHeight(); i+=17)
        {
        	wall.add(new Wall(0,i));
        	wall.add(new Wall(1600,i));
        }
        
        platforms = new ArrayList<Platform>();
        for (int i = 0; i < 10; i++)
        {
        	platforms.add(new Platform(75+i*15,Display.getHeight()-75));
        	platforms.add(new Platform(500+i*15,Display.getHeight()-75));
        	platforms.add(new Platform(560+i*15,Display.getHeight()-120));
        	platforms.add(new Platform(400+i*15,Display.getHeight()-160));
        	platforms.add(new Platform(560+i*15,Display.getHeight()-120));
        	platforms.add(new Platform(40+i*15,150));
        	
        }
        for (int i = 0; i < 40; i++)
        {
        	platforms.add(new Platform(950+i*15,300));
        }
        platforms.add(new Platform(385,300));
        platforms.add(new Platform(380,350));
        platforms.add(new Platform(385,300));
        platforms.add(new Platform(390,250));
        platforms.add(new Platform(395,200));
        
        pipes = new ArrayList<Pipe>();
       	pipes.add(new Pipe(1000,Display.getHeight()-67));
       	pipes.add(new Pipe(60,100));
       	platforms.add(new Platform(55,150));
       	pipes.add(new Pipe(1300,250));
       	pipes.add(new Pipe(980,250));
       
        
        bonuses = new ArrayList<Bonus>();
        bonusTypes = new ArrayList<String>();
        bonusTypes.add("firegun");
        bonusTypes.add("mushroom");
        bonusTypes.add("rocket");
        
        //bonus platforms
        specPlatform = new ArrayList<SpecialPlatform>();
        specPlatform.add(new SpecialPlatform(400, 350));
        specPlatform.add(new SpecialPlatform(100, 470));
        specPlatform.add(new SpecialPlatform(150, 470));
        specPlatform.add(new SpecialPlatform(200, 470));
        specPlatform.add(new SpecialPlatform(250, 100));
        specPlatform.add(new SpecialPlatform(250, 150));
        specPlatform.add(new SpecialPlatform(250, 200));
        specPlatform.add(new SpecialPlatform(250, 250));
        specPlatform.add(new SpecialPlatform(250, 300));
        specPlatform.add(new SpecialPlatform(400, 150));
        
        specPlatform.add(new SpecialPlatform(585, 350));
        specPlatform.add(new SpecialPlatform(590, 300));
        specPlatform.add(new SpecialPlatform(595, 250));
        specPlatform.add(new SpecialPlatform(600, 200));
        
     
        specPlatform.add(new SpecialPlatform(1080, 230));
        specPlatform.add(new SpecialPlatform(1120, 230));
        specPlatform.add(new SpecialPlatform(1160, 230));
        specPlatform.add(new SpecialPlatform(1200, 230));
        specPlatform.add(new SpecialPlatform(1240, 230));
      
        
        background = new Background();
        time=0;
        Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, true);
        
        try 
        {
            AudioManager.getInstance().loadSample("Gameover", "res/Sounds/Gameover2.wav");
          
            
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        
    }

    public Scene nextScene() { return null; }

    public boolean drawFrame(float delta) {


    	
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        
        
        
        GL11.glViewport(0,0,Display.getWidth(),Display.getHeight());
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        //GL11.glOrtho(mario.getX()-200, mario.getX()+200,mario.getY()+200, mario.getY()-200,1,-1);
        if (mario.getX()<=300)
        {
        	screenLocationX1=Display.getWidth()-200;
        	screenLocationX2=0;
        }
        else if (mario.getX()>=1300)
        {
        	screenLocationX1=1617;
        	screenLocationX2=1000;
        }
        else  
        {
        	screenLocationX1=mario.getX()+300;
        	screenLocationX2=mario.getX()-300;
        }
        
        GL11.glOrtho(screenLocationX2, screenLocationX1,Display.getHeight(),0,1,-1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        
        background.draw();
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
        	return false;
        }
	     
        
        font.drawString(screenLocationX2+450, 50, "Score: " + score, Color.yellow);
        font.drawString(screenLocationX2+450, 70, "Bullets: " + mario.getNumberOfBullets(), Color.yellow);
        font.drawString(screenLocationX2+450, 90, "Lives: " + mario.getNumberOfLives(), Color.yellow);
        font.drawString(screenLocationX2+450, 110, "Fly Time: " + mario.getFlyTime(), Color.yellow);

        Random rand = new Random();
        int  randomBonus = rand.nextInt(3); 
        
        
        time++;
        mario.draw();
        
        
        if (goombas.size()<score+10)
        {
        	time++;
        	if (time>100)
        	{
	        	goombas.add(new Goomba());
	        	time=0;
        	}
        }
        
        mario.update(delta);
        for (Goomba goomba: goombas)
		{
        	goomba.update(delta);
		}
       
        // testing collisions with objects
        
        for (int n = 0; n<floor.size();n++)
	    {        
        	mario.testCollision(floor.get(n));
        	for (Goomba goomba: goombas)
			{	
        		goomba.testCollision(floor.get(n));
			}
			floor.get(n).draw();
	    }
       
        
        for (int n = 0; n<wall.size();n++)
	    {        
           	for (Goomba goomba: goombas)
			{	
        		goomba.testCollision(wall.get(n));
			}
           	mario.testCollision(wall.get(n));
           	wall.get(n).draw();
	    }
           
		for (int n = 0; n<platforms.size();n++)
	    {        
			mario.testCollision(platforms.get(n));
			for (Goomba goomba: goombas)
			{	
        		goomba.testCollision(platforms.get(n));
			}
			platforms.get(n).draw();
	    }
		
		for (int n = 0; n<pipes.size();n++)
	    {        
			mario.testCollision(pipes.get(n));
			for (Goomba goomba: goombas)
			{
				goomba.testCollision(pipes.get(n));
			}	
			pipes.get(n).draw();
	    }
		
		for (int n = 0; n<specPlatform.size();n++)
	    {        
			mario.testCollision(specPlatform.get(n));
			for (Goomba goomba: goombas)
			{	
        		goomba.testCollision(specPlatform.get(n));
			}
			specPlatform.get(n).draw();
			if (specPlatform.get(n).hitStatus())
			{
				if (specPlatform.get(n).bonusActivated()==false)
				{	
					specPlatform.get(n).bonusActivate();
					bonuses.add(new Bonus (specPlatform.get(n).getX(), specPlatform.get(n).getY(),bonusTypes.get(randomBonus)));
					
				}
			}
						
	    }
		//checking for lost lives
		before =  mario.getNumberOfLives();
		
		for (Goomba goomba: goombas )
		{
			if (goomba.isActive())
			{
				mario.testCollision(goomba);
		        goomba.testCollision(mario);
		        score+=goomba.getKills();
		        goomba.draw();
			}
		}
		//new mario
		if (before > mario.getNumberOfLives()) 
		{
			for (Goomba goomba: goombas )
			{
				goomba.deactivate();
			}
			int livesLeft = mario.getNumberOfLives();
			int bulletsCount= mario.getNumberOfBullets();
			if (livesLeft==0) 
			{
				AudioManager.getInstance().play("Gameover");
				gameIsOver=true;
				System.out.println("Final score: "+score);
				return false;
			}
				
			mario.deactivate();
			mario = new Mario(15,"res/mario/standing_right.png",bullets);
			mario.setNumberOfLives(livesLeft);
			mario.setNumberOfBullets(bulletsCount);
		}
			 
		
		for (int n = 0; n<bonuses.size();n++)
	    {
			bonuses.get(n).testCollision(mario);
			mario.testCollision(bonuses.get(n));
			if (bonuses.get(n).isActive())
				bonuses.get(n).draw();
	    }

		for (int n = 0; n<bonuses.size();n++)
	    {
			if (!bonuses.get(n).isActive())
        		bonuses.remove(n);
	    }
	    
		
		
               
        for(int i=0;i<goombas.size();i++)
        {
        	if (!goombas.get(i).isActive())
        		goombas.remove(i);
        }
        
        
        Projectile bullet;

        
        Iterator<Projectile> it= bullets.iterator();
        while (it.hasNext())
        {
            bullet = it.next();
            
            bullet.update(delta);

            if (! bullet.isActive())
            {
                //System.out.println("removing inactive projectile");
                it.remove();
            }
            else 
            {
                bullet.draw();
                for(int i=0;i<goombas.size();i++)
                {
                	goombas.get(i).testCollision(bullet);
                	if (bullet.testCollision(goombas.get(i))) score+=1;
                }
                for (int i=0;i<pipes.size();i++)
                {
                	bullet.testCollision(pipes.get(i));
                }
                
            }
        }

                
        return true;
    }

    public boolean gameOver()
    {
    	return gameIsOver;
    }
}
