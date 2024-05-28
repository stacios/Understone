package Model.Spaces;

import Controller.Drawable;
import Model.*;
import Model.Character;
import Model.Weapon.Attack;
import Model.Glyphid.Glyphid;
import Model.Glyphid.Rock;
import View.Display;

import static Model.CharacterTypes.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Room implements Drawable, Serializable {
    private static final long serialVersionUID = 3L;

    public static final int WALL_THICKNESS = 100;
    private List<Glyphid> myGlyphids;
    private List<Attack> myDwarfAttacks;
    private List<Attack> myGlyphidAttacks;
    private boolean myHasDropPod;
    private Rock myRock;
    private Rock myEgg;
    private int myIdentifier;
    private int myTotalRooms;

    public Room(int theIdentifier, int theTotalRooms) {
        myIdentifier = theIdentifier;
        myGlyphids = new ArrayList<>();
        myDwarfAttacks = new ArrayList<>();
        myGlyphidAttacks = new ArrayList<>();
        myTotalRooms = theTotalRooms;
    }

    public boolean hasRock() {
        return myRock != null;
    }

    public boolean hasEgg() {
        return myEgg != null;
    }

    public int getIdentifier() {
        return myIdentifier;
    }

    public void spawnEnemies() {
        Random random = new Random();

        // Increase the difficulty factor based on room identifier
        int difficultyFactor = myIdentifier + 1;

        int numberOfP = 0;
        for (int i = 0; i < numberOfP; i++) {
            Glyphid praetorian = CharacterFactory.createGlyphid(PRAETORIAN);
            praetorian.setX(random.nextDouble() * 1920 * (2.0 / 3) + 1920 / 6.0);
            praetorian.setY(random.nextDouble() * 1080 * (2.0 / 3) + 1080 / 6.0);
            myGlyphids.add(praetorian);
        }

        int numberOfAS = 1;
        for (int i = 0; i < numberOfAS; i++) {
            Glyphid acidSpitter = CharacterFactory.createGlyphid(ACID_SPIITER);
            acidSpitter.setX(random.nextDouble() * 1920 * (2.0 / 3) + 1920 / 6.0);
            acidSpitter.setY(random.nextDouble() * 1080 * (2.0 / 3) + 1080 / 6.0);
            myGlyphids.add(acidSpitter);
        }

        // Add random number of Grunts, increased by difficulty factor
        //int numberOfGrunts = (random.nextInt(3) + 5) + difficultyFactor;
        // Temp number of grunts to reduce nuymber of grunts spawned, use for dev testing
        int numberOfGrunts = 0;
        for (int i = 0; i < numberOfGrunts; i++) {
            Glyphid grunt = CharacterFactory.createGlyphid(GRUNT);
            grunt.setX(random.nextDouble() * 1920 * (2.0 / 3) + 1920 / 6.0);
            grunt.setY(random.nextDouble() * 1080 * (2.0 / 3) + 1080 / 6.0);
            myGlyphids.add(grunt);
        }

        if (myIdentifier >= myTotalRooms - 1) {
            spawnRock();
        }

        System.out.println("Spawned " + (numberOfGrunts + numberOfP + numberOfAS) + " enemies in the room.");
    }

    // Todo public for now depending on if we want spawning logic to be handled in Cave
    public void spawnRock() {
        myRock = CharacterFactory.createObject(ROCK);
        myRock.setX(960);
        myRock.setY(540);
        myGlyphids.add(myRock);
        System.out.println("Spawned rock in the last room.");
    }

    // Todo public for now depending on if we want spawning logic to be handled in Cave
    public void spawnEgg() {
        myEgg = CharacterFactory.createObject(EGG);
        myEgg.setX(960);
        myEgg.setY(540);
        myGlyphids.add(myEgg);
        System.out.println("Spawned egg after the rock was broken.");
    }

    public boolean canExit() {
        return myGlyphids.isEmpty() && !hasRock();
    }

    /**
     * Gets number of glyphids in current room.
     *
     * @return number of glyphids in current room.
     */
    public int getGlyphids() {
        return myGlyphids.size();
    }

    // TODO magic numbers for now
    public boolean isDwarfInArea(Dwarf dwarf) {
        return dwarf.getY() > 100 && dwarf.getX() > 850 && dwarf.getX() < 1100;
    }

    public void update() {
        // update, remove, receive attacks
        GameLoop.getInstance().getPlayer().update();
        myDwarfAttacks.addAll(List.of(GameLoop.getInstance().getPlayer().getPendingAttacks()));
        for (int i = myGlyphids.size() - 1; i >= 0; i--) {
            Glyphid glyphid = myGlyphids.get(i);
            boolean flag = glyphid.update();
            myGlyphidAttacks.addAll(List.of(glyphid.getPendingAttacks()));
            if (flag) {
                myGlyphids.remove(i);
                if (glyphid == myRock) {
                    spawnEgg();
                    // TODO might refactor later, just a temp way to play sound
                    GameLoop.getInstance().addDrawData("sound:RockBroken");
                } else if (glyphid == myEgg) {
                    GameLoop.getInstance().addDrawData("sound:AlienEggGrabRoars");
                }
            }
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
        result.add("image:Room:" + Display.getInstance().getWidth() / 2 + ":" + Display.getInstance().getHeight() / 2 + ":1920:1080");
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

    @Override
    public String toString() {
        return "Room{" +
                "myIdentifier=" + myIdentifier +
                '}';
    }
}
