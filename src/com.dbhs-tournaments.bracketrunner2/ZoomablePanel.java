package com.etan.bracketrunner2;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.etan.bracketrunner2.Main;

@SuppressWarnings("serial")
public class ZoomablePanel extends JPanel implements ChangeListener, ComponentListener{
	
	protected int scale; // The current scale
	protected Dimension iSize; // The original size
	protected Point newMiddle; // The point that should be at the center of the viewport after a change
	
	/**
	 * Constructor
	 */
	public ZoomablePanel(){
		
		scale = 1;
		Main.getWindow().addComponentListener(this);
		addComponentListener(this);
		
	}//ends constructor

	@Override
	public void componentHidden(ComponentEvent evt){
		
	}//ends componentHidden()

	@Override
	public void componentMoved(ComponentEvent evt){
		
	}//ends componentMoved()

	/**
	 * Handle event when zoom slider is changed.
	 */
	@Override
	public void componentResized(ComponentEvent evt){
		
		if(Main.getControlPanel().hasStarted()) {
			if(evt.getSource().equals(Main.getWindow())){
				iSize = new Dimension((int)(iSize.width/(double)iSize.height*Main.getControlPanel().getScroller().getVisibleRect().getHeight()), (int)Main.getControlPanel().getScroller().getVisibleRect().getHeight());
				setScale(scale);
			}
			else if(evt.getSource().equals(this)){
				try{
					JViewport vp = Main.getControlPanel().getScroller().getViewport();
					Rectangle rect = vp.getViewRect();
					rect.x = newMiddle.x - vp.getWidth()/2;
					rect.y = newMiddle.y - vp.getHeight()/2;
					vp.setViewPosition(new Point(rect.x, rect.y));
				}
				catch(NullPointerException e){
					;
				}
			}
		}
		
	}//ends componentResized()

	@Override
	public void componentShown(ComponentEvent evt){
		
	}//ends componentShown()
	
	/**
	 * @param scale How much to zoom in or out.
	 */
	void setScale(int scale){
		
		int iScale = this.scale;
		this.scale = scale;
		JViewport vp = Main.getControlPanel().getScroller().getViewport();
		Rectangle rect = vp.getViewRect();
		Point middle = new Point(rect.x+rect.width/2, rect.y+rect.height/2);
		
		setPreferredSize(transform(iSize, 1));
		revalidate();
		
		newMiddle = transform(middle, iScale);
		
	}//ends setScale()

	/**
	 * Handles ChangeEvents so that this panel will zoom in and out.
	 * Must be controlled by a JSlider.
	 */
	@Override
	public void stateChanged(ChangeEvent evt){
		
		setScale(((JSlider)evt.getSource()).getValue());
		
	}//ends stateChanged()
	
	/**
	 * 
	 * @param p The point to be found after being transformed.
	 * @param iScale The scale before being transformed.
	 * @return The point on the screen after it has been scaled
	 * that is equivalent to point p on the original screen.
	 */
	private Point transform(Point p, double iScale){

		//return new Point((int)(p.getX()*((scale-1)/(double)4+1)/((iScale-1)/(double)4+1)), (int)(p.getY()*((scale-1)/(double)4+1)/((iScale-1)/(double)4+1)));
		Point point =  new Point((int)(p.getX()*scale/iScale), (int)(p.getY()*scale/iScale));
		
		// fix resizing when points are too close to the edge
		if(point.x < Main.getControlPanel().getScroller().getWidth()/2) {
			point.setLocation(Main.getControlPanel().getScroller().getWidth()/2, point.getY());
		}
		if(point.y < Main.getControlPanel().getScroller().getHeight()/2) {
			point.setLocation(point.getX(), Main.getControlPanel().getScroller().getHeight()/2);
		}
				
		return point;
		
	}//ends transform(Point)
	
	/**
	 * @param d The size of the original screen that is being transformed.
	 * @param iScale The scale before being transformed.
	 * @return Dimension of the new screen.
	 */
	Dimension transform(Dimension d, double iScale){
		
		int endLength = 0;
		if(Main.getControlPanel().getScreen().getBracket() != null) {
			endLength = d.height/Main.getControlPanel().getScreen().getBracket().getFullBracket();
		}
		
		Dimension dim = new Dimension((int) (d.width + (d.width * (double) d.width / (endLength * 5) - d.width) * (double) scale / 10 * Main.getControlPanel().getScroller().getViewport().getWidth()/d.width), 
				(int) (d.height + (d.height * (double) d.width / (endLength * 5) - d.height) * (double) scale / 10 * Main.getControlPanel().getScroller().getViewport().getWidth()/d.width));
		if(Main.getControlPanel().getScreen().getBracket() != null) {
			endLength = dim.height/Main.getControlPanel().getScreen().getBracket().getFullBracket();
		}
		
		if(Main.getControlPanel().getLoserRounds() == 0) {
			while(dim.getWidth() < endLength * (6 * Main.getControlPanel().getRounds() - 1)) {
				dim = new Dimension((int) (dim.getWidth() + dim.height/Main.getControlPanel().getScreen().getBracket().getFullBracket()), (int) (dim.getHeight()));
			}
		} else {
			while(dim.getWidth() < endLength * (6 * (Main.getControlPanel().getRounds() + Main.getControlPanel().getLoserRounds()) - 1)) {
				dim = new Dimension((int) (dim.getWidth() + dim.height/Main.getControlPanel().getScreen().getBracket().getFullBracket()), (int) (dim.getHeight()));
			}
		}
		
		return dim;
		
	}//ends transform(Dimension)

}//ends class
