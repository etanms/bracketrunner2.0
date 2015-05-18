package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.etan.bracketrunner2.matches.Match;
import com.etan.dbhs.DBHSDatabaseIntermediary;

public class MenuBarActionListener implements ActionListener{
	
	private File currentFile;

	@Override
	public void actionPerformed(ActionEvent evt){
		
		if(evt.getActionCommand().equals("New")){
			
			Thread t = new Thread() {
				
				@Override
				public void run() {
			
					JDialog dialog = new LoadingDialog();
					dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
					dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			        dialog.setResizable(false);
			        dialog.setVisible(true);
			        
			        try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					Main.addControlPanel();
					JMenuBar menu = Main.getMenuBar();
					menu.getMenu(0).getMenuComponent(2).setEnabled(false);
					menu.getMenu(0).getMenuComponent(3).setEnabled(true);
					menu.getMenu(1).getMenuComponent(0).setEnabled(true);
					menu.getMenu(1).getMenuComponent(1).setEnabled(true);
					currentFile = null;
					
					dialog.dispose();
					
				}// ends run()
				
			};
			
			t.start();
			
		}//ends newItem
		else if(evt.getActionCommand().equals("Open File...")){
	        
			File file = new File("C:\\Program Files\\BracketRunner\\Tournaments");
			if(!file.exists() || !file.isDirectory()){
				file = new File(System.getProperty("user.home"));
			}
			
			JFileChooser chooser = new JFileChooser(file);
			chooser.setDialogTitle("Choose a tournament file to open.");
			int option = chooser.showOpenDialog(Main.getWindow());
			if(option != JFileChooser.APPROVE_OPTION){
				return;
			}
			currentFile = chooser.getSelectedFile();
			
			Thread t = new Thread() {
				
				@Override
				public void run() {
			
					JDialog dialog = new LoadingDialog();
					dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
					dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			        dialog.setResizable(false);
			        dialog.setVisible(true);
			        
			        try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			
					SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
					JMenuBar menu = Main.getMenuBar();
					try {
						SAXParser parser = saxParserFactory.newSAXParser();
						SaveFileHandler handler = new SaveFileHandler();
						parser.parse(currentFile, handler);
						menu.getMenu(0).getMenuComponent(2).setEnabled(true);
						menu.getMenu(0).getMenuComponent(3).setEnabled(true);
						if(handler.isStarted()){
							menu.getMenu(0).getMenuComponent(4).setEnabled(true);
						}
					}
					catch (ParserConfigurationException e){
				        e.printStackTrace();
				    }
					catch (SAXException e){
				        e.printStackTrace();
				    }
					catch (IOException e){
				        e.printStackTrace();
				    }
					
					menu.getMenu(0).getMenuComponent(2).setEnabled(true);
					menu.getMenu(0).getMenuComponent(3).setEnabled(true);
					menu.getMenu(1).getMenuComponent(0).setEnabled(false);
					menu.getMenu(1).getMenuComponent(1).setEnabled(false);
					
					dialog.dispose();
					
				}// ends run()
				
			};
			
			t.start();
			
		}//ends openItem
		else if(evt.getActionCommand().equals("Save")){
			
			Thread t = new Thread() {
				
				@Override
				public void run() {
			
					JDialog dialog = new SavingDialog();
					dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
					dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			        dialog.setResizable(false);
			        dialog.setVisible(true);
			        
			        try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					save(currentFile.getName());
					
					dialog.dispose();
					
				}// ends run()
				
			};
			
			t.start();
			
		}//ends saveItem
		else if(evt.getActionCommand().equals("Save As...")){
			
			File file = new File("C:\\Program Files\\BracketRunner\\Tournaments");
			if(!file.exists() || !file.isDirectory()){
				file = new File(System.getProperty("user.home"));
			}
			
			JFileChooser chooser = new JFileChooser(file);
			chooser.setDialogTitle("Choose a tournament file to save.");
			int option = chooser.showSaveDialog(Main.getWindow());
			if(option != JFileChooser.APPROVE_OPTION){
				return;
			}
			currentFile = chooser.getSelectedFile();
			Thread t = new Thread() {
				
				@Override
				public void run() {
			
					JDialog dialog = new SavingDialog();
					dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
					dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			        dialog.setResizable(false);
			        dialog.setVisible(true);
			        
			        try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					save(currentFile.getName());
					
					dialog.dispose();
					
				}// ends run()
				
			};
			
			t.start();
			
		}//ends saveAsItem
		else if(evt.getActionCommand().equals("Exit")){
			
			WindowEvent wev = new WindowEvent(Main.getWindow(), WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
            
		}//ends exitItem
		else if(evt.getActionCommand().equals("Load Settings")){
			
			File file = new File("C:\\Program Files\\BracketRunner\\Settings");
			if(!file.exists() || !file.isDirectory()){
				file = new File(System.getProperty("user.home"));
			}
			
			final JFileChooser chooser = new JFileChooser(file);
			chooser.setDialogTitle("Choose a settings file to open.");
			int option = chooser.showOpenDialog(Main.getWindow());
			if(option != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			Thread t = new Thread() {
				
				@Override
				public void run() {
					
					JDialog dialog = new LoadingDialog();
					dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
					dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			        dialog.setResizable(false);
			        dialog.setVisible(true);
			        
			        try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			
					SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
					try {
						SAXParser parser = saxParserFactory.newSAXParser();
						SettingsFileHandler handler = new SettingsFileHandler();
						parser.parse(chooser.getSelectedFile(), handler);
					}
					catch (ParserConfigurationException e){
				        e.printStackTrace();
				    }
					catch (SAXException e){
				        e.printStackTrace();
				    }
					catch (IOException e){
				        e.printStackTrace();
				    }
					
					dialog.dispose();
					
				}// ends run()
				
			};
			
			t.start();
			
		}//ends loadSettingsItem
		else if(evt.getActionCommand().equals("Save Settings")){
			
			File file = new File("C:\\Program Files\\BracketRunner\\Settings");
			if(!file.exists() || !file.isDirectory()){
				file = new File(System.getProperty("user.home"));
			}
			
			final JFileChooser chooser = new JFileChooser(file);
			chooser.setDialogTitle("Choose a settings file to save.");
			int option = chooser.showSaveDialog(Main.getWindow());
			if(option != JFileChooser.APPROVE_OPTION){
				return;
			}
			
			Thread t = new Thread() {
				
				@Override
				public void run() {
			
					JDialog dialog = new SavingDialog();
					dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
					dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
			        dialog.setResizable(false);
			        dialog.setVisible(true);
			        
			        try {
						sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					saveSettings(chooser.getSelectedFile());
					
					dialog.dispose();
					
				}// ends run()
				
			};
			
			t.start();
			
		}//ends saveSettingsItem
		else if(evt.getActionCommand().equals("About")){
			
			String content;
			File about = null;
			try{
				about = new File("C:/Program Files/BracketRunner/about.txt");
				FileReader reader = new FileReader(about);
			       char[] chars = new char[(int) about.length()];
			       reader.read(chars);
			       content = new String(chars);
			       content = content.substring(3);
			       reader.close();
		    }
			catch(Exception e){
				content = new String("An error ocurred while uploading information from " + about.getPath() + ".");
				e.printStackTrace();
			}

			JTextArea textArea = new JTextArea();
			textArea.setText(content);
			textArea.setForeground(Color.BLACK);
			textArea.setFont(new Font("Arial Black", Font.PLAIN, 14));
			textArea.setEditable(false);
			textArea.setBackground(new Color(240,240,240));
			
			JPanel panel = new JPanel();
			panel.setBackground(Color.CYAN);
			panel.add(textArea);
			
			JDialog dialog = new JDialog(Main.getWindow(), "About BracketRunner 2.0", ModalityType.TOOLKIT_MODAL);
			dialog.setContentPane(panel);
			dialog.pack();
			dialog.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-dialog.getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-dialog.getHeight()/2);
			dialog.setVisible(true);
			
		}//ends aboutItem
		
	}//ends actionPerformed()
	
	/**
	 * @return The save file currently associated
	 * with the tournament.
	 */
	protected File getCurrentFile() {
		
		return currentFile;
		
	}// ends getCurrentFile()
	
	protected void save(String str){
		
		boolean started = Main.getControlPanel().hasStarted();
		Writer writer = null;
		try{

			if(currentFile == null){
				currentFile = new File("C:\\Program Files\\BracketRunner\\Tournaments\\" + str);
				int i=2;
				while(currentFile.exists()){
					if(i==2){
						currentFile = new File(currentFile.getPath() + "_" + i);
					}
					else{
						currentFile = new File(currentFile.getPath().substring(0, currentFile.getPath().length()-2) + "_" + i);
					}
					i++;
				}
				currentFile.createNewFile();
			}
			else if(currentFile.isDirectory()){
				currentFile = new File("C:\\Program Files\\BracketRunner\\Tournaments\\" + str);
				currentFile.createNewFile();
			}
			OptionsPanel oPanel = Main.getControlPanel().getScreen().getOptions();
			PlayersPanel pPanel = Main.getControlPanel().getScreen().getPlayers();
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(currentFile), "utf-8"));
		    writer.write("<Tournament xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:BracketRunnerNamespaceSchemaLocation=\"tournament.xsd\" id=\"" + Main.getControlPanel().getTournamentId() + "\" date=\"" + oPanel.getDate().getTime() + "\" location=\"" + oPanel.getTournamentLocationAsInt() + "\" location_name=\"" + oPanel.getTournamentLocation() +  "\" game=\"" + oPanel.getGameAsInt() + "\" started=\"" + Main.getControlPanel().hasStarted() + "\" affected=\"" + oPanel.handicapsAreAffected() + "\">" +
		    		"\n\t<Money entry=\"" + oPanel.getEntry() + "\" withheld=\"" + oPanel.getMoneyWithheld() + "\" added=\"" + oPanel.getMoneyAdded() + "\">" + 
		    		"\n\t</Money>" + 
		    		"\n\t<Format type=\"" + oPanel.getFormatAsInt() + "\">" + 
		    		"\n\t\t<Race length=\"" + oPanel.getRace() + "\"/>");
		    if(oPanel.getFormatAsInt() == 2){
		    	writer.write("\n\t\t<Race length=\"" + oPanel.getLosersRace() + "\"/>");
		    }
		    if(oPanel.getFormatAsInt() == 2){
		    	writer.write("\n\t\t<Finals matches=\"" + oPanel.getFinalsFormat() + "\">");
		    }
		    if(oPanel.getFormatAsInt() == 2 && oPanel.getFinalsFormat() == 2){
		    	writer.write("\n\t\t\t<Race length=\"" + oPanel.getRace() + "\"/>" + 
		    			"\n\t\t\t<Race length=\"" + oPanel.getLosersRace() + "\"/>");
		    }
		    else if(oPanel.getFormatAsInt() == 2){
		    	writer.write("\n\t\t\t<Race length=\"" + oPanel.getFinalsFormat() + "\"/>");
		    }
		    if(oPanel.getFormatAsInt() == 2){
		    	writer.write("\n\t\t</Finals>");
		    }
		    writer.write("\n\t</Format>");
		    if(started) {
			    writer.write("\n\t<Prizes length=\"" + oPanel.getPrizes().length + "\">");
			    for(int i=0; i<oPanel.getPrizes().length; i++){
			    	writer.write("\n\t\t<Prize amount=\"" + oPanel.getPrizes()[i] + "\"/>");
			    }
			    writer.write("\n\t</Prizes>");
		    }
		    writer.write("\n\t<Players length=\"" + pPanel.getTotalPlayers() + "\">");
		    
		    if(started) {
			    Player[] players = pPanel.getPlayers();
			    for(int i=0; i<players.length; i++){
	
			    	Player player = players[i];
		    		writer.write("\n\t\t<Player first_name=\"" + player.getFirstName() + 
		    				"\" last_name=\"" + player.getLastName() + "\" id=\"" + 
		    				player.getId() + "\" elo=\"" + player.getElo() + 
		    				"\" highest_elo=\"" + player.getHighestElo() + "\" lowest_elo=\"" + 
		    				player.getLowestElo() + "\" average=\"" + player.getAverage() + 
		    				"\" highest_average=\"" + player.getHighestAverage() + 
		    				"\" lowest_average=\"" + player.getLowestAverage() + 
		    				"\" established=\"" + player.isEstablished() + "\"/>");
		    		
			    }
		    } else {
		    	IndividualPlayerPanel[] panels = Main.getControlPanel().getScreen().getPlayers().getPanels();
		    	for(int i=0; i<panels.length; i++) {
		    		NewPlayerPanel panel = (NewPlayerPanel) panels[i];
		    		writer.write("\n\t\t<Player first_name=\"" + panel.getFirstName() + "\" last_name=\"" + panel.getLastName() + "\" id=\"" + panel.getID() + "\" elo=\"" + panel.getHandicap() + "\"/>");
		    	}
		    }
		    
		    writer.write("\n\t</Players>");
		    if(started) {
			    writer.write("\n\t<Matches>");
			    
			    LinkedList<Match> matches = Main.getControlPanel().getBracket().getLLMatches();
			    
			    for(int i=0; i<matches.size(); i++) {
			    	
			    	Match match = matches.get(i);
	    			writer.write("\n\t\t<Match round=\"" + match.getRound() + "\" num=\"" + match.getNum() + "\" winner_side=\"" + match.isWinnersSide() + "\" race=\"" + match.getRace() + "\" spot=\"" + match.getSpot() + "\" started=\"" + match.hasStarted() + "\" finished=\"" + match.isFinished() + "\" table=\"" + match.getTable() + "\" forfeit=\"" + match.isForfeit() + "\" ");
	    			if(match.isForfeit()){
	    				writer.write("winner=\"" + match.getWinner().getId() + "\" ");
	    			}
	    			writer.write("split=\"" + match.isSplit() + "\"");
	    			if(match.isSplit()){
	    				writer.write(" p1=\"" + match.getP1().getMoney() + "\" p2=\"" + match.getP2().getMoney() +  "\"");
	    			}
	    			writer.write(">");
	    			if(match.getP1() != null){
	    				writer.write("\n\t\t\t<Player1 id=\"" + match.getP1().getId() + "\" p1Elo=\"" + match.getP1Elo() + "\" p1HighestElo=\"" + match.getP1HighestElo() + "\" p1LowestElo=\"" + match.getP1LowestElo() + "\" p1Average=\"" + match.getP1Average() + "\" p1HighestAverage=\"" + match.getP1HighestAverage() + "\" p1LowestAverage=\"" + match.getP1LowestAverage() + "\" score=\"" + match.getP1Score() + "\"/>");
	    			}
	    			else{
	    				writer.write("\n\t\t\t<Player1 id=\"0\" score=\"0\"/>");
	    			}
	    			if(match.getP2() != null){
	    				writer.write("\n\t\t\t<Player2 id=\"" + match.getP2().getId() + "\" p2Elo=\"" + match.getP2Elo() + "\" p2HighestElo=\"" + match.getP2HighestElo() + "\" p2LowestElo=\"" + match.getP2LowestElo() + "\" p2Average=\"" + match.getP2Average() + "\" p2HighestAverage=\"" + match.getP2HighestAverage() + "\" p2LowestAverage=\"" + match.getP2LowestAverage() + "\" score=\"" + match.getP2Score() + "\"/>");
	    			}
	    			else{
	    				writer.write("\n\t\t\t<Player2 id=\"0\" score=\"0\"/>");
	    			}
	    			writer.write("\n\t\t</Match>");
			    	
			    }
			    
			    writer.write("\n\t</Matches>");
		    }
		    writer.write("\n</Tournament>");
		    
		    Main.getMenuBar().getMenu(0).getItem(2).setEnabled(true);
		}
		catch (IOException e){
			Main.getControlPanel().showErrorMessage("An error ocurred while trying to save the current tournament. If this persists, please contact Etan Mizrahi-Shalom at etanms@gmail.com to report the issue.");
			e.printStackTrace();
		}
		finally{
		   try{
			   if(writer != null){
			   writer.close();
			   }
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
		}
		
		DBHSDatabaseIntermediary.submitSaveFile(Main.getControlPanel().getTournamentId(), currentFile);
		
	}//ends save()
	
	protected void saveSettings(File file){
		
		Writer writer = null;
		try{

			
			OptionsPanel oPanel = Main.getControlPanel().getScreen().getOptions();
		    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		    writer.write("<Tournament xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:BracketRunnerNamespaceSchemaLocation=\"tournament.xsd\" location=\"" + oPanel.getTournamentLocationAsInt() + "\" game=\"" + oPanel.getGameAsInt() + "\" affected=\"" + oPanel.handicapsAreAffected() + "\">" +
		    		"\n\t<Money entry=\"" + oPanel.getEntry() + "\" withheld=\"" + oPanel.getMoneyWithheld() + "\" added=\"" + oPanel.getMoneyAdded() + "\">" + 
		    		"\n\t</Money>" + 
		    		"\n\t<Format type=\"" + oPanel.getFormatAsInt() + "\">" + 
		    		"\n\t\t<Race length=\"" + oPanel.getRace() + "\"/>");
		    if(oPanel.getFormatAsInt() == 2){
		    	writer.write("\n\t\t<Race length=\"" + oPanel.getLosersRace() + "\"/>");
		    }
		    if(oPanel.getFormatAsInt() == 2){
		    	writer.write("\n\t\t<Finals matches=\"" + oPanel.getFinalsFormat() + "\">");
		    }
		    if(oPanel.getFormatAsInt() == 2 && oPanel.getFinalsFormat() == 2){
		    	writer.write("\n\t\t\t<Race length=\"" + oPanel.getRace() + "\"/>" + 
		    			"\n\t\t\t<Race length=\"" + oPanel.getLosersRace() + "\"/>");
		    }
		    else if(oPanel.getFormatAsInt() == 2){
		    	writer.write("\n\t\t\t<Race length=\"" + oPanel.getFinalsRace() + "\"/>");
		    }
		    if(oPanel.getFormatAsInt() == 2){
		    	writer.write("\n\t\t</Finals>");
		    }
		    writer.write("\n\t</Format>" + 
		    		"\n</Tournament>");
		}
		catch (IOException e){
			Main.getControlPanel().showErrorMessage("An error ocurred while trying to save the current tournament. If this persists, please contact Etan Mizrahi-Shalom at etanms@gmail.com to report the issue.");
			e.printStackTrace();
		}
		finally{
		   try{
			   if(writer != null){
			   writer.close();
			   }
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
		}
		
	}//ends saveSettings()

}//ends Class
