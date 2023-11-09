package hw4;

public interface Stack<T> {
  public void push(T element);

  public T pop() throws StackEmptyException;

  public T top() throws StackEmptyException;

  public int size();

  public boolean isEmpty();
}
