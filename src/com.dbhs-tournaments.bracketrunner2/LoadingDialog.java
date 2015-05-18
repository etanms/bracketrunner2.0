package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoadingDialog extends JDialog {
	
	private JLabel[] labels;
	
	private Thread t;
	
	/**
	 * Constructor
	 */
	public LoadingDialog() {
		
		super(Main.getWindow(), "Please Wait...");
		
		Font font = new Font("Arial Black", Font.PLAIN, 16);
		
		labels = new JLabel[10];
		
		JLabel l = new JLabel("L");
		l.setForeground(Color.CYAN);
		l.setFont(font);
		labels[0] = l;
		
		JLabel o = new JLabel("O");
		o.setForeground(Color.CYAN);
		o.setFont(font);
		labels[1] = o;
		
		JLabel a = new JLabel("A");
		a.setForeground(Color.CYAN);
		a.setFont(font);
		labels[2] = a;
		
		JLabel d = new JLabel("D");
		d.setForeground(Color.CYAN);
		d.setFont(font);
		labels[3] = d;
		
		JLabel i = new JLabel("I");
		i.setForeground(Color.CYAN);
		i.setFont(font);
		labels[4] = i;
		
		JLabel n = new JLabel("N");
		n.setForeground(Color.CYAN);
		n.setFont(font);
		labels[5] = n;
		
		JLabel g = new JLabel("G");
		g.setForeground(Color.CYAN);
		g.setFont(font);
		labels[6] = g;
		
		JLabel p1 = new JLabel(".");
		p1.setForeground(Color.CYAN);
		p1.setFont(font);
		labels[7] = p1;
		
		JLabel p2 = new JLabel(".");
		p2.setForeground(Color.CYAN);
		p2.setFont(font);
		labels[8] = p2;
		
		JLabel p3 = new JLabel(".");
		p3.setForeground(Color.CYAN);
		p3.setFont(font);
		labels[9] = p3;
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		for(int j=0; j<labels.length; j++) {
			panel.add(labels[j]);
		}
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(1, 1));
		content.add(panel);
		
		setPreferredSize(new Dimension(175, 60));
		setContentPane(content);
		setEnabled(false);
		pack();
		
		t = new Thread() {
			
			@Override
			public void run() {
				
				while(true) {
					
					for(int i=0; i<labels.length; i++) {
						
						labels[i].setForeground(Color.BLACK);
						try {
							sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
					
					for(int i=0; i<labels.length; i++) {
						labels[i].setForeground(Color.CYAN);
					}
					try {
						sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}// ends run()
			
		};
		
		t.start();
		
	}// ends Constructor

}// ends Class
