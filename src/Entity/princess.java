package Entity;

import Entity.*;
import Titlemap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;



public class princess extends mapobj{
    protected boolean flinching;
    protected long flinchTimer;
    protected int health;
    protected int maxHealth;
    protected boolean dead;
   
    private BufferedImage[] princess;  
    public princess(TileMap tm) {
        super(tm);
        width =100;                       
        height =100;                          
        cwidth =75;                  
        cheight =75; 
         try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/image/princess.png"));
            princess = new BufferedImage[6];
            for(int i =0; i<princess.length;i++){
                princess[i]= spritesheet.getSubimage(i*width,0,width,height);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
         animation = new Animetion();
         animation.setFrames(princess);
         animation.setdelay(200);
    }
     public boolean isDead() { return dead; }
     public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
                
              
	}
   
    private void getNextPosition(){
        // falling
        if(falling) {
            dy += fallSpeed;
           
        }
    }
    
    public void update(){
        //update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(xtemp,ytemp);
        //update animation
        animation.update();
        
    }
    @Override
    public void draw(Graphics2D g){
       // if(notOnScreen()) return;
        setMapPosition();
        super.draw(g);
    }
      
    
    
}
