package attractor;

import java.awt.Color;
import java.awt.Graphics;

public class Health {
	private double max, step, x ,y, xl, yl;
	private Color col;
	private double h;
	public Health(double max_, double step_, double x_, double y_, double xl_, double yl_, Color col_)
	{
		h = max_;
		max = max_;
		step = step_;
		x = x_;
		y = y_;
		xl = xl_;
		yl = yl_;
		col = col_;
	}

	public void setCoords(double x_, double y_) {
		x = x_;
		y = y_;
	}

	public double getHealth()
	{
		return h;
	}

	public void takeHealth()
	{	
		if (h - step >= 0) {
			h-=step;
		} else {
			h = 0;
		}
	}

	public void takeCustomHealth(double step_)
	{
		if (h - step >= 0) {
			h-=step_;
		} else {
			h = 0;
		}
	}
	public void setHealth(int h_)
	{
		h = h_;
	}

	public void draw (Graphics g)
	{
		g.setColor(Color.black);
		g.drawRect((int) x,(int) y,(int) xl,(int) yl);
		g.setColor(col);
		g.fillRect((int) (x + 1),(int) (y + 1),(int) (-2 + xl * (h/max)),(int) (yl - 2));
	}
}
