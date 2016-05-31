package utc.edu.Grigorjevs3520;
import java.io.IOException;
import java.util.LinkedList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class Mario extends Entity{
	
	private Vector2f velocity;
    private float mass;
    private boolean onGround=true;
    private float maxSpeed=0.2f;
    private int direction = 1;
    private boolean alive=true;
    private String runningSprites[] = new String[6];
    private String flyingSprites[] = new String[4];
    private int frameDelay=0;
    private int spriteNumber=0;
    private int width;
    private boolean fireAbility, flyAbility;
    private LinkedList<Projectile> bullets;
    private int bulletWait=0;
    private int numberOfBullets;
    private int numberOfLives;
    private int flyTime;
   
	public Mario(int width, String pngpath,LinkedList<Projectile> bullets)
	{
		super();
		setSprite(width,pngpath);
		this.width=width;
	    velocity = new Vector2f(0,0);
        mass = 1;
        hitbox.setX(Display.getWidth()/2);
        hitbox.setY(Display.getHeight()-200);
        for (int i=0; i<6; i++)
        {
        	runningSprites[i]="res/mario/running_"+i+".png";
        }
        for (int i=0; i<4; i++)
        {
        	flyingSprites[i]="res/mario/flying/flying_"+i+".png";
        }
        fireAbility=false;
        this.bullets = bullets;
        bulletWait=0;
        numberOfBullets=0;
        numberOfLives=3;
        flyAbility=false;
        flyTime=0;
        //music
        try 
        {
            AudioManager.getInstance().loadSample("Gunshot", "res/Sounds/gunshot.wav");
            AudioManager.getInstance().loadSample("Jump", "res/Sounds/jump.wav");
            AudioManager.getInstance().loadSample("Lifelost", "res/Sounds/lifelost.wav");
            
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
	}
	
	public void update(float delta)
	{
		
		float x = hitbox.getX();
        float y = hitbox.getY();
        
        frameDelay+=1;
        

        //screen height restriction
	     if (y < 0)
	     {
	         y = 0;
	         velocity.setY(0);
	     }
     
	     Vector2f extraForce = new Vector2f(0,0);

	     float decel=0;
	      // apply gravity
	     if (!flyAbility)
	     {
		     Vector2f.add(extraForce,(Vector2f) new Vector2f(0, .0015f).scale(delta/mass),extraForce);
		     decel = .009f;
	     }
		 if (flyAbility)
		 {
			 decel = 0;
		 }
	     
	     //moves left and right, jumps
	     if (onGround && !flyAbility)
	     {
		     if (Keyboard.isKeyDown(Keyboard.KEY_UP))
		     {
		    	 onGround=false;
		    	 Vector2f.add(extraForce, new Vector2f(0, -0.5f), extraForce); 	
		    	 AudioManager.getInstance().play("Jump");
		     }
	     }
	     if (flyAbility)
	     {
	    	 if (Keyboard.isKeyDown(Keyboard.KEY_UP) && velocity.getY()> -maxSpeed)
		    	 Vector2f.add(extraForce, new Vector2f(0, -0.02f), extraForce); 
		    	 
	    	 if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && velocity.getY()< maxSpeed)
		    	 Vector2f.add(extraForce, new Vector2f(0, 0.02f), extraForce); 
	     }

	     // add some horizontal forces in response to key presses
	     if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && velocity.getX()> (-maxSpeed))
	     {	 
	    	 Vector2f.add(extraForce, new Vector2f(-.02f, 0), extraForce);
	    	 direction=-1;
	     }
	      
	     if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && velocity.getX()< maxSpeed)
	     {
	    	 Vector2f.add(extraForce, new Vector2f(.02f, 0), extraForce);
	    	 direction=1;
	     }
	    
	     
	     //apply friction
   
	    if (velocity.getX()>0)
		{
		
	    	if (decel > velocity.getX())
	    		velocity.setX(0);
			else	 
				Vector2f.add(extraForce, new Vector2f(-decel, 0), extraForce);
		}
		    	 
		else if (velocity.getX()<0)
		{
			if (-decel < velocity.getX())
				velocity.setX(0);
			else	 
				Vector2f.add(extraForce, new Vector2f(decel, 0), extraForce);
		}
	    //vertical deceleration
	    if (flyAbility)
	    {
		    if (velocity.getY()>0)
		    {
		    	if (decel > velocity.getY())
		    		velocity.setY(0);
				else	 
					Vector2f.add(extraForce, new Vector2f(0, -decel), extraForce);
		    }
		    else if (velocity.getY()<0)
			{
				if (-decel < velocity.getY())
					velocity.setY(0);
				else	 
					Vector2f.add(extraForce, new Vector2f(0, decel), extraForce);
			} 
	    }
	     
	     //switching sprites
	     if (!flyAbility) 
	     {
		     if (frameDelay==5)
				{
					spriteNumber+=1;
					frameDelay=0;
					if (spriteNumber==3)
						spriteNumber=0;
				}
	     }
	     else 
	     {
	    	 if (frameDelay==5)
				{
					spriteNumber+=1;
					frameDelay=0;
					if (spriteNumber==2)
						spriteNumber=0;
				}
	     }
	     if (onGround && !flyAbility)
	     {
	    	 if (velocity.getX()!=0)
	    	 {
	    		 if (direction==1) setSprite(width,runningSprites[spriteNumber]);
	    		 if (direction==-1) setSprite(width,runningSprites[spriteNumber+3]);
	    		 
	    	 }
	    	 else
	    	 {
	    		 if (direction==1) setSprite(width,"res/mario/standing_right.png");
	    		 if (direction==-1) setSprite(width,"res/mario/standing_left.png");
	    	 }
		     
	     }
	     else if (!onGround && !flyAbility)
	     {
	    	 if (direction==1) setSprite(width,"res/mario/jump_right.png");
     		 if (direction==-1) setSprite(width, "res/mario/jump_left.png");
	     }
	     
	     else if (flyAbility)
	     {
	    	 if (direction==1) setSprite(width,flyingSprites[spriteNumber]);
    		 if (direction==-1) setSprite(width,flyingSprites[spriteNumber+2]);
	     }
	    	 
	    	 
	    	 
	     Vector2f.add(velocity, extraForce, velocity);



	     x += velocity.getX()*delta;
	     y += velocity.getY()*delta;


	        

	     
	     
	        
	     if (fireAbility==true)
	     {
	    	 if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		     {
	    		 if (bulletWait==0)
	    		 {
		    		 bullets.add(new Projectile((int)x,(int)y+3,direction));
		    		 bulletWait=10;
		    		 numberOfBullets--;
		    		 AudioManager.getInstance().play("Gunshot");
		    		 if (direction==-1) velocity.setX(0.1f);;
		    		 if (direction==1) velocity.setX(-0.05f);
		    			 
	    		 }
		     }
	     }
	     if (bulletWait>0) bulletWait--;
	     
	     if (numberOfBullets==0) fireAbility=false;
	     if (numberOfBullets>0) fireAbility=true;
	     if (flyTime==0) flyAbility=false;
	     if (flyTime>0) flyTime--;
	     
	     hitbox.setLocation((int)x,(int)y);   
	     
	}
	
	public void onCollision(Entity other)
	{
		if (other instanceof Floor)
		{
			Rectangle overlap = intersection(other);
            float y =hitbox.getY();
            float x =hitbox.getX();
            double height = overlap.getHeight();
            y -= height;
            velocity.setY(0);
            hitbox.setLocation((int) x,(int) y);
            onGround=true;
        } 
		if (other instanceof Wall)
		{
			Rectangle overlap = intersection(other);
            float x =hitbox.getX();
            float y = hitbox.getY();
            double width = overlap.getWidth();
            if (velocity.getX()<0)
            	x += width;
            if (velocity.getX()>0)
            	x -=width;
            velocity.setX(0);
            hitbox.setLocation((int) x,(int) y);
		}
		
		if (other instanceof Platform)
		{
			double x = hitbox.getX();
			double y = hitbox.getY();
			Rectangle overlap = intersection(other);
            double height = overlap.getHeight();
            double width = overlap.getWidth();
            if  (velocity.getY()>0)
			{
				y -= height;
	            velocity.setY(0);
	            onGround=true;
	            hitbox.setLocation((int) x,(int) y);
			}
			if (velocity.getY()<0) 
			{
				y+=height;
				hitbox.setLocation((int) x,(int) y);
				velocity.setY(-velocity.getY());
				if (other instanceof SpecialPlatform)
				{
					if (!((SpecialPlatform) other).hit)
					{
						((SpecialPlatform) other).changePlatform();
						((SpecialPlatform) other).gotHit();
					}
				}
			}
			if (velocity.getX()>0 && height > width)
			{
				x -=width;
				hitbox.setLocation((int) x,(int) y);
			}
            if (velocity.getX()<0 && height > width)
            {
            	x +=width;
            	hitbox.setLocation((int) x,(int) y);
            }
         }
		if (other instanceof Pipe)
		{
			double x = hitbox.getX();
			double y = hitbox.getY();
			Rectangle overlap = intersection(other);
            double height = overlap.getHeight();
            double width = overlap.getWidth();           
            if (height > width)
            {
                velocity.setX(0);
                if (direction==1) x-=width;
                else x+=width;
            }
            else
            {
            	y-=height;
                velocity.setY(0);
                onGround=true;
            }
            hitbox.setLocation((int) x,(int) y);
		}
		if (other instanceof Goomba)
		{
			Rectangle overlap = intersection(other);
            double height = overlap.getHeight();
            double width = overlap.getWidth();           
            if (height > width)
            {
                velocity.setX(0);
                numberOfLives-=1;
                if (numberOfLives>0)
                	AudioManager.getInstance().play("Lifelost");
            }
            else
            {
                
            }
         
		}
		
		if (other instanceof Bonus)
		{

			if (((Bonus) other).getName().equals("firegun"))
			{
				fireAbility=true;
				numberOfBullets += 100;
				
			}
			if (((Bonus) other).getName().equals("mushroom"))
			{
				numberOfLives+=1;
			}
			
			if (((Bonus) other).getName().equals("rocket"))
			{
				flyAbility=true;
				flyTime=1000;
				spriteNumber=0;
			}
		}
		
		
	}
	
	public void gameover()
	{
		
	}
	
	public int getX()
	{
		return hitbox.getX();
	}
	
	public int getY()
	{
		return hitbox.getY();
	}
	public int getNumberOfBullets()
	{
		return numberOfBullets;
	}
	public int getNumberOfLives()
	{
		return numberOfLives;
	}
	public int getFlyTime()
	{
		return flyTime; 
	}
	public void setNumberOfLives(int lives)
	{
		numberOfLives=lives;
	}
	public void setNumberOfBullets(int bullets)
	{
		numberOfBullets = bullets;
	}
	
	public void draw()
	{
		int x = hitbox.getX();
        int y = hitbox.getY();
        int w = hitbox.getWidth();
        int h = hitbox.getHeight();
        
        GL11.glColor3f(1,1,1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		
		GL11.glBegin(GL11.GL_QUADS);
		
        
		// top-left of texture tied to top-left of box        
        GL11.glTexCoord2f(0,0);
        GL11.glVertex2f(x,y);
        
        // top-right 
        GL11.glTexCoord2f(width_ratio,0);
        GL11.glVertex2f(x+w, y);

        // bottom-right
        GL11.glTexCoord2f(width_ratio, height_ratio); 
        GL11.glVertex2f(x+w,y+h);

        // bottom-left
        GL11.glTexCoord2f(0,height_ratio);
        GL11.glVertex2f(x,y+h);
        
        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
