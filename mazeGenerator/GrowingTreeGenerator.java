package mazeGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator
{
    // Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"

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
        runGrowingTree(GeneratorHelper.getRandomCellFromMaze(maze), markedList);
    }

    /**
     *
     * Includes:
     *  Step 1. Pick a random starting cell and add it to set Z
     *  Step 2.1 Using a particular strategy (use DFS, newest cell) select a cell b from Z
     *  Step 2.2.1 randomly select a neighbour first!
     *  Step 2.2.2 If cell b has unvisited neighbouring cells...
     *  Step 2.3 ...carve a path to it, and add the selected neighbour to set Z.
     *  Step 3 Repeat from 1 to 2.3
     *
     * @param rootCell
     */
    private void runGrowingTree(Cell rootCell, boolean[][] markedList)
    {
        // Step 1. Put the starting cell to set Z
        setZ.add(rootCell);




    }


}
