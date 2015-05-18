package com.etan.bracketrunner2.matches;

import com.etan.bracketrunner2.Main;
import com.etan.bracketrunner2.Player;

public class ModifiedLosersMatch extends Match {

	/**
	 * Constructor
	 * 
	 * @param round The round of this match.
	 * @param num The number of the match within the round.
	 */
	public ModifiedLosersMatch(int round, int num) {
		
		super(round, num, false);
		
	}// ends ModifiedLosersMatch()
	
	@Override
	public void end(int p1Score, int p2Score) {
		
		super.end(p1Score, p2Score);
		
		loser.setActive(false);
		
	}// ends end()

	@Override
	public String getName() {
		
		return "L" + String.valueOf((char)(round+64)).concat(Integer.toString(num));
		
	}// ends getName()
	
	@Override
	public boolean isP1Bye() {
		
		if(round == 1) {
			if(Main.getControlPanel().getBracket().getMatches()[1][num*2-1].isP2Bye()) {
				return true;
			}
		} else if(round == 2) {
			if(Main.getControlPanel().getBracket().getLoserMatches()[1][num].isP1Bye()
					&& Main.getControlPanel().getBracket().getLoserMatches()[1][num].isP2Bye()) {
				return true;
			}
		}
		
		return false;
		
	}// ends isP1Bye()
	
	@Override
	public boolean isP2Bye() {
		
		if(round == 1 && Main.getControlPanel().getBracket().getMatches()[1][num*2].isP2Bye()) {
			return true;
		}
		
		return false;
		
	}// ends isP2Bye()

	@Override
	public boolean isUndoable() {
		
		Match[][] matches = Main.getControlPanel().getBracket().getMatches();
		Match[][] loserMatches = Main.getControlPanel().getBracket().getLoserMatches();
		if(round == 3) {
			if(matches[4][num].isFinished()) {
				return false;
			}
		} else if(round == 2){
			if(loserMatches[3][(num+1)/2].isFinished()) {
				return false;
			}
		} else {
			if(loserMatches[2][num].isFinished()) {
				return false;
			}
		}
		
		return true;
		
	}// ends isUndoable()
	
	@Override
	public void setP1(Player p1) {
		
		super.setP1(p1);
		
		if(isP2Bye()) {
			Main.getControlPanel().getBracket().getLoserMatches()[2][num].setP1(p1);
		}
		
	}// ends setP1()
	


	@Override
	public void setP2(Player p2) {
		
		super.setP2(p2);
		
		if(isP1Bye()) {
			if(round == 1) {
				Main.getControlPanel().getBracket().getLoserMatches()[2][num].setP1(p2);
			} else if(num % 2 == 1){
				Main.getControlPanel().getBracket().getLoserMatches()[3][(num+1)/2].setP1(p2);
			} else {
				Main.getControlPanel().getBracket().getLoserMatches()[3][num/2].setP2(p2);
			}
		}
		
	}// ends setP1()

	@Override
	public void setPlayersIntoNextMatch() {
		
		// Set the winner into the next match
		Match[][] matches = Main.getControlPanel().getBracket().getMatches();
		Match[][] loserMatches = Main.getControlPanel().getBracket().getLoserMatches();
		if(round == 3) {
			matches[4][num].setP2(winner);
		} else if(round == 2){
			if(num % 2 == 1) {
				loserMatches[3][(num+1)/2].setP1(winner);
			} else {
				loserMatches[3][(num+1)/2].setP2(winner);
			}
		} else {
			loserMatches[2][num].setP1(winner);
		}
		
	}// ends setPlayersIntoNextMatch()
	
	@Override
	public void undo() {
		
		if(round == 1) {
			
			Main.getControlPanel().getBracket().getLoserMatches()[2][num].setP1(null);
			
		} else if(round == 2) {
			
			if(num % 2 == 1) {
				Main.getControlPanel().getBracket().getLoserMatches()[3][(num+1)/2].setP1(null);
			} else {
				Main.getControlPanel().getBracket().getLoserMatches()[3][num/2].setP2(null);
			}
			
		} else {
			
			Main.getControlPanel().getBracket().getMatches()[4][num].setP2(null);
			
		}
		
		winner.undoHistory();
		
		super.undo();
		
	}// ends undo()

}// ends Class
