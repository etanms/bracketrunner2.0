package com.etan.bracketrunner2;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

public class BracketRunnerWindowListener implements WindowListener {

	@Override
	public void windowActivated(WindowEvent e){
		
	}

	@Override
	public void windowClosed(WindowEvent e){
		
	}

	@Override
	public void windowClosing(WindowEvent e){
		
		int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to exit the application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == 0) {
           System.exit(0);
        }
        
	}//ends windowClosing()

	@Override
	public void windowDeactivated(WindowEvent e){
		
	}

	@Override
	public void windowDeiconified(WindowEvent e){
		
	}

	@Override
	public void windowIconified(WindowEvent e){
		
	}

	@Override
	public void windowOpened(WindowEvent e){
		
	}
	
}// ends Class
