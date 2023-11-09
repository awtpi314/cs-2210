package com.awtpi314.project4;

import com.awtpi314.project4.exceptions.InvalidDataException;
import com.awtpi314.project4.exceptions.QueueEmptyException;
import com.awtpi314.project4.interfaces.Queue;

/**
 * <h3>ArrayQueue</h3>
 * 
 * This implements the {@link com.awtpi314.project4.interfaces.Queue Queue}
 * interface using generics.
 * 
 * @author Alexander Taylor
 * @version 1.0
 * @since 10/16/2023
 */
public class ArrayQueue<E> implements Queue<E> {
  private Object[] elements;
  private int size;
  private int start;

  /**
   * <h3>ArrayQueue</h3>
   * 
   * Constructs the ArrayQueue with a default size of 10
   */
  public ArrayQueue() {
    this(10);
  }

  /**
   * <h3>ArrayQueue</h3>
   * 
   * Constructs the ArrayQueue with the passed size
   * 
   * @param initialSize the size to start with
   */
  public ArrayQueue(int initialSize) {
    if (initialSize <= 0) {
      throw new IndexOutOfBoundsException("Cannot have a queue with an initial size less than 1");
    }

    elements = new Object[initialSize];
    size = 0;
    start = 0;
  }

  /**
   * <h3>enqueue</h3>
   * 
   * Adds the element to the queue, checking first if it is null
   * 
   * @param element the element to add to the queue
   */
  @Override
  public void enqueue(E element) throws InvalidDataException {
    // Null check
    if (!(element instanceof E)) {
      throw new InvalidDataException("Null is not a valid element for this queue");
    }

    // Size up if we need
    if (size >= elements.length) {
      sizeUp();
    }

    // Assign the element to our end location
    elements[(start + size) % elements.length] = element;
    size++;
  }

  /**
   * <h3>dequeue</h3>
   * 
   * Removes the first element from the queue and throws and exception if the
   * queue is empty
   * 
   * @returns the first element in the queue
   * @throws QueueEmptyException
   */
  @Override
  public E dequeue() throws QueueEmptyException {
    // Empty check
    if (isEmpty()) {
      throw new QueueEmptyException("The queue is empty");
    }

    // Need unchecked here
    @SuppressWarnings("unchecked")
    // Cast to the generic type
    E element = (E) elements[start];
    // Null the element so the garbage collector takes care of it
    elements[start] = null;
    // Set the new start, wrapping around if we need to
    start = (start + 1) % elements.length;
    size--;

    return element;
  }

  /**
   * <h3>front</h3>
   * 
   * Same as the dequeue method but doesn't remove the element from the array
   * 
   * @return the element at the front of the queue
   */
  @Override
  public E front() throws QueueEmptyException {
    if (isEmpty()) {
      throw new QueueEmptyException("The queue is empty");
    }

    // Get the element at the index indicated by start
    // We need this to be in two separate lines in order to use SuppressWarnings
    @SuppressWarnings("unchecked")
    E element = (E) elements[start];

    return element;
  }

  /**
   * <h3>size</h3>
   * 
   * Get how many elements are in the queue
   * 
   * @return the size of the queue
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * <h3>isEmpty</h3>
   * 
   * Checks the size of the queue to see if it is empty
   * 
   * @return if the queue is empty
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * <h3>sizeUp</h3>
   * 
   * Increases the size of the array by a factor of two and reorders it if needed
   */
  private void sizeUp() {
    // Create a new array with a size double that of our current array
    Object[] latestAndGreatest = new Object[elements.length * 2];
    // Loop over the current queue
    for (int i = 0; i < size; i++) {
      // Take into account potential wrap-around and move them into the proper places
      latestAndGreatest[i] = elements[(start + i) % elements.length];
    }

    // Set our member variable queue to the new one that we've made
    elements = latestAndGreatest;
    // Reset start and end to what they should be
    start = 0;
  }

  public static void main(String[] args) {
  }
}
