package byog.Core;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class HallWay {
    int length;
    boolean isHorizontal;
    Position pos;

    // HallWay constructor
    public HallWay (int length, boolean isHorizontal, Position p) {
        this.length = length;
        this.isHorizontal = isHorizontal;
        this.pos = p;
    }

    // return a list of HallWay that able to connect room1 and room 2
    public static List<HallWay> connectRoom (Random RANDOM, RectangularRoom room1, RectangularRoom room2) {
        int room1XPosition = RANDOM.nextInt(room1.width - 2) + room1.pos.xCoordinate + 1;
        int room1YPosition = RANDOM.nextInt(room1.height - 2) + room1.pos.yCoordinate + 1;

        int room2XPosition = RANDOM.nextInt(room2.width - 2) + room2.pos.xCoordinate + 1;
        int room2YPosition = RANDOM.nextInt(room2.height - 2) + room2.pos.yCoordinate + 1;

        Position startXPosition = new Position(room1XPosition, room1YPosition);
        int hallWayWidth = room2XPosition - room1XPosition;

        Position startYPosition = new Position(room1XPosition + hallWayWidth, room1YPosition);
        int hallWayHeight = room2YPosition - room1YPosition;


        HallWay horizontalHall = new HallWay(hallWayWidth, true, startXPosition);
        HallWay verticalHall = new HallWay(hallWayHeight, false, startYPosition);

        List<HallWay> hallWayList = new ArrayList<>();
        if (horizontalHall.length != 0) {
            hallWayList.add(horizontalHall);
        }
        if (verticalHall.length != 0) {
            hallWayList.add(verticalHall);
        }
        return hallWayList;
    }
}
