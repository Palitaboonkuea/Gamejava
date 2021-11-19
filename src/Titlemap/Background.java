package Titlemap;

import Entity.PLayer;
import main.Gamepanel;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Background  {
    
    private Image image;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private double moveScale;
    
    public Background(String s){
        try{
           URL BackgroundURL = this.getClass().getResource(s);
           image = new ImageIcon(BackgroundURL).getImage();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Background(String s, double ms) {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
    
    public void setPosition(double x,double y){
        this.x=(x*moveScale)% Gamepanel.WIDTH;
        this.y=(y*moveScale)% Gamepanel.HEIGHT;
    }
    public void setVector(double dx,double dy){
        this.dx=dx;
        this.dy =dy;
    }
   public void update(){
      x +=dx;
      y +=dy;
    }

    public void draw(Graphics2D g){
         g.drawImage(image, 0, 0,null);
      
       
    }
     public void draw1(Graphics g2){
         g2.drawImage(image, 0, 0,null);
       
    }
}
