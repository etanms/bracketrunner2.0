package com.etan.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.plaf.metal.MetalCheckBoxIcon;

@SuppressWarnings("serial")
class SizableIcon extends MetalCheckBoxIcon {
	
	int size;
	
	public SizableIcon(int size) {
		
		this.size = size;
		
	}// ends SizableIcon()
	
	@Override
	protected int getControlSize() {
		
		return size;
		
	}// ends getControlSize()
	
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		if (UIManager.get("CheckBox.gradient") != null) {
			// First half of gradient
			GradientPaint paint = new GradientPaint( 0, 1, Color.WHITE, 0, getIconHeight()*3/2, Color.LIGHT_GRAY);
		    g2d.setPaint(paint);
		    g2d.fillRect(((JCheckBox)c).getBorder().getBorderInsets(c).left, (c.getHeight()-getIconHeight())/2, getIconWidth(), getIconHeight());
		}
		new MetalBorders.ButtonBorder().paintBorder(c, g, x, y, getIconWidth(), getIconHeight());
		    
		AbstractButton b = (AbstractButton) c;
		 if (b.isSelected()) {
			 g.setColor(Color.DARK_GRAY);
			 drawCheck(b, g, x-getIconWidth()/4, y-(b.getHeight()-getIconHeight())/2);
		 }
		 
	}// ends paintIcon()
	
}// ends Class
