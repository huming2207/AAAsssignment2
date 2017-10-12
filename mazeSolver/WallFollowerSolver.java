package mazeSolver;

import maze.Cell;
import maze.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver
{
    private boolean[][] markedList;
    private Stack<Cell> rightStack;
    private int exploreCounter;
    private DirectionHelper directionHelper;

    private boolean solveStatus;

    public WallFollowerSolver()
    {
        solveStatus = false;
        rightStack = new Stack<>();
        exploreCounter = 0;
    }

    private void initWallFollower(Maze maze)
    {
        // Initialize the list, set all to false
        markedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

        // Initialize the direction helper
        directionHelper = new DirectionHelper(maze.type == Maze.HEX);

        // Start to follow the wall
        // To make my life easier, I've used a exception to force the wall follower stop when it hits the exit.
        try
        {
            runDfs(maze);
        }
        catch (TraverseHitExitException traverseHitExitException)
        {
            solveStatus = true;
        }
    }

    /**
     * Bidirectional DFS runner
     * No recursion this time as it's more convenient (and faster/smaller JVM stack?).
     *
     * @param maze The input maze
     */
    private void runDfs(Maze maze) throws TraverseHitExitException
    {
        // Init
        rightStack.push(maze.entrance);

        while(!rightStack.empty())
        {
            // Pop some fresh cells
            Cell selectedCell = rightStack.pop();

            // Draw the dot
            maze.drawFtPrt(selectedCell);

            // Stop if matches:
            if(selectedCell.r == maze.exit.r && selectedCell.c == maze.exit.c)
            {
                throw new TraverseHitExitException();
            }

            // Auto add stack for entrance side
            dfsStackPusher(selectedCell, maze);

        }
    }

    /**
     *
     * Bi-DFS stack pusher
     *
     * @param targetCell The target cell, will iterate and add its neighbor cell
     * @param maze The maze
     */
    private void dfsStackPusher(Cell targetCell, Maze maze)
    {
        // Exit side part, ignore if marked
        if(!markedList[targetCell.r][targetCell.c])
        {
            // Mark the cell
            markedList[targetCell.r][targetCell.c] = true;

            for(int directionIndex = 0; directionIndex < Maze.NUM_DIR; directionIndex++)
            {

                // Update its heading information to direction helper
                directionHelper.updateHeading(directionHelper.getRightDirection());

                // Get its "relatively" right cell
                Cell cell = targetCell.neigh[directionHelper.getRightDirection()];

                if (cell != null && SolveHelper.canGoThru(cell, targetCell))
                {
                    rightStack.push(cell);
                }
            }

            // Increase the counter
            exploreCounter++;
        }
    }

    private boolean shouldTurn(Cell cell, int direction)
    {
        return (cell.neigh[direction] != null
                && cell.wall[direction] != null
                && !cell.wall[direction].present
                && !markedList[cell.neigh[direction].r][cell.neigh[direction].c]);
    }

    @Override
    public void solveMaze(Maze maze)
    {
        initWallFollower(maze);
    }


    @Override
    public boolean isSolved()
    {
        return solveStatus;
    } // end if isSolved()


    @Override
    public int cellsExplored()
    {
        return exploreCounter;
    } // end of cellsExplored()

} // end of class WallFollowerSolver
