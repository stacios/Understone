package Model;

import Controller.InputData;
import Model.Weapon.Weapon;

import java.util.ArrayList;

public class Dwarf extends Character {

    private InputData myInputData;
    private ArrayList<Weapon> myWeapons;

    public Dwarf(String theName, double theX, double theY, int theHealth, int theWidth, int theHeight, double theMoveSpeed, Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
        myWeapons = new ArrayList<>();
    }

    public void switchWeapon(int index) {
        if (index >= 0 && index < myWeapons.size()) {
            setWeapon(myWeapons.get(index));
        }
    }

    public void attack() {
        System.out.println("Dwarf performing a special attack!");
        // attack logic
    }

    public void setInputData(InputData theInputData) {
        myInputData = theInputData;
    }
    @Override
    public void update() {
        super.update();
        // specific logic for Dwarf?

        walk();

    }

    private void walk() {

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
            angle = new Angle(Math.PI/2);

        else if (myInputData.getUp() && myInputData.getRight() == myInputData.getLeft())
            angle = new Angle(3*Math.PI/2);

        else if (myInputData.getRight() && myInputData.getDown() == myInputData.getUp())
            angle = new Angle(0);

        else if (myInputData.getLeft() && myInputData.getDown() == myInputData.getUp())
            angle = new Angle(Math.PI);

        // cases where 2 keys are pressed
        else if (myInputData.getDown() && !myInputData.getUp()) {
            if (myInputData.getRight()) angle = new Angle(Math.PI/4);
            else angle = new Angle(3*Math.PI/4);
        }
        else if (myInputData.getUp() && !myInputData.getDown()) {
            if (myInputData.getRight()) angle = new Angle(7*Math.PI/4);
            else angle = new Angle(5*Math.PI/4);
        }


        if (angle != null) {
            addForce(new Force(angle, getMoveSpeed(), .4));
        }


    }
}
