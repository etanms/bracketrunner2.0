package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;

import com.etan.bracketrunner2.matches.Match;
import com.etan.dbhs.DBHSHandicapMutator;

@SuppressWarnings("serial")
public class EndMatchDialog extends JDialog implements ActionListener {

	Match match;
	
	JButton okButton;
	JButton cancelButton;
	JButton forfeitButton;
	JButton splitButton;
	
	JTextField p1TextField;
	JTextField p2TextField;
	
	JDialog dialog;
	
	/**
	 * Constructor
	 * 
	 * @param match The match to be associated with this dialog.
	 */
	public EndMatchDialog(Match match) {
		
		super(Main.getWindow(), "Match Results", Dialog.ModalityType.TOOLKIT_MODAL);
		
		this.match = match;
		
		Font font = new Font("Arial Black", Font.PLAIN, 13);
    	
    	JLabel instruction = new JLabel("Enter the final score for each player");
    	instruction.setFont(font);
    	instruction.setForeground(Color.BLACK);
    	JLabel instruction_2 = new JLabel("including handicapped games:");
    	instruction_2.setFont(font);
    	instruction_2.setForeground(Color.BLACK);
    	
    	JPanel topPanel = new JPanel();
    	topPanel.setLayout(new GridLayout(2, 1, 0, -8));
    	topPanel.setBackground(Color.CYAN);
    	topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
    	topPanel.add(instruction);
    	topPanel.add(instruction_2);
    	
    	JLabel p1Label = new JLabel(match.getP1().getFirstName() + " " + match.getP1().getLastName());
    	p1Label.setFont(font);
    	p1Label.setForeground(Color.BLACK);
    	p1TextField = new JTextField(2);
    	p1TextField.setFont(font);
    	p1TextField.setForeground(Color.BLACK);
    	
    	JPanel p1Panel = new JPanel();
    	p1Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	p1Panel.add(p1Label);
    	p1Panel.add(p1TextField);
    	
    	JLabel p2Label = new JLabel(match.getP2().getFirstName() + " " + match.getP2().getLastName());
    	p2Label.setFont(font);
    	p2Label.setForeground(Color.BLACK);
    	p2TextField = new JTextField(2);
    	p2TextField.setFont(font);
    	p2TextField.setForeground(Color.BLACK);
    	
    	JPanel p2Panel = new JPanel();
    	p2Panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	p2Panel.add(p2Label);
    	p2Panel.add(p2TextField);
    	
    	forfeitButton = new JButton("Forfeit");
    	forfeitButton.setFont(font);
    	forfeitButton.setForeground(Color.BLACK);
    	forfeitButton.addActionListener(this);
    	splitButton = new JButton("Split");
    	splitButton.setFont(font);
    	splitButton.setForeground(Color.BLACK);
    	splitButton.addActionListener(this);
    	splitButton.setEnabled(false);
    	if(match.isFinalMatch()){
    		splitButton.setEnabled(true);
    	}
    	
    	JPanel topButtonPanel = new JPanel();
    	topButtonPanel.setLayout(new GridLayout(1,2));
    	topButtonPanel.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, Color.RED));
    	topButtonPanel.add(forfeitButton);
    	topButtonPanel.add(splitButton);
    	
    	okButton = new JButton("OK");
    	okButton.setFont(font);
    	okButton.setForeground(Color.BLACK);
    	okButton.addActionListener(this);
    	cancelButton = new JButton("Cancel");
    	cancelButton.setFont(font);
    	cancelButton.setForeground(Color.BLACK);
    	cancelButton.addActionListener(this);
    	
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setLayout(new GridLayout(1,2));
    	buttonPanel.add(okButton);
    	buttonPanel.add(cancelButton);    	

    	JPanel content = new JPanel();
    	content.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
    	content.setLayout(new GridLayout(5, 1));
    	content.add(topPanel);
    	content.add(p1Panel);
    	content.add(p2Panel);
    	content.add(topButtonPanel);
    	content.add(buttonPanel);
    	
    	setContentPane(content);
    	pack();
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}// ends EndMatchDialog()

	/**
	 * Handles all types of events in which data about the match 
	 * is meant to be input.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(okButton)) {
			try {
				
				int p1Score = Integer.parseInt(p1TextField.getText());
				int p2Score = Integer.parseInt(p2TextField.getText());
				
				//Make sure there's no tie.
				if(p1Score == p2Score){
					
					Main.getControlPanel().showErrorMessage("The match cannot end in a tie. Please re-enter both scores.");
					p1TextField.setText("");
					p2TextField.setText("");
					return;
					
				} else{
					
					match.setStarted(true);
					endMatch(p1Score, p2Score);
					
				}
				
			} catch(NumberFormatException e) {
				
				Main.getControlPanel().showErrorMessage("Please make sure that both scores are an integer number.");
				
			}
			
			dispose();
		} else if(evt.getSource().equals(cancelButton)) {
			dispose();
		} else if(evt.getSource().equals(forfeitButton)) {
			dispose();
			Main.getControlPanel().getScreen().getBracket().showForfeitDialog(match);
		} else if(evt.getSource().equals(splitButton)) {
			dialog = new SplitDialog(match, this);
			
			dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
			dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
	        dialog.setResizable(false);
	        dialog.setVisible(true);
		}
		
		Main.getControlPanel().updateInfo();
		
	}// ends actionPerformed()
	
	/**
	 * Releases the TablePanel associated with this match,
	 * changes the players' handicaps if necessary,
	 * sets up the Submit Button if necessary, 
	 * and triggers the match to handle all necessary data.
	 * 
	 * @param p1Score Player 1's score.
	 * @param p2Score Player 2's score.
	 */
	public void endMatch(int p1Score, int p2Score) {
		
		match.end(p1Score, p2Score);
		
		//Set the TablePanel to vacant and match to finished.
		TablePanel[] tables = Main.getControlPanel().getTables();
		for(int i=0; i<tables.length; i++) {
			if(tables[i].getMatch() != null && tables[i].getMatch().equals(match)) {
				tables[i].setMatch(null);
			}
		}
		
		//Handle changes in players' handicaps.
		if(Main.getControlPanel().handicapsAffected() == 2) {
			DBHSHandicapMutator.process(match);
		}
		
		//Set up submit button if needed.
		if(Main.getControlPanel().getFinalMatch().isFinished()) {
			Main.getControlPanel().initSubmitButton();
		}
		
		//Updates all necessary screens.
		Main.getControlPanel().updateInfo();
		
	}// ends endMatch()
	
}// ends Class
