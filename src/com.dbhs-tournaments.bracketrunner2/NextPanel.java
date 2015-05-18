package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

import com.etan.bracketrunner2.matches.Match;

/**
 * Panel used to quickly start the next match while on any screen.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 */
@SuppressWarnings("serial")
class NextPanel extends ClickablePanel implements ActionListener{
	
	private JLabel matchLabel;
	private JButton startButton;
	private JButton skipButton;
	private JButton nextButton;
	
	private Match currentMatch;
	
	private TableDialog dialog;
	
	private LinkedList<Match> matches;
	private int count;
	
	/**
	 * Constructor
	 */
	public NextPanel(){
		
		Font font = new Font("Arial Black", Font.PLAIN, 14);
		
		JLabel label = new JLabel("Next Match: ");
		label.setForeground(Color.BLACK);
		label.setFont(font);
		
		matchLabel = new JLabel();
		matchLabel.setForeground(Color.BLACK);
		matchLabel.setFont(font);
		
		nextButton = new JButton("Next");
		nextButton.setForeground(Color.BLACK);
		nextButton.setFont(font);
		nextButton.addActionListener(this);
		
		skipButton = new JButton("Skip");
		skipButton.setForeground(Color.BLACK);
		skipButton.setFont(font);
		skipButton.addActionListener(this);
		
		startButton = new JButton("Start");
		startButton.setForeground(Color.BLACK);
		startButton.setFont(font);
		startButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		buttonPanel.add(nextButton);
		buttonPanel.add(skipButton);
		buttonPanel.add(startButton);
		
		add(label);
		add(matchLabel);
		add(buttonPanel);

		count = 0;
		matches = Main.getControlPanel().getBracket().getLLMatches();
		
		next();
		if(count != -1){
			currentMatch = matches.get(count);
		}
		else{
			currentMatch = null;
		}
		
		setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, Color.RED));
		
	}//ends Constructor

	@Override
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource().equals(nextButton)){
			next();
		}
		else if(e.getSource().equals(skipButton)){
			count++;
			
			while(matches.get(count).hasStarted() || matches.get(count).getP1() == null || matches.get(count).getP2() == null){
				if(count == matches.size()-1){
					count = -1;
					break;
				}
				else{
					count++;
				}
			}
			if(count == -1){
				next();
			}
			else{
				matchLabel.setText(matches.get(count).getName());
				currentMatch = matches.get(count);
				if(count != matches.size()-1 && matches.get(count).getP1() != null && matches.get(count).getP2() != null){
					skipButton.setEnabled(true);
				}
				startButton.setEnabled(true);
			}
		}
		else if(e.getSource().equals(startButton)){
			if(Main.getControlPanel().getTables().length != 0){
				dialog = new TableDialog();
				dialog.setListener(this);
				dialog.setVisible(true);
			}
			else{
				start();
			}
		}
		else if(e.getActionCommand().equals("OK")){
			if(dialog.getNum() != -1){
				((TablePanel)Main.getControlPanel().getTables()[dialog.getNum()]).setMatch(matches.get(count));
				Main.getControlPanel().updateInfo();
				start();
				dialog.dispose();
			}
		}
		else if(e.getActionCommand().equals("Cancel")){
			dialog.dispose();
		}
		
	}//ends actionPerformed()
	
	/**
	 * @return The match that this panel is currently referring to.
	 */
	@Override
	Match getMatch() {
		
		return currentMatch;
		
	}// ends getMatch()
	
	void next(){
		
		count = 0;
		while(matches.get(count).hasStarted() || matches.get(count).getP1() == null || matches.get(count).getP2() == null){
			if(count != matches.size()-1){
				count++;
			}
			else{
				count = -1;
				break;
			}
		}
		if(count == -1){
			matchLabel.setText("N/A");
			skipButton.setEnabled(false);
			startButton.setEnabled(false);
			currentMatch = null;
		}
		else{
			matchLabel.setText(matches.get(count).getName());
			if(count != matches.size()-1){
				skipButton.setEnabled(true);
			}
			startButton.setEnabled(true);
			currentMatch = matches.get(count);
		}
		
	}//ends next()
	
	void start(){
		
		matches.get(count).setStarted(true);
		if(count == matches.size()-1){
			nextButton.setEnabled(false);
			skipButton.setEnabled(false);
			startButton.setEnabled(false);
		}
		else{
			nextButton.setEnabled(true);
			skipButton.setEnabled(false);
			startButton.setEnabled(false);
		}
		
		next();
		
		Main.getControlPanel().updateInfo();
		
	}//ends start()
	
	void updateInfo() {
		
		if((currentMatch != null && currentMatch.hasStarted()) || currentMatch == null) {
			next();
		}
		
		validate();
		repaint();
		
	}// ends updateInfo();

}//ends Class
