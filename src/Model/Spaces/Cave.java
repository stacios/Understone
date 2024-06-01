package Model.Spaces;

import Model.Dwarf;

import java.io.Serializable;
import java.util.Random;

public class Cave implements Serializable {
    private static final long serialVersionUID = 2L;
    private Room[] myRooms;
    private int currentRoomIndex;

    public Cave() {
        generateCave();
    }

    public void generateCave() {
        int numberOfRooms = 6;
        myRooms = new Room[numberOfRooms];

        for (int i = 0; i < myRooms.length; i++) {
            myRooms[i] = new Room(i, myRooms.length); // Added room identifier
        }

        // TODO add logic for spawning drop pod in final room

        currentRoomIndex = 1; // Start in the first room
    }

    public Room getCurrentRoom() {
        return myRooms[currentRoomIndex];
    }

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

    public void moveToNextRoom() {
        if (hasNextRoom()) {
            currentRoomIndex++;
            myRooms[currentRoomIndex].spawnEnemies();
        } else {
            System.out.println("You are in the last room. Cannot move to next room.");
        }
    }

    public boolean hasPreviousRoom() {
        return currentRoomIndex > 0;
    }

    public boolean hasNextRoom() {
        return currentRoomIndex < myRooms.length - 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Room room : myRooms) {
            sb.append(room.toString()).append("\n");
        }
        return sb.toString();
    }
}
