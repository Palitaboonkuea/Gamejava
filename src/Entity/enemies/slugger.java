package Entity.enemies;

import Entity.*;
import Titlemap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class slugger extends enemy {
    private BufferedImage[] sprites;
    public slugger(TileMap tm) {
        super(tm);
        
        moveSpeed = 0.5;
        maxSpeed = 0.5;
        fallSpeed = 0.2;
        maxFallSpeed = 10.0;
        
        width =100;                           //
        height =100;                      // ขนาดตัวละคร
        cwidth =65;                     ///cwidth cheight กำหนดตำแหน่งตัวละครบนtile map
        cheight = 65; 
        damage=1;
        //load sprites
        try{
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/image/enemy1.png"));
            sprites = new BufferedImage[6];
            for(int i =0; i<sprites.length;i++){
                sprites[i]= spritesheet.getSubimage(i*width,0,width,height);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        animation = new Animetion();
        animation.setFrames(sprites);
        animation.setdelay(140);
        
        right = true;
        facingRight = true;
    }
     private void getNextPosition(){
       // movement
	if(left) {
            dx -= moveSpeed;
            if(dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        }
            else if(right) {
                dx += moveSpeed;
                if(dx > maxSpeed) {
                dx = maxSpeed;
            }
        }
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
        
        //check flinching
        if(flinching){
            long elapsed=(System.nanoTime()-flinchTimer)/1000000;
            if(elapsed>400){
                flinching = false;
            }
        }
       //if it hits a wall,go other director 
        if(right && dx ==0){
            right = false;
            left = true;
            facingRight = false;
        }
        else if(left &&dx==0){
            right = true;
            left = false;
            facingRight = true;
        }
        //update animation
        animation.update();
        
    }
    public void draw(Graphics2D g){
       // if(notOnScreen()) return;
        setMapPosition();
        super.draw(g);
    }
    
    
   
}

