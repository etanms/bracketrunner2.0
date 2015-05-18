package com.etan.bracketrunner2;

import java.util.ArrayList;

import com.etan.bracketrunner2.matches.Match;
import com.etan.dbhs.DBHSMatchHandicapper;

/**
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 23, 2014
 *
 */
public class Player {
	
	private String firstName;
	private String lastName;
	private int id;
	private float elo;
	private float highestElo;
	private float lowestElo;
	private float average;
	private float highestAverage;
	private float lowestAverage;
	private boolean established;
	private int previousMatches;
	private int splitGroup;
	private boolean active;
	private ArrayList<Match> matches;

	/**
	 * Constructor
	 * 
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param elo
	 * @param highestElo
	 * @param lowestElo
	 * @param established
	 * @param splitGroup
	 */
	public Player(String firstName, String lastName, int id,
			float elo, float highestElo, float lowestElo,
			float average, float highestAverage, float lowestAverage,
			boolean established, int splitGroup) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.elo = elo;
		this.highestElo = highestElo;
		this.lowestElo = lowestElo;
		this.average = average;
		this.highestAverage = highestAverage;
		this.lowestAverage = lowestAverage;
		this.established = established;
		this.splitGroup = splitGroup;
		active = true;
		matches = new ArrayList<Match>();

	}// ends Player()
	
	/**
	 * @return The player's current average.
	 */
	public float getAverage() {
		
		return average;
		
	}// ends getAverage()
	
	/**
	 * @return The match that this player is currently playing or waiting to start.
	 */
	public Match getCurrentMatch() {
		
		if(matches.size() > 0) {
			return matches.get(matches.size()-1);
		} else {
			return null;
		}
		
	}// ends getCurrentMatch()
	
	/**
	 * @return The player's first name.
	 */
	public String getFirstName() {
		
		return firstName;
		
	}// ends getFirstName()
	
	/**
	 * @return The number of games won by this player (not including spotted games).
	 */
	public int getGamesWon() {
		
		int gamesWon = 0;
		
		for(int i=0; i<matches.size(); i++) {
			
			Match match = matches.get(i);
			if(match.isFinished()) {
				if(match.getP1().equals(this)) {
					
					if(match.getP1Elo() < match.getP2Elo()) {
						gamesWon += match.getP1Score() - match.getSpot();
					} else {
						gamesWon += match.getP1Score();
					}
					
				} else {
					
					if(match.getP1Elo() < match.getP2Elo()) {
						gamesWon += match.getP2Score();
					} else {
						gamesWon += match.getP2Score() - match.getSpot();
					}
					
				}
			}
			
		}
		
		return gamesWon;
		
	}// ends getGamesWon()
	
	/**
	 * @return The player's elo.
	 */
	public float getElo() {
		
		return elo;
		
	}// ends getElo()
	
	/**
	 * @return The highest average the player has ever reached.
	 */
	public float getHighestAverage() {
		
		return highestAverage;
		
	}// ends getHighestAverage()
	
	/**
	 * @return The player's highest elo ever reached.
	 */
	public float getHighestElo() {
		
		return highestElo;
		
	}// ends getHighestElo()
	
	/**
	 * @return The player's id number.
	 */
	public int getId() {
		
		return id;
		
	}// ends getId()
	
	/**
	 * @return The player's last name.
	 */
	public String getLastName() {
		
		return lastName;
		
	}// ends getLastName()
	
	/**
	 * @return The lowest average the player has ever reached.
	 */
	public float getLowestAverage() {
		
		return lowestAverage;
		
	}// ends getLowestAverage()
	
	/**
	 * @return The player's lowest elo ever reached.
	 */
	public float getLowestElo() {
		
		return lowestElo;
		
	}// ends getLowestElo()
	
	/**
	 * @return The players match history as an ArrayList.
	 */
	public ArrayList<Match> getMatches() {
		
		return matches;
		
	}// ends getMatches()
	
	/**
	 * @return The number of matches won by this player.
	 */
	public int getMatchesWon() {
		
		int matchesWon = 0;
		
		for(int i=0; i<matches.size(); i++) {
			
			Match match = matches.get(i);
			if(!match.isForfeit() && !match.isSplit() && match.isFinished()) {
				if(match.getP1() != null && match.getP1().equals(this) 
						&& match.getP1Score() > match.getP2Score()) {
					
					matchesWon++;
					
				} else if(match.getP2() != null && match.getP2().equals(this) 
						&& match.getP2Score() > match.getP1Score()) {
					
					matchesWon++;
					
				}
			}
			
		}
		
		return matchesWon;
		
	}// ends getMatchesWon()
	
	/**
	 * @return The amount of money the player is guaranteed to win.
	 */
	public int getMoney() {
		
		ControlPanel panel = Main.getControlPanel();
		
		int money = 0;
		
		// Add money if all players are paid
		if(panel.getPlacesPaid()[panel.getPrizes().length - 1] == panel.getTotalPlayers()) {
			money += panel.getPrizes()[panel.getPrizes().length - 1];
		}
		
		// Add money from each match won
		for(int i=0; i<matches.size(); i++) {
			Match match = matches.get(i);
			if((match.getWinner() != null && match.getWinner().equals(this)) 
					|| (match.isP1Bye() || match.isP2Bye())) {
				money += matches.get(i).getMoney();
			}
		}
		
		return money;
		
	}// ends getMoney()
	
	/**
	 * @return The number of matches played by this player before the start of the tournament.
	 */
	public int getPreviousMatches() {
		
		return previousMatches;
		
	}// ends getPreviousMatches()
	
	public int getSplitGroup() {
		
		return splitGroup;
		
	}// ends getSplitGroup()
	
	/**
	 * @return The number of games played by this player (not including spotted games).
	 */
	public int getTotalGames() {
		
		int totalGames = 0;
		
		for(int i=0; i<matches.size(); i++) {
			
			Match match = matches.get(i);
			if(match.isFinished()) {
				if(match.isForfeit() && match.getP1Score() == 0 && match.getP2Score() == 0) {
					;
				} else if(!match.isSplit()) {
					totalGames += match.getP1Score() + match.getP2Score() - match.getSpot();
				}
			}
			
		}
		
		return totalGames;
		
	}// ends getTotalGames()
	
	/**
	 * @return The number of matches played by this player including forfeited and split matches.
	 */
	public int getTotalMatches() {
		
		int totalMatches = 0;
		
		for(int i=0; i<matches.size(); i++) {
			
			Match match = matches.get(i);
			if(!match.isForfeit() && !match.isSplit() && match.isFinished()) {
				if(match.getP1() != null && match.getP2() != null && !match.isSplit()) {
					
					if(match.isForfeit() && match.getP1Score() == 0 && match.getP2Score() == 0) {
						;
					} else {
						totalMatches++;
					}
					
				}
			}
			
		}
		
		return totalMatches;
		
	}// ends getTotalMatches()
	
	/**
	 * @return False if the player has been eliminated from
	 * the tournament, True otherwise.
	 */
	public boolean isActive() {
		
		return active;
		
	}// ends isActive()
	
	/**
	 * @return True if the player is already established. False otherwise.
	 */
	public boolean isEstablished() {
		
		return established;
		
	}// ends isEstablished()
	
	/**
	 * Sets whether or not the player is still active in the tournament.
	 */
	public void setActive(boolean bool) {
		
		active = bool;
		
	}// ends setActive()
	
	/**
	 * @param handicap The average to be set for this Player.
	 */
	public void setAverage(float handicap) {
		
		this.average = handicap;
		
		if(active && !Main.getControlPanel().getFinalMatch().isFinished()) {
			
			Match currentMatch = matches.get(matches.size()-1);
			
			if(!currentMatch.isFinished() && currentMatch.getP1() != null
					&& currentMatch.getP1().equals(this)) {
				currentMatch.setP1Average(handicap);
			} else if(!currentMatch.isFinished() && currentMatch.getP2() != null
					&& currentMatch.getP2().equals(this)) {
				currentMatch.setP2Average(handicap);
			}
			
		}
		
	}// ends setAverage()
	
	/**
	 * @param match The next match that this player is to play.
	 */
	public void setCurrentMatch(Match match) {
		
		matches.add(match);
		
	}// ends setCurrentMatch()
	
	/**
	 * @param established Wether or not the player should be established.
	 */
	public void setEstablished(boolean established) {
		
		this.established = established;
		
	}// ends setEstablished()
	
	/**
	 * @param handicap The elo to be set for this Player.
	 */
	public void setElo(float handicap) {
		
		this.elo = handicap;
		
		if(active && !Main.getControlPanel().getFinalMatch().isFinished()) {
			
			Match currentMatch = matches.get(matches.size()-1);
			
			if(!currentMatch.isFinished() && currentMatch.getP1() != null
					&& currentMatch.getP1().equals(this)) {
				currentMatch.setP1Elo(handicap);
				DBHSMatchHandicapper dmh = new DBHSMatchHandicapper(currentMatch);
				dmh.setDetails();
			} else if(!currentMatch.isFinished() && currentMatch.getP2() != null
					&& currentMatch.getP2().equals(this)) {
				currentMatch.setP2Elo(handicap);
				DBHSMatchHandicapper dmh = new DBHSMatchHandicapper(currentMatch);
				dmh.setDetails();
			}
			
		}
		
	}// ends setElo()
	
	/**
	 * @param highest The highest average to be set for this Player.
	 */
	public void setHighestAverage(float highest) {
		
		this.highestAverage = highest;
		
		Match currentMatch = matches.get(matches.size()-1);
		if(currentMatch.getP1() != null && currentMatch.getP1().equals(this)) {
			currentMatch.setP1HighestAverage(highest);
		} else if(currentMatch.getP2() != null && currentMatch.getP2().equals(this)) {
			currentMatch.setP2HighestAverage(highest);
		}
		
	}// ends setHighestAverage()
	
	/**
	 * @param highest The highest elo to be set for this Player.
	 */
	public void setHighestElo(float highest) {
		
		this.highestElo = highest;
		
		Match currentMatch = matches.get(matches.size()-1);
		if(currentMatch.getP1() != null && currentMatch.getP1().equals(this)) {
			currentMatch.setP1HighestElo(highest);
		} else if(currentMatch.getP2() != null && currentMatch.getP2().equals(this)) {
			currentMatch.setP2HighestElo(highest);
		}
		
	}// ends setHighestElo()
	
	/**
	 * @param lowest The lowest average to be set for this Player.
	 */
	public void setLowestAverage(float lowest) {
		
		this.lowestAverage = lowest;
		
		Match currentMatch = matches.get(matches.size()-1);
		if(currentMatch.getP1() != null && currentMatch.getP1().equals(this)) {
			currentMatch.setP1LowestAverage(lowest);
		} else if(currentMatch.getP2() != null && currentMatch.getP2().equals(this)) {
			currentMatch.setP2LowestAverage(lowest);
		}
		
	}// ends setLowestAverage()
	
	/**
	 * @param lowest The lowest elo to be set for this Player.
	 */
	public void setLowestElo(float lowest) {
		
		this.lowestElo = lowest;
		
		Match currentMatch = matches.get(matches.size()-1);
		if(currentMatch.getP1() != null && currentMatch.getP1().equals(this)) {
			currentMatch.setP1LowestElo(lowest);
		} else if(currentMatch.getP2() != null && currentMatch.getP2().equals(this)) {
			currentMatch.setP2LowestElo(lowest);
		}
		
	}// ends setLowestElo()
	
	/**
	 * @param previousMatches The number of matches of this
	 * tournament's game that this player has played in the 
	 * past.
	 */
	public void setPreviousMatches(int previousMatches) {
		
		this.previousMatches = previousMatches;
		
	}// ends setPreviousMatches()
	
	/**
	 * Removes the last match from this player's match
	 * history. This method should mainly be used when
	 * undoing the results of a match.
	 */
	public void undoHistory() {
		
		if(matches.size() > 0) {
			matches.remove(matches.size()-1);
		}
		
	}// ends undoHistory()
	
}// ends Class
