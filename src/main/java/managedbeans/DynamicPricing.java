package managedbeans;

import java.util.HashMap;
import java.util.Map;

public class DynamicPricing {

    static Map<String, Double> pricingMap = new HashMap<String, Double>();

    public static void setNewPriceOnItem(String itemName, double newPrice) {
        pricingMap.put(itemName, newPrice);
    }

    public static double getNewPrice(String itemName, double oldPrice) {
        if(pricingMap.containsKey(itemName))  {
            return pricingMap.get(itemName);
        }
        return oldPrice;
    }
}
