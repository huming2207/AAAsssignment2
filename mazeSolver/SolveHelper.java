package mazeSolver;

import maze.Cell;
import maze.Maze;
import maze.Wall;

public class SolveHelper
{
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

    protected static boolean canGoThru(Cell cell, Cell neighborCell)
    {
        for(int cellIndex = 0; cellIndex < Maze.NUM_DIR; cellIndex++)
        {
            if(cell.neigh[cellIndex] != null
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
