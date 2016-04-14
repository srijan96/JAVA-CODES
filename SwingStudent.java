import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class SwingStudent{

	Student[] list = new Student[60];

	private JFrame mainFrame;
	private JPanel entry;
	
	private JLabel top;
	private JLabel bottom;
	private JPanel controlPanel;
	
	private JPanel form;
	private JTextField roll;
	private JTextField name;
	private JTextField dept;
	private JTextField date;
	
	private JLabel nameL;
	private JLabel rollL;		
	private JLabel deptL;
	private JLabel dateL;
	
	private JTextField marks;
	
	private JPanel viewer;
	
	private JLabel view,vl1,vl2,vl3,vl4;
	
	public SwingStudent()
	{
		prepareGUI();
	}
	private void prepareGUI()
	{
		mainFrame = new JFrame("Student Management System");
		mainFrame.setSize(800,400);
		mainFrame.setLayout(new GridLayout(1,2));
		mainFrame.getContentPane().setBackground(new Color(70,240,170));
		
		entry = new JPanel();
		entry.setSize(500,400);
		entry.setLayout(new GridLayout(4,2));
		
		viewer = new JPanel();
		viewer.setLayout(new GridLayout(8,1));
		viewer.setSize(300,300);
		
		top = new JLabel("" , JLabel.CENTER);
		bottom = new JLabel("" , JLabel.CENTER);
		
		form = new JPanel();
		form.setLayout(new GridLayout(4,2));
		
		nameL = new JLabel("Name" , JLabel.LEFT);
		rollL = new JLabel("Roll No." , JLabel.LEFT);
		deptL = new JLabel("Department" , JLabel.LEFT);		
		dateL = new JLabel("Date-of-admission(dd mm yyyy)" , JLabel.LEFT);		
		
		roll = new JTextField();
		name = new JTextField();
		dept = new JTextField();
		date = new JTextField();
		
		form.add(nameL);
		form.add(name);
		form.add(rollL);
		form.add(roll);
		form.add(deptL);
		form.add(dept);
		form.add(dateL);
		form.add(date);
		
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(2,2));
		
		entry.add(top);
		entry.add(form);
	}
	private void showEvent()
	{
		top.setText("Enter credentials");
		
		JButton okButton = new JButton("Add");
		JButton sub = new JButton("Show Details");
		marks = new JTextField();
		JButton marksB = new JButton("Update Marks");
		
		okButton.setActionCommand("add");
		sub.setActionCommand("show");
		marksB.setActionCommand("marks");
		
		okButton.addActionListener(new ButtonClickListener()); 
		sub.addActionListener(new ButtonClickListener()); 
		marksB.addActionListener(new ButtonClickListener()); 

		controlPanel.add(okButton);
		controlPanel.add(sub);
		controlPanel.add(marks);
		controlPanel.add(marksB);   
		
		entry.add(controlPanel);
		
		entry.add(bottom);
		
		view = new JLabel("" , JLabel.CENTER);
		vl1 = new JLabel("" , JLabel.CENTER);
		vl2 = new JLabel("" , JLabel.CENTER);
		vl3 = new JLabel("" , JLabel.CENTER);
		vl4 = new JLabel("" , JLabel.CENTER);								
		viewer.add(view);
		viewer.add(vl1);
		viewer.add(vl2);
		viewer.add(vl3);						
		viewer.add(vl4);								
		
		mainFrame.add(entry);
		mainFrame.add(viewer);
		
		mainFrame.setVisible(true);
	}
	private class ButtonClickListener implements ActionListener{
		public void clear()
		{
			roll.setText("");
			name.setText("");
			dept.setText("");
			date.setText("");
			marks.setText("");
		}
		public void actionPerformed(ActionEvent e)
		{
			Student s1 = new Student(0,"","",0,0,0);
			String command = e.getActionCommand();
			if(command.equals("add"))
			{
				String Iname = name.getText();
				int Iroll  = 0,dd=0,mm=0,yy=0;
				if(!(roll.getText().equals("")))
					Iroll = Integer.parseInt(roll.getText());
				String Idept = dept.getText();
				String[] dt = date.getText().split(" ");
				if(dt.length >= 3)
				{
					dd = Integer.parseInt(dt[0]);
					mm = Integer.parseInt(dt[1]);
					yy = Integer.parseInt(dt[2]);										
				}
				String r = s1.validate(Iname,Iroll,Idept,dd,mm,yy);
				if(r.equals("ok"))
				{
					if( list[Iroll] == null){
						s1 = new Student(Iroll,Iname,Idept,dd,mm,yy);
						list[Iroll] = s1;
						clear();
					}
					else
						r = "Roll no. already exists";
				}
				bottom.setText(r);
			}
			else if(command.equals("show"))
			{
				if((roll.getText().equals("")))
					bottom.setText("Provide roll no");
				else{
					int Iproll = Integer.parseInt(roll.getText());
					if(list[Iproll] == null)
						bottom.setText("Empty roll no");
					else
					{
						s1 = list[Iproll];
						view.setText("Details for " + s1.getName());
						vl1.setText("Roll No. " + s1.getRoll());
						vl2.setText("Department " + s1.getDept());
						vl3.setText("Date of Admission " + s1.getDate());
						vl4.setText("Marks " + s1.getMarks());		
						clear();				
					}
				}
			}
			else if(command.equals("marks"))
			{
				if((marks.getText().equals("")))
					bottom.setText("Provide marks seperated by spaces");
				else if((roll.getText().equals("")))
					bottom.setText("Provide roll no");
				else if( list[Integer.parseInt(roll.getText())] == null)
					bottom.setText("Invalid roll no");	
				else{
					int Iproll = Integer.parseInt(roll.getText());
					s1 = list[Iproll];
					s1.updateMarks(marks.getText());
					bottom.setText("Updated marks "+s1.getMarks());
					clear();
				}				
			}
		}
	}
	public static void main(String args[])
	{
		SwingStudent stud = new SwingStudent();
		stud.showEvent();
	}
}
class Student{
	int roll;
	String name;
	String course;
	int admissionDay;
	int admissionMonth;
	int admissionYear;		
	int[] marks=new int[5];
	String validate(String name,int roll,String dept,int dd,int mm,int yy)
		{
			String res = "ok";
			if(yy<2010 || yy > 2016)	res = "InvalidDate";
			else if(mm<1 || mm >12)	res = "InvalidDate";
			else if(dd<1 || dd > 31)	res = "InvalidDate";
			if(roll < 1 || roll > 59)	res = "Roll no out of bound";
			if(name.equals(""))	res = "Name not given";
			if(dept.equals(""))	res = "Department not given";
			return res;
		}
	Student(int roll,String name,String course,int admissionDay,int admissionMonth,int admissionYear)
	{
		this.roll = roll;
		this.name = name;
		this.course = course;
		this.admissionDay = admissionDay;
		this.admissionMonth = admissionMonth;
		this.admissionYear = admissionYear;
		for(int i=0;i<5;i++)	marks[i] = 0;
	}
	String getRoll(){	return Integer.toString(roll);	}
	String getName(){	return name;	}
	String getDept(){	return course;	}
	String getDate(){	return Integer.toString(admissionDay)+"/"+Integer.toString(admissionMonth)+"/"+Integer.toString(admissionYear);	}	
	String getMarks(){	return Integer.toString(marks[0])+"  "+Integer.toString(marks[1])+"  "+Integer.toString(marks[2])+"  "+Integer.toString(marks[3])+"  "+Integer.toString(marks[4]);		}
	void updateMarks(String markstr)
	{
		String[] m = markstr.split(" ");
		for(int i=0;i<m.length && i<5; i++)
			marks[i] = Integer.parseInt(m[i]);
	}
}
