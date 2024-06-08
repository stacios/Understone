package Model;

import Controller.InputData;
import Model.Weapon.Weapon;

import java.util.List;

/**
 * The player of the game. There are 4 types of dwarves: Driller, Engineer, Gunner, and Scout.
 * Each dwarf has a different set of weapons.
 */
public class Dwarf extends Character {

    /**
     * The input data for controlling the Dwarf.
     */
    private transient InputData myInputData;

    /**
     * The array of weapons the Dwarf can use.
     */
    private Weapon[] myWeapons;

    /**
     * The index of the currently equipped weapon.
     */
    private int myWeaponIndex = 0;

    /**
     * Indicates whether the Dwarf has picked up the egg.
     */
    private boolean myHasEgg;

    /**
     * The clock for managing the dash cooldown and duration.
     */
    private int myDashClock;

    /**
     * The angle at which the Dwarf dashes.
     */
    private Angle myDashAngle;

    /**
     * The speed at which the Dwarf dashes.
     */
    private final double myDashSpeed;

    /**
     * The duration of the Dwarf's dash.
     */
    private final int myDashTime;

    /**
     * The cooldown time for the Dwarf's dash.
     */
    private final int myDashCooldown;

    /**
     * Constructs a Dwarf with the specified parameters.
     *
     * @param theName the name of the Dwarf
     * @param theX the initial x-coordinate of the Dwarf
     * @param theY the initial y-coordinate of the Dwarf
     * @param theHealth the health points of the Dwarf
     * @param theWidth the width of the Dwarf
     * @param theHeight the height of the Dwarf
     * @param theMoveSpeed the movement speed of the Dwarf
     * @param theDashSpeed the speed of the Dwarf's dash
     * @param theDashTime the duration of the Dwarf's dash
     * @param theDashCooldown the cooldown time for the Dwarf's dash
     * @param theWeapons the array of weapons the Dwarf can use
     */
    public Dwarf(final String theName, final double theX, final double theY, final int theHealth, final int theWidth,
                 final int theHeight, final double theMoveSpeed, final double theDashSpeed, final int theDashTime,
                 final int theDashCooldown, final Weapon[] theWeapons) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapons[0]);
        myWeapons = theWeapons;
        myDashSpeed = theDashSpeed;
        myDashTime = theDashTime;
        myDashCooldown = theDashCooldown;
        myDashClock = -myDashCooldown;
        myHasEgg = false;
    }

    /**
     * Switches the current weapon to the specified index.
     *
     * @param theIndex the index of the weapon to switch to
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void switchWeapon(final int theIndex) {
        if (theIndex >= 0 && theIndex < myWeapons.length) {
            setWeapon(myWeapons[theIndex]);
            myWeaponIndex = theIndex;
        }
        else {
            throw new IndexOutOfBoundsException("Cannot switch to weapon " + theIndex);
        }
    }

    /**
     * Gets the index of the currently equipped weapon.
     *
     * @return the index of the currently equipped weapon
     */
    public int getWeaponIndex() {
        return myWeaponIndex;
    }

    /**
     * Sets whether Dwarf picked up the egg.
     * @param theHasEgg is the passed boolean conditional if Dwarf picked up.
     */
    public void setEgg(final boolean theHasEgg) {
        myHasEgg = theHasEgg;
    }

    /**
     * Gets whether Dwarf has picked up Egg.
     * @return if Dwarf has picked up Egg.
     */
    public boolean hasEgg() {
        return myHasEgg;
    }

    /**
     * Sets the input data for controlling the Dwarf.
     *
     * @param theInputData the input data to set
     */
    public void setInputData(final InputData theInputData) {
        myInputData = theInputData;
    }

    /**
     * Updates the state of the Dwarf, including movement, dashing, and weapon switching.
     *
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean update() {
        boolean superCall = super.update();
        for (Weapon weapon : myWeapons) {
            if (weapon != super.getWeapon()) {
                weapon.update();
            }
        }

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

    /**
     * Handles the movement of the Dwarf based on input data.
     */
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

    /**
     * Handles the dashing movement of the Dwarf based on input data.
     */
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
     * Gets the draw data for the Dwarf, including sound effects for dashing.
     *
     * @return an array of strings representing the draw data
     */
    @Override
    public String[] getDrawData() {
        if (myDashClock == myDashTime) {
            List<String> temp = new java.util.ArrayList<>(List.of(super.getDrawData()));
            temp.add("sound:Dash");
            return temp.toArray(new String[0]);
        }
        else {
            return super.getDrawData();
        }
    }

    /**
     * Provides a string representation of the Dwarf.
     *
     * @return a string representation of the Dwarf
     */
    @Override
    public String toString() {
        return super.toString() + ", Weapons: " + myWeapons;
    }
}
