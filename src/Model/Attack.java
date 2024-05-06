package Model;

public class Attack {
    private String myName;
    private int myDamage;
    protected double myX;
    protected double myY;
    private HitBox myHitBox;
    private Force myKnockBack;
    protected Angle myAngle;

    public Attack(String theName, int theDamage, double theX, double theY,
                  double theWidth, double theHeight, Force theKnockBack, Angle theAngle) {
        this.myName = theName;
        this.myDamage = theDamage;
        this.myX = theX;
        this.myY = theY;
        this.myHitBox = new HitBox(theX, theY, theWidth, theHeight);
        this.myKnockBack = theKnockBack;
        this.myAngle = theAngle;
    }

    // Update later for game logic!!!
    public void update() {
        // move attack position by the knockback force's components
        //myX += myKnockBack.getXStrength();
        //myY += myKnockBack.getYStrength();
        // update position of HitBox
        myHitBox = new HitBox(myX, myY, myHitBox.getWidth(), myHitBox.getHeight());
    }

    // perform the attack
    public void execute(Character origin, Angle attackAngle) {
        this.myX = origin.getX() + Math.cos(attackAngle.getRadians()) * 10; // Example distance
        this.myY = origin.getY() + Math.sin(attackAngle.getRadians()) * 10;
        this.myAngle = attackAngle;

        this.myHitBox = new HitBox(myX, myY, myHitBox.getWidth(), myHitBox.getHeight());

        //check for collision with targets in range and apply damage and effects(?)
        System.out.println("Executing attack: " + myName + " at position (" + myX + ", " + myY + ")");
    }

    // Check if hit boxes intersect
    public boolean collide(Character other) {
        return myHitBox.colliding(other.getHitBox());
    }

    //
    public Angle getAngle() {
        return this.myAngle;
    }

    public HitBox getHitBox() {
        return this.myHitBox;
    }

    public int getDamage(){
        return this.myDamage;
    }

    public Force getKnockBack(){
        return this.myKnockBack;
    }

    public void setAngle(Angle newAngle) {
        this.myAngle = newAngle;
    }

    public double getX() {
        return this.myX;
    }

    public double getY() {
        return this.myY;
    }

    protected void setPosition(double x, double y) {
        this.myX = x;
        this.myY = y;
        this.getHitBox().setPosition(x, y);
    }
}


