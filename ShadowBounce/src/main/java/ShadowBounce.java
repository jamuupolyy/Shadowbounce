/**
 * SWEN20003 Object Oriented Software Development
 * Project 2, Semester 2, 2019
 *
 * Uses sample solution of Project 1.
 *
 * @author Jason Polychronopoulos
 *
 */

import bagel.*;
import bagel.util.*;

import java.util.*;

public class ShadowBounce extends AbstractGame  {

    private static final Point BALL_POSITION = new Point(512, 32);
    private static final int NUM_BOARDS = 5;
    private static int currentBoardNumber;
    private static int shots;

    private boolean addShots;
    private boolean newTurn;

    private static ArrayList<Board> boards = new ArrayList<>();

    // for testing purposes
    // private Board currentBoard = new Board("res/testBoard.csv");

    public ShadowBounce() {

        // Initialise all our game boards
        for (int i = 0; i < NUM_BOARDS; i++) {
            boards.add(new Board("res/"+i+".csv"));
        }

        // We indicate the start of a new turn, which switches off until all balls are out of bounds.
        newTurn = true;
        currentBoardNumber = 0;
        shots = 20;
    }

    @Override
    public void update(Input input) {

        // Load the current board.
        Board currentBoard = boards.get(currentBoardNumber);

        // If the current board is complete, load the next board.
        if (currentBoard.boardComplete()) currentBoardNumber++;

        // Game ends when there are no more shots left, ESCAPE key is pressed or all boards have been completed.
        if (shots == 0 || input.wasPressed(Keys.ESCAPE) || currentBoardNumber > 4) {
            Window.close();
        }

        // Update the current board state
        currentBoard.update();

        // If it is a new turn, spawn all new turn related characters.
        if (newTurn) {
            currentBoard.startNewTurn();
            newTurn = false;
        }

        // If we don't have a ball and the mouse button was clicked, create one
        if (input.wasPressed(MouseButtons.LEFT) && currentBoard.getBall() == null) {
            currentBoard.setBall(BALL_POSITION, input.directionToMouse(BALL_POSITION));
        }

        // Ball is in play
        if (currentBoard.getBall() != null) {

            // Ball leaves play while touching the bucket, add shots.
            if (currentBoard.getBall().intersects(currentBoard.getBucket())) addShots = true;

            // Delete the ball when it leaves the screen and de-increment unless ball is caught by the bucket.
            if (currentBoard.ballOutOfBounds()) {
                shots += addShots ? 1 : -1;
                addShots = false;
                newTurn = true;
            }
        }
    }

    public static void main(String[] args) {
        new ShadowBounce().run();
    }
}
