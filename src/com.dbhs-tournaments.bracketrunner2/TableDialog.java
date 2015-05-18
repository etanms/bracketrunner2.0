package com.etan.bracketrunner2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Dialog used to display what tables are available and choose
 * one for the next match.
 * 
 * @author Etan Mizrahi-Shalom
 * @version 2.0 Aug 27 2014
 */
@SuppressWarnings("serial")
class TableDialog extends JDialog implements MouseListener{
	
	private JPanel centerPanel;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private int num;
	
	/**
	 * Constructor
	 */
	public TableDialog(){
		
		num = -1;
		
		Font font = new Font("Arial Black", Font.PLAIN, 13);
		
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		content.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		
		JLabel label = new JLabel("Choose a table for the match to use:");
		label.setForeground(Color.BLACK);
		label.setFont(font);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.RED));
		panel.setBackground(Color.CYAN);
		panel.add(label);
		content.add(panel, BorderLayout.NORTH);
		
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(300, 260));
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		for(int i=0; i<Main.getControlPanel().getTables().length; i++){
			TablePanel tp = ((TablePanel)Main.getControlPanel().getTables()[i]);
			if(tp.isDesignated() && tp.getMatch() == null){
				NamePanel np = new NamePanel(i, tp.getTableName());
				centerPanel.add(np);
			}
		}
		JScrollPane pane = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		content.add(pane, BorderLayout.CENTER);
		
		okButton = new JButton("OK");
		okButton.setForeground(Color.BLACK);
		okButton.setFont(font);
		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.BLACK);
		cancelButton.setFont(font);
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		panel.setBorder(BorderFactory.createMatteBorder(4, 0, 0, 0, Color.RED));
		panel.add(okButton);
		panel.add(cancelButton);
		content.add(panel, BorderLayout.SOUTH);
		
		setContentPane(content);
		setSize(new Dimension(350, 350));
		setLocation(Main.getWindow().getLocation().x + Main.getWindow().getWidth()/2 - getWidth()/2, Main.getWindow().getLocation().y + Main.getWindow().getHeight()/2 - getHeight()/2);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}//ends Constructor
	
	public class NamePanel extends JPanel{
		
		private int num;
		
		public NamePanel(int num, String str){
			
			this.num = num;
			
			setBackground(Color.LIGHT_GRAY);
			addMouseListener(TableDialog.this);
			setLayout(new FlowLayout(FlowLayout.CENTER));
			JLabel label = new JLabel(str);
			label.setForeground(Color.BLACK);
			label.setFont(new Font("Arial Black", Font.PLAIN, 13));
			add(label);
			
		}//ends constructor
		
		public int getNum(){
			
			return num;
			
		}//ends getNum()
		
	}//ends class NamePanel
	
	public int getNum(){
		
		return num;
		
	}//ends getNum()
	
	public void setNum(int num){
		
		this.num = num;
		
	}//ends setNum()

	@Override
	public void mouseClicked(MouseEvent evt){
		
		for(int i=0; i<centerPanel.getComponentCount(); i++){
			((NamePanel)centerPanel.getComponent(i)).setBackground(Color.LIGHT_GRAY);
		}
		((NamePanel)evt.getSource()).setBackground(Color.CYAN);
		setNum(((NamePanel)evt.getSource()).getNum());
		
	}//ends mouseClicked()

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	/**
	 * @param listener ActionListener to listen to this dialog.
	 */
	void setListener(ActionListener listener) {
		
		okButton.addActionListener(listener);
		cancelButton.addActionListener(listener);
		
	}// ends setListener()

}//ends Class
