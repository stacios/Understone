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
import java.util.*;

public class Room implements Drawable, Serializable {
    private static final long serialVersionUID = 3L;

    /**
     * Wall Thickness Integer.
     */
    public static final int WALL_THICKNESS = 100;

    /**
     * List of Glyphids.
     */
    private final List<Glyphid> myGlyphids;

    /**
     * List of Dwarf Attacks. 
     */
    private final List<Attack> myDwarfAttacks;

    /**
     * List of Glyphid Attacks. 
     */
    private final List<Attack> myGlyphidAttacks;

    /**
     * List of Rocks/Crystals. 
     */
    private final List<Rock> myRocks;

    /**
     * Glyphid Egg. 
     */
    private Rock myEgg;

    /**
     * Room identifier index. 
     */
    private int myRoomIdentifier;

    /**
     * Total number of rooms.
     */
    private final int myTotalRooms;

    /**
     * Boolean if post egg enemies have been spawned.
     */
    private boolean eggEnemiesSpawned;

    /**
     * Boolean if egg has been collected.
     */
    private boolean myCollectedEgg;

    /**
     * Room constructor that intializes values of what it will contain.
     * @param theIdentifier is the room identifier index.
     * @param theTotalRooms is the total number of rooms.
     */
    public Room(final int theIdentifier, final int theTotalRooms) {
        myRoomIdentifier = theIdentifier;
        myGlyphids = new ArrayList<>();
        myDwarfAttacks = new ArrayList<>();
        myGlyphidAttacks = new ArrayList<>();
        myRocks = new ArrayList<>();
        myTotalRooms = theTotalRooms;
        myCollectedEgg = false;
    }

    /**
     * Boolean if Room contains Egg.
     * @return if Egg is null.
     */
    public boolean hasEgg() {
        return myEgg != null;
    }

    /**
     * Resets room to remove any leftover objects
     */
    public void clearRoom() {
        myGlyphids.clear();
        myRocks.clear();
    }

    /**
     * Gets room identifier.
     * @return room identifier.
     */
    public int getIdentifier() {
        return myRoomIdentifier;
    }

    /**
     * Spawns enemies.
     */
    // TODO maybe fix magic numbers
    public void spawnEnemies() {
            Random random = new Random();

            int numberOfP = 1 + (int)(Math.random() * 3);
            for (int i = 0; i < numberOfP; i++) {
                Glyphid praetorian = CharacterFactory.createGlyphid(PRAETORIAN);
                praetorian.setX(random.nextDouble() * 1920 * (.5) + 1920 / 4.0);
                praetorian.setY(random.nextDouble() * 1080 * (1/3.0) + 1080 / 3.0);
                myGlyphids.add(praetorian);
            }

            int numberOfAS = 1 + (int)(Math.random() * 3);
            for (int i = 0; i < numberOfAS; i++) {
                Glyphid acidSpitter = CharacterFactory.createGlyphid(ACID_SPIITER);
                acidSpitter.setX(random.nextDouble() * 1920 * (.5) + 1920 / 4.0);
                acidSpitter.setY(random.nextDouble() * 1080 * (1/3.0) + 1080 / 3.0);
                myGlyphids.add(acidSpitter);
            }

            int numberOfGrunts = 5 + (int)(Math.random() * (4 - myGlyphids.size()));
            for (int i = 0; i < numberOfGrunts; i++) {
                Glyphid grunt = CharacterFactory.createGlyphid(GRUNT);
                grunt.setX(random.nextDouble() * 1920 * (.5) + 1920 / 4.0);
                grunt.setY(random.nextDouble() * 1080 * (1/3.0) + 1080 / 3.0);
                myGlyphids.add(grunt);
            }

            int numberOfHeals = 1 + (int) (Math.random() * 2);

            for (int i = 0; i < numberOfHeals; i++) {
                Rock heal = CharacterFactory.createObject(HEAL);
                heal.setX(random.nextDouble() * 1920 * (.5) + 1920 / 4.0);
                heal.setY(random.nextDouble() * 1080 * (1/3.0) + 1080 / 3.0);
                myRocks.add(heal);
            }
            System.out.println("Spawned " + (numberOfGrunts + numberOfP + numberOfAS) + " enemies in the room.");

        if (myRoomIdentifier >= myTotalRooms - 1) {
            spawnEgg();
        }
    }

    /**
     * Spawns egg in center of room.
     */
    public void spawnEgg() {
        myEgg = CharacterFactory.createObject(EGG);
        myEgg.setX(960);
        myEgg.setY(540);
        myRocks.add(myEgg);
    }

    /**
     * Returns if myGlyphids list is empty so dwarf can move to next room.
     * @return if myGlyphids is empty.
     */
    public boolean canExit() {
        return myGlyphids.isEmpty();
    }

    /**
     * Specialized version of spawning version because we need a delay after egg is grabbed, as well as stronger enemies.
     */
    public void spawnEggEnemies() {
        // Since spawnEggEnemies is called in the update loop, put this in or it's going to spawn egg enemies multiple times
        if (eggEnemiesSpawned) return;

        eggEnemiesSpawned = true;
            Random random = new Random();

            for (int i = 0; i < 3; i++) {
                Glyphid p = CharacterFactory.createGlyphid(PRAETORIAN);
                p.setX(random.nextDouble() * 1920 * (.30) + 1920 / 3.0);
                p.setY(random.nextDouble() * 1080 * (.30) + 1080 / 3.0);
                myGlyphids.add(p);
            }
    }

    /**
     * Positions dwarf relative to the door they enter/exit in
     *
     * @param thePlayer is the passed player dwarf.
     */
    public void positionDwarf(Dwarf thePlayer) {
        if (thePlayer.hasEgg()) {
            thePlayer.setX(950);
            thePlayer.setY(925);
        } else {
            thePlayer.setX(950);
            thePlayer.setY(155);
        }
    }

    /**
     * Checks if Dwarf is in movable area to go through door.
     * @param thePlayer is the player.
     * @return if player is in area.
     */
    // TODO magic numbers for now
    public boolean isDwarfInArea(final Dwarf thePlayer) {
        // If Dwarf has Egg, they can move upward but not downwards
        if (thePlayer.hasEgg()) {
            return thePlayer.getX() > 800 && thePlayer.getX() < 1100 && thePlayer.getY() > 145 && thePlayer.getY() < 160;
        }

        // If Dwarf does not have Egg, they can only move downwards
        else {
            return thePlayer.getY() > 900 && thePlayer.getY() < 950 && thePlayer.getX() > 800 && thePlayer.getX() < 1100;
        }
    }

    /**
     * Update method
     */
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

                // play voiceline
                if (myGlyphids.isEmpty()) {
                    int rand = (int)(Math.random() * 12);
                    if (rand < 6) {
                        String name = GameLoop.getInstance().getPlayer().getName();
                        if (name.equals("Driller") || name.equals("Engineer") || name.equals("Gunner") || name.equals("Scout"))
                            GameLoop.getInstance().addDrawData("sound:Response" + rand + name);
                    }
                }
            }
        }

        for (int i = myRocks.size() - 1; i >= 0; i--) {
            Rock heal = myRocks.get(i);
            boolean flag = heal.update();
            if (flag) {
                myRocks.remove(i);
                Dwarf player = GameLoop.getInstance().getPlayer();
                player.addHealth(20);
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

        Character c1, c2;
        for (int i = 0; i < myGlyphids.size(); i++) {
            for (int j = i + 1; j < myGlyphids.size(); j++) {
                c1 = myGlyphids.get(i);
                c2 = myGlyphids.get(j);
                if (c1.colliding(c2)) {
                    c1.addForce(new Force(new Angle(c2.getX(), c2.getY(), c1.getX(), c1.getY()), 1));
                    c2.addForce(new Force(new Angle(c1.getX(), c1.getY(), c2.getX(), c2.getY()), 1));
                }
            }
        }

        // player should receive more pushback than glyphids, to prevent the player from running through them
        Dwarf p = GameLoop.getInstance().getPlayer();
        for (Glyphid g : myGlyphids) {
            if (p.colliding(g)) {
                p.addForce(new Force(new Angle(g.getX(), g.getY(), p.getX(), p.getY()), 10));
                g.addForce(new Force(new Angle(p.getX(), p.getY(), g.getX(), g.getY()), .2));
            }
        }

        for (Rock r : myRocks) {
            for (Glyphid g : myGlyphids) {
                if (r.colliding(g)) {
                    g.addForce(new Force(new Angle(r.getX(), r.getY(), g.getX(), g.getY()), 1));
                }
            }

            if (p.colliding(r)) {
                p.addForce(new Force(new Angle(r.getX(), r.getY(), p.getX(), p.getY()), 10));
                r.addForce(new Force(new Angle(p.getX(), p.getY(), r.getX(), r.getY()), .2));
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

            for (Rock r : myRocks) {
                if (r.colliding(a)) {
                    r.receiveAttack(a);
                    flag = true;
                }
            }

            if (flag) {
                a.collided();
            }
        }

        Dwarf player = GameLoop.getInstance().getPlayer();
        if (myEgg != null && GameLoop.getInstance().isDwarfInteracting() && !myCollectedEgg
        && player.getX() < 1920/2 + 200 && player.getX() > 1920/2 - 200 && player.getY() < 1080/2 + 200 && player.getY() > 1080/2 - 200) {
            // This is so when collecting the egg, it will only trigger this once
            myCollectedEgg = true;
            myRocks.remove(myEgg);
            player.setEgg(true);

            GameLoop.getInstance().addDrawData("sound:PickupEgg");
            GameLoop.getInstance().addDrawData("sound:EggGrabRoars");
            GameLoop.getInstance().addDrawData("screenShake:200:9");
            spawnEggEnemies();
            System.out.println("Egg picked up by the dwarf.");
        }
    }

    /**
     * Gets draw data for rendering.
     * @return draw data.
     */
    @Override
    public String[] getDrawData() {
        List<String> result = new ArrayList<>();

        String roomImage = switch (myRoomIdentifier) {
            case 0 -> "RoomPod";
            case 1 -> "Room";
            case 2, 4 -> "RoomR";
            case 3 -> "RoomB";
            // In last case, ternary conditional for room should never trigger
            default -> myRoomIdentifier == myTotalRooms - 1 ? "RoomEgg" : "Room";
        };

        result.add("image:" + roomImage + ":" + Display.getInstance().getWidth() / 2 + ":" + Display.getInstance().getHeight() / 2 + ":1920:1080");


        for (Glyphid e : myGlyphids.toArray(new Glyphid[0])) {
            result.addAll(Arrays.asList(e.getDrawData()));
        }

        for (Rock r : myRocks.toArray(new Rock[0])) {
            result.addAll(Arrays.asList(r.getDrawData()));
        }
        result.addAll(Arrays.asList(GameLoop.getInstance().getPlayer().getDrawData()));
        for (Attack e : myGlyphidAttacks.toArray(new Attack[0])) {
            result.addAll(Arrays.asList(e.getDrawData()));
        }
        for (Attack e : myDwarfAttacks.toArray(new Attack[0])) {
            result.addAll(Arrays.asList(e.getDrawData()));
        }

        return result.toArray(new String[0]);
    }

    /**
     * String representation of Room.
     * @return string representation of Room.
     */
    @Override
    public String toString() {
        return "Room{" +
                "myRoomIdentifier=" + myRoomIdentifier +
                '}';
    }

}
