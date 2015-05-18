package com.etan.bracketrunner2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;

import com.etan.bracketrunner2.brackets.BracketFactory;
import com.etan.bracketrunner2.matches.Match;

/**
 * Panel used to visually represent a single elimination format
 * tournament bracket.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 *
 */
@SuppressWarnings("serial")
class SinglePanel extends BracketPanel {
	
	private static BracketFactory bf = new BracketFactory();
	
	/**
	 * Constructor
	 * 
	 * @param fullBracket The amount of players needed to fill the bracket.
	 */
	public SinglePanel(int fullBracket) {
		
		super(bf.createBracket(fullBracket, BracketFactory.SINGLE));
		
	}// ends SinglePanel()

	@Override
	protected void drawMatch(Graphics g, Match match) {

		Graphics2D g2D = (Graphics2D) g;
		
		int x;
		int y;
		
		int h = getHeight();
		
		int totalRounds = getRounds();
		
		int endLength = h/getFullBracket()/2;
		int matchLength = endLength*8;
		int connectorLength = matchLength/2;
		
		// Reset the font and size
		Font font = new Font("Arial Black", Font.PLAIN, matchLength/16);
		g2D.setFont(font);
		g2D.setStroke(new BasicStroke(endLength/12));
		
		// Set colors to show players' names with
		Color winnerColor;
		Color loserColor;
		if(Main.getControlPanel().getInfoPanel().colored()){
			winnerColor = Color.GREEN;
			loserColor = Color.RED;
		}
		else{
			winnerColor = Color.BLACK;
			loserColor = Color.BLACK;
		}
		
		// Find the point for the center of the left edge of the match
		x = (match.getRound()-1) * (matchLength+connectorLength) + endLength;
		
		if(match.getRound() == 1 && totalRounds > 1) {
			y = (match.getNum()-1)*(h-4*endLength)/((int)Math.pow(2, totalRounds-1)-1) + 2*endLength;
		} else if(match.getRound() == 1) {
			y = (match.getNum()-1)*(h-4*endLength)/2 + 2*endLength;
		} else {
			y = (getMatches()[match.getRound() - 1][match.getNum() * 2 - 1].getRect().y+endLength + getMatches()[match.getRound() - 1][match.getNum() * 2].getRect().y+endLength) / 2;
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
		if(match.getRound() != 1){
			int y1 = getMatches()[match.getRound()-1][match.getNum()*2-1].getRect().y+endLength;
			int y2 = getMatches()[match.getRound()-1][match.getNum()*2].getRect().y+endLength;
			
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
		int[] prizes = Main.getControlPanel().getPrizes();
		g2D.setColor(Color.BLACK);
		if((match.getRound() != 1 && getMatches()[match.getRound()-1][1].getMoney() != 0) || match.getRound() == totalRounds){
		    if(match.getRound() == totalRounds){
		    	g2D.drawString("$" + Integer.toString(prizes[0]), x+matchLength*25/64, y-endLength*11/8);
		    	if(prizes.length>1){
		    		g2D.drawString("$" + Integer.toString(prizes[1]), x+matchLength*25/64, y+endLength*17/8);
		    	}
		    }
			else if(totalRounds-match.getRound()+1 < prizes.length){
				g2D.drawString("$" + Integer.toString(prizes[totalRounds-match.getRound()+1]), x+matchLength*25/64, y-endLength*11/8);
			}
		}
		else if(match.getRound() == 1 && prizes.length == totalRounds+1){
			g2D.drawString("$" + Integer.toString(prizes[prizes.length-1]), x+matchLength*25/64, y-endLength*11/8);
		}
		
        // Draw the names and handicaps.
        Player p1 = match.getP1();
        Player p2 = match.getP2();
        
        if(match.getP1() != null){
        	
        	if(p1.isActive()){
        		g2D.setColor(winnerColor);
        	}
        	else{
        		g2D.setColor(loserColor);
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
        		g2D.setColor(winnerColor);
        	}
        	else{
        		g2D.setColor(loserColor);
        	}
        	String str = p2.getFirstName() + " " + p2.getLastName() + " " + (int)match.getP2Elo();
        	g2D.setFont(font);
        	g2D.setFont(Main.getControlPanel().scaleFont(str, endLength*5, g2D));
        	g2D.drawString(str, x+endLength*9/8, y+endLength*7/8);
        	
    	}
		
	}// ends drawMatch()
    
    @Override
    public Match getFinalMatch() {
    	
    	return bracket.getFinalMatch();
    	
    }// ends getFinalMatch()

}// ends Class
