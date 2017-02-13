package edu.jsu.mcis;

public class ShapeEvent {
    private boolean hexagon;
    public ShapeEvent() {
        this(false);
    }
    public ShapeEvent(boolean hexagon) {
        this.hexagon = hexagon;
    }
    public boolean isOctagonSelected() { return hexagon; }
}