package Model.Spaces;

import java.io.Serializable;

/**
 * Represents a serializable cave consisting of multiple rooms.
 */
public class Cave implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     * Array of Rooms
     */
    private Room[] myRooms;

    /**
     * Index of current Room.
     */
    private int currentRoomIndex;

    /**
     * Constructor for Cave, generates Rooms.
     */
    public Cave() {
        generateCave();
    }

    /**
     * Generates Cave and initializes Rooms.
     */
    public void generateCave() {
        final int numberOfRooms = 6;
        myRooms = new Room[numberOfRooms];

        for (int i = 0; i < myRooms.length; i++) {
            myRooms[i] = new Room(i, myRooms.length);
        }

        currentRoomIndex = 0;
    }

    /**
     * Gets current Room.
     * @return current Room the Dwarf is in.
     */
    public Room getCurrentRoom() {
        return myRooms[currentRoomIndex];
    }

    /**
     * Decrements currentRoomIndex and moves current Room to previous Room.
     */
    public void moveToPreviousRoom() {
        if (hasPreviousRoom()) {
            if (currentRoomIndex == 1) {
                System.out.println("Moving to last room with index of: " + currentRoomIndex);
                currentRoomIndex--;
            } else {
                System.out.println("Moving to previous room with index of: " + currentRoomIndex);
                currentRoomIndex--;
                myRooms[currentRoomIndex].spawnEnemies();
            }
        } else {
            System.out.println("You are in the first room. Cannot move to previous room.");
        }
    }

    /**
     * Increments currentRoomIndex and moves current Room to next Room.
     */
    public void moveToNextRoom() {
        if (hasNextRoom()) {
            currentRoomIndex++;
            myRooms[currentRoomIndex].spawnEnemies();
        } else {
            System.out.println("You are in the last room. Cannot move to next room.");
        }
    }

    /**
     * Checks if Cave has another previous Room to go to.
     * @return if Cave has previous Room.
     */
    public boolean hasPreviousRoom() {
        return currentRoomIndex > 0;
    }

    /**
     * Checks if Cave has next Room to go to.
     * @return if Cave has previous Room.
     */
    public boolean hasNextRoom() {
        return currentRoomIndex < myRooms.length - 1;
    }

    /**
     * String display of myRooms.
     * @return representation of all rooms.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Room room : myRooms) {
            sb.append(room.toString()).append("\n");
        }
        return sb.toString();
    }
}
