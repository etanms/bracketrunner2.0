package com.etan.bracketrunner2;

import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.*;

import com.etan.bracketrunner2.brackets.Bracket;
import com.etan.bracketrunner2.matches.Match;
import com.etan.dbhs.DBHSDatabaseIntermediary;
import com.etan.dbhs.DBHSPlayerInitializer;

/**
 * Panel used to hold all other panels and as a facade
 * to access all data and methods from.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 24, 2014
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener{
	
	private static ScreenPanel screen;
	private static JScrollPane screenScroller;
	private static ButtonPanel buttonPanel;
	private static InfoPanel infoPanel;
	
	private int tournamentId;

	/**
	 * Constructor
	 */
    public ControlPanel(){

      setBackground(Color.RED);
      setLayout(new BorderLayout(4,4));
      
      screen = new ScreenPanel();
      screenScroller = new JScrollPane(screen);
      add(screenScroller, BorderLayout.CENTER);

      buttonPanel = new ButtonPanel();
      add(buttonPanel, BorderLayout.SOUTH);
      
      infoPanel = new InfoPanel();
      add(infoPanel, BorderLayout.NORTH);

    } // end ControlPanel()

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getActionCommand().equals("Start Tournament")) {
			
			Thread t = new Thread() {
				
				LoadingDialog dialog;
				
				@Override
				public void run() {
					
					screen.getPlayers().setButtonsEnabled(false);
					
					dialog = new LoadingDialog();
					dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
					dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			        dialog.setResizable(false);
			        dialog.setVisible(true);
					
					if(startable()) {
						try {
							sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						dialog.dispose();
						showPrizesDialog();
					} else {
						dialog.dispose();
						screen.getPlayers().setButtonsEnabled(true);
					}
					
				}// ends run()
				
			};
			
			t.start();
			
		} else if(evt.getActionCommand().equals("SUBMIT INFO")) {
			
			Thread t = new Thread() {
				
				public void run() {
					
					JDialog dialog = new SavingDialog();
					dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
					dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			        dialog.setResizable(false);
			        dialog.setVisible(true);
			        
			        try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					screen.getMatches().submitInfo();
					screen.getPlayers().submitInfo();
					DBHSPlayerInitializer.unlockPlayers();
					Main.getMenuBarActionListener().save(Main.getMenuBarActionListener().getCurrentFile().getName());
					DBHSDatabaseIntermediary.submitSaveFile(tournamentId, Main.getMenuBarActionListener().getCurrentFile());
					
					infoPanel.setSubmitButtonEnabled(false);
					
					dialog.dispose();
					
				}// ends run()
				
			};
			
			t.start();
			
		}
		
		updateInfo();
		
	}// ends actionPerformed()
	
	/**
	 * @return True if the players' names should be written in 
	 * a color representing their status in the tournament.
	 */
	public boolean colored() {
		
		return infoPanel.colored();
		
	}// ends colored()
	
	/**
	 * @return The amount of players still active in the tournament.
	 */
	public int getActivePlayers() {
		
		return screen.getPlayers().getActivePlayers();
		
	}// ends getActivePlayers()
	
	/**
	 * @return The bracket.
	 */
	public Bracket getBracket() {
		
		return screen.getBracket().getBracket();
		
	}// ends getBracket()
	
	/**
	 * @return The date on which the tournament was started.
	 */
	public Date getDate() {
		
		return screen.getOptions().getDate();
		
	}// ends getDate()
    
    /**
	 * @return Entry Fee paid by each player. Returns -1 if user input is invalid.
	 */
	public int getEntry() {
		
		return screen.getOptions().getEntry();
		
	}// ends getEntry()
    
    /**
     * @return The final match of the tournament. If the format is true double 
     * elimination, this method returns the first final match unless the second
     * final is up next or already started.
     */
    public Match getFinalMatch() {
    	
    	return screen.getBracket().getFinalMatch();
    	
    }// ends getFinalMatch()
	
	/**
	 * @return Returns 1 if the finals are a single match. Returns 2 if the finals are a true double elimination.
	 */
	public int getFinalsFormat() {
		
		return screen.getOptions().getFinalsFormat();
		
	}// ends getFinalsFormat()
	
	/**
	 * @return The length of the race of the final match. Returns 0 if using the handicap system.
	 */
	public int getFinalsRace() {
		
		return screen.getOptions().getFinalsRace();
		
	}// ends getFinalsRace()
	
	/**
	 * @return Format of the tournament.
	 */
	public String getFormat() {
		
		return screen.getOptions().getFormat();
		
	}// ends getFormat()
	
	/**
	 * @return Selected index of formatComboBox of the tournament.
	 */
	public int getFormatAsInt() {
		
		return screen.getOptions().getFormatAsInt();
		
	}// ends getFormatAsInt()
	
	/**
	 * @return Game being played in the tournament.
	 */
	public String getGame() {
		
		return screen.getOptions().getGame();
		
	}// ends getGame()
	
	/**
	 * @return Number of selected index of gameComboBox.
	 */
	public int getGameAsInt() {
		
		return screen.getOptions().getGameAsInt();
		
	}// ends getGameAsInt()
	
	/**
	 * @return InfoPanel
	 */
	InfoPanel getInfoPanel() {
		
		return infoPanel;
		
	}// ends getInfoPanel();
    
    /**
     * @return The number of rounds within the losers' side.
     */
    public int getLoserRounds() {
    	
    	if(screen.getBracket() != null) {
    		return screen.getBracket().getLoserRounds();
    	} else {
    		return 0;
    	}
    	
    }// ends getLoserRounds()
	
	/**
	 * @return The length of the races of all loser's side matches. Returns 0 if using the handicap system.
	 */
	public int getLosersRace() {
		
		return screen.getOptions().getLosersRace();
		
	}// ends getLosersRace()
	
	/**
	 * @return The amount of money added to the pot for the tournament. Returns -1 if user input is invalid.
	 */
	public int getMoneyAdded() {
		
		return screen.getOptions().getMoneyAdded();
		
	}// ends getMoneyAdded()
	
	/**
	 * @return The green's fee taken out of each player's entry fee. Returns -1 if user input is invalid.
	 */
	public int getMoneyWithheld() {
		
		return screen.getOptions().getMoneyWithheld();
		
	}// ends getMoneyWithheld()
	
	/**
	 * @return An int[] filled with all possible options of
	 * amounts of players to be paid.
	 */
	public int[] getPlacesPaid() {
		
		return screen.getOptions().getPlacesPaid();
		
	}// ends getPlacesPaid()
	
	/**
	 * @return int[] containing every prize level where 0 is
	 * the first place prize. Does not repeat prizes if multiple
	 * places recieve the same prize.
	 */
	public int[] getPrizes() {
		
		return screen.getOptions().getPrizes();
		
	}// ends getPrizes()
	
	/**
	 * @return The length of the races of all winner's side matches. Returns 0 if using the handicap system.
	 */
	public int getRace() {
		
		return screen.getOptions().getRace();
		
	}// ends getRace()
	
	/**
	 * @return The total number of rounds on the winners' side.
	 */
	public int getRounds() {
		
		if(screen.getBracket() != null) {
			return screen.getBracket().getRounds();
		} else {
			return 0;
		}
		
	}// ends getRounds()
	
	/**
	 * @return ScreenPanel used to get reference to another screen as 
	 * a listener.
	 */
	ScreenPanel getScreen() {
		
		return screen;
		
	}// ends getScreen()
	
	/**
	 * @return JScrollPane decorator of the screen.
	 */
	public JScrollPane getScroller() {
		
		return screenScroller;
		
	}// ends getScroller()
    
    /**
     * @return Array containing all TablePanels referencing tables that are tournament usable.
     */
    public TablePanel[] getTables() {
    	
    	return screen.getTables().getTables();
    	
    }// ends getTables()
    
    /**
     * @return Total number of participants in the tournament.
     */
    public int getTotalPlayers() {
    	
    	return screen.getPlayers().getTotalPlayers();
    	
    }// ends getTotalPlayers()
    
    /**
     * @return The id number of the current tournament.
     */
    public int getTournamentId() {
    	
    	return tournamentId;
    	
    }// ends getTournamentId()
	
	/**
	 * @return Location of the tournament.
	 */
	public String getTournamentLocation() {
		
		return screen.getOptions().getTournamentLocation();
		
	}// ends getTournamentLocation()
	
	/**
	 * @return Index selected on the locationComboBox.
	 */
	public int getTournamentLocationAsInt() {
		
		return screen.getOptions().getTournamentLocationAsInt();
		
	}// ends getTournamentLocationAsInt()
	
	/**
	 * @return The selectedIndex of the affectedComboBox within the 
	 * OptionsPanel.
	 */
	public int handicapsAffected() {
		
		return screen.getOptions().handicapsAreAffected();
		
	}// ends handicapsAreAffected()
    
    /**
     * @return Whether or not the tournament has started.
     */
    public boolean hasStarted() {
    	
    	return screen.getOptions().hasStarted();
    	
    }// ends hasStarted()
    
    /**
     * Switches out the SubmitPanel on the InfoPanel for the NextPanel.
     */
    void initNextPanel() {
    	
    	infoPanel.initNextPanel();
    	
    }// ends initNextPanel()
    
    /**
     * Switches out the NextPanel on the InfoPanel for the SubmitPanel.
     */
    void initSubmitButton() {
    	
    	infoPanel.initSubmitButton();
    	
    }// ends initSubmitButton()
    
    /**
     * Adds all players to the "locked_players" table within 
     * the database.
     */
    protected void lockPlayers() {
    	
    	Player[] players = screen.getPlayers().getPlayers();
    	
    	for(int i=0; i<players.length; i++) {
    		
    		DBHSPlayerInitializer.lockPlayer(players[i]);
    		
    	}
    	
    }// ends lockPlayers()
    
    /**
     * @return True if possible to add another player to the tournament
     * without disrupting any started matches and while keeping the 
     * player distribution even.
     */
    public boolean morePlayersAllowed() {
    	
    	if(hasStarted()) {
    		return screen.getBracket().morePlayersAllowed();
    	} else {
    		return true;
    	}
    	
    }// ends morePlayersAllowed()
    
    /**
     * Finishes initializing any objects that use a reference to this ControlPanel while within its constructor.
     */
    void postConstruct() {
    	
    	buttonPanel.postConstruct();
    	infoPanel.postConstruct();
    	screen.postConstruct();
    	
    }// ends postConstruct()
    
    /**
     * @param str The string to be scaled.
     * @param width The width in which to fit the str.
     * @param g Graphics object to be used.
     * @return
     */
    public Font scaleFont(String str, int width, Graphics g) {
    	
    	Font font = g.getFont();
    	float fontSize = font.getSize();
    	float fontWidth = g.getFontMetrics(font).stringWidth(str);

    	if(fontWidth <= width) {
    		return font;
    	} else {
    		fontSize *= (float)width / fontWidth;
    		return font.deriveFont(fontSize);
    	}
    	
    }// ends scaleFont()
    
    /**
     * @param str The string to be scaled.
     * @param width The width in which to fit the str.
     * @param c Component being adjusted.
     * @return
     */
    public Font scaleFont(String str, int width, Component c) {
    	
    	Font font = c.getFont();
    	float fontSize = font.getSize();
    	float fontWidth = c.getFontMetrics(font).stringWidth(str);

    	if(fontWidth <= width) {
    		return font;
    	} else {
    		fontSize *= (float)width / fontWidth;
    		return font.deriveFont(fontSize);
    	}
    	
    }// ends scaleFont()
    
    /**
     * Retrieves from the database and sets tournamentId.
     */
    protected void setTournamentId() {
    	
    	tournamentId = DBHSDatabaseIntermediary.getTournamentId();
    	
    }// ends setTournamentId()
    
    /**
     * Sets tournamentId using data from a save file.
     */
    protected void setTournamentId(int id) {
    	
    	tournamentId = id;
    	
    }// ends setTournamentId(int id)
    
    /**
     * Takes a string and separates it into separate lines every 50 characters when 
     * a word ends. Then outputs the message in a dialog with the header "Error!".
     * 
     * @param str The message to place within the dialog.
     */
    public void showErrorMessage(String str) {
    	
    	Font font = new Font("Arial Black", Font.PLAIN, 16);
    	
		JFrame window = Main.getWindow();
    	JDialog eDialog = new JDialog(window, "Error!", Dialog.ModalityType.APPLICATION_MODAL);
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(0, 1, 0, -8));

		if (str.length() > 50) {

			int begin = 0;
			int end = 50;

			while (begin < str.length()) {

				if (end >= str.length()) {
					JPanel jPanel = new JPanel();
					jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
					JLabel label = new JLabel(str.substring(begin, str.length()));
					label.setForeground(Color.RED);
					label.setFont(font);
					jPanel.add(label);
					content.add(jPanel);
					break;
				} else {
					if (str.charAt(end) == ' ') {
						JPanel jPanel = new JPanel();
						jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
						JLabel label = new JLabel(str.substring(begin, end + 1));
						label.setForeground(Color.RED);
						label.setFont(font);
						jPanel.add(label);
						content.add(jPanel);
						begin = end + 1;
						end = begin + 50;
					} else {
						end++;
					}

				}

			}

		}
		else{
			JPanel jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel label = new JLabel(str);
			label.setForeground(Color.RED);
			label.setFont(font);
			jPanel.add(label);
			content.add(jPanel);
		}

		eDialog.setContentPane(content);
		eDialog.pack();
		eDialog.setLocation(window.getLocation().x + window.getWidth()
				/ 2 - eDialog.getWidth() / 2, window.getLocation().y
				+ window.getHeight() / 2 - eDialog.getHeight() / 2);
		eDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		eDialog.setResizable(false);
		eDialog.setVisible(true);
    	
    }// ends showErrorMessage()
    
    /**
     * Shows a dialog on the screen that takes in user input
     * to determine what the prizes will be.
     */
    void showPrizesDialog() {
    	
    	OptionsPanel options = screen.getOptions();
    	int format;
    	switch(options.getFormat()) {
    	case "Single Elimination":
    		format = PrizesDialog.SINGLE;
    		break;
    	case "Single Modified":
    		format = PrizesDialog.MODIFIED;
    		break;
    	case "Double Elimination":
    		format = PrizesDialog.DOUBLE;
    		break;
    	default:
    		format = 0;
    		break;
    	}
    	
    	PrizesDialog pd = new PrizesDialog(screen.getPlayers().getTotalPlayers(), 
    			options.getEntry(), options.getMoneyWithheld(), 
    			options.getMoneyAdded(), format);
    	pd.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - pd.getWidth()/2, 
    			Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - pd.getHeight()/2);
        pd.setResizable(false);
        pd.setVisible(true);
    	
    }// ends showPrizesDialog()
    
    /**
     * @return True if all necessary information needed
     * to start the tournament is ready to be input.
     * False otherwise.
     */
    public boolean startable() {
    	
    	if(!screen.getOptions().startable()) {
    		return false;
    	} else if(!screen.getPlayers().startable()) {
    		return false;
    	} else if(getFormatAsInt() == 1 && screen.getPlayers().getPanels().length < 9) {
    		showErrorMessage("There must be at least nine players in order to run a single modified format.");
    		return false;
    	}
    	
    	return true;
    	
    }// ends startable()
	
	/**
	 * Calls startTournament() on all necessary components when beginning the tournament.
	 */
	void startTournament() {
		
		screen.startTournament();
		screen.getOptions().startTournament();
		screen.getPlayers().startTournament();
		infoPanel.startTournament();
		buttonPanel.startTournament();
		
		updateInfo();
		
	}// ends startTournament()
	
	/**
	 * Calls updateInfo() on the screen which in turn calls updateInfo() on all other screens.
	 */
	public void updateInfo() {
		
		screen.updateInfo();
		infoPanel.updateInfo();
		validate();
		repaint();
		
	}// ends updateInfo()
    
  } // end class ControlPanel
