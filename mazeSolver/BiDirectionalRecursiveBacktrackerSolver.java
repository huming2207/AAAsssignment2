package mazeSolver;

import maze.Cell;
import maze.Maze;
import maze.Wall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver
{
    private boolean[][] entranceSideMarkedList;
    private boolean[][] exitSideMarkedList;
    private boolean solveStatus;
    private int exploreCounter;

    private Stack<Cell> entranceSideStack;
    private Stack<Cell> exitSideStack;

    public BiDirectionalRecursiveBacktrackerSolver()
    {
        // Mark the status
        solveStatus = true;

        // Set the counter to 0
        exploreCounter = 0;

        // Init stack
        entranceSideStack = new Stack<>();
        exitSideStack = new Stack<>();
    }

    private void initBiDfs(Maze maze)
    {
        // Initialize the list, set all to false
        entranceSideMarkedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];
        exitSideMarkedList = new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2];

        runBiDfs(maze);
    }

    /**
     *
     * When starting at the entrance of the maze, the solver will initially randomly choose an adjacent
     * unvisited cell. It moves to that cell (which is the current front for DFS starting at the entrance),
     * update its visit status, then selects another random unvisited neighbour. It continues this process
     * until it hits a deadend (no unvisited neighbours), then it backtracks to a previous cell that has an
     * 3 unvisited neighbour. Randomly select one of the unvisited neighbour and repeat process until we
     * reached the exit (this is always possible for a perfect maze). The path from entrance to exit is the solution.
     *
     * When the two DFS fronts first meet, the path from the entrance to the point they meet, and
     * the path from the exit to the meeting point forms the two halves of a shortest path (in terms of cell visited)
     * from entrance to exit. Combine these paths to get the final path solution.
     *
     * @param maze
     */
    private void runBiDfs(Maze maze)
    {
        entranceSideStack.push(maze.entrance);
        exitSideStack.push(maze.exit);

        while(!entranceSideStack.empty() && !exitSideStack.empty())
        {
            Cell currentEntranceSideCell = entranceSideStack.pop();
            Cell currentExitSideCell = exitSideStack.pop();

            // Auto add stack for entrance side
            dfsToStack(currentEntranceSideCell, entranceSideStack, entranceSideMarkedList, maze);

            // Auto add stack for exit side
            dfsToStack(currentExitSideCell, exitSideStack, exitSideMarkedList, maze);

            // Stop if matches
            if(currentEntranceSideCell.r == currentExitSideCell.r
                    && currentEntranceSideCell.c == currentExitSideCell.c)
            {
                solveStatus = true;
                break;
            }

        }
    }

    private void dfsToStack(Cell targetCell, Stack<Cell> targetStack, boolean[][] markedList, Maze maze)
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
