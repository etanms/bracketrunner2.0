package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.etan.dbhs.DBHSDatabaseIntermediary;
import com.etan.widgets.UnderlinedLabel;

/**
 * Panel used to input and show data about the current tournament.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 23, 2014
 */
@SuppressWarnings("serial")
class OptionsPanel extends JPanel implements ActionListener{
	
    private boolean started;
    private int[] prizes;
	
    private JLabel locationLabel;
    private String[] locationsList;
    private JComboBox<String> locationComboBox;
    private JLabel gameLabel;
    private String[] gamesList;
    private JComboBox<String> gameComboBox;
    private JLabel formatLabel;
    private String[] formatList;
    private JComboBox<String> formatComboBox;
    private JLabel raceLabel;
    private String[] raceList;
    private JComboBox<String> raceComboBox;
    private JLabel raceLabel_2;
    private JComboBox<String> raceComboBox_2;
    private JLabel finalsLabel;
    private JComboBox<String> finalsComboBox;
    private JLabel matchLabel;
    private JLabel finalsRaceLabel;
    private JComboBox<String> finalsRaceComboBox;
    private JLabel entryLabel;
    private JLabel entryTextFieldLabel;
    private JTextField entryTextField;
    private JLabel moneyWithheldLabel;
    private JLabel moneyWithheldTextFieldLabel;
    private JTextField moneyWithheldTextField;
    private JLabel moneyAddedLabel;
    private JLabel moneyAddedTextFieldLabel;
    private JTextField moneyAddedTextField;
    private JLabel affectedLabel;
    private String[] affectedOptions;
    private JComboBox<String> affectedComboBox;
    private int[] placesPaid;
    private Date date;
    
    private JLabel locationLabel_2;
    private JLabel gameLabel_2;
    private JLabel formatLabel_2;
    private JLabel raceLabel_3;
    private JLabel raceLabel_4;
    private JLabel finalsInfoLabel;
    private JLabel entryLabel_2;
    private JLabel moneyWithheldLabel_2;
    private JLabel moneyAddedLabel_2;
    private JLabel affectedLabel_2;
    
    private JPanel titlePanel;
    private JPanel locationPanel;
    private JPanel locationPanel_left;
    private JPanel locationPanel_right;
    private JPanel gamePanel;
    private JPanel gamePanel_left;
    private JPanel gamePanel_right;
    private JPanel formatPanel;
    private JPanel formatPanel_left;
    private JPanel formatPanel_right;
    private JPanel racePanel;
    private JPanel racePanel_left;
    private JPanel racePanel_right;
    private JPanel racePanel_2;
    private JPanel racePanel_left_2;
    private JPanel racePanel_right_2;
    private JPanel finalsPanel;
    private JPanel finalsPanel_left;
    private JPanel finalsPanel_right;
    private JPanel affectedPanel;
    private JPanel affectedPanel_left;
    private JPanel affectedPanel_right;
    private JPanel entryPanel;
    private JPanel entryPanel_left;
    private JPanel entryPanel_right;
    private JPanel moneyWithheldPanel;
    private JPanel moneyWithheldPanel_left;
    private JPanel moneyWithheldPanel_right;
    private JPanel moneyAddedPanel;
    private JPanel moneyAddedPanel_left;
    private JPanel moneyAddedPanel_right;
    private JPanel prizesPanel;

	/**
	 * Constructor
	 */
	public OptionsPanel() {
		
		date = new Date(System.currentTimeMillis());
		
		locationsList = DBHSDatabaseIntermediary.getLocations();
		
		/*try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            
            String sql = "SELECT count(`id`) from `locations`;";
            ResultSet rs = stmt.executeQuery(sql);
            
            if(rs.first()) {
            	locationsList = new String[rs.getInt(1)];
            	
            	sql = "SELECT * from `locations`;";
                rs = stmt.executeQuery(sql);
                
                if(rs.first()) {
                	
                	int i=1;
                	
                	locationsList[0] = rs.getString("name");
                	while(rs.next()) {
                		locationsList[i] = rs.getString("name");
                		i++;
                	}
                	
                }
            }
		} catch(ClassNotFoundException e) {
			locationsList = new String[]{"Other"};
		} catch(SQLException e) {
			locationsList = new String[]{"Other"};
		}*/
		
		JLabel label = new JLabel("TOURNAMENT DETAILS");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial Black", Font.BOLD, 36));
		
		Font font = new Font("Arial Black", Font.PLAIN, 24);
		
		titlePanel = new JPanel();
		titlePanel.setBackground(Color.CYAN);
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		titlePanel.add(label);
		
		locationLabel = new JLabel("LOCATION: ");
		locationLabel .setForeground(Color.BLACK);
		locationLabel.setFont(font);
		
		locationComboBox = new JComboBox<String>(locationsList);
		locationComboBox.setForeground(Color.BLACK);
		locationComboBox.setFont(font);
		locationComboBox.addActionListener(this);
		
		locationPanel_left = new JPanel();
		locationPanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 8));
		locationPanel_left.add(locationLabel);
		
		locationPanel_right = new JPanel();
		locationPanel_right.setLayout(new FlowLayout(FlowLayout.LEFT));
		locationPanel_right.add(locationComboBox);
		
		locationPanel = new JPanel();
		locationPanel.setLayout(new GridLayout(1,2));
	    locationPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		locationPanel.add(locationPanel_left);
		locationPanel.add(locationPanel_right);
		
		gameLabel = new JLabel("GAME: ");
		gameLabel.setForeground(Color.BLACK);
		gameLabel.setFont(font);
		
		gamesList = new String[]{"8-Ball", "9-Ball"};
		gameComboBox = new JComboBox<String>(gamesList);
		gameComboBox.setForeground(Color.BLACK);
		gameComboBox.setFont(font);
		
		gamePanel_left = new JPanel();
		gamePanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 8));
		gamePanel_left.add(gameLabel);
		
		gamePanel_right = new JPanel();
		gamePanel_right.setLayout(new FlowLayout(FlowLayout.LEFT));
		gamePanel_right.add(gameComboBox);
		
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(1,2));
	    gamePanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		gamePanel.add(gamePanel_left);
		gamePanel.add(gamePanel_right);
		
		affectedLabel = new JLabel("HANDICAPS ADJUSTED: ");
		affectedLabel.setForeground(Color.BLACK);
		affectedLabel.setFont(font);
		
		affectedOptions = new String[] {"Never", "After the tournament", "After each match"};
		affectedComboBox = new JComboBox<String>(affectedOptions);
		affectedComboBox.setForeground(Color.BLACK);
		affectedComboBox.setFont(font);
		
		affectedPanel_left = new JPanel();
		affectedPanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 8));
		affectedPanel_left.add(affectedLabel);
		
		affectedPanel_right = new JPanel();
		affectedPanel_right.setLayout(new FlowLayout(FlowLayout.LEFT));
		affectedPanel_right.add(affectedComboBox);
		
		affectedPanel = new JPanel();
		affectedPanel.setLayout(new GridLayout(1,2));
	    affectedPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
	    affectedPanel.add(affectedPanel_left);
	    affectedPanel.add(affectedPanel_right);
		
		formatLabel = new JLabel("FORMAT: ");
		formatLabel.setForeground(Color.BLACK);
		formatLabel.setFont(font);
		
		formatList = new String[]{"Single Elimination", "Single Modified", "Double Elimination"};
		formatComboBox = new JComboBox<String>(formatList);
		formatComboBox.setForeground(Color.BLACK);
		formatComboBox.setFont(font);
		formatComboBox.addActionListener(this);
		
		formatPanel_left = new JPanel();
		formatPanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 8));
		formatPanel_left.add(formatLabel);
		
		formatPanel_right = new JPanel();
		formatPanel_right.setLayout(new FlowLayout(FlowLayout.LEFT));
		formatPanel_right.add(formatComboBox);
		
		formatPanel = new JPanel();
		formatPanel.setLayout(new GridLayout(1,2));
	    formatPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		formatPanel.add(formatPanel_left);
		formatPanel.add(formatPanel_right);
		
		raceLabel = new JLabel("RACE TO: ");
		raceLabel.setForeground(Color.BLACK);
		raceLabel.setFont(font);
		
		raceList = new String[]{"Handicapped", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
		raceComboBox = new JComboBox<String>(raceList);
		raceComboBox.setForeground(Color.BLACK);
		raceComboBox.setFont(font);
		
		racePanel_left = new JPanel();
		racePanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 8));
		racePanel_left.add(raceLabel);
		
		racePanel_right = new JPanel();
		racePanel_right.setLayout(new FlowLayout(FlowLayout.LEFT));
		racePanel_right.add(raceComboBox);
		
		racePanel = new JPanel();
		racePanel.setLayout(new GridLayout(1,2));
	    racePanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		racePanel.add(racePanel_left);
		racePanel.add(racePanel_right);
		
		raceLabel_2 = new JLabel("Losers side race to: ");
		raceLabel_2.setForeground(Color.BLACK);
		raceLabel_2.setFont(font);
		
		raceComboBox_2 = new JComboBox<String>(raceList);
		raceComboBox_2.setForeground(Color.BLACK);
		raceComboBox_2.setFont(font);
		
		racePanel_left_2 = new JPanel();
		racePanel_left_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 8));
		racePanel_left_2.add(raceLabel_2);
		
		racePanel_right_2 = new JPanel();
		racePanel_right_2.setLayout(new FlowLayout(FlowLayout.LEFT));
		racePanel_right_2.add(raceComboBox_2);
		
		racePanel_2 = new JPanel();
		racePanel_2.setLayout(new GridLayout(1,2));
	    racePanel_2.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		racePanel_2.add(racePanel_left_2);
		racePanel_2.add(racePanel_right_2);
		
		finalsLabel = new JLabel("Finals: ");
		finalsLabel.setForeground(Color.BLACK);
		finalsLabel.setFont(font);
		
		finalsComboBox = new JComboBox<String>(new String[]{"1", "2"});
		finalsComboBox.setForeground(Color.BLACK);
		finalsComboBox.setFont(font);
		finalsComboBox.addActionListener(this);
		matchLabel = new JLabel("Match ");
		matchLabel.setForeground(Color.BLACK);
		matchLabel.setFont(font);
		finalsRaceLabel = new JLabel("Race to: ");
		finalsRaceLabel.setForeground(Color.BLACK);
		finalsRaceLabel.setFont(font);
		finalsRaceComboBox = new JComboBox<String>(raceList);
		finalsRaceComboBox.setForeground(Color.BLACK);
		finalsRaceComboBox.setFont(font);
		
		finalsPanel_left = new JPanel();
		finalsPanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 8));
		finalsPanel_left.add(finalsLabel);
		
		finalsPanel_right = new JPanel();
		finalsPanel_right.setLayout(new FlowLayout(FlowLayout.LEFT));
		finalsPanel_right.add(finalsComboBox);
		finalsPanel_right.add(matchLabel);
		finalsPanel_right.add(finalsRaceLabel);
		finalsPanel_right.add(finalsRaceComboBox);
		
		finalsPanel = new JPanel();
		finalsPanel.setLayout(new GridLayout(1,2));
	    finalsPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		finalsPanel.add(finalsPanel_left);
		finalsPanel.add(finalsPanel_right);
		
		entryLabel = new JLabel("ENTRY: ");
		entryLabel.setForeground(Color.BLACK);
		entryLabel.setFont(font);
		
		entryTextFieldLabel = new JLabel("$");
		entryTextFieldLabel.setForeground(Color.BLACK);
		entryTextFieldLabel.setFont(font);
		entryTextField = new JTextField(5);
		entryTextField.setForeground(Color.BLACK);
		entryTextField.setFont(font);
		
		entryPanel_left = new JPanel();
		entryPanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT));
		entryPanel_left.add(entryLabel);
		
		entryPanel_right = new JPanel();
		entryPanel_right.setLayout(new FlowLayout(FlowLayout.LEFT, -1, 4));
		entryPanel_right.add(entryTextFieldLabel);
		entryPanel_right.add(entryTextField);
		
		entryPanel = new JPanel();
		entryPanel.setLayout(new GridLayout(1,2));
	    entryPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		entryPanel.add(entryPanel_left);
		entryPanel.add(entryPanel_right);
		
		moneyWithheldLabel = new JLabel("MONEY WITHHELD: ");
		moneyWithheldLabel.setForeground(Color.BLACK);
		moneyWithheldLabel.setFont(font);
		
		moneyWithheldTextFieldLabel = new JLabel("$");
		moneyWithheldTextFieldLabel.setForeground(Color.BLACK);
		moneyWithheldTextFieldLabel.setFont(font);
		moneyWithheldTextField = new JTextField(5);
		moneyWithheldTextField.setForeground(Color.BLACK);
		moneyWithheldTextField.setFont(font);
		
		moneyWithheldPanel_left = new JPanel();
		moneyWithheldPanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT));
		moneyWithheldPanel_left.add(moneyWithheldLabel);
		
		moneyWithheldPanel_right = new JPanel();
		moneyWithheldPanel_right.setLayout(new FlowLayout(FlowLayout.LEFT, -1, 4));
		moneyWithheldPanel_right.add(moneyWithheldTextFieldLabel);
		moneyWithheldPanel_right.add(moneyWithheldTextField);
		
		moneyWithheldPanel = new JPanel();
		moneyWithheldPanel.setLayout(new GridLayout(1,2));
	    moneyWithheldPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		moneyWithheldPanel.add(moneyWithheldPanel_left);
		moneyWithheldPanel.add(moneyWithheldPanel_right);
		
		moneyAddedLabel = new JLabel("MONEY ADDED: ");
		moneyAddedLabel.setForeground(Color.BLACK);
		moneyAddedLabel.setFont(font);
		
		moneyAddedTextFieldLabel = new JLabel("$");
		moneyAddedTextFieldLabel.setForeground(Color.BLACK);
		moneyAddedTextFieldLabel.setFont(font);
		moneyAddedTextField = new JTextField(5);
		moneyAddedTextField.setForeground(Color.BLACK);
		moneyAddedTextField.setFont(font);
		
		moneyAddedPanel_left = new JPanel();
		moneyAddedPanel_left.setLayout(new FlowLayout(FlowLayout.RIGHT));
		moneyAddedPanel_left.add(moneyAddedLabel);
		
		moneyAddedPanel_right = new JPanel();
		moneyAddedPanel_right.setLayout(new FlowLayout(FlowLayout.LEFT, -1, 4));
		moneyAddedPanel_right.add(moneyAddedTextFieldLabel);
		moneyAddedPanel_right.add(moneyAddedTextField);
		
		moneyAddedPanel = new JPanel();
		moneyAddedPanel.setLayout(new GridLayout(1,2));
	    moneyAddedPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		moneyAddedPanel.add(moneyAddedPanel_left);
		moneyAddedPanel.add(moneyAddedPanel_right);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(titlePanel);
		add(locationPanel);
		add(gamePanel);
		add(affectedPanel);
		add(formatPanel);
		add(racePanel);
		add(entryPanel);
		add(moneyWithheldPanel);
		add(moneyAddedPanel);

	}// ends OptionsPanel()

	/**
	 * Updates the choosable options within JComboBoxes based on
	 * selections of other JComboBoxes.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(locationComboBox)) {
            
            /*String[] names = null;
            
			try {
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
	            stmt = conn.createStatement();
	            
	            String sql = "SELECT `tables` from `locations` WHERE `id` = " + (locationComboBox.getSelectedIndex()) + ";";
	            ResultSet rs = stmt.executeQuery(sql);
	            
	            if(rs.first()) {
	            	JSONParser parser = new JSONParser();
	            	JSONObject obj = (JSONObject) parser.parse(rs.getString(1));
	            	JSONArray array = (JSONArray) obj.get("tables");
	            	names = new String[array.size()];
	            	for(int i=0; i<array.size(); i++) {
	            		names[i] = ((JSONObject) array.get(i)).get("name").toString();
	            	}
	            }
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			Main.getControlPanel().getScreen().getTables().addTables(names);*/
			Main.getControlPanel().getScreen().getTables().addTables(
					DBHSDatabaseIntermediary.getTables(
							locationComboBox.getSelectedIndex()));
		} else if(evt.getSource().equals(formatComboBox)) {
			if(formatComboBox.getSelectedItem().toString().equals("Double Elimination")) {
				raceLabel.setText("Winners side race to: ");
				raceLabel.validate();
				raceLabel.repaint();
				
				removeAll();
				add(titlePanel);
				add(locationPanel);
				add(gamePanel);
				add(affectedPanel);
				add(formatPanel);
				add(racePanel);
				add(racePanel_2);
				add(finalsPanel);
				add(entryPanel);
				add(moneyWithheldPanel);
				add(moneyAddedPanel);
			
				validate();
				repaint();
			} else {
				raceLabel.setText("RACE TO: ");
				raceLabel.validate();
				raceLabel.repaint();
			
				removeAll();
				add(titlePanel);
				add(locationPanel);
				add(gamePanel);
				add(affectedPanel);
				add(formatPanel);
				add(racePanel);
				add(entryPanel);
				add(moneyWithheldPanel);
				add(moneyAddedPanel);
				
				validate();
				repaint();
			}
		} else if(evt.getSource().equals(finalsComboBox)) {
			if(finalsComboBox.getSelectedIndex() == 0){
				matchLabel.setText("Match ");
				finalsPanel_right.removeAll();
				finalsPanel_right.add(finalsComboBox);
				finalsPanel_right.add(matchLabel);
				finalsPanel_right.add(finalsRaceLabel);
				finalsPanel_right.add(finalsRaceComboBox);
			}
			else{
				matchLabel.setText("Matches");
				finalsPanel_right.removeAll();
				finalsPanel_right.add(finalsComboBox);
				finalsPanel_right.add(matchLabel);
			}
			
			finalsPanel_right.validate();
			finalsPanel_right.repaint();
		}
		
	}// ends actionPerformed()
	
	/**
	 * @return Date on which the tournament was started.
	 */
	Date getDate() {
		
		return date;
		
	}// ends getDate()
	
	/**
	 * @return Entry Fee paid by each player. Returns -1 if user input is invalid.
	 */
	int getEntry() {
		
		try {
			if(entryTextField.getText() != "") {
				return Integer.parseInt(entryTextField.getText());
			} else {
				return -1;
			}
		} catch(NumberFormatException e) {
			return -1;
		}
		
	}// ends getEntry()
	
	/**
	 * @return Returns 1 if the finals are a single match. Returns 2 if the finals are a true double elimination.
	 */
	int getFinalsFormat() {
		
		return finalsComboBox.getSelectedIndex() + 1;
		
	}// ends getFinalsFormat()
	
	/**
	 * @return The length of the race of the final match. Returns 0 if using the handicap System.
	 */
	int getFinalsRace() {
		
		return finalsRaceComboBox.getSelectedIndex();
		
	}// ends getFinalsRace()
	
	/**
	 * @return Format of the tournament.
	 */
	String getFormat() {
		
		return formatComboBox.getSelectedItem().toString();
		
	}// ends getFormat()
	
	/**
	 * @return Selected index of formatComboBox of the tournament.
	 */
	int getFormatAsInt() {
		
		return formatComboBox.getSelectedIndex();
		
	}// ends getFormatAsInt()
	
	/**
	 * @return Game being played in the tournament.
	 */
	String getGame() {
		
		return gameComboBox.getSelectedItem().toString();
		
	}// ends getGame()
	
	/**
	 * @return Selected index of gameComboBox.
	 */
	int getGameAsInt() {
		
		return gameComboBox.getSelectedIndex();
		
	}// ends getGameAsInt()
	
	/**
	 * @return The length of the races of all loser's side matches. Returns 0 if using the handicap System.
	 */
	int getLosersRace() {
		
		return raceComboBox_2.getSelectedIndex();
		
	}// ends getLosersRace()
	
	/**
	 * @return The amount of money added to the pot for the tournament. Returns -1 if user input is invalid.
	 */
	int getMoneyAdded() {
		
		try{
			if(moneyAddedTextField.getText() != "") {
				return Integer.parseInt(moneyAddedTextField.getText());
			} else {
				return -1;
			}
		} catch(NumberFormatException e) {
			return -1;
		}
		
	}// ends getMoneyAdded()
	
	/**
	 * @return The green's fee taken out of each player's entry fee. Returns -1 if user input is invalid.
	 */
	int getMoneyWithheld() {
		
		try{
			if(moneyWithheldTextField.getText() != "") {
				return Integer.parseInt(moneyWithheldTextField.getText());
			} else {
				return -1;
			}
		} catch(NumberFormatException e) {
			return -1;
		}
		
	}// ends getMoneyWithheld()
	
	/**
	 * @return An int[] filled with all possible options of
	 * amounts of players to be paid.
	 */
	public int[] getPlacesPaid() {
		
		return placesPaid;
		
	}// ends getPlacesPaid()
	
	/**
	 * @return int[] containing every prize level where 0 is
	 * the first place prize. Does not repeat prizes if multiple
	 * places recieve the same prize.
	 */
	public int[] getPrizes() {
		
		return prizes;
		
	}// ends getPrizes()
	
	/**
	 * @return The length of the races of all winner's side matches. Returns 0 if using the handicap system.
	 */
	int getRace() {
		
		return raceComboBox.getSelectedIndex();
		
	}// ends getRace()
	
	/**
	 * @return Location of the tournament.
	 */
	String getTournamentLocation() {
		
		return locationComboBox.getSelectedItem().toString();
		
	}// ends getLocation()
	
	/**
	 * @return Index selected on the locationComboBox.
	 */
	int getTournamentLocationAsInt() {
		
		return locationComboBox.getSelectedIndex();
		
	}// ends getLocationAsInt()
	
	/**
	 * @return The selected index of the affectedComboBox.
	 */
	int handicapsAreAffected() {
		
		return affectedComboBox.getSelectedIndex();
		
	}// ends handicapsAreAffected()
	
	/**
	 * @return Whether or not the tournament has started.
	 */
	boolean hasStarted() {
		
		return started;
		
	}// ends hasStarted()
	
	/**
	 * Resets the labels to show current prizes.
	 */
	void resetPrizes() {
		
		remove(prizesPanel);
		
		Font font = new Font("Arial Black", Font.PLAIN, 24);
		
		prizesPanel = new JPanel();
		prizesPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		prizesPanel.setLayout(new GridLayout(0,1));
		
		UnderlinedLabel label = new UnderlinedLabel("Prizes");
		label.setFont(font);
		label.setForeground(Color.BLACK);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(label);
		prizesPanel.add(panel);
		
		JLabel numLabel = new JLabel("1.");
		numLabel.setFont(font);
		numLabel.setForeground(Color.BLACK);
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		numPanel.add(numLabel);
		
		JLabel prizeLabel = new JLabel("$" + prizes[0]);
		prizeLabel.setFont(font);
		prizeLabel.setForeground(Color.BLACK);
		JPanel prizePanel = new JPanel();
		prizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		prizePanel.add(prizeLabel);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.add(numPanel);
		panel.add(prizePanel);
		
		prizesPanel.add(panel);
		
		for(int i=1; i<prizes.length; i++) {
			
			String str;
			if(placesPaid[i] == placesPaid[i-1]+1) {
				str = placesPaid[i] + ".";
			} else {
				str = (placesPaid[i-1] + 1) + " - " + placesPaid[i] + ".";
			}
			
			numLabel = new JLabel(str);
			numLabel.setFont(font);
			numLabel.setForeground(Color.BLACK);
			numPanel = new JPanel();
			numPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			numPanel.add(numLabel);
			
			prizeLabel = new JLabel("$" + Integer.toString(prizes[i]));
			prizeLabel.setFont(font);
			prizeLabel.setForeground(Color.BLACK);
			prizePanel = new JPanel();
			prizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			prizePanel.add(prizeLabel);
			
			panel = new JPanel();
			panel.setLayout(new GridLayout(1,2));
			panel.add(numPanel);
			panel.add(prizePanel);
			
			prizesPanel.add(panel);
			
		}
		
		add(prizesPanel);
		
	}// ends resetPrizes()
	
	/**
	 * @param affected The index to set the affectedComboBox to.
	 */
	void setAffected(int affected) {
		
		affectedComboBox.setSelectedIndex(affected);
		
	}// ends setAffected()
	
	/**
	 * @param date The date to set for this tournament.
	 */
	void setDate(Date date) {
		
		this.date = date;
		
	}// ends setDate()
	
	/**
	 * @param entry Integer to set the entry to.
	 */
	void setEntry(int entry) {
		
		entryTextField.setText(Integer.toString(entry));
		
	}// ends setEntry()
	
	/**
	 * @param format The index to set the finalsComboBox to.
	 */
	void setFinalsFormat(int format) {
		
		finalsComboBox.setSelectedIndex(format);
		
	}// ends setFinalsFormat()
	
	/**
	 * @param race The index to set the finalsRaceComboBox to.
	 */
	void setFinalsRace(int race) {
		
		finalsRaceComboBox.setSelectedIndex(race);
		
	}// ends setFinalsRace()
	
	/**
	 * @param format The index to set the formatComboBox to.
	 */
	void setFormat(int format) {
		
		formatComboBox.setSelectedIndex(format);
		
	}// ends setFormat()
	
	/**
	 * @param game The index to set the gameComboBox to.
	 */
	void setGame(int game) {
		
		gameComboBox.setSelectedIndex(game);
		
	}// ends setGame()
	
	/**
	 * @param location The index to set the locationComboBox to.
	 */
	void setLocation(int location) {
		
		locationComboBox.setSelectedIndex(location);
		
	}// ends setLocation()
	
	/**
	 * @param race The index to set the raceComboBox_2 to.
	 */
	void setLosersRace(int race) {
		
		raceComboBox_2.setSelectedIndex(race);
		
	}// ends setLosersRace()
	
	/**
	 * @param added Integer to set the money added to.
	 */
	void setMoneyAdded(int added) {
		
		moneyAddedTextField.setText(Integer.toString(added));
		
	}// ends setMoneyAdded()
	
	/**
	 * @param withheld Integer to set the money withheld to.
	 */
	void setMoneyWithheld(int withheld) {
		
		moneyWithheldTextField.setText(Integer.toString(withheld));
		
	}// ends setMoneyWithheld()
	
	/**
	 * @param prizes int[] containing every prize level where 0 is
	 * the first place prize. Does not repeat prizes if multiple
	 * places recieve the same prize.
	 */
	public void setPrizes(int [] prizes) {
		
		this.prizes = prizes;
		
		placesPaid = null;
		if(getFormat().equals("Single Elimination")) {
			
			int count = 0;
			while(Math.pow(2, count) < Main.getControlPanel().getScreen().getPlayers().getTotalPlayers()){
				count++;
			}
			placesPaid = new int[count+1];
			for(int i=0; i<=count; i++){
				placesPaid[i] = (int)Math.pow(2, i);
			}
			placesPaid[placesPaid.length-1] = Main.getControlPanel().getScreen().getPlayers().getTotalPlayers();
			
		} else if(getFormat().equals("Single Modified")) {
			
			int count = 4;
			while(Math.pow(2, count) < Main.getControlPanel().getScreen().getPlayers().getTotalPlayers()){
				count++;
			}
			if(Main.getControlPanel().getScreen().getPlayers().getTotalPlayers() <= Math.pow(2, count) * 3/4) {
				placesPaid = new int[count+1];
				for(int i=0; i<count; i++){
					placesPaid[i] = (int)Math.pow(2, i);
				}
			} else {
				placesPaid = new int[count+2];
				for(int i=0; i<count; i++){
					placesPaid[i] = (int)Math.pow(2, i);
				}
				placesPaid[count] = (int)Math.pow(2, count) * 3/4;
			}
			placesPaid[placesPaid.length-1] = Main.getControlPanel().getScreen().getPlayers().getTotalPlayers();
			
		} else if(getFormat().equals("Double Elimination")) {
			
			if(Main.getControlPanel().getScreen().getPlayers().getTotalPlayers() <= 5) {
				
				placesPaid = new int[Main.getControlPanel().getScreen().getPlayers().getTotalPlayers()];
				for(int i=0; i<Main.getControlPanel().getScreen().getPlayers().getTotalPlayers(); i++) {
					placesPaid[i] = i+1;
				}
				
			} else {
				
				int count = 5;
				while((Math.pow(2, count/2) + Math.pow(2, (count+1)/2)) / 2 < Main.getControlPanel().getScreen().getPlayers().getTotalPlayers()) {
					count++;
				}
				
				placesPaid = new int[count];
				for(int i=0; i<4; i++) {
					placesPaid[i] = i + 1;
				}
				for(int i=4; i<count; i++) {
					placesPaid[i] = (int) (Math.pow(2, (i+1)/2) + Math.pow(2, (i+2)/2)) / 2;
				}
				if(placesPaid[count - 1] != Main.getControlPanel().getScreen().getPlayers().getTotalPlayers()) {
					placesPaid[count - 1] = Main.getControlPanel().getScreen().getPlayers().getTotalPlayers();
				}
				
			}
			
		}
		
	}// ends setPrizes()
	
	/**
	 * @param race The index to set the raceComboBox to.
	 */
	void setRace(int race) {
		
		raceComboBox.setSelectedIndex(race);
		
	}// ends setRace()
	
	/**
	 * @return True if all necessary information needed
     * to start the tournament is ready to be input.
     * False otherwise.
	 */
	boolean startable() {
		
		if(getEntry() == -1) {
			Main.getControlPanel().showErrorMessage("Please make sure to fill in the entry fee.");
			return false;
		} else if(getMoneyWithheld() == -1) {
			Main.getControlPanel().showErrorMessage("Please make sure to fill in the amount of money withheld from each entry.");
			return false;
		} else if(getMoneyAdded() == -1) {
			Main.getControlPanel().showErrorMessage("Please make sure to fill in the amount of money added to the tournament.");
			return false;
		}
		
		return true;
		
	}// ends startable()
	
	/**
	 * Removes all components used for taking user input, 
	 * and adds a label displaying the information from each 
	 * component in its place. Also displays prize information.
	 */
	void startTournament() {
		
		Font font = new Font("Arial Black", Font.PLAIN, 24);
		
		date = new Date(System.currentTimeMillis());
		
		locationLabel_2 = new JLabel(locationComboBox.getSelectedItem().toString());
		locationLabel_2.setForeground(Color.BLACK);
		locationLabel_2.setFont(font);
		locationPanel_right.remove(locationComboBox);
		locationPanel_right.add(locationLabel_2);
		
		gameLabel_2 = new JLabel(gameComboBox.getSelectedItem().toString());
		gameLabel_2.setForeground(Color.BLACK);
		gameLabel_2.setFont(font);
		gamePanel_right.remove(gameComboBox);
		gamePanel_right.add(gameLabel_2);
		
		affectedLabel_2 = new JLabel(affectedComboBox.getSelectedItem().toString());
		affectedLabel_2.setForeground(Color.BLACK);
		affectedLabel_2.setFont(font);
		affectedPanel_right.remove(affectedComboBox);
		affectedPanel_right.add(affectedLabel_2);
		
		formatLabel_2 = new JLabel(formatComboBox.getSelectedItem().toString());
		formatLabel_2.setForeground(Color.BLACK);
		formatLabel_2.setFont(font);
		formatPanel_right.remove(formatComboBox);
		formatPanel_right.add(formatLabel_2);
		
		raceLabel_3 = new JLabel(raceComboBox.getSelectedItem().toString());
		raceLabel_3.setForeground(Color.BLACK);
		raceLabel_3.setFont(font);
		racePanel_right.remove(raceComboBox);
		racePanel_right.add(raceLabel_3);
		
		if(getFormat().equals("Double Elimination")) {
			
			raceLabel_4 = new JLabel(raceComboBox_2.getSelectedItem().toString());
			raceLabel_4.setForeground(Color.BLACK);
			raceLabel_4.setFont(font);
			racePanel_right_2.remove(raceComboBox_2);
			racePanel_right_2.add(raceLabel_4);
			
			String str = finalsComboBox.getSelectedItem().toString() + " Match";
			if(finalsComboBox.getSelectedIndex() == 0) {
				str.concat("  Race to: " + finalsRaceComboBox.getSelectedItem().toString());
			}
			finalsInfoLabel = new JLabel(str);
			finalsInfoLabel.setForeground(Color.BLACK);
			finalsInfoLabel.setFont(font);
			finalsPanel_right.removeAll();
			finalsPanel_right.add(finalsInfoLabel);
			
		}
		
		entryLabel_2 = new JLabel("$" + entryTextField.getText());
		entryLabel_2.setForeground(Color.BLACK);
		entryLabel_2.setFont(font);
		entryPanel_right.removeAll();
		entryPanel_right.add(entryLabel_2);
		
		moneyWithheldLabel_2 = new JLabel("$" + moneyWithheldTextField.getText());
		moneyWithheldLabel_2.setForeground(Color.BLACK);
		moneyWithheldLabel_2.setFont(font);
		moneyWithheldPanel_right.removeAll();
		moneyWithheldPanel_right.add(moneyWithheldLabel_2);
		
		moneyAddedLabel_2 = new JLabel("$" + moneyAddedTextField.getText());
		moneyAddedLabel_2.setForeground(Color.BLACK);
		moneyAddedLabel_2.setFont(font);
		moneyAddedPanel_right.removeAll();
		moneyAddedPanel_right.add(moneyAddedLabel_2);
		
		// Add prizes
		prizesPanel = new JPanel();
		prizesPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		prizesPanel.setLayout(new GridLayout(0,1));
		
		UnderlinedLabel label = new UnderlinedLabel("Prizes");
		label.setFont(font);
		label.setForeground(Color.BLACK);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(label);
		prizesPanel.add(panel);
		
		JLabel numLabel = new JLabel("1.");
		numLabel.setFont(font);
		numLabel.setForeground(Color.BLACK);
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		numPanel.add(numLabel);
		
		JLabel prizeLabel = new JLabel("$" + prizes[0]);
		prizeLabel.setFont(font);
		prizeLabel.setForeground(Color.BLACK);
		JPanel prizePanel = new JPanel();
		prizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		prizePanel.add(prizeLabel);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.add(numPanel);
		panel.add(prizePanel);
		
		prizesPanel.add(panel);
		
		for(int i=1; i<prizes.length; i++) {
			
			String str;
			if(placesPaid[i] == placesPaid[i-1]+1) {
				str = placesPaid[i] + ".";
			} else {
				str = (placesPaid[i-1] + 1) + " - " + placesPaid[i] + ".";
			}
			
			numLabel = new JLabel(str);
			numLabel.setFont(font);
			numLabel.setForeground(Color.BLACK);
			numPanel = new JPanel();
			numPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			numPanel.add(numLabel);
			
			prizeLabel = new JLabel("$" + Integer.toString(prizes[i]));
			prizeLabel.setFont(font);
			prizeLabel.setForeground(Color.BLACK);
			prizePanel = new JPanel();
			prizePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			prizePanel.add(prizeLabel);
			
			panel = new JPanel();
			panel.setLayout(new GridLayout(1,2));
			panel.add(numPanel);
			panel.add(prizePanel);
			
			prizesPanel.add(panel);
			
		}
		
		add(prizesPanel);
		
		started = true;
		
	}// ends startTournament()
	
	/**
	 * Makes sure that the screen is showing current information.
	 */
	void updateInfo() {
		
		validate();
		repaint();
		
	}// ends updateInfo()

}// ends Class
