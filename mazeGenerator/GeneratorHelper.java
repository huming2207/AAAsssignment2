package mazeGenerator;

import maze.Cell;
import maze.Maze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Some miscellaneous methods to assist the generators
 *
 * @author Ming Hu s3554025
 */
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
            // Trap for young players: do not compare the cells directly (that's the reference)
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
     * @param cell A certain cell
     * @param neighborCell A possible neighbor cell
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
        // Declare the random cell positions
        // Since the hex maze has a wider Y axis, here we need to follow the assignment spec to declare a wider one.
        // It should also support the normal mazes btw.
        int randomPosX = ThreadLocalRandom.current().nextInt(0, maze.sizeR);
        int randomPosY = ThreadLocalRandom.current().nextInt(0, maze.sizeC + (maze.sizeR + 1) / 2);

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

    /**
     *
     * Pick a cell randomly from a cell set
     *
     * @param set a set of cells
     * @return a random cell
     */
    protected static Cell pickRandomCellFromSet(HashSet<Cell> set)
    {
        int randomNeighborCellIndex = ThreadLocalRandom.current().nextInt(0, set.size());
        int index = 0;

        for(Cell randomCell : set)
        {
            if(index == randomNeighborCellIndex)
            {
                if(randomCell == null)
                {
                    return pickRandomCellFromSet(set);
                }
                else
                {
                    return randomCell;
                }
            }

            index++;
        }

        return null;
    }

    /**
     * Pick the last cell (i.e. the latest cell) from a set
     * @param set A certain set
     * @return
     */
    protected static Cell pickLastCellFromSet(HashSet<Cell> set)
    {
        Cell result = null;
        Iterator setIterator = set.iterator();

        while(setIterator.hasNext())
        {
            result = (Cell)setIterator.next();
        }

        return result;
    }


    /**
     * Run BFS to gather all the tunnels and put it back to the maze:
     *  Step 1. Run BFS traverse
     *  Step 2. When a non-null, not marked tunnel
     *
     * I know I can just write a config file reader to read the file (and it should be faster if I did)...
     * But I just want to practise using BFS lol
     *
     * @param maze A certain maze
     * @return
     */
    protected static ArrayList<int[]> collectAllTunnelPositions(Maze maze)
    {
        // Declare something
        Cell baseCell = maze.entrance;
        LinkedList<Cell> cellQueue = new LinkedList<>();
        ArrayList<int[]> tunnelPositionList = new ArrayList<>();
        boolean[][] markedList = new boolean[maze.sizeR][((maze.sizeC + 1) / 2) + maze.sizeC];
        boolean[][] markedTunnelList = new boolean[maze.sizeR][((maze.sizeC + 1) / 2) + maze.sizeC];

        cellQueue.add(baseCell);

        // Also need to "blacklist" the base cell (otherwise it always return true)
        markedList[baseCell.r][baseCell.c] = true;

        while(!cellQueue.isEmpty())
        {
            // Pop a cell from the queue
            Cell currentCell = cellQueue.poll();

            for(Cell cell : currentCell.neigh)
            {
                if(cell == null) { continue; } // skip null cells

                if(!markedList[cell.r][cell.c])
                {
                    // Mark the cell
                    markedList[cell.r][cell.c] = true;

                    // Add to queue
                    cellQueue.add(cell);

                    // If this cell has a tunnel, mark it.
                    if(cell.tunnelTo != null
                            && !markedTunnelList[cell.r][cell.c])
                    {
                        // Mark the tunnel, so only one tunnel will be marked (in one direction)
                        markedTunnelList[cell.tunnelTo.r][cell.tunnelTo.c] = true;

                        // Add it to the list
                        tunnelPositionList.add(new int[]{
                                cell.r,
                                cell.c,
                                cell.tunnelTo.r,
                                cell.tunnelTo.c
                        });
                    }
                }
            }
        }

        return tunnelPositionList;

    }



}
