package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.etan.bracketrunner2.matches.Match;

/**
 * JPanel used to switch between different screens.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 26 2014
 */
@SuppressWarnings("serial")
class ScreenPanel extends JPanel implements ActionListener {
	
	static final int OPTIONS = 0;
	static final int PLAYERS = 1;
	static final int TABLES = 2;
	static final int MATCHES = 3;
	static final int BRACKET = 4;
	static final int WINNERS = 5;
	static final int LOSERS = 6;
	
    private OptionsPanel options;
    private PlayersPanel players;
    private TablesPanel tables;
    private MatchesPanel matches;
    private ClickableBracketPanel bracket;
    private ClickableBracketPanel winners;
    private ClickableBracketPanel losers;
  	
    /**
     * Constructor
     */
    public ScreenPanel(){
        
    	setLayout(new GridLayout(1,1));
    	setBackground(Color.GRAY);
      	
    	options = new OptionsPanel();
    	players = new PlayersPanel();
    	tables = new TablesPanel();
    	
        add(options);
  		
    }// end ScreenPanel()

    /**
     * Handles events and switches screens when
     * buttons from the ButtonPanel are pressed.
     */
    public void actionPerformed(ActionEvent evt) {

  	    if(evt.getActionCommand() == "Details") {
  	    	setScreen(ScreenPanel.OPTIONS);
        } else if(evt.getActionCommand() == "Players") {
        	setScreen(ScreenPanel.PLAYERS);
        } else if(evt.getActionCommand() == "Tables") {
        	setScreen(ScreenPanel.TABLES);
        } else if(evt.getActionCommand() == "Matches") {
        	setScreen(ScreenPanel.MATCHES);
        } else if(evt.getActionCommand() == "Bracket") {
        	setScreen(ScreenPanel.BRACKET);
        } else if(evt.getActionCommand() == "Winners") {
        	setScreen(ScreenPanel.WINNERS);
        } else if(evt.getActionCommand() == "Losers") {
        	setScreen(ScreenPanel.LOSERS);
        }
  	    
  	    Main.getControlPanel().updateInfo();

    }// ends actionPerformed()
    
    /**
     * @return OptionsPanel
     */
    OptionsPanel getOptions() {
    	
    	return options;
    	
    }// ends getOptions()
    
    /**
     * @return PlayersPanel
     */
    PlayersPanel getPlayers() {
    	
    	return players;
    	
    }// ends getPlayers()
    
    /**
     * @return TablesPanel
     */
    TablesPanel getTables() {
    	
    	return tables;
    	
    }// ends getTables()
    
    /**
     * @return MatchesPanel
     */
    MatchesPanel getMatches() {
    	
    	return matches;
    	
    }// ends getMatches()
    
    /**
     * @return BracketPanel
     */
    ClickableBracketPanel getBracket() {
    	
    	return bracket;
    	
    }// ends getBracket()
    
    /**
     * @return WinnersPanel
     */
    ClickableBracketPanel getWinners() {
    	
    	return winners;
    	
    }// ends getWinners()
    
    /**
     * @return LosersPanel
     */
    ClickableBracketPanel getLosers() {
    	
    	return losers;
    	
    }// ends getLosers()
	
	/**
	 * Calls postConstruct() on necessary screens.
	 */
	void postConstruct() {
		
		players.postConstruct();
		tables.postConstruct();
		
	}// ends postConstruct()
  	
    /**
     * Sets the screen to the requested panel.
     */
    void setScreen(int screen) {

  	    if(screen == OPTIONS) {
  	    	
  	    	removeAll();
  	    	add(options);
  	    	Main.getControlPanel().getScroller().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  	    	Main.getControlPanel().getScroller().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
  	    	
        } else if(screen == PLAYERS) {
        	
        	removeAll();
        	add(players);
  	    	Main.getControlPanel().getScroller().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  	    	Main.getControlPanel().getScroller().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
  	    	
        } else if(screen == TABLES) {
        	
        	removeAll();
        	add(tables);
  	    	Main.getControlPanel().getScroller().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  	    	Main.getControlPanel().getScroller().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
  	    	
        } else if(screen == MATCHES) {
        	
        	removeAll();
        	add(matches);
  	    	Main.getControlPanel().getScroller().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
  	    	Main.getControlPanel().getScroller().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
  	    	
        } else if(screen == BRACKET) {
        	
        	// Make certain that MouseEvents don't trigger unintended matches.
        	LinkedList<Match> matches = Main.getControlPanel().getBracket().getLLMatches();
        	for(int i=0; i<matches.size(); i++) {
        		matches.get(i).setRect(null);
        	}
        	
        	removeAll();
        	add(bracket);
  	    	Main.getControlPanel().getScroller().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  	    	Main.getControlPanel().getScroller().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
  	    	
        } else if(screen == WINNERS) {
        	
        	// Make certain that MouseEvents don't trigger unintended matches.
        	LinkedList<Match> matches = Main.getControlPanel().getBracket().getLLMatches();
        	for(int i=0; i<matches.size(); i++) {
        		matches.get(i).setRect(null);
        	}
        	
        	removeAll();
        	add(winners);
  	    	Main.getControlPanel().getScroller().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  	    	Main.getControlPanel().getScroller().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
  	    	
        } else if(screen == LOSERS) {
        	
        	// Make certain that MouseEvents don't trigger unintended matches.
        	LinkedList<Match> matches = Main.getControlPanel().getBracket().getLLMatches();
        	for(int i=0; i<matches.size(); i++) {
        		matches.get(i).setRect(null);
        	}
        	
        	removeAll();
        	add(losers);
  	    	Main.getControlPanel().getScroller().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  	    	Main.getControlPanel().getScroller().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
  	    	
        }
  	    
  	    Main.getControlPanel().updateInfo();

    }// ends setScreen()
    
    /**
     * Figures out what type of bracket or brackets are needed, 
     * and instantiates them.
     */
    void startTournament() {
    	
    	int totalPlayers = players.getTotalPlayers();
    	int fullBracket=1;
    	while(fullBracket < totalPlayers) {
    		fullBracket *= 2;
    	}
    	
    	// Add the bracket
    	switch(Main.getControlPanel().getFormat()) {
    	case "Single Elimination":
    		bracket = new ClickableBracketPanel(fullBracket, ClickableBracketPanel.SINGLE);
    		break;
    	case "Single Modified":
    		bracket = new ClickableBracketPanel(fullBracket, ClickableBracketPanel.MODIFIED);
    		winners = new ClickableBracketPanel(fullBracket, ClickableBracketPanel.WINNERS);
    		losers = new ClickableBracketPanel(fullBracket, ClickableBracketPanel.LOSERS);
    		break;
    	case "Double Elimination":
    		bracket = new ClickableBracketPanel(fullBracket, ClickableBracketPanel.DOUBLE);
    		winners = new ClickableBracketPanel(fullBracket, ClickableBracketPanel.WINNERS);
    		losers = new ClickableBracketPanel(fullBracket, ClickableBracketPanel.LOSERS);
    		break;
    	}
    	
    	// Add the Matches screen
    	matches = new MatchesPanel();
    	matches.postConstruct();
    	
    }// ends startTournament()
    
    /**
	 * Calls updateInfo() on all other screens.
	 */
	void updateInfo() {
		
		options.updateInfo();
		tables.updateInfo();
		players.updateInfo();
		if(options.hasStarted()) {
			matches.updateInfo();
			bracket.updateInfo();
			if(winners != null) {
				winners.updateInfo();
				losers.updateInfo();
			}
		}
		
		validate();
		repaint();
		
	}// ends updateInfo()

}// end class ScreenPanel
