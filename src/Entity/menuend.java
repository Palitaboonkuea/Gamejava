package Entity;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class menuend{
  
    private BufferedImage image,image2;
    public menuend(){
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/image/over.jpg"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/image/win.jpg"));
        }
        catch(Exception e){
            e.printStackTrace(); 
        }
    }
     public void draw(Graphics2D g){
            g.drawImage(image, 0,0, null);  
               
        }
    
     public void draw1(Graphics2D g){
            g.drawImage(image2, 0,0, null);  //setตำแหน่งชีววิตกับเก็บคะแนน
        }
     
    
    }


    

