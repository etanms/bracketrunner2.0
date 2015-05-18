package com.etan.bracketrunner2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SplitPlayerPanel extends JPanel {
	
	static final int LEFT = 0;
	static final int RIGHT = 1;
	
	private NewPlayerPanel panel;
	
	/**
	 * Constructor
	 * 
	 * @param panel The NewPlayerPanel to be associated with this panel.
	 */
	public SplitPlayerPanel(NewPlayerPanel panel, SplitGroupDialog dialog, JComboBox<String> groupBox, int type) {
		
		this.panel = panel;
		
		Font font = new Font("Arial Black", Font.PLAIN, 16);
		
		setLayout(new GridLayout(1, 3));
		
		JLabel nameLabel = new JLabel(panel.getFirstName() + " " + panel.getLastName());
		nameLabel.setForeground(Color.BLACK);
		nameLabel.setFont(font);
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		namePanel.add(nameLabel);
		add(namePanel);
		
		JLabel groupLabel = new JLabel(groupBox.getItemAt(panel.getSplitGroup()));
		groupLabel.setForeground(Color.BLACK);
		groupLabel.setFont(font);
		JPanel groupPanel = new JPanel();
		groupPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		groupPanel.add(groupLabel);
		add(groupPanel);
		
		JButton button = new JButton();
		button.setForeground(Color.BLACK);
		button.setFont(font);
		button.addActionListener(dialog);
		
		if(type == LEFT) {
			button.setText("Add");
			if(panel.getSplitGroup() == groupBox.getSelectedIndex()) {
				button.setEnabled(false);
			}
		} else if(type == RIGHT) {
			button.setText("Remove");
			if(groupBox.getSelectedIndex() == 0) {
				button.setEnabled(false);
			}
		}
			
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(button);
		add(buttonPanel);
		
	}// ends Constructor
	
	/**
	 * @param group The split group to set the NewPlayerPanel to.
	 */
	void setSplitGroup(int group) {
		
		panel.setSplitGroup(group);
		
	}// ends setSplitGroup()

}// ends Class
