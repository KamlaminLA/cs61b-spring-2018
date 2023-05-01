package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /**
     * compute the hexagon width for each row i
     * i start from 0, the bottom row
     * @param s the size of hexagon
     * @param i the row of hexagon
     * @return
     */
    public int hexagonWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return effectiveI * 2 + s;
    }

    /**
     * Computesrelative x coordinate of the leftmost tile in the ith
     * row of a hexagon, assuming that bottom row has an x-coordinate
     * of zero. For example, if s = 3, i = 2, this function return -2
     * @param s size of the hexagon
     * @param i row num of the hexagon, where i = 0 is the bottom
     * @return
     */

    public static int hexRowOffSet(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 -effectiveI;
        }
        return -effectiveI;
    }

    public static void addRow(TETile[][] world, Position p, int width, TETile t) {

        }

}
