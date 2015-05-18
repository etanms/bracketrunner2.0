package com.etan.bracketrunner2.matches;

import java.awt.Rectangle;

import com.etan.bracketrunner2.Main;
import com.etan.bracketrunner2.Player;
import com.etan.bracketrunner2.TablePanel;

import com.etan.dbhs.DBHSMatchHandicapper;

/**
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 23, 2014
 *
 */
public abstract class Match {
	
	protected int round;
	protected int num;
	protected boolean winnersSide;
	protected int spot;
	protected int race;
	protected Player p1;
	protected float p1Average;
	protected float p1Elo;
	protected float p1HighestAverage;
	protected float p1HighestElo;
	protected float p1LowestAverage;
	protected float p1LowestElo;
	protected int p1Score;
	protected Player p2;
	protected float p2Average;
	protected float p2Elo;
	protected float p2HighestAverage;
	protected float p2HighestElo;
	protected float p2LowestAverage;
	protected float p2LowestElo;
	protected int p2Score;
	protected float percentage;
	protected boolean started;
	protected boolean finished;
	protected int money;
	protected boolean split;
	protected boolean forfeit;
	protected Player winner;
	protected Player loser;
	
	protected Rectangle rect;// Used to determine if the match has been clicked on.
	
	/**
	 * Constructor
	 */
	public Match(int round, int num, boolean winnersSide) {
		
		this.round = round;
		this.num = num;
		this.winnersSide = winnersSide;
		
	}// ends Match()
	
	/**
	 * Handles all necessary data when a match is finished.
	 * Should be overriden in all subclasses to set players
	 * to inactive if necessary.
	 */
	public void end(int p1Score, int p2Score) {
		
		// Set the score to the match and update player data.
		this.p1Score = p1Score;
		this.p2Score = p2Score;
		
		// Set winner and loser.
		if(!forfeit && !split) {
			if(p1Score > p2Score) {
				winner = p1;
				loser = p2;
			} else {
				winner = p2;
				loser = p1;
			}
		}
		
		setPlayersIntoNextMatch();
		
		finished = true;
		
		Main.getControlPanel().updateInfo();
		
	}// ends end()
	
	/**
	 * @return The player who lost this match.
	 */
	public Player getLoser() {
		
		return loser;
		
	}// ends getLoser()
	
	/**
	 * @return The difference in guaranteed money between the winner and loser of this match.
	 */
	public int getMoney() {
		
		return money;
		
	}// ends getMoney();
	
	/**
	 * @return A String representation of the name of the match. 
	 * The round would be represented by the corresponding letter 
	 * of the alphabet while the match number would be represented 
	 * as a number. If needed a "W" or an "L" would be added to the 
	 * beginning to specify whether or not the match is on the winners' 
	 * side.
	 */
	public abstract String getName();
	
	/**
	 * @return The number of this match within the round it's in.
	 */
	public int getNum() {
		
		return num;
		
	}// ends getNum()
	
	/**
	 * @return Player 1.
	 */
	public Player getP1() {
		
		return p1;
		
	}// ends getP1()
	
	/**
	 * @return Player 1's average.
	 */
	public float getP1Average() {
		
		return p1Average;
		
	}// ends getP1Average()
	
	/**
	 * @return Player 1's elo.
	 */
	public float getP1Elo() {
		
		return p1Elo;
		
	}// ends getP1Elo()
	
	/**
	 * @return Player 1's highest average ever reached.
	 */
	public float getP1HighestAverage() {
		
		return p1HighestAverage;
		
	}// ends getP1HighestAverage()
	
	/**
	 * @return Player 1's highest elo ever reached.
	 */
	public float getP1HighestElo() {
		
		return p1HighestElo;
		
	}// ends getP1HighestElo()
	
	/**
	 * @return Player 1's lowest average ever reached.
	 */
	public float getP1LowestAverage() {
		
		return p1LowestAverage;
		
	}// ends getP1LowestAverage()
	
	/**
	 * @return Player 1's lowest elo ever reached.
	 */
	public float getP1LowestElo() {
		
		return p1LowestElo;
		
	}// ends getP1LowestElo()
	
	/**
	 * @return The score of player 1 including spotted games.
	 */
	public int getP1Score() {
		
		return p1Score;
		
	}// ends getP1Score()
	
	/**
	 * @return Player 2.
	 */
	public Player getP2() {
		
		return p2;
		
	}// ends getP2()
	
	/**
	 * @return Player 2's average.
	 */
	public float getP2Average() {
		
		return p2Average;
		
	}// ends getP2Average()
	
	/**
	 * @return Player 2's elo.
	 */
	public float getP2Elo() {
		
		return p2Elo;
		
	}// ends getP2Elo()
	
	/**
	 * @return Player 2's highest average ever reached.
	 */
	public float getP2HighestAverage() {
		
		return p2HighestAverage;
		
	}// ends getP2HighestAverage()
	
	/**
	 * @return Player 2's highest elo ever reached.
	 */
	public float getP2HighestElo() {
		
		return p2HighestElo;
		
	}// ends getP2HighestElo()
	
	/**
	 * @return Player 2's lowest average ever reached.
	 */
	public float getP2LowestAverage() {
		
		return p2LowestAverage;
		
	}// ends getP2LowestAverage()
	
	/**
	 * @return Player 2's lowest elo ever reached.
	 */
	public float getP2LowestElo() {
		
		return p2LowestElo;
		
	}// ends getP2LowestElo()
	
	/**
	 * @return The score of player 2 including spotted games.
	 */
	public int getP2Score() {
		
		return p2Score;
		
	}// ends getP2Score()
	
	/**
	 * @return The mathematically expected win percentage of
	 * the higher ranked player.
	 */
	public float getPercentage() {
		
		return percentage;
		
	}// ends getPercentage()
	
	/**
	 * @return The length of the race.
	 */
	public int getRace() {
		
		return race;
		
	}// ends getRace()
	
	/**
	 * @return A rectagle closely containing the visual representation
	 * of the match on the screen.
	 */
	public Rectangle getRect() {
		
		return rect;
		
	}// ends getRect()
	
	/**
	 * @return The round of the tournament of this match. Does not specify winner's side or loser's side.
	 */
	public int getRound() {
		
		return round;
		
	}// end getRound()
	
	/**
	 * @return The amount of games spotted to the lower handicap player.
	 */
	public int getSpot() {
		
		return spot;
		
	}// ends getSpot()
	
	/**
	 * @return The name of the table on which this match is 
	 * being played. If no table is specified, this method 
	 * will return null.
	 */
	public String getTable() {
		
		TablePanel[] tables = Main.getControlPanel().getTables();
		for(int i=0; i<tables.length; i++) {
			if(tables[i].getMatch() != null && tables[i].getMatch().equals(this)) {
				return tables[i].getTableName();
			}
		}
		
		return null;
		
	}// ends getTable
	
	/**
	 * @return The player who won this match.
	 */
	public Player getWinner() {
		
		return winner;
		
	}// ends getWinner()
	
	/**
	 * @return True if the match has started.
	 */
	public boolean hasStarted() {
		
		return started;
		
	}// ends hasStarted()
	
	/**
	 * @return Returns true if the match is considered a final within the format of the tournament.
	 */
	public boolean isFinalMatch() {
		
		if(this.equals(Main.getControlPanel().getBracket().getFinalMatch())) {
			return true;
		}
		
		return false;
		
	}// ends isFinalMatch()
	
	/**
	 * @return True if the match has finished.
	 */
	public boolean isFinished() {
		
		return finished;
		
	}// ends isFinished()
	
	/**
	 * @return True if this match was ended due to a forfeit.
	 */
	public boolean isForfeit() {
		
		return forfeit;
		
	}// ends isForfeit()
	
	/**
	 * @return True if their's currently no possibility
	 * of player 1 not being null in the future with the 
	 * exception of a player joining the tournament 
	 * after it has started.
	 */
	public abstract boolean isP1Bye();
	
	/**
	 * @return True if their's currently no possibility
	 * of player 2 not being null in the future with the 
	 * exception of a player joining the tournament 
	 * after it has started.
	 */
	public abstract boolean isP2Bye();
	
	/**
	 * @return True if this match was ended due to a split.
	 */
	public boolean isSplit() {
		
		return split;
			
	}// ends isSplit()
	
	/**
	 * @return True if all matches depending on the outcome of this
	 * one have not started.
	 */
	public abstract boolean isUndoable();
	
	/**
	 * @return Whether or not this match is a winners' side match.
	 */
	public boolean isWinnersSide() {
		
		return winnersSide;
		
	}// ends isWinnersSide()
	
	/**
	 * Sets the race and spot as necessary.
	 * p1 and p2 must not be null when this is called.
	 */
	void setDetails() {
		
		// If the races use the handicap system.
		if(Main.getControlPanel().getRace() == 0) {
			
			DBHSMatchHandicapper dh = new DBHSMatchHandicapper(this);
			dh.setDetails();
			
		// If the races are to a specific number with no handicap.
		} else {
			
			race = Main.getControlPanel().getRace();
			spot = 0;
			
		}
		
	}// ends setDetails()
	
	/**
	 * @param finished Whether or not the match if finished.
	 */
	public void setFinished(boolean finished) {
		
		this.finished = finished;
		
	}// ends setFinished()
	
	/**
	 * @param forfeit Whether or not the match was ended due to a forfeit.
	 */
	public void setForfeit(boolean forfeit) {
		
		this.forfeit = forfeit;
		
	}// ends setForfeit()
	
	/**
	 * @param loser The player who lost this match.
	 */
	public void setLoser(Player loser) {
		
		this.loser = loser;
		
	}// ends getLoser
	
	/**
	 * @param money The difference to be set in guaranteed money between the winner and loser of this match.
	 */
	public void setMoney(int money) {
		
		this.money = money;
		
	}// ends setMoney()
	
	/**
	 * @param p1 The Player to be set to player1 of this match.
	 */
	public void setP1(Player p1) {
				
		this.p1 = p1;
		
		if(p1 != null) {
			p1.setCurrentMatch(this);
			p1Elo = Float.valueOf(p1.getElo());
			p1HighestElo = Float.valueOf(p1.getHighestElo());
			p1LowestElo = Float.valueOf(p1.getLowestElo());
			p1Average = Float.valueOf(p1.getAverage());
			p1HighestAverage = Float.valueOf(p1.getHighestAverage());
			p1LowestAverage = Float.valueOf(p1.getLowestAverage());
			
			if(p2 != null) {
				setDetails();
			}
		}
		
	}// ends setP1()
	
	/**
	 * @param avg Float to set p1Average to.
	 */
	public void setP1Average(float avg) {
		
		p1Average = avg;
		
	}// ends setP1Average()
	
	/**
	 * @param handicap Float to set p1elo to.
	 */
	public void setP1Elo(float handicap) {
		
		p1Elo = handicap;
		
		if(p2 != null) {
			setDetails();
		}
		
	}// ends setP1Elo()
	
	
	/**
	 * @param highest Float to set p1HighestAverage to.
	 */
	public void setP1HighestAverage(float highest) {
		
		p1HighestAverage = highest;
		
	}// ends setP1HighestAverage()
	
	
	/**
	 * @param highest Float to set p1HighestElo to.
	 */
	public void setP1HighestElo(float highest) {
		
		p1HighestElo = highest;
		
	}// ends setP1HighestElo()
	
	/**
	 * @param lowest Float to set p1LowestAverage to.
	 */
	public void setP1LowestAverage(float lowest) {
		
		p1LowestAverage = lowest;
		
	}// ends setP1LowestAverage()
	
	/**
	 * @param lowest Float to set p1LowestElo to.
	 */
	public void setP1LowestElo(float lowest) {
		
		p1LowestElo = lowest;
		
	}// ends setP1LowestElo()
	
	/**
	 * @param score The score with which to set player1's score equal to.
	 */
	public void setP1Score(int score) {
		
		p1Score = score;
		
	}// ends setP1Score()
	
	/**
	 * @param p2 The Player to be set to player2 of this match.
	 */
	public void setP2(Player p2) {
				
		this.p2 = p2;
		
		if(p2 != null) {
			p2.setCurrentMatch(this);
			p2Elo = Float.valueOf(p2.getElo());
			p2HighestElo = Float.valueOf(p2.getHighestElo());
			p2LowestElo = Float.valueOf(p2.getLowestElo());
			p2Average = Float.valueOf(p2.getAverage());
			p2HighestAverage = Float.valueOf(p2.getHighestAverage());
			p2LowestAverage = Float.valueOf(p2.getLowestAverage());
			
			if(p1 != null) {
				setDetails();
			}
		}
		
	}// ends setP2()
	
	/**
	 * @param avg Float to set p2Average to.
	 */
	public void setP2Average(float avg) {
		
		p2Average = avg;
		
	}// ends setP2Average()
	
	/**
	 * @param handicap Float to set p2Elo to.
	 */
	public void setP2Elo(float handicap) {
		
		p2Elo = handicap;
		
		if(p1 != null) {
			setDetails();
		}
		
	}// ends setP2Elo()
	
	/**
	 * @param highest Float to set p2HighestAverage to.
	 */
	public void setP2HighestAverage(float highest) {
		
		p2HighestAverage = highest;
		
	}// ends setP2HighestAverage()
	
	/**
	 * @param highest Float to set p2HighestElo to.
	 */
	public void setP2HighestElo(float highest) {
		
		p2HighestElo = highest;
		
	}// ends setP2HighestElo()
	
	/**
	 * @param lowest Float to set p2LowestAverage to.
	 */
	public void setP2LowestAverage(float lowest) {
		
		p2LowestAverage = lowest;
		
	}// ends setP2LowestAverage()
	
	/**
	 * @param lowest Float to set p2LowestElo to.
	 */
	public void setP2LowestElo(float lowest) {
		
		p2LowestElo = lowest;
		
	}// ends setP2LowestElo()
	
	/**
	 * @param score The score with which to set player2's score equal to.
	 */
	public void setP2Score(int score) {
		
		p2Score = score;
		
	}// ends setP2Score()
	
	/**
	 * @param p The mathematically expected win percentage
	 * of the higher ranked player.
	 */
	public void setPercentage(float p) {
		
		percentage = p;
		
	}// ends setPercentage()
	
	/**
	 * Sets each player into their next match.
	 */
	public abstract void setPlayersIntoNextMatch();
	
	/**
	 * @param race The length of the race for this match.
	 */
	public void setRace(int race) {
		
		this.race = race;
		Main.getControlPanel().updateInfo();
		
	}// ends setRace()
	
	/**
	 * @param rect The clickable area to associate with this match.
	 */
	public void setRect(Rectangle rect) {
		
		this.rect = rect;
		
	}// ends setRect()
	
	/**
	 * @param split Whether or not the match was ended due to a split.
	 */
	public void setSplit(boolean split) {
		
		this.split = split;
		
	}// ends setSplit()
	
	/**
	 * @param spot The amount of games spotted to one of the players.
	 */
	public void setSpot(int spot) {
		
		this.spot = spot;
		Main.getControlPanel().updateInfo();
		
	}// ends setSpot()
	
	/**
	 * @param started Whether or not the match has started.
	 */
	public void setStarted(boolean started) {
		
		this.started = started;
		
	}// ends setStarted()
	
	/**
	 * @param winner The player who won this match.
	 */
	public void setWinner(Player winner) {
		
		this.winner = winner;
		
	}// ends setWinner()
	
	/**
	 * Undoes effects from end()
	 */
	public void undo() {
		
		// Set players handicaps back to what they were before this match.
		p1.setElo(p1Elo);
		p1.setHighestElo(p1HighestElo);
		p1.setLowestElo(p1LowestElo);
		p2.setElo(p2Elo);
		p2.setHighestElo(p2HighestElo);
		p2.setLowestElo(p2LowestElo);
		
		// Undo winner and loser.
		loser.setActive(true);
		winner = null;
		loser = null;

		// Reset the money in case of undoing a split.
		Main.getControlPanel().getBracket().resetPrizes();
		
		p1Score = 0;
		p2Score = 0;
		forfeit = false;
		split = false;
		started = false;
		finished = false;
		
		Main.getControlPanel().updateInfo();
		
	}// ends undo()

}// ends Class
