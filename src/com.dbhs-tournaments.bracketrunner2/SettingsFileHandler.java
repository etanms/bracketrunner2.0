package com.etan.bracketrunner2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SettingsFileHandler extends DefaultHandler {

	private boolean start;
	private int raceCount;
	private int location;
	private int game;
	private int entry;
	private int moneyWithheld;
	private int moneyAdded;
	private int format;
	private int race;
	private int race_2;
	private int finalsType;
	private int finalsRace;
	private int affected;
	
	public SettingsFileHandler() {
		
		start = true;
		raceCount = 0;
		
	}//ends Constructor;

	@Override
	public void endElement(String uri, String localName, String qName)throws SAXException {
		
		super.endElement(uri, localName, qName);
		
		if(qName.equals("Tournament")) {
			
			if(start) {
				//Set up the tournament details screen
				OptionsPanel option = Main.getControlPanel().getScreen().getOptions();
				option.setLocation(location);
				option.setGame(game);
				option.setEntry(entry);
				option.setMoneyWithheld(moneyWithheld);
				option.setMoneyAdded(moneyAdded);
				option.setFormat(format);
				option.setRace(race);
				if(format == 2) {
					option.setLosersRace(race_2);
					option.setFinalsFormat(finalsType);
					if(finalsType == 0){
						option.setFinalsRace(finalsRace);
					}
				}
				option.setAffected(affected);

				Main.getControlPanel().updateInfo();
				
			} else {
				
				Main.getControlPanel().showErrorMessage(
						"An error ocurred while retrieving data from the saved file you selected." +
						" The data from the file is likely corrupted." +
						" If you wish to try to retrieve the data still," +
						" contact Etan Mizrahi-Shalom at etanms@gmail.com.");
				
			}
		}
		
	}//ends endElement()
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
		
		super.startElement(uri, localName, qName, attributes);
		
		if(qName.equals("Tournament")) {
			try {
				location = Integer.parseInt(attributes.getValue("location"));
				game = Integer.parseInt(attributes.getValue("game"));
				affected = Integer.parseInt(attributes.getValue("affected"));
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
				start = false;
			}
		} else if(qName.equals("Money")) {
			try {
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
			catch(NumberFormatException e) {
				e.printStackTrace();
				start = false;
			}
		} else if(qName.equals("Finals")) {
			try {
				finalsType = Integer.parseInt(attributes.getValue("matches"))-1;
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
				start = false;
			}
		} else if(qName.equals("Race")) {
			try {
				if(raceCount == 0) {
					race = Integer.parseInt(attributes.getValue("length"));
				} else if(raceCount == 1) {
					race_2 = Integer.parseInt(attributes.getValue("length"));
				} else if(raceCount == 2) {
					finalsRace = Integer.parseInt(attributes.getValue("length"));
				}
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
				start = false;
			}
			raceCount++;
		}
		
	}//ends startElement()
	
}// ends Class
