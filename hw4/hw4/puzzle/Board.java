package hw4.puzzle;

public class Board implements WorldState {
    private int[][] tiles;
    private int length;
    /** Constructs a board from an N-by-N array of tiles where
     *  tiles[i][j] = tile at row i, column j
     * @param tiles
     */
    public Board(int[][] tiles) {
        this.tiles = tiles;
        length = tiles[0].length;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * @param i
     * @param j
     * @return
     */
    public int tileAt(int i, int j) {
        if (i >= length || i < 0) {
            throw new IndexOutOfBoundsException("i must between 0 and N - 1");
        }
        if (j >= length || j < 0) {
            throw new IndexOutOfBoundsException("i must between 0 and N - 1");
        }
        return tiles[i][j];
    }

    /**
     * Returns the board size N
     * @return
     */
    public int size() {

    }

    /**
     * Returns the neighbors of the current board
     * @return all neighbors
     */
    public Iterable<WorldState> neighbors() {

    }

    /**
     * Hamming estimate described below
     * @return
     */
    public int hamming() {

    }

    /**
     * Manhattan estimate described below
     * @return
     */
    public int manhattan() {

    }

    /**
     * Estimated distance to goal. This method should simply return the results of
     * manhattan() when submitted to Gradescope.
     * @return
     */
    public int estimatedDistanceToGoal() {

    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's
     * @param y
     * @return
     */
    public boolean equals(Object y) {

    }

    /**
     * Returns the string representation of the board. This
     * method is provided in the skeleton
     * @return
     */
    public String toString() {

    }
    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
