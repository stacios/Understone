package Model.Glyphid;

import Model.Dwarf;
import Model.Force;
import Model.Angle;
import Model.GameLoop;
import Model.Weapon.MeleeAttack;
import Model.Weapon.Weapon;

public class Praetorian extends Glyphid {
    private static final int ATTACK_DAMAGE = 10;
    private static final double ATTACK_KNOCKBACK = 10.0;
    private static final long ATTACK_PAUSE_BEFORE = 5000;
    private static final long ATTACK_PAUSE_AFTER = 5000;
    private static final double FORCE_RESISTANCE = 0.5;

    private long lastAttackTime = 0;
    private boolean isAttacking = false;
    private boolean isPausedBeforeAttack = false;

    public Praetorian(String theName, double theX, double theY, int theHealth,
                      int theWidth, int theHeight, double theMoveSpeed,
                      Weapon theWeapon, double theAttackRange) {
        super(theName, theX, theY, theHealth, theWidth, theHeight, theMoveSpeed, theWeapon, theAttackRange);
    }

    @Override
    public boolean update() {
        Dwarf player = GameLoop.getInstance().getPlayer();
        if (player == null) {
            return super.update();
        }

        double distance = Math.sqrt(Math.pow(player.getX() - this.getX(), 2) + Math.pow(player.getY() - this.getY(), 2));

        if (isPausedBeforeAttack) {
            if (System.currentTimeMillis() - lastAttackTime >= ATTACK_PAUSE_BEFORE) {
                isPausedBeforeAttack = false;
                performAttack(player);
                lastAttackTime = System.currentTimeMillis();
                isAttacking = true;
            }
        } else if (isAttacking) {
            if (System.currentTimeMillis() - lastAttackTime >= ATTACK_PAUSE_AFTER) {
                isAttacking = false;
            }
        } else {
            if (distance <= getAttackRange()) {
                isPausedBeforeAttack = true;
                lastAttackTime = System.currentTimeMillis();
            } else {
                Angle angleToPlayer = new Angle(this.getX(), this.getY(), player.getX(), player.getY());
                if (angleToPlayer != null) {
                    addForce(new Force(angleToPlayer, getMoveSpeed(), 0.4));
                }
            }
        }

        return super.update();
    }

    private void performAttack(Dwarf player) {
        MeleeAttack attack = new MeleeAttack(ATTACK_DAMAGE, 20, 20, ATTACK_KNOCKBACK, 0); // Adjust dimensions as needed
        attack.setPosition(this.getX(), this.getY(), new Angle(this.getX(), this.getY(), player.getX(), player.getY()));
        player.receiveAttack(attack);
    }

    @Override
    public void addForce(Force force) {
        // Apply 50% resistance to incoming forces
        Force reducedForce = new Force(force.getAngle(), force.getStrength() * FORCE_RESISTANCE, force.update() ? force.getStrength() : 0);
        super.addForce(reducedForce);
    }
}
