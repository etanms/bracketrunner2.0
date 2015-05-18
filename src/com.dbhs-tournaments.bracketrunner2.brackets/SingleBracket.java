package com.etan.bracketrunner2.brackets;

import java.util.LinkedList;

import com.etan.bracketrunner2.Main;
import com.etan.bracketrunner2.Player;
import com.etan.bracketrunner2.matches.Match;
import com.etan.bracketrunner2.matches.MatchFactory;

class SingleBracket extends Bracket {
	
	public SingleBracket(int fullBracket) {
		
		super(fullBracket);
		
		totalRounds = (int)(Math.log(fullBracket)/Math.log(2));
		
		int[] prizes = Main.getControlPanel().getPrizes();
		
		// Create each match.
		matches = new Match[totalRounds + 1][(int)(Math.pow(2, totalRounds) + 1)];
		for(int round = 1; round <= totalRounds; round++){			
			for(int matchNum = 1; matchNum <= Math.pow(2, (totalRounds - round)); matchNum++){
				
				Match match = MatchFactory.createMatch(round, matchNum, MatchFactory.SINGLE);
				
				// Set prizes
				if(totalRounds-round+1 < prizes.length){
				    match.setMoney(prizes[totalRounds-round] - prizes[totalRounds-round+1]);
				}
				else if(totalRounds-round < prizes.length){
					match.setMoney(prizes[totalRounds-round]);
				}
				
				matches[round][matchNum] = match;
				
			}
		}
		
		// Set up the LinkedList<Match>
		llMatches = new LinkedList<Match>();
		for(int i=1; i<=totalRounds; i++) {
						
			for(int j=1; j<=Math.pow(2, totalRounds-i); j++) {
				
				llMatches.add(matches[i][j]);
				
			}
			
		}
		
	}// ends SingleBracket()
	
	@Override
	public void addPlayer(Player player) {
		
		int[] pattern = pattern(getFullBracket() / 2);
		for(int i = 0; i < getFullBracket() / 2; i++) {
			
			Match match = matches[1][pattern[getFullBracket()/2 - i - 1] + 1];
			
			if(match.getP2() == null) {
    			if(!matches[2][(match.getNum()+1)/2].hasStarted()) {
    				if(match.getNum() % 2 == 1) {
    					matches[2][(match.getNum()+1)/2].setP1(null);
    				} else {
	    				matches[2][(match.getNum()+1)/2].setP2(null);
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
		
		return matches[totalRounds][1];
		
	}// ends getFinalMatch()
	
	@Override
	public int getLoserRounds() {
		
		return 0;
		
	}// ends getLoserRounds()
	
	@Override
	public void resetLLMatches() {
		
		llMatches.clear();
		for(int i=1; i<=totalRounds; i++) {
						
			for(int j=1; j<=Math.pow(2, totalRounds-i); j++) {
				
				llMatches.add(matches[i][j]);
				
			}
			
		}
		
	}// ends resetLLMatches()
	
	@Override
	public void resetPrizes() {
		
		int[] prizes = Main.getControlPanel().getPrizes();
		
		for(int round = 1; round <= totalRounds; round++){			
			for(int matchNum = 1; matchNum <= Math.pow(2, (totalRounds - round)); matchNum++){
				
				Match match = matches[round][matchNum];
				
				// Set prizes
				if(totalRounds-round+1 < prizes.length){
				    match.setMoney(prizes[totalRounds-round] - prizes[totalRounds-round+1]);
				}
				else if(totalRounds-round < prizes.length){
					match.setMoney(prizes[totalRounds-round]);
				}
				
			}
		}
		
	}// ends resetPrizes()

}// ends Class
