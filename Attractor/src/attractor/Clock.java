package attractor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.Timer;

public class Clock extends Observable implements ActionListener {

	public static Timer timer;   

	public Clock(){
		this.startClock();
	}
	
	public static void main(String[] args) {
		 Clock c = new Clock();
		 while(true)
		 {
			 System.out.println(c.getTime());
		 }
	}
	
	public String getTime(){
		String time = "";

		GregorianCalendar calendario = new GregorianCalendar();   
		int h = calendario.get(GregorianCalendar.HOUR_OF_DAY);   
		int m = calendario.get(GregorianCalendar.MINUTE);   
		int s = calendario.get(GregorianCalendar.SECOND);   
		time += ((h < 10) ? "0" : "") + h + ":";
		time += ((m < 10) ? "0" : "") + m + ":";
		time += ((s < 10) ? "0" : "") + s;

		return time;
	}
	
	public int getIntTime() {
		return (Integer.parseInt(this.getTime().substring(0,1))*3600) + (Integer.parseInt(this.getTime().substring(3,5))*60) + Integer.parseInt(this.getTime().substring(6));
	}
	
	public void actionPerformed(ActionEvent e) {
		setChanged();
		notifyObservers(this.getTime());
	}
	
	public void startClock() {
		if (timer == null) {
			timer = new javax.swing.Timer(1000, this);
			timer.setInitialDelay(0);
			timer.start();
		}
	}

	public void restartClock(){
		if (!timer.isRunning()){
			timer.restart();	
		}
	}
		
	public void stopClock() {
		if (timer != null) {
			timer.stop();
		}
	}
}
