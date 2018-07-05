package domain;

import java.util.List;

public class Report {

    private List<Item> item;
    private double totalValue;
    private double profit;


    public Report(List<Item> item, double totalValue, double profit) {
        this.item = item;
        this.totalValue = totalValue;
        this.profit = profit;
    }

    public Report() {
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
