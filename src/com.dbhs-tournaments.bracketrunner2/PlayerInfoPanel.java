package com.etan.bracketrunner2;

import java.awt.*;

import javax.swing.*;

/**
 * Panel used to show statistics about an individual 
 * player once the tournament has started.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 30, 2014
 *
 */
@SuppressWarnings("serial")
public class PlayerInfoPanel extends IndividualPlayerPanel{
	
	private Player player;
	
	private JPanel inOutPanel;
	private JPanel firstNamePanel;
	private JPanel lastNamePanel;
	private JPanel idNumPanel;
	private JPanel handicapPanel;
	private JPanel matchesWonPanel;
	private JPanel matchesPlayedPanel;
	private JPanel gamesWonPanel;
	private JPanel gamesPlayedPanel;
	private JPanel winPercentagePanel;
	private JPanel moneyPanel;
	
	private JLabel inOutLabel;
	private JLabel matchLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel idNumLabel;
	private JLabel handicapLabel;
	private JLabel matchesWonLabel;
	private JLabel matchesPlayedLabel;
	private JLabel gamesWonLabel;
	private JLabel gamesPlayedLabel;
	private JLabel winPercentageLabel;
	private JLabel moneyLabel;

	public PlayerInfoPanel(Player player){
		
		super(player);
		this.player = player;
		
		setLayout(new GridLayout(1,11));
		
		Font font = new Font("Arial Black", Font.PLAIN, 13);
		
		inOutLabel = new JLabel("IN");
		inOutLabel.setForeground(Color.GREEN);
		inOutLabel.setFont(font);
		if(player.getCurrentMatch() != null){
			matchLabel = new JLabel(" -  " + player.getCurrentMatch().getName());
		}
		else{
			matchLabel = new JLabel();
		}
		matchLabel.setForeground(Color.BLACK);
		matchLabel.setFont(font);
		inOutPanel = new JPanel();
		inOutPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		inOutPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		inOutPanel.add(inOutLabel);
		inOutPanel.add(matchLabel);
		
		String str = player.getFirstName();
		firstNameLabel = new JLabel(str);
		firstNameLabel.setFont(font);
		firstNameLabel.setFont(Main.getControlPanel().scaleFont(str, (Main.getWindow().getWidth()-Main.getWindow().getInsets().left-Main.getWindow().getInsets().right)/11, firstNameLabel));
		firstNameLabel.setForeground(Color.BLACK);
		firstNameLabel.setFont(font);
		firstNamePanel = new JPanel();
		firstNamePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		firstNamePanel.add(firstNameLabel);
		
		str = player.getLastName();
		lastNameLabel = new JLabel(str);
		lastNameLabel.setFont(font);
		lastNameLabel.setFont(Main.getControlPanel().scaleFont(str, (Main.getWindow().getWidth()-Main.getWindow().getInsets().left-Main.getWindow().getInsets().right)/11, lastNameLabel));
		lastNameLabel.setForeground(Color.BLACK);
		lastNameLabel.setFont(font);
		lastNamePanel = new JPanel();
		lastNamePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		lastNamePanel.add(lastNameLabel);
		
		idNumLabel = new JLabel(Integer.toString(player.getId()));
		idNumLabel.setForeground(Color.BLACK);
		idNumLabel.setFont(font);
		idNumPanel = new JPanel();
		idNumPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		idNumPanel.add(idNumLabel);
		
		handicapLabel = new JLabel(Integer.toString((int)player.getElo()));
		handicapLabel.setForeground(Color.BLACK);
		handicapLabel.setFont(font);
		handicapPanel = new JPanel();
		handicapPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		handicapPanel.add(handicapLabel);
		
		matchesWonLabel = new JLabel(Integer.toString(player.getMatchesWon()));
		matchesWonLabel.setForeground(Color.BLACK);
		matchesWonLabel.setFont(font);
		matchesWonPanel = new JPanel();
		matchesWonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		matchesWonPanel.add(matchesWonLabel);
		
		matchesPlayedLabel = new JLabel(Integer.toString(player.getTotalMatches()));
		matchesPlayedLabel.setForeground(Color.BLACK);
		matchesPlayedLabel.setFont(font);
		matchesPlayedPanel = new JPanel();
		matchesPlayedPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		matchesPlayedPanel.add(matchesPlayedLabel);
		
		gamesWonLabel = new JLabel(Integer.toString(player.getGamesWon()));
		gamesWonLabel.setForeground(Color.BLACK);
		gamesWonLabel.setFont(font);
		gamesWonPanel = new JPanel();
		gamesWonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		gamesWonPanel.add(gamesWonLabel);
		
		gamesPlayedLabel = new JLabel(Integer.toString(player.getTotalGames()));
		gamesPlayedLabel.setForeground(Color.BLACK);
		gamesPlayedLabel.setFont(font);
		gamesPlayedPanel = new JPanel();
		gamesPlayedPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		gamesPlayedPanel.add(gamesPlayedLabel);
		
		winPercentageLabel = new JLabel("0%");
		winPercentageLabel.setForeground(Color.BLACK);
		winPercentageLabel.setFont(font);
		winPercentagePanel = new JPanel();
		winPercentagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 4, Color.RED));
		winPercentagePanel.add(winPercentageLabel);
		
		moneyLabel = new JLabel("$" + Integer.toString(player.getMoney()));
		moneyLabel.setForeground(Color.BLACK);
		moneyLabel.setFont(font);
		moneyPanel = new JPanel();
		moneyPanel.add(moneyLabel);
		
		add(inOutPanel);
		add(firstNamePanel);
		add(lastNamePanel);
		add(idNumPanel);
		add(handicapPanel);
		add(matchesWonPanel);
		add(matchesPlayedPanel);
		add(gamesWonPanel);
		add(gamesPlayedPanel);
		add(winPercentagePanel);
		add(moneyPanel);
		
	} // end constructor
	
	/**
	 * This method should only be used when loading 
	 * a save file.
	 * 
	 * @param player The player who's stats should be shown on 
	 * this panel. 
	 */
	void setPlayer(Player player){
		
		this.player = player;
		
	}//ends setPlayer()
	
	/**
	 * Sets all labels to show current information about the player.
	 */
	@Override
	void updateInfo(){
		
		handicapLabel.setText(Integer.toString((int)player.getElo()));
		matchesWonLabel.setText(Integer.toString(player.getMatchesWon()));
		matchesPlayedLabel.setText(Integer.toString(player.getTotalMatches()));
		gamesWonLabel.setText(Integer.toString(player.getGamesWon()));
		gamesPlayedLabel.setText(Integer.toString(player.getTotalGames()));
		if(player.getTotalGames() != 0){
		    winPercentageLabel.setText(Integer.toString((player.getGamesWon() * 100) / player.getTotalGames()) + "%");
		}
		else{
			winPercentageLabel.setText(Integer.toString(0) + "%");
		}
		moneyLabel.setText("$" + Integer.toString(player.getMoney()));
		if(player.getCurrentMatch() != null) {
			matchLabel.setText(" - " + player.getCurrentMatch().getName());
		}
		
		if(Main.getControlPanel().getFormat().equals("Single Elimination")
				|| Main.getControlPanel().getFormat().equals("Single Modified")){
			if(!player.isActive()){
				inOutLabel.setText("OUT");
				inOutLabel.setForeground(Color.RED);
			}
			else{
				inOutLabel.setText("IN");
				inOutLabel.setForeground(Color.GREEN);
			}
		}
		else if(Main.getControlPanel().getFormat().equals("Double Elimination")) {
			if(player.isActive() && (player.getTotalMatches() - player.getMatchesWon()) == 1){
				inOutLabel.setText("1L");
				inOutLabel.setForeground(new Color(218, 196, 5));
			}
			else if(!player.isActive()){
				inOutLabel.setText("OUT");
				inOutLabel.setForeground(Color.RED);
			}
			else{
				inOutLabel.setText("IN");
				inOutLabel.setForeground(Color.GREEN);
			}
		}
		
		validate();
		repaint();
		
	} // end updateInfo()
	
} // end class PlayerInfoPanel
