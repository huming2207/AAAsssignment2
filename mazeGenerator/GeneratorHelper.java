package mazeGenerator;

import maze.Cell;

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
}
