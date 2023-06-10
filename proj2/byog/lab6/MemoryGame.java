package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }
        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String res = "";
        for (int i = 0; i < n; i += 1) {
            char x = CHARACTERS[rand.nextInt(CHARACTERS.length)];
            res += x;
        }
        return res;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        // If we have a print method will be easy for us to debug, we can track every step and print them
        System.out.println(s);
        StdDraw.clear();
        Font font = new Font("Felix", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(20, 20, s);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        // Make a String[] first
        String[] lettersArr = letters.split("");
        for (int i = 0; i < lettersArr.length; i += 1) {
            drawFrame(lettersArr[i]);
            StdDraw.pause(1000);
            StdDraw.clear();
            StdDraw.show();
            StdDraw.pause(750);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        // solicitNCharsInput help us read all the input user typed and determine if all input correct
        String collect = "";
        for (int i = 0; i < n; i += 1) {
            while (StdDraw.hasNextKeyTyped()) {
                char x = StdDraw.nextKeyTyped();
                collect += x;
                drawFrame(collect);
            }
        }
        return collect;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 0;
        gameOver = false;
        //TODO: Establish Game loop
        while (!gameOver) {
            String userString;
            round += 1;
            String randString = generateRandomString(round);
            drawFrame("Round: " + round);
            StdDraw.pause(2000);

            flashSequence(randString);
            userString = solicitNCharsInput(round);
            if (!randString.equals(userString)) {
                gameOver = true;
            }
            StdDraw.pause(750);
        }
        drawFrame("Game Over! You made it to round" + round);
    }

}
