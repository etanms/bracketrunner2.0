package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel to hold the buttons used to switch between the
 * different screens.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 24, 2014
 */
@SuppressWarnings("serial")
class ButtonPanel extends JPanel{
	
	private JButton optionsButton;
	private JButton tablesButton;
	private JButton playersButton;
	private JButton matchesButton;
	private JButton bracketButton;
	private JButton winnersButton;
	private JButton losersButton;

	/**
	 * Constructor
	 */
	public ButtonPanel() {
		
	    setBackground(Color.LIGHT_GRAY);
	    setLayout(new GridLayout(1,7));
	    
	    Font font = new Font("Arial Black", Font.PLAIN, 18);
		
		optionsButton = new JButton("Details");
		optionsButton.setForeground(Color.BLACK);
		optionsButton.setFont(font);
	    add(optionsButton);
	    
	    tablesButton = new JButton("Tables");
		tablesButton.setForeground(Color.BLACK);
		tablesButton.setFont(font);
	    add(tablesButton);

	    playersButton = new JButton("Players");
		playersButton.setForeground(Color.BLACK);
		playersButton.setFont(font);
	    add(playersButton);
	    
	    matchesButton = new JButton("Matches");
		matchesButton.setForeground(Color.BLACK);
		matchesButton.setFont(font);
	    matchesButton.setEnabled(false);
	    add(matchesButton);

	    bracketButton = new JButton("Bracket");
		bracketButton.setForeground(Color.BLACK);
		bracketButton.setFont(font);
	    bracketButton.setEnabled(false);
	    add(bracketButton);

	    winnersButton = new JButton("Winners");
		winnersButton.setForeground(Color.BLACK);
		winnersButton.setFont(font);
	    winnersButton.setEnabled(false);
	    add(winnersButton);

	    losersButton = new JButton("Losers");
		losersButton.setForeground(Color.BLACK);
		losersButton.setFont(font);
	    losersButton.setEnabled(false);
	    add(losersButton);
	    
	}// ends ButtonPanel()
	
	/**
	 * Sets an ActionListener to all the buttons. This was unable to be done
	 * within the constructor because the constructor for ControlPanel was
	 * not yet finished.
	 */
	void postConstruct() {
		
		ScreenPanel screen = Main.getControlPanel().getScreen();
		
	    optionsButton.addActionListener(screen);
	    tablesButton.addActionListener(screen);
	    playersButton.addActionListener(screen);
	    matchesButton.addActionListener(screen);
	    bracketButton.addActionListener(screen);
	    winnersButton.addActionListener(screen);
	    losersButton.addActionListener(screen);
		
	}// ends postConstruct()
	
	/**
	 * Enables the matches button, bracket button,
	 * and winners or losers buttons if needed.
	 */
	void startTournament() {
		
		matchesButton.setEnabled(true);
		bracketButton.setEnabled(true);
		if(Main.getControlPanel().getFormat().equals("Double Elimination")
				|| Main.getControlPanel().getFormat().equals("Single Modified")) {
			winnersButton.setEnabled(true);
			losersButton.setEnabled(true);
		}
		
		validate();
		repaint();
		
	}// ends startTournament()

}// ends Class
