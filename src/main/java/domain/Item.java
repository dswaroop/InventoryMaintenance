package domain;

public class Item {

    private String name;
    private double boughtAt;
    private double soldAt;
    private int availableQty;
    private double value;

    public Item() {
    }

    public Item(String name, double boughtAt, double soldAt) {
        this.name = name;
        this.boughtAt = boughtAt;
        this.soldAt = soldAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBoughtAt() {
        return boughtAt;
    }

    public void setBoughtAt(double boughtAt) {
        this.boughtAt = boughtAt;
    }

    public double getSoldAt() {
        return soldAt;
    }

    public void setSoldAt(double soldAt) {
        this.soldAt = soldAt;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;

        //calculating value
        this.value =  boughtAt * availableQty;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return (" BoughtAt : "+boughtAt+
                " SoldAt : "+soldAt+" Total Qty : "+availableQty+" value : "+value);

    }
}
