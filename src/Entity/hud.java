package Entity;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class hud {
    
    private PLayer player;
    private Font font;
    private BufferedImage image;
    public hud(PLayer p){
        player =p;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/image/hud.png"));
            font = new Font("Arial",Font.PLAIN,40);
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
       
    }
     public void draw(Graphics2D g){
            g.drawImage(image, 0,10, null);  //setตำแหน่งชีววิตกับเก็บคะแนน
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(player.getHealth()+"/"+player.getMaxHealth(),130,50);
            g.drawString(player.getkey()+"/"+player.getMaxkey(),130,117);
        }
    
}
