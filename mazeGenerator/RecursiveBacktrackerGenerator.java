package mazeGenerator;

import maze.Cell;
import maze.Maze;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collections;
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
     * Includes:
     *  Step 1.1: Randomly select a cell from the maze
     *
     * @param maze The maze
     */
    private void initDfs(Maze maze)
    {

        // Declare a DFS marked list and marked the initial values as false.
        boolean[][] markedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

        // Pick a cell with the random index from the map
        runDfs(GeneratorHelper.getRandomCellFromMaze(maze), markedList);

        // Use the original isPerfect() method to detect if this maze is perfect
        // The loop may happens with ~10% possibility
        while(!maze.isPerfect())
        {
            System.out.println("[WARNING] Looks like it's not perfect, gonna retry lol");
            if(maze.type != Maze.TUNNEL)
            {
                // Forget about the size of the tunnel, it doesn't actually take any effects lol.
                maze.initMaze(maze.sizeR, maze.sizeC,
                        maze.entrance.r, maze.entrance.c,
                        maze.exit.r, maze.exit.c,
                        new ArrayList<>(maze.sizeTunnel));
            }
            else
            {
                // If it's a maze, do a collection for the tunnels of the original maze.
                ArrayList<int[]> tunnelList = GeneratorHelper.collectAllTunnelPositions(maze);
                maze.initMaze(maze.sizeR, maze.sizeC,
                        maze.entrance.r, maze.entrance.c,
                        maze.exit.r, maze.exit.c,
                        tunnelList);
            }

            initDfs(maze);
        }


    }

    /**
     * Perform DFS and traverse the whole map from a certain root cell
     *
     * Includes:
     *  All other steps (Just a DFS, no need to explain...)
     *
     * @param rootCell The root cell
     * @param markedList The marked list bool array
     */
    private void runDfs(Cell rootCell, boolean[][] markedList)
    {
        // Mark the list and rebuild the wall
        markedList[rootCell.r][rootCell.c] = true;

        // Randomize the cells
        ArrayList<Cell> shuffledCell = new ArrayList<>();
        Collections.addAll(shuffledCell, rootCell.neigh);

        Collections.shuffle(shuffledCell);

        // Iterate the neighbor shell
        for(Cell currentCell : shuffledCell)
        {
            // Detect if it has been marked, if it does, skip to the next one
            if(currentCell != null && !markedList[currentCell.r][currentCell.c])
            {
                GeneratorHelper.rebuildWall(rootCell, currentCell);

                // "Blacklist" the tunnel exit cell to prevent a loop
                // Sounds ironic, but it works.
                if(currentCell.tunnelTo != null)
                {
                    markedList[currentCell.tunnelTo.r][currentCell.tunnelTo.c] = true;
                }

                runDfs(currentCell,  markedList);
            }
        }
    }


} // end of class RecursiveBacktrackerGenerator
