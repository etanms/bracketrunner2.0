package com.etan.bracketrunner2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
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

import com.etan.dbhs.DBHSPlayerInitializer;
import com.etan.widgets.UnderlinedLabel;

@SuppressWarnings("serial")
public class NewPlayerDialog extends JDialog implements ActionListener {
	
	private JTextField fnTextField;
	private JTextField lnTextField;
	private JTextField idTextField;
	private JTextField handicapTextField;
	
	private JButton okButton;
	private JButton cancelButton;
	
	/**
	 * Constructor
	 */
	public NewPlayerDialog() {
		
		super(Main.getWindow(), "New Player", Dialog.ModalityType.TOOLKIT_MODAL);
		
		Font font = new Font("Arial Black", Font.PLAIN, 14);
		
		JLabel fnLabel = new UnderlinedLabel("First Name");
		fnLabel.setForeground(Color.BLACK);
		fnLabel.setFont(font);
		JPanel fnLabelPanel = new JPanel();
		//fnLabelPanel.setBackground(Color.CYAN);
		fnLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		fnLabelPanel.add(fnLabel);
		
		JLabel lnLabel = new UnderlinedLabel("Last Name");
		lnLabel.setForeground(Color.BLACK);
		lnLabel.setFont(font);
		JPanel lnLabelPanel = new JPanel();
		//lnLabelPanel.setBackground(Color.CYAN);
		lnLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		lnLabelPanel.add(lnLabel);
		
		JLabel idLabel = new UnderlinedLabel("ID#");
		idLabel.setForeground(Color.BLACK);
		idLabel.setFont(font);
		JPanel idLabelPanel = new JPanel();
		//idLabelPanel.setBackground(Color.CYAN);
		idLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		idLabelPanel.add(idLabel);
		
		JLabel handicapLabel = new UnderlinedLabel("Handicap");
		handicapLabel.setForeground(Color.BLACK);
		handicapLabel.setFont(font);
		JPanel handicapLabelPanel = new JPanel();
		//handicapLabelPanel.setBackground(Color.CYAN);
		handicapLabelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		handicapLabelPanel.add(handicapLabel);
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 4));
		labelPanel.add(fnLabelPanel);
		labelPanel.add(lnLabelPanel);
		labelPanel.add(idLabelPanel);
		labelPanel.add(handicapLabelPanel);

		fnTextField = new JTextField(7);
		fnTextField.setForeground(Color.BLACK);
		fnTextField.setFont(font);
		
		lnTextField = new JTextField(7);
		lnTextField.setForeground(Color.BLACK);
		lnTextField.setFont(font);
		
		idTextField = new JTextField(3);
		idTextField.setForeground(Color.BLACK);
		idTextField.setFont(font);
		
		handicapTextField = new JTextField(3);
		handicapTextField.setForeground(Color.BLACK);
		handicapTextField.setFont(font);

		JPanel fnTextFieldPanel = new JPanel();
		fnTextFieldPanel.add(fnTextField);

		JPanel lnTextFieldPanel = new JPanel();
		lnTextFieldPanel.add(lnTextField);

		JPanel idTextFieldPanel = new JPanel();
		idTextFieldPanel.add(idTextField);

		JPanel handicapTextFieldPanel = new JPanel();
		handicapTextFieldPanel.add(handicapTextField);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(1, 4));
		infoPanel.add(fnTextFieldPanel);
		infoPanel.add(lnTextFieldPanel);
		infoPanel.add(idTextFieldPanel);
		infoPanel.add(handicapTextFieldPanel);
		
		okButton = new JButton("OK");
		okButton.setForeground(Color.BLACK);
		okButton.setFont(font);
		okButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setFont(font);
		cancelButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, Color.RED));
		buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		JPanel content = new JPanel();
		content.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		content.setLayout(new BorderLayout());
		content.add(labelPanel, BorderLayout.NORTH);
		content.add(infoPanel, BorderLayout.CENTER);
		content.add(buttonPanel, BorderLayout.SOUTH);
		
		setPreferredSize(new Dimension(500, 200));
		setContentPane(content);
		pack();
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}// ends NewPlayerDialog()

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(okButton)) {
			
			if(checkInfo()) {
				DBHSPlayerInitializer init = new DBHSPlayerInitializer();
				int id = 0;
				float handicap = 0;
				try {
					if(!idTextField.getText().equals("")) {
						id = Integer.parseInt(idTextField.getText());
					}
				}
				catch(NumberFormatException e) {
					Main.getControlPanel().showErrorMessage(
							"Please make sure that all inputs under ID# are integer numbers.");
				}
				
				try {
					if(!handicapTextField.getText().equals("")) {
						handicap = Float.parseFloat(handicapTextField.getText());
					}
				}
				catch(NumberFormatException e) {
					Main.getControlPanel().showErrorMessage(
							"Please make sure that all inputs under handicap are number values.");
				}
				Player player;
				try {
					player = init.initialize(fnTextField.getText(),
							lnTextField.getText(), id, handicap, 0);
					Main.getControlPanel().getScreen().getPlayers().addPlayer(player);
					Main.getControlPanel().showPrizesDialog();
				} catch (Exception e) {
					Main.getControlPanel().showErrorMessage("There was an error while trying to add the new player.");
					e.printStackTrace();
				}
			}
			
			dispose();
			Main.getControlPanel().updateInfo();
			
		} else if(evt.getSource().equals(cancelButton)) {
			
			dispose();
			
		}
		
	}// ends actionPerformed()
	
	boolean checkInfo() {
		
		String fn = fnTextField.getText();
		String ln = lnTextField.getText();
		String id = idTextField.getText();
		String handicap = handicapTextField.getText();
		
		if(id.equals("") && (fn.equals("") && ln.equals(""))) {
			Main.getControlPanel().showErrorMessage("Please make sure that all players have either their first and last names, or their id number filled in.");
			
			return false;
		}
		
		if(fn.equals("") && ln.equals("")) {
			try{
				Integer.parseInt(id);
			}
			catch(NumberFormatException e) {
				if(!fn.equals("") && !ln.equals("")) {
					Main.getControlPanel().showErrorMessage("Please make sure that " + fn + " " + ln + "'s ID is an integer number.");
				} else {
					Main.getControlPanel().showErrorMessage("Please make sure that each player's ID is an integer number.");
				}
				
				return false;
			}
		}
		
		if(!handicap.equals("")) {
			try{
				Float.parseFloat(handicap);
			}
			catch(NumberFormatException e) {
				Main.getControlPanel().showErrorMessage("Please make sure that " + fn + " " + ln + "'s handicap is numeric.");
				
				return false;
			}
		}
		
		return true;
		
	}// ends checkInfo()
	
	/**
	 * Used for an anonymous ActionListener in the 
	 * PlayerPanel.
	 * 
	 * @return cancelButton
	 */
	JButton getCancelButton() {
		
		return cancelButton;
		
	}// ends getCancelButton()
	
	/**
	 * Used for an anonymous ActionListener in the 
	 * PlayerPanel.
	 * 
	 * @return okButton
	 */
	JButton getOkButton() {
		
		return okButton;
		
	}// ends getOkButton()

}// ends Class
