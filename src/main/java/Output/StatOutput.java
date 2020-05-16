package Output;

import models.Customer;

import java.util.ArrayList;
import java.util.List;

public class StatOutput  implements Output {
    private String type;
    private int totalDays;
    private List<Customer> customers;
    private int totalExpenses;
    private double avgExpenses;
    public StatOutput(int totalDays, List<Customer> customers, int totalExpenses, double avgExpenses) {
        this.type = "stat";
        this.totalDays = totalDays;
        this.customers = customers;
        this.totalExpenses = totalExpenses;
        this.avgExpenses = avgExpenses;
    }

}
