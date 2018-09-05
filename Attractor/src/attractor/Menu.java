package attractor;

import java.awt.Graphics;

public class Menu {
	boolean on;
	MenuItem[] main = new MenuItem[] { new MenuItem("Start Game", true, 0, 200, "difficultyMenu"), new MenuItem("Credits", false, 0, 300, "Credits" ), new MenuItem("Exit", false, 0, 400, "exiting") };
	MenuItem[] difficultyArray = new MenuItem[] { new MenuItem("Easy", false, 0, 100, "1"), new MenuItem("Normal", true, 0, 200, "2" ), new MenuItem("Hard", false, 0, 300, "3"), new MenuItem("Insane", false, 0, 400, "4"), new MenuItem("Back", false, 0, 500, "Menu") };
	MenuItem[] MenuArray = new MenuItem[0];
	public Menu(boolean on_, String type)
	{
		on = on_;
		if (type == "main") {
			MenuArray = main;
		}
		else if (type.equals("difficulty")) {
			MenuArray = difficultyArray;
		}
	}
	
	public void setOn(boolean on_)
	{
		on = on_;
	}
	
	public boolean getOn()
	{
		return on;
	}
	
	public String choose() {
		for (int i = 0; i < MenuArray.length; i++)
		{
			if (MenuArray[i].getSelected() == true) {
				return MenuArray[i].getGameStateifSelected();
			}
		}
		return null;
	}
	
	public void draw(Graphics g)
	{
		for (int i = 0; i < MenuArray.length; i++)
		{
			MenuArray[i].draw(g);
		}
	}
	
	public void changeSelection(String downOrUp)
	{
		int oldIndex = 0;
		for (int i = 0; i < MenuArray.length; i++)
		{
			if (MenuArray[i].getSelected() == true) {
				 oldIndex = i;
			}
		}
		if (downOrUp.equals("down")  && MenuArray.length - 1 > oldIndex) {
			MenuArray[oldIndex + 1].setSelected(true);
			MenuArray[oldIndex].setSelected(false);
		}
		else if (downOrUp.equals("up") && oldIndex > 0 ) {
			MenuArray[oldIndex - 1].setSelected(true);
			MenuArray[oldIndex].setSelected(false);
		}

	}
	
}
