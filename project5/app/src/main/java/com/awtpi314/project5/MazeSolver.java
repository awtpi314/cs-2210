package com.awtpi314.project5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * <h3>MazeSolver</h3>
 * 
 * The MazeSolver class is the main class for this application. It takes input
 * from the user and then solves the maze using a breadth-first search
 * algorithm. Input should be in the form that follows
 * 
 * <pre>
 * 6 6
 *S.X...
 *..X.XT
 *X.X.X.
 *..X.X.
 *X.....
 *XX.X.X
 * </pre>
 * 
 * Where X represents a wall, a "." represents an open space, S represents the
 * start, and T represents the finish. The program will then output the shortest
 * path from the start to finish and the distance in number of hops that is
 * required to get from start to finish
 * 
 * @author Alexander Taylor
 * @since 11/1/2023
 * @version 1.0
 */
public class MazeSolver {
  /**
   * Queue to hold the next steps to take
   */
  private Queue<Location> mazeQueue = new LinkedList<>();
  /**
   * 2D array to hold the current status of the maze
   */
  private Location[][] mazeDetails;

  /**
   * Constructor that takes in a 2D array of the maze's initial state.
   * 
   * @param details the initial state of the maze
   */
  public MazeSolver(Location[][] details) {
    mazeDetails = details;
  }

  /**
   * This method performs a breadth-first search on the maze that the object was
   * constructed with.
   * 
   * @return the shortest path from the start to the finish
   */
  public Location[] breadthFirstSearch() {
    // Set the finish to null so we know if we've actually found a path from start
    // to finish
    Location finish = null;
    // Find the start and add it as the first position in the queue
    mazeQueue.add(findStart());

    // Loop while we have more moves to perform
    while (!mazeQueue.isEmpty()) {
      // Get the current location
      Location current = mazeQueue.remove();

      // Check whether we have found the solution to the maze
      if (current.getType() == Location.SquareType.FINISH) {
        // If we've found it then we should set finish so that we know that we found the
        // solution
        finish = current;
        break;
      }

      // Search for moves using the current location
      searchMoves(current).forEach((Location l) -> {
        // Add each new location to the queue to search
        mazeQueue.add(l);
      });
    }

    // Null check to make sure we've found a solution
    if (finish instanceof Location) {
      // Create the path array using the distance from start contained in the finish
      // location
      Location[] path = new Location[finish.getDistFromStart() + 1];
      Location current = finish;

      // Find the path back to the start
      // I thought about using recursion but the iterative solution was simpler and
      // required less work than the recursive solution would have
      for (int i = finish.getDistFromStart(); current.getLastPoint() instanceof Coordinate && i >= 0; i--) {
        // Set the current point in the path
        path[i] = current;
        // Get the next position from the last point of the current element
        int nextX = current.getLastPoint().getX();
        int nextY = current.getLastPoint().getY();
        // Get the actual location from the maze array
        current = mazeDetails[nextY][nextX];
      }

      // This will be the start node
      path[0] = current;

      return path;
    } else {
      // We have no path to the finish. Return null to indicate that.
      return null;
    }
  }

  /**
   * This will check whether the point specified in newLocation is a valid move,
   * i.e. not a wall, the start, or a space that we've already found
   * 
   * @param previous    the location we are searching from
   * @param newLocation the location we are testing
   * @return a location if it's valid or null otherwise
   */
  private Location checkNext(Location previous, Location newLocation) {
    // Use a switch statement to check the type
    switch (newLocation.getType()) {
      case OPEN:
        // We have an open space. Mark it as found
        newLocation.setType(Location.SquareType.FOUND);
        // Fall through to the finish case so we also set the values there too
      case FINISH:
        // We have a new distance from start
        newLocation.setDistFromStart(previous.getDistFromStart() + 1);
        // We know that this is a valid move, so set the parent
        newLocation.setLastPoint(previous.getPosition());
        return newLocation;
      default:
        return null;
    }
  }

  /**
   * This will search each of the cardinal directions to see if there is a valid
   * move in that direction
   * 
   * @param current the node to search around
   * @return an {@link ArrayList} that will contain all the valid moves around the
   *         current space
   */
  public ArrayList<Location> searchMoves(Location current) {
    // This is the array of valid moves from the current position
    ArrayList<Location> returnValue = new ArrayList<>();
    // Store the position so we don't have to call the getter every time
    Coordinate location = current.getPosition();

    // Check to make sure we don't get an IndexOutOfBounds exception
    if (location.getX() > 0) {
      // Check the space to the west of the current space
      Location nextLocation = checkNext(current, mazeDetails[location.getY()][location.getX() - 1]);
      // Null check
      if (nextLocation instanceof Location) {
        returnValue.add(nextLocation);
      }
    }

    // Check to make sure we don't get an IndexOutOfBounds exception
    if (location.getX() < (mazeDetails[0].length - 1)) {
      // Check the space to the east of the current space
      Location nextLocation = checkNext(current, mazeDetails[location.getY()][location.getX() + 1]);
      // Null check
      if (nextLocation instanceof Location) {
        returnValue.add(nextLocation);
      }
    }

    // Check to make sure we don't get an IndexOutOfBounds exception
    if (location.getY() > 0) {
      // Check the space to the north of the current space
      Location nextLocation = checkNext(current, mazeDetails[location.getY() - 1][location.getX()]);
      // Null check
      if (nextLocation instanceof Location) {
        returnValue.add(nextLocation);
      }
    }
    // Check to make sure we don't get an IndexOutOfBounds exception
    if (location.getY() < (mazeDetails.length - 1)) {
      // Check the space to the south of the current space
      Location nextLocation = checkNext(current, mazeDetails[location.getY() + 1][location.getX()]);
      // Null check
      if (nextLocation instanceof Location) {
        returnValue.add(nextLocation);
      }
    }

    return returnValue;
  }

  /**
   * This method iterates over the array and finds the start specified by an S
   * 
   * @return the location of the square that is marked with an S in the array
   */
  public Location findStart() {
    for (Location[] row : mazeDetails) {
      for (Location point : row) {
        if (point.getType() == Location.SquareType.START) {
          return point;
        }
      }
    }

    return null;
  }

  /**
   * Helper function for printing the maze. Useful for debugging
   */
  public void printMaze() {
    for (int i = 0; i < mazeDetails.length; i++) {
      for (int j = 0; j < mazeDetails[0].length; j++) {
        System.out.print(mazeDetails[i][j]);
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void main(String[] args) {
    // Call the getUserInput function to get the initial state of the maze
    Location[][] mazeInformation = getUserInput();

    // Initialize the MazeSolver class with the maze information
    MazeSolver solver = new MazeSolver(mazeInformation);
    // Call the breadthFirstSearch method to get the path from the start to finish
    Location[] path = solver.breadthFirstSearch();

    // Check if we actually found a path from start to finish
    if (path == null) {
      System.out.println("Maze not solvable.");
      return;
    }

    // Print the path if we have one
    printPath(path);

    System.out.printf("Total distance = %d", path.length - 1);
  }

  /**
   * Helper method for printing the location array
   * 
   * @param path the path from start to finish
   */
  private static void printPath(Location[] path) {
    for (var node : path) {
      System.out.printf("<%d %d>\n", node.getPosition().getY(), node.getPosition().getX());
    }
  }

  /**
   * Helper method for getting the maze from the user
   * 
   * @return the 2D location array representing the maze
   */
  private static Location[][] getUserInput() {
    // Try with resources so we don't leak a scanner
    try (Scanner sc = new Scanner(System.in)) {
      // Get the size of the maze from the user.
      int sizeY = sc.nextInt();
      int sizeX = sc.nextInt();

      // Initialize a maze that has the specified dimensions
      Location[][] maze = new Location[sizeY][sizeX];

      // We are assuming that the user has only input correct data.
      for (int i = 0; i < sizeY; i++) {
        // Get the next line of input from the user
        String inputLine = sc.next();

        for (int j = 0; j < inputLine.length(); j++) {
          // We have to reference the SquareType enum like this because Gradel does not
          // like packages
          Location.SquareType type = Location.SquareType.valueOfDisplay(inputLine.substring(j, j + 1));

          // Create a new location and assign it to that point in the maze
          maze[i][j] = new Location(new Coordinate(j, i), null, type, 0);
        }
      }

      return maze;
    }
  }
}
