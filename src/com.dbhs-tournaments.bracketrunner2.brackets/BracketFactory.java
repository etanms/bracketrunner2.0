package com.etan.bracketrunner2.brackets;

public class BracketFactory {
	
	public static final int SINGLE = 0;
	public static final int DOUBLE = 1;
	public static final int MODIFIED = 2;
	
	/**
	 * Creates a new Bracket.
	 * 
	 * @param fullBracket The amount of players needed to fill the bracket.
	 * @param type The format of the bracket.
	 * @return Bracket
	 */
	public Bracket createBracket(int fullBracket, int type) {
		
		Bracket bracket = null;
		
		switch(type) {
		case SINGLE:
			bracket = new SingleBracket(fullBracket);
			break;
		case DOUBLE:
			bracket = new DoubleBracket(fullBracket);
			break;
		case MODIFIED:
			bracket = new ModifiedBracket(fullBracket);
			break;
		}
		
		return bracket;
		
	}// ends createBracket()

}// ends Class
