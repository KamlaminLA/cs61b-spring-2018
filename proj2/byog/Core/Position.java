package byog.Core;

import java.io.Serializable;

public class Position implements Serializable {
    int xCoordinate;
    int yCoordinate;
    public Position (int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }
    public boolean equalPosition (Position pos) {
        return (pos.xCoordinate == this.xCoordinate && pos.yCoordinate == this.yCoordinate);
    }
}
