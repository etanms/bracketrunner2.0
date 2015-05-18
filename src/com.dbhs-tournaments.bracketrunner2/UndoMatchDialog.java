package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.etan.bracketrunner2.matches.Match;

@SuppressWarnings("serial")
public class UndoMatchDialog extends JDialog implements ActionListener {
	
	Match match;
	
	JButton yesButton;
	JButton noButton;
	
	public UndoMatchDialog(Match match) {
		
		super(Main.getWindow(), "", Dialog.ModalityType.TOOLKIT_MODAL);
		
		this.match = match;
		
		Font font = new Font("Arial Black", Font.PLAIN, 13);
		
		JLabel label = new JLabel("Would you like to undo");
		label.setFont(font);
		label.setForeground(Color.BLACK);
		JLabel label_2 = new JLabel("the results of this match?");
		label_2.setFont(font);
		label_2.setForeground(Color.BLACK);
		yesButton = new JButton("Yes");
		yesButton.setFont(font);
		yesButton.setForeground(Color.BLACK);
		noButton = new JButton("No");
		noButton.setFont(font);
		noButton.setForeground(Color.BLACK);
		
		yesButton.addActionListener(this);
		noButton.addActionListener(this);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(Color.CYAN);
		labelPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		labelPanel.setLayout(new GridLayout(2,1,0,-9));
		labelPanel.add(label);
		labelPanel.add(label_2);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);

		JPanel content = new JPanel();
		content.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		content.setLayout(new GridLayout(2,1));
		content.add(labelPanel);
		content.add(buttonPanel);
        
        setContentPane(content);
		pack();
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}// ends UndoMatchDialog()

	/**
	 * Handles all types of events in which data about the match 
	 * is meant to be undone.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(yesButton)) {
			match.undo();
			Main.getControlPanel().initNextPanel();
			dispose();
			Main.getControlPanel().updateInfo();
			Main.getControlPanel().getScreen().getBracket().showEndMatchDialog(match);
		} else if(evt.getSource().equals(noButton)) {
			dispose();
		}
		
	}// ends actionPerformed()

}// ends Class
