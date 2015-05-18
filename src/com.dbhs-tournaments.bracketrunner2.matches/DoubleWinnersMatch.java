package com.etan.bracketrunner2.matches;

import com.etan.bracketrunner2.Main;

public class DoubleWinnersMatch extends Match {

	public DoubleWinnersMatch(int round, int num) {
		
		super(round, num, true);
		
	}// ends DoubleWinnersMatch()
	
	@Override
	public void end(int p1Score, int p2Score) {
				
		super.end(p1Score, p2Score);
		
		if(Main.getControlPanel().getFinalMatch().equals(this)) {
			
			if(Main.getControlPanel().getFinalsFormat() == 1
					|| loser.getTotalMatches() - loser.getMatchesWon() == 2
					|| num == 2) {
				
				loser.setActive(false);
				
			}
			
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
		
		// Final match
		if(round == Main.getControlPanel().getBracket().getRounds() &&
				Main.getControlPanel().getFinalsFormat() == 2 && num == 1) {
			
			if(matches[round][2] != null && matches[round][2].isFinished()) {
				return false;
			}
			
		// Other matches
		} else {
			if(round != Main.getControlPanel().getBracket().getRounds()) {
				if(matches[round+1][(num+1)/2].isFinished()) {
					return false;
				} else if(round == 1) {
					if(loserMatches[1][(num+1)/2].isFinished()) {
						return false;
					}
				} else if(round == 2) {
					if(loserMatches[2][Main.getControlPanel().getBracket().getFullBracket()/4-num+1].isFinished()) {
						return false;
					}
				} else {
					if(loserMatches[(round-1)*2][num].isFinished()) {
						return false;
					}
				}
			}
		}
		
		return true;
		
	}// ends isUndoable()

	@Override
	public void setPlayersIntoNextMatch() {
		
		Match[][] matches = Main.getControlPanel().getBracket().getMatches();
		Match[][] loserMatches = Main.getControlPanel().getBracket().getLoserMatches();
		
		// Set up the second final match
		if(round == Main.getControlPanel().getRounds() &&
				Main.getControlPanel().getFinalsFormat() == 2 && num == 1
				&& loser.getTotalMatches() - loser.getMatchesWon() == 1) {
			
			Match match = MatchFactory.createMatch(round, 2, MatchFactory.DOUBLE_WINNERS);
			match.setMoney(money);
			money = 0;
			match.setP1(p1);
			match.setP2(p2);
			
			Main.getControlPanel().getBracket().getMatches()[round][2] = match;
			Main.getControlPanel().getBracket().getLLMatches().add(match);
			
		} else {
			
			// Set the winner into the next match
			if(round != Main.getControlPanel().getRounds()) {
				if(num % 2 == 1) {
					matches[round+1][(num+1)/2].setP1(winner);
				} else {
					matches[round+1][(num+1)/2].setP2(winner);
				}
			}
			
			// Set the loser into the next match
			if(round != Main.getControlPanel().getRounds()) {
				if(round == 1) {
					if(num % 2 == 1) {
						loserMatches[1][(num+1)/2].setP1(loser);
					} else {
						loserMatches[1][(num+1)/2].setP2(loser);
					}
				} else if(round == 2) {
					loserMatches[2][Main.getControlPanel().getBracket().getFullBracket()/4-num+1].setP2(loser);
				} else {
					loserMatches[(round-1)*2][num].setP2(loser);
				}
			}
			
		}
		
	}// setPlayersIntoNextMatch()
	
	@Override
	public void undo() {
		
		// Remove matches from players histories
		if(!isFinalMatch()) {
			winner.undoHistory();
			loser.undoHistory();
			if(!loser.getCurrentMatch().isWinnersSide()
					&& (loser.getCurrentMatch().isP1Bye()
					|| loser.getCurrentMatch().isP2Bye())) {
				loser.undoHistory();
			}
		}
		
		// Reset next match
		if(round != Main.getControlPanel().getRounds()) {
			// Winners side matches
			if(num % 2 == 1) {
				Main.getControlPanel().getBracket().getMatches()[round+1][(num+1)/2].setP1(null);
			} else {
				Main.getControlPanel().getBracket().getMatches()[round+1][num/2].setP2(null);
			}
			
			// Losers side matches
			if(round == 1) {
				if(num % 2 == 1) {
					Main.getControlPanel().getBracket().getLoserMatches()[1][(num+1)/2].setP1(null);
					if((num+1)/2 % 2 == 1) {
						Main.getControlPanel().getBracket().getLoserMatches()[2][(num+1)/2].setP1(null);
					} else {
						Main.getControlPanel().getBracket().getLoserMatches()[2][(num+1)/2].setP2(null);
					}
					Main.getControlPanel().getBracket().getLoserMatches()[3][((num+1)/2+1)/2].setP1(null);
				} else {
					Main.getControlPanel().getBracket().getLoserMatches()[1][num/2].setP2(null);
					if((num+1)/2 % 2 == 1) {
						Main.getControlPanel().getBracket().getLoserMatches()[2][num/2].setP1(null);
					} else {
						Main.getControlPanel().getBracket().getLoserMatches()[2][num/2].setP2(null);
					}
					Main.getControlPanel().getBracket().getLoserMatches()[3][(num/2+1)/2].setP1(null);
				}
			} else {
				Main.getControlPanel().getBracket().getLoserMatches()[(round-1)*2][(num+1)/2].setP2(null);
			}
		} else if(Main.getControlPanel().getBracket().getMatches()[round][2] != null && num == 1) {
			money = Main.getControlPanel().getBracket().getMatches()[round][2].getMoney();
			Main.getControlPanel().getBracket().getLLMatches().removeLast();
			Main.getControlPanel().getBracket().getMatches()[round][2] = null;
		}
		
		super.undo();
		
	}// ends undo()

}// ends Class
