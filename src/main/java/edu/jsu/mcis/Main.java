package edu.jsu.mcis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel implements ShapeObserver {
    private CustomWidget widget1;
	private CustomWidget widget2;
    private JLabel label;

    public Main() {
        widget1 = new CustomWidget();
		//widget2 = new CustomWidget();
        widget1.addShapeObserver(this);
		//widget2.addShapeObserver(this);
        label = new JLabel("Hexagon", JLabel.CENTER);
        label.setName("label");
        setLayout(new BorderLayout());
        add(widget1, BorderLayout.CENTER);
		//add(widget2, BorderLayout.EAST);
        add(label, BorderLayout.NORTH);
    }
    
    public void shapeChanged(ShapeEvent event) {
        if(event.isOctagonSelected()) { label.setText("Hexagon"); }
        else { label.setText("Octagon"); }
    }


	public static void main(String[] args) {
		JFrame window = new JFrame();
        window.setTitle("Main");
        window.add(new Main());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 500);
        window.setVisible(true);
	}
}