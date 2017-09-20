package mazeGenerator;

import maze.Cell;
import maze.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class ModifiedPrimsGenerator implements MazeGenerator
{

    /**
     * The set Z in assignment spec
     */
    private ArrayList<Cell> originCells;

    /**
     * The set F in assignment spec
     */
    private ArrayList<Cell> neighborCells;

    public ModifiedPrimsGenerator()
    {
        originCells = new ArrayList<>();
        neighborCells = new ArrayList<>();
    }

    @Override
    public void generateMaze(Maze maze)
    {
        // TODO Auto-generated method stub

    } // end of generateMaze()



    private void initPrim(Maze maze)
    {
        runPrim(maze.entrance, maze.sizeC * maze.sizeR);
    }

    /**
     *
     *
     *
     * @param rootCell
     */
    private void runPrim(Cell rootCell, int sizeOfMaze)
    {
        // Add the root cell to original cell list
        originCells.add(rootCell);

        // Add all neighbor cell of the root cell to the neighbor cell list
        Collections.addAll(neighborCells, rootCell.neigh);

        // Randomly select a cell from the frontier set and remove from it.
        // Here randomCell is the Cell "c" in assignment spec
        int randomNeighborCellIndex = ThreadLocalRandom.current().nextInt(0, neighborCells.size());
        Cell randomCell = neighborCells.get(randomNeighborCellIndex);
        neighborCells.remove(randomNeighborCellIndex);

        // Randomly select a cell from the set Z which is the neighbor
        // Here the "cell" is Cell "b" in assignment spec
        for(Cell cell : randomCell.neigh)
        {
            if(originCells.contains(cell))
            {
                rebuildWall(cell, randomCell);

                // Repeat until set Z includes every cell in the maze
                if(sizeOfMaze < originCells.size())
                {
                    runPrim(rootCell, sizeOfMaze);
                }
            }
        }

    }


    /**
     *
     *  Build the new wall!
     *
     * @param rootCell The cell need to be rebuild the wall
     * @param neighborCell The neighbour cell
     */
    private void rebuildWall(Cell rootCell, Cell neighborCell)
    {
        // Remove the root cell's wall first...
        for(int cellIndex = 0; cellIndex < rootCell.neigh.length; cellIndex++)
        {
            if(rootCell.neigh[cellIndex].r == neighborCell.r &&
                    rootCell.neigh[cellIndex].c == neighborCell.c)
            {
                rootCell.wall[cellIndex].present = false;
            }
        }

        // Then remove the neighbor cell's wall
        for(int cellIndex = 0; cellIndex < neighborCell.neigh.length; cellIndex++)
        {
            if(neighborCell.neigh[cellIndex].r == rootCell.r &&
                    neighborCell.neigh[cellIndex].c == rootCell.c)
            {
                neighborCell.wall[cellIndex].present = false;
            }
        }
    }

} // end of class ModifiedPrimsGenerator
