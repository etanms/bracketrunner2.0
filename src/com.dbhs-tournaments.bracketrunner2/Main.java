package com.etan.bracketrunner2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * Driver class for BracketRunner 2.0
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 *
 */
public class Main {

	private static JFrame window;
	private static JPanel content;
	private static JMenuBar menu;
	private static MenuBarActionListener actionListener;

	public static void main(String[] args) {
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		content = new JPanel();
		
		WindowListener listener = new BracketRunnerWindowListener();
		
		actionListener = new MenuBarActionListener();
	    
	    JMenu fileMenu = new JMenu("File");
	    
	    JMenuItem newItem = new JMenuItem("New");
	    newItem.addActionListener(actionListener);
	    fileMenu.add(newItem);
	    
	    JMenuItem openItem = new JMenuItem("Open File...");
	    openItem.addActionListener(actionListener);
	    fileMenu.add(openItem);
	    
	    JMenuItem saveItem = new JMenuItem("Save");
	    saveItem.setEnabled(false);
	    saveItem.addActionListener(actionListener);
	    fileMenu.add(saveItem);
	    
	    JMenuItem saveAsItem = new JMenuItem("Save As...");
	    saveAsItem.setEnabled(false);
	    saveAsItem.addActionListener(actionListener);
	    fileMenu.add(saveAsItem);
	    
	    JMenuItem exitItem = new JMenuItem("Exit");
	    exitItem.addActionListener(actionListener);
	    fileMenu.add(exitItem);
	    
	    JMenu settingsMenu = new JMenu("Settings");
	    
	    JMenuItem loadSettingsItem = new JMenuItem("Load Settings");
	    loadSettingsItem.setEnabled(false);
	    loadSettingsItem.addActionListener(actionListener);
	    settingsMenu.add(loadSettingsItem);
	    
	    JMenuItem saveSettingsItem = new JMenuItem("Save Settings");
	    saveSettingsItem.setEnabled(false);
	    saveSettingsItem.addActionListener(actionListener);
	    settingsMenu.add(saveSettingsItem);
	    
	    JMenu helpMenu = new JMenu("Help");
	    
	    JMenuItem aboutItem = new JMenuItem("About");
	    aboutItem.addActionListener(actionListener);
	    helpMenu.add(aboutItem);
	    
	    menu = new JMenuBar();
	    menu.add(fileMenu);
	    menu.add(settingsMenu);
	    menu.add(helpMenu);

		window = new JFrame("BracketRunner 2.0");
		window.addWindowListener(listener);
	    window.setJMenuBar(menu);
		window.setContentPane(content);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setVisible(true);
		
		content.setPreferredSize(new Dimension(
				d.width-window.getInsets().left-window.getInsets().right, 
				d.height-window.getInsets().top-window.getInsets().bottom));
		window.pack();
		
	} // end main()
	
	/**
	 * Sets the content pane to a new ControlPanel.
	 */
	public static void addControlPanel() {
		
		content = new ControlPanel();
		((ControlPanel) content).postConstruct();
		window.setContentPane(content);
		window.validate();
		window.repaint();
		
	}// ends addControlPanel()

	/**
	 * @return The ControlPanel through which all functionality is called.
	 */
	public static ControlPanel getControlPanel() {

		return (ControlPanel) content;

	}// ends getControlPanel()
	
	/**
	 * @return The JMenuBar associated with the Window.
	 */
	public static JMenuBar getMenuBar() {
		
		return menu;
		
	}// ends getMenuBar()
	
	/**
	 * @return The ActionListener associated with the JMenuBar.
	 */
	public static MenuBarActionListener getMenuBarActionListener() {
		
		return actionListener;
		
	}// ends getMenuBarActionListener()

	/**
	 * @return Window
	 */
	public static JFrame getWindow() {

		return window;

	}// ends getWindow()

} // end class Main
