package com.awtpi314.project5;

/**
 * <h3>Coordinate</h3>
 * 
 * The coordinate class stores the x-y position of a point in the maze
 * 
 * @author Alexander Taylor
 * @since 11/1/2023
 * @version 1.0
 */
public class Coordinate {
  /**
   * The x position in the maze
   */
  private int x;
  /**
   * The y position in the maze
   */
  private int y;

  /**
   * Default constructor
   */
  public Coordinate() {
    this(0, 0);
  }

  /**
   * All parameter constructor
   * 
   * @param x the x-position
   * @param y the y-position
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Getter for the x position
   * 
   * @return the integer x position from the top left
   */
  public int getX() {
    return x;
  }

  /**
   * Getter for the y position
   * 
   * @return the positive integer y position from the top left
   */
  public int getY() {
    return y;
  }

  /**
   * Setter for the x position
   * 
   * @param x the new position from the top left
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Setter for the y position
   * 
   * @param y the new positive y position from the top left
   */
  public void setY(int y) {
    this.y = y;
  }
}
