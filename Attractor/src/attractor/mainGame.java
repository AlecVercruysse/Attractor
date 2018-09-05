package attractor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.Timer;

public class mainGame extends JFrame
implements KeyListener, ActionListener, MouseMotionListener, MouseListener
{

	private static final int SCREEN_WIDTH = 1920;
	private static final int SCREEN_HEIGHT = 1080;
	private static final int DELAY_IN_MILLISEC = 10;

	//Bullets
	private static final int BulletNum = 10;
	private static Bullet[] BulletArray = new Bullet[BulletNum];
	private static final double BulletSpeed = 5.0;
	private static int nextBulletIndex = 0;
	private static int wasdX;
	private static int wasdY;

	private int mx, my;

	private String gameState = "welcome";
	Menu menu = new Menu(true, "main");
	Menu difficulty = new Menu(false, "difficulty");

	Ship ship = new Ship(500, 300, 410, 210, false);
	Health shipHealth =new Health(100, 2, 20, 10, SCREEN_WIDTH - 40, 30, Color.red);
	Debris[] debrisArray = new Debris[] { new Debris(Math.random() * SCREEN_WIDTH , Math.random() * SCREEN_HEIGHT, ship, 100, true) , new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true), new Debris(Math.random() * SCREEN_WIDTH , Math.random() * SCREEN_HEIGHT, ship, 100, true), new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true)};


	private double netTimeElapsed = 0.0;
	private double timeElapsed = 0.0;

	static mainGame gp;

	public static void pause ( int ms )
	{
		try {
			Thread.sleep(ms);
		}
		catch (InterruptedException e) {}
	}

	public mainGame()// 									Constructor
	{
		for (int i = 0; i < BulletNum; i++)
		{
			BulletArray[i] = new Bullet (0.0, 0.0, 0.0, 0.0, false);
		}

		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Timer c= new Timer(DELAY_IN_MILLISEC, this);                        
		c.start();

		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public static void main (String[] args)//				Main
	{
		gp = new mainGame();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		netTimeElapsed += DELAY_IN_MILLISEC * 0.001;
		//System.out.println(netTimeElapsed);
		if (netTimeElapsed > 3.0 && gameState.equals("welcome" )) {
			gameState = "Menu";
			timeElapsed = 0;
		} else if (gameState.equals("exiting")) {
			gp.setVisible(false);
			gp.dispose();
			System.exit(0);
		} else if (gameState.equals("Menu") || gameState.equals("difficultyMenu") || gameState.equals("welcome")) {

		}
		else if (gameState.equals("loseScreen")) {

		}
		else {
			timeElapsed+= DELAY_IN_MILLISEC * 0.001;

			for (int i = 0; i < BulletNum; i++)
			{
				BulletArray[i].update();
			}

			ship.update(mx, my);
			if (ship.getIfTouching(debrisArray)) {
				shipHealth.takeHealth();
			}
			
			for (int i = 0; i < BulletNum; i++) {
				for (int j = 0; j < debrisArray.length; j++) {
					WORK ON COLLISION HERE 
				}
			}
			if (shipHealth.getHealth() == 0) {
				gameState = "loseScreen";
				ship.setIsActive(false);
				for (int i = 0; i < debrisArray.length; i++) {
					debrisArray[i].setActive(false);
				}
				//shipHealth = new Health(100, 2, 20, 10, SCREEN_WIDTH - 40, 30, Color.red);
			}
			for (int i = 0; i < debrisArray.length; i++) {
				debrisArray[i].update(gameState, timeElapsed, debrisArray);
			}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if (gameState.equals("loseScreen")) {
			if (keyCode == KeyEvent.VK_M) {
				gameState = "Menu";
			} else {
				gameState = "difficultyMenu";
			}
		}

			else if (keyCode == KeyEvent.VK_UP)
			{
				if (gameState.equals("Menu")) {
					menu.changeSelection("up");
				}
				else if (gameState.equals("difficultyMenu")) {
					difficulty.changeSelection("up");
				}
		}
		else if (keyCode == KeyEvent.VK_DOWN)
		{
			if (gameState.equals("Menu")) {
				menu.changeSelection("down");
			}
			else if (gameState.equals("difficultyMenu")) {
				difficulty.changeSelection("down");
			}
		}
		else if (keyCode == KeyEvent.VK_ENTER) {
			if (gameState.equals("Menu")) {
				gameState = menu.choose();
			}
			else if (gameState.equals("difficultyMenu")) {
				gameState = difficulty.choose();				
				ship.setIsActive(true);
				int maxHealth = 100;
				if (gameState.equals("4")) {
					maxHealth = 1;
				} else { maxHealth = 100/(Integer.parseInt(gameState) - 1); }
				shipHealth = new Health(maxHealth, 2, 20, 10, SCREEN_WIDTH - 40, 30, Color.red);
				timeElapsed = 0;
				debrisArray = new Debris[] { new Debris(Math.random() * SCREEN_WIDTH , Math.random() * SCREEN_HEIGHT, ship, 100, true) , new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true), new Debris(Math.random() * SCREEN_WIDTH , Math.random() * SCREEN_HEIGHT, ship, 100, true), new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true),new Debris(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT, ship, 100, true)};

			}
		}
		
		else if (keyCode == KeyEvent.VK_W) {
			 wasdY = -1;
		}
		else if (keyCode == KeyEvent.VK_A) {
			 wasdX = -1;
		}
		else if (keyCode == KeyEvent.VK_S) {
			 wasdY = 1;
		}
		else if (keyCode == KeyEvent.VK_D) {
			 wasdX = 1;
		}
		else if (keyCode == KeyEvent.VK_SPACE)
		{
			if (BulletArray[nextBulletIndex].getOn() == false && nextBulletIndex <= 9)
			{
				BulletArray[nextBulletIndex].startBullet(ship.getX(), ship.getY(), BulletSpeed * wasdX ,BulletSpeed * wasdY, true);
				nextBulletIndex++;
				if (nextBulletIndex == 10)
				{
					nextBulletIndex = 0;
				}
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) {
			wasdY = 0;
		} else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
			wasdX = 0;
		}
	}

	public void paint(Graphics g)
	{

		g.setColor(Color.black);
		g.fillRect(0,0, SCREEN_WIDTH, SCREEN_HEIGHT);
		if (gameState.equals("Menu")) {
			menu.draw(g);
		}
		else if (gameState.equals("welcome")) {
			g.setColor(new Color (255, 245, 214));
			g.setFont( new Font( "serif" , Font.BOLD , 30 ));
			g.drawString("Welcome to Attractor!", 200, 300);
			g.drawString("Given birth to by Alec and Buzz", 200, 330);
		}

		else if (gameState.equals("difficultyMenu")) {
			difficulty.draw(g);
		}

		else if (gameState.equals("loseScreen")) {
			g.setColor(new Color (255, 245, 214));
			g.setFont( new Font( "serif" , Font.BOLD , 30 ));
			g.drawString("You lost.", 100, 200);
			g.drawString("Final Score: " +timeElapsed, 100, 230);
			g.drawString("Press m to return to the home screen, or any key to try again", 100, 260);
		}
		else {
			ship.draw(g, this);
			shipHealth.draw(g);
			for (int i = 0; i < debrisArray.length; i++) {
				debrisArray[i].draw(g, this);
			}
			for (int i = 0; i < BulletNum; i++)
			{	
				if (BulletArray[i].getOn())
				{
					BulletArray[i].draw(g, Color.white);
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}