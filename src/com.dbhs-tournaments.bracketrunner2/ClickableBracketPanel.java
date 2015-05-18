package com.etan.bracketrunner2;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.etan.bracketrunner2.brackets.Bracket;
import com.etan.bracketrunner2.brackets.BracketWrapper;
import com.etan.bracketrunner2.matches.Match;

@SuppressWarnings("serial")
public class ClickableBracketPanel extends JPanel implements BracketWrapper, ChangeListener, MouseListener {
	
	public static final int SINGLE = 0;
	public static final int DOUBLE = 1;
	public static final int MODIFIED = 2;
	public static final int WINNERS = 3;
	public static final int LOSERS = 4;
	
	BracketPanel bracket;
	JDialog dialog;
	
	/**
	 * Constructor
	 * 
	 * @param fullBracket The number of players for the bracket
	 * to be filled. 
	 * @param type The type of bracket to use.
	 */
	public ClickableBracketPanel(int fullBracket, int type) {
		
		switch(type) {
		case SINGLE:
			bracket = new SinglePanel(fullBracket);
			break;
		case DOUBLE:
			bracket = new DoublePanel(fullBracket);
			break;
		case MODIFIED:
			bracket = new ModifiedPanel(fullBracket);
			break;
		case WINNERS:
			bracket = new WinnersPanel(Main.getControlPanel().getBracket());
			break;
		case LOSERS:
			bracket = new LosersPanel(Main.getControlPanel().getBracket());
			break;
		}
		
		bracket.addMouseListener(this);
		add(bracket);
		
	}// ends BracketMouseHandler()
	
	/**
	 * Evenly distributes players randomly into the bracket.
	 */
	public void addPlayers(Player[] players) {
		
		bracket.addPlayers(players);
		
	}// ends addPlayers()
	
	/**
	 * @return The bracket displayed by this class.
	 */
	public Bracket getBracket() {
		
		return bracket.getBracket();
		
	}// ends getBracket()

	@Override
	public Match getFinalMatch() {
		
		return bracket.getFinalMatch();
		
	}// ends getFinalMatch()

	@Override
	public int getFullBracket() {
		
		return bracket.getFullBracket();
		
	}// ends getFullBracket()

	@Override
	public LinkedList<Match> getLLMatches() {
		
		return bracket.getLLMatches();
		
	}// ends getLLMatches()

	@Override
	public int getLoserRounds() {

		return bracket.getLoserRounds();
		
	}// ends getLoserRounds()

	@Override
	public Match[][] getMatches() {
		
		return bracket.getMatches();
		
	}// ends getMatches()
    
    /**
	 * @return The total number of rounds on the winners' side.
	 */
	public int getRounds() {
		
		return bracket.getRounds();
		
	}// ends getRounds()

	@Override
	public boolean morePlayersAllowed() {

		return bracket.morePlayersAllowed();
		
	}// ends morePlayersAllowed()

	@Override
	public void mouseClicked(MouseEvent evt) {
		
		LinkedList<Match> matches = bracket.getLLMatches();
		
		for(int i=0; i<matches.size(); i++) {
			
			Match match = matches.get(i);
			if(match.getRect() != null && match.getRect().contains(evt.getPoint())) {
				if(!match.isFinished() && match.getP1() != null && match.getP2() != null) {
					showEndMatchDialog(match);
				} else if(match.isUndoable() && match.getP1() != null && match.getP2() != null) {
					showUndoMatchDialog(match);
				}
			}
			
		}
		
	}// ends mouseClicked()

	@Override
	public void mouseEntered(MouseEvent evt) {
		
		// Do nothing.
		
	}// ends mouseEntered()

	@Override
	public void mouseExited(MouseEvent evt) {
		
		// Do nothing.
		
	}// ends mouseExited()

	@Override
	public void mousePressed(MouseEvent evt) {
		
		// Do nothing.
		
	}// ends mousePressed()

	@Override
	public void mouseReleased(MouseEvent evt) {
		
		// Do nothing.
		
	}// ends mouseReleased()
    
    /**
     * Determines what matches should be drawn to the screen.
     */
    void paintMatches(Graphics g) {
		
		bracket.paintMatches(g);
    	
    }// ends paintMatches()
	
	/**
	 * Displays a dialog to take in user input about a match
	 * being ended.
	 * 
	 * @param match The match associated with the dialog.
	 */
	public void showEndMatchDialog(Match match) {
		
		dialog = new EndMatchDialog(match);
		
    	dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
        dialog.setResizable(false);
        dialog.setVisible(true);
		
	}// ends showEndMatchDialog()
	
	/**
	 * Displays a dialog to take in user input about a match
	 * being forfeited.
	 * 
	 * @param match The match associated with the dialog.
	 */
	protected void showForfeitDialog(Match match) {
		
		dialog = new ForfeitDialog(match);
		
		dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
        dialog.setResizable(false);
        dialog.setVisible(true);
		
	}// ends showForfeitDialog()
	
	/**
	 * Displays a dialog to take in user input about a match
	 * being undone.
	 * 
	 * @param match The match associated with the dialog.
	 */
	public void showUndoMatchDialog(Match match) {
		
		dialog = new UndoMatchDialog(match);
		
		dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
        dialog.setResizable(false);
        dialog.setVisible(true);
		
	}// ends showUndoMatchDialog()
	
	/**
	 * Handles ChangeEvents so that this panel will zoom in and out.
	 * Must be controlled by a JSlider.
	 */
	@Override
	public void stateChanged(ChangeEvent evt){
		
		bracket.stateChanged(evt);
		
	}//ends stateChanged()
	
	public void updateInfo() {
		
		bracket.updateInfo();
		
		validate();
		repaint();
		
	}// ends updateInfo()

}// ends Class
