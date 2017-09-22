package mazeGenerator;

import maze.Cell;
import maze.Maze;

import java.util.concurrent.ThreadLocalRandom;

public class GeneratorHelper
{
    /**
     *
     *  Build the new wall!
     *
     *  Destroy the wall only if:
     *      1. matches with rows and columns
     *      2. not null
     *
     * @param cell The root cell need to be rebuild the wall
     * @param neighborCell The neighbor cell of the root cell
     */
    protected static void rebuildWall(Cell cell, Cell neighborCell)
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

    /**
     * Detect if two cells are neighbors
     *
     * @param cell
     * @param neighborCell
     * @return true if two cells are neighbors
     */
    protected static boolean isNeighbor(Cell cell, Cell neighborCell)
    {
        if(cell == null || neighborCell == null) { return false; }

        for(int cellIndex = 0; cellIndex <= 5; cellIndex++)
        {
            if(neighborCell.neigh[cellIndex] != null
                    && cell.c == neighborCell.neigh[cellIndex].c
                    && cell.r == neighborCell.neigh[cellIndex].r)
            {
                return true;
            }

            if(cell.neigh[cellIndex] != null
                    && cell.neigh[cellIndex].c == neighborCell.c
                    && cell.neigh[cellIndex].r == neighborCell.r)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Grab a random cell from a maze
     *
     * @param maze The maze
     * @return a random cell
     */
    protected static Cell getRandomCellFromMaze(Maze maze)
    {
        int randomPosX, randomPosY;

        // Detect the maze type
        switch(maze.type)
        {
            case Maze.NORMAL:
            {
                // Randomly pick a Cell from the maze map.
                randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
                randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC);
                break;
            }

            case Maze.TUNNEL:
            {
                // Randomly pick a Cell from the maze map.
                randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
                randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC);
                break;
            }

            case Maze.HEX:
            {
                // Randomly pick a Cell from the maze map.
                randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
                randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC + (maze.sizeR + 1) / 2);
                break;
            }

            // Won't happen, just shut up the compiler
            default:
            {
                randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
                randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC);
                break;
            }
        }

        // Pick a cell with the random index from the map
        Cell randomCell = maze.map[randomPosX][randomPosY];

        if(randomCell == null)
        {
            return getRandomCellFromMaze(maze);
        }
        else
        {
            return randomCell;
        }

    }

    protected static boolean[][] getMarkedList(Maze maze)
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

            // Won't happen, just shut up the compiler
            default:
            {
                markedList = new boolean[maze.sizeR][maze.sizeC];
                break;
            }
        }

        return markedList;
    }
}
