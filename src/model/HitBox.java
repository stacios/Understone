package model;

public class HitBox {
    private double myX;
    private double myY;
    private double myWidth;
    private double myHeight;

    public HitBox(double theX, double theY, double theWidth, double theHeight) {
        this.myX = theX;
        this.myY = theY;
        this.myWidth = theWidth;
        this.myHeight = theHeight;
    }

    public void setPosition(double x, double y) {
        this.myX = x;
        this.myY = y;
    }

    //check if this hit box collides with another hit box
    public boolean colliding(HitBox other) {
        // Check for an overlap between the two hit boxes
        return this.myX < other.myX + other.myWidth &&
                this.myX + this.myWidth > other.myX &&
                this.myY < other.myY + other.myHeight &&
                this.myY + this.myHeight > other.myY;
    }
}
