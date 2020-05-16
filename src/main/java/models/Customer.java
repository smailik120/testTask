package models;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Product> purchases = new ArrayList<Product>();
    private int totalExpenses;

    public Customer(String name, List<Product> purchases, int totalExpenses) {
        this.name = name;
        this.purchases = purchases;
        this.totalExpenses = totalExpenses;
    }
}
