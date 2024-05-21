package Model.Spaces;

import Model.Spaces.Room;

import java.io.Serializable;

public class Cave implements Serializable {
    private static final long serialVersionUID = 2L;
    private Room[][] myRooms;

    public Cave(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }
        myRooms = new Room[width][height];
        generateCave();
    }

    public void generateCave() {
        for (int i = 0; i < myRooms.length; i++) {
            for (int j = 0; j < myRooms[i].length; j++) {
                myRooms[i][j] = new Room(Math.random() > 0.8, Math.random() > 0.5);
            }
        }
    }

    public void movePlayer(int x, int y) {
        if (x >= 0 && x < myRooms.length && y >= 0 && y < myRooms[x].length) {
            //Dwarf player = getCharacter();
            // Room newRoom = myRooms[x][y];
            // newRoom.addPlayer(player);
            System.out.println("Player moves to room at position (" + x + ", " + y + ")");
        } else {
            System.out.println("Move invalid: out of the cave bounds.");
        }
    }

    @Override public String toString() { StringBuilder sb = new StringBuilder(); for (Room[] row : myRooms) { for (Room room : row) { sb.append(room.toString()); } } return sb.toString(); }

}
