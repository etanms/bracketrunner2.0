package com.etan.bracketrunner2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.etan.dbhs.DBHSDatabaseIntermediary;
import com.etan.dbhs.DBHSPlayerInitializer;

@SuppressWarnings("serial")
public class PrizesDialog extends JDialog implements ActionListener, FocusListener {
	
	static final int SINGLE = 0;
	static final int MODIFIED = 1;
	static final int DOUBLE = 2;
	
	int players;
	int format;
	int prizeFund;
	
	JLabel prizeLeftLabel;
	JPanel entryPanel;
	String[] placesPaidOptions;
	JComboBox<String> placesPaidBox;
	JButton okButton;
	JButton cancelButton;
	
	public PrizesDialog(int players, int entry, int withheld, int added, int format) {
		
		super(Main.getWindow(), "Payouts", Dialog.ModalityType.TOOLKIT_MODAL);
		
		this.players = players;
		this.format = format;
		
		prizeFund = (entry-withheld) * players + added;
		
		Font font = new Font("Arial Black", Font.BOLD, 14);
		
		JLabel totalPlayersLabel = new JLabel("Players: " + Integer.toString(players));
		totalPlayersLabel.setForeground(Color.BLACK);
		totalPlayersLabel.setFont(font);
		
		JLabel prizeFundLabel = new JLabel("Total Prize Fund: $" + Integer.toString(prizeFund));
		prizeFundLabel.setForeground(Color.BLACK);
		prizeFundLabel.setFont(font);
		
		prizeLeftLabel = new JLabel("Prize Fund Left: $" + Integer.toString(prizeFund));
		prizeLeftLabel.setForeground(Color.BLACK);
		prizeLeftLabel.setFont(font);
		
		JLabel placesPaidLabel = new JLabel("How many places would");
		placesPaidLabel.setForeground(Color.BLACK);
		placesPaidLabel.setFont(font);
		
		JLabel placesPaidLabel_2 = new JLabel("you like to pay?");
		placesPaidLabel_2.setForeground(Color.BLACK);
		placesPaidLabel_2.setFont(font);
		
		
		placesPaidOptions = null;
		if(format == SINGLE) {
			
			int count = 0;
			while(Math.pow(2, count) < players){
				count++;
			}
			placesPaidOptions = new String[count+1];
			for(int i=0; i<=count; i++){
				placesPaidOptions[i] = Integer.toString((int)Math.pow(2, i));
			}
			placesPaidOptions[placesPaidOptions.length-1] = Integer.toString(players);
			
		} else if(format == MODIFIED) {
			
			int count = 4;
			while(Math.pow(2, count) < players){
				count++;
			}
			if(players <= Math.pow(2, count) * 3/4) {
				placesPaidOptions = new String[count+1];
				for(int i=0; i<count; i++){
					placesPaidOptions[i] = Integer.toString((int)Math.pow(2, i));
				}
			} else {
				placesPaidOptions = new String[count+2];
				for(int i=0; i<count; i++){
					placesPaidOptions[i] = Integer.toString((int)Math.pow(2, i));
				}
				placesPaidOptions[count] = Integer.toString((int)Math.pow(2, count) * 3/4);
			}
			placesPaidOptions[placesPaidOptions.length-1] = Integer.toString(players);
			
		} else if(format == DOUBLE) {
			
			if(players <= 5) {
				
				placesPaidOptions = new String[players];
				for(int i=0; i<players; i++) {
					placesPaidOptions[i] = Integer.toString(i+1);
				}
				
			} else {
				
				int count = 5;
				while((Math.pow(2, count/2) + Math.pow(2, (count+1)/2)) / 2 < players) {
					count++;
				}
				
				placesPaidOptions = new String[count];
				for(int i=0; i<4; i++) {
					placesPaidOptions[i] = Integer.toString(i + 1);
				}
				for(int i=4; i<count; i++) {
					placesPaidOptions[i] = Integer.toString(
							(int) (Math.pow(2, (i+1)/2) + Math.pow(2, (i+2)/2)) / 2);
				}
				if(Integer.parseInt(placesPaidOptions[count - 1]) != players) {
					placesPaidOptions[count - 1] = Integer.toString(players);
				}
				
			}
			
		}
		
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(Color.CYAN);
		labelPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		labelPanel.add(totalPlayersLabel);
		labelPanel.add(prizeFundLabel);
		labelPanel.add(prizeLeftLabel);
		
		JPanel ppLabelPanel = new JPanel();
		ppLabelPanel.setOpaque(false);
		ppLabelPanel.setLayout(new BoxLayout(ppLabelPanel, BoxLayout.Y_AXIS));
		ppLabelPanel.add(placesPaidLabel);
		ppLabelPanel.add(placesPaidLabel_2);
		
		entryPanel = new JPanel();
		entryPanel.setLayout(new GridLayout(0,1));
		
		JScrollPane pane = new JScrollPane(entryPanel);
		pane.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 4, Color.RED));
		
		placesPaidBox = new JComboBox<String>(placesPaidOptions);
		placesPaidBox.setForeground(Color.BLACK);
		placesPaidBox.setFont(font);
		placesPaidBox.addActionListener(this);
		placesPaidBox.setSelectedIndex(0);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.add(placesPaidBox);
		
		JPanel ppPanel = new JPanel();
		ppPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 4, Color.RED));
		ppPanel.setLayout(new GridLayout(1,2));
		ppPanel.add(ppLabelPanel);
		ppPanel.add(panel);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2,1));
		topPanel.add(labelPanel);
		topPanel.add(ppPanel);
		
		okButton = new JButton("OK");
		okButton.setForeground(Color.BLACK);
		okButton.setFont(font);
		okButton.addActionListener(this);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setFont(font);
		cancelButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.add(topPanel, BorderLayout.NORTH);
		content.add(pane, BorderLayout.CENTER);
		content.add(buttonPanel, BorderLayout.SOUTH);
		
		WindowListener listener = new WindowListener() {
			
			@Override
			public void windowActivated(WindowEvent evt) {
				
			}

			@Override
			public void windowClosed(WindowEvent evt) {
				
			}

			@Override
			public void windowClosing(WindowEvent evt) {
				
				Main.getControlPanel().getScreen().getPlayers()
				.setButtonsEnabled(true);
				
			}

			@Override
			public void windowDeactivated(WindowEvent evt) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent evt) {
				
			}

			@Override
			public void windowIconified(WindowEvent evt) {
				
			}

			@Override
			public void windowOpened(WindowEvent evt) {
				
			}
			
		};
		
		setPreferredSize(new Dimension(600, 500));
		setContentPane(content);
		pack();
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	addWindowListener(listener);
		
	}// ends PrizesDialog()

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(placesPaidBox)) {
			
			Font font = new Font("Arial Black", Font.BOLD, 14);
			
			entryPanel.removeAll();
				
			for(int i=0; i<=placesPaidBox.getSelectedIndex(); i++){
				JLabel label;
				if(Integer.parseInt(placesPaidOptions[i]) == 1 || Integer.parseInt(placesPaidOptions[i]) == Integer.parseInt(placesPaidOptions[i-1])+1) {
					label = new JLabel(placesPaidOptions[i] + ".");
				} else {
					label = new JLabel(Integer.toString(Integer.parseInt(placesPaidOptions[i-1])+1) + "-" + placesPaidOptions[i] + ".");
				}

				label.setForeground(Color.BLACK);
				label.setFont(font);
				
				JLabel label_2 = new JLabel("$");
				label_2.setForeground(Color.BLACK);
				label_2.setFont(font);
				
				JTextField tb = new JTextField(5);
				tb.setForeground(Color.BLACK);
				tb.setFont(font);
				tb.addFocusListener(this);
			
				JPanel leftPayoutPanel = new JPanel();
				leftPayoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				leftPayoutPanel.add(label);
			
				JPanel rightPayoutPanel = new JPanel();
				rightPayoutPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
				rightPayoutPanel.add(label_2);
				rightPayoutPanel.add(tb);
			
				JPanel panel = new JPanel();
				panel.setLayout(new GridLayout(1,2));
				panel.add(leftPayoutPanel);
				panel.add(rightPayoutPanel);
			
				entryPanel.add(panel);
			}
			
			prizeLeftLabel.setText("Prize Fund Left: $" + Integer.toString(prizeFund));
			
			validate();
			repaint();
			
		} else if(evt.getSource().equals(okButton)) {
			
			final ControlPanel control = Main.getControlPanel();
			
			int prizeFundUsed = 0;
			
			try{
			    for(int i=0; i<=placesPaidBox.getSelectedIndex(); i++){
			    	if(!((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText().equals("")){
			    		if(i<=1){
			    			
			    			prizeFundUsed += Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
			    			
			    		} else if(format == SINGLE){
			    			
			    			if(placesPaidBox.getSelectedIndex() == placesPaidBox.getItemCount()-1 && i+1 == placesPaidBox.getItemCount()){
			    				prizeFundUsed += (players-Integer.parseInt(placesPaidBox.getItemAt(placesPaidBox.getItemCount()-2).toString()))*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
			    			}
			    			else{
			    				prizeFundUsed += Math.pow(2, i-1)*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
			    			}
			    			
			    		} else if(format == MODIFIED){
			    			
			    			if(placesPaidBox.getSelectedIndex() == placesPaidBox.getItemCount()-1 && i+1 == placesPaidBox.getItemCount()){
			    				prizeFundUsed += (players-Integer.parseInt(placesPaidBox.getItemAt(placesPaidBox.getItemCount()-2).toString()))*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
			    			}
			    			else{
			    				prizeFundUsed += Math.pow(2, i-1)*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
			    			}
			    			
			    		} else if(format == DOUBLE){
			    			
			    			if(i<=3){
			    				prizeFundUsed += Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
			    			}
			    			else if(placesPaidBox.getSelectedIndex() == placesPaidBox.getItemCount()-1 && i+1 == placesPaidBox.getItemCount()){
			    				prizeFundUsed += (players-Integer.parseInt(placesPaidBox.getItemAt(placesPaidBox.getItemCount()-2).toString()))*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
			    			}
			    			else{
			    				prizeFundUsed += (Integer.parseInt(placesPaidBox.getItemAt(i).toString())-Integer.parseInt(placesPaidBox.getItemAt(i-1).toString()))*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
			    			}
			    			
			    		}
			    	}
				}
			}
			catch(NumberFormatException e){
				control.showErrorMessage("Please make sure that all payouts are an integer number.");
			}

			if(prizeFundUsed != prizeFund) {
				
				control.showErrorMessage("Please make sure that the amount of money being payed out is equal to the amount of money in the prize pool.");
				
			} else {
				
				Thread t = new Thread() {
					
					LoadingDialog dialog;
					
					@Override
					public void run() {
						
						dispose();
						
						dialog = new LoadingDialog();
						dialog.setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - dialog.getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - dialog.getHeight()/2);
						dialog.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
				        dialog.setResizable(false);
				        dialog.setVisible(true);
						
						int[] prizes = new int[entryPanel.getComponentCount()];
						for(int i=0; i<entryPanel.getComponentCount(); i++) {
							prizes[i] = Integer.parseInt(((JTextField)((JPanel) ((JPanel) entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
						}
						control.getScreen().getOptions().setPrizes(prizes);
						
						if(!control.hasStarted()) {
							
							control.getScreen().getPlayers().setButtonsEnabled(true);
							control.startTournament();
							
							if(control.getTournamentId() == 0) {
								DBHSDatabaseIntermediary.insertTournament();
								control.setTournamentId();
								control.lockPlayers();

								Main.getMenuBar().getMenu(1).getMenuComponent(0).setEnabled(false);
								Main.getMenuBar().getMenu(1).getMenuComponent(1).setEnabled(false);
							}
							
							//Save the tournament right when created
							Main.getMenuBarActionListener().save(
									control.getScreen().getOptions().getTournamentLocation() 
									+ "_" + Calendar.getInstance().get(Calendar.YEAR)
									+ "-" + (Calendar.getInstance().get(Calendar.MONTH)+1) 
									+ "-" + Calendar.getInstance().get(Calendar.DATE));
							
						} else {
							
							control.getScreen().getPlayers().addPlayerToBracket();
							control.getBracket().resetPrizes();
							control.getScreen().getOptions().resetPrizes();
							DBHSDatabaseIntermediary.updatePlayerAmount();
							DBHSPlayerInitializer.lockPlayer(control.getScreen().getPlayers().getPlayers()[control.getTotalPlayers()-1]);
							control.updateInfo();
							
						}
						
						dialog.dispose();
						
					}// ends run()
					
				};
				
				t.start();
				
			}
			
		} else if(evt.getSource().equals(cancelButton)) {
			
			if(Main.getControlPanel().hasStarted()) {
				Main.getControlPanel().getScreen().getPlayers().removePlayer();
			}
			
			Main.getControlPanel().getScreen().getPlayers().setButtonsEnabled(true);
			
			dispose();
			
		}
		
	}// ends actionPerformed()

	@Override
	public void focusGained(FocusEvent evt) {
		
	}// ends focusGained()

	@Override
	public void focusLost(FocusEvent evt) {
		
		int prizeFundUsed = 0;
		
		try{
		    for(int i=0; i<=placesPaidBox.getSelectedIndex(); i++){
		    	if(!((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText().equals("")){
		    		if(i<=1){
		    			
		    			prizeFundUsed += Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
		    			
		    		} else if(format == SINGLE){
		    			
		    			if(placesPaidBox.getSelectedIndex() == placesPaidBox.getItemCount()-1 && i+1 == placesPaidBox.getItemCount()){
		    				prizeFundUsed += (players-Integer.parseInt(placesPaidBox.getItemAt(placesPaidBox.getItemCount()-2).toString()))*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
		    			}
		    			else{
		    				prizeFundUsed += Math.pow(2, i-1)*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
		    			}
		    			
		    		} else if(format == MODIFIED){
		    			
		    			if(placesPaidBox.getSelectedIndex() == placesPaidBox.getItemCount()-1 && i+1 == placesPaidBox.getItemCount()){
		    				prizeFundUsed += (players-Integer.parseInt(placesPaidBox.getItemAt(placesPaidBox.getItemCount()-2).toString()))*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
		    			}
		    			else{
		    				prizeFundUsed += Math.pow(2, i-1)*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
		    			}
		    			
		    		} else if(format == DOUBLE){
		    			
		    			if(i<=3){
		    				prizeFundUsed += Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
		    			}
		    			else if(placesPaidBox.getSelectedIndex() == placesPaidBox.getItemCount()-1 && i+1 == placesPaidBox.getItemCount()){
		    				prizeFundUsed += (players-Integer.parseInt(placesPaidBox.getItemAt(placesPaidBox.getItemCount()-2).toString()))*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
		    			}
		    			else{
		    				prizeFundUsed += (Integer.parseInt(placesPaidBox.getItemAt(i).toString())-Integer.parseInt(placesPaidBox.getItemAt(i-1).toString()))*Integer.parseInt(((JTextField)((JPanel)((JPanel)entryPanel.getComponent(i)).getComponent(1)).getComponent(1)).getText());
		    			}
		    			
		    		}
		    	}
			}
		    prizeLeftLabel.setText("Prize Fund Left: $" + Integer.toString(prizeFund - prizeFundUsed));
		    prizeLeftLabel.validate();
		    prizeLeftLabel.repaint();
		}
		catch(NumberFormatException e){
			Main.getControlPanel().showErrorMessage("Please make sure that all payouts are an integer number.");
		}
		
	}// ends focusLost()

}// ends Class
