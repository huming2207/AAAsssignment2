package mazeGenerator;

import maze.Cell;

public class GeneratorHelper
{
    /**
     *
     *  Build the new wall!
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
}
