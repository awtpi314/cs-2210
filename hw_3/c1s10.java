/**
 * 1-10-3
 */
public class c1s10 {
  public static boolean isMultiple(long n, long m) {
    return n % m == 0;
  }

  public static boolean isEven(int i) {
    return (i & 0x1) == 0;
  }

  private static int sumLessThan(int n) {
    if (n % 2 != 0) {
      n += 1;
    }
    return n * n;
  }

  public static void main(String[] args) {
    int testValues[] = {4, 13, 6, 9, 10, 16};
    for (int val : testValues) {
      System.out.printf("Testing with %d - %d\n", val, sumLessThan(val));
    }
  }
}