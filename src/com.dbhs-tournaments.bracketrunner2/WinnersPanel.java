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
 * Panel used to visually represent the winners' side of
 *  a double elimination format tournament bracket.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 *
 */
@SuppressWarnings("serial")
class WinnersPanel extends BracketPanel {
	
	/**
	 * Constructor
	 */
	public WinnersPanel(Bracket bracket) {
		
		super(bracket);
		
		int w = (int) Main.getControlPanel().getScroller().getViewport().getVisibleRect().getHeight() / bracket.getFullBracket() * (bracket.getRounds() * 6 - 1);
		
		JViewport v = Main.getControlPanel().getScroller().getViewport();
		setPreferredSize(new Dimension(w, v.getHeight()-v.getInsets().top-v.getInsets().bottom));
		iSize = getPreferredSize();
		
	}// ends WinnersPanel()
	
	@Override
	protected void drawMatch(Graphics g, Match match) {
		
		Graphics2D g2D = (Graphics2D) g;
		
		int x = 0;
		int y = 0;
		
		int h = getHeight();
		
		int totalRounds = getRounds();
		Match[][] matches = getMatches();
		
		int endLength = h/getFullBracket()/2;
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
			x = (match.getRound()-1)*(matchLength+connectorLength) + endLength*2;
		} else {
			x = (match.getRound()-1) * (matchLength+connectorLength) + endLength;
		}
		
		if(Main.getControlPanel().getFormat() == "Double Elimination") {
			
			if(match.getRound() == totalRounds) {
				if(match.getNum() == 1) {
					y = matches[totalRounds-1][1].getRect().y+endLength;
				} else {
					y = matches[totalRounds-1][1].getRect().y+endLength*4;
				}
			} else if(match.getRound() == 1 && totalRounds > 1) {
				y = (match.getNum()-1)*(h-4*endLength)/((int)Math.pow(2, totalRounds-2)-1) + 2*endLength;
			} else if(match.getRound() == 1) {
				y = (match.getNum()-1)*(h-4*endLength)/2 + 2*endLength;
			} else {
				y = (matches[match.getRound() - 1][match.getNum() * 2 - 1].getRect().y+endLength
						+ matches[match.getRound() - 1][match.getNum() * 2].getRect().y+endLength) / 2;
			}
			
		} else if(Main.getControlPanel().getFormat() == "Single Modified") {
			
			if(match.getRound() == 4) {
				y = matches[3][match.getNum()].getRect().y+endLength;
			} else if(match.getRound() == 1 && totalRounds > 1) {
				y = (match.getNum()-1)*(h-4*endLength)/((int)Math.pow(2, totalRounds-2)-1) + 2*endLength;
			} else if(match.getRound() == 1) {
				y = (match.getNum()-1)*(h-4*endLength)/2 + 2*endLength;
			} else {
				y = (matches[match.getRound() - 1][match.getNum() * 2 - 1].getRect().y+endLength
						+ matches[match.getRound() - 1][match.getNum() * 2].getRect().y+endLength) / 2;
			}
		
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
		if(Main.getControlPanel().getFormat() == "Double Elimination") {
			
			if(match.getRound() == totalRounds) {
				if(match.getNum() == 1) {
					
					g2D.drawLine(x-connectorLength, y, x, y);
					
				} else {
					
					g2D.drawLine(x-endLength, y-endLength*2, x, y+endLength);
					g2D.drawLine(x-endLength*7/20, y-endLength*2, x, y-endLength);
					g2D.drawLine(x+endLength*53/8, y-endLength*11/5, x+endLength*7, y-endLength);
					g2D.drawLine(x+endLength*6, y-endLength*2, x+endLength*32/5, y-endLength-2);
					g2D.drawLine(x+endLength*7, y-endLength*3, x+endLength*8, y);
					
				}
			} else if(match.getRound() != 1){
				
				int y1 = matches[match.getRound()-1][match.getNum()*2-1].getRect().y+endLength;
				int y2 = matches[match.getRound()-1][match.getNum()*2].getRect().y+endLength;
				
			    g2D.drawLine(x-connectorLength/2, y, x, y);
			    g2D.drawLine(x-connectorLength/2, y1, x-connectorLength/2, y2);
			    g2D.drawLine(x-connectorLength, y1, x-connectorLength/2, y1);
			    g2D.drawLine(x-connectorLength, y2, x-connectorLength/2, y2);
			    
			}
			
		} else if(Main.getControlPanel().getFormat() == "Single Modified") {
			
			if(match.getRound() == 4) {
				
				g2D.drawLine(x-connectorLength, y, x, y);
					
			} else if(match.getRound() != 1){
				
				int y1 = matches[match.getRound()-1][match.getNum()*2-1].getRect().y+endLength;
				int y2 = matches[match.getRound()-1][match.getNum()*2].getRect().y+endLength;
				
			    g2D.drawLine(x-connectorLength/2, y, x, y);
			    g2D.drawLine(x-connectorLength/2, y1, x-connectorLength/2, y2);
			    g2D.drawLine(x-connectorLength, y1, x-connectorLength/2, y1);
			    g2D.drawLine(x-connectorLength, y2, x-connectorLength/2, y2);
			    
			}
			
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
			g2D.setColor(Color.BLACK);
		    if(match.getRound() == totalRounds && match.getNum() == 1 && matches[totalRounds][2] != null){
		    	;
		    } else if(match.getRound() == totalRounds){
		    	g.drawString("$" + Integer.toString(prizes[0]), x+endLength*25/8, y-endLength*11/8);
		    	if(prizes.length>1){
		    		g.drawString("$" + Integer.toString(prizes[1]), x+endLength*25/8, y+endLength*17/8);
		    	}
		    } else if(prizes.length > (totalRounds-1-match.getRound())*2+2){
		    	g.drawString("$" + Integer.toString(prizes[(totalRounds-1-match.getRound())*2+2]), x+endLength*25/8, y-endLength*11/8);
		    } else if(match.getRound() == 1 & prizes.length == (totalRounds-2)*2+2){
				g.drawString("$" + Integer.toString(prizes[prizes.length-1]), x+endLength*25/8, y-endLength*11/8);
			}
			
		} else if(Main.getControlPanel().getFormat() == "Single Modified") {
			
			// Draw the money
			int[] prizes = Main.getControlPanel().getPrizes();
			g2D.setColor(Color.BLACK);
			if(match.getRound() != 1 || match.getRound() == totalRounds){
			    if(match.getRound() == totalRounds){
			    	g.drawString("$" + Integer.toString(prizes[0]), x+endLength*25/8, y-endLength*11/8);
			    	if(prizes.length>1){
			    		g.drawString("$" + Integer.toString(prizes[1]), x+endLength*25/8, y+endLength*17/8);
			    	}
			    }
			    else if(prizes.length > totalRounds - match.getRound() + 1){
			    	g.drawString("$" + Integer.toString(prizes[totalRounds - match.getRound() + 1]), x+endLength*25/8, y-endLength*11/8);
			    }
			}
			else if(match.getRound() == 1 & prizes.length > totalRounds){
				g.drawString("$" + Integer.toString(prizes[prizes.length-1]), x+endLength*25/8, y-endLength*11/8);
			}
			
		}
		
        // Draw the names and handicaps.
        Player p1 = match.getP1();
        Player p2 = match.getP2();
        
        if(match.getP1() != null){
        	
        	if(p1.isActive()){
        		if(Main.getControlPanel().getFormat().equals("Double Elimination")) {
	        		if(p1.getMatchesWon() == p1.getTotalMatches()) {
	        			g2D.setColor(noLossColor);
	        		} else {
	        			g2D.setColor(oneLossColor);
	        		}
        		} else if(Main.getControlPanel().getFormat().equals("Single Modified")) {
        			g2D.setColor(noLossColor);
        		}
        	}
        	else{
        		g2D.setColor(twoLossColor);
        	}
        	
        	String str = p1.getFirstName() + " " + p1.getLastName() + " " + (int)match.getP1Elo();
        	g2D.setFont(Main.getControlPanel().scaleFont(str, endLength*5, g2D));
        	g2D.drawString(str, x+endLength*9/8, y-endLength/8);

            if(match.getRound() == 1 && match.getP2() == null){
            	g2D.setColor(Color.BLACK);
            	g2D.setFont(font);
            	g2D.setFont(Main.getControlPanel().scaleFont("Bye", endLength*5/4, g2D));
            	g2D.drawString("Bye", x+endLength*9/8, y+endLength*7/8);
            }
            
        }
        if(p2 != null){
        	
        	if(p2.isActive()){
        		if(Main.getControlPanel().getFormat().equals("Double Elimination")) {
	        		if(p2.getMatchesWon() == p2.getTotalMatches()) {
	        			g2D.setColor(noLossColor);
	        		} else {
	        			g2D.setColor(oneLossColor);
	        		}
        		} else if(Main.getControlPanel().getFormat().equals("Single Modified")) {
        			g2D.setColor(noLossColor);
        		}
        	}
        	else{
        		g2D.setColor(twoLossColor);
        	}
        	
        	String str = p2.getFirstName() + " " + p2.getLastName() + " " + (int)match.getP2Elo();
        	g2D.setFont(font);
        	g2D.setFont(Main.getControlPanel().scaleFont(str, endLength*5, g2D));
        	g2D.drawString(str, x+endLength*9/8, y+endLength*7/8);
        	
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
			if(llMatches.get(i).isWinnersSide()) {
				Match match = llMatches.get(i);
				drawMatch(g, match);
			}
		}
		
	}// ends paintMatches()
	
	@Override
	Dimension transform(Dimension d, double iScale){
		
		int endLength = d.height/Main.getControlPanel().getScreen().getBracket().getFullBracket();

		Dimension dim = new Dimension((int) (d.width + (d.width * (double) d.width / (endLength * 5) - d.width) * (double) scale / 10 * Main.getControlPanel().getScroller().getViewport().getWidth()/d.width), 
				(int) (d.height + (d.height * (double) d.width / (endLength * 5) - d.height) * (double) scale / 10 * Main.getControlPanel().getScroller().getViewport().getWidth()/d.width));
		
		endLength = dim.height/Main.getControlPanel().getScreen().getBracket().getFullBracket();
		
		while(dim.getWidth() < endLength * (6 * Main.getControlPanel().getRounds() - 1)) {
			dim = new Dimension((int) (dim.getWidth() + dim.height/Main.getControlPanel().getScreen().getBracket().getFullBracket()), (int) (dim.getHeight()));
		}
		
		return dim;
		
	}//ends transform(Dimension)

}// ends Class
