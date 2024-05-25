package Model.Weapon;

import Model.Angle;

public class ShotgunWeapon extends Weapon {

    private final int myPelletCount;
    private final Angle mySpread;
    public ShotgunWeapon(int theMaxCooldown, Attack theAttack, String theSound, int thePelletCount, Angle theSpread) {
        super(theMaxCooldown, theAttack, theSound);
        myPelletCount = thePelletCount;
        mySpread = theSpread;
    }

    @Override
    public Attack[] getPendingAttacks() {
        Attack[] arr = super.getPendingAttacks();
        Attack[] result = new Attack[arr.length * myPelletCount];

        Attack attack;
        Angle angle;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < myPelletCount; j++) {
                attack = arr[i].clone();
                angle = new Angle(arr[i].getAngle().getRadians()
                        - mySpread.getRadians()/2 + j * (mySpread.getRadians() / (myPelletCount - 1)));
                attack.setAngle(angle);
                result[i * myPelletCount + j] = attack;
            }
        }
        return result;
    }
}
