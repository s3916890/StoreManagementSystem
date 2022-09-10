package controller;

import controller.Account;
import view.AdminMenu;

import java.io.IOException;
import java.util.Scanner;

public class Admin extends Account {
    public Admin() {
        super();
    }

    public String adNameLoginInput() throws IOException, InterruptedException {
        AdminMenu menu = new AdminMenu();
        Scanner sc = new Scanner(System.in);
        System.out.print("Developer Name: ");
        String adName = sc.nextLine();
        if (!adName.equals("admin")) {
            System.out.println("Developer Name is not available, please try again !!!");
            menu.adView();
        }
        return adName;
    }

    public boolean verifyAdmin(String admin, String password) {
        String hPassword = this.hashing(password);
        return admin.equals("admin") && hPassword.equals("21232f297a57a5a743894a0e4a801fc3");
    }
}
