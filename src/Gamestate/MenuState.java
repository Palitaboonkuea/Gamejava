package Gamestate;

import Titlemap.Background;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
public class MenuState extends GameState {
    private Background bg;
    private int currentChoice =0;
    private String[] options={"Start","Quit"};
    private Color titlecolor;
    private Font titleFont;
    private Font font;
   
    
    public MenuState(GamestateManager gsm){
        this.gsm = gsm;
        try{
           
                 bg= new Background("/image/blackground2.gif");
               
            
           
            titlecolor = new Color(80,60,74);
            titleFont = new Font("ARial",Font.PLAIN,100);
            font = new Font("ARial",Font.PLAIN,57);
        }   
        catch(Exception e){
            e.printStackTrace();
        
       }
    }
    public void init(){}
    public void update(){
        bg.update();
    }
     public void draw1(Graphics g2){
        bg.draw1(g2);
        g2.setColor(titlecolor);
        g2.setFont(titleFont);
        g2.drawString("FIND THE LOST",275,130);
        g2.drawString("PRINCESS",400,240);
        //draw menu options
        g2.setFont(font);
        for(int i=0;i<options.length;i++){
            if(i == currentChoice){
                g2.setColor(Color.RED);
            }
            else{
                g2.setColor(Color.BLACK);
            }
            g2.drawString(options[i], 570, 620+i*60);
        }
    }
     
     public void draw(Graphics2D g){
         bg.draw1(g);
        
    }
     private void select() {
		if(currentChoice == 0) {
			gsm.setState(GamestateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			System.exit(0);
		}
	
	
     }
    public void keyPressed(int k){
        if(k==KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP){
            currentChoice--;
            if(currentChoice==-1){
                currentChoice= options.length -1;
            }
        }
        if(k== KeyEvent.VK_DOWN){
            currentChoice++;
            if(currentChoice== options.length){
                currentChoice = 0;
            }
        }
    }
    public void keyReleased(int k){}
}



