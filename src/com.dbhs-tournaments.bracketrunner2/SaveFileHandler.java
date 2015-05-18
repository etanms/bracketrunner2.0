package com.etan.bracketrunner2;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.etan.bracketrunner2.matches.Match;
import com.etan.bracketrunner2.matches.MatchFactory;

public class SaveFileHandler extends DefaultHandler {
	
	private boolean start;
	private boolean started;
	private int id;
	private Date date;
	private int location;
	private int game;
	private int entry;
	private int moneyWithheld;
	private int moneyAdded;
	private int format;
	private int race;
	private int race_2;
	private int affected;
	private int raceCount;
	private int finalsFormat;
	private int finalsRace;
	private int[] prizes;
	private int prizeNum;
	private Player[] players;
	private int playerNum;
	private int[] rounds;
	private int[] nums;
	private boolean[] wSides;
	private boolean[] finished;
	private boolean[] forfeit;
	private int[] winner;
	private boolean[] split;
	private int[] p1Ids;
	private float[] p1Elos;
	private float[] p1HighElos;
	private float[] p1LowElos;
	private float[] p1Avgs;
	private float[] p1HighAvgs;
	private float[] p1LowAvgs;
	private int[] p1Scores;
	private int[] p2Ids;
	private float[] p2Elos;
	private float[] p2HighElos;
	private float[] p2LowElos;
	private float[] p2Avgs;
	private float[] p2HighAvgs;
	private float[] p2LowAvgs;
	private int[] p2Scores;
	private int[] races;
	private int[] spots;
	private int p1Money;
	private int p2Money;
	private int matchNum;
	
	/**
	 * Constructor
	 */
	public SaveFileHandler(){
		
		start = true;
		raceCount = 0;
		prizeNum = 0;
		playerNum = 0;
		matchNum = 0;
		
	}//ends Constructor

	@Override
	public void endElement(String uri, String localName, String qName)throws SAXException{
		
		super.endElement(uri, localName, qName);
		
		if(qName.equals("Match")){
			
			matchNum++;
			
		} else if(qName.equals("Tournament")) {
			
			if(start){
				
				Main.addControlPanel();
				Main.getControlPanel().setTournamentId(id);
				
				//Set up the tournament details screen
				OptionsPanel option = Main.getControlPanel().getScreen().getOptions();
				option.setDate(date);
				option.setLocation(location);
				option.setGame(game);
				option.setEntry(entry);
				option.setMoneyWithheld(moneyWithheld);
				option.setMoneyAdded(moneyAdded);
				option.setFormat(format);
				option.setRace(race);
				option.setAffected(affected);
				
				if(format == 2){
					option.setLosersRace(race_2);
					option.setFinalsFormat(finalsFormat);
					if(finalsFormat == 1){
						option.setFinalsRace(finalsRace);
					}
				}
				
				//Set up the players screen
				PlayersPanel playerPanel = Main.getControlPanel().getScreen().getPlayers();
				for(int i=0; i<players.length; i++){
					NewPlayerPanel panel = new NewPlayerPanel();
					panel.setId(players[i].getId());
					panel.setFirstName(players[i].getFirstName());
					panel.setLastName(players[i].getLastName());
					panel.setHandicap(players[i].getElo());
					playerPanel.addPlayerPanel(panel);
				}
				
				//Set Prizes
				option.setPrizes(prizes);
				
				//Start the tournament if needed.
				if(started && playerPanel.startable()) {
					
					Main.getControlPanel().startTournament();
					
					IndividualPlayerPanel[] panels = playerPanel.getPanels();
					
					//Clear player's match history.
					for(int i=0; i<panels.length; i++) {
						panels[i].getPlayer().getMatches().clear();
					}
					
					//Connect PlayerInfoPanels to the correct players
					for(int i=0; i<panels.length; i++) {
						for(int j=0; j<players.length; j++) {
							if(panels[i].getPlayer().getId() == players[j].getId()) {
								players[j] = panels[i].getPlayer();
							}
						}
					}
					
					//Set up the bracket if needed
					LinkedList<Match> matches = Main.getControlPanel().getBracket().getLLMatches();
					matches.clear();
					
					for(int i=0; i<matchNum; i++) {
						
						//Create the matches
						Match match = null;
						int type;
						switch(format) {
						case 0:
							match = MatchFactory.createMatch(rounds[i], nums[i], MatchFactory.SINGLE);
							break;
						case 1:
							if(wSides[i] == true) {
								type = MatchFactory.MODIFIED_WINNERS;
							} else {
								type = MatchFactory.MODIFIED_LOSERS;
							}
							match = MatchFactory.createMatch(rounds[i], nums[i], type);
							break;
						case 2:
							if(wSides[i] == true) {
								type = MatchFactory.DOUBLE_WINNERS;
							} else {
								type = MatchFactory.DOUBLE_LOSERS;
							}
							match = MatchFactory.createMatch(rounds[i], nums[i], type);
							break;
						}
						
						if(match.isWinnersSide()) {
							Main.getControlPanel().getBracket().getMatches()[match.getRound()][match.getNum()] = match;
						} else {
							Main.getControlPanel().getBracket().getLoserMatches()[match.getRound()][match.getNum()] = match;
						}
						
						matches.add(match);
						
					}
					
					for(int i=0; i<matchNum; i++) {
						
						Match match = matches.get(i);
						
						// Set players if needed.
						if(match.getRound() == 1 && match.isWinnersSide()) {
							
							match.setP1(getPlayerById(p1Ids[i]));
							match.setP2(getPlayerById(p2Ids[i]));
							
						}
						
						match.setFinished(finished[i]);
						match.setForfeit(forfeit[i]);
						match.setSplit(split[i]);
						match.setP1Score(p1Scores[i]);
						match.setP2Score(p2Scores[i]);
						
						if(!(rounds[i] == 1 && wSides[i] == true)) {
						
							if(match.isFinished() && match.getP1() != null && match.getP2() != null) {
								
								if(match.isForfeit() || match.isSplit()) {
									if(match.getP1().getId() == winner[i]) {
										match.setWinner(match.getP1());
										match.setLoser(match.getP2());
									} else {
										match.setWinner(match.getP2());
										match.setLoser(match.getP1());
									}
								} else if(p1Scores[i]>p2Scores[i]) {
									match.setWinner(match.getP1());
									match.setLoser(match.getP2());
								} else {
									match.setWinner(match.getP2());
									match.setLoser(match.getP1());
								}
								
								match.end(p1Scores[i], p2Scores[i]);
								
							}
							
							if(match.isFinished()) {
								match.setStarted(true);
							}
							
						}
						
						// Set players with byes in round one into the next match if needed
						// and handle finished matches.
						if(rounds[i] == 1 && rounds[i+1] == 2 && wSides[i] == true) {
							
							Match[][] ms = Main.getControlPanel().getBracket().getMatches();
							
							// Move players with byes along into the second round.
							for(int j=0; j<=i; j++) {
								
								Match m = ms[1][j+1];
								if(m.isP2Bye()) {
									if(m.getNum()%2 == 1) {
										ms[2][(m.getNum()+1)/2].setP1(m.getP1());
									} else {
										ms[2][(m.getNum()+1)/2].setP2(m.getP1());
									}
								} else if(m.isP1Bye()) {
									if(m.getNum()%2 == 1) {
										ms[2][(m.getNum()+1)/2].setP1(m.getP2());
									} else {
										ms[2][(m.getNum()+1)/2].setP2(m.getP2());
									}
								}
								
								// Handle finished matces.
								if(m.isFinished() && m.getP1() != null && m.getP2() != null) {
									
									if(m.isForfeit() || m.isSplit()) {
										if(m.getP1().getId() == winner[j]) {
											m.setWinner(m.getP1());
											m.setLoser(m.getP2());
										} else {
											m.setWinner(m.getP2());
											m.setLoser(m.getP1());
										}
									} else if(p1Scores[j]>p2Scores[j]) {
										m.setWinner(m.getP1());
										m.setLoser(m.getP2());
									} else {
										m.setWinner(m.getP2());
										m.setLoser(m.getP1());
									}
									
									m.end(p1Scores[j], p2Scores[j]);
									
								}
								
								if(m.isFinished()) {
									m.setStarted(true);
								}
								
							}
							
						}
						
					}
					
					// Set the race and spots after the players have
					// been inserted so that the matchup is not based
					// on the players' current handicaps.
					for(int i=0; i<matchNum; i++) {
						
						Match match = matches.get(i);
						
						match.setP1Elo(p1Elos[i]);
						match.setP1HighestElo(p1HighElos[i]);
						match.setP1LowestElo(p1LowElos[i]);
						match.setP2Elo(p2Elos[i]);
						match.setP2HighestElo(p2HighElos[i]);
						match.setP2LowestElo(p2LowElos[i]);
						match.setRace(races[i]);
						match.setSpot(spots[i]);
						
					}
					
					Main.getControlPanel().getBracket().resetLLMatches();
					
					// Set money
					Main.getControlPanel().getBracket().resetPrizes();
					if(Main.getControlPanel().getFinalMatch().isSplit()) {
						if(p1Money > p2Money) {
							Main.getControlPanel().getFinalMatch().setMoney(p1Money - p2Money);
							if(Main.getControlPanel().getPrizes().length >= 3) {
								Main.getControlPanel().getFinalMatch().getP1().getMatches().get(Main.getControlPanel().getFinalMatch().getP1().getMatches().size()-2).setMoney(p2Money - Main.getControlPanel().getPrizes()[2]);
								Main.getControlPanel().getFinalMatch().getP2().getMatches().get(Main.getControlPanel().getFinalMatch().getP2().getMatches().size()-2).setMoney(p2Money - Main.getControlPanel().getPrizes()[2]);
							} else {
								Main.getControlPanel().getFinalMatch().getP1().getMatches().get(Main.getControlPanel().getFinalMatch().getP1().getMatches().size()-2).setMoney(p2Money);
								Main.getControlPanel().getFinalMatch().getP2().getMatches().get(Main.getControlPanel().getFinalMatch().getP2().getMatches().size()-2).setMoney(p2Money);
							}
						} else {
							Main.getControlPanel().getFinalMatch().setMoney(p2Money - p1Money);
							if(Main.getControlPanel().getPrizes().length >= 3) {
								Main.getControlPanel().getFinalMatch().getP1().getMatches().get(Main.getControlPanel().getFinalMatch().getP1().getMatches().size()-2).setMoney(p1Money - Main.getControlPanel().getPrizes()[2]);
								Main.getControlPanel().getFinalMatch().getP2().getMatches().get(Main.getControlPanel().getFinalMatch().getP2().getMatches().size()-2).setMoney(p1Money - Main.getControlPanel().getPrizes()[2]);
							} else {
								Main.getControlPanel().getFinalMatch().getP1().getMatches().get(Main.getControlPanel().getFinalMatch().getP1().getMatches().size()-2).setMoney(p1Money);
								Main.getControlPanel().getFinalMatch().getP2().getMatches().get(Main.getControlPanel().getFinalMatch().getP2().getMatches().size()-2).setMoney(p1Money);
							}
						}
					}
					
					Main.getControlPanel().getInfoPanel().next();
			    	Main.getControlPanel().updateInfo();
		    	
				}
		    	
			} else {
				
				JDialog eDialog = new JDialog(Main.getWindow(), "Error!", Dialog.ModalityType.TOOLKIT_MODAL);
				JPanel content = new JPanel();
				content.setLayout(new GridLayout(0, 1, 0, -8));
				
				String str = "An error ocurred while retrieving data from the saved file you selected. The data from the file is likely corrupted. If you wish to try to retrieve the data still, contact Etan Mizrahi-Shalom at etanms@gmail.com.";

				if (str.length() > 50) {

					int begin = 0;
					int end = 50;

					while (begin < str.length()) {

						if (end >= str.length()) {
							JPanel jPanel = new JPanel();
							jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
							jPanel.add(new JLabel(str.substring(begin, str.length())));
							content.add(jPanel);
							break;
						} else {
							if (str.charAt(end) == ' ') {
								JPanel jPanel = new JPanel();
								jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
								jPanel.add(new JLabel(str.substring(begin, end + 1)));
								content.add(jPanel);
								begin = end + 1;
								end = begin + 50;
							} else {
								end++;
							}

						}

					}

				}

				eDialog.setContentPane(content);
				eDialog.pack();
				eDialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()
						/ 2 - eDialog.getWidth() / 2, Main.getWindow().getLocation().y
						+ Main.getWindow().getHeight() / 2 - eDialog.getHeight() / 2);
				eDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				eDialog.setResizable(false);
				eDialog.setVisible(true);
				
			}
		}
		
	}//ends endElement()
	
	public Player getPlayerById(int id){
		
		if(id == 0) {
			return null;
		}
		
		for(int i=0; i<players.length; i++){
			if(players[i].getId() == id){
				return players[i];
			}
		}
		
		return null;
		
	}//ends getPlayerById()

	/**
	 * @return True if the tournament has already started.
	 * False otherwise.
	 */
	boolean isStarted() {
		
		return started;
		
	}// ends isStarted()

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
		
		super.startElement(uri, localName, qName, attributes);
		
		if(qName.equals("Tournament")){
			try{
				if(attributes.getValue("id") != null) {
					id = Integer.parseInt(attributes.getValue("id"));
				} else {
					id = -1;
				}
				date = new Date(Long.parseLong(attributes.getValue("date")));
				location = Integer.parseInt(attributes.getValue("location"));
				game = Integer.parseInt(attributes.getValue("game"));
				started = Boolean.parseBoolean(attributes.getValue("started"));
				affected = Integer.parseInt(attributes.getValue("affected"));
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		else if(qName.equals("Money")){
			try{
				entry = Integer.parseInt(attributes.getValue("entry"));
				moneyWithheld = Integer.parseInt(attributes.getValue("withheld"));
				moneyAdded = Integer.parseInt(attributes.getValue("added"));
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		else if(qName.equals("Format")){
			try{
				format = Integer.parseInt(attributes.getValue("type"));
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		else if(qName.equals("Finals")){
			try{
				finalsFormat = Integer.parseInt(attributes.getValue("matches"))-1;
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		else if(qName.equals("Race")){
			try{
				if(raceCount == 0){
					race = Integer.parseInt(attributes.getValue("length"));
				}
				else if(raceCount == 1){
					race_2 = Integer.parseInt(attributes.getValue("length"));
				}
				else if(raceCount == 2){
					finalsRace = Integer.parseInt(attributes.getValue("length"));
				}
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
			raceCount++;
		}
		else if(qName.equals("Prizes")){
			try{
				prizes = new int[Integer.parseInt(attributes.getValue("length"))];
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		else if(qName.equals("Prize")){
			try{
				prizes[prizeNum] = Integer.parseInt(attributes.getValue("amount"));
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
			prizeNum++;
		}
		else if(qName.equals("Players")){
			try{
				players = new Player[Integer.parseInt(attributes.getValue("length"))];
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		else if(qName.equals("Player")){
			
			try{
			
				float e;// elo
				float he;// highest elo
				float le;// lowest elo
				float a;// average
				float ha;// highest average
				float la;// lowest average
				
				if(attributes.getValue("elo") != null) {
					
					e = Float.parseFloat(attributes.getValue("elo"));
					he = Float.parseFloat(attributes.getValue("highest_elo"));
					le = Float.parseFloat(attributes.getValue("lowest_elo"));
					a = Float.parseFloat(attributes.getValue("average"));
					ha = Float.parseFloat(attributes.getValue("highest_average"));
					la = Float.parseFloat(attributes.getValue("lowest_average"));
					
				}else {
					
					e = 0;
					he = 0;
					le = 0;
					a = 0;
					ha = 0;
					la = 0;
					
				}
				
				players[playerNum] = new Player(attributes.getValue("first_name"), attributes.getValue("last_name"), Integer.parseInt(attributes.getValue("id")), e, he, le, a, ha, la, Boolean.parseBoolean(attributes.getValue("established")), 0);
				
			}
			catch(NumberFormatException e) {
				
				e.printStackTrace();
				start = false;
				
			}
			playerNum++;
		} else if(qName.equals("Matches")) {
			int count = 0;
			while(Math.pow(2, count) < playerNum){
				count++;
			}
			
			int i = 0;
			switch(format){
			case 0:
				for(int j=0; j<count; j++){
					i += (int)Math.pow(2, j);
				}
				break;
			case 1:
				for(int j=0; j<=count; j++){
					i += (int)Math.pow(2, j);
				}
				i += (int)Math.pow(2, count)*3/2;
				
			case 2:
				for(int j=0; j<=count; j++){
					i += (int)Math.pow(2, j);
				}
				for(int j=0; j<=(count-1)*2; j++){
					i += (int)Math.pow(2, j/2);
				}
				i+=2;
				break;
			}
			
			rounds = new int[i];
			nums = new int[i];
			wSides = new boolean[i];
			finished = new boolean[i];
			forfeit = new boolean[i];
			winner = new int[i];
			split = new boolean[i];
			p1Ids = new int[i];
			p1Elos = new float[i];
			p1HighElos = new float[i];
			p1LowElos = new float[i];
			p1Avgs = new float[i];
			p1HighAvgs = new float[i];
			p1LowAvgs = new float[i];
			p1Scores = new int[i];
			p2Ids = new int[i];
			p2Elos = new float[i];
			p2HighElos = new float[i];
			p2LowElos = new float[i];
			p2Avgs = new float[i];
			p2HighAvgs = new float[i];
			p2LowAvgs = new float[i];
			p2Scores = new int[i];
			races = new int[i];
			spots = new int[i];
		}
		else if(qName.equals("Match")){
			try{
				rounds[matchNum] = Integer.parseInt(attributes.getValue("round"));
				nums[matchNum] = Integer.parseInt(attributes.getValue("num"));
				wSides[matchNum] = Boolean.parseBoolean(attributes.getValue("winner_side"));
				races[matchNum] = Integer.parseInt(attributes.getValue("race"));
				spots[matchNum] = Integer.parseInt(attributes.getValue("spot"));
				finished[matchNum] = Boolean.parseBoolean(attributes.getValue("finished"));
				if(Boolean.parseBoolean(attributes.getValue("forfeit"))){
					forfeit[matchNum] = true;
					winner[matchNum] = Integer.parseInt(attributes.getValue("winner"));
				}
				else{
					forfeit[matchNum] = false;
					winner[matchNum] = 0;
				}
				if(Boolean.parseBoolean(attributes.getValue("split"))){
					split[matchNum] = true;
					p1Money = Integer.parseInt(attributes.getValue("p1"));
					p2Money = Integer.parseInt(attributes.getValue("p2"));
				}
				else{
					split[matchNum] = false;
				}
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		else if(qName.equals("Player1")){
			try{
				p1Ids[matchNum] = Integer.parseInt(attributes.getValue("id"));
				if(Integer.parseInt(attributes.getValue("id")) != 0) {
					p1Elos[matchNum] = Float.parseFloat(attributes.getValue("p1Elo"));
					p1HighElos[matchNum] = Float.parseFloat(attributes.getValue("p1HighestElo"));
					p1LowElos[matchNum] = Float.parseFloat(attributes.getValue("p1LowestElo"));
					p1Avgs[matchNum] = Float.parseFloat(attributes.getValue("p1Average"));
					p1HighAvgs[matchNum] = Float.parseFloat(attributes.getValue("p1HighestAverage"));
					p1LowAvgs[matchNum] = Float.parseFloat(attributes.getValue("p1LowestAverage"));
				}
				p1Scores[matchNum] = Integer.parseInt(attributes.getValue("score"));
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		else if(qName.equals("Player2")){
			try{
				p2Ids[matchNum] = Integer.parseInt(attributes.getValue("id"));
				if(Integer.parseInt(attributes.getValue("id")) != 0) {
					p2Elos[matchNum] = Float.parseFloat(attributes.getValue("p2Elo"));
					p2HighElos[matchNum] = Float.parseFloat(attributes.getValue("p2HighestElo"));
					p2LowElos[matchNum] = Float.parseFloat(attributes.getValue("p2LowestElo"));
					p2Avgs[matchNum] = Float.parseFloat(attributes.getValue("p2Average"));
					p2HighAvgs[matchNum] = Float.parseFloat(attributes.getValue("p2HighestAverage"));
					p2LowAvgs[matchNum] = Float.parseFloat(attributes.getValue("p2LowestAverage"));
				}
				p2Scores[matchNum] = Integer.parseInt(attributes.getValue("score"));
			}
			catch(NumberFormatException e){
				e.printStackTrace();
				start = false;
			}
		}
		
	}//ends startElement()
	
}// ends Class
