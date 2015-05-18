package com.etan.bracketrunner2;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JViewport;

import com.etan.bracketrunner2.matches.Match;

/**
 * Interface for subclasses that will switch the screen to a particular
 * match when double-clicked on.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27, 2014
 *
 */
@SuppressWarnings("serial")
abstract class ClickablePanel extends JPanel implements MouseListener {
	
	/**
	 * Constructor
	 */
	public ClickablePanel() {
		
		addMouseListener(this);
		
	}// ends ClickablePanel()
	
	/**
	 * @return The match to set the screen to focus on if 
	 * this panel is double-clicked.
	 */
	abstract Match getMatch();
	
	@Override
	public void mouseClicked(MouseEvent evt) {

		if(evt.getClickCount() == 2 && getMatch() != null) {
			
			Main.getControlPanel().getScreen().setScreen(ScreenPanel.BRACKET);
			
			ClickableBracketPanel bracket = Main.getControlPanel().getScreen().getBracket();
			JViewport viewport = Main.getControlPanel().getScroller().getViewport();
			
			bracket.paintMatches(bracket.getGraphics());
			
			int x = (int) (getMatch().getRect().x + getMatch().getRect().getWidth()/2 - viewport.getVisibleRect().width/2);
        	int y = (int) (getMatch().getRect().y + getMatch().getRect().getHeight()/2 - viewport.getVisibleRect().height/2);
        	viewport.setViewSize(new Dimension(bracket.getWidth(), bracket.getHeight()));
        	viewport.setViewPosition(new Point((int)x, y));
        	
        	Main.getControlPanel().updateInfo();
			
		}

	} // end mouseClicked()

	@Override
	public void mouseEntered(MouseEvent evt) {

	} // end mouseEntered()

	@Override
	public void mouseExited(MouseEvent evt) {

	} // end mouseExited()

	@Override
	public void mousePressed(MouseEvent evt) {

	} // end mousePressed()

	@Override
	public void mouseReleased(MouseEvent evt) {

	} // end mouseReleased()

}// ends Class
