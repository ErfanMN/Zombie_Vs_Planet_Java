/*** In The Name of Allah ***/
package game;

import javax.swing.*;

/**
 * Program start.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Main {
	
    public static void main(String[] args) {

        ThreadPool.init();
        // Show the game menu ...
        GameMenu gameMenu = new GameMenu("Menu");
        gameMenu.setLocationRelativeTo(null); // put frame at center of screen
        gameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameMenu.ShowMenu();


    }
}
