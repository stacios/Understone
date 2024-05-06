package Controller;

public class DrawData {

    private final int myX;
    private final int myY;
    private final double myAngle;
    private final int myWidth;
    private final int myHeight;
    private final String myImage;
    private final String[] mySounds;

    public DrawData(final String theImage, final String[] theSounds,
                    final double theX, final double theY, final int theWidth, final int theHeight, final double theAngle) {
        myX = (int)theX;
        myY = (int)theY;
        myWidth = theWidth;
        myHeight = theHeight;
        myAngle = theAngle;
        myImage = theImage;
        mySounds = theSounds;
    }

    public DrawData(final String theImage, final String[] theSounds,
                    final double theX, final double theY, final int theWidth, final int theHeight) {
        this(theImage, theSounds, theX, theY, theWidth, theHeight, 0);
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }

    public double getAngle() {
        return myAngle;
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }

    public String getImage() {
        return myImage;
    }

    public String[] getSounds() {
        return mySounds;
    }
    @Override
    public String toString() {
        return myImage + ": ( Pos: " + myX + ", " + myY + " | Dim: " + myWidth + ", " + myHeight + " )";
    }
}
