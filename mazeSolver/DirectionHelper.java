package mazeSolver;

import maze.Maze;

import java.util.ArrayList;
import java.util.Arrays;

public class DirectionHelper
{
    private ArrayList<Integer> relativeDirection;
    private int heading;
    private boolean isHex;


    public DirectionHelper(boolean isHex)
    {
        if(isHex)
        {
            relativeDirection = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        }
        else
        {
            relativeDirection = new ArrayList<>(Arrays.asList(0, null, 2, 3, null, 5));
        }

        this.isHex = isHex;
        this.heading = Maze.NORTH;
    }


    /**
     *
     * Get the right direction.
     *
     * For example (Square cell)
     *  Heading: North 2 -> Right side: East 0
     *  Heading: East 0  -> Right side: South 5
     *  Heading: South 5 -> Right side: West 3
     *  Heading: West 3  -> Right side: North 2
     *
     * ...actually it just need to get the previous valid element of the array
     *
     * @return Direction index
     */
    protected int getRightDirection()
    {
        int previousElementIndex = relativeDirection.indexOf(heading) - 1;

        if(previousElementIndex < 0)
        {
            previousElementIndex = 5;
        }

        // e.g. 2 -> 0 or 5 -> 3
        if(relativeDirection.get(previousElementIndex) == null)
        {
            previousElementIndex -= 1;
        }

        return previousElementIndex;
    }

    /**
     *
     * Update heading, e.g. Maze.NORTH
     *
     * @param headingDirection new heading direction
     */
    protected void updateHeading(int headingDirection)
    {
        this.heading = headingDirection;
    }

}
