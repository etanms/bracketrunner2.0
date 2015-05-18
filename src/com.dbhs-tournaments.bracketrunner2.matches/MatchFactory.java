package com.etan.bracketrunner2.matches;

public class MatchFactory {
	
	public static final int SINGLE = 1;
	public static final int MODIFIED_WINNERS = 2;
	public static final int MODIFIED_LOSERS = 3;
	public static final int DOUBLE_WINNERS = 4;
	public static final int DOUBLE_LOSERS = 5;
	
	/**
	 * @param round The round of the match.
	 * @param num The number of the match within its round.
	 * @param type The format of the tournament in which this match
	 * takes place, and whether or not it is a winners' side match.
	 * @return The match being created.
	 */
	public static Match createMatch(int round, int num, int type) {
		
		switch(type) {
		case SINGLE:
			return new SingleMatch(round, num);
		case MODIFIED_WINNERS:
			return new ModifiedWinnersMatch(round, num);
		case MODIFIED_LOSERS:
			return new ModifiedLosersMatch(round, num);
		case DOUBLE_WINNERS:
			return new DoubleWinnersMatch(round, num);
		case DOUBLE_LOSERS:
			return new DoubleLosersMatch(round, num);
		default:
			return new SingleMatch(round, num);
		}
		
	}// ends createMatch()

}// ends Class
