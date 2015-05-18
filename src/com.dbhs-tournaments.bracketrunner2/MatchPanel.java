package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.etan.bracketrunner2.matches.Match;

@SuppressWarnings("serial")
public class MatchPanel extends ClickablePanel implements ActionListener{
	
	private Match match;
	private JLabel p1Label;
	private JLabel p1Handicap;
	private JLabel p1Score;
	private JLabel p2Label;
	private JLabel p2Handicap;
	private JLabel p2Score;
	private JLabel spotLabel;
	private JLabel tableLabel;
	private JPanel tablePanel;
	private JButton button;
	private TableDialog dialog;
	
	/**
	 * Constructor
	 * 
	 * @param match The match to be associated with this MatchPanel.
	 */
	public MatchPanel(Match match){
		
		this.match = match;
		
		setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		setLayout(new GridLayout(1, 3));
		
		Font font = new Font("Arial Black", Font.PLAIN, 16);
		
		JLabel label = new JLabel(match.getName());
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Arial Black", Font.PLAIN, 22));
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(label);
		JPanel matchPanel = new JPanel();
		matchPanel.setLayout(new GridLayout(3,1, -12, -12));
		matchPanel.add(new JPanel());
		matchPanel.add(panel);
		matchPanel.add(new JPanel());
		add(matchPanel);
		
		JPanel namePanel = new JPanel();
		namePanel.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 4, Color.RED));
		namePanel.setLayout(new GridLayout(3, 1, 0, -8));
		
		Player p1 = match.getP1();
		Player p2 = match.getP2();
		
		if(p1 != null){
			String str = p1.getFirstName() + " " + p1.getLastName();
			p1Label = new JLabel(str);
			p1Label.setFont(font);
			p1Label.setFont(Main.getControlPanel().scaleFont(str, (Main.getWindow().getWidth()-Main.getWindow().getInsets().left-Main.getWindow().getInsets().right)/9, p1Label));
			
			p1Handicap = new JLabel(Integer.toString((int)match.getP1Elo()));
			p1Handicap.setFont(font);
			
			if(Main.getControlPanel().colored()){
				if(p1.isActive()){
					if(Main.getControlPanel().getFormat().equals("Double Elimination") && p1.getTotalMatches() - p1.getMatchesWon() == 1) {
						p1Label.setForeground(new Color(218, 196, 5));
						p1Handicap.setForeground(new Color(218, 196, 5));
					} else {
						p1Label.setForeground(Color.GREEN);
						p1Handicap.setForeground(Color.GREEN);
					}
				}
				else{
					p1Label.setForeground(Color.RED);
					p1Handicap.setForeground(Color.RED);
				}
			}
			else{
				p1Label.setForeground(Color.BLACK);
				p1Handicap.setForeground(Color.BLACK);
			}
			
			p1Score = new JLabel(" -  " + match.getP1Score());
			p1Score.setForeground(Color.BLACK);
			p1Score.setFont(font);
		}
		else{
			if(match.isWinnersSide()) {
				p1Label = new JLabel("TBD");
				p1Label.setForeground(Color.BLACK);
				p1Label.setFont(font);
			} else {
				if(match.isP1Bye()) {
					p1Label = new JLabel("Bye");
				} else {
					p1Label = new JLabel("TBD");
				}
				p1Label.setForeground(Color.BLACK);
				p1Label.setFont(font);
			}
			
			p1Handicap = new JLabel();
			p1Handicap.setForeground(Color.BLACK);
			p1Handicap.setFont(font);
			
			p1Score = new JLabel();
			p1Score.setForeground(Color.BLACK);
			p1Score.setFont(font);
		}
		panel = new JPanel();
		panel.add(p1Label);
		panel.add(p1Handicap);
		panel.add(p1Score);
		namePanel.add(panel);
		
		spotLabel = new JLabel();
		spotLabel.setForeground(Color.BLACK);
		spotLabel.setFont(font);
		if(p1!= null && p2 != null){
			spotLabel.setText(match.getSpot() + "/" + match.getRace());
		}
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(spotLabel);
		namePanel.add(panel);
		
		if(p2 != null){
			String str = p2.getFirstName() + " " + p2.getLastName();
			p2Label = new JLabel(str);
			p2Label.setFont(font);
			p2Label.setFont(Main.getControlPanel().scaleFont(str, (Main.getWindow().getWidth()-Main.getWindow().getInsets().left-Main.getWindow().getInsets().right)/9, p2Label));
			
			p2Handicap = new JLabel(Integer.toString((int)match.getP2Elo()));
			p2Handicap.setFont(font);
			
			if(Main.getControlPanel().colored()){
				if(p2.isActive()){
					if(Main.getControlPanel().getFormat().equals("Double Elimination") && p2.getTotalMatches() - p2.getMatchesWon() == 1) {
						p2Label.setForeground(new Color(218, 196, 5));
						p2Handicap.setForeground(new Color(218, 196, 5));
					} else {
						p2Label.setForeground(Color.GREEN);
						p2Handicap.setForeground(Color.GREEN);
					}
				}
				else{
					p2Label.setForeground(Color.RED);
					p2Handicap.setForeground(Color.RED);
				}
			}
			else{
				p2Label.setForeground(Color.BLACK);
				p2Handicap.setForeground(Color.BLACK);
			}
			
			p2Score = new JLabel(" -  " + match.getP1Score());
			p2Score.setForeground(Color.BLACK);
			p2Score.setFont(font);
		}
		else{
			if(match.isP2Bye()) {
				p2Label = new JLabel("BYE");
			} else {

				p2Label = new JLabel("TBD");
			}
			p2Label.setForeground(Color.BLACK);
			p2Label.setFont(font);
			
			p2Handicap = new JLabel();
			p2Handicap.setForeground(Color.BLACK);
			p2Handicap.setFont(font);
			
			p2Score = new JLabel();
			p2Score.setForeground(Color.BLACK);
			p2Score.setFont(font);
		}
		panel = new JPanel();
		panel.add(p2Label);
		panel.add(p2Handicap);
		panel.add(p2Score);
		namePanel.add(panel);

		add(namePanel);
		
		String str = null;
		if(match.isFinished()) {
			str = "Finished";
		} else if(match.hasStarted()) {
			str = "Started";
			
			TablePanel[] tables = Main.getControlPanel().getTables();
			for(int i=0; i<tables.length; i++) {
				if(tables[i].getMatch() == match) {
					str = tables[i].getTableName();
				}
			}
		} else {
			str = "Not Started";
		}
		
		tableLabel = new JLabel(str);
		tableLabel.setForeground(Color.BLACK);
		tableLabel.setFont(font);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(tableLabel);
		tablePanel = new JPanel();
		tablePanel.setLayout(new GridLayout(2, 1, 0, -8));
		tablePanel.add(panel);
		
		button = new JButton();
		button.setForeground(Color.BLACK);
		button.setFont(font);
		button.addActionListener(this);
		tablePanel.add(new JPanel());
		
		add(tablePanel);
		
		updateInfo();
		
	}//ends MatchPanel

	@Override
	public void actionPerformed(ActionEvent evt){
		
		if(evt.getActionCommand().equals("Start")) {
			if(Main.getControlPanel().getTables().length != 0) {
				dialog = new TableDialog();
				dialog.setListener(this);
				dialog.setVisible(true);
			} else {
				match.setStarted(true);
				Main.getControlPanel().getInfoPanel().next();
			}
		} else if(evt.getActionCommand().equals("OK")) {
			if(dialog.getNum() != -1) {
				Main.getControlPanel().getTables()[dialog.getNum()].setMatch(match);
				dialog.dispose();
				match.setStarted(true);
				Main.getControlPanel().getInfoPanel().next();
			}
		} else if(evt.getActionCommand().equals("Cancel")) {
			dialog.dispose();
		} else if(evt.getActionCommand().equals("End")) {
			Main.getControlPanel().getScreen().getBracket().showEndMatchDialog(match);
		} else if(evt.getActionCommand().equals("Undo")) {
			if(match.isUndoable()){
				Main.getControlPanel().getScreen().getBracket().showUndoMatchDialog(match);
			}
		}
		
		Main.getControlPanel().updateInfo();
		
	}//ends actionPerformed()
	
	/**
	 * Switches the foreground color on the button
	 * to signify that a match is ready to be started.
	 */
	public void blink() {
		
		if(button.getForeground().equals(Color.CYAN)) {
			button.setForeground(Color.BLACK);
		} else {
			button.setForeground(Color.CYAN);
		}
		
	}// ends blink();
	
	/**
	 * @return The match associated with this MatchPanel
	 */
	public Match getMatch(){
		
		return match;
		
	}//ends getMatch()
	
	/**
	 * Makes sure everything is showing current information.
	 */
	public void updateInfo(){
		
		Font font = new Font("Arial Black", Font.PLAIN, 16);
		
		Player p1 = match.getP1();
		Player p2 = match.getP2();
		
		if(p1 != null){
			p1Label.setText(p1.getFirstName() + " " + p1.getLastName());
			p1Handicap.setText(Integer.toString((int) match.getP1Elo()));
			if(Main.getControlPanel().colored()){
				if(p1.isActive()){
					if(Main.getControlPanel().getFormat().equals("Double Elimination") && p1.getTotalMatches() - p1.getMatchesWon() == 1) {
						p1Label.setForeground(new Color(218, 196, 5));
						p1Handicap.setForeground(new Color(218, 196, 5));
					} else {
						p1Label.setForeground(Color.GREEN);
						p1Handicap.setForeground(Color.GREEN);
					}
				}
				else{
					p1Label.setForeground(Color.RED);
					p1Handicap.setForeground(Color.RED);
				}
			}
			else{
				p1Label.setForeground(Color.BLACK);
				p1Handicap.setForeground(Color.BLACK);
			}
			p1Score.setText(" - " + match.getP1Score());
			p1Score.setForeground(Color.BLACK);
		}
		else{
			if(match.isP1Bye()) {
				p1Label .setText("BYE");
			} else {
				p1Label.setText("TBD");
			}
			p1Label.setForeground(Color.BLACK);
			p1Label.setFont(font);
			
			p1Handicap = new JLabel();
			p1Handicap.setForeground(Color.BLACK);
			p1Handicap.setFont(font);
			
			p1Score = new JLabel();
			p1Score.setForeground(Color.BLACK);
			p1Score.setFont(font);
		}
		
		if(p2 != null){
			p2Label.setText(p2.getFirstName() + " " + p2.getLastName());
			p2Handicap.setText(Integer.toString((int)match.getP2Elo()));
			if(Main.getControlPanel().colored()){
				if(Main.getControlPanel().colored()){
					if(p2.isActive()){
						if(Main.getControlPanel().getFormat().equals("Double Elimination") && p2.getTotalMatches() - p2.getMatchesWon() == 1) {
							p2Label.setForeground(new Color(218, 196, 5));
							p2Handicap.setForeground(new Color(218, 196, 5));
						} else {
							p2Label.setForeground(Color.GREEN);
							p2Handicap.setForeground(Color.GREEN);
						}
					}
					else{
						p2Label.setForeground(Color.RED);
						p2Handicap.setForeground(Color.RED);
					}
				}
				else{
					p2Label.setForeground(Color.BLACK);
					p2Handicap.setForeground(Color.RED);
				}
			}
			else{
				p2Label.setForeground(Color.BLACK);
				p2Handicap.setForeground(Color.BLACK);
			}
			p2Score.setText(" - " + match.getP2Score());
			p2Score.setForeground(Color.BLACK);
		}
		else{
			if(match.isP2Bye()) {
				p2Label .setText("BYE");
			} else {
				p2Label.setText("TBD");
			}
			p2Label.setForeground(Color.BLACK);
			p2Label.setFont(font);
			
			p2Handicap = new JLabel();
			p2Handicap.setForeground(Color.BLACK);
			p2Handicap.setFont(font);
			
			p2Score = new JLabel();
			p2Score.setForeground(Color.BLACK);
			p2Score.setFont(font);
		}
		
		if(p1!= null && p2 != null){
			spotLabel.setText(match.getSpot() + "/" + match.getRace());
		}
		
		if(match.isFinished()){
			tableLabel.setText("Finished");
			button.setText("Undo");
			button.setForeground(Color.BLACK);
		}
		else if(match.hasStarted()){
			String str = "Started";
			TablePanel[] tables = Main.getControlPanel().getTables();
			for(int i=0; i<tables.length; i++) {
				if(tables[i].getMatch() == match) {
					str = tables[i].getTableName();
				}
			}
			
			tableLabel.setText(str);
			button.setText("End");
			button.setForeground(Color.BLACK);
		}
		else{
			tableLabel.setText("Not Started");
			button.setText("Start");
		}
		
		if(match.getP1() != null && match.getP2() != null){
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel.add(button);
			if(tablePanel.getComponentCount() > 1 
					&& tablePanel.getComponent(1) != null) {
				tablePanel.remove(1);
			}
			tablePanel.add(panel);
		}
		
		validate();
		repaint();
		
	}//ends updateInfo()

}//ends Class
