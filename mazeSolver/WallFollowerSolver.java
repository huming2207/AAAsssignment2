package mazeSolver;

import maze.Cell;
import maze.Maze;

import java.util.Stack;


/**
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver
{
    private Stack<Cell> cellStack;
    private boolean[][] markedList;

    private boolean solveStatus;

    public WallFollowerSolver()
    {
        cellStack = new Stack<>();
        solveStatus = false;
    }

    private void initWallFollower(Maze maze)
    {
        // Initialize the list, set all to false
        markedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

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
        markedList[nextCell.r][nextCell.c] = true;
        maze.drawFtPrt(nextCell);

        if(nextCell.r == maze.exit.r && nextCell.c == maze.exit.c)
        {
            throw new TraverseHitExitException();
        }

        // Always try turn east first, if it can't, try north and south, then west.
        if(shouldTurn(nextCell, Maze.EAST))
        {
            followWall(maze, nextCell.neigh[Maze.EAST]);
        }

        if(shouldTurn(nextCell, Maze.NORTH))
        {
            followWall(maze, nextCell.neigh[Maze.NORTH]);
        }

        if(shouldTurn(nextCell, Maze.SOUTH))
        {
            followWall(maze, nextCell.neigh[Maze.SOUTH]);
        }

        if(shouldTurn(nextCell, Maze.NORTHEAST))
        {
            followWall(maze, nextCell.neigh[Maze.NORTHEAST]);
        }

        if(shouldTurn(nextCell, Maze.SOUTHWEST))
        {
            followWall(maze, nextCell.neigh[Maze.SOUTHWEST]);
        }

        if(shouldTurn(nextCell, Maze.WEST))
        {
            followWall(maze, nextCell.neigh[Maze.WEST]);
        }


    }

    /**
     *
     * Detect if it should turn to a specific direction
     *
     * Condition:
     *  1. Wall is not null (for normal maze cell, only #0, #2, #3 and #5 are not null)
     *  2. Wall doesn't present (has been torn down)
     *  3. The neighbor is not null
     *  4. Wasn't marked
     *
     * Btw, I REALLY DON'T KNOW WHY USE int INSTEAD OF enum FOR DIRECTION???????
     * If enum was used, I would use iterator instead of a brunch of if-else and this method...
     *
     * @param nextCell
     * @param directionType
     * @return
     */
    private boolean shouldTurn(Cell nextCell, int directionType)
    {
        return (nextCell.wall[directionType] != null
                && !nextCell.wall[directionType].present
                && nextCell.neigh[directionType] != null
                && !markedList[nextCell.neigh[directionType].r][nextCell.neigh[directionType].c]);
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
        // TODO Auto-generated method stub
        return 0;
    } // end of cellsExplored()

} // end of class WallFollowerSolver
