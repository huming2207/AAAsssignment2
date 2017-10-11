package mazeSolver;

import maze.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DirectionHelper
{
    private ArrayList<Integer> relativeDirection;
    private boolean isHex;
    private ArrayList<Integer> squareCellOffset;


    public DirectionHelper(boolean isHex)
    {
        relativeDirection = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
        squareCellOffset = new ArrayList<>(Arrays.asList(2, 1, 2));

        this.isHex = isHex;
    }

    /**
     * Turn CLOCKWISE (EAST), apply the offset with -1
     *
     * Meanwhijle, square cells here only have directions with index 0, 2, 3, 5 (E, N, W, S)
     * so, the offset is -2, -1, -2
     *
     */
    protected void turnEast()
    {
        if(isHex)
        {
            // REMEMBER TO GET THE NEGATIVE NUMBER HERE!!!
            Collections.rotate(relativeDirection, -squareCellOffset.get(0));
            Collections.rotate(squareCellOffset, -1);
        }
        else
        {
            Collections.rotate(relativeDirection, -1);
        }
    }

    /**
     * Turn ANTI-CLOCKWISE (WEST), apply the offset with +1
     *
     * Meanwhijle, square cells here only have directions with index 0, 2, 3, 5 (E, N, W, S)
     * so, the offset is +2, +1, +2
     *
     */
    protected void turnWest()
    {
        if(isHex)
        {
            // REMEMBER TO GET THE POSITIVE NUMBER HERE!!!
            Collections.rotate(relativeDirection, squareCellOffset.get(0));
            Collections.rotate(squareCellOffset, 1);
        }
        else
        {
            Collections.rotate(relativeDirection, 1);
        }
    }

    /**
     *
     * Negative for clockwise turns, Positive for anti-clockwise turns
     *
     * @param directionOffset Turning offset
     */
    protected void turnDirection(int directionOffset)
    {
        Collections.rotate(relativeDirection, directionOffset);
    }

    protected int getRelativeDirection(int directionIndex)
    {
        return relativeDirection.get(directionIndex);
    }


}
