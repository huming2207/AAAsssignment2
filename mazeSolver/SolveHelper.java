package mazeSolver;

import maze.Cell;
import maze.Maze;
import maze.Wall;

public class SolveHelper
{
    /**
     * If the cell is wrapped by a wall, return true.
     * @param cell The test cell
     * @return true if wrapped
     */
    protected static boolean isWrappedByWall(Cell cell)
    {
        for(Wall wall : cell.wall)
        {
            if(wall != null && wall.present)
            {
                return true;
            }

        }

        return false;
    }

    /**
     *
     * Detect if a certain traverse method can go to another neighbor cell from a certain cell.
     * The condition must be:
     *  1. Both cells and the walls between them are not null
     *  2. The position matches
     *  3. The wall does not present
     *
     * @param cell
     * @param neighborCell
     * @return
     */
    protected static boolean canGoThru(Cell cell, Cell neighborCell)
    {
        for(int cellIndex = 0; cellIndex < Maze.NUM_DIR; cellIndex++)
        {
            if(cell != null
                    && cell.neigh[cellIndex] != null
                    && neighborCell != null
                    && cell.wall[cellIndex] != null
                    && cell.neigh[cellIndex].r == neighborCell.r
                    && cell.neigh[cellIndex].c == neighborCell.c
                    && !cell.wall[cellIndex].present)
            {
                return true;
            }
        }

        // Return false if they are not even neighbor
        return false;
    }
}
