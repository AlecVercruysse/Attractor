package attractor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;


public class Debris {
	private double x, y, maxHealth;
	private Ship followObject;
	private boolean active;
	Image debrisImage = new ImageIcon( "DodgerFallers.jpg" ).getImage();
	private Health health;

	private static final int SCREEN_WIDTH = 1920;
	private static final int SCREEN_HEIGHT = 1080;
	
	double cooldownStart;

	public Debris (double x_, double y_, Ship followObject_, double health_, boolean active_) {
		x = x_;
		y = y_;
		followObject = followObject_;
		maxHealth = health_;
		active = active_;
		health = new Health(100.0, 1.0, x, y + 35, 30, 10, Color.red );
	}

	public void draw (Graphics g, ImageObserver io) {
		if (active) {
			g.drawImage(debrisImage, (int) x, (int) y, io);
			health.draw(g);
		}
	}

	public double getX() {
		return x;

	}

	public double getY() {
		return y;
	}

	public void setActive(boolean b) {
		active = b;
	}

	public void update(String gameState_, double timeElapsed, Debris[] parentArray) {
		if (active) {
			health.setCoords(x, y + 30);
			double xDiff = followObject.getX() - x - 15; // 15 is to set wanted center
			double yDiff = followObject.getY() - y - 15;
			double mDistance = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));

			double timeMultiplier = 0;
			if (timeElapsed < 30.0) {
				timeMultiplier = timeElapsed;
			} else { }//timeMultiplier = 30 + (timeElapsed - 30)*0.2; }
			x += xDiff * Integer.parseInt(gameState_) * 0.1 * timeMultiplier/ mDistance ;
			y += yDiff * Integer.parseInt(gameState_) * 0.1 * timeMultiplier/ mDistance ;

			for (int i = 0; i < parentArray.length; i++) {
				double debrisXd = parentArray[i].getX() - x;
				double debrisYd = parentArray[i].getY() - y;
				double debrisDistance = Math.sqrt((debrisXd * debrisXd) + (debrisYd * debrisYd));
				if (debrisDistance < 30.0 && debrisDistance != 0.0) {
					//System.out.println("TRIGGERED");
					x = Math.random() * SCREEN_WIDTH;
					y = Math.random() * SCREEN_HEIGHT;
					cooldownStart = timeElapsed;
					setActive(false);
				} else if (debrisDistance != 0.0) {
					x += -debrisXd * 0.05 / (debrisDistance);
					y += -debrisYd * 0.05 / (debrisDistance);
				}
			} 
		}
		if (cooldownStart != 0.0) {
			if (cooldownStart + 2.0 <= timeElapsed) {
				setActive(true);
				cooldownStart = 0.0;
			}
		}
	}

	public void hit(double damage) {
		if (active) {
			health.takeCustomHealth(damage);
		}
	}
}
