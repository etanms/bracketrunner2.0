package com.etan.widgets;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class SizableJCheckBox extends JCheckBox {
	
	public SizableJCheckBox(int size) {
		
		super();
		
		setIcon(new SizableIcon(size));
		
	}// ends SizableJCheckBox(int size)

}// ends Class
