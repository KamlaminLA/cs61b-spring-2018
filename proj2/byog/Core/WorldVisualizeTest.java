package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Random;

public class WorldVisualizeTest {

    public static void main(String[] args) {
        int WIDTH = 80;
        int HEIGHT = 30;
        TERenderer ter = new TERenderer();
        Random random = new Random(1123567);

        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = Game.worldGenerator(random);
        ter.renderFrame(world);
    }
}
