package BackEnd;

/**
 * This class is a coordinate class which will hold an integer coordinate pair X and Y
 * for a particular object or item.
 *
 * @author Atif Ishaq & Joshua Oladitan.
 * @version 1.0
 */

public class Coordinate {

    /*
     * These are variables to represent the x and y coordinate pairs.
     */
    private int x;
    private int y;

    /**
     * Constructor takes in a coordinate with x and y.
     *
     * @param xCoor the X coordinate
     * @param yCoor the Y coordinate
     */
    public Coordinate(int xCoor, int yCoor) {
        x = xCoor;
        y = yCoor;
    }

    /**
     * This method gets the x Coordinate.
     *
     * @return the x Coordinate.
     */
    public int getX() {
        return this.x;
    }


    /**
     * This method gets the y Coordinate.
     *
     * @return the y Coordinate.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Checks if too coordinates are equal.
     *
     * @param input coordinate to check
     * @return true if showing the same location.
     */
    public boolean equals(Coordinate input) {
        return (x == input.getX()) && (y == input.getY());
    }

    /**
     * This method returns the coordinates x and y to a string.
     *
     * @return A string consisting of the coordinates.
     */
    @Override
    public String toString() {
        return getX() + " " + getY();
    }

    /**
     * In this method were checking if two coordinates are equal of different objects.
     *
     * @param o This is an object which contains coordinates.
     * @return true if the coordinates are equal, else false.
     */
    @Override
    public boolean equals(Object o) {
        return (o instanceof Coordinate) &&
                ((Coordinate) o).getX() == this.getX() &&
                ((Coordinate) o).getY() == this.getY();
    }

    /**
     * Shifts a Coordinate by a given amount
     * does not mutate original object
     *
     * @param x amount to shift right
     * @param y amount to shift down
     * @return a new Coordinate in shifted location.
     */
    public Coordinate shift(int x, int y) {
        return shift(new Coordinate(x, y));
    }

    /**
     * Shifts a Coordinate by a given amount
     * does not mutate original object
     *
     * @param shiftAmount amount to shift right and down
     * @return a new Coordinate in shifted location.
     */
    public Coordinate shift(Coordinate shiftAmount) {
        return new Coordinate(shiftAmount.getX() + x, shiftAmount.getY() + y);
    }
}
