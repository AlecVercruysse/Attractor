package attractor;

import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class Ship 
{

	private double x, y, dx, dy, height, width;

	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 1500;

	private double a, b, csqr;
	private double easingAmount = 0.1;

	private double angle;

	private Image myImage;

	private boolean isActive;

	public Ship(int _x, int _y, int _width, int _height, boolean isActive_)
	{
		x = _x;
		y = _y;
		width = _width;
		height = _height;
		myImage = new ImageIcon ("DodgerShip.jpg").getImage();
		isActive = isActive_;
	}

	public void move()
	{
		if (isActive) {
			x += dx;
			y += dy;
		}
	}

	public void setIsActive( boolean e) {
		isActive = e;
	}

	public boolean getIsActive () {
		return isActive;
	}
	public void rotate(double magnitude)
	{
		angle += magnitude;
	}

	public void update(double mx, double my)
	{
		this.move();
		a = mx - x - 15; // 15 to set center @ mouse
		b = my - y - 15;
		csqr = (b*b) + (a*a);
		double c = Math.sqrt(csqr);
		if (c > 1)
		{
			x += a * easingAmount;
			y += b * easingAmount;
		}
	}

	public void draw(Graphics g, ImageObserver io)
	{
		if (isActive) { 
			g.drawImage (myImage, (int)x, (int)y, io);
		}
	}
	public double getX()
	{
		return x + 15;
	}

	public double getY()
	{
		return y + 15;
	}

	public double getA()
	{
		return a;
	}

	public double getB()
	{
		return b;
	}

	public double getHeight()
	{
		return height;
	}

	public boolean getIfTouching(Debris[] dArray)
	{
		if (isActive) {
			for (int i = 0; i < dArray.length; i++) {
				if (isActive) {
					double debx = dArray[i].getX();
					double deby = dArray[i].getY();
					if (x < debx+30 && x+30 > debx && y < deby+30 && y+30 > deby) {
						return true;
					}
				}
			}
		}
		return false;

	}

	public double getWidth()
	{
		return width;
	}

	public double getAngle()
	{
		return angle;
	}

}
