package managedbeans;

import domain.Item;
import domain.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessBean {

    Map<String, Item> inventory = new HashMap<String, Item>();
    List<Item> deletedItems = new ArrayList<Item>();


    List<Report> reports = new ArrayList<Report>();

    public void performOperation(String cmd) {

        if(cmd.equalsIgnoreCase("#")) {
            InventoryUtils.printReport(reports);
        }

        boolean isValid = ValidatorBean.validateCommand(cmd);

        if(isValid) {
            String[] cmdSplit = cmd.split(" ");
            if(cmdSplit[0].equalsIgnoreCase("create")) {

                createItem(cmdSplit[1], cmdSplit[2], cmdSplit[3]);

            } else if(cmdSplit[0].equalsIgnoreCase("updateBuy")) {

                updateItemBuy(cmdSplit[1], cmdSplit[2]);

            } else if(cmdSplit[0].equalsIgnoreCase("updateSell")) {

                updateItemSell(cmdSplit[1], cmdSplit[2]);

            } else if(cmdSplit[0].equalsIgnoreCase("delete")) {

                deleteItem(cmdSplit[1]);

            } else if(cmdSplit[0].equalsIgnoreCase("report")) {

                report();

            } else if(cmdSplit[0].equalsIgnoreCase("updateSellPrice")) {

                updateSellPrice(cmdSplit[1], cmdSplit[2]);

            }
        }
    }

    private void createItem(String itemName, String buyAt, String sellAt) {

        Item item = new Item(itemName, Double.parseDouble(buyAt), Double.parseDouble(sellAt));
        getInventory().put(itemName, item);
    }

    private void updateItemBuy(String itemName, String qty) {
        Item item = getInventory().get(itemName);
        item.setAvailableQty(Integer.parseInt(qty));

        getInventory().put(itemName, item);
    }

    private void updateItemSell(String itemName, String qty) {
        Item item = getInventory().get(itemName);
        item.setAvailableQty(item.getAvailableQty()-Integer.parseInt(qty));
        getInventory().put(itemName, item);

        double newPrice = DynamicPricing.getNewPrice(itemName, item.getSoldAt());

        InventoryUtils.prepareModelAndUpdateQtySold(itemName, qty, newPrice);
    }



    private void deleteItem(String itemName) {
        if(getInventory().containsKey(itemName)) {
            deletedItems.add(getInventory().get(itemName));

            getInventory().remove(itemName);
        }
    }

    private void report() {
        Report report = InventoryUtils.buildReport(getInventory(), deletedItems);
        reports.add(report);
    }

    public Map<String, Item> getInventory() {
        return inventory;
    }

    private void updateSellPrice(String itemName, String newSellPrice) {
        double newPrice = Double.parseDouble(newSellPrice);

        if(getInventory().containsKey(itemName)) {
            DynamicPricing.setNewPriceOnItem(itemName, newPrice);
        }
    }

    public List<Report> getReports() {
        return reports;
    }


}
