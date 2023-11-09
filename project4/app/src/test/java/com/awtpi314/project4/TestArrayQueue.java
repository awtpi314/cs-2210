package com.awtpi314.project4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;

import com.awtpi314.project4.exceptions.InvalidDataException;
import com.awtpi314.project4.exceptions.QueueEmptyException;

/**
 * <h3>TestArrayQueue</h3>
 * 
 * TestArrayQueue uses a few different test cases to ensure proper operation of
 * the whole queue.
 * 
 * @author Alexander Taylor
 * @version 1.0
 * @since 10/16/2023
 */
public class TestArrayQueue {
  /**
   * <h3>simpleOperations</h3>
   * 
   * Tests some simple operations of the queue. The queue should resize on the
   * last loop iteration in this test.
   * 
   * We ensure that the size is the proper number after each operation and compare
   * the list to what is expected at the end.
   */
  @Test
  public void simpleOperations() {
    // Init queue with initial size of 10
    ArrayQueue<Integer> queue = new ArrayQueue<>();

    // These are the elements that we will test adding to queue
    int[] elements = { 10, 13, 2, 6, 102, 321, 5, 93, 54, 87 };

    // Enqueue all the elements ensuring there are no exceptions thrown
    for (int i = 0; i < elements.length; i++) {
      try {
        queue.enqueue(elements[i]);
      } catch (InvalidDataException e) {
        throw new JUnitException("This should not have thrown an error", e);
      }

      // Test for size equality
      assertEquals(i + 1, queue.size());
    }

    // Returned elements holds the values that we dequeue from the list
    int[] returnedElements = new int[elements.length];
    for (int i = elements.length - 1; i >= 0; i--) {
      try {
        // Dequeue and put it in its proper place in the list
        returnedElements[elements.length - i - 1] = queue.dequeue();
      } catch (QueueEmptyException e) {
        throw new JUnitException("The queue should not be empty yet", e);
      }

      // Size equality again
      assertEquals(i, queue.size());
    }

    // Ensure that the arrays are identical
    assertTrue(Arrays.compare(elements, returnedElements) == 0);
  }

  /**
   * <h3>largeWrappingQueue</h3>
   * 
   * This is basically an extension of {@link #simpleOperations()
   * simpleOperations}. It enqueues five elements, and then dequeues them, making
   * it so that the queue will wrap around in the first set. We then proceed to
   * enqueue the other 10000 elements.
   */

  @Test
  public void largeWrappingQueue() {
    // Init with the default size of 10
    ArrayQueue<Integer> queue = new ArrayQueue<>();

    // Create our two arrays for holding elements
    int[] elements = new int[10005];
    int[] returnedElements = new int[10005];

    // Initialize the array to random values
    for (int i = 0; i < elements.length; i++) {
      elements[i] = (int) Math.floor(Math.random() * elements.length);
    }

    // Enqueue five elements to facilitate circular assignment
    for (int i = 0; i < 5; i++) {
      try {
        queue.enqueue(elements[i]);
      } catch (InvalidDataException e) {
        throw new JUnitException("None of the data should be invalid", e);
      }
    }

    // Dequeue those elements so that we don't expand until we wrap around
    for (int i = 0; i < 5; i++) {
      try {
        returnedElements[i] = queue.dequeue();
      } catch (QueueEmptyException e) {
        throw new JUnitException("Queue should not be empty");
      }
    }

    // Enqueue the rest of the 10005 elements
    for (int i = 5; i < elements.length; i++) {
      try {
        queue.enqueue(elements[i]);
      } catch (InvalidDataException e) {
        throw new JUnitException("None of the data should be invalid", e);
      }
    }

    // Dequeue them, keeping the result in the array
    for (int i = 5; i < elements.length; i++) {
      try {
        returnedElements[i] = queue.dequeue();
      } catch (QueueEmptyException e) {
        throw new JUnitException("Queue should not be empty");
      }
    }

    // Make sure the arrays are identical
    assertTrue(Arrays.compare(elements, returnedElements) == 0);
  }

  /**
   * <h3>emptyQueue</h3>
   * 
   * Test what will happen if we try to dequeue from an empty queue. It should
   * throw and {@link com.awtpi314.project4.exceptions.QueueEmptyException
   * QueueEmptyException} which we test for by using the assertThrows
   */
  @Test
  public void emptyQueue() {
    // Init with default size of 10
    ArrayQueue<Integer> queue = new ArrayQueue<>();

    // Enqueue five elements
    for (int i = 0; i < 5; i++) {
      try {
        queue.enqueue(i);
      } catch (InvalidDataException e) {
        throw new JUnitException("None of the data should be invalid", e);
      }
    }

    // Dequeue those elements
    for (int i = 0; i < 5; i++) {
      try {
        queue.dequeue();
      } catch (QueueEmptyException e) {
        throw new JUnitException("Queue should not be empty");
      }
    }

    // Make sure an expcetion is thrown when we try to dequeue another element
    assertThrows(QueueEmptyException.class, () -> {
      queue.dequeue();
    });
  }

  /**
   * <h3>smallInitialSize</h3>
   * 
   * This tests the resizing of the array when the initial size is 1. We enqueue
   * five elements and the queue should have resized to a capacity of 8.
   */
  @Test
  public void smallInitialSize() {
    // Init with default size
    ArrayQueue<Integer> queue = new ArrayQueue<>(1);

    // Add five elements
    for (int i = 0; i < 5; i++) {
      try {
        queue.enqueue(i);
      } catch (InvalidDataException e) {
        throw new JUnitException("None of the data should be invalid", e);
      }
    }

    // Make sure that the size is correct
    assertEquals(5, queue.size());
  }

  /**
   * <h3>enqueueNullElements</h3>
   * 
   * This will try to enqueue a null value to the queue. It should throw an
   * {@link com.awtpi314.project4.exceptions.InvalidDataException
   * InvalidDataException} since null is not an instance of the generic type.
   */
  @Test
  public void enqueueNullElements() {
    // Init with default size
    ArrayQueue<Integer> queue = new ArrayQueue<>();

    // Try to enqueue a null element
    assertThrows(InvalidDataException.class, () -> {
      queue.enqueue(null);
    });
  }
}