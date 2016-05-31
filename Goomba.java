package utc.edu.Grigorjevs3520;

import java.io.IOException;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class Goomba extends Entity{
	
	private String pngpath="res/goomba1.png";
	private Vector2f velocity;
	private int direction = -1;
	private int frameDelay;
	private int width = 16;
	private int kills;
	private float x,y;
	private float mass,speed;
	private Random rand;
	

	public Goomba()
	{
		super();
		setSprite(width,pngpath);
		rand = new Random();
		int randomPlace = rand.nextInt(4)+1;
		if (randomPlace==1) 
			hitbox.setLocation(85, 84);
		if (randomPlace==2)
			hitbox.setLocation(1025, 517);
		if (randomPlace==3)
			hitbox.setLocation(1325, 234);
		if (randomPlace==4)
			hitbox.setLocation(1005, 234);
		int randomDirection = rand.nextInt(2)+1;
		if (randomDirection==1) speed=-0.008f;
		if (randomDirection==2) speed=0.08f;
		
		velocity = new Vector2f(speed, 0);
		frameDelay=0;
		kills=0;
		mass=1f;
		
		try 
        {
			AudioManager.getInstance().loadSample("Headshot", "res/Sounds/headshot.wav");
			AudioManager.getInstance().loadSample("JumpOnGoomba", "res/Sounds/jumpOnGoomba.wav");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
	}
	
	public void update(float delta)
	{
		frameDelay+=1;
		
		x = hitbox.getX();
        y = hitbox.getY();
        
        if (frameDelay==10) setSprite(width,"res/goomba2.png");
		if (frameDelay==20)
		{
			setSprite(width,"res/goomba1.png");
			frameDelay=0;
		}
		Vector2f extraForce = new Vector2f(0,0);
		Vector2f.add(extraForce,(Vector2f) new Vector2f(0, .0005f).scale(delta/mass),extraForce);
	    
		Vector2f.add(velocity, extraForce, velocity);
		
		x += velocity.getX()*delta;
		y +=velocity.getY()*delta;
		
		hitbox.setLocation((int)x,(int)y);
	}
	
	public void flightUpdate(float delta)
	{
		frameDelay+=1;
		
		x = hitbox.getX();
        y = hitbox.getY();
        
        if (frameDelay==10) setSprite(width,"res/goomba2.png");
		if (frameDelay==20)
		{
			setSprite(width,"res/goomba1.png");
			frameDelay=0;
		}
		
		
		
		
		
	}
	
	
	
	
	public void onCollision(Entity other)
	{
		if (other instanceof Mario)
		{
			Rectangle overlap = intersection(other);
            double height = overlap.getHeight();
            double width = overlap.getWidth();           
            if (height > width)
            {
                velocity.setX(0);
                
            }
            else
            {
                velocity.setX(0);
                setSprite(this.width,"res/goombaDead.png");
                kills=1;
                deactivate();
                AudioManager.getInstance().play("JumpOnGoomba");
            }
         
		}
		if (other instanceof Wall )
		{
			
			Rectangle overlap = intersection(other);
			float y =hitbox.getY();
            float x =hitbox.getX();
            double width= overlap.getWidth();
            if (direction==1) x -= width;

            else  x += width;
            
            
            hitbox.setLocation((int) x,(int) y);
            direction=-direction;
            if (direction==1)
            	velocity.setX(0.08f);
            else velocity.setX(-0.008f);
            
            
		}
		if (other instanceof Floor)
		{
			Rectangle overlap = intersection(other);
            float y =hitbox.getY();
            float x =hitbox.getX();
            double height = overlap.getHeight();
            y -= height;
            velocity.setY(0);
            hitbox.setLocation((int) x,(int) y);
           
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
                if (direction==1)
                {
                	x-=width;
                }
                else x+=width;
                direction=-direction;
                if (direction==1)
                	velocity.setX(0.08f);
                else velocity.setX(-0.008f);
            }
            else
            {
            	y-=height;
                velocity.setY(0);
            }
            
            hitbox.setLocation((int) x,(int) y);
		}
		if (other instanceof Platform || other instanceof SpecialPlatform)
		{
			double x = hitbox.getX();
			double y = hitbox.getY();
			Rectangle overlap = intersection(other);
            double height = overlap.getHeight();
            double width = overlap.getWidth();           
            if (height > width)
            { 
                if (direction==1)
                {
                	x-=width;
                }
                else x+=width;
                direction=-direction;
                if (direction==1)
                	velocity.setX(0.08f);
                else velocity.setX(-0.008f);
            }
            else
            {
            	y-=height;
                velocity.setY(0);
            }
            
            hitbox.setLocation((int) x,(int) y);
		}
		if (other instanceof Projectile)
		{
			AudioManager.getInstance().play("Headshot");
			this.deactivate();
		}
	}
	
	public int getKills()
	{
		return kills;
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
