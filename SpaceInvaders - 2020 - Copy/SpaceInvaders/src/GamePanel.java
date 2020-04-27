import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.*;
import java.awt.Font.*;

import java.io.*; 

class GamePanel extends JPanel{
	
	private boolean [] keys;
	private boolean enemyHit = false, playerHit = false;
	
	public boolean ready = false;
	private boolean gotName = false;

	private int reload, wait, specTimer = 1000, specDirect;
	private int LIVES = 3, X = 100, Y = 650, V = 5;
	private int playerScore;
	private int counter = 0, eCount = 700;
	
	ArrayList <Rectangle> bulletRects = new ArrayList<Rectangle>();
	ArrayList <Rectangle> enBullRects = new ArrayList<Rectangle>();

	ArrayList <Rectangle> invaderRects = new ArrayList<Rectangle>();
	ArrayList <Rectangle> shieldRects = new ArrayList<Rectangle>();
	ArrayList <Shield> shields = new ArrayList<Shield>();
	
	ArrayList <Integer> scores = new ArrayList<Integer>();
	
	ArrayList <Integer> highScores = new ArrayList<Integer>(); // not scores, but highscores to read and write to txt

	Shield s;
																				
	Rectangle playerRect, bulletRect, enemyRect, specEnemyRect, invader, cInvader, cPlayer;
	Rectangle replayRect, borderRect;

	private Image player, playerDeath, playerDeath2,playerDeath3, enemyBullet1, enemyBullet2, life;
	private Image enemy1a, enemy2a, enemy3a, enemy1b, enemy2b, enemy3b, enemySpec, enemyDeath;
	private Image bg, gameover, scoreText, livesText;
	private Image hit1, hit2, hit3;
	
	int n, x; 
	int speedUp, eWait;
	boolean check = true;

	public GamePanel(){
		keys = new boolean[KeyEvent.KEY_LAST+1];
													
        s = new Shield(0, 0, 0, 0, 0);											// Shield object

		playerRect = new Rectangle(X, Y, 60, 40);								
        enemyRect = new Rectangle(X, 50, 30, 30);								
        specEnemyRect = new Rectangle(-70, 120, 65, 35);
        	
        replayRect = new Rectangle(380, 390, 40, 30);
        borderRect = new Rectangle(0, 730, 800, 40);

		makeShield();

		//IMAGES
		
		bg = new ImageIcon("bg.png").getImage();
		gameover = new ImageIcon("gameover.png").getImage();
        
        player = new ImageIcon("player.png").getImage();
        player = player.getScaledInstance(60, 40, Image.SCALE_SMOOTH);
        
        hit1 = new ImageIcon("hit1.png").getImage();
        hit1 = hit1.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        
        hit2 = new ImageIcon("hit2.png").getImage();
        hit2 = hit2.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        
        hit3 = new ImageIcon("hit3.png").getImage();
        hit3 = hit3.getScaledInstance(20, 20, Image.SCALE_SMOOTH); 
        
		enemy1a = new ImageIcon("enemy1a.png").getImage();
        enemy1a = enemy1a.getScaledInstance(35, 30, Image.SCALE_SMOOTH);

		enemy2a = new ImageIcon("enemy2a.png").getImage();
        enemy2a = enemy2a.getScaledInstance(35, 30, Image.SCALE_SMOOTH);
        
		enemy3a = new ImageIcon("enemy3a.png").getImage();
        enemy3a = enemy3a.getScaledInstance(35, 30, Image.SCALE_SMOOTH);
        
        enemy1b = new ImageIcon("enemy1b.png").getImage();
        enemy1b = enemy1b.getScaledInstance(35, 30, Image.SCALE_SMOOTH);

		enemy2b = new ImageIcon("enemy2b.png").getImage();
        enemy2b = enemy2b.getScaledInstance(35, 30, Image.SCALE_SMOOTH);
        
		enemy3b = new ImageIcon("enemy3b.png").getImage();
        enemy3b = enemy3b.getScaledInstance(35, 30, Image.SCALE_SMOOTH);
        
		enemySpec = new ImageIcon("enemySpec.png").getImage();
        enemySpec = enemySpec.getScaledInstance(65, 35, Image.SCALE_SMOOTH);
        
		enemyDeath = new ImageIcon("enemyDeath.png").getImage();
        enemyDeath = enemyDeath.getScaledInstance(40, 30, Image.SCALE_SMOOTH);
        
		playerDeath = new ImageIcon("playerDeath.png").getImage();
        playerDeath = playerDeath.getScaledInstance(35, 30, Image.SCALE_SMOOTH);    
        	
        playerDeath2 = new ImageIcon("playerDeath2.png").getImage();
        playerDeath2 = playerDeath2.getScaledInstance(35, 30, Image.SCALE_SMOOTH);   
        	
      	playerDeath3 = new ImageIcon("playerDeath3.png").getImage();
        playerDeath3 = playerDeath3.getScaledInstance(35, 30, Image.SCALE_SMOOTH);       
        
		enemyBullet1 = new ImageIcon("enemyBullet1.png").getImage();
        enemyBullet1 = enemyBullet1.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        
		enemyBullet2 = new ImageIcon("enemyBullet2.png").getImage();
        enemyBullet2 = enemyBullet2.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        
		life = new ImageIcon("life.png").getImage();
        life = life.getScaledInstance(60, 40, Image.SCALE_SMOOTH);                                
        
        scoreText = new ImageIcon("scoreText.png").getImage();
        scoreText = scoreText.getScaledInstance(120, 30, Image.SCALE_SMOOTH);  
        	
        livesText = new ImageIcon("livesText.png").getImage();
        livesText = livesText.getScaledInstance(100, 30, Image.SCALE_SMOOTH);   
     
	}	
																				
    public void setKey(int k, boolean v) {					
    	keys[k] = v;
    }
    
    public boolean win(){														// Checks if invaders are alive
    	if(invaderRects.size() == 0){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public boolean defeat(){													// Checks if player is alive			
    	if(LIVES == 0){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public void reset(){														// Resets game when player chooses to replay
    
    	if (keys[KeyEvent.VK_SPACE]){
    		LIVES = 3;		
			playerRect.x = X;
			playerRect.y = Y;
			playerScore = 0;
			gotName = false;
			
			n = 0;
			x = 0;
			check = true;
			eWait = 0;
			speedUp = 0;
			
			scores.clear();
			invaderRects.clear();
			
			shieldRects.clear();
			shields.clear();
			makeShield();
    	}

	}
    
    public void addNotify() {
        super.addNotify();
        ready = true;
    }
    
    ////////////////////////////////////////////////////////////////
    
    public String getUserName(){
    	String name = " ";
    	if(!gotName){
    		gotName = true;
         	name = JOptionPane.showInputDialog("ENTER YOUR NAME:");
    	}
    	return name;
    }
    
    // read highscores from txt file
    public void readScores(){
    	
    	try{
    		Scanner inFile = new Scanner(new File("highscores.txt"));
    		int n = Integer.parseInt(inFile.nextLine());
    		for ( int i = 0; i < n; i ++ ) {
    			String [] scoreStat = inFile.nextLine().split(",");
    			highScores.add(Integer.parseInt(scoreStat[1]));
    		}
    	}
    	
    	catch( Exception ex ) {
    		System.out.println(ex + ": highscores.txt" );
    	}
    }
    
    public int getHighScorePos( int playerScore ) {
    	for ( int i = 0; i < highScores.size(); i ++ ) {	
    		if (playerScore >= highScores.size()) {
    			for (int j = highScores.size()-1; j > i; j--) {	//update highscores
    				highScores.set(j, highScores.get(j-1));
    			}
    			return i+1;
    		}
    	}
    	return -1;
    }
    
    // after write to file
    
    ////////////////////////////////////////////////////////////////////////////////////////////
    
    public void makeScores(){
    	for (int i = 0; i < 5; i ++){											// Arraylist of invader scores
			for (int j = 0; j < 11; j ++){
				if (i == 0){
					scores.add(40);
				}
				else if (i == 1 || i == 2){
					scores.add(20);
				}
				else if (i == 3 || i == 4){
					scores.add(10);
				}
			}
		}
    }
    
    public void makeEnemies(){
		for (int i = 0; i < 5; i ++){											// Creates invader rects				
			for (int j = 0; j < 11; j ++){
				int eX = (150 + 50 * j);
				int eY = (170 + 50 * i);
				invaderRects.add(new Rectangle(eX, eY, 40, 30));
			}
		}
    }
    
    public void moveEnemies(){													// Move invaders
		
		int y = 0;
		int s = 0;
		
		if (eWait != 0){														// Timer
			eWait -= 1;
		}

		if (n < 80 && check == true && eWait == 0){								// While invaders are in frame, move right
			n += 5;
			x = 5;	
		}

		if (n > -120 && check == false && eWait == 0){							// While invaders are in frame, move left
			n -= 5;
			x = -5;	
		}

		if (n == 80){															// When invaders reach end of frame, move down
			check = false;
			y = 10;
			s = 4;
		}

		if (n == -120){
			check = true;
			y = 10;
			s = 4;
		}
	
		
		if (eWait == 0){
			for(Rectangle e : invaderRects){
				e.x += x;
				e.y += y;
			}	
			
			speedUp += s;														// Gradually speed up as invaders approach player
			eWait = 80 - speedUp;
			counter ++;
		}
		
    }
    
    public void makeShield(){													// Makes shields
    	for(int i = 0; i < 4; i++){												// 4 shields made of 10 squares each
			for(int j = 0; j < 2; j ++){
				shields.add(new Shield(100 + 21 * i, 525 + 21 * j, 20, 20, 0));
				shields.add(new Shield(270 + 21 * i, 525 + 21 * j, 20, 20, 0));
				shields.add(new Shield(450 + 21 * i, 525 + 21 * j, 20, 20, 0));
				shields.add(new Shield(620 + 21 * i, 525 + 21 * j, 20, 20, 0));
			}
		}
		
		for(int i = 0; i < 2; i++){
			shields.add(new Shield(100 + 63 * i, 525 + 21 * 2, 20, 20, 0));
			shields.add(new Shield(270 + 63 * i, 525 + 21 * 2, 20, 20, 0));
			shields.add(new Shield(450 + 63 * i, 525 + 21 * 2, 20, 20, 0));
			shields.add(new Shield(620 + 63 * i, 525 + 21 * 2, 20, 20, 0));
		}
		
		for(int i = 0; i < 4; i++){														// Creates shield rects
			for(int j = 0; j < 2; j ++){
				shieldRects.add(new Rectangle(100 + 21 * i, 525 + 21 * j, 20, 20));
				shieldRects.add(new Rectangle(270 + 21 * i, 525 + 21 * j, 20, 20));
				shieldRects.add(new Rectangle(450 + 21 * i, 525 + 21 * j, 20, 20));
				shieldRects.add(new Rectangle(620 + 21 * i, 525 + 21 * j, 20, 20));
			}
		}
		
		for(int i = 0; i < 2; i++){
			shieldRects.add(new Rectangle(100 + 63 * i, 525 + 21 * 2, 20, 20));
			shieldRects.add(new Rectangle(270 + 63 * i, 525 + 21 * 2, 20, 20));
			shieldRects.add(new Rectangle(450 + 63 * i, 525 + 21 * 2, 20, 20));
			shieldRects.add(new Rectangle(620 + 63 * i, 525 + 21 * 2, 20, 20));
		}
		
		
    }
    
    public void hitShield(ArrayList <Rectangle> bList){							// When bullets hit shield
    	
    	for(int b = bList.size()-1; b >= 0; b--){
    		Rectangle br = bList.get(b);
    		
    		for(int s = shieldRects.size()-1; s >= 0; s--){
				Rectangle sr = shieldRects.get(s);
				Shield sh = shields.get(s);
				
				if(br.intersects(sr) == true){									// Bullet hits shield
					bList.remove(br);
					reload = 10;												// Reload after collision
					sh.hits += 1;
					
					if(sh.hits == 4){
						shieldRects.remove(sr);	
						shields.remove(sh);	
					}	
				}
			}	
    	}
    }
    
    public void addBullet(){													// Add new player bullet to ArrayList
    	bulletRects.add(new Rectangle(playerRect.x + 30, playerRect.y - 5, 3, 10));
    }
    
    public void addEnemyBull(){													// Add new enemy bullet to ArrayList
    	int ranNum = (int)(Math.random()*1);									// Randomly select an invader and whether to shoot bullet or not
    	int ranEn = (int)(Math.random()*invaderRects.size());
    	
    	if(invaderRects.size() != 0){
    		Rectangle ranEnemy = invaderRects.get(ranEn);	
    		
    		if(ranNum == 0){
	    		enBullRects.add(new Rectangle(ranEnemy.x + 15, ranEnemy.y + 35, 3, 10));		
	    	}
    	}
    }

    public void shoot(){														// Shoot bullet
		int ranNum = 0;

		if(reload != 0){														// Time before next bullet
			reload -= 1;
		}

    	if(keys[KeyEvent.VK_SPACE] && reload == 0){
    		reload = 50;
    		addBullet();
    	}

    	for(int i = bulletRects.size()-1; i >= 0; i--){

			//MOVE BULLET
    		Rectangle br = bulletRects.get(i);
    		br.y -= V;

			//COLLISION
			if(br.y <= 0){														// Bullet goes off screen
	    		bulletRects.remove(br);;
	    	}
	    	
			for(int e = invaderRects.size()-1; e >= 0; e--){
				
				Rectangle er = invaderRects.get(e);
				
				if(br.intersects(er)){											// Bullet hits enemy
					
					cInvader = er;
					enemyHit = true;
					bulletRects.remove(br);
					invaderRects.remove(er);
					reload = 10;												// Reload after collision
					
					playerScore += scores.get(e);
					scores.remove(e);
				}
				
				if (br.intersects(specEnemyRect)){								
					bulletRects.remove(br);
					specEnemyRect.x = -70;
					specTimer = 1000;
					reload = 10;
					
					ranNum = (int)(Math.random()*1);
					
					if (ranNum == 0){
						playerScore += 150;
					}
					
					if (ranNum == 1){
						playerScore += 100;
					}
				}
				
			}
			
			hitShield(bulletRects);
		}
    }

    public void enAttack(){														// Enemy Attack
																				// Time before next attack
    	if(wait == 0){
    		wait = 200;
    		addEnemyBull();
    	}

    	for(int i = enBullRects.size()-1; i >= 0; i--){
    		Rectangle eb = enBullRects.get(i);
    		
    		eb.y += V;															// Move bullet	
    		
			if (eb.intersects(playerRect) == true){								// Bullet hits player
				cPlayer = playerRect;
				playerHit = true;
					
				enBullRects.remove(eb);											// Remove bullet
				LIVES -= 1;														// Remove life
				playerRect.x = X;												// Reset player position
				playerRect.y = Y;
			}
			
			if (eb.intersects(borderRect) == true){								// Bullet reaches border
				enBullRects.remove(eb);		
			}
		}
		
		hitShield(enBullRects);
		wait -= 1;
    }
    
    public void special(){														// Special invader
			
    	if (specTimer != 0){
			specTimer -= 1;
			
			if (specDirect == 0){												// Invader appears from left
				specEnemyRect.x = -70;	
			}
			
			if (specDirect == 1){												// Invader appears from right
				specEnemyRect.x = 805;	
			}
			
		}
		
		if (specTimer == 0){
			
			if (specDirect == 0){
				specEnemyRect.x += 1;
				
				if (specEnemyRect.x > getWidth()){
					specTimer = 1000;											// Reset timer
					specDirect = (int)(Math.random()*2);						// Randomly chooses direction invader appears from
				}					
			}
			
			else if (specDirect == 1){
				specEnemyRect.x -= 1;
				
				if (specEnemyRect.x < 0){
					specTimer = 1000;	
					specDirect = (int)(Math.random()*2);	
				}
			}
    		
		}
		
	}
    
	public void move(){															// Moves player
		
		if(keys[KeyEvent.VK_RIGHT] && playerRect.x != 755){						// Checks if player is in bounds of screen
			playerRect.x += 5;
		}
		
		if(keys[KeyEvent.VK_LEFT] && playerRect.x != 5){
			playerRect.x -= 5;
		}
	}

    public void paintComponent(Graphics g){

    	// BACKGROUND
    	g.drawImage(bg, 0, 0, this);
		
		// PLAYERSCORE
    	g.drawImage(scoreText, 70, 60, this);
    	
    	g.setFont(new Font("Tahoma", Font.PLAIN, 30));
    	g.setColor(Color.green);
    	g.drawString(String.valueOf(playerScore), 210, 86);
    	
    	// LIVES
    	g.drawImage(livesText, 430, 60, this);
    	
    	for(int i = 0; i < LIVES; i ++){	
    		g.drawImage(life, 550 + i*65, 50, this);
    	}
    	
    	// SHIELDS
    	for(Shield s : shields){
    		g.setColor(Color.green);
			g.fillRect(s.x, s.y, s.w, s.h);
			
			if(s.hits == 1){													// If shield is hit
				g.drawImage(hit1, s.x, s.y, this);
			}
			else if(s.hits == 2){
				g.drawImage(hit2, s.x, s.y, this);
			}
			else if(s.hits == 3){
				g.drawImage(hit3, s.x, s.y, this);
			}
    	}

		// PLAYER
		
		/*
		if (playerHit == true && cnt > 0){
			g.drawImage(playerDeath, playerRect.x, playerRect.y, this);
    		cnt --;
		}
		
		if (cnt <= 0){
			playerHit = false;
			cnt = 400;
		}
		
		if (playerHit == false){	
			g.drawImage(player, playerRect.x, playerRect.y, this);
		}
		*/
		
		g.drawImage(player, playerRect.x, playerRect.y, this);

		// SPECIAL ENEMY
		g.drawImage(enemySpec, specEnemyRect.x, specEnemyRect.y, this);	
			
		// ENEMY
		//counter = e.getCounter();												// For animations
		
		if (defeat() == false){													// While player is alive
		
			for (int i = 0; i < invaderRects.size(); i ++){
			//for (Rectangle invader : invaderRects){
				
				invader = invaderRects.get(i);
				
				/*
				if (invader.enemyHit() == true){
					g.drawImage(enemyDeath, invader.x, invader.y, this);
				}
				*/
				
				if (enemyHit == true && eCount > 0){							// Enemy death animations
					g.drawImage(enemyDeath, cInvader.x, cInvader.y, this);
		    		eCount --;
				}
				
				if (eCount <= 0){
					enemyHit = false;
					eCount = 700;												// Time of enemy death animation
				}
				
				if (scores.get(i) == 10){										// Bottom rows of invaders
					if (counter%2 == 0){
						g.drawImage(enemy3a, invader.x, invader.y, this);
					}
					if (counter%2 == 1){
						g.drawImage(enemy3b, invader.x, invader.y, this);
					}
				}
				
				else if (scores.get(i) == 20){									// Middle row
					if (counter%2 == 0){
						g.drawImage(enemy2a, invader.x, invader.y, this);
					}
					if (counter%2 == 1){
						g.drawImage(enemy2b, invader.x, invader.y, this);
					}
				}
				
				else if (scores.get(i) == 40){									// Top row
					if (counter%2 == 0){
						g.drawImage(enemy1a, invader.x, invader.y, this);
					}
					if (counter%2 == 1){
						g.drawImage(enemy1b, invader.x, invader.y, this);
					}
	
				}

				if (invader.y >= 550 && invader.x >= 670){											// If invader crosses bounds, gameover
					LIVES = 0;
				}

			}

		}
		

		//	PLAYER BULLETS
		for(Rectangle b : bulletRects){
			g.setColor(Color.white);
			g.fillRect(b.x, b.y, b.width, b.height);
		}

		//	ENEMY BULLETS   
		for(Rectangle eb : enBullRects){
			g.drawImage(enemyBullet1, eb.x, eb.y, this);
		}
		
		//	BORDER
		g.setColor(Color.green);
    	g.drawLine(1, getHeight() - 40, getWidth(), getHeight() - 40);
    	
		//	GAMEOVER
		if(defeat() == true){													// If player loses all lives, gameover
			g.drawImage(gameover, 0, 0, this);
		}

    }
}