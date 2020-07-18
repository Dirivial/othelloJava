import java.util.Objects;

/**
 * The class Position is for creating a position like in a coordination-system.
 *
 * @author c19aky
 *
 */

public class Position {
    private int x;
    private int y;

    /*
     * Constructor for  the class Position
     * @ x: x-value for the position
     * @ y: y-value for the position
     *
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     * GetY()
     * Get y value of a position.
     * Returns: y-value.
     */
    public int getY() {
        return y;
    }

    /*
     * getX()
     * Get x-value of a position.
     * Returns: x-value.
     */
    public int getX() {
        return x;
    }

    /*
     * getPosToSouth()
     *
     * Creates a new position with the same x-value but y-value + 1.
     * Returns: The new position.
     */
    public Position getPosToSouth() {
        Position p2 = new Position(0,0);
        p2.y = y+1;
        p2.x = x;
        return p2;
    }

    /*
     * getPosToNorth()
     *
     * Creates a new position with the same x-value but y-value - 1.
     * Returns: The new position.
     */
    public Position getPosToNorth() {
        Position p2 = new Position(0,0);
        p2.y = y-1;
        p2.x = x;
        return p2;
    }

    /*
     * getPosToWest()
     *
     * Creates a new position with the same y-value but x-value - 1.
     * Returns: The new position.
     */
    public Position getPosToWest() {
        Position p2 = new Position(0,0);
        p2.y = y;
        p2.x = x-1;
        return p2;
    }

    /*
     * getPosToEast()
     *
     * Creates a new position with the same y-value but x-value + 1.
     * Returns: The new position.
     */
    public Position getPosToEast() {
        Position p2 = new Position(0,0);
        p2.y = y;
        p2.x = x+1;
        return p2;
    }

    public Position getPosToNorthEast() {
        Position p2 = new Position(0,0);
        p2.y = y-1;
        p2.x = x+1;
        return p2;
    }

    public Position getPosToNorthWest() {
        Position p2 = new Position(0,0);
        p2.y = y-1;
        p2.x = x-1;
        return p2;
    }
    public Position getPosToSouthEast() {
        Position p2 = new Position(0,0);
        p2.y = y+1;
        p2.x = x+1;
        return p2;
    }
    public Position getPosToSouthWest() {
        Position p2 = new Position(0,0);
        p2.y = y+1;
        p2.x = x-1;
        return p2;
    }

    /*
     * equals()
     * @ Object o: Object to test if it is equal.
     *
     * Tests if a given object is an instance of se.umu.cs.c19aky.stuff.Position and if it is, whether it has the same values as this se.umu.cs.c19aky.stuff.Position.
     *
     * Returns: True if a given object is a se.umu.cs.c19aky.stuff.Position and has the same x- and y-values as this se.umu.cs.c19aky.stuff.Position.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position p2 = (Position) o;
            return p2.x == this.x && p2.y == this.y;
        } else {
            return false;
        }
    }

    /*
     * hashCode()
     *
     * Returns: This position but hashed.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /*
     * toString()
     *
     * Returns: A String with the position x- and y-values.
     */
    @Override
    public String toString() {
        return "Position{" +
                "x=" + (x) +
                ", y=" + (y) +
                '}';
    }
}

