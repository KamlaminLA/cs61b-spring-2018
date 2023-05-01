package byog.Core;

import java.util.Random;

public class RectangularRoom {
    int width;
    int height;
    Position pos;
    public RectangularRoom (int width, int height, Position pos) {
        this.width = width;
        this.height = height;
        this.pos = pos;
    }
    public static RectangularRoom generateRectangular (Random RANDOM, int gameWidth, int gameHeight) {
        // random generate RectangularRoom and confirm their position
        // width and height are random number from 0 to game.height or width and not exceed it
        int rectangularWidth = RANDOM.nextInt(gameWidth / 3 - 3 ) + 3;
        int rectangularHeight = RANDOM.nextInt(gameHeight / 3 - 3) + 3;

        // since the rectangular width and height should not exceed the room height and width
        // we should set the rectangularRoom position x, y has rectangularWidth and H from the edge
        int xCoordinate = RANDOM.nextInt(gameWidth - rectangularWidth);
        int yCoordinate = RANDOM.nextInt(gameHeight - rectangularHeight);

        Position rectangularPosition = new Position(xCoordinate, yCoordinate);
        RectangularRoom newRoom = new RectangularRoom(rectangularWidth, rectangularHeight, rectangularPosition);

        return newRoom;

    }

}
