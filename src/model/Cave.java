package model;

public class Cave {
    private Room[][] myRooms;

    public Cave(int width, int height) {
        myRooms = new Room[width][height];
        generateCave();
    }

    public void generateCave() {
        for (int i = 0; i < myRooms.length; i++) {
            for (int j = 0; j < myRooms[i].length; j++) {
                myRooms[i][j] = new Room();
            }
        }
    }

    public void movePlayer(int x, int y) {

        if (x >= 0 && x < myRooms.length && y >= 0 && y < myRooms[x].length) {
            Character player = getCharacter();
            Room newRoom = myRooms[x][y];
            //player.setCurrentRoom(newRoom);
        } else {
            System.out.println("Move invalid: Coordinates are out of the cave's bounds.");
        }
    }


    private Character getCharacter() {
        // Placeholder for actual player retrieval logic
        return new Dwarf();
    }
}
