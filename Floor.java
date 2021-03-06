package utc.edu.Grigorjevs3520;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Floor extends Entity{
	
	private static String pngpath = "res/floor_tile.png";
	private static int height = 17;
	private static int width = 17;

	public Floor (int x,int y)
	{
		super(x,y,width,height,pngpath);
	}
	
	
	void update()
	{
		
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
