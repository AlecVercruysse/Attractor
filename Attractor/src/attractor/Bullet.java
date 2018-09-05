package attractor;

import java.awt.Color;
import java.awt.Graphics;


public class Bullet {
	private double x, y, dx, dy, dir;
	private boolean isOn;
	private int timeStarted;
	private static final int radius = 6;
	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 600;
	private static final int timeToLast = 3;
	private static Clock C = new Clock();
	
	public Bullet (double x_, double y_, double dx_, double dy_, boolean on_)  
	{
		x = x_;
		y = y_;
		dx = dx_;
		dy = dy_;
		isOn = on_;
	}
	
	public double getX ()
	{
		return x;
	}
	
	public double getY ()
	{
		return y;
	}
	public boolean getOn()
	{
		return isOn;
	}
	 public void setX(double x_)
	 {
		 x = x_;
	 }
	 public void setY(double y_)
	 {
		 y = y_;
	 }
	 public void setDX(double dx_)
	 {
		 dx = dx_;
	 }
	 public void setDY(double dy_)
	 {
		 dy = dy_;
	 }
	 
	 public void toggleOn()
	 {
		 isOn =  !isOn;
	 }
	 
	 public void startBullet(double x_, double y_, double dx_, double dy_, boolean isOn_)
	 {
		 x = x_;
		 y = y_;
		 dx = dx_;
		 dy = dy_;
		 isOn = isOn_;
		 timeStarted = C.getIntTime();
		 
	 }
	 
	 public boolean hitCircle(double xIn, double yIn, int radIn)
	 {
		 return ( Math.pow(radIn - radius, 2) <= Math.pow(xIn - x, 2) + Math.pow(yIn - y, 2) && Math.pow(xIn - x, 2) + Math.pow(yIn - y, 2)  <= Math.pow(radIn+ radius, 2) );

	 }
	 
	 public boolean hitBox(double xIn, double yIn, double dimension) // code by e.James on (http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection)
	 {
		 double circleDistanceX = Math.abs(x - xIn);
		 double circleDistanceY = Math.abs(y - yIn);
		 
		 if (circleDistanceX > (dimension / 2 + radius)) {return false;}
		 if (circleDistanceY > (dimension / 2 + radius)) {return false;}
		 
		 if (circleDistanceX <= dimension / 2) {return true;}
		 if (circleDistanceY <= dimension / 2) {return true;}
		 
		 double cornerDistance_sq = Math.pow((circleDistanceX - dimension / 2), 2) + Math.pow((circleDistanceY - dimension / 2), 2);
		 return (cornerDistance_sq <= (radius * radius));
	 }
	
	public void update()
	{
		if (timeStarted + timeToLast < C.getIntTime())
		{
			isOn = false;
		}
		x = x + dx;
		y = y + dy;
		
		if (x > SCREEN_WIDTH)
		{
			x  = 0;
		}
		else if (x < 0)
		{
			x = SCREEN_WIDTH;
		}
		if (y > SCREEN_HEIGHT)
		{
			y = 0;
		}
		else if (y < 0)
		{
			 y = SCREEN_HEIGHT;
		}
	}
	
	public void draw(Graphics g, Color col)
	{
		g.setColor(col);
		g.fillOval((int) (x - (radius / 2)),(int) (y - radius / 2),radius * 2, radius * 2);
	}
}
