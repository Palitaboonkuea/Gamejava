package Entity;
import Titlemap.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
public class PLayer extends mapobj {
    private int key1;
    private int num;
    private int maxkey;
    // player stuff
    private int health;
    private int maxHealth;
    private boolean dead;
    private boolean flinching;
    private long flinchTimer;
    // scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;
    // gliding
    private boolean gliding;
   
	
     // animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {1,4,1,1,2};   
	// animation actions
    private static final int IDLE = 0;
    private static final int WALKING = 1;
    private static final int JUMPING = 2;
    private static final int FALLING = 3;
    private static final int GLIDING = 4;
    private static final int FIREBALL = 5;
    private static final int SCRATCHING = 6;
    private Font font;
        

    public PLayer(TileMap tm) {
        super(tm);
       
        width = 100;
        height = 100;
        cwidth = 60;  
        cheight= 60;   
        moveSpeed = 0.3;
        maxSpeed = 1.6;
        stopSpeed = 0.4;
        fallSpeed = 0.15;
        maxFallSpeed = 4.0;
        jumpStart = -8.5;    ///ยิ่งติดลบเยอะยิ่งโดดสูง
        stopJumpSpeed = 0.3;	
        facingRight = true;
	key1 = 0;
        num=0;
        maxkey =1;
        health = maxHealth = 2;   
        scratchRange = 40;
       
		
        // load sprites
        try {
            font =  new Font("ARial",Font.PLAIN,120);
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/image/princesstile22.png"));
            
            sprites = new ArrayList<BufferedImage[]>();
            for(int i = 0; i < 5; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];
                for(int j = 0; j < numFrames[i]; j++) {
                    if(i != 6) {
                        bi[j] = spritesheet.getSubimage(j * width,i * height,width,height);
                    }
                    else {
                        bi[j] = spritesheet.getSubimage(j * width * 2,i * height,width,height);
                    }
                }
                sprites.add(bi);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        animation = new Animetion();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setdelay(400);
      
    }
        public int getnum(){
            return num;
        }
        public void setnum(int n){
            this.num =n;
        }
        public int getkey(){return key1;}
        public int getMaxkey() { return maxkey; }
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public void setScratching() {
	    scratching = true;
	}
	public void setGliding(boolean b) { 
	    gliding = b;
	}
        public void checkAttack(ArrayList<enemy> en){
            for(int i =0;i< en.size(); i++){
                enemy e = en.get(i);
                if(intersects(e)){
                    hit(e.getDamage());
                }
            }   
        }
         public void checkcollision(ArrayList<keys> key){
            for(int i =0;i< key.size(); i++){
                keys kn = key.get(i);
                if(facingRight) {
                    if(kn.getx() > x &&kn.getx() < x + scratchRange && kn.gety() > y - height / 2 && kn.gety() < y + height / 2) {
                        kn.hit(scratchDamage);
                        key1=1;
                    }
                }
                else {
                    if(kn.getx() < x &&kn.getx() > x - scratchRange &&kn.gety() > y - height / 2 && kn.gety() < y + height / 2) {
                        key1=1;
                    }
		}
            }
            setnum(key1);
           
      }
       public void checkprincess(ArrayList<princess> prin){
           for(int i =0;i< prin.size(); i++){
                if(getnum()==1){
                     princess pn = prin.get(i);
                if(facingRight) {
                    if(pn.getx() > x &&pn.getx() < x + scratchRange && pn.gety() > y - height / 2 && pn.gety() < y + height / 2) {
                        pn.hit(scratchDamage);
                      
                    }
                }
                else {
                    if(pn.getx() < x &&pn.getx() > x - scratchRange &&pn.gety() > y - height / 2 && pn.gety() < y + height / 2) {
                       
                    }
		}
                }
                
               
            }
           
       }
      public void hit(int damage) {           /////เมือถูก enemy ชีววิตลดลง
		if(flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	private void getNextPosition() {
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
            else {
                if(dx > 0) {
                    dx -= stopSpeed;
                    if(dx < 0) {
                        dx = 0;
                    }
                }
                else if(dx < 0) {
                    dx += stopSpeed;
                    if(dx > 0) {
                        dx = 0;
                    }
                }
            }
            if((currentAction == SCRATCHING || currentAction == FIREBALL) &&!(jumping || falling)) {
                dx = 0;
            }
            // jumping
            if(jumping && !falling) {
                dy = jumpStart;
                falling = true;	
            }
            // falling
            if(falling) {
                if(dy > 0 && gliding) dy += fallSpeed * 0.1;
                else dy += fallSpeed;
                if(dy > 0) jumping = false;
                if(dy < 0 && !jumping) dy += stopJumpSpeed;
                if(dy > maxFallSpeed) dy = maxFallSpeed;	
            }
            //check done flinching  //เวลาโดนศัตรูตัวจะกระพริบ
            if(flinching){
                long elapsed = (System.nanoTime()-flinchTimer)/1000000;
                if(elapsed> 1000){
                    flinching = false;
                }
            }
        }
        public void update() {
            // update position
            getNextPosition();
            checkTileMapCollision();
            setPosition(xtemp, ytemp);
            // set animation
            if(scratching) {
                if(currentAction != SCRATCHING) {
                    currentAction = SCRATCHING;
                    animation.setFrames((sprites.get(SCRATCHING)));
                    animation.setdelay(50);
                    width = 100;
                }
            }
            else if(dy > 0) {
                if(gliding) {
                    if(currentAction != GLIDING) {
                        currentAction = GLIDING;
                        animation.setFrames(sprites.get(GLIDING));
                        animation.setdelay(100);
                        width = 100;
                    }
                }
                else if(currentAction != FALLING) {
                    currentAction = FALLING;
                    animation.setFrames(sprites.get(FALLING));
                    animation.setdelay(60);
                    width = 100;
                }
            }
            else if(dy < 0) {
                if(currentAction != JUMPING) {
                    currentAction = JUMPING;
                    animation.setFrames(sprites.get(JUMPING));
                    animation.setdelay(1);
                    width = 100;
                }
            }
            else if(left || right) {
                if(currentAction != WALKING) {
                    currentAction = WALKING;
                    animation.setFrames(sprites.get(WALKING));
                    animation.setdelay(60);
                    width = 100;
                }
            }
            else {
                if(currentAction != IDLE) {
                    currentAction = IDLE;
                    animation.setFrames(sprites.get(IDLE));
                    animation.setdelay(200);
                    width = 100;
                }
            }
            animation.update();
		// set direction
		if(currentAction != SCRATCHING && currentAction != FIREBALL) {
			if(right) facingRight = true;
			if(left) facingRight = false;
		}
	}
    @Override
        public void draw(Graphics2D g) {
            setMapPosition();
		// draw player
                if(flinching) {
                    long elapsed =(System.nanoTime() - flinchTimer) / 1000000;
                    if(elapsed / 100 % 2 == 0) {
                        return;
                    }
                }
                super.draw(g);
        }
}