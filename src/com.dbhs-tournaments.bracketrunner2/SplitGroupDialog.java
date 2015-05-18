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
import java.util.ArrayList;

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

@SuppressWarnings("serial")
public class SplitGroupDialog extends JDialog implements ActionListener {
	
	private IndividualPlayerPanel[] panels;
	
	private JButton newButton;
	
	private JComboBox<String> groupBox;
	
	private JPanel topPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private JScrollPane scroll_left;
	private JScrollPane scroll_right;
	
	private JDialog dialog;
	
	/**
	 * Constructor
	 */
	public SplitGroupDialog(IndividualPlayerPanel[] panels) {
		
		super(Main.getWindow(), "Split Players");
		
		this.panels = panels;
		
		Font font = new Font("Arial Black", Font.PLAIN, 16);
		
		topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		topPanel.setLayout(new GridLayout(1, 2));
		
		JLabel label = new JLabel("Current Groups");
		label.setForeground(Color.BLACK);
		label.setFont(font);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.RED));
		panel.setBackground(Color.CYAN);
		panel.add(label);
		topPanel.add(panel);
		
		newButton = new JButton("New Group");
		newButton.setForeground(Color.BLACK);
		newButton.setFont(font);
		newButton.addActionListener(this);
		
		JPanel newButtonPanel = new JPanel();
		newButtonPanel.setBackground(Color.CYAN);
		newButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		newButtonPanel.add(newButton);

		label = new JLabel("Group:");
		label.setForeground(Color.BLACK);
		label.setFont(font);
		
		ArrayList<String> groups = Main.getControlPanel().getScreen().getPlayers().getSplitGroups();
		String[] groupsArray = new String[groups.size()];
		groupsArray = groups.toArray(groupsArray);
		
		groupBox = new JComboBox<String>(groupsArray);
		groupBox.setForeground(Color.BLACK);
		groupBox.setFont(font);
		groupBox.addActionListener(this);
		
		JPanel groupsPanel = new JPanel();
		groupsPanel.setBackground(Color.CYAN);
		groupsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		groupsPanel.add(label);
		groupsPanel.add(groupBox);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.RED));
		panel.add(newButtonPanel);
		panel.add(groupsPanel);
		topPanel.add(panel);
		
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.RED));
		leftPanel.setBackground(Color.LIGHT_GRAY);
		
		scroll_left = new JScrollPane(leftPanel);
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.RED));
		rightPanel.setBackground(Color.LIGHT_GRAY);
		
		scroll_right = new JScrollPane(rightPanel);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		mainPanel.add(scroll_left);
		mainPanel.add(scroll_right);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		content.add(topPanel, BorderLayout.NORTH);
		content.add(mainPanel, BorderLayout.CENTER);
		
		setContentPane(content);
		setSize(new Dimension(1000, 500));
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - getWidth()/2,
				Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - getHeight()/2);
    	setResizable(false);
		
	}// ends Constructor

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(newButton)) {
			
			dialog = new JDialog(this, "New Group", Dialog.ModalityType.APPLICATION_MODAL);
			
			Font font = new Font("Arial Black", Font.PLAIN, 16);
			
			JPanel labelPanel = new JPanel();
			labelPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
			labelPanel.setLayout(new GridLayout(2, 1));
			
			JLabel label = new JLabel("What would you like to");
			label.setForeground(Color.BLACK);
			label.setFont(font);
			JPanel panel = new JPanel();
			panel.setBackground(Color.CYAN);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel.add(label);
			labelPanel.add(panel);
			
			JLabel label_2 = new JLabel("name the new group?");
			label_2.setForeground(Color.BLACK);
			label_2.setFont(font);
			panel = new JPanel();
			panel.setBackground(Color.CYAN);
			panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			panel.add(label_2);
			labelPanel.add(panel);
			
			final JTextField tf = new JTextField(20);
			tf.setForeground(Color.BLACK);
			tf.setFont(font);
			JPanel tfPanel = new JPanel();
			tfPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			tfPanel.add(tf);
			
			JButton okButton = new JButton("OK");
			okButton.setForeground(Color.BLACK);
			okButton.setFont(font);
			okButton.addActionListener(this);
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {
					
					if(!tf.getText().equals("")) {
						
						ArrayList<String> groups = new ArrayList<String>();
						for(int i=0; i<groupBox.getItemCount(); i++) {
							groups.add(groupBox.getItemAt(i));
						}
						groups.add(tf.getText());
						
						Main.getControlPanel().getScreen().getPlayers().setSplitGroups(groups);
						groupBox.addItem(tf.getText());
						
					}
					
				}
				
			});
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setForeground(Color.BLACK);
			cancelButton.setFont(font);
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent evt) {
					
					dialog.dispose();
					
				}// ends actionPerformed()
				
			});
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, Color.RED));
			buttonPanel.setLayout(new GridLayout(1, 2));
			buttonPanel.add(okButton);
			buttonPanel.add(cancelButton);
			
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());
			content.add(labelPanel, BorderLayout.NORTH);
			content.add(tfPanel, BorderLayout.CENTER);
			content.add(buttonPanel, BorderLayout.SOUTH);

			dialog.setContentPane(content);
			dialog.pack();
			dialog.setLocation(getLocation().x + getWidth()/2 - dialog.getWidth()/2, getLocation().y + getHeight()/2 - dialog.getHeight()/2);
	    	dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        dialog.setResizable(false);
	        dialog.setVisible(true);
			
		} else if(evt.getSource().equals(groupBox)) {
			
			redoPanels();
			
		} else if(evt.getActionCommand().equals("OK")) {
			
			dialog.dispose();
			
		} else if(evt.getActionCommand().equals("Add")) {
			
			SplitPlayerPanel panel = (SplitPlayerPanel) ((JButton) evt.getSource()).getParent().getParent();
			panel.setSplitGroup(groupBox.getSelectedIndex());
			redoPanels();
			
		} else if(evt.getActionCommand().equals("Remove")) {

			SplitPlayerPanel panel = (SplitPlayerPanel) ((JButton) evt.getSource()).getParent().getParent();
			panel.setSplitGroup(0);
			redoPanels();
			
		}
		
	}// ends actionPerformed()
	
	/**
	 * Set the JScrollPanes' sizes.
	 */
	void postConstruct() {
		
		scroll_left.setPreferredSize(new Dimension((int) leftPanel.getPreferredSize().getWidth(),
				(int) getPreferredSize().getHeight() - (int) topPanel.getPreferredSize().getHeight()));
		scroll_right.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(),
				(int) getPreferredSize().getHeight() - (int) topPanel.getPreferredSize().getHeight()));
		
		redoPanels();
		
		setVisible(false);
		setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
		setVisible(true);
		
	}// ends postConstruct()
	
	/**
	 * Sets up the left and right panels to show the correct information.
	 */
	void redoPanels() {
		
		leftPanel.removeAll();
		rightPanel.removeAll();
		
		SplitPlayerPanel panel = null;
		
		for(int i=0; i<panels.length; i++) {
			
			NewPlayerPanel newPanel = (NewPlayerPanel) panels[i];
			
			panel = new SplitPlayerPanel(newPanel, this, groupBox, SplitPlayerPanel.LEFT);
			if(i != 0) {
				panel.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, Color.RED));
			}
			panel.setMaximumSize(new Dimension((int) panel.getMaximumSize().getWidth(), (int) panel.getPreferredSize().getHeight()));
			
			leftPanel.add(panel);

		}
		
		for(int i=0; i<panels.length; i++) {
			
			NewPlayerPanel newPanel = (NewPlayerPanel) panels[i];
			
			if(newPanel.getSplitGroup() == groupBox.getSelectedIndex()) {

				panel = new SplitPlayerPanel(newPanel, this, groupBox, SplitPlayerPanel.RIGHT);
				if(i != 0) {
					panel.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, Color.RED));
				}
				panel.setMaximumSize(new Dimension((int) panel.getMaximumSize().getWidth(), (int) panel.getPreferredSize().getHeight()));
				
				rightPanel.add(panel);
				
			}
			
		}
		
		validate();
		
		// Set the border for the bottom panels if needed.
		if(leftPanel.getComponentCount() != 0) {
			panel = (SplitPlayerPanel) leftPanel.getComponent(leftPanel.getComponentCount()-1);
			if(panel.getY() + panel.getHeight() < scroll_left.getHeight()) {
				panel.setBorder(BorderFactory.createMatteBorder(4, 0, 4, 0, Color.RED));
			}
		}
		
		if(rightPanel.getComponentCount() != 0) {
			panel = (SplitPlayerPanel) rightPanel.getComponent(rightPanel.getComponentCount()-1);
			if(panel.getY() + panel.getHeight() < scroll_left.getHeight()) {
				panel.setBorder(BorderFactory.createMatteBorder(4, 0, 4, 0, Color.RED));
			}
		}
		
		validate();
		repaint();
		
	}// ends redoPanels

}// ends Class
