package mazeGenerator;

import com.sun.tools.javah.Gen;
import maze.Cell;
import maze.Maze;
import maze.Wall;

import java.util.ArrayList;
import java.util.Arrays;
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
     * @param maze The maze
     */
    private void initDfs(Maze maze)
    {
        // Declare a DFS marked list and marked the initial values as false.
        boolean[][] markedList;

        // Detect the maze type
        switch(maze.type)
        {
            case Maze.NORMAL:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC];
                break;
            }

            case Maze.TUNNEL:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC];
                break;
            }

            case Maze.HEX:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
                break;
            }

            default:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC];
                break;
            }
        }

        // Randomly pick a Cell from the maze map.
        int randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeC);
        int randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
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
