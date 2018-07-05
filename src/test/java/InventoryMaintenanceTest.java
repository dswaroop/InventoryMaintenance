import managedbeans.BusinessBean;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryMaintenanceTest {

    BusinessBean businessBean = null;

    @Before
    public void beforeTest() {
        businessBean = new BusinessBean();
    }

    @After
    public void tearDown() {
        businessBean = null;
    }

    @Test
    public void testFullRun() {

        businessBean.performOperation("create Book01 10.50 13.79");
        businessBean.performOperation("create Food01 1.47 3.98");
        businessBean.performOperation("create Med01 30.63 34.29");
        businessBean.performOperation("create Tab01 57.00 84.98");
        businessBean.performOperation("updateBuy Tab01 100");
        businessBean.performOperation("updateSell Tab01 2");
        businessBean.performOperation("updateBuy Food01 500");
        businessBean.performOperation("updateBuy Book01 100");
        businessBean.performOperation("updateBuy Med01 100");
        businessBean.performOperation("updateSell Food01 1");
        businessBean.performOperation("updateSell Food01 1");
        businessBean.performOperation("updateSell Tab01 2");
        businessBean.performOperation("report");
        businessBean.performOperation("#");

        Assert.assertEquals(10317.06, businessBean.getReports().get(0).getTotalValue(), 0.01);

    }

    @Test
    public void testCreateItem() {
        businessBean.performOperation("create Book01 10.50 13.79");
        Assert.assertEquals(1, businessBean.getInventory().size());
    }

    @Test
    public void testUpdateItemBuy() {
        businessBean.performOperation("create Tab01 57.00 84.98");
        businessBean.performOperation("updateBuy Tab01 100");

        Assert.assertEquals(100, businessBean.getInventory().get("Tab01").getAvailableQty());
    }

    @Test
    public void testUpdateItemSell() {
        businessBean.performOperation("create Food01 1.47 3.98");
        businessBean.performOperation("updateBuy Food01 500");
        businessBean.performOperation("updateSell Food01 1");

        Assert.assertEquals(499, businessBean.getInventory().get("Food01").getAvailableQty());
    }

    @Test
    public void testDeleteItem() {
        businessBean.performOperation("create Book01 10.50 13.79");
        businessBean.performOperation("delete Book01");

        Assert.assertNotNull(businessBean.getInventory());
    }

    @Test
    public void testReport() {
        businessBean.performOperation("create Food01 1.47 3.98");
        businessBean.performOperation("updateBuy Food01 500");
        businessBean.performOperation("updateSell Food01 1");
        businessBean.performOperation("report");

        Assert.assertNotNull(businessBean.getInventory().get("Food01"));
    }

    @Test
    public void testUpdateSellPrice() {
        businessBean.performOperation("create Food01 1.47 3.47");

        businessBean.performOperation("updateBuy Food01 500");
        businessBean.performOperation("updateSell Food01 1");
        businessBean.performOperation("report");
        Assert.assertEquals(new Double(2.0).doubleValue(), businessBean.getReports().get(0).getProfit(), 0.01);

        businessBean.performOperation("updateSellPrice Food01 4.47");
        businessBean.performOperation("updateSell Food01 1");
        businessBean.performOperation("report");
        Assert.assertEquals(new Double(3.0).doubleValue(), businessBean.getReports().get(1).getProfit(), 0.01);

    }


}
