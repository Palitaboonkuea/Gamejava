package Entity;
import Titlemap.TileMap;

public class enemy extends mapobj {
    protected boolean dead;
    protected int damage;
    protected int health;
    protected int maxHealth;
    protected boolean flinching;
    protected long flinchTimer;

    
    
    public enemy(TileMap tm) {
        super(tm);
    }
    
    public boolean isDead(){
        return dead; 
     }
    public int getDamage(){
        return damage;
    }
   public void hit(int damage){
        if(dead || flinching) return;
        health -= damage;
        if(health<0) health =0;
        if(health==0)dead = true;
        flinching=true;
        flinchTimer = System.nanoTime();
    }
    public void update(){
        
    }
}

