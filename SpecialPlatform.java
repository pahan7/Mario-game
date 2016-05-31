package utc.edu.Grigorjevs3520;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class SpecialPlatform extends Platform{
	
	
	public boolean hit  = false;
	public String pngpath;
	private int x;
	private int y;
	private Bonus bonus;
	private boolean bonusActive;
	
	public SpecialPlatform(int x, int y) {
		super(x, y);
		this.x=x;
		this.y=y;
		bonusActive=false;
		//bonus = new Bonus(x,y+10,"res/bonus/" + bonuspath + ".png");
		
	}
	
	public void update()
	{
	
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void gotHit()
	{
		hit=true;
	}

	public void changePlatform()
	{
		setSprite(15,"res/specialBrick.png");
		hitbox.setLocation(x, y);		
		
	}

	public boolean hitStatus()
	{
		return hit;
	}
	
	public void bonusActivate()
	{
		bonusActive=true;
	}

	public boolean bonusActivated() {
		
		return bonusActive;
	}
}
