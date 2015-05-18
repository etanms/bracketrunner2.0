package com.etan.bracketrunner2.brackets;

import java.util.LinkedList;

import com.etan.bracketrunner2.Player;
import com.etan.bracketrunner2.matches.Match;

public interface BracketWrapper {
	
	Bracket bracket = null;
	
	/**
	 * Evenly distributes players randomly into the bracket.
	 */
	void addPlayers(Player[] players);
	
	/**
     * @return The final match of the tournament. If the format is true double 
     * elimination, this method returns the first final match unless the second
     * final is up next or already started.
     */
	Match getFinalMatch();
	
	/**
     * @return The number of players needed to fill the bracket.
     */
	int getFullBracket();
	
	/**
     * @return A linked list of all the matches in the order
     * that they should be played.
     */
	LinkedList<Match> getLLMatches();
	
	/**
	 * @return The number of rounds within the losers' side.
	 */
	int getLoserRounds();
	
	/**
     * @return A two dimensional array of matches where the 
     * first dimension of the array is the round of the matches
     * and the second is the number of the match within the 
     * round.
     */
	Match[][] getMatches();
	
	/**
	 * @return The total number of rounds on the winners' side.
	 */
	int getRounds();
	
	/**
     * @return True if possible to add another player to the tournament
     * without disrupting any started matches and while keeping the 
     * player distribution even.
     */
    boolean morePlayersAllowed();

}// ends Interface
