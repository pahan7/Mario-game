package utc.edu.Grigorjevs3520;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import org.lwjgl.input.Keyboard;

import org.lwjgl.LWJGLException;
import org.lwjgl.BufferUtils;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.LinkedList;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;


public class Main
{
 public static final int TARGET_FPS=100;
 public static final int SCR_WIDTH=800;
 public static final int SCR_HEIGHT=600;
 private static boolean isOver=false;

 public static void main(String[] args) throws LWJGLException, IOException
 {
	
     initGL(SCR_WIDTH, SCR_HEIGHT);
    
     
     Menu gameMenu = new Menu();
     GameTest newGame = new GameTest();
     gameMenu.addItem("Mario Survival",  newGame);
     gameMenu.addSpecial("Exit", Menu.DO_EXIT);
     Scene resumeGame = newGame;

     Scene currScene = gameMenu;
     boolean doResume = false;
    
     while ( currScene.go()  )
     {
          // if nextScene() returns null (the default) reload the menu
         currScene = currScene.nextScene();
        
         
        
         
         if (currScene == null)
         {
        	
        	 if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        	 {
	        	 newGame= new GameTest();
	        	 gameMenu.clear();
	        	 gameMenu.addItem("Resume", resumeGame); 
	        	 gameMenu.addItem("New game", newGame);   	 
	             gameMenu.addSpecial("Exit", Menu.DO_EXIT);
	             currScene = gameMenu;
	             //doResume=true;
        	 }
        	 else 
        	 {
        		 newGame = new GameTest();
        		 gameMenu.clear();
	        	 gameMenu.addItem("New game", newGame);   	 
	             gameMenu.addSpecial("Exit", Menu.DO_EXIT);
	             currScene = gameMenu;
        	 }
         }
         	 
	     if (currScene.equals(newGame)) 
	     {
	        	 	//System.out.println("privet");
	       	resumeGame=(GameTest) currScene;
	     }
	     System.out.println("Changing Scene: " + currScene);
         
     }


     Display.destroy();
     AudioManager.getInstance().destroy();
 }
    

 public static void initGL(int width, int height) throws LWJGLException
 {
     // open window of appropriate size
     Display.setDisplayMode(new DisplayMode(width, height));
     Display.create();
     Display.setVSyncEnabled(true);
     
     // enable 2D textures
     GL11.glEnable(GL11.GL_TEXTURE_2D);              
  
     // set "clear" color to black
     GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);         

     // enable alpha blending
     GL11.glEnable(GL11.GL_BLEND);
     GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      
     // set viewport to entire window
     GL11.glViewport(0,0,width,height);
      
     // set up orthographic projectionr
     GL11.glMatrixMode(GL11.GL_PROJECTION);
     GL11.glLoadIdentity();
     GL11.glOrtho(0, width, height, 0, 1, -1);
     // GLU.gluPerspective(90f, 1.333f, 2f, -2f);
     // GL11.glTranslated(0, 0, -500);
     GL11.glMatrixMode(GL11.GL_MODELVIEW);
 }
}