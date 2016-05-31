package utc.edu.Grigorjevs3520;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Projectile extends Entity {

    private static final int WIDTH=5;
    private static final int HEIGHT=2;
    private static final float SPEED=0.3f;

    private int direction;

    public Projectile(int x, int y, int direction) {
        super(x,y,WIDTH,HEIGHT);
        this.direction = direction;
    }

    public void update(float delta) {

        int x = (int)(hitbox.getX() + (SPEED * delta * direction));
        hitbox.setX(x);

        if (x < 0 || x > 1600)
        {
            this.deactivate();
        }

        
    }
    
    public void onCollision(Entity other)
    {
    	
    	if (other instanceof Goomba)
    	{
    		this.deactivate();
    	}
    	if (other instanceof Pipe)
    	{
    		this.deactivate();
    	}
    	if (other instanceof Wall)
    	{
    		
    		this.deactivate();
    	}
    }
    
    
    public void draw() {

        int x = hitbox.getX();
        int y = hitbox.getY();
        int w = hitbox.getWidth();
        int h = hitbox.getHeight();

        GL11.glColor3f(1,1,0);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glVertex2f(x,y);
        GL11.glVertex2f(x+w,y);
        GL11.glVertex2f(x+w,y+h);
        GL11.glVertex2f(x,y+h);

        GL11.glEnd();            

    }

    
}
