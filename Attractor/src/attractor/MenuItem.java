package attractor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuItem {
	String name, selectedGameState;
	boolean selected;
	int x,y;
	public MenuItem(String name_, boolean selected_, int x_, int y_, String selectedGameState_)
	{
		name = name_;
		selected = selected_;
		x = x_;
		y = y_;
		selectedGameState = selectedGameState_;
		
	}
	public boolean getSelected()
	{
		return selected;
	}
	
	public String getGameStateifSelected() {
		return selectedGameState;
	}
	public void setSelected(boolean selected_)
	{
		selected = selected_;
	}
	
	public void draw(Graphics g)
	{
		if (selected)
		{
			g.setColor(new Color (170, 170, 170));
			g.fillRect(x, y - 30, 600, 40);
		}
		g.setColor(new Color (255, 245, 214));
		g.setFont( new Font( "serif" , Font.BOLD , 30 ));
		g.drawString(name, x, y);

	}
}
