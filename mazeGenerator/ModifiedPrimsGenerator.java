package mazeGenerator;

import maze.Cell;
import maze.Maze;

import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class ModifiedPrimsGenerator implements MazeGenerator
{

    /**
     * The set Z in assignment spec
     */
    private HashSet<Cell> setZ;

    /**
     * The set F in assignment spec
     */
    private HashSet<Cell> setF;

    public ModifiedPrimsGenerator()
    {
        setZ = new HashSet<>();
        setF = new HashSet<>();
    }

    @Override
    public void generateMaze(Maze maze)
    {
        initPrim(maze);

    } // end of generateMaze()


    /**
     * Includes:
     *  Step 1.1: Pick a random starting cell
     *
     * @param maze
     */
    private void initPrim(Maze maze)
    {

        // Fire in the hole!
        runPrim(GeneratorHelper.getRandomCellFromMaze(maze), maze.sizeC * maze.sizeR);
    }

    /**
     *
     * Recursively do the Prim's algorithm
     *
     * Includes:
     *  Step 1.2: add the random cell to set Z;
     *  Step 1.3: Put all neighbouring cells of starting cell into the frontier set F;
     *  Step 2.1: Randomly select a cell c from the frontier set and remove it from F;
     *  Step 2.2: Randomly select a cell b that is in Z and adjacent to the cell c.
     *  Step 2.3: Carve a path between c and b.
     *  Step 3: (Repeat 1.2/1.3)
     *  Step 4: Repeat until Z includes every cell in the maze.
     *
     * @param rootCell The root cell (Cell "c" in the assignment spec)
     * @param sizeOfMaze Size of the maze (row * column)
     */
    private void runPrim(Cell rootCell, int sizeOfMaze)
    {
        // Step 1.2 Add the root cell to original cell list
        setZ.add(rootCell);

        // Step 1.3 Add all neighbor cell of the root cell to the neighbor cell list
        Collections.addAll(setF, rootCell.neigh);

        // Step 2.1 Randomly select a cell "c" from the frontier set "F" and remove from it.
        Cell cellC = pickRandomCellFromSet(setF);
        while(cellC == null)
        {
            cellC = pickRandomCellFromSet(setF);
        }

        // Step 2.2 Randomly select a cell "b" that is in Z and adjacent to the cell "c"
        Cell cellB = pickRandomCellFromSet(setZ);
        while(!GeneratorHelper.isNeighbor(cellB, cellC))
        {
            cellB = pickRandomCellFromSet((setZ));
        }

        // Step 2.3 Carve a path between Cell "b" and "c"
        if(!setZ.contains(cellC))
        {
            GeneratorHelper.rebuildWall(cellB, cellC);
        }
        else
        {
            return;
        }

        // Step 3/4
        while(setZ.size() < sizeOfMaze)
        {
            runPrim(cellC, sizeOfMaze);
        }
    }

    /**
     *
     * Pick a cell randomly from a cell set
     *
     * @param set a set of cells
     * @return a random cell
     */
    private Cell pickRandomCellFromSet(HashSet<Cell> set)
    {
        int randomNeighborCellIndex = ThreadLocalRandom.current().nextInt(0, set.size());
        int index = 0;

        for(Cell randomCell : set)
        {
            if(index == randomNeighborCellIndex)
            {
                return randomCell;
            }

            index++;
        }

        return null;
    }
} // end of class ModifiedPrimsGenerator
