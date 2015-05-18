package com.etan.bracketrunner2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.util.LinkedList;

import javax.swing.JViewport;

import com.etan.bracketrunner2.brackets.Bracket;
import com.etan.bracketrunner2.matches.Match;

/**
 * Panel used to visually represent the losers' side of
 *  a double elimination format tournament bracket.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 *
 */
@SuppressWarnings("serial")
class LosersPanel extends BracketPanel {
	
	/**
	 * Constructor
	 */
	public LosersPanel(Bracket bracket) {
		
		super(bracket);
		
		int w = (int) Main.getControlPanel().getScroller().getViewport().getVisibleRect().getHeight() 
				/ bracket.getFullBracket()*2 * (bracket.getLoserRounds() * 6 - 1);
		
		JViewport v = Main.getControlPanel().getScroller().getViewport();
		setPreferredSize(new Dimension(w, v.getHeight()-v.getInsets().top-v.getInsets().bottom));
		iSize = getPreferredSize();
		
	}// ends LosersPanel()
	
	@Override
	protected void drawMatch(Graphics g, Match match) {
		
		Graphics2D g2D = (Graphics2D) g;
		
		int x;
		int y;
		
		int h = getHeight();
		
		int totalRounds = getRounds();
		
		int endLength = h/getFullBracket();
		int matchLength = endLength*8;
		int connectorLength = matchLength/2;
		
		// Reset the font and size
		Font font = new Font("Arial Black", Font.PLAIN, matchLength/16);
		g2D.setFont(font);
		g2D.setStroke(new BasicStroke(endLength/12));
		
		// Set colors to show players' names with
		Color noLossColor;
		Color oneLossColor;
		Color twoLossColor;
		if(Main.getControlPanel().getInfoPanel().colored()){
			noLossColor = Color.GREEN;
			oneLossColor = new Color(218, 196, 5);
			twoLossColor = Color.RED;
		}
		else{
			noLossColor = Color.BLACK;
			oneLossColor = Color.BLACK;
			twoLossColor = Color.BLACK;
		}
		
		// Find the point for the center of the left edge of the match
		if(match.getRound() == totalRounds && match.getNum() == 2) {
			x = (match.getRound()-1)*(matchLength+connectorLength) + endLength*14;
		} else {
			x = (match.getRound()-1) * (matchLength+connectorLength) + endLength;
		}
		if(match.getRound() == 1 && Main.getControlPanel().getLoserRounds() > 2){
			y = (match.getNum()-1)*(h-4*endLength)/((int)Math.pow(2, totalRounds-3)-1) + 2*endLength;
		} else if(match.getRound() == 1) {
			y = h/2;
		} else if(match.getRound()%2 == 1){
			y = (bracket.getLoserMatches()[match.getRound() - 1][match.getNum() * 2 - 1].getRect().y + endLength + bracket.getLoserMatches()[match.getRound() - 1][match.getNum() * 2].getRect().y + endLength) / 2;
		}
		else{
			y = bracket.getLoserMatches()[match.getRound()-1][match.getNum()].getRect().y + endLength;
		}
		
		// Set the bounds for the clickable area
		match.setRect(new Rectangle(x, y-endLength, matchLength, 2*endLength));
		
		// Fill in with two different shades of grey
		g2D.setColor(new Color(210, 210, 210));
		g2D.fillRect(x + 1, y, matchLength-endLength-1, endLength+1);
		g2D.setColor(new Color(240, 240, 240));
		g2D.fillRect(x + 1, y - endLength, matchLength-endLength-1, endLength);
        
        // Fill in rectangle and draw the score
        g2D.setColor(Color.CYAN);
		g2D.fillRect(x+1, y-endLength, endLength+1, endLength);
		g2D.fillRect(x+1, y, endLength+1, endLength);
		
		if(match.getP1Score() != 0 || match.getP2Score() != 0){
			g2D.setColor(Color.BLACK);
			g2D.drawString(Integer.toString(match.getP1Score()), x+endLength*3/8, y-endLength*13/40);
			g2D.drawString(Integer.toString(match.getP2Score()), x+endLength*3/8, y+endLength*7/10);
		}
		
		// Draw a rectangular area with two intersecting lines inside of it
		g2D.setColor(Color.RED);
		g2D.drawRect(x, y-endLength-1, matchLength-endLength-1, endLength*2+1);
		g2D.drawLine(x, y, x+matchLength-endLength-2, y);
		g2D.drawLine(x+endLength+1, y-endLength, x+endLength+1, y+endLength);
		
		// Draw the lines connecting this match to the two from the round before
		if(match.getRound() % 2 == 0) {
			
			g2D.drawLine(x-connectorLength, y, x, y);
				
		} else if(match.getRound() != 1){
			
			int y1 = bracket.getLoserMatches()[match.getRound()-1][match.getNum()*2-1].getRect().y+endLength;
			int y2 = bracket.getLoserMatches()[match.getRound()-1][match.getNum()*2].getRect().y+endLength;
			
		    g2D.drawLine(x-connectorLength/2, y, x, y);
		    g2D.drawLine(x-connectorLength/2, y1, x-connectorLength/2, y2);
		    g2D.drawLine(x-connectorLength, y1, x-connectorLength/2, y1);
		    g2D.drawLine(x-connectorLength, y2, x-connectorLength/2, y2);
		    
		}
		
		// Draw the arcs on the right side of the match
	    Arc2D.Double p1Spot = new Arc2D.Double(x+matchLength-endLength*2-1, y-endLength-1, endLength*2, endLength*2+2, 0, 90, Arc2D.PIE);
	    Arc2D.Double p2Spot = new Arc2D.Double(x+matchLength-endLength*2-1, y-endLength, endLength*2, endLength*2, 0, -90, Arc2D.PIE);
        g2D.setColor(Color.CYAN);
	    g2D.fill(p1Spot);
	    g2D.setColor(Color.RED);
        g2D.draw(p1Spot);
        g2D.setColor(Color.CYAN);
	    g2D.fill(p2Spot);
	    g2D.setColor(Color.RED);
        g2D.draw(p2Spot);
        
        // Draw the spots if needed.
        if(match.getP1() != null && match.getP2() != null){
        	
        	g2D.setColor(Color.BLACK);
        	
        	if(match.getP1Elo() < match.getP2Elo()){
        		g2D.drawString(match.getSpot() + "/" + match.getRace(), x+matchLength*7/8+1, y-endLength/8);
        	}
        	else if(match.getP1Elo() > match.getP2Elo()){
        		g2D.drawString(match.getSpot() + "/" + match.getRace(), x+matchLength*7/8+1, y+endLength/2);
        	}
        	else {
        		g2D.drawString(match.getSpot() + "/" + match.getRace(), x+matchLength*7/8+1, y-endLength/8);
        	}
        	
        }
		
		// Draw match identifier
		g2D.setColor(Color.BLACK);
		g2D.drawString(match.getName(), x+matchLength*25/64, y+endLength*3/2);
		
		// Draw the money
		if(Main.getControlPanel().getFormat() == "Double Elimination") {
			
			int[] prizes = Main.getControlPanel().getPrizes();
			if(match.getMoney() != 0){
				g.setColor(Color.BLACK);
				if((totalRounds-2)*2-match.getRound()+2 < prizes.length){
					g.drawString("$" + Integer.toString(prizes[(totalRounds-2)*2-match.getRound()+2]), x+endLength*25/8, y-endLength*11/8);
				}
			}
			
		} else if(Main.getControlPanel().getFormat() == "Single Modified") {
			
			int[] prizes = Main.getControlPanel().getPrizes();
			if(prizes.length > totalRounds - match.getRound() + 1){
		    	g.drawString("$" + Integer.toString(prizes[totalRounds - match.getRound() + 1]), x+endLength*25/8, y-endLength*11/8);
		    }
			
		}
		
		// Draw the names and spots.
        Player p1 = match.getP1();
        Player p2 = match.getP2();
        
		if(p1 != null){
			if(p1.isActive()){
				if(Main.getControlPanel().getFormat().equals("Single Modified")) {
					g2D.setColor(noLossColor);
				} else if(Main.getControlPanel().getFormat().equals("Double Elimination")) {
					g2D.setColor(oneLossColor);
				}
        	}
        	else{
        		g2D.setColor(twoLossColor);
        	}
			g.drawString(p1.getFirstName() + " " + p1.getLastName() + " " + (int)match.getP1Elo(), x+endLength*9/8, y-endLength/8);
		}
		
		if(p2 != null) {
			if(p2.isActive()){
				if(Main.getControlPanel().getFormat().equals("Single Modified")) {
					g2D.setColor(noLossColor);
				} else if(Main.getControlPanel().getFormat().equals("Double Elimination")) {
					g2D.setColor(oneLossColor);
				}
	    	}
	    	else{
	    		g2D.setColor(twoLossColor);
	    	}
			g.drawString(p2.getFirstName() + " " + p2.getLastName() + " " + (int)match.getP2Elo(), x+endLength*9/8, y+endLength*7/8);
		}
		g.setColor(Color.BLACK);
		if(match.isP2Bye()){
			g.drawString("Bye", x+endLength*9/8, y+endLength*7/8);
		}
		if(match.isP1Bye()){
			g.drawString("Bye", x+endLength*9/8, y-endLength/8);
		}
		
	}// ends drawMatch()

	@Override
	public Match getFinalMatch() {
		
		return null;
		
	}// ends getFinalMatch()
	
	@Override
	public void paintMatches(Graphics g) {
		
		LinkedList<Match> llMatches = bracket.getLLMatches();
		
		for(int i = 0; i < llMatches.size(); i++){
			if(!llMatches.get(i).isWinnersSide()) {
				drawMatch(g, llMatches.get(i));
			}
		}
		
	}// ends paintMatches()
	
	@Override
	Dimension transform(Dimension d, double iScale){
		
		int endLength = d.height/Main.getControlPanel().getScreen().getBracket().getFullBracket();

		Dimension dim = new Dimension((int) (d.width + (d.width * (double) d.width / (endLength * 5) - d.width) * (double) scale / 10 * Main.getControlPanel().getScroller().getViewport().getWidth()/d.width), 
				(int) (d.height + (d.height * (double) d.width / (endLength * 5) - d.height) * (double) scale / 10 * Main.getControlPanel().getScroller().getViewport().getWidth()/d.width));
		
		endLength = dim.height/Main.getControlPanel().getScreen().getBracket().getFullBracket();
		
		while(dim.getWidth() < endLength * (6 * Main.getControlPanel().getLoserRounds() - 1)) {
			dim = new Dimension((int) (dim.getWidth() + dim.height/Main.getControlPanel().getScreen().getBracket().getFullBracket()), (int) (dim.getHeight()));
		}
		
		return dim;
		
	}//ends transform(Dimension)

}// ends Class
