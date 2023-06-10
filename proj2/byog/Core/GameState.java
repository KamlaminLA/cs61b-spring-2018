package byog.Core;

import byog.TileEngine.TETile;

import java.io.Serializable;

public class GameState implements Serializable {
    /** GameState class has two variable are TETile[][] and Player
     * this two variable are use for GameState to record the current state
     * of the game.
     */
    Player player;
    TETile[][] world;

    public GameState(Player player, TETile[][] world) {
        this.player = player;
        this.world = world;
    }
}
