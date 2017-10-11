package mazeSolver;

import maze.Cell;
import maze.Maze;

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
            followWall(maze, maze.entrance);
        }
        catch (TraverseHitExitException traverseHitExitException)
        {
            solveStatus = true;
        }
    }

    /**
     *
     * Wall follower
     *
     * @param maze
     * @param nextCell
     */
    private void followWall(Maze maze, Cell nextCell) throws TraverseHitExitException
    {
        // Init, add the first cell to the stack
        rightStack.push(nextCell);
        markedList[nextCell.r][nextCell.c] = true;
        maze.drawFtPrt(nextCell);

        while(!rightStack.isEmpty())
        {
            // Grab a cell out
            Cell nextRightCell = rightStack.pop();

            if(nextRightCell.r == maze.exit.r && nextRightCell.c == maze.exit.c)
            {
                throw new TraverseHitExitException();
            }

            // follow the right wall
            stackPusher(maze, nextRightCell, rightStack);
        }
    }

    private void stackPusher(Maze maze, Cell cell, Stack<Cell> stack)
    {
        // Iterate each direction (EAST will always be the first direction)
        for(int direction = 0; direction < Maze.NUM_DIR; direction++)
        {

            // Detect if it should turn with current direction
            if(shouldTurn(cell, directionHelper.getRelativeDirection(direction)))
            {
                Cell relativeCell = cell.neigh[directionHelper.getRelativeDirection(direction)];

                // Mark the list
                markedList[relativeCell.r][relativeCell.c] = true;

                // Draw the pattern
                maze.drawFtPrt(relativeCell);

                // Push to the stack
                stack.push(relativeCell);

                // Reset the relative position if it's turning left or right
                directionHelper.turnDirection(-direction);


            }
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
