package Model;

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

    public double getWidth() {
        return myWidth;
    }

    public double getHeight() {
        return myHeight;
    }

    public void setPosition(double theX, double theY) {
        this.myX = theX;
        this.myY = theY;
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
