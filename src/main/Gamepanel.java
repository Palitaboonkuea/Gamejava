package main;
import Gamestate.GamestateManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
public class Gamepanel extends JPanel implements Runnable,KeyListener{
    
   public static final int WIDTH = 1300;
   public static final int HEIGHT = 695;
   
   //game thread
   private Thread thread;
   private boolean  running;
   private int FPS = 60;
   private long targetTime = 1000/ FPS;
   
   //image
   private Image image;
   private Graphics g2;
   private Graphics2D g;
   //game state manager
   private GamestateManager gsm;
   
   
   public Gamepanel(){
       super();
       setPreferredSize(new Dimension(WIDTH,HEIGHT));
       setFocusable(true);
       requestFocus();
   }
   
 
   public void addNotify(){
       super.addNotify();
       if(thread == null){
           thread = new Thread(this);
           addKeyListener(this);
           thread.start();
       }
   }
   private void init(){
       image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
       g = (Graphics2D) image.getGraphics();
       g2=(Graphics) image.getGraphics();
       
       running = true;
       gsm = new GamestateManager();
   }
   @Override
   public void run(){
       init();
       long start;
       long elapsed;
       long wait;
       //game loop
       while(running){
           start = System.nanoTime();
           update();
           draw();
           draw1();
           drawToScreen();
           elapsed = System.nanoTime()- start;
           wait = targetTime - elapsed/1000;
          
           
       }
   }
   private void update(){
       gsm.update();
   }
   private void draw(){
       gsm.draw(g);
   }
   
   private void draw1(){
       gsm.draw1(g2);
   }
   private void drawToScreen(){
       Graphics gb = getGraphics();
       gb.drawImage(image,0,0,WIDTH,HEIGHT,null);
       gb.dispose();
       
   }
   
   @Override
   public void keyTyped(KeyEvent key){
       
   }
   @Override
   public void keyPressed(KeyEvent key){
       gsm.keyPressed(key.getKeyCode());
   }
   @Override
   public void keyReleased(KeyEvent key){
       gsm.keyReleased(key.getKeyCode());
   }
   
  
   
}
