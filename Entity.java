package utc.edu.Grigorjevs3520;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public abstract class Entity {
	
	 	protected Rectangle hitbox;
	    private boolean active;
	    protected Texture texture;
	    protected float width_ratio;
	    protected float height_ratio;
	    private String pngpath;

	    public Entity()
	    {
	    	active = true;
	    }
	    
	    public Entity(int x, int y, int w, int h) {
	        active = true;
	        hitbox = new Rectangle(x,y,w,h);
	    }

	    public Entity(int x, int y, int w, int h, String pngpath) {
	        // non-empty rectangle
	        active = true;
	        this.pngpath=pngpath;
	        try
			{
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(pngpath));
				
				width_ratio = (1.0f) * texture.getImageWidth()  / texture.getTextureWidth();
				height_ratio = (1.0f) * texture.getImageWidth()  / texture.getTextureWidth();
				
				hitbox = new Rectangle(x,y,w, (int)(h*(float)texture.getImageHeight()/ texture.getImageWidth()));
				
			}
			catch (java.io.IOException e)
			{
				e.printStackTrace();
				System.err.println("failed to load image " + pngpath);
				System.exit(-1);
			}
	    }
	    
	    public void init()
	    {
	    }

	    public void destroy()
	    {
	    }

	    public void update(float delta)
	    {
	    }

	    public void draw()
	    {
	    }
	    
	    public boolean intersects(Entity other)
	    {
	        return hitbox.intersects(other.hitbox);
	    }

	    public Rectangle intersection(Entity other)
	    {
	        Rectangle rval = new Rectangle();
	        return hitbox.intersection(other.hitbox, rval);
	    }

	    public boolean testCollision(Entity other)
	    {
	        if (hitbox.intersects(other.hitbox)) 
	        {
	            onCollision(other);
	            return true;
	        }
	        else 
	        {
	            return false;
	        }
	    }

	    public void onCollision(Entity other)
	    {
	    }

	    public boolean isActive()
	    {
	        return active;
	    }
	    
	    protected void activate()
	    {
	    	active = true;
	    }

	    protected void deactivate()
	    {
	        active = false;
	    }
	    
	    public void  setSprite(int width,String pngpath)
	    {
	    	try
			{
				texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(pngpath));
				
				width_ratio = (1.0f) * texture.getImageWidth()  / texture.getTextureWidth();
				height_ratio = (1.0f) * texture.getImageHeight()  / texture.getTextureHeight();
				
				hitbox = new Rectangle(0,0,width, (int)(width*(float)texture.getImageHeight()/ texture.getImageWidth()));
				
			}
			catch (java.io.IOException e)
			{
				e.printStackTrace();
				System.err.println("failed to load image " + pngpath);
				System.exit(-1);
			}
	    }

		public String getPngPath() {
			
			return pngpath;
		}
	
	   
}
