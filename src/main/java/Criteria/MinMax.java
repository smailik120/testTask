package Criteria;

public class MinMax implements Criteria{
    int minExpenses;
    int maxExpenses;

    public MinMax(int minExpenses, int maxExpenses) {
        this.minExpenses = minExpenses;
        this.maxExpenses = maxExpenses;
    }
}
