package Gamestate;

import Entity.*;
import Entity.enemies.slugger;
import main.Gamepanel;
import Titlemap.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Level1State extends GameState
{
    private Font font2;
    private int currentChoice =0;
    private int currentChoice2 =0;
    private String[] options={"Restart"};
    private String[] options2={" Menu","Restart"};
    private int key1=0;
    private PLayer n;
    private Font font;
    private Background bg;
    private TileMap tileMap;
    private PLayer player;
    private ArrayList<enemy> en;
    private ArrayList<keys> key;
    private ArrayList<princess> prin;
    private ArrayList<explosion> exp;
    private hud hd;
    private menuend end;
	
	public Level1State(GamestateManager gsm) {
		this.gsm = gsm;
		init();
	}
    @Override
	public void init() {
		
		tileMap = new TileMap(90);
		tileMap.loadTiles("/image/bgrassE2.png");
		tileMap.loadMap("/image/level1-1.map");
		tileMap.setPosition(0, 0);
                tileMap.setTween(0.07);
                
                bg = new Background("/image/bgtree.jpg",0.1);
                player = new PLayer(tileMap);
		player.setPosition(140,140);
           
                populateenemy();
                keyss();
                prince();
              
                exp =  new ArrayList<explosion>();
                hd= new hud(player);
                end = new menuend();
	}
        private void populateenemy(){
              en = new ArrayList<enemy>();
                slugger s;       
            Point[] point = new Point[] {
            new Point(600, 100),
            new Point(920, 200),
            new Point(1430, 200),
            new Point(1525, 200),
            new Point(1680, 200),
            new Point(1600, 200),
            new Point(1960, 200),
            new Point(3600, 200),
            new Point(3400, 200)
            };
            for(int i =0; i<point.length;i++){
                s =new slugger(tileMap);
                s.setPosition(point[i].x, point[i].y);
                en.add(s);
            }
               
        }
        private void keyss(){
            keys k;
            key =  new ArrayList<keys>();
             Point[] point = new Point[] {
            new Point(1490, 200),
        };
          for(int i =0; i<point.length;i++){
              k  = new keys(tileMap);
              k.setPosition(point[i].x, point[i].y);
              key.add(k);
            }
        }
        private void prince(){
            princess  p;
            prin = new ArrayList<princess>();
            Point[] point = new Point[]{
                new Point(3720,330),
            };
            for(int i =0; i<point.length;i++){
              p  = new princess(tileMap);
              p.setPosition(point[i].x, point[i].y);
              prin.add(p);
            } 
        }

    @Override
	public void update() {
            //update player
            player.update();
            tileMap.setPosition(Gamepanel.WIDTH/2-player.getx(),Gamepanel.HEIGHT/2-player.gety());
            //set background
            bg.setPosition(tileMap.getx(),tileMap.gety());
            //attack enemy
            player.checkAttack(en);
            player.checkcollision(key);
            player.checkprincess(prin);

            //update all  enemy
            for(int i =0;i<en.size();i++){ 
                enemy e = en.get(i);
                e.update();
                en.get(i).update();        
            }
            
            for(int i =0;i<key.size();i++){ 
                keys k = key.get(i);
                k.update();  
              if(k.isDead()) {
                   key.remove(i);
		   i--;
               
                exp.add(new explosion(k.getx(), k.gety()));
                } 
            }
             for(int i =0;i<prin.size();i++){ 
                princess p =prin.get(i);
                p.update();
                prin.get(i).update();  
                 if(p.isDead()){
                   
                   prin.remove(i);
		   i--;
               
                exp.add(new explosion(p.getx(), p.gety()));
                }
               
            }
            //update explosion 
           for(int i =0; i<exp.size();i++){
                exp.get(i).update();
                if(exp.get(i).shouldRemove()){
                    exp.remove(i);
                    i--;
                }
            } 
        }

    @Override
	public void draw(Graphics2D g) {
		//draw bg
                bg.draw(g);
		tileMap.draw(g);
                
                //draw player
                player.draw(g);
                
                //draw enemy
                for(int i=0; i< en.size();i++){
                    en.get(i).draw(g);
                }
                //draw explosion
               for(int i =0; i<exp.size();i++){
                    exp.get(i).setMapPosition((int)tileMap.getx(),(int)tileMap.gety());
                    exp.get(i).draw(g);
                }
                
                //draw key
                for(int i=0; i< key.size();i++){
                    key.get(i).draw(g);
                }
                
                //draw princess
                for(int i=0; i<prin.size();i++){
                    prin.get(i).draw(g);
                }
                
                
                //draw hud
                hd.draw(g);
                //draw end
               if(player.getHealth() == 0){
                   end.draw(g);
                   font =  new Font("ARial",Font.PLAIN,120);
                   g.setFont(font);
                   g.setColor(Color.BLACK);
                   g.drawString("GAME OVER",305,180);
                   font2 = new Font("ARial",Font.PLAIN,47);
                   g.setFont(font2);
                   for(int i=0;i<options.length;i++){
                       if(i == currentChoice){
                           g.setColor(Color.RED);
                       }
                       else{
                           g.setColor(Color.BLACK);
                       }
                       g.drawString(options[i], 570, 670+i*50);
                   }
               }
               if(prin.size()==0){
                  end.draw1(g);
                   font =  new Font("ARial",Font.PLAIN,120);
                   g.setFont(font);
                   g.setColor(Color.BLACK);
                   g.drawString("YOU WIN",365,180);
                    font2 = new Font("ARial",Font.PLAIN,40);
                   g.setFont(font2);
                   for(int i=0;i<options2.length;i++){
                       if(i == currentChoice2){
                           g.setColor(Color.BLUE);
                       }
                       else{
                           g.setColor(Color.BLACK);
                       }
                       g.drawString(options2[i], 560, 620+i*50);
                   }
                   
                   
               }
                
	}
          private void select() {
		if(currentChoice == 0) {
			gsm.setState(GamestateManager.MENUSTATE);
		}
                if(currentChoice2 == 0){
                    gsm.setState(GamestateManager.MENUSTATE);
                }
                if(currentChoice2 == 1) {
			gsm.setState(GamestateManager.LEVEL1STATE);
		}
               
	
	
     }
	
    @Override
	public void keyPressed(int k) {
            if(k== KeyEvent.VK_LEFT) player.setLeft(true);
            if(k== KeyEvent.VK_RIGHT) player.setRight(true);
            if(k== KeyEvent.VK_W) player.setJumping(true);
            if(k==KeyEvent.VK_E) player.setGliding(true);
            if(k==KeyEvent.VK_ENTER){
            select();
        }
             if(k == KeyEvent.VK_UP){
            currentChoice2--;     
            currentChoice--;
            if(currentChoice==-1){
                currentChoice= options.length -1;
            }
            if(currentChoice2 ==-1){
                currentChoice2= options2.length -1;
            }
        }
            if(k== KeyEvent.VK_DOWN){
            currentChoice2++;   
            currentChoice++;
            if(currentChoice== options.length){
                currentChoice = 0;
            }
              if(currentChoice2== options2.length){
                currentChoice2 = 0;
            }
        }
        }
    @Override
	public void keyReleased(int k) {
            if(k== KeyEvent.VK_LEFT) player.setLeft(false);
            if(k== KeyEvent.VK_RIGHT) player.setRight(false);
            if(k== KeyEvent.VK_W) player.setJumping(false);
            if(k==KeyEvent.VK_E) player.setGliding(false);
            if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
           
        }
    @Override
         public void draw1(Graphics g2) {}
	
    
}

