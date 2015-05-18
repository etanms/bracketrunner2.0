package com.etan.widgets;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JLabel;

/**
 * This is the only class that I looked up online.
 * I did not write this. I simply copied and pasted.
 * I do not know who to give credit to for this class.
 */
@SuppressWarnings("serial")
public class UnderlinedLabel extends JLabel {

    public UnderlinedLabel() {
      this("");
    }

    public UnderlinedLabel(String text) {
      super(text);
    }

    public void paint(Graphics g) {
      Rectangle r;
      super.paint(g);
      r = getBounds();// Changed from g.getClipBounds() because the line wouldn't be drawn correctly when scrolling.
      g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent(), getFontMetrics(getFont()).stringWidth(getText()), r.height - getFontMetrics(getFont()).getDescent());
    }
    
  } // end Class
