package com.etan.bracketrunner2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.etan.bracketrunner2.matches.Match;
import com.etan.bracketrunner2.matches.MatchFactory;
import com.etan.dbhs.DBHSDatabaseIntermediary;

/**
 * Panel used to display information about matches as well as 
 * to start and finish individual matches.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 *
 */
@SuppressWarnings("serial")
public class MatchesPanel extends JPanel implements ActionListener{
	
	private JComboBox<String> roundBox;
	private JPanel topPanel;
	private JPanel currentPanel;
	private JPanel roundPanel;
	
	private JScrollPane scroll_left;
	private JScrollPane scroll_right;
	
	private LinkedList<Match> matches;
	
	/**
	 * Constructor 
	 */
	public MatchesPanel(){
		
		matches = Main.getControlPanel().getBracket().getLLMatches();
		int w = Main.getControlPanel().getRounds();
		int l = Main.getControlPanel().getLoserRounds();
		
		Font font = new Font("Arial Black", Font.PLAIN, 24);
		
		// Set up the rounds ComboBox
		String[] rounds = new String[w + l];
		for(int i=0; i<w; i++){
			if(l != 0) {
				rounds[i] = "W" + Integer.toString(i+1);
			} else {
				rounds[i] = Integer.toString(i+1);
			}
		}
		for(int i=w; i<w + l; i++) {
			rounds[i] = "L" + Integer.toString(i-w+1);
		}
		
		roundBox = new JComboBox<String>(rounds);
		roundBox.setForeground(Color.BLACK);
		roundBox.setFont(font);
		roundBox.addActionListener(this);
		
		topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		topPanel.setLayout(new GridLayout(1, 2));
		
		JLabel label = new JLabel("Current Matches");
		label.setForeground(Color.BLACK);
		label.setFont(font);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.RED));
		panel.setBackground(Color.CYAN);
		panel.add(label);
		topPanel.add(panel);

		label = new JLabel("Round");
		label.setForeground(Color.BLACK);
		label.setFont(font);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.RED));
		panel.setBackground(Color.CYAN);
		panel.add(label);
		panel.add(roundBox);
		topPanel.add(panel);
		
		currentPanel = new JPanel();
		currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.Y_AXIS));
		currentPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.RED));
		currentPanel.setBackground(Color.LIGHT_GRAY);
		
		scroll_left = new JScrollPane(currentPanel);
		
		roundPanel = new JPanel();
		roundPanel.setLayout(new BoxLayout(roundPanel, BoxLayout.Y_AXIS));
		roundPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.RED));
		roundPanel.setBackground(Color.LIGHT_GRAY);
		
		scroll_right = new JScrollPane(roundPanel);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		mainPanel.add(scroll_left);
		mainPanel.add(scroll_right);
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		add(topPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
				
		// Set up timer to make buttons blink
		Thread timer = new Thread() {
			@Override
			public void run() {
				try {
					while(true) {
						for(int i=0; i<currentPanel.getComponentCount(); i++) {
							MatchPanel panel = (MatchPanel) currentPanel.getComponent(i);
							if(!panel.getMatch().hasStarted() && !panel.getMatch().isFinished()) {
								panel.blink();
							}
						}
						for(int i=0; i<roundPanel.getComponentCount(); i++) {
							MatchPanel panel = (MatchPanel) roundPanel.getComponent(i);
							if(!panel.getMatch().hasStarted() && !panel.getMatch().isFinished()) {
								panel.blink();
							}
						}
						
						Thread.sleep(1000);
					}
				}
				catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		};
		timer.start();
		
	}//ends MatchesPanel()

	/**
	 * Listens to the round selector.
	 */
	@Override
	public void actionPerformed(ActionEvent evt){
		
		updateInfo();
		
	}//ends actionPerformed()
	
	/**
	 * Set the JScrollPanes' sizes.
	 */
	void postConstruct() {
		
		scroll_left.setPreferredSize(new Dimension((int) currentPanel.getPreferredSize().getWidth(), (int) Main.getControlPanel().getScreen().getPreferredSize().getHeight() - (int) topPanel.getPreferredSize().getHeight()));
		scroll_right.setPreferredSize(new Dimension((int) roundPanel.getPreferredSize().getWidth(), (int) Main.getControlPanel().getScreen().getPreferredSize().getHeight() - (int) topPanel.getPreferredSize().getHeight()));
		
	}// ends postConstruct()
	
	/**
	 * Sets up the left and right panels to show the correct matches.
	 */
	public void redoMatches(){
		
		//Current Matches
		currentPanel.removeAll();
		for(int i=0; i<matches.size(); i++){
			Match match = matches.get(i);
			if(!match.isFinished() && match.getP1() != null && match.getP2() != null){
				currentPanel.add(new MatchPanel(match));
			}
		}
		
		//Round Panel Matches
		int w = Main.getControlPanel().getRounds();
		int selected;
		Match[][] m;
		if(roundBox.getSelectedIndex() < w) {
			selected = roundBox.getSelectedIndex()+1;
			m = Main.getControlPanel().getBracket().getMatches();
		} else {
			selected = roundBox.getSelectedIndex()-w+1;
			m = Main.getControlPanel().getBracket().getLoserMatches();
		}
		
		roundPanel.removeAll();
		
		int count = 1;
		while(count < m[selected].length && m[selected][count] != null) {
			roundPanel.add(new MatchPanel(m[selected][count]));
			count++;
		}
		
		for(int i=0; i<currentPanel.getComponentCount(); i++) {
			JPanel panel = (JPanel) currentPanel.getComponent(i);
			panel.setMaximumSize(new Dimension((int) panel.getMaximumSize().getWidth(), (int) panel.getPreferredSize().getHeight()));
		}
		for(int i=0; i<roundPanel.getComponentCount(); i++) {
			JPanel panel = (JPanel) roundPanel.getComponent(i);
			panel.setMaximumSize(new Dimension((int) panel.getMaximumSize().getWidth(), (int) panel.getPreferredSize().getHeight()));
		}
		
	}//ends redoMatches()
	
	/**
	 * Inputs data about each match into the database.
	 */
	void submitInfo() {
		
		int[] payed = Main.getControlPanel().getPlacesPaid();
		if(payed[Main.getControlPanel().getPrizes().length-1] == Main.getControlPanel().getTotalPlayers()) {
			int[] prizes = Main.getControlPanel().getPrizes();
			IndividualPlayerPanel[] panels = Main.getControlPanel().getScreen().getPlayers().getPanels();
			for(int i=0; i<panels.length; i++) {
				Match match = MatchFactory.createMatch(0, 0, MatchFactory.SINGLE);
				match.setP1(panels[i].getPlayer());
				match.setMoney(prizes[prizes.length-1]);
				DBHSDatabaseIntermediary.insertMatch(match);
			}
		}
		
		for(int i=0; i<matches.size(); i++) {
			DBHSDatabaseIntermediary.insertMatch(matches.get(i));
		}
		
	}// ends submitInfo()
	
	/**
	 * Sets up the screen to show current information.
	 */
	public void updateInfo(){
		
		redoMatches();
		
		validate();
		
		// Set the borders for MatchPanels in the currentPanel.
		for(int i=0; i<currentPanel.getComponentCount()-1; i++) {
			((JPanel) currentPanel.getComponent(i)).setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		}
		if(currentPanel.getComponentCount() != 0) {
			JPanel panel = (JPanel) currentPanel.getComponent(currentPanel.getComponentCount()-1);
			if(panel.getY() + panel.getHeight() < scroll_left.getHeight()) {
				panel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
			} else {
				panel.setBorder(null);
			}
		}
		
		// Set the borders for MatchPanels in the roundPanel.
		for(int i=0; i<roundPanel.getComponentCount()-1; i++) {
			((JPanel) roundPanel.getComponent(i)).setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		}
		if(roundPanel.getComponentCount() != 0) {
			JPanel panel = (JPanel) roundPanel.getComponent(roundPanel.getComponentCount()-1);
			if(panel.getY() + panel.getHeight() < scroll_left.getHeight()) {
				panel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
			} else {
				panel.setBorder(null);
			}
		}
		
		for(int i=0; i<currentPanel.getComponentCount(); i++){
			((MatchPanel)currentPanel.getComponent(i)).updateInfo();
		}
		for(int i=0; i<roundPanel.getComponentCount(); i++){
			((MatchPanel)roundPanel.getComponent(i)).updateInfo();
		}
		
		validate();
		repaint();
		
	}//ends updateInfo()
	
}// ends Class
