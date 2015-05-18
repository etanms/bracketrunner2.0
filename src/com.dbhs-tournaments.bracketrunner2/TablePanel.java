package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.etan.bracketrunner2.matches.Match;
import com.etan.widgets.SizableJCheckBox;

/**
 * Panel used to represent a table on the Tables screen.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 */
@SuppressWarnings("serial")
public class TablePanel extends ClickablePanel implements ActionListener {
	
	private JCheckBox box;
	private JTextField nameTF;
	private JLabel nameLabel;
	private JLabel statusLabel;
	private JButton addButton;
	
	private Match match;
	private boolean created;// Wheter or not it has been created or is still in that process.
	private boolean designated;// Whether or not it is a tournament table
	
	/**
	 * Constructor
	 */
	public TablePanel() {
		
		Font font = new Font("Arial Black", Font.PLAIN, 18);
		
		box = new SizableJCheckBox(18);
		
		nameTF = new JTextField(15);
		nameTF.setForeground(Color.BLACK);
		nameTF.setFont(font);
		
		addButton = new JButton("Add");
		addButton.setForeground(Color.BLACK);
		addButton.setFont(font);
		addButton.addActionListener(this);
		
		add(nameTF);
		add(addButton);
		
	}// ends TablePanel()
	
	@Override
	public void actionPerformed(ActionEvent evt){
		
		create(null);
		
	}//ends actionPerformed()
	
	/**
	 * Sets up the panel for when the table is created.
	 */
	void create(String str) {
		
		if(str != null) {
			nameTF.setText(str);
		}
		
		Font font = new Font("Arial Black", Font.PLAIN, 18);
		
		nameLabel = new JLabel(nameTF.getText().trim());
		nameLabel.setForeground(Color.BLACK);
		nameLabel.setFont(font);
		
		statusLabel = new JLabel("OPEN");
		statusLabel.setForeground(Color.GREEN);
		statusLabel.setFont(font);
		
		removeAll();
		setLayout(new GridLayout(1,3));
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(box);
		add(panel);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(nameLabel);
		add(panel);
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(statusLabel);
		add(panel);
		
		validate();
		repaint();
		
		created = true;
		
	}// ends create()
	
	/**
	 * @return The match currently associated with this panel.
	 */
	public Match getMatch() {
		
		return match;
		
	}// ends getMatch()
	
	/**
	 * @return The name of the table associated with this TablePanel.
	 */
	public String getTableName(){
		
		return nameLabel.getText();
		
	}//ends getTableName()
	
	/**
	 * @return Whether or not the table has been created or is 
	 * still in that process.
	 */
	public boolean isCreated() {
		
		return created;
		
	}// ends isCreated()
	
	/**
	 * @return Whether of not the table is a designated tournament table.
	 */
	public boolean isDesignated() {
		
		return designated;
		
	}// ends isDesignated()
	
	/**
	 * @return Whether or not the checkbox associated with this
	 * panel is selected.
	 */
	boolean isSelected() {
		
		return box.isSelected();
				
	}// ends isSelected()
	
	/**
	 * @param match The match to associate with this match.
	 */
	public void setMatch(Match match) {
		
		this.match = match;
		
		if(match != null) {
			statusLabel.setText("IN USE");
			statusLabel.setForeground(Color.RED);
		} else {
			statusLabel.setText("OPEN");
			statusLabel.setForeground(Color.GREEN);
		}
		
	}// ends setMatch()
	
	/**
	 * @param designated Whether or not it is a tournament table.
	 */
	public void setDesignated(Boolean designated) {
		
		this.designated = designated;
		
	}// ends setDesignated()
	
	/**
	 * @param bool Whether or not the checkbox associated to 
	 * this panel should be selected.
	 */
	void setSelected(boolean bool) {
		
		box.setSelected(bool);
		
	}// ends setSelected()
	
	/**
	 * Makes sure that this is showing current information.
	 */
	void updateInfo() {
		
		validate();
		repaint();
		
	}// ends updateInfo()

}// ends Class
