package com.etan.bracketrunner2.matches;

import com.etan.bracketrunner2.Main;

public class ModifiedWinnersMatch extends Match {

	/**
	 * Constructor
	 * 
	 * @param round The round of this match.
	 * @param num The number of the match within the round.
	 */
	public ModifiedWinnersMatch(int round, int num) {
		
		super(round, num, true);
		
	}// ends ModifiedWinnersMatch()
	
	@Override
	public void end(int p1Score, int p2Score) {
		
		super.end(p1Score, p2Score);
		
		if(round > 2 && !split) {
			loser.setActive(false);
		}
		
	}// ends end()

	@Override
	public String getName() {
		
		return "W" + String.valueOf((char)(round+64)).concat(Integer.toString(num));
		
	}// ends getName()
	
	@Override
	public boolean isP1Bye() {
		
		return false;
		
	}// ends isP1Bye()
	
	@Override
	public boolean isP2Bye() {
		
		if(round == 1 && p2 == null) {
			return true;
		}
		
		return false;
		
	}// ends isP2Bye()

	@Override
	public boolean isUndoable() {
		
		Match[][] matches = Main.getControlPanel().getBracket().getMatches();
		Match[][] loserMatches = Main.getControlPanel().getBracket().getLoserMatches();
		if(round == 1) {
			if(matches[2][(num+1)/2].isFinished()) {
				return false;
			} else if(loserMatches[1][(num+1)/2].isFinished()) {
				return false;
			}
		} else if(round == 2) {
			if(matches[3][(num+1)/2].isFinished()) {
				return false;
			} else if(loserMatches[2][Main.getControlPanel().getBracket().getFullBracket()/4-num+1].isFinished()) {
				return false;
			}
		} else if (round == 3) {
			if(matches[4][num].isFinished()) {
				return false;
			}
		} else {
			if(round != Main.getControlPanel().getBracket().getRounds() &&
					matches[round+1][(num+1)/2].isFinished()) {
				return false;
			}
		}
		
		return true;
		
	}// ends isUndoable()

	@Override
	public void setPlayersIntoNextMatch() {
		
		// Set the winner into the next match
		Match[][] matches = Main.getControlPanel().getBracket().getMatches();
		if(round == 3) {
			matches[4][num].setP1(winner);
		} else if(round != Main.getControlPanel().getBracket().getRounds()) {
			if(num % 2 == 1) {
				matches[round+1][(num+1)/2].setP1(winner);
			} else {
				matches[round+1][(num+1)/2].setP2(winner);
			}
		}
		
		// Set the loser into the next match
		Match[][] loserMatches = Main.getControlPanel().getBracket().getLoserMatches();
		if(round == 1) {
			if(num % 2 == 1) {
				loserMatches[1][(num+1)/2].setP1(loser);
			} else {
				loserMatches[1][(num+1)/2].setP2(loser);
			}
		} else if(round == 2) {
			loserMatches[2][Main.getControlPanel().getBracket().getFullBracket()/4-num+1].setP2(loser);
		}
		
	}// ends setPlayersIntoNextMatch()
	
	@Override
	public void undo() {
		
		// Winners side matches
		if(round == 3) {
			
			Main.getControlPanel().getBracket().getMatches()[4][num].setP1(null);
			
		} else if(round != Main.getControlPanel().getRounds()) {
			
			if(num % 2 == 1) {
				Main.getControlPanel().getBracket().getMatches()[round+1][(num+1)/2].setP1(null);
			} else {
				Main.getControlPanel().getBracket().getMatches()[round+1][num/2].setP2(null);
			}
			
		}
		
		// Losers side matches
		if(round == 1) {
			
			if(num % 2 == 1) {
				Main.getControlPanel().getBracket().getLoserMatches()[1][(num+1)/2].setP1(null);
			} else {
				Main.getControlPanel().getBracket().getLoserMatches()[1][num/2].setP2(null);
			}
			
		} else if(round == 2) {
			
			Main.getControlPanel().getBracket().getLoserMatches()[2][Main.getControlPanel().getBracket().getFullBracket()/4-num+1].setP2(null);
			
		}
		
		// Remove matches from players histories
		if(!isFinalMatch()) {
			winner.undoHistory();
			loser.undoHistory();
			if(loser.getCurrentMatch().isP1Bye()
					|| loser.getCurrentMatch().isP2Bye()) {
				loser.undoHistory();
			}
		}
		
		super.undo();
		
	}// ends undo()

}// ends Class
