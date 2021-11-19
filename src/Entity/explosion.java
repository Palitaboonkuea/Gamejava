package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class explosion {
    private int x,y,xmap,ymap;
    private int width,height;
    private Animetion animation;
    private BufferedImage[] sprites;
    
    private boolean remove;
    public explosion(int x, int y){
        this.x=x;
        this.y=y;
        width=30;
        height=30;
        
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/image/explosion.png"));
            sprites = new BufferedImage[6];
            for(int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width,0,width,height);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        animation = new Animetion();
        animation.setFrames(sprites);
        animation.setdelay(70);
    }
    public void update(){
       animation.update();
	if(animation.hasPlayedOnce()) {
            remove = true;
        }
    }
    public boolean shouldRemove(){
        return remove;
    }
    public void setMapPosition(int x,int y){
        xmap =x;
        ymap=y;
    }
    public void draw(Graphics2D g){
        g.drawImage( animation.getImage(),x+xmap-width/2,y+ymap-height/2,null);
       
    }
    
}
