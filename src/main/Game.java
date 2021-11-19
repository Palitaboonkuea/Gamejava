package main;

import javax.swing.*;
import javax.swing.JFrame;


public class Game {
    public static void main(String[] args){
        JFrame window=new JFrame("Find The Lost Pricess");
        window.setContentPane(new Gamepanel());
       
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
        
    }
}
