package managedbeans;

import domain.Item;
import domain.QtyPriceModel;
import domain.Report;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class InventoryUtils {

    static Map<String, List<QtyPriceModel>> qtySoldByItem = new HashMap<String, List<QtyPriceModel>>();

    public static void prepareModelAndUpdateQtySold(String itemName, String qty, double newPrice) {

        QtyPriceModel priceModel = new QtyPriceModel();
        priceModel.setName(itemName);
        priceModel.setPrice(newPrice);
        priceModel.setQty(Integer.parseInt(qty));

        if(!qtySoldByItem.containsKey(itemName)) {
            List<QtyPriceModel> priceModels = new ArrayList<QtyPriceModel>();
            priceModels.add(priceModel);
            qtySoldByItem.put(itemName, priceModels);
        } else {
            qtySoldByItem.get(itemName).add(priceModel);
        }
    }

    public static Report buildReport(Map<String, Item> inventory,
                                      List<Item> deletedItems) {

        DecimalFormat df = new DecimalFormat(".##");
        df.setRoundingMode(RoundingMode.DOWN);

        double totalValue = 0.0;
        for(Item item : inventory.values()) {
            totalValue += item.getValue();
        }

        double profitValue = 0.0;
        for(String itemName : qtySoldByItem.keySet()) {
            Item item = inventory.get(itemName);
            List<QtyPriceModel> qtyPriceModels = qtySoldByItem.get(itemName);

            for(QtyPriceModel priceModel : qtyPriceModels) {
                double margin = priceModel.getPrice() - item.getBoughtAt();
                double profitOnItem = margin * priceModel.getQty();

                profitValue += profitOnItem;
            }
        }

        double deletedItemsTotalValue = 0.0;
        for(Item item : deletedItems) {
            deletedItemsTotalValue += item.getValue();
        }
        double finalProfit = profitValue - deletedItemsTotalValue;

        Report report = new Report(new ArrayList(inventory.values()),
                Double.valueOf(df.format(totalValue)),
                Double.valueOf(df.format(finalProfit)));

        //resetting qtySoldByItem
        qtySoldByItem = new HashMap<String, List<QtyPriceModel>>();

        return report;
    }

    public static void printReport(List<Report> reports) {
        if(reports.size() > 0) {
            for(Report report : reports) {

                sortItems(report.getItem());

                System.out.println("                    INVENTORY REPORT");
                System.out.println("    Item Name               Bought At               Sold At             AvailableQty            Value");
                System.out.println("  ===============       =================       ===============      ===================      ============");
                for(Item item : report.getItem()) {
                    System.out.println("    "+item.getName()+"                "+item.getBoughtAt()+"                      "+item.getSoldAt()+"                  "+item.getAvailableQty()+"                      "+item.getValue());
                }
                System.out.println("  ========================================================================================================");
                System.out.println("    Total Value                                                                                     "+report.getTotalValue());
                System.out.println("    Profit Since Previous Report                                                                    "+report.getProfit());
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
            }
        } else {
            System.err.println("No Report Found");
        }
    }

    public static void sortItems(List<Item> items) {

        if(items.size() > 0) {
            Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(final Item object1, final Item object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
        }

    }
}
