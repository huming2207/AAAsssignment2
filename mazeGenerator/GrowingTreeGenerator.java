package mazeGenerator;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.sun.org.apache.xpath.internal.operations.Bool;
import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator
{
    // Growing tree maze generator. As it is very general,
    // here we implement as "usually pick the most recent cell, but occasionally pick a random cell"

    double threshold = 0.1;

    private HashSet<Cell> setZ;

    public GrowingTreeGenerator()
    {
        setZ = new HashSet<>();
    }

    @Override
    public void generateMaze(Maze maze)
    {
        initGrowingTree(maze);
    }


    private void initGrowingTree(Maze maze)
    {
        // Declare a DFS marked list and marked the initial values as false.
        boolean[][] markedList = GeneratorHelper.getMarkedList(maze);

        // Fire in the hole!
        runGrowingTree(maze, GeneratorHelper.getRandomCellFromMaze(maze), markedList);
    }

    /**
     *
     * Includes:
     *  1. Pick a random starting cell and add it to set Z (initially Z is empty, after addition it contains just the starting cell).
     *  2.1. Using a particular strategy to select a cell b from Z.
     *  2.2. If cell b has unvisited neighbouring cells, randomly select a neighbour, carve a path to it, and add the selected neighbour to set Z.
     *  2.3. If b has no unvisited neighbours, remove it from Z.
     *  3. Repeat step 2 until Z is empty
     *
     * @param rootCell
     */
    private void runGrowingTree(Maze maze, Cell rootCell, boolean[][] markedList)
    {
        // Step 1. Put the starting cell to set Z
        setZ.add(rootCell);

        // The random opportunity should be 10% here
        Cell cellB;
        boolean randomNeighbor = ThreadLocalRandom
                                    .current()
                                    .nextDouble(0, 1)
                                    <= threshold;

        if(randomNeighbor)
        {
            //  Step 2.1 Using a particular strategy (use Prim's, randomly) select a cell b from Z
            cellB = GeneratorHelper.pickRandomCellFromSet(setZ);
        }
        else
        {
            //  Step 2.1 Using a particular strategy (use DFS, the newest) select a cell b from Z
            cellB = GeneratorHelper.pickLastCellFromSet(setZ);
        }

        // Step 2.2 If cell b has unvisited neighbouring cells, randomly select a neighbour
        ArrayList<Cell> shuffledCellList = new ArrayList<>();
        Collections.addAll(shuffledCellList, cellB.neigh);
        Collections.shuffle(shuffledCellList);

        Cell neighborCell = null;
        int neighborIndex = 0;
        while(neighborCell == null && neighborIndex <= 5)
        {
            // Condition:
            //   1. the cell is not null;
            //   2. the cell is not visited before
            if(shuffledCellList.get(neighborIndex) != null
                    && !markedList[shuffledCellList.get(neighborIndex).r][shuffledCellList.get(neighborIndex).c])
            {
                // Assign the neighbor cell
                neighborCell = shuffledCellList.get(neighborIndex);

                // If there will be a loop, try another neighbor
                while(GeneratorHelper.isLoop(maze, cellB, neighborCell))
                {
                    neighborCell = shuffledCellList.get(neighborIndex);
                }

                // Mark as visited
                markedList[shuffledCellList.get(neighborIndex).r][shuffledCellList.get(neighborIndex).c] = true;

                // Carve the wall
                GeneratorHelper.rebuildWall(cellB, neighborCell);

                // Loop until the set Z is empty
                while(setZ.size() > 0)
                {
                    // Step 3. Recursion (Including add to set "Z")
                    runGrowingTree(maze, neighborCell, markedList);
                }

            }

            // Increase the index
            neighborIndex++;
        }

        // Step 2.3. If b has no unvisited neighbours, remove it from Z.
        if(neighborCell == null)
        {
            setZ.remove(cellB);
        }
    }

}
