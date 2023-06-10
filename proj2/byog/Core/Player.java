package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

/** java.io.Serializable is a interface that allows objects to be serialized,
 * which means the object will be converting into a stream of bytes, and later
 * convert back into the object if needed.
 */

public class Player implements Serializable {
    Position pos;

    public Player(Position pos) {
        this.pos = pos;
    }

    public void moverPlayer(TETile[][] world, char input, int worldWidth, int worldHeight) {
        input = Character.toLowerCase(input);
        if (input == 'w') {
            if (isMovable(world, worldWidth, worldHeight, this.pos.xCoordinate, this.pos.yCoordinate + 1)) {
                this.pos.yCoordinate += 1;
            }
        }
        if (input == 's') {
            if (isMovable(world, worldWidth, worldHeight, this.pos.xCoordinate, this.pos.yCoordinate - 1)) {
                this.pos.yCoordinate -= 1;
            }
        }
        if (input == 'a') {
            if (isMovable(world, worldWidth, worldHeight, this.pos.xCoordinate - 1, this.pos.yCoordinate)) {
                this.pos.xCoordinate -= 1;
            }
        }
        if (input == 'd') {
            if (isMovable(world, worldWidth, worldHeight, this.pos.xCoordinate + 1, this.pos.yCoordinate)) {
                this.pos.xCoordinate += 1;
            }
        }
    }
    public boolean isMovable(TETile[][] world, int worldWidth, int worldHeight, int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (x > worldWidth || y > worldHeight) {
            return false;
        }
        if (world[x][y] != Tileset.FLOOR) {
            return false;
        }
        return true;
    }

}
