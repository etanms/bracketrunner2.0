package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import com.etan.widgets.SizableJCheckBox;

/**
 * Panel used to display information about the tournament
 * while the tournament is going on. Also holds some quick
 * controls to set up matches, start the tournament if it 
 * hasn't started yet, and toggle whether players' names
 * are displayed in color or not.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 24, 2014
 */
@SuppressWarnings("serial")
class InfoPanel extends JPanel implements ActionListener {
	
	private JLabel colorLabel;
	private JLabel colorLabel_2;
	private JPanel colorLabelPanel;
	private JCheckBox colorCheckBox;
	private JPanel colorCheckBoxPanel;
	private JButton submitButton;
	private JPanel submitPanel;
	private JLabel playerCountLabel;
	private JPanel playerCountPanel;
	private JLabel stillAcceptedLabel;
	private JLabel stillAcceptedLabel_2;
	private JPanel stillAcceptedPanel;
	private JPanel playerLabelPanel;
	private JButton startButton;
	private JPanel startPanel;
	private NextPanel nextPanel;
	private JLabel zoomLabel;
	private JLabel zoomLabel_2;
	private JPanel zoomLabelPanel;
	private JSlider zoomSlider;
	private JPanel zoomSliderPanel;
	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;

	/**
	 * Constructor
	 */
	public InfoPanel(){
		
		setBackground(Color.CYAN);
    	setLayout(new GridLayout(1,0));
    	
    	Font font = new Font("Arial Black", Font.PLAIN, 18);
    	
    	colorLabel = new JLabel("Show players names in color");
    	colorLabel.setForeground(Color.BLACK);
    	colorLabel.setFont(font);
    	JPanel cPanel = new JPanel();
    	cPanel.setOpaque(false);
    	cPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	cPanel.add(colorLabel);
    	
    	colorLabel_2 = new JLabel("to represent their status");
    	colorLabel_2.setForeground(Color.BLACK);
    	colorLabel_2.setFont(font);
    	JPanel cPanel_2 = new JPanel();
    	cPanel_2.setOpaque(false);
    	cPanel_2.setLayout(new FlowLayout(FlowLayout.CENTER));
    	cPanel_2.add(colorLabel_2);
    	
    	colorLabelPanel = new JPanel();
    	colorLabelPanel.setOpaque(false);
    	colorLabelPanel.setLayout(new GridLayout(2, 1, 0, -12));
        colorLabelPanel.add(cPanel);
        colorLabelPanel.add(cPanel_2);
    	
    	colorCheckBox = new SizableJCheckBox(18);
    	colorCheckBox.setOpaque(false);
		colorCheckBox.addActionListener(this);
    	
    	colorCheckBoxPanel = new JPanel();
    	colorCheckBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	colorCheckBoxPanel.setOpaque(false);
    	colorCheckBoxPanel.add(colorCheckBox);
    	
    	submitButton = new JButton("SUBMIT INFO");
    	submitButton.setForeground(Color.BLACK);
    	submitButton.setFont(font);
    	
    	submitPanel = new JPanel();
    	submitPanel.setOpaque(false);
    	submitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	submitPanel.add(submitButton);
    	
    	playerCountLabel = new JLabel("Players: 0/0");
    	playerCountLabel.setForeground(Color.BLACK);
    	playerCountLabel.setFont(font);
    	
    	playerCountPanel = new JPanel();
    	playerCountPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	playerCountPanel.setOpaque(false);
    	playerCountPanel.add(playerCountLabel);

		stillAcceptedLabel = new JLabel("New entries still accepted: ");
		stillAcceptedLabel.setForeground(Color.BLACK);
		stillAcceptedLabel.setFont(font);
		stillAcceptedLabel_2 = new JLabel("YES");
		stillAcceptedLabel_2.setForeground(Color.GREEN);
		stillAcceptedLabel_2.setFont(font);
		
		
		stillAcceptedPanel = new JPanel();
		stillAcceptedPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		stillAcceptedPanel.setOpaque(false);
		stillAcceptedPanel.add(stillAcceptedLabel);
		stillAcceptedPanel.add(stillAcceptedLabel_2);
    	
    	playerLabelPanel = new JPanel();
    	playerLabelPanel.setOpaque(false);
    	playerLabelPanel.setLayout(new GridLayout(2,1));
    	playerLabelPanel.add(playerCountPanel);
    	playerLabelPanel.add(stillAcceptedPanel);
    	
    	startButton = new JButton("Start Tournament");
    	startButton.setForeground(Color.BLACK);
    	startButton.setFont(font);
    	
    	startPanel = new JPanel();
    	startPanel.setOpaque(false);
    	startPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	startPanel.add(startButton);
    	
    	zoomLabel = new JLabel("Use the slider below to zoom");
    	zoomLabel.setForeground(Color.BLACK);
    	zoomLabel.setFont(font);
    	JPanel zPanel = new JPanel();
    	zPanel.setOpaque(false);
    	zPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	zPanel.add(zoomLabel);
    	
    	zoomLabel_2 = new JLabel("in and out of the brackets.");
    	zoomLabel_2.setForeground(Color.BLACK);
    	zoomLabel_2.setFont(font);
    	JPanel zPanel_2 = new JPanel();
    	zPanel_2.setOpaque(false);
    	zPanel_2.setLayout(new FlowLayout(FlowLayout.CENTER));
    	zPanel_2.add(zoomLabel_2);
    	
    	zoomLabelPanel = new JPanel();
    	zoomLabelPanel.setOpaque(false);
    	zoomLabelPanel.setLayout(new GridLayout(2,1,0,-12));
    	zoomLabelPanel.add(zPanel);
    	zoomLabelPanel.add(zPanel_2);
    	
    	zoomSlider = new JSlider(0,10,0);
    	
    	zoomSliderPanel = new JPanel();
    	zoomSliderPanel.setOpaque(false);
    	zoomSliderPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    	zoomSliderPanel.add(zoomSlider);
    	
    	leftPanel = new JPanel();
    	leftPanel.setOpaque(false);
    	leftPanel.setLayout(new GridLayout(2,1));
    	leftPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,4, Color.RED));
    	leftPanel.add(colorLabelPanel);
    	leftPanel.add(colorCheckBoxPanel);
    	
    	centerPanel = new JPanel();
    	centerPanel.setOpaque(false);
    	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    	centerPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,4, Color.RED));
    	centerPanel.add(playerLabelPanel);
    	centerPanel.add(startPanel);
    	
    	rightPanel = new JPanel();
    	rightPanel.setOpaque(false);
    	rightPanel.setLayout(new GridLayout(2,1));
        
    	add(leftPanel);
    	add(centerPanel);
    	add(rightPanel);
		
	}// ends Constructor

	/**
	 * Handles events from colorCheckBox
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {

		Main.getControlPanel().updateInfo();
				
	}// ends actionPerformed()
	
	/**
	 * @return True if the names should be written in color
	 * representing their status in the tournament.
	 */
	boolean colored() {
		
		return colorCheckBox.isSelected();
		
	}// ends colored();
    
    /**
     * Switches out the SubmitPanel for the NextPanel.
     */
    void initNextPanel() {
    	
    	centerPanel.remove(1);
    	centerPanel.add(nextPanel);
    	validate();
    	repaint();
    	
    }// ends initNextPanel()
    
    /**
     * Switches out the NextPanel for the SubmitPanel.
     */
    void initSubmitButton() {
    	
    	centerPanel.remove(1);
    	centerPanel.add(submitPanel);
    	validate();
    	repaint();
    	
    }// ends initSubmitButton()
    
    void next() {
    	
    	nextPanel.next();
    	
    }// ends next()
	
	/**
	 * Sets an ActionListener to all the buttons, checkboxes, and sliders. This was unable to be done
	 * within the constructor because the constructor for ControlPanel was not yet finished.
	 */
	void postConstruct() {
		
		ControlPanel control = Main.getControlPanel();
		submitButton.addActionListener(control);
		startButton.addActionListener(control);
		
	}// ends postConstruct()
	
	/**
	 * @param enabled Whether or not the submitButton should be enabled.
	 */
	void setSubmitButtonEnabled(boolean enabled) {
		
		submitButton.setEnabled(enabled);
		
	}// ends setSubmitButtonEnabled()
	
	void startTournament() {

		//Set up nextPanel
    	nextPanel = new NextPanel();
    	nextPanel.setOpaque(false);
		
		// Remove submitPanel from the centerPanel
		// and add the playerCountPanel and nextPanel.
		centerPanel.removeAll();
		centerPanel.add(playerLabelPanel);
		centerPanel.add(nextPanel);
		
		// Set listeners for the zoom slider.
		zoomSlider.addChangeListener(Main.getControlPanel().getScreen().getBracket());
		if(Main.getControlPanel().getFormat().equals("Double Elimination")
				|| Main.getControlPanel().getFormat().equals("Single Modified")) {
			zoomSlider.addChangeListener(Main.getControlPanel().getScreen().getWinners());
			zoomSlider.addChangeListener(Main.getControlPanel().getScreen().getLosers());
		}

		// Add zoom panels to the right panel.
    	rightPanel.add(zoomLabelPanel);
    	rightPanel.add(zoomSliderPanel);
		
	}// ends startTournament()
	
	/**
	 * Makes sure that the screen is showing current information.
	 */
	void updateInfo() {
		
		if(Main.getControlPanel().hasStarted()) {
			
			// Update playerCountLabel
			playerCountLabel.setText("Players " + Main.getControlPanel().getActivePlayers() + "/" + Main.getControlPanel().getTotalPlayers());
			
			// Update stillAcceptedLabel_2
			String str;
			if(Main.getControlPanel().getScreen().getBracket().morePlayersAllowed()) {
				str = "YES";
				stillAcceptedLabel_2.setForeground(Color.GREEN);
			} else {
				str = "NO";
				stillAcceptedLabel_2.setForeground(Color.RED);
			}
			stillAcceptedLabel_2.setText(str);
			
		}
		
		// Update nextPanel
		if(nextPanel != null) {
			nextPanel.updateInfo();
		}
		
		validate();
		repaint();
		
	}// ends updateInfo()

}// ends Class
