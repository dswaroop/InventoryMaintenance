package managedbeans;

public class ValidatorBean {

    public static boolean validateCommand(String cmd) {

        if(cmd.startsWith("create") ||
                cmd.startsWith("updateBuy") ||
                cmd.startsWith("updateSell") ||
                cmd.startsWith("report") ||
                cmd.startsWith("delete") ||
                cmd.startsWith("updateSellPrice")) {

            return true;
        }
        return false;
    }
}
