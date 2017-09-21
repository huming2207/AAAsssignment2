package mazeGenerator;

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
        boolean[][] markedList = new boolean[maze.sizeR][maze.sizeC];

        // Randomly pick a Cell from the maze map.
        int randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeC);
        int randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
        Cell randomCell = maze.map[randomPosX][randomPosY];

        // Fire in the hole!
        runDfs(randomCell, markedList);
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
                rebuildWall(rootCell, currentCell);
                runDfs(currentCell,  markedList);
            }
        }
    }

    /**
     *
     *  Build the new wall!
     *
     * @param cell The root cell need to be rebuild the wall
     */
    private void rebuildWall(Cell cell, Cell neighborCell)
    {
        for(int cellIndex = 0; cellIndex <= 5; cellIndex++)
        {
            if(neighborCell.neigh[cellIndex] != null
                    && cell.c == neighborCell.neigh[cellIndex].c
                    && cell.r == neighborCell.neigh[cellIndex].r)
            {
                neighborCell.wall[cellIndex].present = false;
            }

            if(cell.neigh[cellIndex] != null
                    && cell.neigh[cellIndex].c == neighborCell.c
                    && cell.neigh[cellIndex].r == neighborCell.r)
            {
                cell.wall[cellIndex].present = false;
            }
        }

    }
} // end of class RecursiveBacktrackerGenerator
