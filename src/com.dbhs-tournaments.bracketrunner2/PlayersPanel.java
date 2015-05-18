package com.etan.bracketrunner2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.etan.bracketrunner2.matches.Match;
import com.etan.dbhs.DBHSDatabaseIntermediary;
import com.etan.dbhs.DBHSHandicapMutator;
import com.etan.dbhs.DBHSPlayerInitializer;
import com.etan.widgets.UnderlinedLabel;

/**
 * Screen used to input and show data about the players.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27, 2014
 *
 */
@SuppressWarnings("serial")
class PlayersPanel extends JPanel implements ActionListener, MouseListener {
	
	private Player[] players;
	private int totalPlayers;
	private Object lastClicked;
	
	private UnderlinedLabel statusLabel;
	private UnderlinedLabel fnLabel;
	private UnderlinedLabel lnLabel;
	private UnderlinedLabel idLabel;
	private UnderlinedLabel handicapLabel;
	private UnderlinedLabel matchesWonLabel;
	private UnderlinedLabel matchesLabel;
	private UnderlinedLabel gamesWonLabel;
	private UnderlinedLabel gamesPlayedLabel;
	private UnderlinedLabel winPercentageLabel;
	private UnderlinedLabel moneyLabel;
	
	private JButton splitButton;
	private JButton addPlayerButton;
	
	private ArrayList<String> groups;

	private JLabel numPlayersLabel;
	
	private JPanel buttonPanel;
	private JPanel labelPanel;
	private JPanel mainPanel;
	private JScrollPane scroll;
	
	/**
	 * Constructor
	 */
	public PlayersPanel() {
		
		setLayout(new BorderLayout());
		
		Font font = new Font("Arial Black", Font.PLAIN, 13);

		statusLabel = new UnderlinedLabel("Status");
		statusLabel.setForeground(Color.BLACK);
		statusLabel.setFont(font);
		fnLabel = new UnderlinedLabel("First Name");
		fnLabel.setForeground(Color.BLACK);
		fnLabel.setFont(font);
		fnLabel.addMouseListener(this);
		lnLabel = new UnderlinedLabel("Last Name");
		lnLabel.setForeground(Color.BLACK);
		lnLabel.setFont(font);
		lnLabel.addMouseListener(this);
		idLabel = new UnderlinedLabel("ID#");
		idLabel.setForeground(Color.BLACK);
		idLabel.setFont(font);
		idLabel.addMouseListener(this);
		handicapLabel = new UnderlinedLabel("Handicap");
		handicapLabel.setForeground(Color.BLACK);
		handicapLabel.setFont(font);
		handicapLabel.addMouseListener(this);
		matchesWonLabel = new UnderlinedLabel("Matches Won");
		matchesWonLabel.setForeground(Color.BLACK);
		matchesWonLabel.setFont(font);
		matchesLabel = new UnderlinedLabel("Matches Played");
		matchesLabel.setForeground(Color.BLACK);
		matchesLabel.setFont(font);
		gamesWonLabel = new UnderlinedLabel("Games Won");
		gamesWonLabel.setForeground(Color.BLACK);
		gamesWonLabel.setFont(font);
		gamesPlayedLabel = new UnderlinedLabel("Games Played");
		gamesPlayedLabel.setForeground(Color.BLACK);
		gamesPlayedLabel.setFont(font);
		winPercentageLabel = new UnderlinedLabel("Win Percentage");
		winPercentageLabel.setForeground(Color.BLACK);
		winPercentageLabel.setFont(font);
		moneyLabel = new UnderlinedLabel("Money Won");
		moneyLabel.setForeground(Color.BLACK);
		moneyLabel.setFont(font);
		
		groups = new ArrayList<String>();
		groups.add("Not Split");
		
		splitButton = new JButton("Split Players");
		splitButton.setForeground(Color.BLACK);
		splitButton.setFont(new Font("Arial Black", Font.PLAIN, 18));
		splitButton.addActionListener(this);
		
		JPanel splitPanel = new JPanel();
		splitPanel.setOpaque(false);
		splitPanel.add(splitButton);
		
		addPlayerButton = new JButton("Add Player");
		addPlayerButton.setForeground(Color.BLACK);
		addPlayerButton.setFont(new Font("Arial Black", Font.PLAIN, 18));
		addPlayerButton.addActionListener(this);
		
		numPlayersLabel = new JLabel("Total Players: " + totalPlayers);
		numPlayersLabel.setForeground(Color.BLACK);
		numPlayersLabel.setFont(new Font("Arial Black", Font.PLAIN, 18));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 5));
		buttonPanel.setBackground(Color.CYAN);
		buttonPanel.add(splitPanel);
		buttonPanel.add(addPlayerButton);
		buttonPanel.add(numPlayersLabel);

		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(1, 11));
		labelPanel.setBackground(Color.CYAN);
		
		JPanel statusPanel = new JPanel();
		statusPanel.setOpaque(false);
		statusPanel.add(statusLabel);
		labelPanel.add(statusPanel);
		
		JPanel fnPanel = new JPanel();
		fnPanel.setOpaque(false);
		fnPanel.add(fnLabel);
		labelPanel.add(fnPanel);
		
		JPanel lnPanel = new JPanel();
		lnPanel.setOpaque(false);
		lnPanel.add(lnLabel);
		labelPanel.add(lnPanel);
		
		JPanel idPanel = new JPanel();
		idPanel.setOpaque(false);
		idPanel.add(idLabel);
		labelPanel.add(idPanel);
		
		JPanel handicapPanel = new JPanel();
		handicapPanel.setOpaque(false);
		handicapPanel.add(handicapLabel);
		labelPanel.add(handicapPanel);
		
		JPanel matchesWonPanel = new JPanel();
		matchesWonPanel.setOpaque(false);
		matchesWonPanel.add(matchesWonLabel);
		labelPanel.add(matchesWonPanel);
		
		JPanel matchesPanel = new JPanel();
		matchesPanel.setOpaque(false);
		matchesPanel.add(matchesLabel);
		labelPanel.add(matchesPanel);
		
		JPanel gamesWonPanel = new JPanel();
		gamesWonPanel.setOpaque(false);
		gamesWonPanel.add(gamesWonLabel);
		labelPanel.add(gamesWonPanel);
		
		JPanel gamesPlayedPanel = new JPanel();
		gamesPlayedPanel.setOpaque(false);
		gamesPlayedPanel.add(gamesPlayedLabel);
		labelPanel.add(gamesPlayedPanel);
		
		JPanel winPercentagePanel = new JPanel();
		winPercentagePanel.setOpaque(false);
		winPercentagePanel.add(winPercentageLabel);
		labelPanel.add(winPercentagePanel);
		
		JPanel moneyPanel = new JPanel();
		moneyPanel.setOpaque(false);
		moneyPanel.add(moneyLabel);
		labelPanel.add(moneyPanel);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		topPanel.add(buttonPanel);
		topPanel.add(labelPanel);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.LIGHT_GRAY);
		mainPanel.setLayout(new GridLayout(0, 1));
		
		scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		add(topPanel, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		
	}// ends PlayersPanel()

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(addPlayerButton)) {
			
			if(!Main.getControlPanel().hasStarted()) {
				
				NewPlayerPanel panel = new NewPlayerPanel();
				panel.setMaximumSize(new Dimension(panel.getMaximumSize().width, panel.getMinimumSize().height));
				mainPanel.add(panel);
				totalPlayers++;
				
			} else {
				
				if(Main.getControlPanel().morePlayersAllowed()) {
	
					showNewPlayerDialog();
					
					Main.getControlPanel().updateInfo();
					
				} else{
					Main.getControlPanel().showErrorMessage("You cannot add new players at this time either due to the format of this tournament, or due to the bracket being full.");
				}
				
			}
			
		} else if(evt.getSource().equals(splitButton)) {
			
			SplitGroupDialog dialog = new SplitGroupDialog(getPanels());
			dialog.setVisible(true);
			dialog.postConstruct();
			
		} else if(evt.getActionCommand().equals("X")) {
			
			mainPanel.remove(((JButton)evt.getSource()).getParent().getParent());
			totalPlayers--;
			
		}
		
		updateInfo();
		
	}// ends actionPerformed()
	
	/**
	 * Adds a player after the tournament has already started.
	 * 
	 * @param player The player to be added.
	 */
	void addPlayer(Player player) {
		
		players[totalPlayers] = player;
		totalPlayers++;
		
	}// ends addPlayer()
	
	/**
	 * Used for loading a save file. Adds an IndividualPlayerPanel
	 * to the mainPanel.
	 * 
	 * @param panel The panel to be added to the mainPanel.
	 */
	void addPlayerPanel(IndividualPlayerPanel panel) {
		
		mainPanel.add(panel);
		totalPlayers++;
		
	}// ends addPlayerPanel()
	
	/**
	 * Adds the last player in the array of players to the bracket.
	 * Used when adding a new player to the tournament after it 
	 * has already started.
	 */
	void addPlayerToBracket() {
		
		Player player = players[totalPlayers - 1];
		Main.getControlPanel().getBracket().addPlayer(player);
		PlayerInfoPanel panel = new PlayerInfoPanel(player);
		mainPanel.add(panel);
		
	}// ends addPlayerToBracket()
	
	/**
	 * Checks that user input about a player's information
	 * is usable data.
	 * 
	 * @param panel The NewPlayerPanel to be checked.
	 * @return True if data is usable, False otherwise.
	 */
	private boolean checkPlayerInfo(NewPlayerPanel panel) {
		
		if(panel.getID().equals("") && (panel.getFirstName().equals("") || panel.getLastName().equals(""))) {
			Main.getControlPanel().showErrorMessage("Please make sure that all players have either their first and last names, or their id number filled in.");
			
			return false;
		}
		
		if(panel.getFirstName().equals("") && panel.getLastName().equals("")) {
			try{
				Integer.parseInt(panel.getID());
			}
			catch(NumberFormatException e) {
				if(!panel.getFirstName().equals("") && !panel.getLastName().equals("")) {
					Main.getControlPanel().showErrorMessage("Please make sure that " + panel.getFirstName() + " " + panel.getLastName() + "'s ID is an integer number.");
				} else {
					Main.getControlPanel().showErrorMessage("Please make sure that each player's ID is an integer number.");
				}
				
				return false;
			}
		}
		
		if(!panel.getHandicap().equals("")) {
			try{
				Float.parseFloat(panel.getHandicap());
			}
			catch(NumberFormatException e) {
				Main.getControlPanel().showErrorMessage("Please make sure that " + panel.getFirstName() + " " + panel.getLastName() + "'s handicap is numeric.");
				
				return false;
			}
		}
		
		return true;
		
	}// ends checkPlayerInfo()
	
	/**
	 * @return The number of active players in the tournaments.
	 */
	int getActivePlayers() {
		
		IndividualPlayerPanel[] panels = getPanels();
		
		if(panels != null) {
			
			int count = 0;
			for(int i=0; i<panels.length; i++) {
				if(panels[i].getPlayer() != null && 
						panels[i].getPlayer().isActive()) {
					count++;
				}
			}
			
			return count;
			
		} else {
			return 0;
		}
		
	}// ends getActivePlayers()
	
	/**
	 * @return IndividualPlayerPanel[] containing all panels within 
	 * the mainPanel.
	 */
	IndividualPlayerPanel[] getPanels() {
		
		IndividualPlayerPanel[] panels = new IndividualPlayerPanel[mainPanel.getComponentCount()];
		
		for(int i=0; i<panels.length; i++) {
			panels[i] = (IndividualPlayerPanel) mainPanel.getComponent(i);
		}
		
		return panels;
		
	}// ends getPanels()
	
	/**
	 * @return Player[] containing all the players in the tournament.
	 */
	Player[] getPlayers() {
		
		Player[] p = new Player[totalPlayers];
		
		for(int i=0; i<p.length; i++) {
			p[i] = ((IndividualPlayerPanel) mainPanel.getComponent(i)).getPlayer();
		}
		
		return p;
		
	}// ends getPlayers()
	
	/**
	 * @return String[] containing the names of all groups of
	 * players to be split in the bracket.
	 */
	ArrayList<String> getSplitGroups() {
		
		return groups;
		
	}// ends getSplitGroups()
	
	/**
	 * @return The amount of players in the beginning of the tournament.
	 */
	int getTotalPlayers() {
		
		return totalPlayers;
		
	}// ends getTotalPlayers()
	
	/**
	 * @param panel NewPlayerPanel from which to extract player info.
	 * @return Whether or not the information was acceptable.
	 */
	private boolean initPlayer(NewPlayerPanel panel, DBHSPlayerInitializer init, int count) {
		
		String firstName = panel.getFirstName();
		String lastName = panel.getLastName();
		int group = panel.getSplitGroup();
		int id = 0;
		float handicap = 0;
		try {
			if(!panel.getID().equals("")) {
				id = Integer.parseInt(panel.getID());
			}
		}
		catch(NumberFormatException e) {
			Main.getControlPanel().showErrorMessage(
					"Please make sure that all inputs under ID# are integer numbers.");
			return false;
		}
		
		try {
			if(!panel.getHandicap().equals("")) {
				handicap = Float.parseFloat(panel.getHandicap());
			}
		}
		catch(NumberFormatException e) {
			Main.getControlPanel().showErrorMessage(
					"Please make sure that all inputs under handicap are number values.");
		}
		
		try {
			Player player = init.initialize(firstName, lastName, id, handicap, group);
			players[count] = player;
		} catch (Exception e) {
			e.printStackTrace();
			Main.getControlPanel().showErrorMessage(e.getMessage());
			return false;
		}
		
		return true;
		
	}// ends initPlayer()

	@Override
	public void mouseClicked(MouseEvent evt) {

		if (Main.getControlPanel().hasStarted()) {
			
			PlayerInfoPanel[] array = new PlayerInfoPanel[mainPanel.getComponentCount()];

			for (int i = 0; i < mainPanel.getComponentCount(); i++) {

				array[i] = (PlayerInfoPanel) mainPanel.getComponent(i);

			}

			if(((UnderlinedLabel) evt.getSource()).getText().equals("Status")){
				
				for (int i = 0; i < array.length - 1; i++) {

					int min = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getPlayer().getTotalMatches()-array[j].getPlayer().getMatchesWon() < array[min]
								.getPlayer().getTotalMatches()-array[min].getPlayer().getMatchesWon()) {
							min = j;
						}

					}

					PlayerInfoPanel temp = array[min];
					array[min] = array[i];
					array[i] = temp;

				}
				
			}//ends sort by status
			
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"First Name")) {

				for (int i = 0; i < array.length - 1; i++) {

					int min = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j]
								.getPlayer()
								.getFirstName()
								.compareToIgnoreCase(
										array[min].getPlayer().getFirstName()) < 0) {
							min = j;
						}

					}

					PlayerInfoPanel temp = array[min];
					array[min] = array[i];
					array[i] = temp;

				}

			} // end sort by first name
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Last Name")) {

				for (int i = 0; i < array.length - 1; i++) {

					int min = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j]
								.getPlayer()
								.getLastName()
								.compareToIgnoreCase(
										array[min].getPlayer().getLastName()) < 0) {
							min = j;
						}

					}

					PlayerInfoPanel temp = array[min];
					array[min] = array[i];
					array[i] = temp;

				}

			} // end sort by last name
			else if (((UnderlinedLabel) evt.getSource()).getText()
					.equals("ID#")) {

				for (int i = 0; i < array.length - 1; i++) {

					int min = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getPlayer().getId() < array[min]
								.getPlayer().getId()) {
							min = j;
						}

					}

					PlayerInfoPanel temp = array[min];
					array[min] = array[i];
					array[i] = temp;

				}

			} // end sort by ID
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Handicap")) {

				for (int i = 0; i < array.length - 1; i++) {

					int max = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getPlayer().getElo() > array[max]
								.getPlayer().getElo()) {
							max = j;
						}

					}

					PlayerInfoPanel temp = array[max];
					array[max] = array[i];
					array[i] = temp;

				}

			} // end sort by handicap
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Matches Won")) {

				for (int i = 0; i < array.length - 1; i++) {

					int max = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getPlayer().getMatchesWon() > array[max]
								.getPlayer().getMatchesWon()) {
							max = j;
						}

					}

					PlayerInfoPanel temp = array[max];
					array[max] = array[i];
					array[i] = temp;

				}

			} // end sort by matches won
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Matches Played")) {

				for (int i = 0; i < array.length - 1; i++) {

					int max = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getPlayer().getTotalMatches() > array[max]
								.getPlayer().getTotalMatches()) {
							max = j;
						}

					}

					PlayerInfoPanel temp = array[max];
					array[max] = array[i];
					array[i] = temp;

				}

			} // end sort by matches played
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Games Won")) {

				for (int i = 0; i < array.length - 1; i++) {

					int max = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getPlayer().getGamesWon() > array[max]
								.getPlayer().getGamesWon()) {
							max = j;
						}

					}

					PlayerInfoPanel temp = array[max];
					array[max] = array[i];
					array[i] = temp;

				}

			} // end sort by games won
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Games Played")) {

				for (int i = 0; i < array.length - 1; i++) {

					int max = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getPlayer().getTotalGames() > array[max]
								.getPlayer().getTotalGames()) {
							max = j;
						}

					}

					PlayerInfoPanel temp = array[max];
					array[max] = array[i];
					array[i] = temp;

				}

			} // end sort by games played
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Win Percentage")) {

				for (int i = 0; i < array.length - 1; i++) {

					int max = i;

					for (int j = i + 1; j < array.length; j++) {

						if ((double)array[j].getPlayer().getGamesWon()/array[j].getPlayer().getTotalGames() > (double)array[max].getPlayer().getGamesWon()/array[max].getPlayer().getTotalGames()) {
							max = j;
						}

					}

					PlayerInfoPanel temp = array[max];
					array[max] = array[i];
					array[i] = temp;

				}

			} // end sort by win percentage
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Money Won")) {

				for (int i = 0; i < array.length - 1; i++) {

					int max = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getPlayer().getMoney() > array[max]
								.getPlayer().getMoney()) {
							max = j;
						}

					}

					PlayerInfoPanel temp = array[max];
					array[max] = array[i];
					array[i] = temp;

				}

			} // end sort by money won

			mainPanel.removeAll();

			if (evt.getSource() == lastClicked) {

				for (int i = array.length - 1; i >= 0; i--) {

					mainPanel.add(array[i]);
					lastClicked = null;

				}

			} else {

				for (int i = 0; i < array.length; i++) {

					mainPanel.add(array[i]);
					lastClicked = evt.getSource();

				}

			}

		} else {
			
			NewPlayerPanel[] array = new NewPlayerPanel[mainPanel.getComponentCount()];

			for (int i = 0; i < mainPanel.getComponentCount(); i++) {

				array[i] = (NewPlayerPanel) mainPanel.getComponent(i);

			}
			
			if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"First Name")) {

				for (int i = 0; i < array.length - 1; i++) {

					int min = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getFirstName().compareToIgnoreCase(
								array[min].getFirstName()) < 0) {
							min = j;
						}

					}

					NewPlayerPanel temp = array[min];
					array[min] = array[i];
					array[i] = temp;

				}

			} // end sort by first name
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Last Name")) {

				for (int i = 0; i < array.length - 1; i++) {

					int min = i;

					for (int j = i + 1; j < array.length; j++) {

						if (array[j].getLastName().compareToIgnoreCase(
										array[min].getLastName()) < 0) {
							min = j;
						}

					}

					NewPlayerPanel temp = array[min];
					array[min] = array[i];
					array[i] = temp;

				}

			} // end sort by last name
			else if (((UnderlinedLabel) evt.getSource()).getText()
					.equals("ID#")) {

				for (int i = 0; i < array.length - 1; i++) {

					int min = i;

					for (int j = i + 1; j < array.length; j++) {
						
						int a;
						int b;
						try {
							a = Integer.parseInt(array[j].getID());
						}
						catch(NumberFormatException e) {
							a = 0;
						}
						try {
							b = Integer.parseInt(array[min].getID());
						}
						catch(NumberFormatException e) {
							b = 0;
						}

						if (a < b) {
							min = j;
						}

					}

					NewPlayerPanel temp = array[min];
					array[min] = array[i];
					array[i] = temp;

				}

			} // end sort by ID
			else if (((UnderlinedLabel) evt.getSource()).getText().equals(
					"Handicap")) {

				for (int i = 0; i < array.length - 1; i++) {

					int max = i;

					for (int j = i + 1; j < array.length; j++) {
						
						float a;
						float b;
						try {
							a = Float.parseFloat(array[j].getHandicap());
						}
						catch(NumberFormatException e) {
							a = 0;
						}
						try {
							b = Float.parseFloat(array[max].getHandicap());
						}
						catch(NumberFormatException e) {
							b = 0;
						}

						if (a > b) {
							max = j;
						}

					}

					NewPlayerPanel temp = array[max];
					array[max] = array[i];
					array[i] = temp;

				}

			} // end sort by rank
			


			mainPanel.removeAll();

			if (evt.getSource() == lastClicked) {

				for (int i = array.length - 1; i >= 0; i--) {

					mainPanel.add(array[i]);
					lastClicked = null;

				}

			} else {

				for (int i = 0; i < array.length; i++) {

					mainPanel.add(array[i]);
					lastClicked = evt.getSource();

				}

			}
			
		}

		updateInfo();
		
	}// ends mouseClicked()

	/**
	 * Sets sortable labels' color to purple when hovered over.
	 */
	@Override
	public void mouseEntered(MouseEvent evt) {
		
		((UnderlinedLabel) evt.getSource()).setForeground(new Color(102, 51, 153));
		
	}// ends mouseEntered()

	/**
	 * Sets sortable labels' color back to black when exited.
	 */
	@Override
	public void mouseExited(MouseEvent evt) {
		
		((UnderlinedLabel) evt.getSource()).setForeground(Color.BLACK);
		
	}// ends mouscExited()

	@Override
	public void mousePressed(MouseEvent evt) {
		
		;
		
	}// ends mousePressed()
	
	@Override
	public void mouseReleased(MouseEvent evt) {
		
		;
		
	}// ends mouseReleased()
	
	/**
	 * Sets the size of mainPanel.
	 */
	void postConstruct() {
		
		scroll.setPreferredSize(new Dimension(getWidth(), (int) Main.getControlPanel().getScreen().getPreferredSize().getHeight() - (int) labelPanel.getPreferredSize().getHeight() - (int) buttonPanel.getPreferredSize().getHeight()));
		
	}// ends postConstruct()

	/**
	 * Removes the last player from the array of players
	 * when the process of adding a new player after the 
	 * tournament has already started is canceled.
	 */
	void removePlayer() {
		
		players[totalPlayers - 1] = null;
		totalPlayers--;
		
	}// ends removePlayer()
	
	/**
	 * @param enabled Whether or not to enable the buttons.
	 */
	void setButtonsEnabled(boolean enabled) {
		
		splitButton.setEnabled(enabled);
		addPlayerButton.setEnabled(enabled);
		
	}// ends setButtonsEnabled()
	
	/**
	 * @param groupsArray String[] to be converted to ArrayList<String>.
	 */
	void setSplitGroups(ArrayList<String> groupsList) {
		
		groups.clear();
		for(int i=0; i<groupsList.size(); i++) {
			groups.add(groupsList.get(i));
		}
		
	}// ends setSplitGroups()
	
	/**
	 * Shows a dialog used to take user input in order to
	 * create a new player to add to the tournament after
	 * it has already started.
	 */
	void showNewPlayerDialog() {
		
		NewPlayerDialog dialog = new NewPlayerDialog();
		dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
		dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        dialog.setResizable(false);
        dialog.setVisible(true);
		
	}// ends showNewPlayerDialog()
	
	/**
	 * @return True if all necessary information needed
     * to start the tournament is ready to be input.
     * False otherwise.
	 */
	boolean startable() {
		
		if(totalPlayers > 0) {
			
			for(int i=0; i<mainPanel.getComponentCount(); i++) {
				if(!checkPlayerInfo((NewPlayerPanel) mainPanel.getComponent(i))) {
					return false;
				}
			}

			
			DBHSPlayerInitializer init = new DBHSPlayerInitializer();
			
			// Take in player information.
			int count = 1;
			while(Math.pow(2, count) < mainPanel.getComponentCount()) {
				count++;
			}
			players = new Player[(int) Math.pow(2, count)];
			for(int i=0; i<mainPanel.getComponentCount(); i++) {
				if(!initPlayer((NewPlayerPanel)mainPanel.getComponent(i), init, i)) {
					// reset player array to empty
					for(int j=0; j<players.length; j++) {
						players[j] = null;
					}
					
					return false;
				}
			}
			
		} else {
			
			Main.getControlPanel().showErrorMessage("There are no players registered.");
			
			return false;
			
		}
		
		return true;
		
	}// ends startable()
	
	/**
	 * Set up the screen for changes once the tournament has started.
	 */
	void startTournament() {

		// Set up listeners to be able so sort.
		statusLabel.addMouseListener(this);
		matchesWonLabel.addMouseListener(this);
		matchesLabel.addMouseListener(this);
		gamesWonLabel.addMouseListener(this);
		gamesPlayedLabel.addMouseListener(this);
		winPercentageLabel.addMouseListener(this);
		moneyLabel.addMouseListener(this);
		
		// Add players to bracket.
		Main.getControlPanel().getBracket().setPlayers(players);
		
		// Switch out NewPlayerPanels for PlayerInfoPanels.
		for(int i=0; i<mainPanel.getComponentCount(); i++) {
			PlayerInfoPanel panel = new PlayerInfoPanel(players[i]);
			panel.setMaximumSize(new Dimension(panel.getMaximumSize().width, mainPanel.getComponent(i).getPreferredSize().height));
			mainPanel.remove(i);
			mainPanel.add(panel, i);
		}
		
		splitButton.setEnabled(false);
		
		updateInfo();

	}// ends startTournament()
	
	/**
	 * Updates data about each player within the database.
	 */
	void submitInfo() {
		
		// Change players' handicaps if handicaps only change 
		// after the tournament.
		if(Main.getControlPanel().handicapsAffected() == 1) {
			
			LinkedList<Match> matches = Main.getControlPanel().getBracket().getLLMatches();
			
			for(int i=0; i<matches.size(); i++) {
				
				Match match = matches.get(i);
				
				if(match.getWinner() != null && match.getLoser() != null) {
					
					DBHSHandicapMutator.process(match);
					
				}
				
			}
			
		}
		
		// Update the database.
		for(int i=0; i<mainPanel.getComponentCount(); i++) {
			
			DBHSDatabaseIntermediary.updatePlayer(
					((PlayerInfoPanel) mainPanel.getComponent(i)).getPlayer());
			
		}
		
	}// ends submitInfo()
	
	/**
	 * Makes sure that the screen is showing current information.
	 */
	void updateInfo() {
		
		numPlayersLabel.setText("Total Players: " + totalPlayers);
		
		if(!Main.getControlPanel().morePlayersAllowed()) {
			addPlayerButton.setEnabled(false);
		} else if(!addPlayerButton.isEnabled()) {
			addPlayerButton.setEnabled(true);
		}
		
		for(int i=0; i<mainPanel.getComponentCount(); i++) {
			((IndividualPlayerPanel)mainPanel.getComponent(i)).updateInfo();
		}
		
		//Set up layout for mainPanel and adjust the border of children if needed.
		if(mainPanel.getComponentCount() != 0) {
			
			int n = scroll.getHeight() / mainPanel.getComponent(0).getPreferredSize().height;
			mainPanel.setLayout(new GridLayout(Math.max(mainPanel.getComponentCount(), n), 1));
			for(int i=0; i<mainPanel.getComponentCount(); i++) {
				((JPanel)mainPanel.getComponent(i)).setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
			}
			if(mainPanel.getComponentCount() > n) {
				((JPanel) mainPanel.getComponent(mainPanel.getComponentCount() - 1)).setBorder(null);
			}
			
		}
		
		validate();
		repaint();
		
	}// ends updateInfo()

}// ends Class
