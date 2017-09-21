package mazeGenerator;

import maze.Cell;
import maze.Maze;

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
        boolean[][] markedList;

        int randomPosX, randomPosY;

        // Detect the maze type
        switch(maze.type)
        {
            case Maze.NORMAL:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC];

                // Randomly pick a Cell from the maze map.
                randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
                randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC);
                break;
            }

            case Maze.TUNNEL:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC];

                // Randomly pick a Cell from the maze map.
                randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
                randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC);
                break;
            }

            case Maze.HEX:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

                // Randomly pick a Cell from the maze map.
                randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
                randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC + (maze.sizeR + 1) / 2);
                break;
            }

            // Won't happen, just shut up the compiler
            default:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC];

                randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
                randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC);
                break;
            }
        }

        // Pick a cell with the random index from the map
        Cell randomCell = maze.map[randomPosX][randomPosY];

        if(randomCell == null)
        {
            initDfs(maze);
        }
        else
        {
            // Fire in the hole!
            runDfs(randomCell, markedList);
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
        Collections.addAll(shuffledCell, rootCell.neigh.clone());
        Collections.shuffle(shuffledCell);

        // Iterate the neighbor shell
        for(Cell currentCell : shuffledCell)
        {
            // Detect if it has been marked, if it does, skip to the next one
            if(currentCell != null && !markedList[currentCell.r][currentCell.c])
            {
                GeneratorHelper.rebuildWall(rootCell, currentCell);
                runDfs(currentCell,  markedList);
            }
        }
    }


} // end of class RecursiveBacktrackerGenerator
