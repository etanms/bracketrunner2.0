package com.etan.bracketrunner2;

import com.etan.bracketrunner2.matches.Match;

@SuppressWarnings("serial")
abstract class IndividualPlayerPanel extends ClickablePanel{
	
	private Player player;
	private int split;
	
	/**
	 * Constructor
	 * 
	 * @param player The player to associate with this panel.
	 */
	public IndividualPlayerPanel(Player player){
		
		this.player = player;
		
	}//ends Constructor

	@Override
	Match getMatch() {
		
		return player.getCurrentMatch();
		
	}// ends getMatch()
	
	/**
	 * @return The player associated with the panel.
	 */
	Player getPlayer() {
		
		return player;
		
	}//ends getPlayer()
	
	/**
	 * @return The split group associated with the player
	 * that is associated with this panel.
	 */
	int getSplitGroup() {
		
		return split;
		
	}// ends getSplitGroup()
	
	/**
	 * @param player The player to be associated with this panel.
	 */
	void setPlayer(Player player) {
		
		this.player = player;
		updateInfo();
		
	}// ends setPlayer()
	
	/**
	 * @param split The split group to associate with the player
	 * that is associated with this panel.
	 */
	void setSplitGroup(int split) {
		
		this.split = split;
		
	}// ends setSplitGroup()
	
	abstract void updateInfo();

}//ends Class
