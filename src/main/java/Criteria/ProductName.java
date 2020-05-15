package Criteria;

public class ProductName implements Criteria{
    String productName;
    int minTimes;

    public ProductName(String productName, int minTimes) {
        this.productName = productName;
        this.minTimes = minTimes;
    }
}
