package BackEnd;
/**
 * This class is a coordinate class which will hold an integer coordinate pair X and Y
 * for a particular object or item.
 * @author Atif Ishaq and Joshua Oladitan.
 * @version 1.0
 *
 */

public class Coordinate {
    /**
     * These are variables to represent the x and y coordinate pairs.
     */
    private int x;
    private int y;

    /**
     * Constructor takes in a coordinate with x and y.
     */
    public Coordinate(int xCoor, int yCoor){
        x = xCoor;
        y = yCoor;
    }

    /**
     * This method gets the x Coordinate.
     * @return the x Coordinate.
     */
    public int getX() {
        return this.x;
    }

    /**
     * This method sets the x Coordinate.
     * @param xCoor This parameter would be set as the x Coordinate.
     */
    public void setX(int xCoor){
        this.x = xCoor;
    }

    /**
     * This method gets the y Coordinate.
     * @return the y Coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * THis method sets the y Coordinate.
     * @param yCoor This parameter would be set as the y Coordinate.
     */
    public void setY(int yCoor){
        y = yCoor;
    }



}
