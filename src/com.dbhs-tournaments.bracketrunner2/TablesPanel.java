package com.etan.bracketrunner2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.etan.widgets.SizableJCheckBox;

/**
 * Panel used to display data about tables.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 *
 */
@SuppressWarnings("serial")
class TablesPanel extends JPanel implements ActionListener {
	
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private JScrollPane scroll_left;
	private JScrollPane scroll_right;
	
	private JCheckBox selectBox_left;
	private JLabel selectBox_leftLabel;
	private JCheckBox selectBox_right;
	private JLabel selectBox_rightLabel;
	
	private JButton addButton;
	private JButton removeButton;
	private JButton newButton;
	private JButton deleteButton;
	
	/**
	 * Constructor
	 */
	public TablesPanel(){
		
		Font font = new Font("Arial Black", Font.PLAIN, 18);
		
		JPanel topLeftPanel = new JPanel();
		topLeftPanel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 2, Color.RED));
		topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.Y_AXIS));
		
		JLabel leftLabel = new JLabel("Tournament Tables");
		leftLabel.setForeground(Color.BLACK);
		leftLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(leftLabel);
		topLeftPanel.add(panel);
		
		removeButton = new JButton("Remove");
		removeButton.setForeground(Color.BLACK);
		removeButton.setFont(font);
		removeButton.addActionListener(this);
		selectBox_left = new SizableJCheckBox(18);
		selectBox_left.setForeground(Color.BLACK);
		selectBox_left.setFont(font);
		selectBox_left.setOpaque(false);
		selectBox_left.addActionListener(this);
		selectBox_leftLabel = new JLabel("Select All");
		selectBox_leftLabel.setForeground(Color.BLACK);
		selectBox_leftLabel.setFont(font);
		JPanel selectPanel = new JPanel();
		selectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		selectPanel.setOpaque(false);
		selectPanel.add(selectBox_left);
		selectPanel.add(selectBox_leftLabel);
		panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.add(removeButton);
		panel.add(selectPanel);
		topLeftPanel.add(panel);
		
		JPanel topRightPanel = new JPanel();
		topRightPanel.setBorder(BorderFactory.createMatteBorder(4, 2, 4, 4, Color.RED));
		topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS));
		
		JLabel rightLabel = new JLabel("Non-Tournament Tables");
		rightLabel.setForeground(Color.BLACK);
		rightLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
		panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.add(rightLabel);
		topRightPanel.add(panel);
		
		newButton = new JButton("New");
		newButton.setForeground(Color.BLACK);
		newButton.setFont(font);
		newButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.setForeground(Color.BLACK);
		deleteButton.setFont(font);
		deleteButton.addActionListener(this);
		addButton = new JButton("Add");
		addButton.setForeground(Color.BLACK);
		addButton.setFont(font);
		addButton.addActionListener(this);
		selectBox_right = new SizableJCheckBox(18);
		selectBox_right.setForeground(Color.BLACK);
		selectBox_right.setFont(font);
		selectBox_right.setOpaque(false);
		selectBox_right.addActionListener(this);
		selectBox_rightLabel = new JLabel("Select All");
		selectBox_rightLabel.setForeground(Color.BLACK);
		selectBox_rightLabel.setFont(font);
		selectPanel = new JPanel();
		selectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		selectPanel.setOpaque(false);
		selectPanel.add(selectBox_right);
		selectPanel.add(selectBox_rightLabel);
		panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.add(newButton);
		panel.add(deleteButton);
		panel.add(addButton);
		panel.add(selectPanel);
		topRightPanel.add(panel);
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,2));
		topPanel.add(topLeftPanel);
		topPanel.add(topRightPanel);
		
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
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		bottomPanel.setBorder(BorderFactory.createMatteBorder(0, 4, 4, 4, Color.RED));
		bottomPanel.add(scroll_left);
		bottomPanel.add(scroll_right);
		
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.CENTER);
		
	}//ends TablesPanel()

	/**
	 * Handle events from buttons on this screen.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getSource().equals(addButton)){
			for(int i=0; i<rightPanel.getComponentCount(); i++) {
				TablePanel tp = (TablePanel)rightPanel.getComponent(i);
				if(tp.isSelected() && tp.isCreated()){
					tp.setDesignated(true);
					tp.setSelected(selectBox_left.isSelected());
					leftPanel.add(tp);
					i--;
				}
			}
			for(int i=0; i<rightPanel.getComponentCount(); i++) {
				((TablePanel) rightPanel.getComponent(i)).setSelected(false);
			}
			selectBox_right.setSelected(false);
		} else if(evt.getSource().equals(removeButton)){
			for(int i=0; i<leftPanel.getComponentCount(); i++) {
				TablePanel tp = (TablePanel)leftPanel.getComponent(i);
				if(tp.isSelected()){
					if(tp.getMatch() != null){
						Main.getControlPanel().showErrorMessage(tp.getTableName() + " cannot be removed because it is currently being used for a match.");
					}
					else{
						tp.setDesignated(false);
						tp.setSelected(selectBox_right.isSelected());
						rightPanel.add(tp);
						i--;
					}
				}
			}
			for(int i=0; i<leftPanel.getComponentCount(); i++) {
				((TablePanel) leftPanel.getComponent(i)).setSelected(false);
			}
			selectBox_left.setSelected(false);
		} else if(evt.getSource().equals(newButton)){
			TablePanel tp = new TablePanel();
			tp.setPreferredSize(new Dimension((int) rightPanel.getSize().getWidth() - rightPanel.getInsets().left - rightPanel.getInsets().right, (int) tp.getPreferredSize().getHeight()));
			tp.setMaximumSize(new Dimension((int) rightPanel.getSize().getWidth() - rightPanel.getInsets().left - rightPanel.getInsets().right, (int) tp.getPreferredSize().getHeight()));
			tp.setSelected(selectBox_right.isSelected());
			rightPanel.add(tp);
		} else if(evt.getSource().equals(deleteButton)){
			for(int i=0; i<rightPanel.getComponentCount(); i++){
				TablePanel tp = (TablePanel)rightPanel.getComponent(i);
				if(tp.isSelected() && tp.isCreated()){
					if(tp.getMatch() != null){
						Main.getControlPanel().showErrorMessage(tp.getTableName() + " cannot be deleted because it is currently being used for a match.");
					}
					else{
						leftPanel.remove(tp);
						rightPanel.remove(tp);
						i--;
					}
				}
			}
			for(int i=0; i<rightPanel.getComponentCount(); i++) {
				((TablePanel) rightPanel.getComponent(i)).setSelected(false);
			}
			selectBox_right.setSelected(false);
		} else if(evt.getSource().equals(selectBox_left)) {
			for(int i=0; i<leftPanel.getComponentCount(); i++) {
				((TablePanel) leftPanel.getComponent(i)).setSelected(selectBox_left.isSelected());
			}
		} else if(evt.getSource().equals(selectBox_right)) {
			for(int i=0; i<rightPanel.getComponentCount(); i++) {
				((TablePanel) rightPanel.getComponent(i)).setSelected(selectBox_right.isSelected());
			}
		}
		
		Main.getControlPanel().updateInfo();
		
	}// ends actionPerformed()
	
	/**
	 * Creates tables based on the location and information
	 * about the tables at that location stored in the database.
	 * 
	 * @param names String[] fill with names of the tables.
	 */
	void addTables(String[] names) {
		
		leftPanel.removeAll();
		rightPanel.removeAll();
		
		if(names != null) {
			for(int i=0; i<names.length; i++) {
				TablePanel tp = new TablePanel();
				JScrollPane pane = Main.getControlPanel().getScroller();
				tp.setPreferredSize(new Dimension((int) pane.getWidth()/2, (int) tp.getPreferredSize().getHeight()));
				tp.setMaximumSize(new Dimension((int) Main.getWindow().getWidth()/2, (int) tp.getPreferredSize().getHeight()));
				tp.create(names[i]);
				tp.setSelected(selectBox_right.isSelected());
				rightPanel.add(tp);
			}
		}
		
		updateInfo();
		
	}// ends addTables()
	
	/**
	 * @return Array of all TablePanels referencing a tournament usable table.
	 */
	TablePanel[] getTables() {
		
		TablePanel[] tables = new TablePanel[leftPanel.getComponentCount()];
		for(int i=0; i<leftPanel.getComponentCount(); i++) {
			tables[i] = (TablePanel) leftPanel.getComponent(i);
		}
		
		return tables;
		
	}// ends getTables()
	
	/**
	 * Set the JScrollPanes' sizes.
	 */
	void postConstruct() {
		
		scroll_left.setPreferredSize(new Dimension((int) leftPanel.getPreferredSize().getWidth(), (int) Main.getControlPanel().getScreen().getPreferredSize().getHeight() - (int) topPanel.getPreferredSize().getHeight()));
		scroll_right.setPreferredSize(new Dimension((int) rightPanel.getPreferredSize().getWidth(), (int) Main.getControlPanel().getScreen().getPreferredSize().getHeight() - (int) topPanel.getPreferredSize().getHeight()));
		
	}// ends postConstruct()
	
	/**
	 * Makes sure that the screen is showing current information.
	 */
	void updateInfo() {
		
		validate();
		
		// Set the borders for tablePanels in the leftPanel.
		for(int i=0; i<leftPanel.getComponentCount()-1; i++) {
			((JPanel) leftPanel.getComponent(i)).setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		}
		if(leftPanel.getComponentCount() != 0) {
			JPanel panel = (JPanel) leftPanel.getComponent(leftPanel.getComponentCount()-1);
			if(panel.getY() + panel.getHeight() < scroll_left.getHeight()) {
				panel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
			} else {
				panel.setBorder(null);
			}
		}
		
		// Set the borders for tablePanels in the rightPanel.
		for(int i=0; i<rightPanel.getComponentCount()-1; i++) {
			((JPanel) rightPanel.getComponent(i)).setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		}
		if(rightPanel.getComponentCount() != 0) {
			JPanel panel = (JPanel) rightPanel.getComponent(rightPanel.getComponentCount()-1);
			if(panel.getY() + panel.getHeight() < scroll_left.getHeight()) {
				panel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
			} else {
				panel.setBorder(null);
			}
		}
		
		for(int i=0; i<leftPanel.getComponentCount(); i++) {
			((TablePanel) leftPanel.getComponent(i)).updateInfo();
		}
		for(int i=0; i<rightPanel.getComponentCount(); i++) {
			((TablePanel) rightPanel.getComponent(i)).updateInfo();
		}
		
		validate();
		repaint();
		
	}// ends updateInfo()

}// ends Class
