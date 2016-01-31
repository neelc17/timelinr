package timelinr;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @authors Neel Chaudhari, Risha Wadhwa
 */

public class End extends JPanel implements ActionListener{
	Messagey ma;
	JButton b;
	Base base;
	String s;
	int h;
	int m;
	public End(Base ba){
		base = ba;
		setLayout(new BorderLayout());
	}
	
	public void setValues(String sa, int ha, int mar){
		s = sa;
		h = ha;
		m = mar;
		ma = new Messagey(s, h, m);
		b = new JButton("Go Back to Home");
		b.addActionListener(this);
		add(b, BorderLayout.SOUTH);
		add(ma, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e){
		base.showIt("home");
	}
}

class Messagey extends JPanel {
	String message;
	int hours;
	int minutes;
	public Messagey(String s, int h, int m){
		message = s;
		hours = h;
		minutes = m;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString("You have " + message + " " + hours + " hours and " + minutes + " minutes.", (getWidth()/2)-10, getHeight()/2);
	}
}