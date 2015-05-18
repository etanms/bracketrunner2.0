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
public class ForfeitDialog extends JDialog implements ActionListener {
	
	Match match;
	
	JButton p1Button;
	JButton p2Button;
	
	JTextField p1TextField;
	JTextField p2TextField;
	
	public ForfeitDialog(Match match) {
		
		super(Main.getWindow(), "Forfeit", Dialog.ModalityType.TOOLKIT_MODAL);
		
		this.match = match;
		
		Font font = new Font("Arial Black", Font.PLAIN, 13);

		JLabel instructionLabel = new JLabel("Click the button of the player who");
		instructionLabel.setFont(font);
		instructionLabel.setForeground(Color.BLACK);
		
		JLabel instructionLabel_2 = new JLabel("will be moving on to the next round.");
		instructionLabel_2.setFont(font);
		instructionLabel_2.setForeground(Color.BLACK);
		
		JLabel instructionLabel_3 = new JLabel("If there were any games already played,");
		instructionLabel_3.setFont(font);
		instructionLabel_3.setForeground(Color.BLACK);
		
		JLabel instructionLabel_4 = new JLabel("please input the scores in the box below");
		instructionLabel_4.setFont(font);
		instructionLabel_4.setForeground(Color.BLACK);
		
		JLabel instructionLabel_5 = new JLabel("each player's name first.");
		instructionLabel_5.setFont(font);
		instructionLabel_5.setForeground(Color.BLACK);

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		labelPanel.setBackground(Color.CYAN);
		labelPanel.add(instructionLabel);

		JPanel labelPanel_2 = new JPanel();
		labelPanel_2.setLayout(new FlowLayout(FlowLayout.CENTER));
		labelPanel_2.setBackground(Color.CYAN);
		labelPanel_2.add(instructionLabel_2);

		JPanel labelPanel_3 = new JPanel();
		labelPanel_3.setLayout(new FlowLayout(FlowLayout.CENTER));
		labelPanel_3.setBackground(Color.CYAN);
		labelPanel_3.add(instructionLabel_3);

		JPanel labelPanel_4 = new JPanel();
		labelPanel_4.setLayout(new FlowLayout(FlowLayout.CENTER));
		labelPanel_4.setBackground(Color.CYAN);
		labelPanel_4.add(instructionLabel_4);

		JPanel labelPanel_5 = new JPanel();
		labelPanel_5.setLayout(new FlowLayout(FlowLayout.CENTER));
		labelPanel_5.setBackground(Color.CYAN);
		labelPanel_5.add(instructionLabel_5);

		JPanel forfeitInstructionPanel = new JPanel();
		forfeitInstructionPanel.setLayout(new GridLayout(5,1,0,-9));
		forfeitInstructionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		forfeitInstructionPanel.add(labelPanel);
		forfeitInstructionPanel.add(labelPanel_2);
		forfeitInstructionPanel.add(labelPanel_3);
		forfeitInstructionPanel.add(labelPanel_4);
		forfeitInstructionPanel.add(labelPanel_5);
		
		JPanel p1Panel = new JPanel();
		p1Panel.setLayout(new GridLayout(2,1));

		p1Button = new JButton(match.getP1().getFirstName() + " " + match.getP1().getLastName());
		p1Button.setFont(font);
		p1Button.setForeground(Color.BLACK);
		p1Button.addActionListener(this);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(p1Button);
		p1Panel.add(panel);
		
		p1TextField = new JTextField(2);
		p1TextField.setFont(font);
		p1TextField.setForeground(Color.BLACK);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(p1TextField);
		p1Panel.add(panel);
		
		JPanel p2Panel = new JPanel();
		p2Panel.setLayout(new GridLayout(2,1));
		
		p2Button = new JButton(match.getP2().getFirstName() + " " + match.getP2().getLastName());
		p2Button.setFont(font);
		p2Button.setForeground(Color.BLACK);
		p2Button.addActionListener(this);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(p2Button);
		p2Panel.add(panel);
		
		p2TextField = new JTextField(2);
		p2TextField.setFont(font);
		p2TextField.setForeground(Color.BLACK);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(p2TextField);
		p2Panel.add(panel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(p1Panel);
		buttonPanel.add(p2Panel);

		
		JPanel content = new JPanel();
		content.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		content.setLayout(new GridLayout(2,1));
		content.add(forfeitInstructionPanel);
		content.add(buttonPanel);

		setContentPane(content);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		
	}// ends ForfeitDialog()

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		int p1Score;
		int p2Score;
		
		if(!p1TextField.getText().equals("")) {
			try{
				p1Score = Integer.parseInt(p1TextField.getText());
			}
			catch(NumberFormatException e) {
				Main.getControlPanel().showErrorMessage("Please make sure that all input is an integer number.");
				return;
			}
		} else if(match.getP1Elo() < match.getP2Elo()) {
			p1Score = match.getSpot();
		} else {
			p1Score = 0;
		}
		
		if(!p2TextField.getText().equals("")) {
			try{
				p2Score = Integer.parseInt(p2TextField.getText());
			}
			catch(NumberFormatException e) {
				Main.getControlPanel().showErrorMessage("Please make sure that all input is an integer number.");
				return;
			}
		} else if(match.getP2Elo() < match.getP1Elo()) {
			p2Score = match.getSpot();
		} else {
			p2Score = 0;
		}
		
		if(evt.getSource().equals(p1Button)) {
			match.setWinner(match.getP1());
			match.setLoser(match.getP2());
		} else if(evt.getSource().equals(p2Button)){
			match.setWinner(match.getP2());
			match.setLoser(match.getP1());
		}
		
		//Handle changes in players' handicaps.
		if(Main.getControlPanel().handicapsAffected() == 2) {
			DBHSHandicapMutator.process(match);
		}
		
		match.setForfeit(true);
		match.end(p1Score, p2Score);
		
		//Set the TablePanel to vacant and match to finished.
		TablePanel[] tables = Main.getControlPanel().getTables();
		for(int i=0; i<tables.length; i++) {
			if(tables[i].getMatch() != null && tables[i].getMatch().equals(match)) {
				tables[i].setMatch(null);
			}
		}
		
		//Set up submit button if needed.
		if(Main.getControlPanel().getFinalMatch().isFinished()) {
			Main.getControlPanel().initSubmitButton();
		}
		
		//Updates all necessary screens.
		Main.getControlPanel().updateInfo();
		
		dispose();
		
		Main.getControlPanel().updateInfo();
		
	}// ends actionPerformed()

}// ends Class
