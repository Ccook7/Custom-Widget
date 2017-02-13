package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CustomWidget extends JPanel implements MouseListener {
    private java.util.List<ShapeObserver> observers;
    
    
    private final Color HEXAGON_COLOR = Color.green;
    private final Color DEFAULT_COLOR = Color.white;
	private final Color OCTAGON_COLOR = Color.red;
    private boolean octagonSelected;
    private Point[] hexagon;
	private Point[] octagon;

    
    public CustomWidget() {
        observers = new ArrayList<>();
        
        octagonSelected = false;
		hexagon = new Point[6];
		for(int i = 0; i < hexagon.length; i++) { hexagon[i] = new Point(); }
        octagon = new Point[8];
        for(int i = 0; i < octagon.length; i++) { octagon[i] = new Point(); }
        Dimension dim = getPreferredSize();
        calculateVertices(dim.width, dim.height);
        setBorder(BorderFactory.createLineBorder(Color.black));
        addMouseListener(this);
    }

    
    public void addShapeObserver(ShapeObserver observer) {
        if(!observers.contains(observer)) observers.add(observer);
    }
    public void removeShapeObserver(ShapeObserver observer) {
        observers.remove(observer);
    }
    private void notifyObservers() {
        ShapeEvent event = new ShapeEvent(octagonSelected);
        for(ShapeObserver obs : observers) {
            obs.shapeChanged(event);
        }
    }
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    private void calculateVertices(int width, int height) {
        // Square size should be half of the smallest dimension (width or height).
        int side = Math.min(width, height) / 4;
        for(int i = 0; i < hexagon.length; i++) {
            double rads = 0 + (i * (Math.PI / (hexagon.length / 2)));
            double x = Math.cos(rads);
            double y = Math.sin(rads);
            hexagon[i].setLocation(width/3 + (x * (side/4)), height/2 + (y * side/4));
            
        }
        
        for(int i = 0; i < octagon.length; i++) {
            double rads = Math.PI * 0.125 + (i * (Math.PI / (octagon.length / 2)));
            double x = Math.cos(rads);
            double y = Math.sin(rads);
            octagon[i].setLocation(width - (width/3) + (x * (side/4)), height/2 + (y * side/4));
        
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        calculateVertices(getWidth(), getHeight());
        Shape[] shapes = getShapes();
        g2d.setColor(Color.black);
        g2d.draw(shapes[0]);
        g2d.draw(shapes[1]);
        
        if(!octagonSelected) {
			g2d.setColor(HEXAGON_COLOR);
            g2d.fill(shapes[0]);
            g2d.setColor(DEFAULT_COLOR);
            g2d.fill(shapes[1]);
        }
        else {         
			g2d.setColor(DEFAULT_COLOR);
            g2d.fill(shapes[0]); 
            g2d.setColor(OCTAGON_COLOR);
            g2d.fill(shapes[1]);
        }
	}

    public void mouseClicked(MouseEvent event) {
        Shape[] shapes = getShapes();
        if(shapes[0].contains(event.getX(), event.getY())) {
            octagonSelected = true;
            notifyObservers();
        }
        if(shapes[1].contains(event.getX(), event.getY())) {
            octagonSelected = false;
            notifyObservers();
        }
        
        repaint(shapes[0].getBounds());
        repaint(shapes[1].getBounds());
    }
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    
    public Shape[] getShapes() {
        
		Shape[] shapes = new Shape[2];
		int[] x = new int[hexagon.length];
        int[] y = new int[hexagon.length];
		
        for(int i = 0; i < hexagon.length; i++)
       {
            x[i] = hexagon[i].x;
            y[i] = hexagon[i].y;
        }
        shapes[0] = new Polygon(x, y, hexagon.length);
        
        x = new int[octagon.length];
        y = new int[octagon.length];
        for(int i = 0; i < octagon.length; i++) 
        {
            x[i] = octagon[i].x;
            y[i] = octagon[i].y;
        }
        shapes[1] = new Polygon(x, y, octagon.length);
		
        return shapes;
    }
    public boolean isOctagonSelected() { return octagonSelected; }



	public static void main(String[] args) {
		JFrame window = new JFrame("Custom Widget");
        window.add(new CustomWidget());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 300);
        window.setVisible(true);
	}
}
