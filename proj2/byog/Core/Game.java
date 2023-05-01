package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    // read the first string of the input
    public static char readFirstChar (String input) {
        return input.charAt(0);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        // we are not able to deal all the code in this method, need to create
        // other class to help us break the hard problem into small task
        input = input.toLowerCase();
        char firstChar =  readFirstChar(input);
        long seed = Long.parseLong(input.replaceAll("[^0-9]", ""));
        Random RANDOM = new Random(seed);
        TETile[][] world = worldGenerator(RANDOM);
        return  world;

    }

    public static TETile[][] worldGenerator(Random RANDOM) {
        List<RectangularRoom> existingRooms = new ArrayList<>();
        // Initiate the tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < world.length; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        // put random rooms into existingRooms and then insert the existing rooms to world
        for (int x = 0; x < 10; x += 1) {
            RectangularRoom newRoom = RectangularRoom.generateRectangular(RANDOM, WIDTH, HEIGHT);
            if (!roomOverlap(newRoom, existingRooms)) {
                existingRooms.add(newRoom);
            }
        }
        for (RectangularRoom room : existingRooms) {
            insertRoom(world, room);
        }

        List<HallWay> halls = new ArrayList<>();
        // put all the applicable hall to List<HallWay> and then insert to the world
        for (int i = 0; i < existingRooms.size(); i += 1) {
            RectangularRoom room1 = existingRooms.get(i);
            RectangularRoom room2;
            if (i == existingRooms.size() - 1) {
                room2 = existingRooms.get(i - 1);
            } else {
                room2 = existingRooms.get(i + 1);
            }
            halls.addAll(HallWay.connectRoom(RANDOM, room1, room2));
        }
        for (HallWay hall : halls) {
            insertHallWay(world, hall);
        }

        //random create an unlocked door among the floor
        for (int i = 0; i < 20; i += 1) {
            int x = RANDOM.nextInt(WIDTH - 4) + 4;
            int y = RANDOM.nextInt(HEIGHT - 4) + 4;
            if (world[x][y] == Tileset.WALL) {
                world[x][y] = Tileset.LOCKED_DOOR;
                break;
            }
        }
        return world;
    }

    // insert rectangular room into game
    public static void insertRoom(TETile[][] world, RectangularRoom room) {
        for (int i = 0; i < room.width; i += 1) {
            for (int j = 0; j < room.height; j += 1) {
                world[room.pos.xCoordinate + i][room.pos.yCoordinate + j] = Tileset.WALL;
            }
        }
        for (int i = 1; i < room.width - 1; i += 1) {
            for (int j = 1; j < room.height - 1; j += 1) {
                world[room.pos.xCoordinate + i][room.pos.yCoordinate + j] = Tileset.FLOOR;
            }
        }
    }

    // insert the floor into the rectangular room
    public static void insertFloor(TETile[][] world) {

    }

    // insert the halls to the world so that connect all the room
    public static void insertHallWay(TETile[][] world, HallWay hall) {
        if (hall.isHorizontal) {
            if (hall.length > 0) {
                for (int x = 0; x <= hall.length; x += 1) {
                    world[hall.pos.xCoordinate + x][hall.pos.yCoordinate] = Tileset.FLOOR;
                    setWall(world, hall.pos.xCoordinate + x, hall.pos.yCoordinate + 1);
                    setWall(world, hall.pos.xCoordinate + x, hall.pos.yCoordinate - 1);
                }
            } else {
                for (int x = 0; x >= hall.length; x -= 1) {
                    world[hall.pos.xCoordinate + x][hall.pos.yCoordinate] = Tileset.FLOOR;
                    setWall(world, hall.pos.xCoordinate + x, hall.pos.yCoordinate + 1);
                    setWall(world, hall.pos.xCoordinate + x, hall.pos.yCoordinate - 1);
                }
            }
        } else {
            if (hall.length > 0) {
                for (int y = 0; y <= hall.length; y += 1) {
                    world[hall.pos.xCoordinate][hall.pos.yCoordinate + y] = Tileset.FLOOR;
                    setWall(world, hall.pos.xCoordinate + 1, hall.pos.yCoordinate + y);
                    setWall(world, hall.pos.xCoordinate - 1, hall.pos.yCoordinate + y);
                }
            } else {
                for (int y = 0; y >= hall.length; y -= 1) {
                    world[hall.pos.xCoordinate][hall.pos.yCoordinate + y] = Tileset.FLOOR;
                    setWall(world, hall.pos.xCoordinate + 1, hall.pos.yCoordinate + y);
                    setWall(world, hall.pos.xCoordinate - 1, hall.pos.yCoordinate + y);
                }
            }
        }
    }
    // insert Floor if current tile is not floor
    public static void setWall(TETile[][] world, int x, int y) {
        if (world[x][y] != Tileset.FLOOR) {
            world[x][y] = Tileset.WALL;
        }
    }

    // check whether the room overlap
    public static boolean roomOverlap(RectangularRoom rr, List<RectangularRoom> er) {
        for (RectangularRoom room : er) {
            if (checkOverlap(rr, room)) {
                return true;
            }
        }
        return false;
    }
    // helper method
    private static boolean checkOverlap (RectangularRoom r1, RectangularRoom r2) {
        int r1XPos = r1.pos.xCoordinate;
        int r1EndXPos = r1XPos + r1.width;
        int r1YPos = r1.pos.yCoordinate;
        int r1EndYPos = r1YPos + r1.height;

        int r2XPos = r2.pos.xCoordinate;
        int r2EndXPos = r2XPos + r2.width;
        int r2YPos = r2.pos.yCoordinate;
        int r2EndYPos = r2YPos + r2.height;

        /*if ((r2XPos > r1XPos && r2XPos < r1EndXPos) || (r2EndXPos > r1XPos && r2EndXPos < r1EndXPos)) {
            if ((r2YPos > r1YPos && r2YPos < r1EndYPos) || (r2EndYPos > r1YPos && r2EndYPos < r1EndYPos)) {
                return true;
            }
        }*/

        for (int x = r1XPos; x < r1EndXPos; x += 1) {
            if (x < r2EndXPos && x > r2XPos) {
                for (int y = r1YPos; y < r1EndYPos; y += 1) {
                    if (y < r2EndYPos && y > r2YPos) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
