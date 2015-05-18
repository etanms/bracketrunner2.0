package com.etan.bracketrunner2.brackets;

import java.util.LinkedList;

import com.etan.bracketrunner2.Player;
import com.etan.bracketrunner2.matches.Match;

public abstract class Bracket {
	
	protected Match[][] matches;
	protected Match[][] loserMatches;
	protected LinkedList<Match> llMatches;
	protected int fullBracket = 0;
	protected int totalRounds = 0;
	
	/**
	 * Constructor
	 * 
	 * @param fullBracket The maximum number of players the bracket
	 * can hold.
	 */
	public Bracket(int fullBracket) {
		
		this.fullBracket = fullBracket;
		
	}// ends Bracket()
	
	/**
	 * Adds a new player to the bracket after the tournament
	 * has already started.
	 * 
	 * @param player The player to be added.
	 */
	public abstract void addPlayer(Player player);
	
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
		
		return fullBracket;
		
	}// ends getFullBracket()
	
	/**
     * @return A linked list of all the matches in the order
     * that they should be played.
     */
	public LinkedList<Match> getLLMatches() {
		
		return llMatches;
		
	}// ends getLLMatches()
	
	/**
	 * @return A two dimensional array of losers' side matches 
	 * where the first dimension of the array is the round of 
	 * the matches and the second is the number of the match 
	 * within the round.
	 */
	public Match[][] getLoserMatches() {
		
		return loserMatches;
		
	}// ends getLoserMatches()
	
	/**
	 * @return The number of rounds within the losers' side.
	 */
	public abstract int getLoserRounds();
	
	/**
     * @return A two dimensional array of matches where the 
     * first dimension of the array is the round of the matches
     * and the second is the number of the match within the 
     * round.
     */
	public Match[][] getMatches() {
		
		return matches;
		
	}// ends getMatches()
	
	/**
	 * @return The total number of rounds on the winners' side.
	 */
	public int getRounds() {
		
		return totalRounds;
		
	}// ends getRounds()
	
	public void insertPlayers(Player[] p) {
		
		int[] pattern = pattern(fullBracket/2);
		
		// Place the players into the first round.
		for(int i=0; i<p.length; i++) {
			if(i < fullBracket/2) {
				llMatches.get(pattern[i]).setP1(p[i]);
			} else {
				llMatches.get(pattern[fullBracket - i - 1]).setP2(p[i]);
			}
		}
		
		// Move players with byes along into the second round.
		for(int i=0; i<fullBracket/2; i++) {
			
			Match match = llMatches.get(i);
			if(match.getP2() == null) {
				if(match.getNum()%2 == 1) {
					matches[2][(match.getNum()+1)/2].setP1(match.getP1());
				} else {
					matches[2][(match.getNum()+1)/2].setP2(match.getP1());
				}
			}
			
		}
		
	}// ends insertPlayers()
	
	/**
     * @return True if possible to add another player to the tournament
     * without disrupting any started matches and while keeping the 
     * player distribution even.
     */
    public boolean morePlayersAllowed() {
    	
    	int i = 0;
    	while(llMatches.size() > i && llMatches.get(i) != null && llMatches.get(i).getRound() == 1) {
    		
    		Match match = llMatches.get(i);
    		if(match.getP2() == null) {
    			if(!matches[2][(match.getNum()+1)/2].hasStarted()) {
    				if(loserMatches != null && !loserMatches[1][(match.getNum()+1)/2].hasStarted()) {
    					return true;
    				} else if(loserMatches == null) {
    					return true;
    				}
    			}
    		}
    		
    		i++;
    		
    	}
    	
    	return false;
    	
    }// ends morePlayersAllowed()
    
    /**
     * Creates the pattern needed to insert players into the 
     * bracket in a way that keeps the bracket evenly distributed.
     * This function assumes that the argument will be a power
     * of 2.
     * 
     * @param num The number of matches in the round.
     * Must be a power of 2.
     * @return An int[] containing the numbers
     * of the matches in the order they are to be inserted
     * into or played starting from 0.
     */
    public int[] pattern(int num) {
    	
    	int[] order = new int[num];
    	for(int i=0; i<num; i++) {
    		order[i] = i;
    	}
    	
    	int[] temp = new int[num];
    	
    	// For each round
    	for(int i=0; i<Math.log(num)/Math.log(2); i++) {
    		
    		// For how many groups of players will be used in this round
    		for(int j=0; j<Math.pow(2, Math.log(num)/Math.log(2) - i); j++) {
    			
    			// Select groups from the beginning
	    		if(j%2 == 0) {
	    			
	    			// For how many players are contained in each group
	    			for(int k=0; k<Math.pow(2, i); k++) {
	    				
	    				temp[(int) (j*Math.pow(2, i)+k)] = order[(int) (j/2*Math.pow(2, i) + k)];
	    				
	    			}
	    		// Select groups from the end		
	    		} else {

	    			// For how many players are contained in each group
	    			for(int k=0; k<Math.pow(2, i); k++) {
	    				
	    				temp[(int) (j*Math.pow(2, i)+k)] = order[(int) (order.length - (j+1)/2*Math.pow(2, i) + k)];
	    				
	    			}
	    				
	    		}
    		
    		}
    		
    		order = temp;
    		temp = new int[num];
    		
    	}
    	
    	// Swap the keys and values.
    	for(int i=0; i<order.length; i++) {
    		for(int j=0; j<order.length; j++) {
    			if(order[i] == j) {
    				temp[j] = i;
    			}
    		}
    	}
    	
    	order = temp;
    	
    	return order;
    	
    }// ends pattern()
    
    /**
     * Resets the LinkedList<Match>.
     */
    public abstract void resetLLMatches();
    
    /**
	 * Resets prizes in the event of undoing a split.
	 */
    public abstract void resetPrizes();
	
	/**
	 * Evenly distributes players randomly into the bracket.
	 */
	public void setPlayers(Player[] players) {
		
		DrawPerformer draw = new DrawPerformer(this, players);
		draw.performDraw();
		
	}// ends setPlayers()

}// ends Class
