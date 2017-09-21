package mazeGenerator;

import com.sun.tools.javah.Gen;
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
        initPrim(maze);

    } // end of generateMaze()



    private void initPrim(Maze maze)
    {
        runPrim(maze.entrance, maze.sizeC * maze.sizeR);
    }

    /**
     *
     * Recursively do the Prim's algorithm
     *
     * @param rootCell The root cell (Cell "c" in the assignment spec)
     * @param sizeOfMaze Size of the maze (row * column)
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
                GeneratorHelper.rebuildWall(cell, randomCell);

                // Repeat until set Z includes every cell in the maze
                if(sizeOfMaze < originCells.size())
                {
                    runPrim(rootCell, sizeOfMaze);
                }
            }
        }

    }




} // end of class ModifiedPrimsGenerator
