package timelinr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @authors Rishi Wadhwa, Siddharth Mathur, Neel Chaudhari
 */

public class Timeline extends JPanel{
	ArrayList<ArrayList<String>> st;
	String end;
	Top t;
	Middle m;
	Bottom b;
	String finMess;
	int finHrs;
	int finMin;
	Base base;
	public Timeline(Base ba){
		base = ba;
		setLayout(new GridLayout(3, 1));
		finMess = "";
		finHrs = 0;
		finMin = 0;
		st = null;
		end = "";
	}
	public void setST(ArrayList<ArrayList<String>> s){
		removeAll();
		st = s;
		end = st.get(st.size()-1).get(2);
		t = new Top(st, this, end);
		m = new Middle(st);
		b = new Bottom(st, this);
		add(t);
		add(m);
		add(b);
		revalidate();
		repaint();
	}		      
}
	
class Top extends JPanel implements ActionListener{
	Timeline timel;
	Timer time;
	String currTime;
	DateFormat dateFormat;
	Calendar cal;
	JLabel timela;
	String end;
	ArrayList<ArrayList<String>> data;
	public Top(ArrayList<ArrayList<String>> st, Timeline ti, String e){
		data = st;
		timel = ti;
		end = e.substring(0, 2) + ":" + e.substring(2, 4) + ":" + "00";
		time = new Timer(1, this);
		time.addActionListener(this);
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		cal = Calendar.getInstance();
		String str = dateFormat.format(cal.getTime());
		currTime = str.substring(str.indexOf(" ")+1);
		timela = new JLabel(currTime);
		add(timela);
		time.start();
	}

	public void actionPerformed(ActionEvent e){
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		cal = Calendar.getInstance();
		String str = dateFormat.format(cal.getTime());
		currTime = str.substring(str.indexOf(" ")+1);
		String[] lumos = currTime.split(":");
		int timeHrs = Integer.parseInt(lumos[0]);
		int timeMin = Integer.parseInt(lumos[1]);
		int timeSec = Integer.parseInt(lumos[2]);
		String[] lomsees = end.split(":");
		int endHrs = Integer.parseInt(lomsees[0]);
		int endMin = Integer.parseInt(lomsees[1]);
		int endSec = Integer.parseInt(lomsees[2]);
		int diffHrs = endHrs - timeHrs;
		int diffMin = endMin - timeMin;
		int diffSec = endSec - timeSec;
		if(diffSec < 0){
			diffMin--;
			diffSec = 60 + diffSec;
		}
		if(diffMin < 0){
			diffHrs--;
			diffMin = 60 + diffMin;
		}
		String diff = "";
		if(diffHrs < 10)
			diff += "0" + diffHrs;
		else
			diff += "" + diffHrs;
		if(diffMin < 10)
			diff += ":0" + diffMin;
		else
			diff += ":" + diffMin;
		if(diffSec < 10)
			diff += ":0" + diffSec;
		else
			diff += ":" + diffSec;
		if(diffHrs < 0 || diffMin < 0 || diffSec < 0){
			diff = "00:00:00";
			timela.setForeground(Color.RED);
			timela.setText(diff);
			timela.revalidate();
			time.stop();
		}
		else{
			timela.setText(diff);
			timela.revalidate();
		}
	}
}

class Middle extends JPanel{
	ArrayList<ArrayList<String>> data;
	int[] hourpixels;
	
	public Middle(ArrayList<ArrayList<String>> st){
		data = st;
		hourpixels = new int[25];
	}
	
	public void paintComponent(Graphics g){
		setBackground(Color.BLACK);
		super.paintComponent(g);

		int width = (int)getWidth();
		int height = (int)getHeight();
		
		g.setColor(Color.WHITE);
		g.fillRect(50, (height/2) - 5, width -100, 10);
		
		int hour = 0;
		String hstring = "";
		int twentyfour = (width - 100)/24;
		int temphour = 0;
		for(int i = 50; hour < 25; i += twentyfour){//add numbers to number line
			hourpixels[hour] = i;
			if(hour >= 13)
				temphour = hour - 12;
			else
				temphour = hour;
			
			if (hour == 0)
				g.drawString("12", i, (height/2) + 40);
			else{
				hstring = temphour + "";
				g.drawString(hstring, i, (height/2) + 40);
			}
			
			hour++;
		}
		String task = "";
		String start = "";
		String end = "";
		int p1 = 0;
		int p2 = 0;
		for (int c = 0; c < data.size(); c++){
			task = (data.get(c)).get(0);
			start = (data.get(c)).get(1);
			end = (data.get(c)).get(2);
			
			Color co = new Color((int)(Math.random()*255)+1, (int)(Math.random()*255)+1, (int)(Math.random()*255)+1);
			g.setColor(co);
			
			g.drawRect(60 * c, height - 30, 7, 7);
			g.drawString(task, 60 * c + 9, height - 20);

			p1 = hourpixels[Integer.parseInt(start.substring(0,2))] + (Integer.parseInt(start.substring(2,4))*twentyfour)/60;
			p2 = hourpixels[Integer.parseInt(end.substring(0,2))] + (Integer.parseInt(end.substring(2,4))*twentyfour)/60;
			g.fillRect(p1, (height/2) - 7, p2 - p1, 14);
		}
		
	}
}


class Bottom extends JPanel implements ActionListener{
	Timeline timel;
	ArrayList<ArrayList<String>> data;
	Timer time;
	String currTime;
	DateFormat dateFormat;
	int diffHrs, diffMin;
	boolean isZero;
	Calendar cal;
	int indCurr;
	String end;
	JLabel timela;
	JButton button;
	public Bottom(ArrayList<ArrayList<String>> s, Timeline ti){
		data = s;
		timel = ti;
		isZero = false;
		time = new Timer(1, this);
		time.addActionListener(this);
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		cal = Calendar.getInstance();
		String str = dateFormat.format(cal.getTime());
		currTime = str.substring(str.indexOf(" ")+1);
		indCurr = findCurrTask();
		end = data.get(indCurr).get(2).substring(0, 2) + ":" + data.get(indCurr).get(2).substring(2, 4) + ":" + "00";
		timela = new JLabel(currTime);
		add(timela);
		button = new JButton("Done with " + data.get(indCurr).get(0));
		button.addActionListener(this);
		add(button);
		time.start();
	}
	
	public int findCurrTask(){
		for(int i = 0; i < data.size(); i++){
			if(Integer.parseInt(currTime.substring(0, 2) + currTime.substring(3, 5)) > Integer.parseInt(data.get(i).get(1)) && Integer.parseInt(currTime.substring(0, 2) + currTime.substring(3, 5)) < Integer.parseInt(data.get(i).get(2))){
				return i;
			}
		}
		return 0;
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==time){
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			cal = Calendar.getInstance();
			String str = dateFormat.format(cal.getTime());
			currTime = str.substring(str.indexOf(" ")+1);
			String[] lumos = currTime.split(":");
			int timeHrs = Integer.parseInt(lumos[0]);
			int timeMin = Integer.parseInt(lumos[1]);
			int timeSec = Integer.parseInt(lumos[2]);
			String[] lomsees = end.split(":");
			int endHrs = Integer.parseInt(lomsees[0]);
			int endMin = Integer.parseInt(lomsees[1]);
			int endSec = Integer.parseInt(lomsees[2]);
			int diffSec = 0;
			if(!isZero){
				diffHrs = endHrs - timeHrs;
				diffMin = endMin - timeMin;
				diffSec = endSec - timeSec;
				if(diffSec < 0){
					diffMin--;
					diffSec = 60 + diffSec;
				}
				if(diffMin < 0){
					diffHrs--;
					diffMin = 60 + diffMin;
				}
			}
			else{
				diffHrs = timeHrs - endHrs;
				diffMin = timeMin - endMin;
				diffSec = timeSec - endSec;
				if(diffSec > 59){
					diffMin++;
					diffSec = 0;
				}
				if(diffMin > 59){
					diffHrs++;
					diffMin = 0;
				}
			}
			String diff = "";
			if(diffHrs < 10)
				diff += "0" + diffHrs;
			else
				diff += "" + diffHrs;
			if(diffMin < 10)
				diff += ":0" + diffMin;
			else
				diff += ":" + diffMin;
			if(diffSec < 10)
				diff += ":0" + diffSec;
			else
				diff += ":" + diffSec;
			if(diffHrs < 0 || diffMin < 0 || diffSec < 0){
				new Reminder("<html>REMINDER:<br>You should be done with " + data.get(indCurr).get(0) + " by now.</html>");
				diff = "00:00:00";
				isZero = true;
				timela.setForeground(Color.RED);
				timela.setText(diff);
				timela.revalidate();
			}
			else{
				if(isZero)
					diff = "-" + diff;
				timela.setText(diff);
				timela.revalidate();
			}
		}
		else if(e.getSource() == button){
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			cal = Calendar.getInstance();
			String str = dateFormat.format(cal.getTime());
			currTime = str.substring(str.indexOf(" ")+1);
			if(isZero)
				timel.finMess = "wasted";
			else
				timel.finMess = "saved";
			timel.finHrs = diffHrs;
			timel.finMin = diffMin;
			data.get(indCurr).set(2, currTime.replace(":", "").substring(0, 4));
			isZero = false;
			dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			cal = Calendar.getInstance();
			String stri = dateFormat.format(cal.getTime());
			currTime = str.substring(stri.indexOf(" ")+1);
			time.restart();
			timel.m = new Middle(data);
			timel.m.repaint();
			timel.m.revalidate();
			timel.repaint();
			timel.revalidate();
			timel.base.end.setValues(timel.finMess, diffHrs, diffMin);
			timel.base.showIt("end");
		}
	}
}

class Reminder extends JFrame{
	private JLabel la;
	public Reminder(String s){
		super("Reminder");
		setSize(500, 150);
		la = new JLabel(s);
		setContentPane(la);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}