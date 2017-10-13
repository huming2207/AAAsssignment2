package mazeSolver;

import maze.Cell;
import maze.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 *
 * @author Ming Hu s3554025
 * @author Yuxuan Cheng s3516930
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver
{
    private boolean[][] entranceMarkedList;
    private boolean[][] exitMarkedList;
    private boolean solveStatus;
    private int exploreCounter;

    private Stack<Cell> entranceStack;
    private Stack<Cell> exitStack;

    public BiDirectionalRecursiveBacktrackerSolver()
    {
        // Mark the status
        solveStatus = false;

        // Set the counter to 0
        exploreCounter = 0;

        // Init stack
        entranceStack = new Stack<>();
        exitStack = new Stack<>();
    }

    /**
     *
     * Bi-DFS initializer
     *
     * @param maze
     */
    private void initBiDfs(Maze maze)
    {
        // Initialize the list, set all to false
        entranceMarkedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
        exitMarkedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

        // Fire in the hole!
        runBiDfs(maze);

    }

    /**
     * Bidirectional DFS runner
     * No recursion this time as it's more convenient (and faster/smaller JVM stack?).
     *
     * @param maze The input maze
     */
    private void runBiDfs(Maze maze)
    {
        // Initialization
        entranceStack.push(maze.entrance);
        exitStack.push(maze.exit);

        while(!entranceStack.empty() && !exitStack.empty())
        {
            // Pop some fresh cells
            Cell entranceCell = entranceStack.pop();
            Cell exitCell = exitStack.pop();

            // Stop if matches:
            //  1. It's the same cell
            //  2. The entrance list has marked the exit cell, or vice-versa.
            if(entranceCell.r == exitCell.r && entranceCell.c == exitCell.c
                    || exitMarkedList[entranceCell.r][entranceCell.c]
                    || entranceMarkedList[exitCell.r][exitCell.c])
            {
                break;
            }

            // Auto add stack for entrance side
            dfsStackPusher(entranceCell, entranceStack, entranceMarkedList, maze);

            // Auto add stack for exit side
            dfsStackPusher(exitCell, exitStack, exitMarkedList, maze);
        }

        // ...winner winner, chicken dinner!
        solveStatus = true;
    }

    /**
     *
     * Bi-DFS stack pusher
     * This method is to reuse the code in both DFS ways
     *
     * @param targetCell The target cell, will iterate and add its neighbor cell
     * @param targetStack The target stack
     * @param markedList Target marked list
     * @param maze The maze
     */
    private void dfsStackPusher(Cell targetCell, Stack<Cell> targetStack, boolean[][] markedList, Maze maze)
    {
        // Exit side part, ignore if marked
        if(!markedList[targetCell.r][targetCell.c])
        {
            // Mark the cell
            markedList[targetCell.r][targetCell.c] = true;

            // Draw the dot
            maze.drawFtPrt(targetCell);

            // Randomize the cells
            ArrayList<Cell> shuffledCell = new ArrayList<>();
            Collections.addAll(shuffledCell, targetCell.neigh);
            Collections.shuffle(shuffledCell);

            // Add the neighbors
            for (Cell cell : shuffledCell)
            {
                if (cell != null && SolveHelper.canGoThru(cell, targetCell))
                {
                    targetStack.push(cell);
                }
            }

            // Increase the counter
            exploreCounter++;
        }
    }

    @Override
    public void solveMaze(Maze maze)
    {
        initBiDfs(maze);

    } // end of solveMaze()


    @Override
    public boolean isSolved()
    {
        return solveStatus;
    } // end if isSolved()


    @Override
    public int cellsExplored()
    {
        return exploreCounter;
    } // end of cellsExplored()

} // end of class BiDirectionalRecursiveBackTrackerSolver
