// SPACE INVADERS
// Sheng Fang & Emily Wang

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SpaceInvaders extends JFrame implements ActionListener, KeyListener{
	
	Timer myTimer;
	GamePanel game = new GamePanel();

    public SpaceInvaders() {
		super("SPACE INVADERS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		
		myTimer = new Timer(10, this);	 										// Trigger every 10 ms
		add(game);

		new Menu(this);
    }

    public void start(){
		myTimer.start();
		setVisible(true);
		addKeyListener(this);
		setResizable(false);
	}
																				
	public void actionPerformed(ActionEvent e){
		Object source = e.getSource();

		if (game != null){
			if (game.defeat() == false){										// While player is alive:
				game.move();													// Move player
				game.shoot();													// Shoot bullets
				game.enAttack();												// Enemies attack
				game.moveEnemies();												// Move enemies
				game.special();				
					
				if(game.win() == true){											// If player defeats all invaders
					game.makeEnemies();											// Spawn more invaders
					game.makeScores();
				}

			}
			
			if (game.defeat() == true){											// If player is defeated:
				game.getUserName();												// Get user name
				game.reset();													// Reset game
			}
			
			game.repaint();
			
		}

	}

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
    	game.setKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
		game.setKey(e.getKeyCode(), false);
    }
	
    public static void main(String[] arguments) {
		SpaceInvaders frame = new SpaceInvaders();
    }
}