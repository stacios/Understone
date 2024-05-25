package Model.Glyphid;

import Model.Character;
import Model.Weapon.Weapon;

public abstract class Glyphid extends Character {
    public Glyphid(String theName, double theX, double theY, int theHealth,
                   int theWidth, int theHeight, double theMoveSpeed,
                   Weapon theWeapon) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon);
    }



}

