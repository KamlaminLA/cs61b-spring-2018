package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Game {
    static TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final String SAVE_GAME_FILE = "saved-game.txt";

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public static void playWithKeyboard() {
        displayMenu();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char x = Character.toLowerCase(StdDraw.nextKeyTyped());
                interpretChar(x);
            }
        }
    }

    public static void interpretChar(char key) {
        if (key == 'n') {
            Random rand = new Random(askUserSeed());
            TETile[][] world = worldGenerator(rand);
            Player player = initiatePlayer(world, rand);
            startGameWithKeyboard(world, player);
        } else if (key == 'l') {
            loadGameWithKeyboard();
        } else if (key == 'q') {
            System.exit(0);
        }
    }

    // Start game with keyboard
    public static void startGameWithKeyboard(TETile[][] world, Player player) {
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char currentKey = StdDraw.nextKeyTyped();
                if (currentKey == ':') {
                    // we need this while loop to help us take a break to give us time
                    // to typed 'q' because the program immediately process next if statement
                    // if we didn't break and we haven't typed yet.
                    while (!StdDraw.hasNextKeyTyped()) {
                    }
                    if (Character.toLowerCase(StdDraw.nextKeyTyped()) == 'q') {
                        break;
                    }
                } else {
                    world[player.pos.xCoordinate][player.pos.yCoordinate] = Tileset.FLOOR;
                    player.moverPlayer(world, currentKey, WIDTH, HEIGHT);
                    world[player.pos.xCoordinate][player.pos.yCoordinate] = Tileset.PLAYER;
                    ter.renderFrame(world);
                    System.out.println("success");
                }
            }
        }
        gameSave(world, player);
        displayMenu();
    }

    //load the previous game by using keyboard
    public static void loadGameWithKeyboard() {
        GameState gameState = readObjectFromFile(SAVE_GAME_FILE);
        startGameWithKeyboard(gameState.world, gameState.player);
    }

    public static GameState readObjectFromFile(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Object obj = objectInputStream.readObject();
            objectInputStream.close();
            return (GameState) obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    // read user seed when using playWithKeyBoard
    public static long askUserSeed() {
        StdDraw.clear();
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        Font font = new Font("Felix", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 10, "Please Enter a Seed");
        StdDraw.show();

        String seed = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char currentKey = StdDraw.nextKeyTyped();
                if (currentKey == 's') {
                    break;
                }
                if (Character.isDigit(currentKey)) {
                    seed += currentKey;
                }
            }
        }
        return Long.parseLong(seed);
    }

    // display the game menu for user to select
    public static void displayMenu() {
        StdDraw.clear();
        StdDraw.clear(Color.black);
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        Font font = new Font("Felix", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.text(WIDTH / 2, HEIGHT / 2 + 10, "CS61B: THE GAME");
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 5, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 10, "Load Game (Q)");
        StdDraw.show();
    }

    // read the first string of the input
    public static char readFirstChar(String input) {
        return input.charAt(0);
    }

    public static String readUserInput(String userInput) {
        String res = userInput.replaceAll("\\d", "");
        try {
            res = res.substring(1);
        } catch (NullPointerException e) {
            return null;
        }
        return res;
    }

    // initiate a Player with random position
    public static Player initiatePlayer(TETile[][] world, Random rand) {
        Position pos = new Position(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
        while (world[pos.xCoordinate][pos.yCoordinate] != Tileset.FLOOR) {
            pos = new Position(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
        }
        Player player = new Player(pos);
        world[pos.xCoordinate][pos.yCoordinate] = Tileset.PLAYER;

        return player;
    }

    // save the game state to the save-game.txt file
    public static void gameSave(TETile[][] world, Player player) {
        try {
            FileOutputStream fileOut = new FileOutputStream(SAVE_GAME_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            GameState gameState = new GameState(player, world);
            out.writeObject(gameState);
            out.close();
            System.out.println("ObjectOutputStream Closed Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Break statement has no effect on if statements. It only works on switch, for and do loops.
    public static void startWithInputString(TETile[][] world, Player player, String input) {
        for (int i = 0; i < input.length(); i += 1) {
            char currentKey = input.charAt(i);
            if (currentKey == ':' && input.charAt(i + 1) == 'q') {
                break;
            }
            world[player.pos.xCoordinate][player.pos.yCoordinate] = Tileset.FLOOR;
            player.moverPlayer(world, currentKey, WIDTH, HEIGHT);
        }
        world[player.pos.xCoordinate][player.pos.yCoordinate] = Tileset.PLAYER;
        gameSave(world, player);
    }

    // load the previous game when the user type 'l'
    public static TETile[][] loadPreviousGame(String input) {
        GameState gameState = readGameFile(SAVE_GAME_FILE);
        startWithInputString(gameState.world, gameState.player, input);
        return gameState.world;
    }

    // read the previous gameState that we previously save
    public static GameState readGameFile(String FilePath) {
        try {
            FileInputStream fileIn = new FileInputStream(FilePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            Object obj = in.readObject();
            in.close();
            return (GameState) obj;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
        char firstChar = readFirstChar(input);
        if (firstChar == 'n') {
            long seed = Long.parseLong(input.replaceAll("[^0-9]", ""));
            String userInput = readUserInput(input);
            Random random = new Random(seed);
            TETile[][] world = worldGenerator(random);

            Player player = initiatePlayer(world, random);
            startWithInputString(world, player, userInput);
            return world;
        }
        if (firstChar == 'l') {
            String userInput = readUserInput(input);
            return loadPreviousGame(userInput);
        }
        if (firstChar == 'q') {
            return null;
        }
        throw new RuntimeException("Input Error");
    }

    public static TETile[][] worldGenerator(Random random) {
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
            RectangularRoom newRoom = RectangularRoom.generateRectangular(random, WIDTH, HEIGHT);
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
            halls.addAll(HallWay.connectRoom(random, room1, room2));
        }
        for (HallWay hall : halls) {
            insertHallWay(world, hall);
        }

        //random create an unlocked door among the floor
        for (int i = 0; i < 20; i += 1) {
            int x = random.nextInt(WIDTH - 4) + 4;
            int y = random.nextInt(HEIGHT - 4) + 4;
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
    private static boolean checkOverlap(RectangularRoom r1, RectangularRoom r2) {
        int r1XPos = r1.pos.xCoordinate;
        int r1EndXPos = r1XPos + r1.width;
        int r1YPos = r1.pos.yCoordinate;
        int r1EndYPos = r1YPos + r1.height;

        int r2XPos = r2.pos.xCoordinate;
        int r2EndXPos = r2XPos + r2.width;
        int r2YPos = r2.pos.yCoordinate;
        int r2EndYPos = r2YPos + r2.height;

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
