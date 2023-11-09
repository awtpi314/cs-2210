package com.awtpi314.project4.interfaces;

import com.awtpi314.project4.exceptions.InvalidDataException;
import com.awtpi314.project4.exceptions.QueueEmptyException;

public interface Queue<E> {
    public void enqueue(E element) throws InvalidDataException;
    public E dequeue() throws QueueEmptyException;
    public E front() throws QueueEmptyException;
    public int size();
    public boolean isEmpty();
}
