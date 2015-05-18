package com.etan.bracketrunner2;

import java.awt.*;

import javax.swing.*;


/**
 * Panel used to input data about a new player.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 29, 2014
 *
 */
@SuppressWarnings("serial")
class NewPlayerPanel extends IndividualPlayerPanel {
	
	private int splitGroup;

	private JButton deleteButton;
	private JButton addButton;
	private JTextField fnTextField;
	private JTextField lnTextField;
	private JTextField idTextField;
	private JTextField handicapTextField;
	private JLabel matchesWonLabel;
	private JLabel matchesPlayedLabel;
	private JLabel gamesWonLabel;
	private JLabel gamesPlayedLabel;
	private JLabel winPercentageLabel;
	private JLabel moneyLabel;

	private JPanel buttonPanel;
	private JPanel fnTextFieldPanel;
	private JPanel lnTextFieldPanel;
	private JPanel idTextFieldPanel;
	private JPanel handicapTextFieldPanel;
	private JPanel matchesWonPanel;
	private JPanel matchesPlayedPanel;
	private JPanel gamesWonPanel;
	private JPanel gamesPlayedPanel;
	private JPanel winPercentagePanel;
	private JPanel moneyPanel;

	public NewPlayerPanel() {

		super(null);
		
		Font font = new Font("Arial Black", Font.PLAIN, 14);

		addButton = new JButton("Add");
		addButton.setForeground(Color.BLACK);
		addButton.setFont(font);
		addButton.setBackground(new Color(244, 234, 150));
		addButton.addActionListener(Main.getControlPanel().getScreen());
		deleteButton = new JButton("X");
		deleteButton.setForeground(Color.RED);
		deleteButton.setBackground(Color.BLACK);
		deleteButton.setFont(font);
		deleteButton.addActionListener(Main.getControlPanel().getScreen().getPlayers());
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
		matchesWonLabel = new JLabel("0");
		matchesWonLabel.setForeground(Color.BLACK);
		matchesWonLabel.setFont(font);
		matchesPlayedLabel = new JLabel("0");
		matchesPlayedLabel.setForeground(Color.BLACK);
		matchesPlayedLabel.setFont(font);
		gamesWonLabel = new JLabel("0");
		gamesWonLabel.setForeground(Color.BLACK);
		gamesWonLabel.setFont(font);
		gamesPlayedLabel = new JLabel("0");
		gamesPlayedLabel.setForeground(Color.BLACK);
		gamesPlayedLabel.setFont(font);
		winPercentageLabel = new JLabel("0%");
		winPercentageLabel.setForeground(Color.BLACK);
		winPercentageLabel.setFont(font);
		moneyLabel = new JLabel("$0");
		moneyLabel.setForeground(Color.BLACK);
		moneyLabel.setFont(font);

		

		buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		buttonPanel.add(deleteButton);

		fnTextFieldPanel = new JPanel();
		fnTextFieldPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		fnTextFieldPanel.add(fnTextField);

		lnTextFieldPanel = new JPanel();
		lnTextFieldPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		lnTextFieldPanel.add(lnTextField);

		idTextFieldPanel = new JPanel();
		idTextFieldPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		idTextFieldPanel.add(idTextField);

		handicapTextFieldPanel = new JPanel();
		handicapTextFieldPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		handicapTextFieldPanel.add(handicapTextField);

		matchesWonPanel = new JPanel();
		matchesWonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		matchesWonPanel.add(matchesWonLabel);

		matchesPlayedPanel = new JPanel();
		matchesPlayedPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		matchesPlayedPanel.add(matchesPlayedLabel);

		gamesWonPanel = new JPanel();
		gamesWonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		gamesWonPanel.add(gamesWonLabel);

		gamesPlayedPanel = new JPanel();
		gamesPlayedPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		gamesPlayedPanel.add(gamesPlayedLabel);

		winPercentagePanel = new JPanel();
		winPercentagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		winPercentagePanel.add(winPercentageLabel);

		moneyPanel = new JPanel();
		moneyPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.RED));
		moneyPanel.add(moneyLabel);
		
		setLayout(new GridLayout(1, 11));
		add(buttonPanel);
		add(fnTextFieldPanel);
		add(lnTextFieldPanel);
		add(idTextFieldPanel);
		add(handicapTextFieldPanel);
		add(matchesWonPanel);
		add(matchesPlayedPanel);
		add(gamesWonPanel);
		add(gamesPlayedPanel);
		add(winPercentagePanel);
		add(moneyPanel);

	} // end constructor

	/**
	 * @return Text from firstNameTextField.
	 */
	String getFirstName() {

		return fnTextField.getText();

	} // end getFirstName()

	/**
	 * @return Text from handicapTextField.
	 */
	String getHandicap() {

		return handicapTextField.getText();

	} // end getHandicap()

	/**
	 * @return Text from idTextField.
	 */
	String getID() {

		return idTextField.getText();

	} // end getID()

	/**
	 * @return Text from lastNameTextField.
	 */
	String getLastName() {

		return lnTextField.getText();

	} // end getLastName()
	
	/**
	 * @return The identifying number of the group of players
	 * to split this player among.
	 */
	int getSplitGroup() {
		
		return splitGroup;
		
	}// ends getSplitGroup()
	
	/**
	 * @param firstName String to set fnTextField text to.
	 */
	void setFirstName(String firstName) {
		
		fnTextField.setText(firstName);
		
	}// ends setFirstName()
	
	/**
	 * @param handicap Float to set handicapTextField text to.
	 */
	void setHandicap(float handicap) {
		
		handicapTextField.setText(Float.toString(handicap));
		
	}// ends setHandicap()
	
	/**
	 * @param id Integer to set idTextField text to.
	 */
	void setId(int id) {
		
		idTextField.setText(Integer.toString(id));
		
	}// ends setId()
	
	/**
	 * @param lastName String to set fnTextField text to.
	 */
	void setLastName(String lastName) {
		
		lnTextField.setText(lastName);
		
	}// ends setLastName()
	
	/**
	 * @param Integer number of the group with which to be split.
	 */
	void setSplitGroup(int group) {
		
		splitGroup = group;
		
	}// ends setSplitGroup()

	/**
	 * Does nothing.
	 */
	@Override
	void updateInfo() {

		validate();
		repaint();

	}// ends update()

} // end class
