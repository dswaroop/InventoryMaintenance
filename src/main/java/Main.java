import managedbeans.BusinessBean;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        BusinessBean businessBean = new BusinessBean();
        String command = "";

        while(!command.equalsIgnoreCase("#")) {
            System.out.print("Enter Command : ");
            command = keyboard.nextLine();

            businessBean.performOperation(command);
        }
    }
}
