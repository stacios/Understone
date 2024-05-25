package Model.Spaces;

import Controller.Drawable;
import Model.*;
import Model.Character;
import Model.Weapon.Attack;
import Model.Glyphid.Glyphid;
import Model.Glyphid.Rock;
import View.Display;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Room implements Drawable, Serializable {
    private static final long serialVersionUID = 3L;

    public static final int WALL_THICKNESS = 100;
    private List<Glyphid> myGlyphids;
    private List<Attack> myDwarfAttacks;
    private List<Attack> myGlyphidAttacks;
    private boolean myHasDropPod;
    private Rock myRock;

    public Room(boolean theHasDropPod, boolean theHasRock) {
        myGlyphids = new ArrayList<>();
        myDwarfAttacks = new ArrayList<>();
        myGlyphidAttacks = new ArrayList<>();
        myHasDropPod = theHasDropPod;
        if (theHasRock) {
            myRock = new Rock("Rock", 1,1,1,1,1,0,null);
        }

        myGlyphids.add(CharacterFactory.createGlyphid("Grunt")); // TEMPORARY

    }
    public boolean hasRock() {
        return myRock != null;
    }

    public void spawnEnemies() {
        System.out.println("Enemies spawned in the room.");
    }

    public boolean canExit() {
        // Return true only if (?)
        return false;
    }


    public void update() {

        // update, remove, receive attacks
        GameLoop.getInstance().getPlayer().update();
        myDwarfAttacks.addAll(List.of(GameLoop.getInstance().getPlayer().getPendingAttacks()));
        for (int i = myGlyphids.size() - 1; i >= 0; i--) {
            boolean flag = myGlyphids.get(i).update();
            myGlyphidAttacks.addAll(List.of(myGlyphids.get(i).getPendingAttacks()));
            if (flag)
                myGlyphids.remove(i);
        }
        for (int i = myDwarfAttacks.size() - 1; i >= 0; i--) {
            if (myDwarfAttacks.get(i).update()) {
                myDwarfAttacks.remove(i);
            }
        }
        for (int i = myGlyphidAttacks.size() - 1; i >= 0; i--) {
            if (myGlyphidAttacks.get(i).update()) {
                myGlyphidAttacks.remove(i);
            }
        }

        // character on character collisions
        List<Character> characters = new ArrayList<>(myGlyphids);
        characters.add(GameLoop.getInstance().getPlayer());
        Character c1, c2;
        for (int i = 0; i < characters.size(); i++) {
            for (int j = i + 1; j < characters.size(); j++) {
                c1 = characters.get(i);
                c2 = characters.get(j);
                if (c1.colliding(c2)) {
                    c1.addForce(new Force(new Angle(c2.getX(), c2.getY(), c1.getX(), c1.getY()), 2, .5));
                    c2.addForce(new Force(new Angle(c1.getX(), c1.getY(), c2.getX(), c2.getY()), 2, .5));
                }
            }
        }

        // attack on character collisions

        for (Attack a : myGlyphidAttacks) {
            if (GameLoop.getInstance().getPlayer().colliding(a)) {
                GameLoop.getInstance().getPlayer().receiveAttack(a);
                a.collided();
            }
        }

        for (Attack a : myDwarfAttacks) {
            boolean flag = false;
            for (Glyphid g : myGlyphids) {
                if (g.colliding(a)) {
                    g.receiveAttack(a);
                    flag = true;
                }
            }
            if (flag) {
                a.collided();
            }
        }
    }

    @Override
    public String[] getDrawData() {

        List<String> result = new ArrayList<>();
        result.add("image:Room:" + Display.getInstance().getWidth() /2 + ":" + Display.getInstance().getHeight()/2 + ":1920:1080");
        for (Glyphid e : myGlyphids) {
            result.addAll(Arrays.asList(e.getDrawData()));
        }
        result.addAll(Arrays.asList(GameLoop.getInstance().getPlayer().getDrawData()));
        for (Attack e : myGlyphidAttacks) {
            result.addAll(Arrays.asList(e.getDrawData()));
        }
        for (Attack e : myDwarfAttacks) {
            result.addAll(Arrays.asList(e.getDrawData()));
        }


        return result.toArray(new String[0]);
    }

}
