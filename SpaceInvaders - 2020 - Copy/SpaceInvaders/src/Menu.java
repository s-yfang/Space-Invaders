import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class Menu extends JFrame implements ActionListener{
	private SpaceInvaders space;
	JButton playBtn = new JButton("PLAY");
	JButton scoresBtn = new JButton("HIGHSCORES");

	public Menu(SpaceInvaders s){
		super ("SPACE INVADERS");
		setSize (800, 800);
		space = s;
		playBtn.addActionListener(this);
		scoresBtn.addActionListener(this);

		ImageIcon back = new ImageIcon("titleText.png");
		JLabel backLabel = new JLabel(back);
		JLayeredPane mPage = new JLayeredPane();
		mPage.setLayout(null);

		backLabel.setSize(800, 800);
		backLabel.setLocation(0, - 30);
		mPage.add(backLabel, 1);
						
		// PLAY													
		playBtn.setSize(250, 40);												
		playBtn.setLocation(280, 500);
		playBtn.setOpaque(false);
		playBtn.setContentAreaFilled(false);
		playBtn.setBorderPainted(false);
		mPage.add(playBtn, 2);
		
		// HIGHSCORES
		scoresBtn.setSize(260, 40);
		scoresBtn.setLocation(270, 560);
		scoresBtn.setOpaque(false);
		scoresBtn.setContentAreaFilled(false);
		scoresBtn.setBorderPainted(false);
		mPage.add(scoresBtn, 2);

		add(mPage);
		setVisible(true);
	}

    public void actionPerformed(ActionEvent evt) {
    	setVisible(false);
		space.start();
    }
}
