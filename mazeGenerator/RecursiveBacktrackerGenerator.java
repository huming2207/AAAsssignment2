package mazeGenerator;

import maze.Cell;
import maze.Maze;
import maze.Wall;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class RecursiveBacktrackerGenerator implements MazeGenerator
{

    @Override
    public void generateMaze(Maze maze)
    {
        initDfs(maze);
    } // end of generateMaze()


    /**
     *
     * DFS initiator
     *
     * @param maze The maze
     */
    private void initDfs(Maze maze)
    {
        // Declare a DFS marked list and marked the initial values as false.
        boolean[][] markedList = new boolean[maze.sizeR][maze.sizeC];

        // Fire the hole!
        runDfs(maze.entrance, markedList);
    }

    /**
     * Perform DFS and traverse the whole map from a certain root cell
     *
     * @param rootShell The root shell
     * @param markedList The marked list bool array
     */
    private void runDfs(Cell rootShell, boolean[][] markedList)
    {
        // Mark the list and rebuild the wall
        markedList[rootShell.r][rootShell.c] = true;
        rebuildWall(rootShell);

        // Iterate the neighbor shell
        for(Cell currentCell : rootShell.neigh)
        {
            // Detect if it has been marked, if it does, skip to the next one
            if(!markedList[currentCell.r - 1][currentCell.c - 1])
            {
                runDfs(currentCell, markedList);
            }
        }

    }

    /**
     *
     *  Build the new wall!
     *
     * @param cell The cell need to be rebuild the wall
     */
    private void rebuildWall(Cell cell)
    {
        // Grab a random index
        int randomIndex = ThreadLocalRandom.current().nextInt(0, cell.wall.length);

        // If the wall is null, pick again.
        if(cell.wall[randomIndex] != null)
        {
            // Set the wall present value to false
            cell.wall[randomIndex].present = false;
        }
        else
        {
            rebuildWall(cell); // TODO: StackOverflow here!!!
        }
    }


} // end of class RecursiveBacktrackerGenerator
