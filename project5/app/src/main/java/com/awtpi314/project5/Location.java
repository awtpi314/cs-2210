package com.awtpi314.project5;

/**
 * <h3>Location</h3>
 * 
 * This class holds two location objects which will tell where the point is and
 * where it was discoverd from. The {@link SquareType} object is an enum which
 * will tell what type of point it is in the maze. Lastly the distance from
 * start is useful for making the post-solve logic much cleaner.
 * 
 * @author Alexander Taylor
 * @since 11/1/2023
 * @version 1.0
 */
public class Location {
  /**
   * The current position in the maze
   */
  private Coordinate position;
  /**
   * The point that discovered this point in the maze
   */
  private Coordinate lastPoint;
  /**
   * The type of point in the maze
   */
  private SquareType type;
  /**
   * Integer distance from the start in the least number of hops
   */
  private int distFromStart;

  /**
   * Default consturctor used for initializing the array.
   */
  public Location() {
    this(new Coordinate(), new Coordinate(), SquareType.OPEN, 0);
  }

  /**
   * Constructor with all parameters. This is used once we get user input and know
   * what the maze looks like.
   * 
   * @param position      The x-y position of this point in the maze
   * @param lastPoint     The x-y position of the point that discovered this one
   * @param type          What the square should be seen as in the maze
   * @param distFromStart A distance from the start in the least number of hops
   */
  public Location(Coordinate position, Coordinate lastPoint, SquareType type, int distFromStart) {
    this.position = position;
    this.lastPoint = lastPoint;
    this.type = type;
    this.distFromStart = distFromStart;
  }

  /**
   * Getter for the position of this point
   * 
   * @return the x-y position of this point
   */
  public Coordinate getPosition() {
    return position;
  }

  /**
   * Getter for the position of the point that discovered this one.
   * 
   * @return the x-y position of the last point
   */
  public Coordinate getLastPoint() {
    return lastPoint;
  }

  /**
   * Getter for the type of point.
   * 
   * @return the type of this point
   */
  public SquareType getType() {
    return type;
  }

  /**
   * Getter for the distance from the start
   * 
   * @return the distance from the start of the maze in the least number of hops
   */
  public int getDistFromStart() {
    return distFromStart;
  }

  /**
   * Setter for the position in the array.
   * 
   * @param position the new position in the array
   */
  public void setPosition(Coordinate position) {
    this.position = position;
  }

  /**
   * Setter for the position of the point that discovered this one.
   * 
   * @param lastPoint the position of the point that discovered the current point
   */
  public void setLastPoint(Coordinate lastPoint) {
    this.lastPoint = lastPoint;
  }

  /**
   * Setter for the type of square in the maze
   * 
   * @param type the new type of the square
   */
  public void setType(SquareType type) {
    this.type = type;
  }

  /**
   * Setter for the distance from the start
   * 
   * @param distFromStart the new distance from the start in the fewest hops
   */
  public void setDistFromStart(int distFromStart) {
    this.distFromStart = distFromStart;
  }

  /**
   * Override for the toString method. This makes printing the maze a lot easier.
   * 
   * @return the display value of the location
   */
  @Override
  public String toString() {
    return type.display;
  }

  /**
   * <h3>SquareType</h3>
   * 
   * The SquareType enum is used to determine what we should do with the squares
   * in the array.
   */
  public static enum SquareType {
    WALL("X"),
    OPEN("."),
    START("S"),
    FINISH("T"),
    FOUND("F");

    /**
     * How the SquareType is displayed in user input
     */
    public final String display;

    /**
     * Private constructor for making an enum
     * 
     * @param display the value that is displayed
     */
    private SquareType(String display) {
      this.display = display;
    }

    /**
     * Get the enum representation of the string passed
     * 
     * @param display the string to find in the enum
     * @return the value of the enum corresponding to the string passed
     */
    public static SquareType valueOfDisplay(String display) {
      for (SquareType e : values()) {
        if (e.display.equals(display)) {
          return e;
        }
      }

      return null;
    }
  }
}