package com.etan.bracketrunner2.matches;

import com.etan.bracketrunner2.Main;

public class SingleMatch extends Match {

	public SingleMatch(int round, int num) {
		
		super(round, num, true);
		
	}// ends SingleMatch()
	
	@Override
	public void end(int p1Score, int p2Score) {
		
		super.end(p1Score, p2Score);
		if(!split) {
			loser.setActive(false);
		}
		
	}// ends end()

	@Override
	public String getName() {
		
		return String.valueOf((char)(round+64)).concat(Integer.toString(num));
		
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
		
		if(!isFinalMatch() && 
				!Main.getControlPanel().getBracket().getMatches()[round+1][(num+1)/2].isFinished()) {
			
			return true;
			
		} else if(isFinalMatch()) {
			
			return true;
			
		}
		
		return false;
		
	}// ends isUndoable()

	@Override
	public void setPlayersIntoNextMatch() {
		
		// Set the winner into the next match
		if(!isFinalMatch()) {
			if(num % 2 == 1) {
				Main.getControlPanel().getBracket().getMatches()[round+1][(num+1)/2].setP1(winner);
			} else {
				Main.getControlPanel().getBracket().getMatches()[round+1][num/2].setP2(winner);
			}
		}
		
	}// ends setPlayersIntoNextMatch()
	
	@Override
	public void undo() {
		
		if(!isFinalMatch()) {
			if(num % 2 == 1) {
				Main.getControlPanel().getBracket().getMatches()[round+1][(num+1)/2].setP1(null);
			} else {
				Main.getControlPanel().getBracket().getMatches()[round+1][num/2].setP2(null);
			}
		}
		
		if(!isFinalMatch()) {
			winner.undoHistory();
		}
		
		super.undo();
		
	}// ends undo()

}// ends Class
