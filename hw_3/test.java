public class test {
  private static int getBestMeals(int lifeLengthDays) {
    final int numMeals = lifeLengthDays * 3;
    int numBestMeals = 0;
    int bestMeal = 0;
    for (int i = 0; i < numMeals; i++) {
      final int currentMeal = (int) Math.floor(Math.random() * numMeals) + 1;
      if (currentMeal > bestMeal) {
        bestMeal = currentMeal;
        numBestMeals++;
      }
    }

    return numBestMeals;
  }

  public static void main(String[] args) {
    final int numRuns = 10000;
    final int lifeLength = 1000;
    int total = 0;
    for (int i = 0; i < numRuns; i++) {
      total += getBestMeals(lifeLength);
    }

    double totalTheory = 0;
    for (int i = 1; i < lifeLength * 3; i++) {
      totalTheory += 1.0 / i;
    }

    System.out.printf(
        "Run with %d trials. Life length is %d. Average best meals is %f. Theory says that it should be %f", numRuns,
        lifeLength, (double) total / numRuns, totalTheory);
  }
}
