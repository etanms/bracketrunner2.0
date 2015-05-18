package com.etan.bracketrunner2;

import java.awt.BorderLayout;
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

@SuppressWarnings("serial")
public class SplitDialog extends JDialog implements ActionListener {
	
	Match match;
	
	JTextField p1TextField;
	JTextField p2TextField;
	
	JButton okButton;
	JButton cancelButton;
	
	EndMatchDialog parent;
	
	public SplitDialog(Match match, EndMatchDialog dialog) {
		
		super(dialog, "Split", Dialog.ModalityType.TOOLKIT_MODAL);
		
		this.match = match;
		parent = dialog;
		
		Font font = new Font("Arial Black", Font.PLAIN, 13);

		JLabel instructionLabel = new JLabel("Enter the amout of money that each player will");
		instructionLabel.setFont(font);
		instructionLabel.setForeground(Color.BLACK);
		JLabel instructionLabel_2 = new JLabel("receive in the text field next to their names.");
		instructionLabel_2.setFont(font);
		instructionLabel_2.setForeground(Color.BLACK);
		JPanel instructionLabelPanel = new JPanel();
		instructionLabelPanel.setBackground(Color.CYAN);
		instructionLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		instructionLabelPanel.add(instructionLabel);
		JPanel instructionLabelPanel_2 = new JPanel();
		instructionLabelPanel_2.setBackground(Color.CYAN);
		instructionLabelPanel_2.setLayout(new FlowLayout(FlowLayout.CENTER));
		instructionLabelPanel_2.add(instructionLabel_2);

		JPanel instructionPanel = new JPanel();
		instructionPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		instructionPanel.setLayout(new GridLayout(2,1,0,-9));
		instructionPanel.add(instructionLabelPanel);
		instructionPanel.add(instructionLabelPanel_2);

		JLabel p1Label = new JLabel(match.getP1().getFirstName() + " " + match.getP1().getLastName());
		p1Label.setFont(font);
		p1Label.setForeground(Color.BLACK);
		JPanel p1LabelPanel = new JPanel();
		p1LabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p1LabelPanel.add(p1Label);
		
		JLabel p2Label = new JLabel(match.getP2().getFirstName() + " " + match.getP2().getLastName());
		p2Label.setFont(font);
		p2Label.setForeground(Color.BLACK);
		JPanel p2LabelPanel = new JPanel();
		p2LabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p2LabelPanel.add(p2Label);
		
		p1TextField = new JTextField(5);
		p1TextField.setFont(font);
		p1TextField.setForeground(Color.BLACK);
		
		JLabel dollarLabel = new JLabel("$");
		dollarLabel.setFont(font);
		dollarLabel.setForeground(Color.BLACK);
		JPanel p1TextFieldPanel = new JPanel();
		p1TextFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1TextFieldPanel.add(dollarLabel);
		p1TextFieldPanel.add(p1TextField);

		JPanel p1Panel = new JPanel();
		p1Panel.setLayout(new GridLayout(1,2));
		p1Panel.add(p1LabelPanel);
		p1Panel.add(p1TextFieldPanel);
		
		p2TextField = new JTextField(5);
		p2TextField.setFont(font);
		p2TextField.setForeground(Color.BLACK);
		JLabel dollarLabel_2 = new JLabel("$");
		dollarLabel_2.setFont(font);
		dollarLabel_2.setForeground(Color.BLACK);
		JPanel p2TextFieldPanel = new JPanel();
		p2TextFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2TextFieldPanel.add(dollarLabel_2);
		p2TextFieldPanel.add(p2TextField);

		JPanel p2Panel = new JPanel();
		p2Panel.setLayout(new GridLayout(1,2));
		p2Panel.add(p2LabelPanel);
		p2Panel.add(p2TextFieldPanel);
		
		JPanel playersPanel = new JPanel();
		playersPanel.setLayout(new GridLayout(2,1));
		playersPanel.add(p1Panel);
		playersPanel.add(p2Panel);

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
		content.setLayout(new BorderLayout());
		content.add(instructionPanel, BorderLayout.NORTH);
		content.add(playersPanel);
		content.add(buttonPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setContentPane(content);
		pack();
		
	}// ends SplitDialog()

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(okButton)) {
			
			int p1Money;
			int p2Money;
			
			Player p1 = match.getP1();
			Player p2 = match.getP2();
			
			try {
				
				p1Money = Integer.parseInt(p1TextField.getText());
				p2Money = Integer.parseInt(p2TextField.getText());

				int[] prizes = Main.getControlPanel().getPrizes();
				int totalPrize;
				if(prizes.length > 1) {
					
					totalPrize = prizes[0] + prizes[1];
					
				} else {
					
					totalPrize = prizes[0];
					
				}
				
				if(p1Money + p2Money != totalPrize) {
					
					Main.getControlPanel().showErrorMessage(
							"Please make sure that the sum of both players' prize adds up " +
							"to " + totalPrize);
					
				} else {
					
					//Set winner and loser and handle changes in prizes.
					if(p2Money > p1Money) {
						match.setWinner(p2);
						match.setLoser(p1);
						
						if(prizes.length > 2) {
							p1.getMatches().get(p1.getMatches().size()-2).setMoney(p1Money - prizes[2]);
							p2.getMatches().get(p2.getMatches().size()-2).setMoney(p1Money - prizes[2]);
						} else {
							p1.getMatches().get(p1.getMatches().size()-2).setMoney(p1Money);
							p2.getMatches().get(p2.getMatches().size()-2).setMoney(p1Money);
						}
						match.setMoney(p2Money - p1Money);
					} else {
						match.setWinner(p1);
						match.setLoser(p2);
						
						if(prizes.length > 2) {
							p1.getMatches().get(p1.getMatches().size()-2).setMoney(p2Money - prizes[2]);
							p2.getMatches().get(p2.getMatches().size()-2).setMoney(p2Money - prizes[2]);
						} else {
							p1.getMatches().get(p1.getMatches().size()-2).setMoney(p2Money);
							p2.getMatches().get(p2.getMatches().size()-2).setMoney(p2Money);
						}
						match.setMoney(p1Money - p2Money);
					}
					
					match.setSplit(true);
					match.setFinished(true);
					
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
					parent.dispose();
					
				}
				
			}
			catch(NumberFormatException e) {
				
				Main.getControlPanel().showErrorMessage(
						"Please make sure that all input is an integer number.");
				
			}
			
		} else if(evt.getSource().equals(cancelButton)) {
			
			dispose();
			
		}
		
		Main.getControlPanel().updateInfo();
		
	}// ends actionPerformed()

}// ends Class
