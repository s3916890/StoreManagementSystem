package view;

import lib.OptionInput;

import java.io.IOException;

public class VisitOrNot {
    public void view() throws IOException {
        System.out.println("\n===================================================================== Keep visiting or exit !!! =====================================================================");
        System.out.println("""
                    1. Visit
                    2. Exit""");
        Menu menu = new Menu();
        VisitOrNot visitOrNot = new VisitOrNot();
        String option = OptionInput.input();
        switch (option) {
            case "1" -> {
                menu.view();
            }
            case "2" -> {
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.exit(1);
            }
            default -> {
                System.out.println("There is no match option, please enter again");
                visitOrNot.view();
            }
        }
    }
}
