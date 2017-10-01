package mazeSolver;

import maze.Cell;
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
}
