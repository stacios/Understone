package Model;

import Controller.InputData;
import Model.Weapon.Weapon;

/**
 * The player of the game. There are 4 types of dwarves: Driller, Engineer, Gunner, and Scout.
 * Each dwarf has a different set of weapons.
 */
public class Dwarf extends Character {

    private transient InputData myInputData;
    private Weapon[] myWeapons;

    private int myWeaponIndex = 0;

    private int myDashClock;
    private Angle myDashAngle;
    private final double myDashSpeed;
    private final int myDashTime;
    private final int myDashCooldown;

    public Dwarf(String theName, double theX, double theY, int theHealth, int theWidth, int theHeight, double theMoveSpeed,
                 double theDashSpeed, int theDashTime, int theDashCooldown, Weapon[] theWeapons) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapons[0]);
        myWeapons = theWeapons;
        myDashSpeed = theDashSpeed;
        myDashTime = theDashTime;
        myDashCooldown = theDashCooldown;
        myDashClock = -myDashCooldown;
    }

    public void switchWeapon(int theIndex) {
        if (theIndex >= 0 && theIndex < myWeapons.length) {
            setWeapon(myWeapons[theIndex]);
            myWeaponIndex = theIndex;
        }
    }

    public int getWeaponIndex() {
        return myWeaponIndex;
    }

    public void setInputData(final InputData theInputData) {
        myInputData = theInputData;
    }

    @Override
    public boolean update() {
        boolean superCall = super.update();

        move();
        dash();
        if (myInputData.getM1())
            attemptAttack(myInputData.getMouseX(), myInputData.getMouseY());
        if (myInputData.getWeapon1())
            switchWeapon(0);
        if (myInputData.getWeapon2())
            switchWeapon(1);
        if (myInputData.getWeapon3())
            switchWeapon(2);

        return superCall;

    }

    private void move() {

        //get the number of movement keys pressed
        boolean[] inputs = new boolean[]{myInputData.getDown(), myInputData.getUp(),
                myInputData.getRight(), myInputData.getLeft()};
        int count = 0;
        for (boolean b : inputs) {
            count += b ? 1 : 0;
        }

        Angle angle = null;
        // case where all keys are pressed
        if (myInputData.getUp() && myInputData.getDown() && myInputData.getRight() && myInputData.getLeft())
            angle = null;

            // cases where 1 or 3 keys are pressed
        else if (myInputData.getDown() && myInputData.getRight() == myInputData.getLeft())
            angle = new Angle(Math.PI / 2);

        else if (myInputData.getUp() && myInputData.getRight() == myInputData.getLeft())
            angle = new Angle(3 * Math.PI / 2);

        else if (myInputData.getRight() && myInputData.getDown() == myInputData.getUp())
            angle = new Angle(0);

        else if (myInputData.getLeft() && myInputData.getDown() == myInputData.getUp())
            angle = new Angle(Math.PI);

            // cases where 2 keys are pressed
        else if (myInputData.getDown() && !myInputData.getUp()) {
            if (myInputData.getRight()) angle = new Angle(Math.PI / 4);
            else angle = new Angle(3 * Math.PI / 4);
        } else if (myInputData.getUp() && !myInputData.getDown()) {
            if (myInputData.getRight()) angle = new Angle(7 * Math.PI / 4);
            else angle = new Angle(5 * Math.PI / 4);
        }


        if (angle != null) {
            addForce(new Force(angle, getMoveSpeed(), .4));
        }
        if (myDashClock < 0) {
            myDashAngle = angle;
        }

    }

    private void dash() {
        if (myDashClock != -myDashCooldown) {
            myDashClock--;
        }
        if (myDashClock == -myDashCooldown && myDashAngle != null && myInputData.getDash()) {
            myDashClock = myDashTime;
        }
        if (myDashClock >= 0 && myDashAngle != null) {
            addForce(new Force(myDashAngle, myDashSpeed));
        }
    }


    /**
     * Todo Dwarf toString.
     *
     * @return String representation of Dwarf.
     */
    @Override
    public String toString() {
        return super.toString() + ", Weapons: " + myWeapons;
    }
}
