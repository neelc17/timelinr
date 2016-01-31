package timelinr;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @authors Neel Chaudhari, Siddharth Mathur
 */

public class Schedule extends JPanel implements ActionListener{
	private Base base;
	private HowMany hm;
	private Tasks t;
	private JButton cont;
	private ArrayList<ArrayList<String>> info;
	
	public Schedule(Base b){
		setLayout(new BorderLayout());
		base = b;
		t = new Tasks(this);
		hm = new HowMany(t);
		cont = new JButton("Continue");
		cont.addActionListener(this);
		info = new ArrayList<ArrayList<String>>();
		add(hm, BorderLayout.NORTH);
		add(cont, BorderLayout.SOUTH);
		add(t, BorderLayout.CENTER);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == cont){
			info = t.returnArray();
			base.time.setST(info);
			base.showIt("time");
			
		}
	}
}

class HowMany extends JPanel implements ActionListener{
	private Tasks tasks;
	private JTextField howMany;
	private JButton contin;
	private int numTasks;
	
	public HowMany(Tasks t){
		setLayout(new FlowLayout());
		tasks = t;
		howMany = new JTextField(10);
		contin = new JButton("Continue");
		numTasks = 0;
		contin.addActionListener(this);
		add(howMany);
		add(contin);
	}
	
	public int getNumTasks(){
		return numTasks;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == contin){
			try{
				numTasks = Integer.parseInt(howMany.getText().trim());
				tasks.setTasks(numTasks);
			}catch(Exception ex){
				new Error("Something went wrong, try entering a proper integer with no extra characters");
			}
		}
	}
}

class TheTask extends JPanel implements ActionListener{
	private JLabel enterName;
	private JLabel enterTime;
	private JLabel enterDash;
	private JLabel enterColon;
	public JTextField name;
	public JTextField fromHour;
	public JTextField fromMin;
	public JTextField toHour;
	public JTextField toMin;
	private JMenuBar fromMBar;
	private JMenuBar toMBar;
	public JMenu toMenu;
	public JMenu fromMenu;
	private JMenuItem toAM;
	private JMenuItem toPM;
	private JMenuItem fromAM;
	private JMenuItem fromPM;
	private int curr;
	
	public TheTask(int i){
		setLayout(new FlowLayout());
		curr = i;
		enterName = new JLabel("Task " + curr + " name:");
		enterTime = new JLabel("Time: ");
		enterDash = new JLabel("-");
		enterColon = new JLabel(":");
		name = new JTextField(25);
		fromHour = new JTextField(2);
		fromMin = new JTextField(2);
		toHour = new JTextField(2);
		toMin = new JTextField(2);
		fromMBar = new JMenuBar();
		fromMenu = new JMenu("AM");
		fromAM = new JMenuItem("AM");
		fromPM = new JMenuItem("PM");
		toMBar = new JMenuBar();
		toMenu = new JMenu("AM");
		toAM = new JMenuItem("AM");
		toPM = new JMenuItem("PM");
		
		add(enterName);
		add(name);
		add(enterTime);
		add(fromHour);
		add(enterColon);
		add(fromMin);
		fromMenu.add(fromAM);
		fromMenu.add(fromPM);
		fromMBar.add(fromMenu);
		add(fromMBar);
		add(enterDash);
		add(toHour);
		enterColon = new JLabel(":");
		add(enterColon);
		add(toMin);
		toMenu.add(toAM);
		toMenu.add(toPM);
		toMBar.add(toMenu);
		add(toMBar);
		fromAM.addActionListener(this);
		fromPM.addActionListener(this);
		toAM.addActionListener(this);
		toPM.addActionListener(this);

	}
	
	public void actionPerformed(ActionEvent e){
		Object o = e.getSource();
		if(o == fromAM || o == fromPM){
			fromMenu.setText(((JMenuItem)o).getText());
		}
		
		else if(o == toAM || o == toPM){
			toMenu.setText(((JMenuItem)o).getText());
		}
	}
}

class Tasks extends JPanel{
	private int tasks;
	private JPanel pan;
	private ArrayList<TheTask> ts;
	public Tasks(JPanel p){
		tasks = 0;
		pan = p;
	}
	
	public void setTasks(int t){
		removeAll();
		tasks = t;
		setLayout(new GridLayout(tasks, 1));
		ts = new ArrayList<TheTask>();
		for(int i = 0; i < tasks; i++){
			TheTask ayy = new TheTask(i+1);
			ts.add(ayy);
			add(ayy);
			
		}
		revalidate();
		repaint();
		pan.revalidate();
		pan.repaint();
	}
	
	public ArrayList<ArrayList<String>> returnArray(){
		ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < ts.size(); i++){
			ArrayList<String> as = new ArrayList<String>();
			String toHour = "";
			String toMin = ts.get(i).toMin.getText().trim();
			if(Integer.parseInt(ts.get(i).toHour.getText().trim()) < 10)
				toHour = "0" + Integer.parseInt(ts.get(i).toHour.getText().trim()); 
			else
				toHour = "" + Integer.parseInt(ts.get(i).toHour.getText().trim());
			if(ts.get(i).toMenu.getText().equals("PM") && Integer.parseInt(toHour) != 12)
				toHour = "" + (Integer.parseInt(toHour)+12);
			if(Integer.parseInt(toHour) == 12 && ts.get(i).toMenu.getText().equals("AM"))
				toHour = "00";
			String fromHour = "";
			String fromMin = ts.get(i).fromMin.getText().trim();
			if(Integer.parseInt(ts.get(i).fromHour.getText().trim()) < 10)
				fromHour = "0" + Integer.parseInt(ts.get(i).fromHour.getText().trim()); 
			else
				fromHour = "" + Integer.parseInt(ts.get(i).fromHour.getText().trim());
			if(ts.get(i).fromMenu.getText().equals("PM") && Integer.parseInt(fromHour) != 12)
				fromHour = "" + (Integer.parseInt(fromHour)+12);
			if(Integer.parseInt(fromHour) == 12 && ts.get(i).fromMenu.getText().equals("AM"))
				fromHour = "00";

			as.add(ts.get(i).name.getText());
			as.add(fromHour+fromMin);
			as.add(toHour+toMin);
			System.out.println(as);
			arr.add(as);
		}
		return arr;
	}
}

class Error extends JFrame{
	private JLabel la;
	public Error(String s){
		super("Error");
		setSize(500, 150);
		la = new JLabel(s);
		setContentPane(la);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}