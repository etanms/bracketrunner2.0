package com.etan.bracketrunner2.brackets;

import java.util.LinkedList;

import com.etan.bracketrunner2.Main;
import com.etan.bracketrunner2.Player;
import com.etan.bracketrunner2.matches.Match;
import com.etan.bracketrunner2.matches.MatchFactory;

class ModifiedBracket extends Bracket {
	
	public ModifiedBracket(int fullBracket) {
		
		super(fullBracket);
		
		totalRounds = (int)(Math.log(fullBracket)/Math.log(2) + 1);
		
		int[] prizes = Main.getControlPanel().getPrizes();
		
		// Create winners side matches
		matches = new Match[totalRounds+1][(int)(Math.pow(2, totalRounds - 1) + 1)];
		for(int round = 1; round <= 3; round++) {			
			for(int matchNum = 1; matchNum <= Math.pow(2, (totalRounds-1 - round)); matchNum++) {
				
				Match match = MatchFactory.createMatch(round, matchNum, MatchFactory.MODIFIED_WINNERS);
				
				// Set prizes
				if(totalRounds - round + 1 < prizes.length) {
					match.setMoney(prizes[totalRounds - round]-prizes[totalRounds - round + 1]);
				} else if(totalRounds - round < prizes.length) {
					match.setMoney(prizes[totalRounds - round]);
				} else {
					match.setMoney(0);
				}
				
				if(round == 1 && Main.getControlPanel().getTotalPlayers() <= fullBracket * 3/4) {
					match.setMoney(0);
				}
				
				matches[round][matchNum] = match;
				
			}
		}
		for(int round = 4; round <= totalRounds; round++) {
			for(int matchNum = 1; matchNum <= Math.pow(2, totalRounds - round); matchNum++) {
				
				Match match = MatchFactory.createMatch(round, matchNum,  MatchFactory.MODIFIED_WINNERS);
				
				// Set prizes
				if(totalRounds - round + 1 < prizes.length) {
					match.setMoney(prizes[totalRounds - round]-prizes[totalRounds - round + 1]);
				} else if(totalRounds - round < prizes.length) {
					match.setMoney(prizes[totalRounds - round]);
				} else {
					match.setMoney(0);
				}
				
				matches[round][matchNum] = match;
				
			}
		}
		
		// Create losers side matches
		loserMatches = new Match[4][fullBracket / 4 + 1];
		for(int i=1; i<=3; i++) {
			for(int j=1; j<=Math.pow(2, ((totalRounds - 2) * 2 - i) / 2); j++) {
				
				Match match = MatchFactory.createMatch(i, j, MatchFactory.MODIFIED_LOSERS);
				
				// Set prizes// Set prizes
				if(totalRounds - i + 1 < prizes.length) {
					match.setMoney(prizes[totalRounds - i]-prizes[totalRounds - i + 1]);
				} else if(totalRounds - i < prizes.length) {
					match.setMoney(prizes[totalRounds - i]);
				} else {
					match.setMoney(0);
				}
				
				if(i == 1 && Main.getControlPanel().getTotalPlayers() <= fullBracket * 3/4) {
					match.setMoney(0);
				}
				
				loserMatches[i][j] = match;
				
			}
		}
		
		// LinkedList matches
		llMatches = new LinkedList<Match>();
		
		// Winners side first 2 rounds
		for(int i=1; i<=2; i++) {
			
			for(int j=1; j<=Math.pow(2, totalRounds - 1 - i); j++) {
				llMatches.add(matches[i][j]);
			}
			
		}
		
		// Losers side first 2 rounds
		for(int i=1; i<=2; i++) {
			
			for(int j=1; j<=Math.pow(2, totalRounds - 3); j++) {
				llMatches.add(loserMatches[i][j]);
			}
			
		}
		
		// Winners side round 3
		for(int j=1; j<=Math.pow(2, totalRounds - 4); j++) {
			llMatches.add(matches[3][j]);
		}
		
		// Losers side round 3
		for(int j=1; j<=Math.pow(2, totalRounds - 4); j++) {
			llMatches.add(loserMatches[3][j]);
		}
		
		// All other matches
		for(int i=4; i<=totalRounds; i++) {
			for(int j=1; j<=Math.pow(2, totalRounds - i); j++) {
				llMatches.add(matches[i][j]);
			}
		}
		
	}// ends ModifiedBracket()
	
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
		
		return matches[(int)(Math.log(fullBracket)/Math.log(2)) + 1][1];
		
	}// ends getFinalMatch()
	
	@Override
	public int getLoserRounds() {
		
		return 3;
		
	}// ends getLoserRounds()
	
	@Override
	public void resetLLMatches() {
		
		llMatches.clear();
		
		// Winners side first 2 rounds
		for(int i=1; i<=2; i++) {
			
			for(int j=1; j<=Math.pow(2, totalRounds - 1 - i); j++) {
				llMatches.add(matches[i][j]);
			}
			
		}
		
		// Losers side first 2 rounds
		for(int i=1; i<=2; i++) {
			
			for(int j=1; j<=Math.pow(2, totalRounds - 3); j++) {
				llMatches.add(loserMatches[i][j]);
			}
			
		}
		
		// Winners side round 3
		for(int j=1; j<=Math.pow(2, totalRounds - 4); j++) {
			llMatches.add(matches[3][j]);
		}
		
		// Losers side round 3
		for(int j=1; j<=Math.pow(2, totalRounds - 4); j++) {
			llMatches.add(loserMatches[3][j]);
		}
		
		// All other matches
		for(int i=4; i<=totalRounds; i++) {
			for(int j=1; j<=Math.pow(2, totalRounds - i); j++) {
				llMatches.add(matches[i][j]);
			}
		}
		
	}// ends resetLLMatches()
	
	@Override
	public void resetPrizes() {
		
		int[] prizes = Main.getControlPanel().getPrizes();
		
		// Winners side matches
		for(int round = 1; round <= 3; round++) {			
			for(int matchNum = 1; matchNum <= Math.pow(2, (totalRounds-1 - round)); matchNum++) {
				
				Match match = matches[round][matchNum];
				
				// Set prizes
				if(totalRounds - round + 1 < prizes.length) {
					match.setMoney(prizes[totalRounds - round]-prizes[totalRounds - round + 1]);
				} else if(totalRounds - round < prizes.length) {
					match.setMoney(prizes[totalRounds - round]);
				} else {
					match.setMoney(0);
				}
				
				if(round == 1 && Main.getControlPanel().getTotalPlayers() <= fullBracket * 3/4) {
					match.setMoney(0);
				}
				
			}
		}
		for(int round = 4; round <= totalRounds; round++) {
			for(int matchNum = 1; matchNum <= Math.pow(2, totalRounds - round); matchNum++) {
				
				Match match = matches[round][matchNum];
				
				// Set prizes
				if(totalRounds - round + 1 < prizes.length) {
					match.setMoney(prizes[totalRounds - round]-prizes[totalRounds - round + 1]);
				} else if(totalRounds - round < prizes.length) {
					match.setMoney(prizes[totalRounds - round]);
				} else {
					match.setMoney(0);
				}
				
			}
		}
		
		// Losers side matches
		for(int i=1; i<=3; i++) {
			for(int j=1; j<=Math.pow(2, ((totalRounds - 2) * 2 - i) / 2); j++) {
				
				Match match = loserMatches[i][j];
				
				// Set prizes
				if(totalRounds - i + 1 < prizes.length) {
					match.setMoney(prizes[totalRounds - i]-prizes[totalRounds - i + 1]);
				} else if(totalRounds - i < prizes.length) {
					match.setMoney(prizes[totalRounds - i]);
				} else {
					match.setMoney(0);
				}
				
				if(i == 1 && Main.getControlPanel().getTotalPlayers() <= fullBracket * 3/4) {
					match.setMoney(0);
				}
				
			}
		}
		
	}// ends resetPrizes()

}// ends Class
