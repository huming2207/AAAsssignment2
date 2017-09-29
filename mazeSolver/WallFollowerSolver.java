package mazeSolver;

import maze.Cell;
import maze.Maze;
import maze.Wall;

import java.util.ArrayList;


/**
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver
{
    private ArrayList<Cell> cellList;
    private boolean[][] markedList;

    public WallFollowerSolver()
    {
        cellList = new ArrayList<>();
    }

    private void followWall(Maze maze, Cell nextCell)
    {

    }

    @Override
    public void solveMaze(Maze maze)
    {
        // TODO Auto-generated method stub

    } // end of solveMaze()


    @Override
    public boolean isSolved()
    {
        // TODO Auto-generated method stub
        return false;
    } // end if isSolved()


    @Override
    public int cellsExplored()
    {
        // TODO Auto-generated method stub
        return 0;
    } // end of cellsExplored()

} // end of class WallFollowerSolver
