/**
 * SWEN20003 Object Oriented Software Development
 * Project 2, Semester 2, 2019
 *
 * Uses sample solution of Project 1.
 *
 * @author Jason Polychronopoulos
 *
 * External code referenced:
 * Reading csv files - https://stackabuse.com/reading-and-writing-csvs-in-java/
 *
 */

import bagel.Window;
import bagel.util.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board {

    private ArrayList<Peg> pegs = new ArrayList<>();
    private ArrayList<Ball> balls = new ArrayList<>();

    // Indexes and strings for parsing csv files
    private static final int TYPE_IDX = 0;
    private static final int X_IDX = 1;
    private static final int Y_IDX = 2;

    // Strings for imageSrc string generation
    private static final String RES_PATH = "res/";
    private static final String PEG_SRC = "peg.png";

    private Ball ball;
    private Bucket bucket;
    private PowerUp powerUp;

    public Board(String boardName) {
        loadBoard(boardName);
        generateRedPegs();
        this.bucket = new Bucket();
    }

    /**
     * Loads the game board from a csv.
     * @param boardName The name of the board csv file.
     */
    public void loadBoard(String boardName) {

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(boardName));
            String row;

            // Read through every line of the csv and store in an ArrayList
            while (( row = csvReader.readLine()) != null) {

                String[] data = row.split(",");
                String[] pegType = data[TYPE_IDX].split("_");
                boolean isHorizontal = false;
                boolean isVertical = false;

                // Get the x and y co-ordinates of the peg
                double x = Double.parseDouble(data[X_IDX]);
                double y = Double.parseDouble(data[Y_IDX]);

                String imageSrc;
                // Peg has a special shape
                if (pegType.length > 2) {
                    if (pegType[TYPE_IDX +2].equals("horizontal")) {
                        isHorizontal = true;
                    }
                    else {
                        isVertical = true;
                    }
                    imageSrc = RES_PATH + pegType[TYPE_IDX] + "-" + pegType[TYPE_IDX +2] + "-" + PEG_SRC;
                }

                // The peg is regular
                else {
                    imageSrc = RES_PATH + pegType[TYPE_IDX] + "-" + PEG_SRC;
                }

                // Adds the peg to the ArrayList of pegs
                if (pegType[0].equals("blue")) {
                    pegs.add(new BluePeg(new Point(x, y), imageSrc, isHorizontal, isVertical));
                }
                else {
                    pegs.add(new GreyPeg(new Point(x, y), imageSrc, isHorizontal, isVertical));
                }
            }
            csvReader.close();
        }

        // Catch improper filename
        catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    /**
     * Counts the number of blue pegs on the game board.
     * @return count: The number of blue pegs on the game board.
     */
    private int countBluePegs() {
        int count = 0;
        for (Peg peg : pegs)
            count += (peg instanceof BluePeg) ? 1 : 0;
        return count;
    }

    /**
     * Counts the number of red pegs on the game board.
     * @return count: The number of red pegs on the game board.
     */
    private int countRedPegs() {
        int count = 0;
        for (Peg peg : pegs)
            count += (peg instanceof RedPeg) ? 1 : 0;
        return count;
    }

    /**
     * Gets the indexes of all the blue pegs in the pegs ArrayList.
     * @return bluePegIndexes: Array of indexes for all the blue pegs on the board.
     */
    private Integer[] getBluePegIndexes() {
        Integer[] bluePegIndexes = new Integer[countBluePegs()];
        int index=0;
        for (Peg peg : pegs) {
            if (peg instanceof BluePeg) {
                bluePegIndexes[index] = pegs.indexOf(peg);
                index++;
            }
        }
        return bluePegIndexes;
    }

    /**
     * Get the current boards bucket.
     * @return the instance bucket.
     */
    public Bucket getBucket() {
        return bucket;
    }

    /**
     * Randomly selects 1/5th of the blue pegs on the game board, and turns them into red pegs.
     */
    private void generateRedPegs() {

        final double PERCENTAGE_RED_PEGS = 0.2;

        // Shuffle an array with the size equalling the number of blue pegs on the board
        Integer[] bluePegIndexes = getBluePegIndexes().clone();
        Collections.shuffle(Arrays.asList(bluePegIndexes));

        // Slice the array to the number of red pegs that will be generated
        int numberOfRedPegs = (int) Math.floor(countBluePegs() * PERCENTAGE_RED_PEGS);
        Integer[] redPegsIndexes = Arrays.copyOfRange(bluePegIndexes, 0, numberOfRedPegs);

        // Select blue pegs from an array of sampled indexes
        for (int redPegIndex: redPegsIndexes) {

            setPegAtIndex(redPegIndex,
                    "red",
                    pegs.get(redPegIndex).isHorizontal(),
                    pegs.get(redPegIndex).isVertical());
        }
    }

    /**
     * Generates a green peg from a random blue pegs position.
     */
    private void generateGreenPeg() {

        // Shuffle an array with the size equalling the number of blue pegs on the board
        Integer[] bluePegIndexes = getBluePegIndexes().clone();
        Collections.shuffle(Arrays.asList(bluePegIndexes));

        // If there are blue pegs left to turn green
        if (bluePegIndexes.length > 0) {
            int greenPegIndex = bluePegIndexes[0];
            setPegAtIndex(greenPegIndex,
                    "green",
                    pegs.get(greenPegIndex).isHorizontal(),
                    pegs.get(greenPegIndex).isVertical());

        }
    }

    /**
     * Set a peg in pegs to a specific type.
     * @param index The index of the peg in the ArrayList you wish to set.
     * @param colour A string of the colour of the peg.
     * @param isHorizontal boolean value if the peg is horizontal.
     * @param isVertical boolean value if the peg is vertical.
     */
    private void setPegAtIndex(int index, String colour, boolean isHorizontal, boolean isVertical) {

        String imageSrc;
        Peg peg;

        if (isHorizontal) {
            imageSrc = RES_PATH + colour + "-horizontal-"+PEG_SRC;
        }
        else if (isVertical) {
            imageSrc = RES_PATH + colour + "-vertical-"+PEG_SRC;
        }
        else {
            imageSrc = RES_PATH + colour + "-" + PEG_SRC;
        }

        // Based on colour, set the peg.
        switch (colour) {
            case "green":
                peg = new GreenPeg(pegs.get(index).getRect().centre(), imageSrc, isHorizontal, isVertical);
                break;
            case "red":
                peg = new RedPeg(pegs.get(index).getRect().centre(), imageSrc, isHorizontal, isVertical);
                break;
            case "blue":
                peg = new BluePeg(pegs.get(index).getRect().centre(), imageSrc, isHorizontal, isVertical);
                break;
            default:
                peg = null;
                break;
        }

        // Replace the randomly selected blue peg with a green peg
        pegs.set(index, peg);
    }

    public void setBall(Point position, Vector2 direction) {
        this.ball = new Ball(position, direction, "res/ball.png");
    }

    /**
     * Sets the current boards ball to null when off-screen.
     * @return true when ball has landed off screen, false otherwise.
     */
    public boolean ballOutOfBounds() {

        if (ball.outOfScreen()) {
            ball = null;
            return true;
        }
        return false;
    }

    /**
     * Gets the current ball of the board.
     * @return null if ball is null, else returns a ball.
     */
    public Ball getBall() {
        if (ball == null) {
            return null;
        }
        return ball;
    }

    /**
     * Spawns a power-up on the board.
     */
    private void spawnPowerUp() {
        double chance = Math.random();
        if (chance < 0.1 && powerUp == null) {
            powerUp = new PowerUp(
                    new Point(Math.random() * Window.getWidth(),
                            Math.random() * Window.getHeight()),
                    new Point(Math.random() * Window.getWidth(),
                            Math.random() * Window.getHeight()
                    )
            );
        }
    }

    /**
     * Generate a fireball at the position of the ball that struck the power-up.
     */
    private void generateFireball() {
        // If the ball exists and intersects the power-up, it becomes a fireball
        if (ball != null && ball.intersects(powerUp)) {
            this.ball = new Fireball(ball.getRect().centre(), ball.getVelocity(), "res/fireball.png");
            powerUp = null;
        }
    }

    /**
     * Reset the green peg from the previous turn back to a blue peg.
     */
    private void clearGreenPeg() {

        // Set the green peg back to a blue peg.
        for (int i=0; i < this.pegs.size(); i++) {
            if (pegs.get(i) instanceof GreenPeg) {
                setPegAtIndex(i, "blue", pegs.get(i).isHorizontal(), pegs.get(i).isVertical());
            }
        }
    }


    /**
     * Check whether the ball has come into contact with bucket.
     * @return true when ball has intersected the bucket, false otherwise.
     */
    @Deprecated
    public boolean ballCaughtByBucket() {
        return ball.intersects(bucket);
    }

    /**
     * All actions to be performed by the board at the start of a turn.
     */
    public void startNewTurn() {
        clearGreenPeg();
        generateGreenPeg();
        spawnPowerUp();
    }

    /**
     * Checks to see when the board is complete
     * @return true when the board has had all red pegs destroyed, false otherwise.
     */
    public boolean boardComplete() {
        return countRedPegs() == 0;
    }

    public void update() {

        bucket.update();

        // The power-up is in play
        if (powerUp != null) {
            powerUp.update();
            generateFireball();
        }

        // A ball exists
        if (ball != null) {

            ball.update();

            // Delete pegs that can be destroyed
            for (int i=0; i < pegs.size(); i++) {

                // Store an array of the corner points of the current ball
                Point[] ballPoints = {
                        ball.getRect().topLeft(),
                        ball.getRect().topRight(),
                        ball.getRect().bottomLeft(),
                        ball.getRect().bottomRight()
                };

                // The ball has hit a peg
                if (ball.intersects(pegs.get(i))) {

                    /*
                     Loop through all corners and the centre of the ball,
                     and use the first point that intersects with the peg
                    */
                    for (Point ballPoint : ballPoints) {
                        if (ball.getRect().intersects(ballPoint)) {
                            ball.bounceOff(
                                    pegs.get(i).getRect().intersectedAt(ballPoint, ball.getVelocity())
                            );
                        }
                    }

                    // If the ball is not a grey peg, we can destroy it
                    if (!(pegs.get(i) instanceof GreyPeg)) pegs.remove(i);
                }
            }
        }

        // Draw all remaining pegs
        for (Peg peg : pegs) peg.draw();

    }

}


