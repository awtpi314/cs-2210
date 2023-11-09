package hw4;

public class NodeListStack<T> implements Stack<T> {
  PositionalList<T> data;

  public NodeListStack() {
    data = new NodePositionList<>();
  }

  @Override
  public void push(T element) {
    data.addLast(element);
  }

  @Override
  public T pop() throws StackEmptyException {
    if (size() < 1) {
      throw new StackEmptyException("Tried to pop when there was nothing on the stack.");
    }
    try {
      return data.remove(data.last());
    } catch (IllegalArgumentException e) {
      throw new StackEmptyException("Error popping from the stack.");
    }
  }

  @Override
  public T top() throws StackEmptyException {
    if (size() < 1) {
      throw new StackEmptyException("Tried to top when there was nothing in the stack.");
    }
    if (data.last() != null) {
      return data.last().getElement();
    } else {
      throw new StackEmptyException("Tried to top when there was nothing in the stack.");
    }
  }

  @Override
  public int size() {
    return data.size();
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }
}
