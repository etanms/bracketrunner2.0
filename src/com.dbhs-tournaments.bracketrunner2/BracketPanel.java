package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JViewport;

import com.etan.bracketrunner2.brackets.Bracket;
import com.etan.bracketrunner2.brackets.BracketWrapper;
import com.etan.bracketrunner2.matches.Match;

/**
 * Interface for subclasses that will be visual
 * representations of the tournament bracket, 
 * each based on a different tournament format.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 *
 */
@SuppressWarnings("serial")
abstract class BracketPanel extends ZoomablePanel implements BracketWrapper {
	
	protected Bracket bracket;
	
	/**
	 * Constructor
	 * 
	 * @param bracket The bracket to be drawn by this.
	 */
	public BracketPanel(Bracket bracket) {
		
		this.bracket = bracket;
		
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.RED, 4));

		JViewport v = Main.getControlPanel().getScroller().getViewport();
		
		int w;
		if(bracket.getLoserRounds() == 0) {
			w = (int) v.getVisibleRect().getHeight() / bracket.getFullBracket() * (bracket.getRounds() * 6 - 1);
		} else {
			w = (int) v.getVisibleRect().getHeight() / bracket.getFullBracket() * ((bracket.getRounds() + bracket.getLoserRounds()) * 6 - 1);
		}
		
		setPreferredSize(new Dimension(w, v.getHeight()-v.getInsets().top-v.getInsets().bottom));
		iSize = getPreferredSize();
		
	}// ends BracketPanel()
	
	/**
	 * Evenly distributes players randomly into the bracket.
	 */
	public void addPlayers(Player[] players) {
		
		bracket.setPlayers(players);
		
	}// ends addPlayers()
	
	/**
	 * Draws the specified match.
	 * 
	 * @param match The match to be drawn.
	 */
	protected abstract void drawMatch(Graphics g, Match match);
	
	/**
	 * @return The bracket displayed by this panel.
	 */
	public Bracket getBracket() {
		
		return bracket;
		
	}// ends getBracket()
    
    /**
     * @return The final match of the tournament. If the format is true double 
     * elimination, this method returns the first final match unless the second
     * final is up next or already started.
     */
    public abstract Match getFinalMatch();
    
    /**
     * @return The number of players needed to fill the bracket.
     */
    public int getFullBracket() {
    	
    	return bracket.getFullBracket();
    	
    }// ends getFullBracket()
    
    /**
     * @return A linked list of all the matches in the order
     * that they should be played.
     */
    public LinkedList<Match> getLLMatches() {
    	
    	return bracket.getLLMatches();
    	
    }// ends getLLMatches()
    
    /**
     * @return The number of rounds within the losers' side.
     */
    public int getLoserRounds() {
    	
    	return bracket.getLoserRounds();
    	
    }// ends getLoserRounds()
    
    /**
     * @return A two dimensional array of matches where the 
     * first dimension of the array is the round of the matches
     * and the second is the number of the match within the 
     * round.
     */
    public Match[][] getMatches() {
    	
    	return bracket.getMatches();
    	
    }// ends getMatches()
    
    /**
	 * @return The total number of rounds on the winners' side.
	 */
	public int getRounds() {
		
		return bracket.getRounds();
		
	}// ends getRounds()
    
    /**
     * @return True if possible to add another player to the tournament
     * without disrupting any started matches and while keeping the 
     * player distribution even.
     */
    public boolean morePlayersAllowed() {
    	
    	return bracket.morePlayersAllowed();
    	
    }// ends morePlayersAllowed()
	
    @Override
	public void paintComponent(Graphics g) {
    	
		super.paintComponent(g);
		
		paintMatches(g);
		
	} // end paintComponent()
    
    /**
     * Determines what matches should be drawn to the screen.
     */
    void paintMatches(Graphics g) {
		
		LinkedList<Match> llMatches = bracket.getLLMatches();
		
		for(int i = 0; i < llMatches.size(); i++){
			drawMatch(g, llMatches.get(i));
		}
    	
    }// ends paintMatches()
	
	/**
	 * Makes sure that the screen is showing current information.
	 */
	public void updateInfo() {
		
		validate();
		repaint();
		
	}// ends updateInfo()

}// ends Class
