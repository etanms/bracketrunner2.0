package com.etan.bracketrunner2.brackets;

import java.util.LinkedList;

import com.etan.bracketrunner2.Main;
import com.etan.bracketrunner2.Player;
import com.etan.bracketrunner2.matches.Match;
import com.etan.bracketrunner2.matches.MatchFactory;

class DoubleBracket extends Bracket {
	
	public DoubleBracket(int fullBracket) {
		
		super(fullBracket);
		
		totalRounds = (int)(Math.log(fullBracket)/Math.log(2) + 1);
		int[] prizes = Main.getControlPanel().getPrizes();
		
		// Create winners side matches
		matches = new Match[totalRounds + 1][(int)(Math.pow(2, totalRounds - 1) + 1)];
		for(int round = 1; round <= totalRounds-1; round++) {			
			for(int matchNum = 1; matchNum <= Math.pow(2, (totalRounds-1 - round)); matchNum++) {
				
				Match match = MatchFactory.createMatch(round, matchNum, MatchFactory.DOUBLE_WINNERS);
				
				// Set prizes
				if(totalRounds - round + 3 < prizes.length) {
					match.setMoney(prizes[totalRounds - round+1]-prizes[totalRounds - round + 3]);
				} else if(totalRounds - round + 2 < prizes.length) {
					match.setMoney(prizes[totalRounds - round + 1]);
				} else {
					match.setMoney(0);
				}
				
				if(round == 1 && Main.getControlPanel().getTotalPlayers() <= fullBracket * 3/4) {
					match.setMoney(0);
				}
				
				matches[round][matchNum] = match;
				
			}
		}
		
		// Create losers side matches
		loserMatches = new Match[(totalRounds-2) * 2 + 1][fullBracket / 4 + 1];
		for(int i=1; i<=(totalRounds-2)*2; i++) {
			for(int j=1; j<=Math.pow(2, ((totalRounds-2)*2-i)/2); j++) {
				
				Match match = MatchFactory.createMatch(i, j, MatchFactory.DOUBLE_LOSERS);
				
				// Set prizes
				if((totalRounds-2)*2-i+2 < prizes.length){
					match.setMoney(prizes[(totalRounds-2)*2-i+1] - prizes[(totalRounds-2)*2-i+2]);
				}
				else if((totalRounds-2)*2-i+1 < prizes.length){
					match.setMoney(prizes[(totalRounds-2)*2-i+1]);
				}
				else{
					match.setMoney(0);
				}
				
				if(i == 1 && Main.getControlPanel().getTotalPlayers() <= fullBracket * 3/4) {
					match.setMoney(0);
				}
				
				loserMatches[i][j] = match;
				
			}
		}
		
		// Create final matches
		Match match = MatchFactory.createMatch(totalRounds, 1, MatchFactory.DOUBLE_WINNERS);
		if(prizes.length > 1) {
			match.setMoney(prizes[0]-prizes[1]);
		} else {
			match.setMoney(prizes[0]);
		}
		matches[totalRounds][1] = match;
		
		// LinkedList matches
		llMatches = new LinkedList<Match>();
		
		// First round matches
		for(int i=1; i<=Math.pow(2, totalRounds-2); i++) {
			llMatches.add(matches[1][i]);
		}
		
		// All rounds but first and last
		for(int i=2; i<totalRounds; i++) {
			
			
			for(int j=1; j<=Math.pow(2, totalRounds - 1 - i); j++) {
				
				llMatches.add(matches[i][j]);
				
			}
			for(int j=1; j<=Math.pow(2, totalRounds - 1 - i); j++) {
				
				llMatches.add(loserMatches[(i-1)*2-1][j]);
				
			}
			for(int j=1; j<=Math.pow(2, totalRounds - 1 - i); j++) {
				
				llMatches.add(loserMatches[(i-1)*2][j]);
				
			}
			
		}
		
		// Final matches
		llMatches.add(matches[totalRounds][1]);
		
	}// ends DoubleBracket()
	
	@Override
	public void addPlayer(Player player) {
		
		int[] pattern = pattern(getFullBracket() / 2);
		for(int i = 1; i <= getFullBracket() / 2; i++) {
			
			Match match = matches[1][pattern[getFullBracket()/2 - i - 1] + 1];
			
			if(match.getP2() == null) {
    			if(!matches[2][(match.getNum()+1)/2].hasStarted()
    					&& !loserMatches[2][(match.getNum() + 1) / 2].hasStarted()) {
    				if(match.getNum() % 2 == 1) {
    					matches[2][(match.getNum()+1)/2].setP1(null);
	    				loserMatches[2][(match.getNum() + 1)/2].setP1(null);
    				} else {
	    				matches[2][(match.getNum()+1)/2].setP2(null);
	    				loserMatches[2][(match.getNum() + 1)/2].setP2(null);
    				}
    				match.setFinished(false);
    				match.setP2(player);
    				break;
    			}
    		}
			
		}
		
	}// ends addPlayer()

	@Override
	public Match getFinalMatch() {
		
		if(matches[(int)(Math.log(fullBracket)/Math.log(2) + 1)][2] != null) {
			return matches[(int)(Math.log(fullBracket)/Math.log(2) + 1)][2];
		} else {
			return matches[(int)(Math.log(fullBracket)/Math.log(2) + 1)][1];
		}
		
	}// ends getFinalMatch()
	
	@Override
	public int getLoserRounds() {
		
		return (totalRounds-2)*2;
		
	}// ends getLoserRounds()
	
	@Override
	public void resetLLMatches() {
		
		llMatches.clear();
		
		// First round matches
		for(int i=1; i<=Math.pow(2, totalRounds-2); i++) {
			llMatches.add(matches[1][i]);
		}
		
		// All rounds but first and last
		for(int i=2; i<totalRounds; i++) {
			
			
			for(int j=1; j<=Math.pow(2, totalRounds - 1 - i); j++) {
				
				llMatches.add(matches[i][j]);
				
			}
			for(int j=1; j<=Math.pow(2, totalRounds - 1 - i); j++) {
				
				llMatches.add(loserMatches[(i-1)*2-1][j]);
				
			}
			for(int j=1; j<=Math.pow(2, totalRounds - 1 - i); j++) {
				
				llMatches.add(loserMatches[(i-1)*2][j]);
				
			}
			
		}
		
		// Final matches
		llMatches.add(matches[totalRounds][1]);
		if(matches[totalRounds][2] != null) {
			llMatches.add(matches[totalRounds][2]);
		}
		
	}// ends resetLLMatches()
	
	@Override
	public void resetPrizes() {

		int[] prizes = Main.getControlPanel().getPrizes();
		
		// Winners side matches.
		for(int round = 1; round <= totalRounds-1; round++) {			
			for(int matchNum = 1; matchNum <= Math.pow(2, (totalRounds-1 - round)); matchNum++) {
				
				Match match = matches[round][matchNum];
				
				// Set prizes
				if(round == totalRounds - 1) {
					if(prizes.length > 2) {
						match.setMoney(prizes[1] - prizes[2]);
					} else if(prizes.length > 1) {
						match.setMoney(prizes[1]);
					} else {
						match.setMoney(0);
					}
				} else if((totalRounds - round)*2 < prizes.length) {
					match.setMoney(prizes[(totalRounds - round)*2 - 2]-prizes[(totalRounds - round)*2]);
				} else if((totalRounds - round)*2 - 2 < prizes.length) {
					match.setMoney(prizes[(totalRounds - round)*2 - 2]);
				} else {
					match.setMoney(0);
				}
				
				if(round == 1 && Main.getControlPanel().getTotalPlayers() <= fullBracket * 3/4) {
					match.setMoney(0);
				}
				
			}
		}
		
		// Loser side matches
		for(int i=1; i<=(totalRounds-2)*2; i++) {
			for(int j=1; j<=Math.pow(2, (totalRounds-i)/2); j++) {

				Match match = loserMatches[i][j];
				
				// Set prizes
				if((totalRounds-2)*2-i+2 < prizes.length){
					match.setMoney(prizes[(totalRounds-2)*2-i+1] - prizes[(totalRounds-2)*2-i+2]);
				}
				else if((totalRounds-2)*2-i+1 < prizes.length){
					match.setMoney(prizes[(totalRounds-2)*2-i+1]);
				}
				else{
					match.setMoney(0);
				}
				
				if(i == 1 && Main.getControlPanel().getTotalPlayers() <= fullBracket * 3/4) {
					match.setMoney(0);
				}
				
			}
		}
		
		//Final matches
		Match match = matches[totalRounds][1];
		if(prizes.length > 1) {
			match.setMoney(prizes[0]-prizes[1]);
		} else {
			match.setMoney(prizes[0]);
		}
		if(matches[totalRounds][2] != null) {
			match.setMoney(0);
			match = matches[totalRounds][2];
			if(prizes.length > 1) {
				match.setMoney(prizes[0]-prizes[1]);
			} else {
				match.setMoney(prizes[0]);
			}
		}
		
	}// ends resetPrizes()

}// ends Class
