package Gamestate;

import Entity.menuend;
import Entity.*;
import java.util.ArrayList;

abstract class GameState {
    protected GamestateManager gsm;
    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void draw1(java.awt.Graphics g2);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
}



public class GamestateManager {
   
	
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
      
	public GamestateManager() {
		
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
		gameStates.add(new Level1State(this));
              

	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).init();
              
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}
        public void draw1(java.awt.Graphics g2){
        gameStates.get(currentState).draw1(g2);
    }
	
}


 


