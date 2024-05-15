package Model.Weapon;

public class MeleeAttack extends Attack {

    private int myStage;
    public MeleeAttack(int theDamage, int theWidth, int theHeight, double theKnockBackStrength, double theInitialDistance) {
        super(theDamage, theWidth, theHeight, theKnockBackStrength, theInitialDistance);
        myStage = 0;
    }

    @Override
    public boolean update() {

        if (myStage == 1) {
            deactivate();
        } else if (myStage == 4 * 2) {
            return true;
        }
        myStage++;
        return false;
    }

    @Override
    public String[] getDrawData() {
        return new String[]{"rotatedImage:Swipe" + myStage/2 + ":" + getX() + ":" + getY()
                + ":" + getWidth() + ":" + getHeight() + ":" + getAngle().getRadians()};
    }
}
