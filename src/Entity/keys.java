package Entity;
import Titlemap.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class keys extends mapobj{
    protected boolean flinching;
    protected long flinchTimer;
    protected int health;
    protected int maxHealth;
    protected boolean dead;
    protected int key1=0;
    private BufferedImage[] k;    
    public keys(TileMap tm) {
        super(tm);
        moveSpeed = 0.5;
        maxSpeed = 0.5;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
        width =60;                        
        height =60;                          
        cwidth =45;                    
        cheight =45;
         try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/image/coint.png"));
            k = new BufferedImage[6];
            for(int i =0; i<k.length;i++){
                k[i]= spritesheet.getSubimage(i*width,0,width,height);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        animation = new Animetion();
        animation.setFrames(k);
        animation.setdelay(240);
        
    }
    public boolean isDead() { return dead; }
    
     private void getNextPosition(){
        // falling
        if(falling) {
            dy += fallSpeed;
           
        }
    }
     public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
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

