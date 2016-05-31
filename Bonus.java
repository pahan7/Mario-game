package utc.edu.Grigorjevs3520;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

public class Bonus extends Entity{
	
	private static int w=15;
	private static int h=20;
	private String path;
	
	
	public Bonus(int x,int y,String path)
	{
		super(x, y-20, w, h, "res/bonus/"+path+".png");
		this.path=path;
		try 
        {
            AudioManager.getInstance().loadSample("Bonus", "res/Sounds/bonus.wav");
        
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
	}
	
	public void onCollision(Entity other)
	{
		if (other instanceof Mario)
		{
			this.deactivate();
			AudioManager.getInstance().play("Bonus");
		}
	}
	
	public String getName()
	{
		return path;
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
