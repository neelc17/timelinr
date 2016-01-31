package timelinr;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * @authors Siddharth Mathur, Neel Chaudhari
 */

public class Help extends JPanel{
	Base base;
	Font qFont, aFont;
	JLabel q1, q2, q3, a1, a2, a3;
	public Help(Base b){
		base = b;
		q1 = new JLabel("How does Timelinr improve my productivity?");
		q2 = new JLabel("How do I use Timelinr?");
		q3 = new JLabel("Who is this application made for?");
		a1 = new JLabel("<html>It constantly sends you notifications, keeping you on track. <br>It provides an easily understandable interface.<br><br></html>");
		a2 = new JLabel("<html>When you start, it takes you to a page where you can enter task names<br>and timings. After that a timeline will created using the data you gave,<br>and you can click the button when you finish each task. <br>At the end the program will tell you how much time you wasted or saved.<br><br></html>");
		a3 = new JLabel("<html>Although it is geared towards helping high school and college students <br>staying focused on their work rather than other distractions, this <br>application can be used by anybody!<br><br></html>");
		qFont = new Font("Verdana", Font.PLAIN, 23);
		aFont = new Font("Verdana", Font.PLAIN, 17);
		q1.setFont(qFont);
		q2.setFont(qFont);
		q3.setFont(qFont);
		a1.setFont(aFont);
		a2.setFont(aFont);
		a3.setFont(aFont);
		add(q1);
		add(a1);
		add(q2);
		add(a2);
		add(q3);
		add(a3);
		JButton bon = new JButton("Back to home");
		add(bon);
		bon.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				base.showIt("home");
			}
		});
	}
}