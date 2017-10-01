package mazeSolver;

import maze.Cell;
import maze.Maze;

/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver
{
    private boolean[][] markedList;
    private boolean solveStatus;
    private int exploreCounter;

    public BiDirectionalRecursiveBacktrackerSolver()
    {
        // Mark the status
        solveStatus = true;

        exploreCounter = 0;
    }

    private void initBiDfs(Maze maze)
    {
        // Initialize the list, set all to false
        markedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

        // Start to run bi-directional DFS
        // To make my life easier, I've used a exception to force the wall follower stop when it hits the exit.
        try
        {
            runBiDfs(maze, maze.entrance, maze.exit);
        }
        catch (TraverseHitSearchException exception)
        {
            solveStatus = true;
        }
    }

    private void runBiDfs(Maze maze, Cell nextStartCell, Cell nextEndCell) throws TraverseHitSearchException
    {

    }


    @Override
    public void solveMaze(Maze maze)
    {
        initBiDfs(maze);

    } // end of solveMaze()


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

} // end of class BiDirectionalRecursiveBackTrackerSolver
