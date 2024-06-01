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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    // We need this for inputting slight delay in enemies spawned to sync up with Room transition animation
    private transient ScheduledExecutorService myScheduler;
    private boolean eggEnemiesSpawned;


    public Room(int theIdentifier, int theTotalRooms) {
        myIdentifier = theIdentifier;
        myGlyphids = new ArrayList<>();
        myDwarfAttacks = new ArrayList<>();
        myGlyphidAttacks = new ArrayList<>();
        myTotalRooms = theTotalRooms;
        myScheduler = Executors.newScheduledThreadPool(1);
    }

    public boolean hasEgg() {
        return myEgg != null;
    }

    // We dont want any objects left over from previous
    public void clearRoom() {
        myGlyphids.clear();
    }

    public int getIdentifier() {
        return myIdentifier;
    }

    public void spawnEnemies() {
        myScheduler.schedule(() -> {
            Random random = new Random();

            // Increase the difficulty factor based on room identifier
            int difficultyFactor = myIdentifier + 1;

            int numberOfP = 1;
            for (int i = 0; i < numberOfP; i++) {
                Glyphid praetorian = CharacterFactory.createGlyphid(PRAETORIAN);
                praetorian.setX(random.nextDouble() * 1920 * (2.0 / 3) + 1920 / 6.0);
                praetorian.setY(random.nextDouble() * 1080 * (2.0 / 3) + 1080 / 6.0);
                myGlyphids.add(praetorian);
            }

            int numberOfAS = 2;
            for (int i = 0; i < numberOfAS; i++) {
                Glyphid acidSpitter = CharacterFactory.createGlyphid(ACID_SPIITER);
                acidSpitter.setX(random.nextDouble() * 1920 * (2.0 / 3) + 1920 / 6.0);
                acidSpitter.setY(random.nextDouble() * 1080 * (2.0 / 3) + 1080 / 6.0);
                myGlyphids.add(acidSpitter);
            }

            // Add random number of Grunts, increased by difficulty factor
            //int numberOfGrunts = (random.nextInt(3) + 5) + difficultyFactor;
            // Temp number of grunts to reduce nuymber of grunts spawned, use for dev testing
            int numberOfGrunts = 3;
            for (int i = 0; i < numberOfGrunts; i++) {
                Glyphid grunt = CharacterFactory.createGlyphid(GRUNT);
                grunt.setX(random.nextDouble() * 1920 * (2.0 / 3) + 1920 / 6.0);
                grunt.setY(random.nextDouble() * 1080 * (2.0 / 3) + 1080 / 6.0);
                myGlyphids.add(grunt);
            }

            int numberOfHeals = 2;

            for (int i = 0; i < numberOfHeals; i++) {
                Rock heal = CharacterFactory.createObject(HEAL);
                heal.setX(random.nextDouble() * 1920 * (2.0 / 3) + 1920 / 6.0);
                heal.setY(random.nextDouble() * 1080 * (2.0 / 3) + 1080 / 6.0);
                myGlyphids.add(heal);
            }
            System.out.println("Spawned " + (numberOfGrunts + numberOfP + numberOfAS) + " enemies in the room.");
        }, 75, TimeUnit.MILLISECONDS);


        if (myIdentifier >= myTotalRooms - 1) {
            spawnEgg();
        }
    }

    // Todo public for now depending on if we want spawning logic to be handled in Cave
    public void spawnRock() {
        myRock = CharacterFactory.createObject(HEAL);
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

    // TODO Comment empty glyphid list for dev purposes
    // TODO Find some better way of doing this
    // Returns if all glyphids are dead. Ignores rock(as crystals do not have to be destroyed).
    public boolean canExit() {
        return myGlyphids.stream().noneMatch(g -> !(g instanceof Rock));
        //return true;
    }

    /**
     * Specialized version of spawning version because we need a delay after egg is grabbed, as well as stronger enemies.
     */
    public void spawnEggEnemies() {
        // Since spawnEggEnemies is called in the update loop, put this in or it's going to spawn egg enemies multiple times
        if (eggEnemiesSpawned) return;

        eggEnemiesSpawned = true;
        myScheduler.schedule(() -> {
            Random random = new Random();

            for (int i = 0; i < 1; i++) {

                Glyphid p = CharacterFactory.createGlyphid(PRAETORIAN);
                p.setX(random.nextDouble() * 1920 * (2.0 / 3) + 1920 / 6.0);
                p.setY(random.nextDouble() * 1080 * (2.0 / 3) + 1080 / 6.0);
                myGlyphids.add(p);
            }
        }, 2, TimeUnit.SECONDS);
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
        // If Dwarf has Egg, they can move upward but not downwards
        if (dwarf.hasEgg()) {
            return dwarf.getX() > 800 && dwarf.getX() < 1120 && dwarf.getY() > 145 && dwarf.getY() < 160;
        }

        // If Dwarf does not have Egg, they can only move downwards
        else {
            return dwarf.getY() > 900 && dwarf.getY() < 950 && dwarf.getX() > 850 && dwarf.getX() < 1100;
        }
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
                if (glyphid instanceof Rock) {
                    Dwarf player = GameLoop.getInstance().getPlayer();
                    // Magic heal value for now
                    player.addHealth(20);
                    GameLoop.getInstance().addDrawData("sound:Heal");
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

        // Todo Way to detect if player is colliding with egg, and then collect it with space;
        Dwarf player = GameLoop.getInstance().getPlayer();
        if (myEgg != null && player.colliding(myEgg) && GameLoop.getInstance().isDwarfInteracting()) {
            myGlyphids.remove(myEgg);
            player.setEgg(true);

            // Todo temporary sound for egg and roars
            GameLoop.getInstance().addDrawData("sound:PickupEgg");
            GameLoop.getInstance().addDrawData("sound:EggGrabRoars");
            Display.getInstance().shakeScreen(200, 9);
            spawnEggEnemies();
            System.out.println("Egg picked up by the dwarf.");
        }
    }

    @Override
    public String[] getDrawData() {
        List<String> result = new ArrayList<>();

        String roomImage = switch (myIdentifier) {
            case 0 -> "RoomPod";
            case 1 -> "Room";
            case 2, 4 -> "RoomR";
            case 3 -> "RoomB";
            // In last case, ternary conditional for room should never trigger
            default -> myIdentifier == myTotalRooms - 1 ? "RoomEgg" : "Room";
        };

        result.add("image:" + roomImage + ":" + Display.getInstance().getWidth() / 2 + ":" + Display.getInstance().getHeight() / 2 + ":1920:1080");


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
